package net.minecraft.server;

import java.util.List;

public class EntityFishingHook extends Entity
{
    /** The tile this entity is on, X position */
    private int d = -1;

    /** The tile this entity is on, Y position */
    private int e = -1;

    /** The tile this entity is on, Z position */
    private int f = -1;
    private int g = 0;
    private boolean h = false;
    public int a = 0;
    public EntityHuman owner;
    private int i;
    private int j = 0;

    /** the number of ticks remaining until this fish can no longer be caught */
    private int at = 0;

    /** the bobber that the fish hit */
    public Entity hooked = null;
    private int au;
    private double av;
    private double aw;
    private double ax;
    private double ay;
    private double az;

    public EntityFishingHook(World par1World)
    {
        super(par1World);
        this.a(0.25F, 0.25F);
        this.al = true;
    }

    public EntityFishingHook(World par1World, EntityHuman par2EntityPlayer)
    {
        super(par1World);
        this.al = true;
        this.owner = par2EntityPlayer;
        this.owner.hookedFish = this;
        this.a(0.25F, 0.25F);
        this.setPositionRotation(par2EntityPlayer.locX, par2EntityPlayer.locY + 1.62D - (double) par2EntityPlayer.height, par2EntityPlayer.locZ, par2EntityPlayer.yaw, par2EntityPlayer.pitch);
        this.locX -= (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.locY -= 0.10000000149011612D;
        this.locZ -= (double)(MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * 0.16F);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0F;
        float var3 = 0.4F;
        this.motX = (double)(-MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * var3);
        this.motZ = (double)(MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI) * var3);
        this.motY = (double)(-MathHelper.sin(this.pitch / 180.0F * (float) Math.PI) * var3);
        this.c(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
    }

    protected void a() {}

    public void c(double par1, double par3, double par5, float par7, float par8)
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
        super.j_();

        if (this.au > 0)
        {
            double var21 = this.locX + (this.av - this.locX) / (double)this.au;
            double var22 = this.locY + (this.aw - this.locY) / (double)this.au;
            double var23 = this.locZ + (this.ax - this.locZ) / (double)this.au;
            double var7 = MathHelper.g(this.ay - (double) this.yaw);
            this.yaw = (float)((double)this.yaw + var7 / (double)this.au);
            this.pitch = (float)((double)this.pitch + (this.az - (double)this.pitch) / (double)this.au);
            --this.au;
            this.setPosition(var21, var22, var23);
            this.b(this.yaw, this.pitch);
        }
        else
        {
            if (!this.world.isStatic)
            {
                ItemStack var1 = this.owner.bS();

                if (this.owner.dead || !this.owner.isAlive() || var1 == null || var1.getItem() != Item.FISHING_ROD || this.e(this.owner) > 1024.0D)
                {
                    this.die();
                    this.owner.hookedFish = null;
                    return;
                }

                if (this.hooked != null)
                {
                    if (!this.hooked.dead)
                    {
                        this.locX = this.hooked.locX;
                        this.locY = this.hooked.boundingBox.b + (double)this.hooked.length * 0.8D;
                        this.locZ = this.hooked.locZ;
                        return;
                    }

                    this.hooked = null;
                }
            }

            if (this.a > 0)
            {
                --this.a;
            }

            if (this.h)
            {
                int var19 = this.world.getTypeId(this.d, this.e, this.f);

                if (var19 == this.g)
                {
                    ++this.i;

                    if (this.i == 1200)
                    {
                        this.die();
                    }

                    return;
                }

                this.h = false;
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

            Vec3D var20 = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
            Vec3D var2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            MovingObjectPosition var3 = this.world.a(var20, var2);
            var20 = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
            var2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);

            if (var3 != null)
            {
                var2 = this.world.getVec3DPool().create(var3.pos.c, var3.pos.d, var3.pos.e);
            }

            Entity var4 = null;
            List var5 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
            double var6 = 0.0D;
            double var13;

            for (int var8 = 0; var8 < var5.size(); ++var8)
            {
                Entity var9 = (Entity)var5.get(var8);

                if (var9.L() && (var9 != this.owner || this.j >= 5))
                {
                    float var10 = 0.3F;
                    AxisAlignedBB var11 = var9.boundingBox.grow((double) var10, (double) var10, (double) var10);
                    MovingObjectPosition var12 = var11.a(var20, var2);

                    if (var12 != null)
                    {
                        var13 = var20.d(var12.pos);

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
                if (var3.entity != null)
                {
                    if (var3.entity.damageEntity(DamageSource.projectile(this, this.owner), 0))
                    {
                        this.hooked = var3.entity;
                    }
                }
                else
                {
                    this.h = true;
                }
            }

            if (!this.h)
            {
                this.move(this.motX, this.motY, this.motZ);
                float var24 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / Math.PI);

                for (this.pitch = (float)(Math.atan2(this.motY, (double)var24) * 180.0D / Math.PI); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F)
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
                float var25 = 0.92F;

                if (this.onGround || this.positionChanged)
                {
                    var25 = 0.5F;
                }

                byte var27 = 5;
                double var26 = 0.0D;

                for (int var29 = 0; var29 < var27; ++var29)
                {
                    double var14 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (double)(var29 + 0) / (double)var27 - 0.125D + 0.125D;
                    double var16 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (double)(var29 + 1) / (double)var27 - 0.125D + 0.125D;
                    AxisAlignedBB var18 = AxisAlignedBB.a().a(this.boundingBox.a, var14, this.boundingBox.c, this.boundingBox.d, var16, this.boundingBox.f);

                    if (this.world.b(var18, Material.WATER))
                    {
                        var26 += 1.0D / (double)var27;
                    }
                }

                if (var26 > 0.0D)
                {
                    if (this.at > 0)
                    {
                        --this.at;
                    }
                    else
                    {
                        short var28 = 500;

                        if (this.world.D(MathHelper.floor(this.locX), MathHelper.floor(this.locY) + 1, MathHelper.floor(this.locZ)))
                        {
                            var28 = 300;
                        }

                        if (this.random.nextInt(var28) == 0)
                        {
                            this.at = this.random.nextInt(30) + 10;
                            this.motY -= 0.20000000298023224D;
                            this.makeSound("random.splash", 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                            float var30 = (float) MathHelper.floor(this.boundingBox.b);
                            int var15;
                            float var17;
                            float var31;

                            for (var15 = 0; (float)var15 < 1.0F + this.width * 20.0F; ++var15)
                            {
                                var31 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
                                var17 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
                                this.world.addParticle("bubble", this.locX + (double) var31, (double) (var30 + 1.0F), this.locZ + (double) var17, this.motX, this.motY - (double) (this.random.nextFloat() * 0.2F), this.motZ);
                            }

                            for (var15 = 0; (float)var15 < 1.0F + this.width * 20.0F; ++var15)
                            {
                                var31 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
                                var17 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
                                this.world.addParticle("splash", this.locX + (double) var31, (double) (var30 + 1.0F), this.locZ + (double) var17, this.motX, this.motY, this.motZ);
                            }
                        }
                    }
                }

                if (this.at > 0)
                {
                    this.motY -= (double)(this.random.nextFloat() * this.random.nextFloat() * this.random.nextFloat()) * 0.2D;
                }

                var13 = var26 * 2.0D - 1.0D;
                this.motY += 0.03999999910593033D * var13;

                if (var26 > 0.0D)
                {
                    var25 = (float)((double)var25 * 0.9D);
                    this.motY *= 0.8D;
                }

                this.motX *= (double)var25;
                this.motY *= (double)var25;
                this.motZ *= (double)var25;
                this.setPosition(this.locX, this.locY, this.locZ);
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("xTile", (short) this.d);
        par1NBTTagCompound.setShort("yTile", (short) this.e);
        par1NBTTagCompound.setShort("zTile", (short) this.f);
        par1NBTTagCompound.setByte("inTile", (byte) this.g);
        par1NBTTagCompound.setByte("shake", (byte) this.a);
        par1NBTTagCompound.setByte("inGround", (byte) (this.h ? 1 : 0));
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
        this.a = par1NBTTagCompound.getByte("shake") & 255;
        this.h = par1NBTTagCompound.getByte("inGround") == 1;
    }

    public int c()
    {
        if (this.world.isStatic)
        {
            return 0;
        }
        else
        {
            byte var1 = 0;

            if (this.hooked != null)
            {
                double var2 = this.owner.locX - this.locX;
                double var4 = this.owner.locY - this.locY;
                double var6 = this.owner.locZ - this.locZ;
                double var8 = (double) MathHelper.sqrt(var2 * var2 + var4 * var4 + var6 * var6);
                double var10 = 0.1D;
                this.hooked.motX += var2 * var10;
                this.hooked.motY += var4 * var10 + (double) MathHelper.sqrt(var8) * 0.08D;
                this.hooked.motZ += var6 * var10;
                var1 = 3;
            }
            else if (this.at > 0)
            {
                EntityItem var13 = new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.RAW_FISH));
                double var3 = this.owner.locX - this.locX;
                double var5 = this.owner.locY - this.locY;
                double var7 = this.owner.locZ - this.locZ;
                double var9 = (double) MathHelper.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
                double var11 = 0.1D;
                var13.motX = var3 * var11;
                var13.motY = var5 * var11 + (double) MathHelper.sqrt(var9) * 0.08D;
                var13.motZ = var7 * var11;
                this.world.addEntity(var13);
                this.owner.a(StatisticList.B, 1);
                this.owner.world.addEntity(new EntityExperienceOrb(this.owner.world, this.owner.locX, this.owner.locY + 0.5D, this.owner.locZ + 0.5D, this.random.nextInt(6) + 1));
                var1 = 1;
            }

            if (this.h)
            {
                var1 = 2;
            }

            this.die();
            this.owner.hookedFish = null;
            return var1;
        }
    }

    /**
     * Will get destroyed next tick.
     */
    public void die()
    {
        super.die();

        if (this.owner != null)
        {
            this.owner.hookedFish = null;
        }
    }
}
