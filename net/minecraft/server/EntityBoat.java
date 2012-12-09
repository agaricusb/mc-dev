package net.minecraft.server;

import java.util.List;

public class EntityBoat extends Entity
{
    private boolean a;
    private double b;
    private int c;
    private double d;
    private double e;
    private double f;
    private double g;
    private double h;

    public EntityBoat(World par1World)
    {
        super(par1World);
        this.a = true;
        this.b = 0.07D;
        this.m = true;
        this.a(1.5F, 0.6F);
        this.height = this.length / 2.0F;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    protected void a()
    {
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(1));
        this.datawatcher.a(19, new Integer(0));
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB g(Entity par1Entity)
    {
        return par1Entity.boundingBox;
    }

    /**
     * returns the bounding box for this entity
     */
    public AxisAlignedBB E()
    {
        return this.boundingBox;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean M()
    {
        return true;
    }

    public EntityBoat(World par1World, double par2, double par4, double par6)
    {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.height, par6);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = par2;
        this.lastY = par4;
        this.lastZ = par6;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double X()
    {
        return (double)this.length * 0.0D - 0.30000001192092896D;
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
        else if (!this.world.isStatic && !this.dead)
        {
            this.h(-this.h());
            this.g(10);
            this.setDamage(this.getDamage() + par2 * 10);
            this.K();

            if (par1DamageSource.getEntity() instanceof EntityHuman && ((EntityHuman)par1DamageSource.getEntity()).abilities.canInstantlyBuild)
            {
                this.setDamage(100);
            }

            if (this.getDamage() > 40)
            {
                if (this.passenger != null)
                {
                    this.passenger.mount(this);
                }

                this.a(Item.BOAT.id, 1, 0.0F);
                this.die();
            }

            return true;
        }
        else
        {
            return true;
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return !this.dead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();

        if (this.g() > 0)
        {
            this.g(this.g() - 1);
        }

        if (this.getDamage() > 0)
        {
            this.setDamage(this.getDamage() - 1);
        }

        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        byte var1 = 5;
        double var2 = 0.0D;

        for (int var4 = 0; var4 < var1; ++var4)
        {
            double var5 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (double)(var4 + 0) / (double)var1 - 0.125D;
            double var7 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (double)(var4 + 1) / (double)var1 - 0.125D;
            AxisAlignedBB var9 = AxisAlignedBB.a().a(this.boundingBox.a, var5, this.boundingBox.c, this.boundingBox.d, var7, this.boundingBox.f);

            if (this.world.b(var9, Material.WATER))
            {
                var2 += 1.0D / (double)var1;
            }
        }

        double var24 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        double var6;
        double var8;

        if (var24 > 0.26249999999999996D)
        {
            var6 = Math.cos((double)this.yaw * Math.PI / 180.0D);
            var8 = Math.sin((double)this.yaw * Math.PI / 180.0D);

            for (int var10 = 0; (double)var10 < 1.0D + var24 * 60.0D; ++var10)
            {
                double var11 = (double)(this.random.nextFloat() * 2.0F - 1.0F);
                double var13 = (double)(this.random.nextInt(2) * 2 - 1) * 0.7D;
                double var15;
                double var17;

                if (this.random.nextBoolean())
                {
                    var15 = this.locX - var6 * var11 * 0.8D + var8 * var13;
                    var17 = this.locZ - var8 * var11 * 0.8D - var6 * var13;
                    this.world.addParticle("splash", var15, this.locY - 0.125D, var17, this.motX, this.motY, this.motZ);
                }
                else
                {
                    var15 = this.locX + var6 + var8 * var11 * 0.7D;
                    var17 = this.locZ + var8 - var6 * var11 * 0.7D;
                    this.world.addParticle("splash", var15, this.locY - 0.125D, var17, this.motX, this.motY, this.motZ);
                }
            }
        }

        double var12;
        double var26;

        if (this.world.isStatic && this.a)
        {
            if (this.c > 0)
            {
                var6 = this.locX + (this.d - this.locX) / (double)this.c;
                var8 = this.locY + (this.e - this.locY) / (double)this.c;
                var26 = this.locZ + (this.f - this.locZ) / (double)this.c;
                var12 = MathHelper.g(this.g - (double) this.yaw);
                this.yaw = (float)((double)this.yaw + var12 / (double)this.c);
                this.pitch = (float)((double)this.pitch + (this.h - (double)this.pitch) / (double)this.c);
                --this.c;
                this.setPosition(var6, var8, var26);
                this.b(this.yaw, this.pitch);
            }
            else
            {
                var6 = this.locX + this.motX;
                var8 = this.locY + this.motY;
                var26 = this.locZ + this.motZ;
                this.setPosition(var6, var8, var26);

                if (this.onGround)
                {
                    this.motX *= 0.5D;
                    this.motY *= 0.5D;
                    this.motZ *= 0.5D;
                }

                this.motX *= 0.9900000095367432D;
                this.motY *= 0.949999988079071D;
                this.motZ *= 0.9900000095367432D;
            }
        }
        else
        {
            if (var2 < 1.0D)
            {
                var6 = var2 * 2.0D - 1.0D;
                this.motY += 0.03999999910593033D * var6;
            }
            else
            {
                if (this.motY < 0.0D)
                {
                    this.motY /= 2.0D;
                }

                this.motY += 0.007000000216066837D;
            }

            if (this.passenger != null)
            {
                this.motX += this.passenger.motX * this.b;
                this.motZ += this.passenger.motZ * this.b;
            }

            var6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);

            if (var6 > 0.35D)
            {
                var8 = 0.35D / var6;
                this.motX *= var8;
                this.motZ *= var8;
                var6 = 0.35D;
            }

            if (var6 > var24 && this.b < 0.35D)
            {
                this.b += (0.35D - this.b) / 35.0D;

                if (this.b > 0.35D)
                {
                    this.b = 0.35D;
                }
            }
            else
            {
                this.b -= (this.b - 0.07D) / 35.0D;

                if (this.b < 0.07D)
                {
                    this.b = 0.07D;
                }
            }

            if (this.onGround)
            {
                this.motX *= 0.5D;
                this.motY *= 0.5D;
                this.motZ *= 0.5D;
            }

            this.move(this.motX, this.motY, this.motZ);

            if (this.positionChanged && var24 > 0.2D)
            {
                if (!this.world.isStatic)
                {
                    this.die();
                    int var25;

                    for (var25 = 0; var25 < 3; ++var25)
                    {
                        this.a(Block.WOOD.id, 1, 0.0F);
                    }

                    for (var25 = 0; var25 < 2; ++var25)
                    {
                        this.a(Item.STICK.id, 1, 0.0F);
                    }
                }
            }
            else
            {
                this.motX *= 0.9900000095367432D;
                this.motY *= 0.949999988079071D;
                this.motZ *= 0.9900000095367432D;
            }

            this.pitch = 0.0F;
            var8 = (double)this.yaw;
            var26 = this.lastX - this.locX;
            var12 = this.lastZ - this.locZ;

            if (var26 * var26 + var12 * var12 > 0.001D)
            {
                var8 = (double)((float)(Math.atan2(var12, var26) * 180.0D / Math.PI));
            }

            double var14 = MathHelper.g(var8 - (double) this.yaw);

            if (var14 > 20.0D)
            {
                var14 = 20.0D;
            }

            if (var14 < -20.0D)
            {
                var14 = -20.0D;
            }

            this.yaw = (float)((double)this.yaw + var14);
            this.b(this.yaw, this.pitch);

            if (!this.world.isStatic)
            {
                List var16 = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));
                int var27;

                if (var16 != null && !var16.isEmpty())
                {
                    for (var27 = 0; var27 < var16.size(); ++var27)
                    {
                        Entity var18 = (Entity)var16.get(var27);

                        if (var18 != this.passenger && var18.M() && var18 instanceof EntityBoat)
                        {
                            var18.collide(this);
                        }
                    }
                }

                for (var27 = 0; var27 < 4; ++var27)
                {
                    int var28 = MathHelper.floor(this.locX + ((double) (var27 % 2) - 0.5D) * 0.8D);
                    int var19 = MathHelper.floor(this.locZ + ((double) (var27 / 2) - 0.5D) * 0.8D);

                    for (int var20 = 0; var20 < 2; ++var20)
                    {
                        int var21 = MathHelper.floor(this.locY) + var20;
                        int var22 = this.world.getTypeId(var28, var21, var19);
                        int var23 = this.world.getData(var28, var21, var19);

                        if (var22 == Block.SNOW.id)
                        {
                            this.world.setTypeId(var28, var21, var19, 0);
                        }
                        else if (var22 == Block.WATER_LILY.id)
                        {
                            Block.WATER_LILY.dropNaturally(this.world, var28, var21, var19, var23, 0.3F, 0);
                            this.world.setTypeId(var28, var21, var19, 0);
                        }
                    }
                }

                if (this.passenger != null && this.passenger.dead)
                {
                    this.passenger = null;
                }
            }
        }
    }

    public void V()
    {
        if (this.passenger != null)
        {
            double var1 = Math.cos((double)this.yaw * Math.PI / 180.0D) * 0.4D;
            double var3 = Math.sin((double)this.yaw * Math.PI / 180.0D) * 0.4D;
            this.passenger.setPosition(this.locX + var1, this.locY + this.X() + this.passenger.W(), this.locZ + var3);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void b(NBTTagCompound par1NBTTagCompound) {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void a(NBTTagCompound par1NBTTagCompound) {}

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != par1EntityPlayer)
        {
            return true;
        }
        else
        {
            if (!this.world.isStatic)
            {
                par1EntityPlayer.mount(this);
            }

            return true;
        }
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamage(int par1)
    {
        this.datawatcher.watch(19, Integer.valueOf(par1));
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public int getDamage()
    {
        return this.datawatcher.getInt(19);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void g(int par1)
    {
        this.datawatcher.watch(17, Integer.valueOf(par1));
    }

    /**
     * Gets the time since the last hit.
     */
    public int g()
    {
        return this.datawatcher.getInt(17);
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void h(int par1)
    {
        this.datawatcher.watch(18, Integer.valueOf(par1));
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int h()
    {
        return this.datawatcher.getInt(18);
    }
}
