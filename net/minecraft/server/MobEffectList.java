package net.minecraft.server;

public class MobEffectList
{
    /** The array of potion types. */
    public static final MobEffectList[] byId = new MobEffectList[32];
    public static final MobEffectList b = null;
    public static final MobEffectList FASTER_MOVEMENT = (new MobEffectList(1, false, 8171462)).b("potion.moveSpeed").b(0, 0);
    public static final MobEffectList SLOWER_MOVEMENT = (new MobEffectList(2, true, 5926017)).b("potion.moveSlowdown").b(1, 0);
    public static final MobEffectList FASTER_DIG = (new MobEffectList(3, false, 14270531)).b("potion.digSpeed").b(2, 0).a(1.5D);
    public static final MobEffectList SLOWER_DIG = (new MobEffectList(4, true, 4866583)).b("potion.digSlowDown").b(3, 0);
    public static final MobEffectList INCREASE_DAMAGE = (new MobEffectList(5, false, 9643043)).b("potion.damageBoost").b(4, 0);
    public static final MobEffectList HEAL = (new InstantMobEffect(6, false, 16262179)).b("potion.heal");
    public static final MobEffectList HARM = (new InstantMobEffect(7, true, 4393481)).b("potion.harm");
    public static final MobEffectList JUMP = (new MobEffectList(8, false, 7889559)).b("potion.jump").b(2, 1);
    public static final MobEffectList CONFUSION = (new MobEffectList(9, true, 5578058)).b("potion.confusion").b(3, 1).a(0.25D);

    /** The regeneration Potion object. */
    public static final MobEffectList REGENERATION = (new MobEffectList(10, false, 13458603)).b("potion.regeneration").b(7, 0).a(0.25D);
    public static final MobEffectList RESISTANCE = (new MobEffectList(11, false, 10044730)).b("potion.resistance").b(6, 1);

    /** The fire resistance Potion object. */
    public static final MobEffectList FIRE_RESISTANCE = (new MobEffectList(12, false, 14981690)).b("potion.fireResistance").b(7, 1);

    /** The water breathing Potion object. */
    public static final MobEffectList WATER_BREATHING = (new MobEffectList(13, false, 3035801)).b("potion.waterBreathing").b(0, 2);

    /** The invisibility Potion object. */
    public static final MobEffectList INVISIBILITY = (new MobEffectList(14, false, 8356754)).b("potion.invisibility").b(0, 1);

    /** The blindness Potion object. */
    public static final MobEffectList BLINDNESS = (new MobEffectList(15, true, 2039587)).b("potion.blindness").b(5, 1).a(0.25D);

    /** The night vision Potion object. */
    public static final MobEffectList NIGHT_VISION = (new MobEffectList(16, false, 2039713)).b("potion.nightVision").b(4, 1);

    /** The hunger Potion object. */
    public static final MobEffectList HUNGER = (new MobEffectList(17, true, 5797459)).b("potion.hunger").b(1, 1);

    /** The weakness Potion object. */
    public static final MobEffectList WEAKNESS = (new MobEffectList(18, true, 4738376)).b("potion.weakness").b(5, 0);

    /** The poison Potion object. */
    public static final MobEffectList POISON = (new MobEffectList(19, true, 5149489)).b("potion.poison").b(6, 0).a(0.25D);

    /** The wither Potion object. */
    public static final MobEffectList WITHER = (new MobEffectList(20, true, 3484199)).b("potion.wither").b(1, 2).a(0.25D);
    public static final MobEffectList w = null;
    public static final MobEffectList x = null;
    public static final MobEffectList y = null;
    public static final MobEffectList z = null;
    public static final MobEffectList A = null;
    public static final MobEffectList B = null;
    public static final MobEffectList C = null;
    public static final MobEffectList D = null;
    public static final MobEffectList E = null;
    public static final MobEffectList F = null;
    public static final MobEffectList G = null;

    /** The Id of a Potion object. */
    public final int id;

    /** The name of the Potion. */
    private String I = "";

    /** The index for the icon displayed when the potion effect is active. */
    private int J = -1;

    /**
     * This field indicated if the effect is 'bad' - negative - for the entity.
     */
    private final boolean K;
    private double L;
    private boolean M;

