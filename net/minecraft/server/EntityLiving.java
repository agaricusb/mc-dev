package net.minecraft.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class EntityLiving extends Entity
{
    /**
     * An array of probabilities that determines whether a random enchantment should be added to the held item. Indexed
     * by difficulty.
     */
    private static final float[] b = new float[] {0.0F, 0.0F, 0.005F, 0.01F};
    private static final float[] c = new float[] {0.0F, 0.0F, 0.05F, 0.1F};
    private static final float[] d = new float[] {0.0F, 0.0F, 0.005F, 0.02F};
    public static final float[] as = new float[] {0.0F, 0.01F, 0.07F, 0.2F};
    public int maxNoDamageTicks = 20;
    public float au;
    public float av;
    public float aw = 0.0F;
    public float ax = 0.0F;

    /** Entity head rotation yaw */
    public float ay = 0.0F;

    /** Entity head rotation yaw at previous tick */
    public float az = 0.0F;
    protected float aA;
    protected float aB;
    protected float aC;
    protected float aD;
    protected boolean aE = true;

    /** the path for the texture of this entityLiving */
    protected String texture = "/mob/char.png";
    protected boolean aG = true;
    protected float aH = 0.0F;

    /**
     * a string holding the type of entity it is currently only implemented in entityPlayer(as 'humanoid')
     */
    protected String aI = null;
    protected float aJ = 1.0F;

    /** The score value of the Mob, the amount of points the mob is worth. */
    protected int aK = 0;
    protected float aL = 0.0F;

    /**
     * A factor used to determine how far this entity will move each tick if it is walking on land. Adjusted by speed,
     * and slipperiness of the current block.
     */
    public float aM = 0.1F;

    /**
     * A factor used to determine how far this entity will move each tick if it is jumping or falling.
     */
    public float aN = 0.02F;
    public float aO;
    public float aP;
    protected int health = this.getMaxHealth();
    public int aR;

    /**
     * in each step in the damage calculations, this is set to the 'carryover' that would result if someone was damaged
     * .25 hearts (for example), and added to the damage in the next step
     */
    protected int aS;

    /** Number of ticks since this EntityLiving last produced its sound */
    public int aT;

    /**
     * The amount of time remaining this entity should act 'hurt'. (Visual appearance of red tint)
     */
    public int hurtTicks;

    /** What the hurt time was max set to last. */
    public int aV;

    /** The yaw at which this entity was last attacked from. */
    public float aW = 0.0F;

    /**
     * The amount of time remaining this entity should act 'dead', i.e. have a corpse in the world.
     */
    public int deathTicks = 0;
    public int attackTicks = 0;
    public float aZ;
    public float ba;

    /**
     * This gets set on entity death, but never used. Looks like a duplicate of isDead
     */
    protected boolean bb = false;

    /** The experience points the Entity gives. */
    protected int bc;
    public int bd = -1;
    public float be = (float)(Math.random() * 0.8999999761581421D + 0.10000000149011612D);
    public float bf;
    public float bg;

    /**
     * Only relevant when legYaw is not 0(the entity is moving). Influences where in its swing legs and arms currently
     * are.
     */
    public float bh;

    /** The most recent player that has attacked this entity */
    protected EntityHuman killer = null;

    /**
     * Set to 60 when hit by the player or the player's wolf, then decrements. Used to determine whether the entity
     * should drop items on death.
     */
    protected int lastDamageByPlayerTime = 0;

    /** is only being set, has no uses as of MC 1.1 */
    private EntityLiving lastDamager = null;
    private int f = 0;
    private EntityLiving g = null;
    public int bk = 0;
    protected HashMap effects = new HashMap();

    /** Whether the DataWatcher needs to be updated with the active potions */
    private boolean updateEffects = true;
    private int i;
    private ControllerLook lookController;
    private ControllerMove moveController;

    /** Entity jumping helper */
    private ControllerJump jumpController;
    private EntityAIBodyControl senses;
    private Navigation navigation;
    protected final PathfinderGoalSelector goalSelector;
    protected final PathfinderGoalSelector targetSelector;

    /** The active target the Task system uses for tracking */
    private EntityLiving bM;
    private EntitySenses bN;
    private float bO;
    private ChunkCoordinates bP = new ChunkCoordinates(0, 0, 0);

    /** If -1 there is no maximum distance */
    private float bQ = -1.0F;

    /** Equipment (armor and held item) for this entity. */
    private ItemStack[] equipment = new ItemStack[5];

    /** Chances for each equipment piece from dropping when this entity dies. */
    protected float[] dropChances = new float[5];
    private ItemStack[] bS = new ItemStack[5];

    /** Whether an arm swing is currently in progress. */
    public boolean bp = false;
    public int bq = 0;

    /** Whether this entity can pick up items from the ground. */
    protected boolean canPickUpLoot = false;

    /** Whether this entity should NOT despawn. */
    private boolean persistent = false;

    /**
     * The number of updates over which the new position and rotation are to be applied to the entity.
     */
    protected int bs;

    /** The new X position to be applied to the entity. */
    protected double bt;

    /** The new Y position to be applied to the entity. */
    protected double bu;

    /** The new Z position to be applied to the entity. */
    protected double bv;

    /** The new yaw rotation to be applied to the entity. */
    protected double bw;

    /** The new yaw rotation to be applied to the entity. */
    protected double bx;
    float by = 0.0F;

    /** Amount of damage taken in last hit, in half-hearts */
    protected int lastDamage = 0;

    /** The age of this EntityLiving (used to determine when it dies) */
    protected int bA = 0;
    protected float bB;
    protected float bC;
    protected float bD;

    /** used to check whether entity is jumping. */
    protected boolean bE = false;
    protected float bF = 0.0F;
    protected float bG = 0.7F;

    /** Number of ticks since last jump */
    private int bU = 0;

    /** This entity's current target. */
    private Entity bV;

    /** How long to keep a specific target entity */
    protected int bH = 0;

    public EntityLiving(World par1World)
    {
        super(par1World);
        this.m = true;
        this.goalSelector = new PathfinderGoalSelector(par1World != null && par1World.methodProfiler != null ? par1World.methodProfiler : null);
        this.targetSelector = new PathfinderGoalSelector(par1World != null && par1World.methodProfiler != null ? par1World.methodProfiler : null);
        this.lookController = new ControllerLook(this);
        this.moveController = new ControllerMove(this);
        this.jumpController = new ControllerJump(this);
        this.senses = new EntityAIBodyControl(this);
        this.navigation = new Navigation(this, par1World, 16.0F);
        this.bN = new EntitySenses(this);
        this.av = (float)(Math.random() + 1.0D) * 0.01F;
        this.setPosition(this.locX, this.locY, this.locZ);
        this.au = (float)Math.random() * 12398.0F;
        this.yaw = (float)(Math.random() * Math.PI * 2.0D);
        this.ay = this.yaw;

        for (int var2 = 0; var2 < this.dropChances.length; ++var2)
        {
            this.dropChances[var2] = 0.05F;
        }

        this.X = 0.5F;
    }

    public ControllerLook getControllerLook()
    {
        return this.lookController;
    }

    public ControllerMove getControllerMove()
    {
        return this.moveController;
    }

    public ControllerJump getControllerJump()
    {
        return this.jumpController;
    }

    public Navigation getNavigation()
    {
        return this.navigation;
    }

    /**
     * returns the EntitySenses Object for the EntityLiving
     */
    public EntitySenses aA()
    {
        return this.bN;
    }

    public Random aB()
    {
        return this.random;
    }

    public EntityLiving aC()
    {
        return this.lastDamager;
    }

    public EntityLiving aD()
    {
        return this.g;
    }

    public void l(Entity par1Entity)
    {
        if (par1Entity instanceof EntityLiving)
        {
            this.g = (EntityLiving)par1Entity;
        }
    }

    public int aE()
    {
        return this.bA;
    }

    public float ap()
    {
        return this.ay;
    }

    /**
     * the movespeed used for the new AI system
     */
    public float aF()
    {
        return this.bO;
    }

    /**
     * set the movespeed used for the new AI system
     */
    public void e(float par1)
    {
        this.bO = par1;
        this.f(par1);
    }

    public boolean m(Entity par1Entity)
    {
        this.l(par1Entity);
        return false;
    }

    /**
     * Gets the active target the Task system uses for tracking
     */
    public EntityLiving aG()
    {
        return this.bM;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void b(EntityLiving par1EntityLiving)
    {
        this.bM = par1EntityLiving;
    }

    /**
     * Returns true if this entity can attack entities of the specified class.
     */
    public boolean a(Class par1Class)
    {
        return EntityCreeper.class != par1Class && EntityGhast.class != par1Class;
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in the AIEatGrass)
     */
    public void aH() {}

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void a(double par1, boolean par3)
    {
        if (!this.H())
        {
            this.I();
        }

        if (par3 && this.fallDistance > 0.0F)
        {
            int var4 = MathHelper.floor(this.locX);
            int var5 = MathHelper.floor(this.locY - 0.20000000298023224D - (double) this.height);
            int var6 = MathHelper.floor(this.locZ);
            int var7 = this.world.getTypeId(var4, var5, var6);

            if (var7 == 0)
            {
                int var8 = this.world.e(var4, var5 - 1, var6);

                if (var8 == 11 || var8 == 32 || var8 == 21)
                {
                    var7 = this.world.getTypeId(var4, var5 - 1, var6);
                }
            }

            if (var7 > 0)
            {
                Block.byId[var7].a(this.world, var4, var5, var6, this, this.fallDistance);
            }
        }

        super.a(par1, par3);
    }

    /**
     * Returns true if entity is within home distance from current position
     */
    public boolean aI()
    {
        return this.e(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
    }

    public boolean e(int par1, int par2, int par3)
    {
        return this.bQ == -1.0F ? true : this.bP.e(par1, par2, par3) < this.bQ * this.bQ;
    }

    public void b(int par1, int par2, int par3, int par4)
    {
        this.bP.b(par1, par2, par3);
        this.bQ = (float)par4;
    }

    public ChunkCoordinates aJ()
    {
        return this.bP;
    }

    public float aK()
    {
        return this.bQ;
    }

    public void aL()
    {
        this.bQ = -1.0F;
    }

    public boolean aM()
    {
        return this.bQ != -1.0F;
    }

    public void c(EntityLiving par1EntityLiving)
    {
        this.lastDamager = par1EntityLiving;
        this.f = this.lastDamager != null ? 60 : 0;
    }

    protected void a()
    {
        this.datawatcher.a(8, Integer.valueOf(this.i));
        this.datawatcher.a(9, Byte.valueOf((byte) 0));
        this.datawatcher.a(10, Byte.valueOf((byte) 0));
    }

    /**
     * returns true if the entity provided in the argument can be seen. (Raytrace)
     */
    public boolean n(Entity par1Entity)
    {
        return this.world.a(this.world.getVec3DPool().create(this.locX, this.locY + (double) this.getHeadHeight(), this.locZ), this.world.getVec3DPool().create(par1Entity.locX, par1Entity.locY + (double) par1Entity.getHeadHeight(), par1Entity.locZ)) == null;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return !this.dead;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean M()
    {
        return !this.dead;
    }

    public float getHeadHeight()
    {
        return this.length * 0.85F;
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int aN()
    {
        return 80;
    }

    /**
     * Plays living's sound at its position
     */
    public void aO()
    {
        String var1 = this.aY();

        if (var1 != null)
        {
            this.makeSound(var1, this.aX(), this.aV());
        }
    }

    /**
     * Gets called every tick from main Entity class
     */
    public void y()
    {
        this.aO = this.aP;
        super.y();
        this.world.methodProfiler.a("mobBaseTick");

        if (this.isAlive() && this.random.nextInt(1000) < this.aT++)
        {
            this.aT = -this.aN();
            this.aO();
        }

        if (this.isAlive() && this.inBlock())
        {
            this.damageEntity(DamageSource.STUCK, 1);
        }

        if (this.isFireproof() || this.world.isStatic)
        {
            this.extinguish();
        }

        if (this.isAlive() && this.a(Material.WATER) && !this.bc() && !this.effects.containsKey(Integer.valueOf(MobEffectList.WATER_BREATHING.id)))
        {
            this.setAirTicks(this.g(this.getAirTicks()));

            if (this.getAirTicks() == -20)
            {
                this.setAirTicks(0);

                for (int var1 = 0; var1 < 8; ++var1)
                {
                    float var2 = this.random.nextFloat() - this.random.nextFloat();
                    float var3 = this.random.nextFloat() - this.random.nextFloat();
                    float var4 = this.random.nextFloat() - this.random.nextFloat();
                    this.world.addParticle("bubble", this.locX + (double) var2, this.locY + (double) var3, this.locZ + (double) var4, this.motX, this.motY, this.motZ);
                }

                this.damageEntity(DamageSource.DROWN, 2);
            }

            this.extinguish();
        }
        else
        {
            this.setAirTicks(300);
        }

        this.aZ = this.ba;

        if (this.attackTicks > 0)
        {
            --this.attackTicks;
        }

        if (this.hurtTicks > 0)
        {
            --this.hurtTicks;
        }

        if (this.noDamageTicks > 0)
        {
            --this.noDamageTicks;
        }

        if (this.health <= 0)
        {
            this.aP();
        }

        if (this.lastDamageByPlayerTime > 0)
        {
            --this.lastDamageByPlayerTime;
        }
        else
        {
            this.killer = null;
        }

        if (this.g != null && !this.g.isAlive())
        {
            this.g = null;
        }

        if (this.lastDamager != null)
        {
            if (!this.lastDamager.isAlive())
            {
                this.c((EntityLiving) null);
            }
            else if (this.f > 0)
            {
                --this.f;
            }
            else
            {
                this.c((EntityLiving) null);
            }
        }

        this.bx();
        this.aD = this.aC;
        this.ax = this.aw;
        this.az = this.ay;
        this.lastYaw = this.yaw;
        this.lastPitch = this.pitch;
        this.world.methodProfiler.b();
    }

    /**
     * handles entity death timer, experience orb and particle creation
     */
    protected void aP()
    {
        ++this.deathTicks;

        if (this.deathTicks == 20)
        {
            int var1;

            if (!this.world.isStatic && (this.lastDamageByPlayerTime > 0 || this.alwaysGivesExp()) && !this.isBaby())
            {
                var1 = this.getExpValue(this.killer);

                while (var1 > 0)
                {
                    int var2 = EntityExperienceOrb.getOrbValue(var1);
                    var1 -= var2;
                    this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, var2));
                }
            }

            this.die();

            for (var1 = 0; var1 < 20; ++var1)
            {
                double var8 = this.random.nextGaussian() * 0.02D;
                double var4 = this.random.nextGaussian() * 0.02D;
                double var6 = this.random.nextGaussian() * 0.02D;
                this.world.addParticle("explode", this.locX + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, this.locY + (double) (this.random.nextFloat() * this.length), this.locZ + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, var8, var4, var6);
            }
        }
    }

    /**
     * Decrements the entity's air supply when underwater
     */
    protected int g(int par1)
    {
        int var2 = EnchantmentManager.getOxygenEnchantmentLevel(this);
        return var2 > 0 && this.random.nextInt(var2 + 1) > 0 ? par1 : par1 - 1;
    }

    /**
     * Get the experience points the entity currently has.
     */
    protected int getExpValue(EntityHuman par1EntityPlayer)
    {
        return this.bc;
    }

    /**
     * Only use is to identify if class is an instance of player for experience dropping
     */
    protected boolean alwaysGivesExp()
    {
        return false;
    }

    /**
     * Spawns an explosion particle around the Entity's location
     */
    public void aR()
    {
        for (int var1 = 0; var1 < 20; ++var1)
        {
            double var2 = this.random.nextGaussian() * 0.02D;
            double var4 = this.random.nextGaussian() * 0.02D;
            double var6 = this.random.nextGaussian() * 0.02D;
            double var8 = 10.0D;
            this.world.addParticle("explode", this.locX + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width - var2 * var8, this.locY + (double) (this.random.nextFloat() * this.length) - var4 * var8, this.locZ + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width - var6 * var8, var2, var4, var6);
        }
    }

    /**
     * Handles updating while being ridden by an entity
     */
    public void U()
    {
        super.U();
        this.aA = this.aB;
        this.aB = 0.0F;
        this.fallDistance = 0.0F;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();

        if (!this.world.isStatic)
        {
            int var1;

            for (var1 = 0; var1 < 5; ++var1)
            {
                ItemStack var2 = this.getEquipment(var1);

                if (!ItemStack.matches(var2, this.bS[var1]))
                {
                    ((WorldServer)this.world).getTracker().a(this, new Packet5EntityEquipment(this.id, var1, var2));
                    this.bS[var1] = var2 == null ? null : var2.cloneItemStack();
                }
            }

            var1 = this.bJ();

            if (var1 > 0)
            {
                if (this.bk <= 0)
                {
                    this.bk = 20 * (30 - var1);
                }

                --this.bk;

                if (this.bk <= 0)
                {
                    this.r(var1 - 1);
                }
            }
        }

        this.c();
        double var12 = this.locX - this.lastX;
        double var3 = this.locZ - this.lastZ;
        float var5 = (float)(var12 * var12 + var3 * var3);
        float var6 = this.aw;
        float var7 = 0.0F;
        this.aA = this.aB;
        float var8 = 0.0F;

        if (var5 > 0.0025000002F)
        {
            var8 = 1.0F;
            var7 = (float)Math.sqrt((double)var5) * 3.0F;
            var6 = (float)Math.atan2(var3, var12) * 180.0F / (float)Math.PI - 90.0F;
        }

        if (this.aP > 0.0F)
        {
            var6 = this.yaw;
        }

        if (!this.onGround)
        {
            var8 = 0.0F;
        }

        this.aB += (var8 - this.aB) * 0.3F;
        this.world.methodProfiler.a("headTurn");

        if (this.be())
        {
            this.senses.a();
        }
        else
        {
            float var9 = MathHelper.g(var6 - this.aw);
            this.aw += var9 * 0.3F;
            float var10 = MathHelper.g(this.yaw - this.aw);
            boolean var11 = var10 < -90.0F || var10 >= 90.0F;

            if (var10 < -75.0F)
            {
                var10 = -75.0F;
            }

            if (var10 >= 75.0F)
            {
                var10 = 75.0F;
            }

            this.aw = this.yaw - var10;

            if (var10 * var10 > 2500.0F)
            {
                this.aw += var10 * 0.2F;
            }

            if (var11)
            {
                var7 *= -1.0F;
            }
        }

        this.world.methodProfiler.b();
        this.world.methodProfiler.a("rangeChecks");

        while (this.yaw - this.lastYaw < -180.0F)
        {
            this.lastYaw -= 360.0F;
        }

        while (this.yaw - this.lastYaw >= 180.0F)
        {
            this.lastYaw += 360.0F;
        }

        while (this.aw - this.ax < -180.0F)
        {
            this.ax -= 360.0F;
        }

        while (this.aw - this.ax >= 180.0F)
        {
            this.ax += 360.0F;
        }

        while (this.pitch - this.lastPitch < -180.0F)
        {
            this.lastPitch -= 360.0F;
        }

        while (this.pitch - this.lastPitch >= 180.0F)
        {
            this.lastPitch += 360.0F;
        }

        while (this.ay - this.az < -180.0F)
        {
            this.az -= 360.0F;
        }

        while (this.ay - this.az >= 180.0F)
        {
            this.az += 360.0F;
        }

        this.world.methodProfiler.b();
        this.aC += var7;
    }

    /**
     * Heal living entity (param: amount of half-hearts)
     */
    public void heal(int par1)
    {
        if (this.health > 0)
        {
            this.health += par1;

            if (this.health > this.getMaxHealth())
            {
                this.health = this.getMaxHealth();
            }

            this.noDamageTicks = this.maxNoDamageTicks / 2;
        }
    }

    public abstract int getMaxHealth();

    public int getHealth()
    {
        return this.health;
    }

    public void setHealth(int par1)
    {
        this.health = par1;

        if (par1 > this.getMaxHealth())
        {
            par1 = this.getMaxHealth();
        }
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
        else if (this.world.isStatic)
        {
            return false;
        }
        else
        {
            this.bA = 0;

            if (this.health <= 0)
            {
                return false;
            }
            else if (par1DamageSource.k() && this.hasEffect(MobEffectList.FIRE_RESISTANCE))
            {
                return false;
            }
            else
            {
                if ((par1DamageSource == DamageSource.ANVIL || par1DamageSource == DamageSource.FALLING_BLOCK) && this.getEquipment(4) != null)
                {
                    par2 = (int)((float)par2 * 0.55F);
                }

                this.bg = 1.5F;
                boolean var3 = true;

                if ((float)this.noDamageTicks > (float)this.maxNoDamageTicks / 2.0F)
                {
                    if (par2 <= this.lastDamage)
                    {
                        return false;
                    }

                    this.damageEntity(par1DamageSource, par2 - this.lastDamage);
                    this.lastDamage = par2;
                    var3 = false;
                }
                else
                {
                    this.lastDamage = par2;
                    this.aR = this.health;
                    this.noDamageTicks = this.maxNoDamageTicks;
                    this.damageEntity(par1DamageSource, par2);
                    this.hurtTicks = this.aV = 10;
                }

                this.aW = 0.0F;
                Entity var4 = par1DamageSource.getEntity();

                if (var4 != null)
                {
                    if (var4 instanceof EntityLiving)
                    {
                        this.c((EntityLiving) var4);
                    }

                    if (var4 instanceof EntityHuman)
                    {
                        this.lastDamageByPlayerTime = 60;
                        this.killer = (EntityHuman)var4;
                    }
                    else if (var4 instanceof EntityWolf)
                    {
                        EntityWolf var5 = (EntityWolf)var4;

                        if (var5.isTamed())
                        {
                            this.lastDamageByPlayerTime = 60;
                            this.killer = null;
                        }
                    }
                }

                if (var3)
                {
                    this.world.broadcastEntityEffect(this, (byte) 2);

                    if (par1DamageSource != DamageSource.DROWN && par1DamageSource != DamageSource.EXPLOSION2)
                    {
                        this.K();
                    }

                    if (var4 != null)
                    {
                        double var9 = var4.locX - this.locX;
                        double var7;

                        for (var7 = var4.locZ - this.locZ; var9 * var9 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D)
                        {
                            var9 = (Math.random() - Math.random()) * 0.01D;
                        }

                        this.aW = (float)(Math.atan2(var7, var9) * 180.0D / Math.PI) - this.yaw;
                        this.a(var4, par2, var9, var7);
                    }
                    else
                    {
                        this.aW = (float)((int)(Math.random() * 2.0D) * 180);
                    }
                }

                if (this.health <= 0)
                {
                    if (var3)
                    {
                        this.makeSound(this.ba(), this.aX(), this.aV());
                    }

                    this.die(par1DamageSource);
                }
                else if (var3)
                {
                    this.makeSound(this.aZ(), this.aX(), this.aV());
                }

                return true;
            }
        }
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float aV()
    {
        return this.isBaby() ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int aW()
    {
        int var1 = 0;
        ItemStack[] var2 = this.getEquipment();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            ItemStack var5 = var2[var4];

            if (var5 != null && var5.getItem() instanceof ItemArmor)
            {
                int var6 = ((ItemArmor)var5.getItem()).b;
                var1 += var6;
            }
        }

        return var1;
    }

    protected void k(int par1) {}

    /**
     * Reduces damage, depending on armor
     */
    protected int b(DamageSource par1DamageSource, int par2)
    {
        if (!par1DamageSource.ignoresArmor())
        {
            int var3 = 25 - this.aW();
            int var4 = par2 * var3 + this.aS;
            this.k(par2);
            par2 = var4 / 25;
            this.aS = var4 % 25;
        }

        return par2;
    }

    /**
     * Reduces damage, depending on potions
     */
    protected int c(DamageSource par1DamageSource, int par2)
    {
        if (this.hasEffect(MobEffectList.RESISTANCE))
        {
            int var3 = (this.getEffect(MobEffectList.RESISTANCE).getAmplifier() + 1) * 5;
            int var4 = 25 - var3;
            int var5 = par2 * var4 + this.aS;
            par2 = var5 / 25;
            this.aS = var5 % 25;
        }

        return par2;
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void d(DamageSource par1DamageSource, int par2)
    {
        if (!this.isInvulnerable())
        {
            par2 = this.b(par1DamageSource, par2);
            par2 = this.c(par1DamageSource, par2);
            this.health -= par2;
        }
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float aX()
    {
        return 1.0F;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "damage.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "damage.hit";
    }

    /**
     * knocks back this entity
     */
    public void a(Entity par1Entity, int par2, double par3, double par5)
    {
        this.am = true;
        float var7 = MathHelper.sqrt(par3 * par3 + par5 * par5);
        float var8 = 0.4F;
        this.motX /= 2.0D;
        this.motY /= 2.0D;
        this.motZ /= 2.0D;
        this.motX -= par3 / (double)var7 * (double)var8;
        this.motY += (double)var8;
        this.motZ -= par5 / (double)var7 * (double)var8;

        if (this.motY > 0.4000000059604645D)
        {
            this.motY = 0.4000000059604645D;
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource par1DamageSource)
    {
        Entity var2 = par1DamageSource.getEntity();

        if (this.aK >= 0 && var2 != null)
        {
            var2.c(this, this.aK);
        }

        if (var2 != null)
        {
            var2.a(this);
        }

        this.bb = true;

        if (!this.world.isStatic)
        {
            int var3 = 0;

            if (var2 instanceof EntityHuman)
            {
                var3 = EnchantmentManager.getBonusMonsterLootEnchantmentLevel((EntityLiving) var2);
            }

            if (!this.isBaby() && this.world.getGameRules().getBoolean("doMobLoot"))
            {
                this.dropDeathLoot(this.lastDamageByPlayerTime > 0, var3);
                this.dropEquipment(this.lastDamageByPlayerTime > 0, var3);

                if (this.lastDamageByPlayerTime > 0)
                {
                    int var4 = this.random.nextInt(200) - var3;

                    if (var4 < 5)
                    {
                        this.l(var4 <= 0 ? 1 : 0);
                    }
                }
            }
        }

        this.world.broadcastEntityEffect(this, (byte) 3);
    }

    protected void l(int par1) {}

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.getLootId();

        if (var3 > 0)
        {
            int var4 = this.random.nextInt(3);

            if (par2 > 0)
            {
                var4 += this.random.nextInt(par2 + 1);
            }

            for (int var5 = 0; var5 < var4; ++var5)
            {
                this.b(var3, 1);
            }
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return 0;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1)
    {
        super.a(par1);
        int var2 = MathHelper.f(par1 - 3.0F);

        if (var2 > 0)
        {
            if (var2 > 4)
            {
                this.makeSound("damage.fallbig", 1.0F, 1.0F);
            }
            else
            {
                this.makeSound("damage.fallsmall", 1.0F, 1.0F);
            }

            this.damageEntity(DamageSource.FALL, var2);
            int var3 = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.locY - 0.20000000298023224D - (double) this.height), MathHelper.floor(this.locZ));

            if (var3 > 0)
            {
                StepSound var4 = Block.byId[var3].stepSound;
                this.makeSound(var4.getStepSound(), var4.getVolume1() * 0.5F, var4.getVolume2() * 0.75F);
            }
        }
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void e(float par1, float par2)
    {
        double var9;

        if (this.H() && (!(this instanceof EntityHuman) || !((EntityHuman)this).abilities.isFlying))
        {
            var9 = this.locY;
            this.a(par1, par2, this.be() ? 0.04F : 0.02F);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.800000011920929D;
            this.motY *= 0.800000011920929D;
            this.motZ *= 0.800000011920929D;
            this.motY -= 0.02D;

            if (this.positionChanged && this.c(this.motX, this.motY + 0.6000000238418579D - this.locY + var9, this.motZ))
            {
                this.motY = 0.30000001192092896D;
            }
        }
        else if (this.J() && (!(this instanceof EntityHuman) || !((EntityHuman)this).abilities.isFlying))
        {
            var9 = this.locY;
            this.a(par1, par2, 0.02F);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.5D;
            this.motY *= 0.5D;
            this.motZ *= 0.5D;
            this.motY -= 0.02D;

            if (this.positionChanged && this.c(this.motX, this.motY + 0.6000000238418579D - this.locY + var9, this.motZ))
            {
                this.motY = 0.30000001192092896D;
            }
        }
        else
        {
            float var3 = 0.91F;

            if (this.onGround)
            {
                var3 = 0.54600006F;
                int var4 = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));

                if (var4 > 0)
                {
                    var3 = Block.byId[var4].frictionFactor * 0.91F;
                }
            }

            float var8 = 0.16277136F / (var3 * var3 * var3);
            float var5;

            if (this.onGround)
            {
                if (this.be())
                {
                    var5 = this.aF();
                }
                else
                {
                    var5 = this.aM;
                }

                var5 *= var8;
            }
            else
            {
                var5 = this.aN;
            }

            this.a(par1, par2, var5);
            var3 = 0.91F;

            if (this.onGround)
            {
                var3 = 0.54600006F;
                int var6 = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));

                if (var6 > 0)
                {
                    var3 = Block.byId[var6].frictionFactor * 0.91F;
                }
            }

            if (this.g_())
            {
                float var10 = 0.15F;

                if (this.motX < (double)(-var10))
                {
                    this.motX = (double)(-var10);
                }

                if (this.motX > (double)var10)
                {
                    this.motX = (double)var10;
                }

                if (this.motZ < (double)(-var10))
                {
                    this.motZ = (double)(-var10);
                }

                if (this.motZ > (double)var10)
                {
                    this.motZ = (double)var10;
                }

                this.fallDistance = 0.0F;

                if (this.motY < -0.15D)
                {
                    this.motY = -0.15D;
                }

                boolean var7 = this.isSneaking() && this instanceof EntityHuman;

                if (var7 && this.motY < 0.0D)
                {
                    this.motY = 0.0D;
                }
            }

            this.move(this.motX, this.motY, this.motZ);

            if (this.positionChanged && this.g_())
            {
                this.motY = 0.2D;
            }

            if (this.world.isStatic && (!this.world.isLoaded((int) this.locX, 0, (int) this.locZ) || !this.world.getChunkAtWorldCoords((int) this.locX, (int) this.locZ).d))
            {
                if (this.locY > 0.0D)
                {
                    this.motY = -0.1D;
                }
                else
                {
                    this.motY = 0.0D;
                }
            }
            else
            {
                this.motY -= 0.08D;
            }

            this.motY *= 0.9800000190734863D;
            this.motX *= (double)var3;
            this.motZ *= (double)var3;
        }

        this.bf = this.bg;
        var9 = this.locX - this.lastX;
        double var12 = this.locZ - this.lastZ;
        float var11 = MathHelper.sqrt(var9 * var9 + var12 * var12) * 4.0F;

        if (var11 > 1.0F)
        {
            var11 = 1.0F;
        }

        this.bg += (var11 - this.bg) * 0.4F;
        this.bh += this.bg;
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean g_()
    {
        int var1 = MathHelper.floor(this.locX);
        int var2 = MathHelper.floor(this.boundingBox.b);
        int var3 = MathHelper.floor(this.locZ);
        int var4 = this.world.getTypeId(var1, var2, var3);
        return var4 == Block.LADDER.id || var4 == Block.VINE.id;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        if (this.health < -32768)
        {
            this.health = -32768;
        }

        par1NBTTagCompound.setShort("Health", (short)this.health);
        par1NBTTagCompound.setShort("HurtTime", (short)this.hurtTicks);
        par1NBTTagCompound.setShort("DeathTime", (short)this.deathTicks);
        par1NBTTagCompound.setShort("AttackTime", (short)this.attackTicks);
        par1NBTTagCompound.setBoolean("CanPickUpLoot", this.canPickUpLoot);
        par1NBTTagCompound.setBoolean("PersistenceRequired", this.persistent);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.equipment.length; ++var3)
        {
            NBTTagCompound var4 = new NBTTagCompound();

            if (this.equipment[var3] != null)
            {
                this.equipment[var3].save(var4);
            }

            var2.add(var4);
        }

        par1NBTTagCompound.set("Equipment", var2);
        NBTTagList var6;

        if (!this.effects.isEmpty())
        {
            var6 = new NBTTagList();
            Iterator var7 = this.effects.values().iterator();

            while (var7.hasNext())
            {
                MobEffect var5 = (MobEffect)var7.next();
                var6.add(var5.a(new NBTTagCompound()));
            }

            par1NBTTagCompound.set("ActiveEffects", var6);
        }

        var6 = new NBTTagList();

        for (int var8 = 0; var8 < this.dropChances.length; ++var8)
        {
            var6.add(new NBTTagFloat(var8 + "", this.dropChances[var8]));
        }

        par1NBTTagCompound.set("DropChances", var6);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.health = par1NBTTagCompound.getShort("Health");

        if (!par1NBTTagCompound.hasKey("Health"))
        {
            this.health = this.getMaxHealth();
        }

        this.hurtTicks = par1NBTTagCompound.getShort("HurtTime");
        this.deathTicks = par1NBTTagCompound.getShort("DeathTime");
        this.attackTicks = par1NBTTagCompound.getShort("AttackTime");
        this.canPickUpLoot = par1NBTTagCompound.getBoolean("CanPickUpLoot");
        this.persistent = par1NBTTagCompound.getBoolean("PersistenceRequired");
        NBTTagList var2;
        int var3;

        if (par1NBTTagCompound.hasKey("Equipment"))
        {
            var2 = par1NBTTagCompound.getList("Equipment");

            for (var3 = 0; var3 < this.equipment.length; ++var3)
            {
                this.equipment[var3] = ItemStack.a((NBTTagCompound) var2.get(var3));
            }
        }

        if (par1NBTTagCompound.hasKey("ActiveEffects"))
        {
            var2 = par1NBTTagCompound.getList("ActiveEffects");

            for (var3 = 0; var3 < var2.size(); ++var3)
            {
                NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
                MobEffect var5 = MobEffect.b(var4);
                this.effects.put(Integer.valueOf(var5.getEffectId()), var5);
            }
        }

        if (par1NBTTagCompound.hasKey("DropChances"))
        {
            var2 = par1NBTTagCompound.getList("DropChances");

            for (var3 = 0; var3 < var2.size(); ++var3)
            {
                this.dropChances[var3] = ((NBTTagFloat)var2.get(var3)).data;
            }
        }
    }

    /**
     * Checks whether target entity is alive.
     */
    public boolean isAlive()
    {
        return !this.dead && this.health > 0;
    }

    public boolean bc()
    {
        return false;
    }

    public void f(float par1)
    {
        this.bC = par1;
    }

    public void e(boolean par1)
    {
        this.bE = par1;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        if (this.bU > 0)
        {
            --this.bU;
        }

        if (this.bs > 0)
        {
            double var1 = this.locX + (this.bt - this.locX) / (double)this.bs;
            double var3 = this.locY + (this.bu - this.locY) / (double)this.bs;
            double var5 = this.locZ + (this.bv - this.locZ) / (double)this.bs;
            double var7 = MathHelper.g(this.bw - (double) this.yaw);
            this.yaw = (float)((double)this.yaw + var7 / (double)this.bs);
            this.pitch = (float)((double)this.pitch + (this.bx - (double)this.pitch) / (double)this.bs);
            --this.bs;
            this.setPosition(var1, var3, var5);
            this.b(this.yaw, this.pitch);
        }

        if (Math.abs(this.motX) < 0.005D)
        {
            this.motX = 0.0D;
        }

        if (Math.abs(this.motY) < 0.005D)
        {
            this.motY = 0.0D;
        }

        if (Math.abs(this.motZ) < 0.005D)
        {
            this.motZ = 0.0D;
        }

        this.world.methodProfiler.a("ai");

        if (this.bg())
        {
            this.bE = false;
            this.bB = 0.0F;
            this.bC = 0.0F;
            this.bD = 0.0F;
        }
        else if (this.bf())
        {
            if (this.be())
            {
                this.world.methodProfiler.a("newAi");
                this.bl();
                this.world.methodProfiler.b();
            }
            else
            {
                this.world.methodProfiler.a("oldAi");
                this.bn();
                this.world.methodProfiler.b();
                this.ay = this.yaw;
            }
        }

        this.world.methodProfiler.b();
        this.world.methodProfiler.a("jump");

        if (this.bE)
        {
            if (!this.H() && !this.J())
            {
                if (this.onGround && this.bU == 0)
                {
                    this.bi();
                    this.bU = 10;
                }
            }
            else
            {
                this.motY += 0.03999999910593033D;
            }
        }
        else
        {
            this.bU = 0;
        }

        this.world.methodProfiler.b();
        this.world.methodProfiler.a("travel");
        this.bB *= 0.98F;
        this.bC *= 0.98F;
        this.bD *= 0.9F;
        float var11 = this.aM;
        this.aM *= this.bB();
        this.e(this.bB, this.bC);
        this.aM = var11;
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("push");

        if (!this.world.isStatic)
        {
            this.bd();
        }

        this.world.methodProfiler.b();
        this.world.methodProfiler.a("looting");

        if (!this.world.isStatic && this.canPickUpLoot && !this.bb && this.world.getGameRules().getBoolean("mobGriefing"))
        {
            List var2 = this.world.a(EntityItem.class, this.boundingBox.grow(1.0D, 0.0D, 1.0D));
            Iterator var12 = var2.iterator();

            while (var12.hasNext())
            {
                EntityItem var4 = (EntityItem)var12.next();

                if (!var4.dead && var4.itemStack != null)
                {
                    ItemStack var13 = var4.itemStack;
                    int var6 = b(var13);

                    if (var6 > -1)
                    {
                        boolean var14 = true;
                        ItemStack var8 = this.getEquipment(var6);

                        if (var8 != null)
                        {
                            if (var6 == 0)
                            {
                                if (var13.getItem() instanceof ItemSword && !(var8.getItem() instanceof ItemSword))
                                {
                                    var14 = true;
                                }
                                else if (var13.getItem() instanceof ItemSword && var8.getItem() instanceof ItemSword)
                                {
                                    ItemSword var9 = (ItemSword)var13.getItem();
                                    ItemSword var10 = (ItemSword)var8.getItem();

                                    if (var9.g() == var10.g())
                                    {
                                        var14 = var13.getData() > var8.getData() || var13.hasTag() && !var8.hasTag();
                                    }
                                    else
                                    {
                                        var14 = var9.g() > var10.g();
                                    }
                                }
                                else
                                {
                                    var14 = false;
                                }
                            }
                            else if (var13.getItem() instanceof ItemArmor && !(var8.getItem() instanceof ItemArmor))
                            {
                                var14 = true;
                            }
                            else if (var13.getItem() instanceof ItemArmor && var8.getItem() instanceof ItemArmor)
                            {
                                ItemArmor var15 = (ItemArmor)var13.getItem();
                                ItemArmor var16 = (ItemArmor)var8.getItem();

                                if (var15.b == var16.b)
                                {
                                    var14 = var13.getData() > var8.getData() || var13.hasTag() && !var8.hasTag();
                                }
                                else
                                {
                                    var14 = var15.b > var16.b;
                                }
                            }
                            else
                            {
                                var14 = false;
                            }
                        }

                        if (var14)
                        {
                            if (var8 != null && this.random.nextFloat() - 0.1F < this.dropChances[var6])
                            {
                                this.a(var8, 0.0F);
                            }

                            this.setEquipment(var6, var13);
                            this.dropChances[var6] = 2.0F;
                            this.persistent = true;
                            this.receive(var4, 1);
                            var4.die();
                        }
                    }
                }
            }
        }

        this.world.methodProfiler.b();
    }

    protected void bd()
    {
        List var1 = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        if (var1 != null && !var1.isEmpty())
        {
            for (int var2 = 0; var2 < var1.size(); ++var2)
            {
                Entity var3 = (Entity)var1.get(var2);

                if (var3.M())
                {
                    this.o(var3);
                }
            }
        }
    }

    protected void o(Entity par1Entity)
    {
        par1Entity.collide(this);
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean be()
    {
        return false;
    }

    /**
     * Returns whether the entity is in a local (client) world
     */
    protected boolean bf()
    {
        return !this.world.isStatic;
    }

    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean bg()
    {
        return this.health <= 0;
    }

    public boolean bh()
    {
        return false;
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void bi()
    {
        this.motY = 0.41999998688697815D;

        if (this.hasEffect(MobEffectList.JUMP))
        {
            this.motY += (double)((float)(this.getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F);
        }

        if (this.isSprinting())
        {
            float var1 = this.yaw * 0.017453292F;
            this.motX -= (double)(MathHelper.sin(var1) * 0.2F);
            this.motZ += (double)(MathHelper.cos(var1) * 0.2F);
        }

        this.am = true;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean bj()
    {
        return true;
    }

    /**
     * Makes the entity despawn if requirements are reached
     */
    protected void bk()
    {
        if (!this.persistent)
        {
            EntityHuman var1 = this.world.findNearbyPlayer(this, -1.0D);

            if (var1 != null)
            {
                double var2 = var1.locX - this.locX;
                double var4 = var1.locY - this.locY;
                double var6 = var1.locZ - this.locZ;
                double var8 = var2 * var2 + var4 * var4 + var6 * var6;

                if (this.bj() && var8 > 16384.0D)
                {
                    this.die();
                }

                if (this.bA > 600 && this.random.nextInt(800) == 0 && var8 > 1024.0D && this.bj())
                {
                    this.die();
                }
                else if (var8 < 1024.0D)
                {
                    this.bA = 0;
                }
            }
        }
    }

    protected void bl()
    {
        ++this.bA;
        this.world.methodProfiler.a("checkDespawn");
        this.bk();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("sensing");
        this.bN.a();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("targetSelector");
        this.targetSelector.a();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("goalSelector");
        this.goalSelector.a();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("navigation");
        this.navigation.e();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("mob tick");
        this.bm();
        this.world.methodProfiler.b();
        this.world.methodProfiler.a("controls");
        this.world.methodProfiler.a("move");
        this.moveController.c();
        this.world.methodProfiler.c("look");
        this.lookController.a();
        this.world.methodProfiler.c("jump");
        this.jumpController.b();
        this.world.methodProfiler.b();
        this.world.methodProfiler.b();
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void bm() {}

    protected void bn()
    {
        ++this.bA;
        this.bk();
        this.bB = 0.0F;
        this.bC = 0.0F;
        float var1 = 8.0F;

        if (this.random.nextFloat() < 0.02F)
        {
            EntityHuman var2 = this.world.findNearbyPlayer(this, (double) var1);

            if (var2 != null)
            {
                this.bV = var2;
                this.bH = 10 + this.random.nextInt(20);
            }
            else
            {
                this.bD = (this.random.nextFloat() - 0.5F) * 20.0F;
            }
        }

        if (this.bV != null)
        {
            this.a(this.bV, 10.0F, (float) this.bp());

            if (this.bH-- <= 0 || this.bV.dead || this.bV.e(this) > (double)(var1 * var1))
            {
                this.bV = null;
            }
        }
        else
        {
            if (this.random.nextFloat() < 0.05F)
            {
                this.bD = (this.random.nextFloat() - 0.5F) * 20.0F;
            }

            this.yaw += this.bD;
            this.pitch = this.bF;
        }

        boolean var4 = this.H();
        boolean var3 = this.J();

        if (var4 || var3)
        {
            this.bE = this.random.nextFloat() < 0.8F;
        }
    }

    /**
     * Updates the arm swing progress counters and animation progress
     */
    protected void bo()
    {
        int var1 = this.h();

        if (this.bp)
        {
            ++this.bq;

            if (this.bq >= var1)
            {
                this.bq = 0;
                this.bp = false;
            }
        }
        else
        {
            this.bq = 0;
        }

        this.aP = (float)this.bq / (float)var1;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int bp()
    {
        return 40;
    }

    /**
     * Changes pitch and yaw so that the entity calling the function is facing the entity provided as an argument.
     */
    public void a(Entity par1Entity, float par2, float par3)
    {
        double var4 = par1Entity.locX - this.locX;
        double var8 = par1Entity.locZ - this.locZ;
        double var6;

        if (par1Entity instanceof EntityLiving)
        {
            EntityLiving var10 = (EntityLiving)par1Entity;
            var6 = this.locY + (double)this.getHeadHeight() - (var10.locY + (double)var10.getHeadHeight());
        }
        else
        {
            var6 = (par1Entity.boundingBox.b + par1Entity.boundingBox.e) / 2.0D - (this.locY + (double)this.getHeadHeight());
        }

        double var14 = (double) MathHelper.sqrt(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float)(-(Math.atan2(var6, var14) * 180.0D / Math.PI));
        this.pitch = -this.b(this.pitch, var13, par3);
        this.yaw = this.b(this.yaw, var12, par2);
    }

    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
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

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        return this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox);
    }

    /**
     * sets the dead flag. Used when you fall off the bottom of the world.
     */
    protected void C()
    {
        this.damageEntity(DamageSource.OUT_OF_WORLD, 4);
    }

    /**
     * returns a (normalized) vector of where this entity is looking
     */
    public Vec3D Z()
    {
        return this.i(1.0F);
    }

    /**
     * interpolated look vector
     */
    public Vec3D i(float par1)
    {
        float var2;
        float var3;
        float var4;
        float var5;

        if (par1 == 1.0F)
        {
            var2 = MathHelper.cos(-this.yaw * 0.017453292F - (float) Math.PI);
            var3 = MathHelper.sin(-this.yaw * 0.017453292F - (float) Math.PI);
            var4 = -MathHelper.cos(-this.pitch * 0.017453292F);
            var5 = MathHelper.sin(-this.pitch * 0.017453292F);
            return this.world.getVec3DPool().create((double) (var3 * var4), (double) var5, (double) (var2 * var4));
        }
        else
        {
            var2 = this.lastPitch + (this.pitch - this.lastPitch) * par1;
            var3 = this.lastYaw + (this.yaw - this.lastYaw) * par1;
            var4 = MathHelper.cos(-var3 * 0.017453292F - (float) Math.PI);
            var5 = MathHelper.sin(-var3 * 0.017453292F - (float) Math.PI);
            float var6 = -MathHelper.cos(-var2 * 0.017453292F);
            float var7 = MathHelper.sin(-var2 * 0.017453292F);
            return this.world.getVec3DPool().create((double) (var5 * var6), (double) var7, (double) (var4 * var6));
        }
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int bv()
    {
        return 4;
    }

    /**
     * Returns whether player is sleeping or not
     */
    public boolean isSleeping()
    {
        return false;
    }

    protected void bx()
    {
        Iterator var1 = this.effects.keySet().iterator();

        while (var1.hasNext())
        {
            Integer var2 = (Integer)var1.next();
            MobEffect var3 = (MobEffect)this.effects.get(var2);

            if (!var3.tick(this))
            {
                if (!this.world.isStatic)
                {
                    var1.remove();
                    this.c(var3);
                }
            }
            else if (var3.getDuration() % 600 == 0)
            {
                this.b(var3);
            }
        }

        int var11;

        if (this.updateEffects)
        {
            if (!this.world.isStatic)
            {
                if (this.effects.isEmpty())
                {
                    this.datawatcher.watch(9, Byte.valueOf((byte) 0));
                    this.datawatcher.watch(8, Integer.valueOf(0));
                    this.setInvisible(false);
                }
                else
                {
                    var11 = PotionBrewer.a(this.effects.values());
                    this.datawatcher.watch(9, Byte.valueOf((byte) (PotionBrewer.b(this.effects.values()) ? 1 : 0)));
                    this.datawatcher.watch(8, Integer.valueOf(var11));
                    this.setInvisible(this.hasEffect(MobEffectList.INVISIBILITY.id));
                }
            }

            this.updateEffects = false;
        }

        var11 = this.datawatcher.getInt(8);
        boolean var12 = this.datawatcher.getByte(9) > 0;

        if (var11 > 0)
        {
            boolean var4 = false;

            if (!this.isInvisible())
            {
                var4 = this.random.nextBoolean();
            }
            else
            {
                var4 = this.random.nextInt(15) == 0;
            }

            if (var12)
            {
                var4 &= this.random.nextInt(5) == 0;
            }

            if (var4 && var11 > 0)
            {
                double var5 = (double)(var11 >> 16 & 255) / 255.0D;
                double var7 = (double)(var11 >> 8 & 255) / 255.0D;
                double var9 = (double)(var11 >> 0 & 255) / 255.0D;
                this.world.addParticle(var12 ? "mobSpellAmbient" : "mobSpell", this.locX + (this.random.nextDouble() - 0.5D) * (double) this.width, this.locY + this.random.nextDouble() * (double) this.length - (double) this.height, this.locZ + (this.random.nextDouble() - 0.5D) * (double) this.width, var5, var7, var9);
            }
        }
    }

    public void by()
    {
        Iterator var1 = this.effects.keySet().iterator();

        while (var1.hasNext())
        {
            Integer var2 = (Integer)var1.next();
            MobEffect var3 = (MobEffect)this.effects.get(var2);

            if (!this.world.isStatic)
            {
                var1.remove();
                this.c(var3);
            }
        }
    }

    public Collection getEffects()
    {
        return this.effects.values();
    }

    public boolean hasEffect(int par1)
    {
        return this.effects.containsKey(Integer.valueOf(par1));
    }

    public boolean hasEffect(MobEffectList par1Potion)
    {
        return this.effects.containsKey(Integer.valueOf(par1Potion.id));
    }

    /**
     * returns the PotionEffect for the supplied Potion if it is active, null otherwise.
     */
    public MobEffect getEffect(MobEffectList par1Potion)
    {
        return (MobEffect)this.effects.get(Integer.valueOf(par1Potion.id));
    }

    /**
     * adds a PotionEffect to the entity
     */
    public void addEffect(MobEffect par1PotionEffect)
    {
        if (this.e(par1PotionEffect))
        {
            if (this.effects.containsKey(Integer.valueOf(par1PotionEffect.getEffectId())))
            {
                ((MobEffect)this.effects.get(Integer.valueOf(par1PotionEffect.getEffectId()))).a(par1PotionEffect);
                this.b((MobEffect) this.effects.get(Integer.valueOf(par1PotionEffect.getEffectId())));
            }
            else
            {
                this.effects.put(Integer.valueOf(par1PotionEffect.getEffectId()), par1PotionEffect);
                this.a(par1PotionEffect);
            }
        }
    }

    public boolean e(MobEffect par1PotionEffect)
    {
        if (this.getMonsterType() == EnumMonsterType.UNDEAD)
        {
            int var2 = par1PotionEffect.getEffectId();

            if (var2 == MobEffectList.REGENERATION.id || var2 == MobEffectList.POISON.id)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if this entity is undead.
     */
    public boolean bA()
    {
        return this.getMonsterType() == EnumMonsterType.UNDEAD;
    }

    /**
     * Remove the specified potion effect from this entity.
     */
    public void o(int par1)
    {
        MobEffect var2 = (MobEffect)this.effects.remove(Integer.valueOf(par1));

        if (var2 != null)
        {
            this.c(var2);
        }
    }

    protected void a(MobEffect par1PotionEffect)
    {
        this.updateEffects = true;
    }

    protected void b(MobEffect par1PotionEffect)
    {
        this.updateEffects = true;
    }

    protected void c(MobEffect par1PotionEffect)
    {
        this.updateEffects = true;
    }

    /**
     * This method returns a value to be applied directly to entity speed, this factor is less than 1 when a slowdown
     * potion effect is applied, more than 1 when a haste potion effect is applied and 2 for fleeing entities.
     */
    public float bB()
    {
        float var1 = 1.0F;

        if (this.hasEffect(MobEffectList.FASTER_MOVEMENT))
        {
            var1 *= 1.0F + 0.2F * (float)(this.getEffect(MobEffectList.FASTER_MOVEMENT).getAmplifier() + 1);
        }

        if (this.hasEffect(MobEffectList.SLOWER_MOVEMENT))
        {
            var1 *= 1.0F - 0.15F * (float)(this.getEffect(MobEffectList.SLOWER_MOVEMENT).getAmplifier() + 1);
        }

        return var1;
    }

    /**
     * Sets the position of the entity and updates the 'last' variables
     */
    public void enderTeleportTo(double par1, double par3, double par5)
    {
        this.setPositionRotation(par1, par3, par5, this.yaw, this.pitch);
    }

    /**
     * If Animal, checks if the age timer is negative
     */
    public boolean isBaby()
    {
        return false;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumMonsterType getMonsterType()
    {
        return EnumMonsterType.UNDEFINED;
    }

    /**
     * Renders broken item particles using the given ItemStack
     */
    public void a(ItemStack par1ItemStack)
    {
        this.makeSound("random.break", 0.8F, 0.8F + this.world.random.nextFloat() * 0.4F);

        for (int var2 = 0; var2 < 5; ++var2)
        {
            Vec3D var3 = this.world.getVec3DPool().create(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            var3.a(-this.pitch * (float) Math.PI / 180.0F);
            var3.b(-this.yaw * (float) Math.PI / 180.0F);
            Vec3D var4 = this.world.getVec3DPool().create(((double) this.random.nextFloat() - 0.5D) * 0.3D, (double) (-this.random.nextFloat()) * 0.6D - 0.3D, 0.6D);
            var4.a(-this.pitch * (float) Math.PI / 180.0F);
            var4.b(-this.yaw * (float) Math.PI / 180.0F);
            var4 = var4.add(this.locX, this.locY + (double) this.getHeadHeight(), this.locZ);
            this.world.addParticle("iconcrack_" + par1ItemStack.getItem().id, var4.c, var4.d, var4.e, var3.c, var3.d + 0.05D, var3.e);
        }
    }

    public int as()
    {
        if (this.aG() == null)
        {
            return 3;
        }
        else
        {
            int var1 = (int)((float)this.health - (float)this.getMaxHealth() * 0.33F);
            var1 -= (3 - this.world.difficulty) * 4;

            if (var1 < 0)
            {
                var1 = 0;
            }

            return var1 + 3;
        }
    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack bD()
    {
        return this.equipment[0];
    }

    /**
     * 0: Tool in Hand; 1-4: Armor
     */
    public ItemStack getEquipment(int par1)
    {
        return this.equipment[par1];
    }

    public ItemStack q(int par1)
    {
        return this.equipment[par1 + 1];
    }

    /**
     * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is armor. Params: Item, slot
     */
    public void setEquipment(int par1, ItemStack par2ItemStack)
    {
        this.equipment[par1] = par2ItemStack;
    }

    /**
     * returns the inventory of this entity (only used in EntityPlayerMP it seems)
     */
    public ItemStack[] getEquipment()
    {
        return this.equipment;
    }

    /**
     * Drop the equipment for this entity.
     */
    protected void dropEquipment(boolean par1, int par2)
    {
        for (int var3 = 0; var3 < this.getEquipment().length; ++var3)
        {
            ItemStack var4 = this.getEquipment(var3);
            boolean var5 = this.dropChances[var3] > 1.0F;

            if (var4 != null && (par1 || var5) && this.random.nextFloat() - (float)par2 * 0.01F < this.dropChances[var3])
            {
                if (!var5 && var4.f())
                {
                    int var6 = Math.max(var4.k() - 25, 1);
                    int var7 = var4.k() - this.random.nextInt(this.random.nextInt(var6) + 1);

                    if (var7 > var6)
                    {
                        var7 = var6;
                    }

                    if (var7 < 1)
                    {
                        var7 = 1;
                    }

                    var4.setData(var7);
                }

                this.a(var4, 0.0F);
            }
        }
    }

    protected void bE()
    {
        if (this.random.nextFloat() < d[this.world.difficulty])
        {
            int var1 = this.random.nextInt(2);
            float var2 = this.world.difficulty == 3 ? 0.1F : 0.25F;

            if (this.random.nextFloat() < 0.07F)
            {
                ++var1;
            }

            if (this.random.nextFloat() < 0.07F)
            {
                ++var1;
            }

            if (this.random.nextFloat() < 0.07F)
            {
                ++var1;
            }

            for (int var3 = 3; var3 >= 0; --var3)
            {
                ItemStack var4 = this.q(var3);

                if (var3 < 3 && this.random.nextFloat() < var2)
                {
                    break;
                }

                if (var4 == null)
                {
                    Item var5 = a(var3 + 1, var1);

                    if (var5 != null)
                    {
                        this.setEquipment(var3 + 1, new ItemStack(var5));
                    }
                }
            }
        }
    }

    /**
     * Called whenever an item is picked up from walking over it. Args: pickedUpEntity, stackSize
     */
    public void receive(Entity par1Entity, int par2)
    {
        if (!par1Entity.dead && !this.world.isStatic)
        {
            EntityTracker var3 = ((WorldServer)this.world).getTracker();

            if (par1Entity instanceof EntityItem)
            {
                var3.a(par1Entity, new Packet22Collect(par1Entity.id, this.id));
            }

            if (par1Entity instanceof EntityArrow)
            {
                var3.a(par1Entity, new Packet22Collect(par1Entity.id, this.id));
            }

            if (par1Entity instanceof EntityExperienceOrb)
            {
                var3.a(par1Entity, new Packet22Collect(par1Entity.id, this.id));
            }
        }
    }

    public static int b(ItemStack par0ItemStack)
    {
        if (par0ItemStack.id != Block.PUMPKIN.id && par0ItemStack.id != Item.SKULL.id)
        {
            if (par0ItemStack.getItem() instanceof ItemArmor)
            {
                switch (((ItemArmor)par0ItemStack.getItem()).a)
                {
                    case 0:
                        return 4;

                    case 1:
                        return 3;

                    case 2:
                        return 2;

                    case 3:
                        return 1;
                }
            }

            return 0;
        }
        else
        {
            return 4;
        }
    }

    /**
     * Params: Armor slot, Item tier
     */
    public static Item a(int par0, int par1)
    {
        switch (par0)
        {
            case 4:
                if (par1 == 0)
                {
                    return Item.LEATHER_HELMET;
                }
                else if (par1 == 1)
                {
                    return Item.GOLD_HELMET;
                }
                else if (par1 == 2)
                {
                    return Item.CHAINMAIL_HELMET;
                }
                else if (par1 == 3)
                {
                    return Item.IRON_HELMET;
                }
                else if (par1 == 4)
                {
                    return Item.DIAMOND_HELMET;
                }

            case 3:
                if (par1 == 0)
                {
                    return Item.LEATHER_CHESTPLATE;
                }
                else if (par1 == 1)
                {
                    return Item.GOLD_CHESTPLATE;
                }
                else if (par1 == 2)
                {
                    return Item.CHAINMAIL_CHESTPLATE;
                }
                else if (par1 == 3)
                {
                    return Item.IRON_CHESTPLATE;
                }
                else if (par1 == 4)
                {
                    return Item.DIAMOND_CHESTPLATE;
                }

            case 2:
                if (par1 == 0)
                {
                    return Item.LEATHER_LEGGINGS;
                }
                else if (par1 == 1)
                {
                    return Item.GOLD_LEGGINGS;
                }
                else if (par1 == 2)
                {
                    return Item.CHAINMAIL_LEGGINGS;
                }
                else if (par1 == 3)
                {
                    return Item.IRON_LEGGINGS;
                }
                else if (par1 == 4)
                {
                    return Item.DIAMOND_LEGGINGS;
                }

            case 1:
                if (par1 == 0)
                {
                    return Item.LEATHER_BOOTS;
                }
                else if (par1 == 1)
                {
                    return Item.GOLD_BOOTS;
                }
                else if (par1 == 2)
                {
                    return Item.CHAINMAIL_BOOTS;
                }
                else if (par1 == 3)
                {
                    return Item.IRON_BOOTS;
                }
                else if (par1 == 4)
                {
                    return Item.DIAMOND_BOOTS;
                }

            default:
                return null;
        }
    }

    protected void bF()
    {
        if (this.bD() != null && this.random.nextFloat() < b[this.world.difficulty])
        {
            EnchantmentManager.a(this.random, this.bD(), 5);
        }

        for (int var1 = 0; var1 < 4; ++var1)
        {
            ItemStack var2 = this.q(var1);

            if (var2 != null && this.random.nextFloat() < c[this.world.difficulty])
            {
                EnchantmentManager.a(this.random, var2, 5);
            }
        }
    }

    /**
     * Initialize this creature.
     */
    public void bG() {}

    /**
     * Returns an integer indicating the end point of the swing animation, used by {@link #aP} to provide a
     * progress indicator. Takes dig speed enchantments into account.
     */
    private int h()
    {
        return this.hasEffect(MobEffectList.FASTER_DIG) ? 6 - (1 + this.getEffect(MobEffectList.FASTER_DIG).getAmplifier()) * 1 : (this.hasEffect(MobEffectList.SLOWER_DIG) ? 6 + (1 + this.getEffect(MobEffectList.SLOWER_DIG).getAmplifier()) * 2 : 6);
    }

    /**
     * Swings the item the player is holding.
     */
    public void bH()
    {
        if (!this.bp || this.bq >= this.h() / 2 || this.bq < 0)
        {
            this.bq = -1;
            this.bp = true;

            if (this.world instanceof WorldServer)
            {
                ((WorldServer)this.world).getTracker().a(this, new Packet18ArmAnimation(this, 1));
            }
        }
    }

    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean bI()
    {
        return false;
    }

    public final int bJ()
    {
        return this.datawatcher.getByte(10);
    }

    public final void r(int par1)
    {
        this.datawatcher.watch(10, Byte.valueOf((byte) par1));
    }
}
