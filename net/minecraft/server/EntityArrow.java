package net.minecraft.server;

import java.util.List;

public class EntityArrow extends Entity implements IProjectile
{
    private int d = -1;
    private int e = -1;
    private int f = -1;
    private int g = 0;
    private int h = 0;
    private boolean inGround = false;

    /** 1 if the player can pick up the arrow */
    public int fromPlayer = 0;

    /** Seems to be some sort of timer for animating an arrow. */
    public int shake = 0;

    /** The owner of this arrow. */
    public Entity shooter;
    private int j;
    private int as = 0;
    private double damage = 2.0D;

    /** The amount of knockback an arrow applies when it hits a mob. */
    private int au;

    public EntityArrow(World par1World)
    {
        super(par1World);
        this.a(0.5F, 0.5F);
    }

    public EntityArrow(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        this.a(0.5F, 0.5F);
        this.setPosition(par2, par4, par6);
        this.height = 0.0F;
    }

    public EntityArrow(World par1World, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving, float par4, float par5)
    {
        super(par1World);
        this.shooter = par2EntityLiving;

        if (par2EntityLiving instanceof EntityHuman)
        {
            this.fromPlayer = 1;
        }

        this.locY = par2EntityLiving.locY + (double)par2EntityLiving.getHeadHeight() - 0.10000000149011612D;
        double var6 = par3EntityLiving.locX - par2EntityLiving.locX;
        double var8 = par3EntityLiving.locY + (double)par3EntityLiving.getHeadHeight() - 0.699999988079071D - this.locY;
        double var10 = par3EntityLiving.locZ - par2EntityLiving.locZ;
        double var12 = (double) MathHelper.sqrt(var6 * var6 + var10 * var10);

        if (var12 >= 1.0E-7D)
        {
            float var14 = (float)(Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
            float var15 = (float)(-(Math.atan2(var8, var12) * 180.0D / Math.PI));
            double var16 = var6 / var12;
            double var18 = var10 / var12;
            this.setPositionRotation(par2EntityLiving.locX + var16, this.locY, par2EntityLiving.locZ + var18, var14, var15);
            this.height = 0.0F;
            float var20 = (float)var12 * 0.2F;
            this.shoot(var6, var8 + (double) var20, var10, par4, par5);
        }
    }

    public EntityArrow(World par1World, EntityLiving par2EntityLiving, float par3)
    {
        super(par1World);
        this.shooter = par2EntityLiving;

        if (par2EntityLiving instanceof EntityHuman)
        {
            this.fromPlayer = 1;
        }

        this.a(0.5F, 0.5F);
        this.setPositionRotation(par2EntityLiving.locX, par2EntityLiving.locY + (double) par2EntityLiving.getHeadHeight(), par2EntityLiving.locZ, par2EntityLiving.yaw, par2EntityLiving.pitch);
        this.locX -= (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.locY -= 0.10000000149011612D;
        this.locZ -= (double)(MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0F;
        this.motX = (double)(-MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI));
        this.motZ = (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI));
        this.motY = (double)(-MathHelper.sin(this.pitch / 180.0F * (float) Math.PI));
        this.shoot(this.motX, this.motY, this.motZ, par3 * 1.5F, 1.0F);
    }

    protected void a()
    {
        this.datawatcher.a(16, Byte.valueOf((byte) 0));
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shoot(double par1, double par3, double par5, float par7, float par8)
    {
        float var9 = MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)var9;
        par3 /= (double)var9;
        par5 /= (double)var9;
        par1 += this.random.nextGaussian() * 0.007499999832361937D * (double)par8;
        par3 += this.random.nextGaussian() * 0.007499999832361937D * (double)par8;
        par5 += this.random.nextGaussian() * 0.007499999832361937D * (double)par8;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        this.motX = par1;
        this.motY = par3;
        this.motZ = par5;
        float var10 = MathHelper.sqrt(par1 * par1 + par5 * par5);
        this.lastYaw = this.yaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        this.lastPitch = this.pitch = (float)(Math.atan2(par3, (double)var10) * 180.0D / Math.PI);
        this.j = 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();

        if (this.lastPitch == 0.0F && this.lastYaw == 0.0F)
        {
            float var1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.lastYaw = this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / Math.PI);
            this.lastPitch = this.pitch = (float)(Math.atan2(this.motY, (double)var1) * 180.0D / Math.PI);
        }

        int var16 = this.world.getTypeId(this.d, this.e, this.f);

        if (var16 > 0)
        {
            Block.byId[var16].updateShape(this.world, this.d, this.e, this.f);
            AxisAlignedBB var2 = Block.byId[var16].e(this.world, this.d, this.e, this.f);

            if (var2 != null && var2.a(this.world.getVec3DPool().create(this.locX, this.locY, this.locZ)))
            {
                this.inGround = true;
            }
        }

        if (this.shake > 0)
        {
            --this.shake;
        }

        if (this.inGround)
        {
            int var18 = this.world.getTypeId(this.d, this.e, this.f);
            int var19 = this.world.getData(this.d, this.e, this.f);

            if (var18 == this.g && var19 == this.h)
            {
                ++this.j;

                if (this.j == 1200)
                {
                    this.die();
                }
            }
            else
            {
                this.inGround = false;
                this.motX *= (double)(this.random.nextFloat() * 0.2F);
                this.motY *= (double)(this.random.nextFloat() * 0.2F);
                this.motZ *= (double)(this.random.nextFloat() * 0.2F);
                this.j = 0;
                this.as = 0;
            }
        }
        else
        {
            ++this.as;
            Vec3D var17 = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
            Vec3D var3 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            MovingObjectPosition var4 = this.world.rayTrace(var17, var3, false, true);
            var17 = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
            var3 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);

            if (var4 != null)
            {
                var3 = this.world.getVec3DPool().create(var4.pos.c, var4.pos.d, var4.pos.e);
            }

            Entity var5 = null;
            List var6 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
            double var7 = 0.0D;
            int var9;
            float var11;

            for (var9 = 0; var9 < var6.size(); ++var9)
            {
                Entity var10 = (Entity)var6.get(var9);

                if (var10.L() && (var10 != this.shooter || this.as >= 5))
                {
                    var11 = 0.3F;
                    AxisAlignedBB var12 = var10.boundingBox.grow((double) var11, (double) var11, (double) var11);
                    MovingObjectPosition var13 = var12.a(var17, var3);

                    if (var13 != null)
                    {
                        double var14 = var17.d(var13.pos);

                        if (var14 < var7 || var7 == 0.0D)
                        {
                            var5 = var10;
                            var7 = var14;
                        }
                    }
                }
            }

            if (var5 != null)
            {
                var4 = new MovingObjectPosition(var5);
            }

            float var20;

            if (var4 != null)
            {
                if (var4.entity != null)
                {
                    var20 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
                    int var23 = MathHelper.f((double) var20 * this.damage);

                    if (this.d())
                    {
                        var23 += this.random.nextInt(var23 / 2 + 2);
                    }

                    DamageSource var21 = null;

                    if (this.shooter == null)
                    {
                        var21 = DamageSource.arrow(this, this);
                    }
                    else
                    {
                        var21 = DamageSource.arrow(this, this.shooter);
                    }

                    if (this.isBurning())
                    {
                        var4.entity.setOnFire(5);
                    }

                    if (var4.entity.damageEntity(var21, var23))
                    {
                        if (var4.entity instanceof EntityLiving)
                        {
                            if (!this.world.isStatic)
                            {
                                EntityLiving var24 = (EntityLiving)var4.entity;
                                var24.r(var24.bJ() + 1);
                            }

                            if (this.au > 0)
                            {
                                float var25 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);

                                if (var25 > 0.0F)
                                {
                                    var4.entity.g(this.motX * (double) this.au * 0.6000000238418579D / (double) var25, 0.1D, this.motZ * (double) this.au * 0.6000000238418579D / (double) var25);
                                }
                            }
                        }

                        this.makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                        this.die();
                    }
                    else
                    {
                        this.motX *= -0.10000000149011612D;
                        this.motY *= -0.10000000149011612D;
                        this.motZ *= -0.10000000149011612D;
                        this.yaw += 180.0F;
                        this.lastYaw += 180.0F;
                        this.as = 0;
                    }
                }
                else
                {
                    this.d = var4.b;
                    this.e = var4.c;
                    this.f = var4.d;
                    this.g = this.world.getTypeId(this.d, this.e, this.f);
                    this.h = this.world.getData(this.d, this.e, this.f);
                    this.motX = (double)((float)(var4.pos.c - this.locX));
                    this.motY = (double)((float)(var4.pos.d - this.locY));
                    this.motZ = (double)((float)(var4.pos.e - this.locZ));
                    var20 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
                    this.locX -= this.motX / (double)var20 * 0.05000000074505806D;
                    this.locY -= this.motY / (double)var20 * 0.05000000074505806D;
                    this.locZ -= this.motZ / (double)var20 * 0.05000000074505806D;
                    this.makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                    this.inGround = true;
                    this.shake = 7;
                    this.e(false);

                    if (this.g != 0)
                    {
                        Block.byId[this.g].a(this.world, this.d, this.e, this.f, this);
                    }
                }
            }

