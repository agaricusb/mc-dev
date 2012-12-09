package net.minecraft.server;

public class EntityEnderman extends EntityMonster
{
    private static boolean[] d = new boolean[256];

    /**
     * Counter to delay the teleportation of an enderman towards the currently attacked target
     */
    private int e = 0;
    private int f = 0;

    public EntityEnderman(World par1World)
    {
        super(par1World);
        this.texture = "/mob/enderman.png";
        this.bG = 0.2F;
        this.a(0.6F, 2.9F);
        this.X = 1.0F;
    }

    public int getMaxHealth()
    {
        return 40;
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, new Byte((byte) 0));
        this.datawatcher.a(17, new Byte((byte) 0));
        this.datawatcher.a(18, new Byte((byte) 0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setShort("carried", (short)this.getCarriedId());
        par1NBTTagCompound.setShort("carriedData", (short)this.getCarriedData());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.setCarriedId(par1NBTTagCompound.getShort("carried"));
        this.setCarriedData(par1NBTTagCompound.getShort("carriedData"));
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findTarget()
    {
        EntityHuman var1 = this.world.findNearbyVulnerablePlayer(this, 64.0D);

        if (var1 != null)
        {
            if (this.d(var1))
            {
                if (this.f == 0)
                {
                    this.world.makeSound(var1, "mob.endermen.stare", 1.0F, 1.0F);
                }

                if (this.f++ == 5)
                {
                    this.f = 0;
                    this.f(true);
                    return var1;
                }
            }
            else
            {
                this.f = 0;
            }
        }

        return null;
    }

    /**
     * Checks to see if this enderman should be attacking this player
     */
    private boolean d(EntityHuman par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.armor[3];

        if (var2 != null && var2.id == Block.PUMPKIN.id)
        {
            return false;
        }
        else
        {
            Vec3D var3 = par1EntityPlayer.i(1.0F).a();
            Vec3D var4 = this.world.getVec3DPool().create(this.locX - par1EntityPlayer.locX, this.boundingBox.b + (double) (this.length / 2.0F) - (par1EntityPlayer.locY + (double) par1EntityPlayer.getHeadHeight()), this.locZ - par1EntityPlayer.locZ);
            double var5 = var4.b();
            var4 = var4.a();
            double var7 = var3.b(var4);
            return var7 > 1.0D - 0.025D / var5 ? par1EntityPlayer.n(this) : false;
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        if (this.G())
        {
            this.damageEntity(DamageSource.DROWN, 1);
        }

        this.bG = this.target != null ? 6.5F : 0.3F;
        int var1;

        if (!this.world.isStatic && this.world.getGameRules().getBoolean("mobGriefing"))
        {
            int var2;
            int var3;
            int var4;

            if (this.getCarriedId() == 0)
            {
                if (this.random.nextInt(20) == 0)
                {
                    var1 = MathHelper.floor(this.locX - 2.0D + this.random.nextDouble() * 4.0D);
                    var2 = MathHelper.floor(this.locY + this.random.nextDouble() * 3.0D);
                    var3 = MathHelper.floor(this.locZ - 2.0D + this.random.nextDouble() * 4.0D);
                    var4 = this.world.getTypeId(var1, var2, var3);

                    if (d[var4])
                    {
                        this.setCarriedId(this.world.getTypeId(var1, var2, var3));
                        this.setCarriedData(this.world.getData(var1, var2, var3));
                        this.world.setTypeId(var1, var2, var3, 0);
                    }
                }
            }
            else if (this.random.nextInt(2000) == 0)
            {
                var1 = MathHelper.floor(this.locX - 1.0D + this.random.nextDouble() * 2.0D);
                var2 = MathHelper.floor(this.locY + this.random.nextDouble() * 2.0D);
                var3 = MathHelper.floor(this.locZ - 1.0D + this.random.nextDouble() * 2.0D);
                var4 = this.world.getTypeId(var1, var2, var3);
                int var5 = this.world.getTypeId(var1, var2 - 1, var3);

                if (var4 == 0 && var5 > 0 && Block.byId[var5].b())
                {
                    this.world.setTypeIdAndData(var1, var2, var3, this.getCarriedId(), this.getCarriedData());
                    this.setCarriedId(0);
                }
            }
        }

        for (var1 = 0; var1 < 2; ++var1)
        {
            this.world.addParticle("portal", this.locX + (this.random.nextDouble() - 0.5D) * (double) this.width, this.locY + this.random.nextDouble() * (double) this.length - 0.25D, this.locZ + (this.random.nextDouble() - 0.5D) * (double) this.width, (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
        }

        if (this.world.u() && !this.world.isStatic)
        {
            float var6 = this.c(1.0F);

            if (var6 > 0.5F && this.world.k(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) && this.random.nextFloat() * 30.0F < (var6 - 0.4F) * 2.0F)
            {
                this.target = null;
                this.f(false);
                this.m();
            }
        }

        if (this.G())
        {
            this.target = null;
            this.f(false);
            this.m();
        }

        this.bE = false;

        if (this.target != null)
        {
            this.a(this.target, 100.0F, 100.0F);
        }

        if (!this.world.isStatic && this.isAlive())
        {
            if (this.target != null)
            {
                if (this.target instanceof EntityHuman && this.d((EntityHuman) this.target))
                {
                    this.bB = this.bC = 0.0F;
                    this.bG = 0.0F;

                    if (this.target.e(this) < 16.0D)
                    {
                        this.m();
                    }

                    this.e = 0;
                }
                else if (this.target.e(this) > 256.0D && this.e++ >= 30 && this.p(this.target))
                {
                    this.e = 0;
                }
            }
            else
            {
                this.f(false);
                this.e = 0;
            }
        }

        super.c();
    }

    /**
     * Teleport the enderman to a random nearby position
     */
    protected boolean m()
    {
        double var1 = this.locX + (this.random.nextDouble() - 0.5D) * 64.0D;
        double var3 = this.locY + (double)(this.random.nextInt(64) - 32);
        double var5 = this.locZ + (this.random.nextDouble() - 0.5D) * 64.0D;
        return this.j(var1, var3, var5);
    }

    /**
     * Teleport the enderman to another entity
     */
    protected boolean p(Entity par1Entity)
    {
        Vec3D var2 = this.world.getVec3DPool().create(this.locX - par1Entity.locX, this.boundingBox.b + (double) (this.length / 2.0F) - par1Entity.locY + (double) par1Entity.getHeadHeight(), this.locZ - par1Entity.locZ);
        var2 = var2.a();
        double var3 = 16.0D;
        double var5 = this.locX + (this.random.nextDouble() - 0.5D) * 8.0D - var2.c * var3;
        double var7 = this.locY + (double)(this.random.nextInt(16) - 8) - var2.d * var3;
        double var9 = this.locZ + (this.random.nextDouble() - 0.5D) * 8.0D - var2.e * var3;
        return this.j(var5, var7, var9);
    }

    /**
     * Teleport the enderman
     */
    protected boolean j(double par1, double par3, double par5)
    {
        double var7 = this.locX;
        double var9 = this.locY;
        double var11 = this.locZ;
        this.locX = par1;
        this.locY = par3;
        this.locZ = par5;
        boolean var13 = false;
        int var14 = MathHelper.floor(this.locX);
        int var15 = MathHelper.floor(this.locY);
        int var16 = MathHelper.floor(this.locZ);
        int var18;

        if (this.world.isLoaded(var14, var15, var16))
        {
            boolean var17 = false;

            while (!var17 && var15 > 0)
            {
                var18 = this.world.getTypeId(var14, var15 - 1, var16);

                if (var18 != 0 && Block.byId[var18].material.isSolid())
                {
                    var17 = true;
                }
                else
                {
                    --this.locY;
                    --var15;
                }
            }

            if (var17)
            {
                this.setPosition(this.locX, this.locY, this.locZ);

                if (this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox))
                {
                    var13 = true;
                }
            }
        }

        if (!var13)
        {
            this.setPosition(var7, var9, var11);
            return false;
        }
        else
        {
            short var30 = 128;

            for (var18 = 0; var18 < var30; ++var18)
            {
                double var19 = (double)var18 / ((double)var30 - 1.0D);
                float var21 = (this.random.nextFloat() - 0.5F) * 0.2F;
                float var22 = (this.random.nextFloat() - 0.5F) * 0.2F;
                float var23 = (this.random.nextFloat() - 0.5F) * 0.2F;
                double var24 = var7 + (this.locX - var7) * var19 + (this.random.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                double var26 = var9 + (this.locY - var9) * var19 + this.random.nextDouble() * (double)this.length;
                double var28 = var11 + (this.locZ - var11) * var19 + (this.random.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                this.world.addParticle("portal", var24, var26, var28, (double) var21, (double) var22, (double) var23);
            }

            this.world.makeSound(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
            this.makeSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return this.q() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.endermen.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.endermen.death";
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.ENDER_PEARL.id;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.getLootId();

        if (var3 > 0)
        {
            int var4 = this.random.nextInt(2 + par2);

            for (int var5 = 0; var5 < var4; ++var5)
            {
                this.b(var3, 1);
            }
        }
    }

    /**
     * Set the id of the block an enderman carries
     */
    public void setCarriedId(int par1)
    {
        this.datawatcher.watch(16, Byte.valueOf((byte) (par1 & 255)));
    }

    /**
     * Get the id of the block an enderman carries
     */
    public int getCarriedId()
    {
        return this.datawatcher.getByte(16);
    }

    /**
     * Set the metadata of the block an enderman carries
     */
    public void setCarriedData(int par1)
    {
        this.datawatcher.watch(17, Byte.valueOf((byte) (par1 & 255)));
    }

    /**
     * Get the metadata of the block an enderman carries
     */
    public int getCarriedData()
    {
        return this.datawatcher.getByte(17);
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
        else if (par1DamageSource instanceof EntityDamageSourceIndirect)
        {
            for (int var3 = 0; var3 < 64; ++var3)
            {
                if (this.m())
                {
                    return true;
                }
            }

            return false;
        }
        else
        {
            if (par1DamageSource.getEntity() instanceof EntityHuman)
            {
                this.f(true);
            }

            return super.damageEntity(par1DamageSource, par2);
        }
    }

    public boolean q()
    {
        return this.datawatcher.getByte(18) > 0;
    }

    public void f(boolean par1)
    {
        this.datawatcher.watch(18, Byte.valueOf((byte) (par1 ? 1 : 0)));
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int c(Entity par1Entity)
    {
        return 7;
    }

    static
    {
        d[Block.GRASS.id] = true;
        d[Block.DIRT.id] = true;
        d[Block.SAND.id] = true;
        d[Block.GRAVEL.id] = true;
        d[Block.YELLOW_FLOWER.id] = true;
        d[Block.RED_ROSE.id] = true;
        d[Block.BROWN_MUSHROOM.id] = true;
        d[Block.RED_MUSHROOM.id] = true;
        d[Block.TNT.id] = true;
        d[Block.CACTUS.id] = true;
        d[Block.CLAY.id] = true;
        d[Block.PUMPKIN.id] = true;
        d[Block.MELON.id] = true;
        d[Block.MYCEL.id] = true;
    }
}
