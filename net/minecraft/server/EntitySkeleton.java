package net.minecraft.server;

import java.util.Calendar;

public class EntitySkeleton extends EntityMonster implements IRangedEntity
{
    private PathfinderGoalArrowAttack d = new PathfinderGoalArrowAttack(this, 0.25F, 60, 10.0F);
    private PathfinderGoalMeleeAttack e = new PathfinderGoalMeleeAttack(this, EntityHuman.class, 0.31F, false);

    public EntitySkeleton(World par1World)
    {
        super(par1World);
        this.texture = "/mob/skeleton.png";
        this.bH = 0.25F;
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
        this.goalSelector.a(3, new PathfinderGoalFleeSun(this, this.bH));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, this.bH));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));

        if (par1World != null && !par1World.isStatic)
        {
            this.m();
        }
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(13, new Byte((byte) 0));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean be()
    {
        return true;
    }

    public int getMaxHealth()
    {
        return 20;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.skeleton.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.skeleton.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.skeleton.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void a(int par1, int par2, int par3, int par4)
    {
        this.makeSound("mob.skeleton.step", 0.15F, 1.0F);
    }

    public boolean m(Entity par1Entity)
    {
        if (super.m(par1Entity))
        {
            if (this.getSkeletonType() == 1 && par1Entity instanceof EntityLiving)
            {
                ((EntityLiving)par1Entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 200));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int c(Entity par1Entity)
    {
        if (this.getSkeletonType() == 1)
        {
            ItemStack var2 = this.bD();
            int var3 = 4;

            if (var2 != null)
            {
                var3 += var2.a((Entity) this);
            }

            return var3;
        }
        else
        {
            return super.c(par1Entity);
        }
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumMonsterType getMonsterType()
    {
        return EnumMonsterType.UNDEAD;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        if (this.world.u() && !this.world.isStatic)
        {
            float var1 = this.c(1.0F);

            if (var1 > 0.5F && this.random.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && this.world.k(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)))
            {
                boolean var2 = true;
                ItemStack var3 = this.getEquipment(4);

                if (var3 != null)
                {
                    if (var3.f())
                    {
                        var3.setData(var3.i() + this.random.nextInt(2));

                        if (var3.i() >= var3.k())
                        {
                            this.a(var3);
                            this.setEquipment(4, (ItemStack) null);
                        }
                    }

                    var2 = false;
                }

                if (var2)
                {
                    this.setOnFire(8);
                }
            }
        }

        super.c();
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource par1DamageSource)
    {
        super.die(par1DamageSource);

        if (par1DamageSource.f() instanceof EntityArrow && par1DamageSource.getEntity() instanceof EntityHuman)
        {
            EntityHuman var2 = (EntityHuman)par1DamageSource.getEntity();
            double var3 = var2.locX - this.locX;
            double var5 = var2.locZ - this.locZ;

            if (var3 * var3 + var5 * var5 >= 2500.0D)
            {
                var2.a(AchievementList.v);
            }
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.ARROW.id;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3;
        int var4;

        if (this.getSkeletonType() == 1)
        {
            var3 = this.random.nextInt(3 + par2) - 1;

            for (var4 = 0; var4 < var3; ++var4)
            {
                this.b(Item.COAL.id, 1);
            }
        }
        else
        {
            var3 = this.random.nextInt(3 + par2);

            for (var4 = 0; var4 < var3; ++var4)
            {
                this.b(Item.ARROW.id, 1);
            }
        }

        var3 = this.random.nextInt(3 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.b(Item.BONE.id, 1);
        }
    }

    protected void l(int par1)
    {
        if (this.getSkeletonType() == 1)
        {
            this.a(new ItemStack(Item.SKULL.id, 1, 1), 0.0F);
        }
    }

    protected void bE()
    {
        super.bE();
        this.setEquipment(0, new ItemStack(Item.BOW));
    }

    /**
     * Initialize this creature.
     */
    public void bG()
    {
        if (this.world.worldProvider instanceof WorldProviderHell && this.aB().nextInt(5) > 0)
        {
            this.goalSelector.a(4, this.e);
            this.setSkeletonType(1);
            this.setEquipment(0, new ItemStack(Item.STONE_SWORD));
        }
        else
        {
            this.goalSelector.a(4, this.d);
            this.bE();
            this.bF();
        }

        this.canPickUpLoot = this.random.nextFloat() < at[this.world.difficulty];

        if (this.getEquipment(4) == null)
        {
            Calendar var1 = this.world.T();

            if (var1.get(2) + 1 == 10 && var1.get(5) == 31 && this.random.nextFloat() < 0.25F)
            {
                this.setEquipment(4, new ItemStack(this.random.nextFloat() < 0.1F ? Block.JACK_O_LANTERN : Block.PUMPKIN));
                this.dropChances[4] = 0.0F;
            }
        }
    }

    public void m()
    {
        this.goalSelector.a(this.e);
        this.goalSelector.a(this.d);
        ItemStack var1 = this.bD();

        if (var1 != null && var1.id == Item.BOW.id)
        {
            this.goalSelector.a(4, this.d);
        }
        else
        {
            this.goalSelector.a(4, this.e);
        }
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void d(EntityLiving par1EntityLiving)
    {
        EntityArrow var2 = new EntityArrow(this.world, this, par1EntityLiving, 1.6F, 12.0F);
        int var3 = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, this.bD());
        int var4 = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, this.bD());

        if (var3 > 0)
        {
            var2.b(var2.c() + (double) var3 * 0.5D + 0.5D);
        }

        if (var4 > 0)
        {
            var2.a(var4);
        }

        if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, this.bD()) > 0 || this.getSkeletonType() == 1)
        {
            var2.setOnFire(100);
        }

        this.makeSound("random.bow", 1.0F, 1.0F / (this.aB().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(var2);
    }

    /**
     * Return this skeleton's type.
     */
    public int getSkeletonType()
    {
        return this.datawatcher.getByte(13);
    }

    /**
     * Set this skeleton's type.
     */
    public void setSkeletonType(int par1)
    {
        this.datawatcher.watch(13, Byte.valueOf((byte) par1));
        this.fireProof = par1 == 1;

        if (par1 == 1)
        {
            this.a(0.72F, 2.16F);
        }
        else
        {
            this.a(0.6F, 1.8F);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("SkeletonType"))
        {
            byte var2 = par1NBTTagCompound.getByte("SkeletonType");
            this.setSkeletonType(var2);
        }

        this.m();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setByte("SkeletonType", (byte) this.getSkeletonType());
    }

    /**
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     */
    public void setEquipment(int par1, ItemStack par2ItemStack)
    {
        super.setEquipment(par1, par2ItemStack);

        if (!this.world.isStatic && par1 == 0)
        {
            this.m();
        }
    }
}
