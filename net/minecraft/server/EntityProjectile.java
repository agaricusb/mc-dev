package net.minecraft.server;

import java.util.List;

public abstract class EntityProjectile extends Entity implements IProjectile
{
    private int blockX = -1;
    private int blockY = -1;
    private int blockZ = -1;
    private int inBlockId = 0;
    protected boolean inGround = false;
    public int shake = 0;

    /** The entity that threw this throwable item. */
    private EntityLiving shooter;
    private String shooterName = null;
    private int i;
    private int j = 0;

    public EntityProjectile(World par1World)
    {
        super(par1World);
        this.a(0.25F, 0.25F);
    }

    protected void a() {}

    public EntityProjectile(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World);
        this.shooter = par2EntityLiving;
        this.a(0.25F, 0.25F);
        this.setPositionRotation(par2EntityLiving.locX, par2EntityLiving.locY + (double) par2EntityLiving.getHeadHeight(), par2EntityLiving.locZ, par2EntityLiving.yaw, par2EntityLiving.pitch);
        this.locX -= (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.locY -= 0.10000000149011612D;
        this.locZ -= (double)(MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0F;
        float var3 = 0.4F;
        this.motX = (double)(-MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * var3);
        this.motZ = (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * var3);
        this.motY = (double)(-MathHelper.sin((this.pitch + this.d()) / 180.0F * (float) Math.PI) * var3);
        this.shoot(this.motX, this.motY, this.motZ, this.c(), 1.0F);
    }

    public EntityProjectile(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        this.i = 0;
        this.a(0.25F, 0.25F);
        this.setPosition(par2, par4, par6);
        this.height = 0.0F;
    }

    protected float c()
    {
        return 1.5F;
    }

    protected float d()
    {
        return 0.0F;
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
        this.i = 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        this.T = this.locX;
        this.U = this.locY;
        this.V = this.locZ;
        super.j_();

        if (this.shake > 0)
        {
            --this.shake;
        }

        if (this.inGround)
        {
            int var1 = this.world.getTypeId(this.blockX, this.blockY, this.blockZ);

            if (var1 == this.inBlockId)
            {
                ++this.i;

                if (this.i == 1200)
                {
                    this.die();
                }

                return;
            }

            this.inGround = false;
            this.motX *= (double)(this.random.nextFloat() * 0.2F);
            this.motY *= (double)(this.random.nextFloat() * 0.2F);
            this.motZ *= (double)(this.random.nextFloat() * 0.2F);
            this.i = 0;
            this.j = 0;
        }
        else
        {
            ++this.j;
        }

        Vec3D var16 = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
        Vec3D var2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
        MovingObjectPosition var3 = this.world.a(var16, var2);
        var16 = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
        var2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);

        if (var3 != null)
        {
            var2 = this.world.getVec3DPool().create(var3.pos.c, var3.pos.d, var3.pos.e);
        }

        if (!this.world.isStatic)
        {
            Entity var4 = null;
            List var5 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
            double var6 = 0.0D;
            EntityLiving var8 = this.getShooter();

            for (int var9 = 0; var9 < var5.size(); ++var9)
            {
                Entity var10 = (Entity)var5.get(var9);

                if (var10.L() && (var10 != var8 || this.j >= 5))
                {
                    float var11 = 0.3F;
                    AxisAlignedBB var12 = var10.boundingBox.grow((double) var11, (double) var11, (double) var11);
                    MovingObjectPosition var13 = var12.a(var16, var2);

                    if (var13 != null)
                    {
                        double var14 = var16.d(var13.pos);

                        if (var14 < var6 || var6 == 0.0D)
                        {
                            var4 = var10;
                            var6 = var14;
                        }
                    }
                }
            }

            if (var4 != null)
            {
                var3 = new MovingObjectPosition(var4);
            }
        }

        if (var3 != null)
        {
            if (var3.type == EnumMovingObjectType.TILE && this.world.getTypeId(var3.b, var3.c, var3.d) == Block.PORTAL.id)
            {
                this.aa();
            }
            else
            {
                this.a(var3);
            }
        }

        this.locX += this.motX;
        this.locY += this.motY;
        this.locZ += this.motZ;
        float var17 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / Math.PI);

        for (this.pitch = (float)(Math.atan2(this.motY, (double)var17) * 180.0D / Math.PI); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F)
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
        float var18 = 0.99F;
        float var19 = this.g();

        if (this.H())
        {
            for (int var7 = 0; var7 < 4; ++var7)
            {
                float var20 = 0.25F;
                this.world.addParticle("bubble", this.locX - this.motX * (double) var20, this.locY - this.motY * (double) var20, this.locZ - this.motZ * (double) var20, this.motX, this.motY, this.motZ);
            }

            var18 = 0.8F;
        }

        this.motX *= (double)var18;
        this.motY *= (double)var18;
        this.motZ *= (double)var18;
        this.motY -= (double)var19;
        this.setPosition(this.locX, this.locY, this.locZ);
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float g()
    {
        return 0.03F;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected abstract void a(MovingObjectPosition var1);

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("xTile", (short) this.blockX);
        par1NBTTagCompound.setShort("yTile", (short) this.blockY);
        par1NBTTagCompound.setShort("zTile", (short) this.blockZ);
        par1NBTTagCompound.setByte("inTile", (byte) this.inBlockId);
        par1NBTTagCompound.setByte("shake", (byte) this.shake);
        par1NBTTagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));

        if ((this.shooterName == null || this.shooterName.length() == 0) && this.shooter != null && this.shooter instanceof EntityHuman)
        {
            this.shooterName = this.shooter.getLocalizedName();
        }

        par1NBTTagCompound.setString("ownerName", this.shooterName == null ? "" : this.shooterName);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.blockX = par1NBTTagCompound.getShort("xTile");
        this.blockY = par1NBTTagCompound.getShort("yTile");
        this.blockZ = par1NBTTagCompound.getShort("zTile");
        this.inBlockId = par1NBTTagCompound.getByte("inTile") & 255;
        this.shake = par1NBTTagCompound.getByte("shake") & 255;
        this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
        this.shooterName = par1NBTTagCompound.getString("ownerName");

        if (this.shooterName != null && this.shooterName.length() == 0)
        {
            this.shooterName = null;
        }
    }

    public EntityLiving getShooter()
    {
        if (this.shooter == null && this.shooterName != null && this.shooterName.length() > 0)
        {
            this.shooter = this.world.a(this.shooterName);
        }

        return this.shooter;
    }
}
