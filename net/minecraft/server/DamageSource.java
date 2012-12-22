package net.minecraft.server;

public class DamageSource
{
    public static DamageSource FIRE = (new DamageSource("inFire")).j();
    public static DamageSource BURN = (new DamageSource("onFire")).h().j();
    public static DamageSource LAVA = (new DamageSource("lava")).j();
    public static DamageSource STUCK = (new DamageSource("inWall")).h();
    public static DamageSource DROWN = (new DamageSource("drown")).h();
    public static DamageSource STARVE = (new DamageSource("starve")).h();
    public static DamageSource CACTUS = new DamageSource("cactus");
    public static DamageSource FALL = (new DamageSource("fall")).h();
    public static DamageSource OUT_OF_WORLD = (new DamageSource("outOfWorld")).h().i();
    public static DamageSource GENERIC = (new DamageSource("generic")).h();
    public static DamageSource EXPLOSION = (new DamageSource("explosion")).m();
    public static DamageSource EXPLOSION2 = new DamageSource("explosion");
    public static DamageSource MAGIC = (new DamageSource("magic")).h().p();
    public static DamageSource WITHER = (new DamageSource("wither")).h();
    public static DamageSource ANVIL = new DamageSource("anvil");
    public static DamageSource FALLING_BLOCK = new DamageSource("fallingBlock");

    /** This kind of damage can be blocked or not. */
    private boolean r = false;
    private boolean s = false;
    private float t = 0.3F;

    /** This kind of damage is based on fire or not. */
    private boolean u;

    /** This kind of damage is based on a projectile or not. */
    private boolean v;

    /**
     * Whether this damage source will have its damage amount scaled based on the current difficulty.
     */
    private boolean w;

    /** Whether the damage is magic based. */
    private boolean x = false;
    public String translationIndex;

    public static DamageSource mobAttack(EntityLiving par0EntityLiving)
    {
        return new EntityDamageSource("mob", par0EntityLiving);
    }

    /**
     * returns an EntityDamageSource of type player
     */
    public static DamageSource playerAttack(EntityHuman par0EntityPlayer)
    {
        return new EntityDamageSource("player", par0EntityPlayer);
    }

    /**
     * returns EntityDamageSourceIndirect of an arrow
     */
    public static DamageSource arrow(EntityArrow par0EntityArrow, Entity par1Entity)
    {
        return (new EntityDamageSourceIndirect("arrow", par0EntityArrow, par1Entity)).b();
    }

    /**
     * returns EntityDamageSourceIndirect of a fireball
     */
    public static DamageSource fireball(EntityFireball par0EntityFireball, Entity par1Entity)
    {
        return par1Entity == null ? (new EntityDamageSourceIndirect("onFire", par0EntityFireball, par0EntityFireball)).j().b() : (new EntityDamageSourceIndirect("fireball", par0EntityFireball, par1Entity)).j().b();
    }

    public static DamageSource projectile(Entity par0Entity, Entity par1Entity)
    {
        return (new EntityDamageSourceIndirect("thrown", par0Entity, par1Entity)).b();
    }

    public static DamageSource b(Entity par0Entity, Entity par1Entity)
    {
        return (new EntityDamageSourceIndirect("indirectMagic", par0Entity, par1Entity)).h().p();
    }

    public static DamageSource a(Entity par0Entity)
    {
        return (new EntityDamageSource("thorns", par0Entity)).p();
    }

    /**
     * Returns true if the damage is projectile based.
     */
    public boolean a()
    {
        return this.v;
    }

    /**
     * Define the damage type as projectile based.
     */
    public DamageSource b()
    {
        this.v = true;
        return this;
    }

    public boolean ignoresArmor()
    {
        return this.r;
    }

    /**
     * How much satiate(food) is consumed by this DamageSource
     */
    public float d()
    {
        return this.t;
    }

    public boolean ignoresInvulnerability()
    {
        return this.s;
    }

    protected DamageSource(String par1Str)
    {
        this.translationIndex = par1Str;
    }

    public Entity f()
    {
        return this.getEntity();
    }

    public Entity getEntity()
    {
        return null;
    }

    protected DamageSource h()
    {
        this.r = true;
        this.t = 0.0F;
        return this;
    }

    protected DamageSource i()
    {
        this.s = true;
        return this;
    }

    /**
     * Define the damage type as fire based.
     */
    protected DamageSource j()
    {
        this.u = true;
        return this;
    }

    /**
     * Returns the message to be displayed on player death.
     */
    public String getLocalizedDeathMessage(EntityHuman par1EntityPlayer)
    {
        return LocaleI18n.get("death." + this.translationIndex, new Object[]{par1EntityPlayer.name});
    }

    /**
     * Returns true if the damage is fire based.
     */
    public boolean k()
    {
        return this.u;
    }

    /**
     * Return the name of damage type.
     */
    public String l()
    {
        return this.translationIndex;
    }

    /**
     * Set whether this damage source will have its damage amount scaled based on the current difficulty.
     */
    public DamageSource m()
    {
        this.w = true;
        return this;
    }

    /**
     * Return whether this damage source will have its damage amount scaled based on the current difficulty.
     */
    public boolean n()
    {
        return this.w;
    }

    /**
     * Returns true if the damage is magic based.
     */
    public boolean o()
    {
        return this.x;
    }

    /**
     * Define the damage type as magic based.
     */
    public DamageSource p()
    {
        this.x = true;
        return this;
    }
}
