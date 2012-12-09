package net.minecraft.server;

import java.util.List;

public class EntityWither extends EntityMonster implements IRangedEntity
{
    private float[] d = new float[2];
    private float[] e = new float[2];
    private float[] f = new float[2];
    private float[] g = new float[2];
    private int[] h = new int[2];
    private int[] i = new int[2];
    private int j;

    /** Selector used to determine the entities a wither boss should attack. */
    private static final IEntitySelector bI = new EntitySelectorNotUndead();

    public EntityWither(World par1World)
    {
        super(par1World);
        this.setHealth(this.getMaxHealth());
        this.texture = "/mob/wither.png";
        this.a(0.9F, 4.0F);
        this.fireProof = true;
        this.bG = 0.6F;
        this.getNavigation().e(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, this.bG, 40, 20.0F));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, this.bG));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 30.0F, 0, false, false, bI));
        this.bc = 50;
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, new Integer(100));
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(0));
        this.datawatcher.a(19, new Integer(0));
        this.datawatcher.a(20, new Integer(0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setInt("Invul", this.n());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.t(par1NBTTagCompound.getInt("Invul"));
        this.datawatcher.watch(16, Integer.valueOf(this.health));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.wither.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.wither.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.wither.death";
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        if (!this.world.isStatic)
        {
            this.datawatcher.watch(16, Integer.valueOf(this.health));
        }

        this.motY *= 0.6000000238418579D;
        double var4;
        double var6;
        double var8;

        if (!this.world.isStatic && this.u(0) > 0)
        {
            Entity var1 = this.world.getEntity(this.u(0));

            if (var1 != null)
            {
                if (this.locY < var1.locY || !this.o() && this.locY < var1.locY + 5.0D)
                {
                    if (this.motY < 0.0D)
                    {
                        this.motY = 0.0D;
                    }

                    this.motY += (0.5D - this.motY) * 0.6000000238418579D;
                }

                double var2 = var1.locX - this.locX;
                var4 = var1.locZ - this.locZ;
                var6 = var2 * var2 + var4 * var4;

                if (var6 > 9.0D)
                {
                    var8 = (double) MathHelper.sqrt(var6);
                    this.motX += (var2 / var8 * 0.5D - this.motX) * 0.6000000238418579D;
                    this.motZ += (var4 / var8 * 0.5D - this.motZ) * 0.6000000238418579D;
                }
            }
        }

        if (this.motX * this.motX + this.motZ * this.motZ > 0.05000000074505806D)
        {
            this.yaw = (float)Math.atan2(this.motZ, this.motX) * (180F / (float)Math.PI) - 90.0F;
        }

        super.c();
        int var20;

        for (var20 = 0; var20 < 2; ++var20)
        {
            this.g[var20] = this.e[var20];
            this.f[var20] = this.d[var20];
        }

        int var21;

        for (var20 = 0; var20 < 2; ++var20)
        {
            var21 = this.u(var20 + 1);
            Entity var3 = null;

            if (var21 > 0)
            {
                var3 = this.world.getEntity(var21);
            }

            if (var3 != null)
            {
                var4 = this.v(var20 + 1);
                var6 = this.w(var20 + 1);
                var8 = this.x(var20 + 1);
                double var10 = var3.locX - var4;
                double var12 = var3.locY + (double)var3.getHeadHeight() - var6;
                double var14 = var3.locZ - var8;
                double var16 = (double) MathHelper.sqrt(var10 * var10 + var14 * var14);
                float var18 = (float)(Math.atan2(var14, var10) * 180.0D / Math.PI) - 90.0F;
                float var19 = (float)(-(Math.atan2(var12, var16) * 180.0D / Math.PI));
                this.d[var20] = this.b(this.d[var20], var19, 40.0F);
                this.e[var20] = this.b(this.e[var20], var18, 10.0F);
            }
            else
            {
                this.e[var20] = this.b(this.e[var20], this.aw, 10.0F);
            }
        }

        boolean var22 = this.o();

        for (var21 = 0; var21 < 3; ++var21)
        {
            double var23 = this.v(var21);
            double var5 = this.w(var21);
            double var7 = this.x(var21);
            this.world.addParticle("smoke", var23 + this.random.nextGaussian() * 0.30000001192092896D, var5 + this.random.nextGaussian() * 0.30000001192092896D, var7 + this.random.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);

            if (var22 && this.world.random.nextInt(4) == 0)
            {
                this.world.addParticle("mobSpell", var23 + this.random.nextGaussian() * 0.30000001192092896D, var5 + this.random.nextGaussian() * 0.30000001192092896D, var7 + this.random.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
            }
        }

        if (this.n() > 0)
        {
            for (var21 = 0; var21 < 3; ++var21)
            {
                this.world.addParticle("mobSpell", this.locX + this.random.nextGaussian() * 1.0D, this.locY + (double) (this.random.nextFloat() * 3.3F), this.locZ + this.random.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
            }
        }
    }

    protected void bl()
    {
        int var1;

        if (this.n() > 0)
        {
            var1 = this.n() - 1;

            if (var1 <= 0)
            {
                this.world.createExplosion(this, this.locX, this.locY + (double) this.getHeadHeight(), this.locZ, 7.0F, false, this.world.getGameRules().getBoolean("mobGriefing"));
                this.world.e(1013, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
            }

            this.t(var1);

            if (this.ticksLived % 10 == 0)
            {
                this.heal(10);
            }
        }
        else
        {
            super.bl();
            int var13;

            for (var1 = 1; var1 < 3; ++var1)
            {
                if (this.ticksLived >= this.h[var1 - 1])
                {
                    this.h[var1 - 1] = this.ticksLived + 10 + this.random.nextInt(10);

                    if (this.world.difficulty >= 2)
                    {
                        int var10001 = var1 - 1;
                        int var10003 = this.i[var1 - 1];
                        this.i[var10001] = this.i[var1 - 1] + 1;

                        if (var10003 > 15)
                        {
                            float var2 = 10.0F;
                            float var3 = 5.0F;
                            double var4 = MathHelper.a(this.random, this.locX - (double) var2, this.locX + (double) var2);
                            double var6 = MathHelper.a(this.random, this.locY - (double) var3, this.locY + (double) var3);
                            double var8 = MathHelper.a(this.random, this.locZ - (double) var2, this.locZ + (double) var2);
                            this.a(var1 + 1, var4, var6, var8, true);
                            this.i[var1 - 1] = 0;
                        }
                    }

                    var13 = this.u(var1);

                    if (var13 > 0)
                    {
                        Entity var15 = this.world.getEntity(var13);

                        if (var15 != null && var15.isAlive() && this.e(var15) <= 900.0D && this.n(var15))
                        {
                            this.a(var1 + 1, (EntityLiving) var15);
                            this.h[var1 - 1] = this.ticksLived + 40 + this.random.nextInt(20);
                            this.i[var1 - 1] = 0;
                        }
                        else
                        {
                            this.c(var1, 0);
                        }
                    }
                    else
                    {
                        List var14 = this.world.a(EntityLiving.class, this.boundingBox.grow(20.0D, 8.0D, 20.0D), bI);

                        for (int var17 = 0; var17 < 10 && !var14.isEmpty(); ++var17)
                        {
                            EntityLiving var5 = (EntityLiving)var14.get(this.random.nextInt(var14.size()));

                            if (var5 != this && var5.isAlive() && this.n(var5))
                            {
                                if (var5 instanceof EntityHuman)
                                {
                                    if (!((EntityHuman)var5).abilities.isInvulnerable)
                                    {
                                        this.c(var1, var5.id);
                                    }
                                }
                                else
                                {
                                    this.c(var1, var5.id);
                                }

                                break;
                            }

                            var14.remove(var5);
                        }
                    }
                }
            }

            if (this.aG() != null)
            {
                this.c(0, this.aG().id);
            }
            else
            {
                this.c(0, 0);
            }

            if (this.j > 0)
            {
                --this.j;

                if (this.j == 0 && this.world.getGameRules().getBoolean("mobGriefing"))
                {
                    var1 = MathHelper.floor(this.locY);
                    var13 = MathHelper.floor(this.locX);
                    int var16 = MathHelper.floor(this.locZ);
                    boolean var19 = false;

                    for (int var18 = -1; var18 <= 1; ++var18)
                    {
                        for (int var20 = -1; var20 <= 1; ++var20)
                        {
                            for (int var7 = 0; var7 <= 3; ++var7)
                            {
                                int var21 = var13 + var18;
                                int var9 = var1 + var7;
                                int var10 = var16 + var20;
                                int var11 = this.world.getTypeId(var21, var9, var10);

                                if (var11 > 0 && var11 != Block.BEDROCK.id && var11 != Block.ENDER_PORTAL.id && var11 != Block.ENDER_PORTAL_FRAME.id)
                                {
                                    int var12 = this.world.getData(var21, var9, var10);
                                    this.world.triggerEffect(2001, var21, var9, var10, var11 + (var12 << 12));
                                    Block.byId[var11].c(this.world, var21, var9, var10, var12, 0);
                                    this.world.setTypeId(var21, var9, var10, 0);
                                    var19 = true;
                                }
                            }
                        }
                    }

                    if (var19)
                    {
                        this.world.a((EntityHuman) null, 1012, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
                    }
                }
            }

            if (this.ticksLived % 20 == 0)
            {
                this.heal(1);
            }
        }
    }

    public void m()
    {
        this.t(220);
        this.setHealth(this.getMaxHealth() / 3);
    }

    /**
     * Sets the Entity inside a web block.
     */
    public void am() {}

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int aW()
    {
        return 4;
    }

    private double v(int par1)
    {
        if (par1 <= 0)
        {
            return this.locX;
        }
        else
        {
            float var2 = (this.aw + (float)(180 * (par1 - 1))) / 180.0F * (float)Math.PI;
            float var3 = MathHelper.cos(var2);
            return this.locX + (double)var3 * 1.3D;
        }
    }

    private double w(int par1)
    {
        return par1 <= 0 ? this.locY + 3.0D : this.locY + 2.2D;
    }

    private double x(int par1)
    {
        if (par1 <= 0)
        {
            return this.locZ;
        }
        else
        {
            float var2 = (this.aw + (float)(180 * (par1 - 1))) / 180.0F * (float)Math.PI;
            float var3 = MathHelper.sin(var2);
            return this.locZ + (double)var3 * 1.3D;
        }
    }

    private float b(float par1, float par2, float par3)
    {
        float var4 = MathHelper.g(par2 - par1);

        if (var4 > par3)
        {
            var4 = par3;
        }

        if (var4 < -par3)
        {
            var4 = -par3;
        }

        return par1 + var4;
    }

    private void a(int par1, EntityLiving par2EntityLiving)
    {
        this.a(par1, par2EntityLiving.locX, par2EntityLiving.locY + (double) par2EntityLiving.getHeadHeight() * 0.5D, par2EntityLiving.locZ, par1 == 0 && this.random.nextFloat() < 0.001F);
    }

    private void a(int par1, double par2, double par4, double par6, boolean par8)
    {
        this.world.a((EntityHuman) null, 1014, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
        double var9 = this.v(par1);
        double var11 = this.w(par1);
        double var13 = this.x(par1);
        double var15 = par2 - var9;
        double var17 = par4 - var11;
        double var19 = par6 - var13;
        EntityWitherSkull var21 = new EntityWitherSkull(this.world, this, var15, var17, var19);

        if (par8)
        {
            var21.e(true);
        }

        var21.locY = var11;
        var21.locX = var9;
        var21.locZ = var13;
        this.world.addEntity(var21);
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void d(EntityLiving par1EntityLiving)
    {
        this.a(0, par1EntityLiving);
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
        else if (par1DamageSource == DamageSource.DROWN)
        {
            return false;
        }
        else if (this.n() > 0)
        {
            return false;
        }
        else
        {
            Entity var3;

            if (this.o())
            {
                var3 = par1DamageSource.f();

                if (var3 instanceof EntityArrow)
                {
                    return false;
                }
            }

            var3 = par1DamageSource.getEntity();

            if (var3 != null && !(var3 instanceof EntityHuman) && var3 instanceof EntityLiving && ((EntityLiving)var3).getMonsterType() == this.getMonsterType())
            {
                return false;
            }
            else
            {
                if (this.j <= 0)
                {
                    this.j = 20;
                }

                for (int var4 = 0; var4 < this.i.length; ++var4)
                {
                    this.i[var4] += 3;
                }

                return super.damageEntity(par1DamageSource, par2);
            }
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        this.b(Item.NETHER_STAR.id, 1);
    }

    /**
     * Makes the entity despawn if requirements are reached
     */
    protected void bk()
    {
        this.bA = 0;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return !this.dead;
    }

    /**
     * Returns the health points of the dragon.
     */
    public int b()
    {
        return this.datawatcher.getInt(16);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1) {}

    /**
     * adds a PotionEffect to the entity
     */
    public void addEffect(MobEffect par1PotionEffect) {}

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean be()
    {
        return true;
    }

    public int getMaxHealth()
    {
        return 300;
    }

    public int n()
    {
        return this.datawatcher.getInt(20);
    }

    public void t(int par1)
    {
        this.datawatcher.watch(20, Integer.valueOf(par1));
    }

    /**
     * Returns the target entity ID if present, or -1 if not @param par1 The target offset, should be from 0-2
     */
    public int u(int par1)
    {
        return this.datawatcher.getInt(17 + par1);
    }

    public void c(int par1, int par2)
    {
        this.datawatcher.watch(17 + par1, Integer.valueOf(par2));
    }

    /**
     * Returns whether the wither is armored with its boss armor or not by checking whether its health is below half of
     * its maximum.
     */
    public boolean o()
    {
        return this.b() <= this.getMaxHealth() / 2;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumMonsterType getMonsterType()
    {
        return EnumMonsterType.UNDEAD;
    }
}