            if (this.d())
            {
                for (var9 = 0; var9 < 4; ++var9)
                {
                    this.world.addParticle("crit", this.locX + this.motX * (double) var9 / 4.0D, this.locY + this.motY * (double) var9 / 4.0D, this.locZ + this.motZ * (double) var9 / 4.0D, -this.motX, -this.motY + 0.2D, -this.motZ);
                }
            }

            this.locX += this.motX;
            this.locY += this.motY;
            this.locZ += this.motZ;
            var20 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / Math.PI);

            for (this.pitch = (float)(Math.atan2(this.motY, (double)var20) * 180.0D / Math.PI); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F)
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
            float var22 = 0.99F;
            var11 = 0.05F;

            if (this.H())
            {
                for (int var26 = 0; var26 < 4; ++var26)
                {
                    float var27 = 0.25F;
                    this.world.addParticle("bubble", this.locX - this.motX * (double) var27, this.locY - this.motY * (double) var27, this.locZ - this.motZ * (double) var27, this.motX, this.motY, this.motZ);
                }

                var22 = 0.8F;
            }

            this.motX *= (double)var22;
            this.motY *= (double)var22;
            this.motZ *= (double)var22;
            this.motY -= (double)var11;
            this.setPosition(this.locX, this.locY, this.locZ);
            this.D();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("xTile", (short)this.d);
        par1NBTTagCompound.setShort("yTile", (short)this.e);
        par1NBTTagCompound.setShort("zTile", (short)this.f);
        par1NBTTagCompound.setByte("inTile", (byte)this.g);
        par1NBTTagCompound.setByte("inData", (byte)this.h);
        par1NBTTagCompound.setByte("shake", (byte)this.shake);
        par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        par1NBTTagCompound.setByte("pickup", (byte)this.fromPlayer);
        par1NBTTagCompound.setDouble("damage", this.damage);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.d = par1NBTTagCompound.getShort("xTile");
        this.e = par1NBTTagCompound.getShort("yTile");
        this.f = par1NBTTagCompound.getShort("zTile");
        this.g = par1NBTTagCompound.getByte("inTile") & 255;
        this.h = par1NBTTagCompound.getByte("inData") & 255;
        this.shake = par1NBTTagCompound.getByte("shake") & 255;
        this.inGround = par1NBTTagCompound.getByte("inGround") == 1;

        if (par1NBTTagCompound.hasKey("damage"))
        {
            this.damage = par1NBTTagCompound.getDouble("damage");
        }

        if (par1NBTTagCompound.hasKey("pickup"))
        {
            this.fromPlayer = par1NBTTagCompound.getByte("pickup");
        }
        else if (par1NBTTagCompound.hasKey("player"))
        {
            this.fromPlayer = par1NBTTagCompound.getBoolean("player") ? 1 : 0;
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void c_(EntityHuman par1EntityPlayer)
    {
        if (!this.world.isStatic && this.inGround && this.shake <= 0)
        {
            boolean var2 = this.fromPlayer == 1 || this.fromPlayer == 2 && par1EntityPlayer.abilities.canInstantlyBuild;

            if (this.fromPlayer == 1 && !par1EntityPlayer.inventory.pickup(new ItemStack(Item.ARROW, 1)))
            {
                var2 = false;
            }

            if (var2)
            {
                this.makeSound("random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.receive(this, 1);
                this.die();
            }
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    public void b(double par1)
    {
        this.damage = par1;
    }

    public double c()
    {
        return this.damage;
    }

    /**
     * Sets the amount of knockback the arrow applies when it hits a mob.
     */
    public void a(int par1)
    {
        this.au = par1;
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean aq()
    {
        return false;
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public void e(boolean par1)
    {
        byte var2 = this.datawatcher.getByte(16);

        if (par1)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var2 | 1)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var2 & -2)));
        }
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public boolean d()
    {
        byte var1 = this.datawatcher.getByte(16);
        return (var1 & 1) != 0;
    }
}