    /** Is the color of the liquid for this potion. */
    private final int N;

    protected MobEffectList(int par1, boolean par2, int par3)
    {
        this.id = par1;
        byId[par1] = this;
        this.K = par2;

        if (par2)
        {
            this.L = 0.5D;
        }
        else
        {
            this.L = 1.0D;
        }

        this.N = par3;
    }

    /**
     * Sets the index for the icon displayed in the player's inventory when the status is active.
     */
    protected MobEffectList b(int par1, int par2)
    {
        this.J = par1 + par2 * 8;
        return this;
    }

    /**
     * returns the ID of the potion
     */
    public int getId()
    {
        return this.id;
    }

    public void tick(EntityLiving par1EntityLiving, int par2)
    {
        if (this.id == REGENERATION.id)
        {
            if (par1EntityLiving.getHealth() < par1EntityLiving.getMaxHealth())
            {
                par1EntityLiving.heal(1);
            }
        }
        else if (this.id == POISON.id)
        {
            if (par1EntityLiving.getHealth() > 1)
            {
                par1EntityLiving.damageEntity(DamageSource.MAGIC, 1);
            }
        }
        else if (this.id == WITHER.id)
        {
            par1EntityLiving.damageEntity(DamageSource.WITHER, 1);
        }
        else if (this.id == HUNGER.id && par1EntityLiving instanceof EntityHuman)
        {
            ((EntityHuman)par1EntityLiving).j(0.025F * (float) (par2 + 1));
        }
        else if ((this.id != HEAL.id || par1EntityLiving.bA()) && (this.id != HARM.id || !par1EntityLiving.bA()))
        {
            if (this.id == HARM.id && !par1EntityLiving.bA() || this.id == HEAL.id && par1EntityLiving.bA())
            {
                par1EntityLiving.damageEntity(DamageSource.MAGIC, 6 << par2);
            }
        }
        else
        {
            par1EntityLiving.heal(6 << par2);
        }
    }

    /**
     * Hits the provided entity with this potion's instant effect.
     */
    public void applyInstantEffect(EntityLiving par1EntityLiving, EntityLiving par2EntityLiving, int par3, double par4)
    {
        int var6;

        if ((this.id != HEAL.id || par2EntityLiving.bA()) && (this.id != HARM.id || !par2EntityLiving.bA()))
        {
            if (this.id == HARM.id && !par2EntityLiving.bA() || this.id == HEAL.id && par2EntityLiving.bA())
            {
                var6 = (int)(par4 * (double)(6 << par3) + 0.5D);

                if (par1EntityLiving == null)
                {
                    par2EntityLiving.damageEntity(DamageSource.MAGIC, var6);
                }
                else
                {
                    par2EntityLiving.damageEntity(DamageSource.b(par2EntityLiving, par1EntityLiving), var6);
                }
            }
        }
        else
        {
            var6 = (int)(par4 * (double)(6 << par3) + 0.5D);
            par2EntityLiving.heal(var6);
        }
    }

    /**
     * Returns true if the potion has an instant effect instead of a continuous one (eg Harming)
     */
    public boolean isInstant()
    {
        return false;
    }

    /**
     * checks if Potion effect is ready to be applied this tick.
     */
    public boolean a(int par1, int par2)
    {
        int var3;

        if (this.id != REGENERATION.id && this.id != POISON.id)
        {
            if (this.id == WITHER.id)
            {
                var3 = 40 >> par2;
                return var3 > 0 ? par1 % var3 == 0 : true;
            }
            else
            {
                return this.id == HUNGER.id;
            }
        }
        else
        {
            var3 = 25 >> par2;
            return var3 > 0 ? par1 % var3 == 0 : true;
        }
    }

    /**
     * Set the potion name.
     */
    public MobEffectList b(String par1Str)
    {
        this.I = par1Str;
        return this;
    }

    /**
     * returns the name of the potion
     */
    public String a()
    {
        return this.I;
    }

    protected MobEffectList a(double par1)
    {
        this.L = par1;
        return this;
    }

    public double getDurationModifier()
    {
        return this.L;
    }

    public boolean i()
    {
        return this.M;
    }

    /**
     * Returns the color of the potion liquid.
     */
    public int j()
    {
        return this.N;
    }
}
