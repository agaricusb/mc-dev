package net.minecraft.server;

import java.util.List;

public abstract class EntityFireball extends Entity
{
    private int e = -1;
    private int f = -1;
    private int g = -1;
    private int h = 0;
    private boolean i = false;
    public EntityLiving shooter;
    private int j;
    private int as = 0;
    public double dirX;
    public double dirY;
    public double dirZ;

    public EntityFireball(World par1World)
    {
        super(par1World);
        this.a(1.0F, 1.0F);
    }

    protected void a() {}

    public EntityFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World);
        this.a(1.0F, 1.0F);
        this.setPositionRotation(par2, par4, par6, this.yaw, this.pitch);
        this.setPosition(par2, par4, par6);
        double var14 = (double) MathHelper.sqrt(par8 * par8 + par10 * par10 + par12 * par12);
        this.dirX = par8 / var14 * 0.1D;
        this.dirY = par10 / var14 * 0.1D;
        this.dirZ = par12 / var14 * 0.1D;
    }

    public EntityFireball(World par1World, EntityLiving par2EntityLiving, double par3, double par5, double par7)
    {
        super(par1World);
        this.shooter = par2EntityLiving;
        this.a(1.0F, 1.0F);
        this.setPositionRotation(par2EntityLiving.locX, par2EntityLiving.locY, par2EntityLiving.locZ, par2EntityLiving.yaw, par2EntityLiving.pitch);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0F;
        this.motX = this.motY = this.motZ = 0.0D;
        par3 += this.random.nextGaussian() * 0.4D;
        par5 += this.random.nextGaussian() * 0.4D;
        par7 += this.random.nextGaussian() * 0.4D;
        double var9 = (double) MathHelper.sqrt(par3 * par3 + par5 * par5 + par7 * par7);
        this.dirX = par3 / var9 * 0.1D;
        this.dirY = par5 / var9 * 0.1D;
        this.dirZ = par7 / var9 * 0.1D;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        if (!this.world.isStatic && (this.shooter != null && this.shooter.dead || !this.world.isLoaded((int) this.locX, (int) this.locY, (int) this.locZ)))
        {
            this.die();
        }
        else
        {
            super.j_();
            this.setOnFire(1);

            if (this.i)
            {
                int var1 = this.world.getTypeId(this.e, this.f, this.g);

                if (var1 == this.h)
                {
                    ++this.j;

                    if (this.j == 600)
                    {
                        this.die();
                    }

                    return;
                }

                this.i = false;
                this.motX *= (double)(this.random.nextFloat() * 0.2F);
                this.motY *= (double)(this.random.nextFloat() * 0.2F);
                this.motZ *= (double)(this.random.nextFloat() * 0.2F);
                this.j = 0;
                this.as = 0;
            }
            else
            {
                ++this.as;
            }

            Vec3D var15 = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
            Vec3D var2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            MovingObjectPosition var3 = this.world.a(var15, var2);
            var15 = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
            var2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);

            if (var3 != null)
            {
                var2 = this.world.getVec3DPool().create(var3.pos.c, var3.pos.d, var3.pos.e);
            }

            Entity var4 = null;
            List var5 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
            double var6 = 0.0D;

            for (int var8 = 0; var8 < var5.size(); ++var8)
            {
                Entity var9 = (Entity)var5.get(var8);

                if (var9.L() && (!var9.i(this.shooter) || this.as >= 25))
                {
                    float var10 = 0.3F;
                    AxisAlignedBB var11 = var9.boundingBox.grow((double) var10, (double) var10, (double) var10);
                    MovingObjectPosition var12 = var11.a(var15, var2);

                    if (var12 != null)
                    {
                        double var13 = var15.d(var12.pos);

                        if (var13 < var6 || var6 == 0.0D)
                        {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }

            if (var4 != null)
            {
                var3 = new MovingObjectPosition(var4);
            }

            if (var3 != null)
            {
                this.a(var3);
            }

            this.locX += this.motX;
            this.locY += this.motY;
            this.locZ += this.motZ;
            float var16 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.yaw = (float)(Math.atan2(this.motZ, this.motX) * 180.0D / Math.PI) + 90.0F;

            for (this.pitch = (float)(Math.atan2((double)var16, this.motY) * 180.0D / Math.PI) - 90.0F; this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F)
            {
                ;
            }

            while (this.pitch - this.lastPitch >= 180.0F)
            {
                this.lastPitch += 360.0F;
            }

            while (this.yaw - this.lastYaw < -180.0F)
            {
                this.lastYaw -= 360.0F;
            }

            while (this.yaw - this.lastYaw >= 180.0F)
            {
                this.lastYaw += 360.0F;
            }

            this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
            this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
            float var17 = this.c();

            if (this.H())
            {
                for (int var19 = 0; var19 < 4; ++var19)
                {
                    float var18 = 0.25F;
                    this.world.addParticle("bubble", this.locX - this.motX * (double) var18, this.locY - this.motY * (double) var18, this.locZ - this.motZ * (double) var18, this.motX, this.motY, this.motZ);
                }

                var17 = 0.8F;
            }

            this.motX += this.dirX;
            this.motY += this.dirY;
            this.motZ += this.dirZ;
            this.motX *= (double)var17;
            this.motY *= (double)var17;
            this.motZ *= (double)var17;
            this.world.addParticle("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
            this.setPosition(this.locX, this.locY, this.locZ);
        }
    }

    /**
     * Return the motion factor for this projectile. The factor is multiplied by the original motion.
     */
    protected float c()
    {
        return 0.95F;
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected abstract void a(MovingObjectPosition var1);

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("xTile", (short)this.e);
        par1NBTTagCompound.setShort("yTile", (short)this.f);
        par1NBTTagCompound.setShort("zTile", (short)this.g);
        par1NBTTagCompound.setByte("inTile", (byte)this.h);
        par1NBTTagCompound.setByte("inGround", (byte)(this.i ? 1 : 0));
        par1NBTTagCompound.set("direction", this.a(new double[]{this.motX, this.motY, this.motZ}));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.e = par1NBTTagCompound.getShort("xTile");
        this.f = par1NBTTagCompound.getShort("yTile");
        this.g = par1NBTTagCompound.getShort("zTile");
        this.h = par1NBTTagCompound.getByte("inTile") & 255;
        this.i = par1NBTTagCompound.getByte("inGround") == 1;

        if (par1NBTTagCompound.hasKey("direction"))
        {
            NBTTagList var2 = par1NBTTagCompound.getList("direction");
            this.motX = ((NBTTagDouble)var2.get(0)).data;
            this.motY = ((NBTTagDouble)var2.get(1)).data;
            this.motZ = ((NBTTagDouble)var2.get(2)).data;
        }
        else
        {
            this.die();
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return true;
    }

    public float Y()
    {
        return 1.0F;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean damageEntity(DamageSource par1DamageSource, int par2)
    {
        if (this.isInvulnerable())
        {
            return false;
        }
        else
        {
            this.K();

            if (par1DamageSource.getEntity() != null)
            {
                Vec3D var3 = par1DamageSource.getEntity().Z();

                if (var3 != null)
                {
                    this.motX = var3.c;
                    this.motY = var3.d;
                    this.motZ = var3.e;
                    this.dirX = this.motX * 0.1D;
                    this.dirY = this.motY * 0.1D;
                    this.dirZ = this.motZ * 0.1D;
                }

                if (par1DamageSource.getEntity() instanceof EntityLiving)
                {
                    this.shooter = (EntityLiving)par1DamageSource.getEntity();
                }

                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Gets how bright this entity is.
     */
    public float c(float par1)
    {
        return 1.0F;
    }
}
