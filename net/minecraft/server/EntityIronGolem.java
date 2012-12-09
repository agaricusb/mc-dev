package net.minecraft.server;

public class EntityIronGolem extends EntityGolem
{
    /** deincrements, and a distance-to-home check is done at 0 */
    private int e = 0;
    Village d = null;
    private int f;
    private int g;

    public EntityIronGolem(World par1World)
    {
        super(par1World);
        this.texture = "/mob/villager_golem.png";
        this.a(1.4F, 2.9F);
        this.getNavigation().a(true);
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 0.25F, true));
        this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.22F, 32.0F));
        this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.16F, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 0.16F));
        this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.16F));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 16.0F, 0, false, true, IMonster.a));
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, Byte.valueOf((byte) 0));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean be()
    {
        return true;
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void bm()
    {
        if (--this.e <= 0)
        {
            this.e = 70 + this.random.nextInt(50);
            this.d = this.world.villages.getClosestVillage(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 32);

            if (this.d == null)
            {
                this.aL();
            }
            else
            {
                ChunkCoordinates var1 = this.d.getCenter();
                this.b(var1.x, var1.y, var1.z, (int) ((float) this.d.getSize() * 0.6F));
            }
        }

        super.bm();
    }

    public int getMaxHealth()
    {
        return 100;
    }

    /**
     * Decrements the entity's air supply when underwater
     */
    protected int g(int par1)
    {
        return par1;
    }

    protected void o(Entity par1Entity)
    {
        if (par1Entity instanceof IMonster && this.aB().nextInt(20) == 0)
        {
            this.b((EntityLiving) par1Entity);
        }

        super.o(par1Entity);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        super.c();

        if (this.f > 0)
        {
            --this.f;
        }

        if (this.g > 0)
        {
            --this.g;
        }

        if (this.motX * this.motX + this.motZ * this.motZ > 2.500000277905201E-7D && this.random.nextInt(5) == 0)
        {
            int var1 = MathHelper.floor(this.locX);
            int var2 = MathHelper.floor(this.locY - 0.20000000298023224D - (double) this.height);
            int var3 = MathHelper.floor(this.locZ);
            int var4 = this.world.getTypeId(var1, var2, var3);

            if (var4 > 0)
            {
                this.world.addParticle("tilecrack_" + var4 + "_" + this.world.getData(var1, var2, var3), this.locX + ((double) this.random.nextFloat() - 0.5D) * (double) this.width, this.boundingBox.b + 0.1D, this.locZ + ((double) this.random.nextFloat() - 0.5D) * (double) this.width, 4.0D * ((double) this.random.nextFloat() - 0.5D), 0.5D, ((double) this.random.nextFloat() - 0.5D) * 4.0D);
            }
        }
    }

    /**
     * Returns true if this entity can attack entities of the specified class.
     */
    public boolean a(Class par1Class)
    {
        return this.p() && EntityHuman.class.isAssignableFrom(par1Class) ? false : super.a(par1Class);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("PlayerCreated", this.p());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.setPlayerCreated(par1NBTTagCompound.getBoolean("PlayerCreated"));
    }

    public boolean m(Entity par1Entity)
    {
        this.f = 10;
        this.world.broadcastEntityEffect(this, (byte) 4);
        boolean var2 = par1Entity.damageEntity(DamageSource.mobAttack(this), 7 + this.random.nextInt(15));

        if (var2)
        {
            par1Entity.motY += 0.4000000059604645D;
        }

        this.makeSound("mob.irongolem.throw", 1.0F, 1.0F);
        return var2;
    }

    public Village m()
    {
        return this.d;
    }

    public void f(boolean par1)
    {
        this.g = par1 ? 400 : 0;
        this.world.broadcastEntityEffect(this, (byte) 11);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "none";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.irongolem.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.irongolem.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void a(int par1, int par2, int par3, int par4)
    {
        this.makeSound("mob.irongolem.walk", 1.0F, 1.0F);
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.random.nextInt(3);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.b(Block.RED_ROSE.id, 1);
        }

        var4 = 3 + this.random.nextInt(3);

        for (int var5 = 0; var5 < var4; ++var5)
        {
            this.b(Item.IRON_INGOT.id, 1);
        }
    }

    public int o()
    {
        return this.g;
    }

    public boolean p()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void setPlayerCreated(boolean par1)
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
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource par1DamageSource)
    {
        if (!this.p() && this.killer != null && this.d != null)
        {
            this.d.a(this.killer.getName(), -5);
        }

        super.die(par1DamageSource);
    }
}
