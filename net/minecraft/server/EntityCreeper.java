package net.minecraft.server;

public class EntityCreeper extends EntityMonster
{
    /**
     * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
     * weird)
     */
    private int d;

    /**
     * The amount of time since the creeper was close enough to the player to ignite
     */
    private int fuseTicks;
    private int maxFuseTicks = 30;

    /** Explosion radius for this creeper. */
    private int explosionRadius = 3;

    public EntityCreeper(World par1World)
    {
        super(par1World);
        this.texture = "/mob/creeper.png";
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalSwell(this));
        this.goalSelector.a(3, new PathfinderGoalAvoidPlayer(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 0.25F, false));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.2F));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean be()
    {
        return true;
    }

    public int as()
    {
        return this.aG() == null ? 3 : 3 + (this.health - 1);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1)
    {
        super.a(par1);
        this.fuseTicks = (int)((float)this.fuseTicks + par1 * 1.5F);

        if (this.fuseTicks > this.maxFuseTicks - 5)
        {
            this.fuseTicks = this.maxFuseTicks - 5;
        }
    }

    public int getMaxHealth()
    {
        return 20;
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, Byte.valueOf((byte) -1));
        this.datawatcher.a(17, Byte.valueOf((byte) 0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);

        if (this.datawatcher.getByte(17) == 1)
        {
            par1NBTTagCompound.setBoolean("powered", true);
        }

        par1NBTTagCompound.setShort("Fuse", (short)this.maxFuseTicks);
        par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.datawatcher.watch(17, Byte.valueOf((byte) (par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));

        if (par1NBTTagCompound.hasKey("Fuse"))
        {
            this.maxFuseTicks = par1NBTTagCompound.getShort("Fuse");
        }

        if (par1NBTTagCompound.hasKey("ExplosionRadius"))
        {
            this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        if (this.isAlive())
        {
            this.d = this.fuseTicks;
            int var1 = this.o();

            if (var1 > 0 && this.fuseTicks == 0)
            {
                this.makeSound("random.fuse", 1.0F, 0.5F);
            }

            this.fuseTicks += var1;

            if (this.fuseTicks < 0)
            {
                this.fuseTicks = 0;
            }

            if (this.fuseTicks >= this.maxFuseTicks)
            {
                this.fuseTicks = this.maxFuseTicks;

                if (!this.world.isStatic)
                {
                    boolean var2 = this.world.getGameRules().getBoolean("mobGriefing");

                    if (this.isPowered())
                    {
                        this.world.explode(this, this.locX, this.locY, this.locZ, (float) (this.explosionRadius * 2), var2);
                    }
                    else
                    {
                        this.world.explode(this, this.locX, this.locY, this.locZ, (float) this.explosionRadius, var2);
                    }

                    this.die();
                }
            }
        }

        super.j_();
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.creeper.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.creeper.death";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource par1DamageSource)
    {
        super.die(par1DamageSource);

        if (par1DamageSource.getEntity() instanceof EntitySkeleton)
        {
            int var2 = Item.RECORD_1.id + this.random.nextInt(Item.RECORD_12.id - Item.RECORD_1.id + 1);
            this.b(var2, 1);
        }
    }

    public boolean m(Entity par1Entity)
    {
        return true;
    }

    /**
     * Returns true if the creeper is powered by a lightning bolt.
     */
    public boolean isPowered()
    {
        return this.datawatcher.getByte(17) == 1;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.SULPHUR.id;
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int o()
    {
        return this.datawatcher.getByte(16);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void a(int par1)
    {
        this.datawatcher.watch(16, Byte.valueOf((byte) par1));
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void a(EntityLightning par1EntityLightningBolt)
    {
        super.a(par1EntityLightningBolt);
        this.datawatcher.watch(17, Byte.valueOf((byte) 1));
    }
}
