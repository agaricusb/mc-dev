package net.minecraft.server;

public class MobEffect
{
    /** ID value of the potion this effect matches. */
    private int effectId;

    /** The duration of the potion effect */
    private int duration;

    /** The amplifier of the potion effect */
    private int amplification;
    private boolean splash;
    private boolean ambient;

    public MobEffect(int par1, int par2)
    {
        this(par1, par2, 0);
    }

    public MobEffect(int par1, int par2, int par3)
    {
        this(par1, par2, par3, false);
    }

    public MobEffect(int par1, int par2, int par3, boolean par4)
    {
        this.effectId = par1;
        this.duration = par2;
        this.amplification = par3;
        this.ambient = par4;
    }

    public MobEffect(MobEffect par1PotionEffect)
    {
        this.effectId = par1PotionEffect.effectId;
        this.duration = par1PotionEffect.duration;
        this.amplification = par1PotionEffect.amplification;
    }

    /**
     * merges the input PotionEffect into this one if this.amplifier <= tomerge.amplifier. The duration in the supplied
     * potion effect is assumed to be greater.
     */
    public void a(MobEffect par1PotionEffect)
    {
        if (this.effectId != par1PotionEffect.effectId)
        {
            System.err.println("This method should only be called for matching effects!");
        }

        if (par1PotionEffect.amplification > this.amplification)
        {
            this.amplification = par1PotionEffect.amplification;
            this.duration = par1PotionEffect.duration;
        }
        else if (par1PotionEffect.amplification == this.amplification && this.duration < par1PotionEffect.duration)
        {
            this.duration = par1PotionEffect.duration;
        }
        else if (!par1PotionEffect.ambient && this.ambient)
        {
            this.ambient = par1PotionEffect.ambient;
        }
    }

    /**
     * Retrieve the ID of the potion this effect matches.
     */
    public int getEffectId()
    {
        return this.effectId;
    }

    public int getDuration()
    {
        return this.duration;
    }

    public int getAmplifier()
    {
        return this.amplification;
    }

    /**
     * Set whether this potion is a splash potion.
     */
    public void setSplash(boolean par1)
    {
        this.splash = par1;
    }

    public boolean isAmbient()
    {
        return this.ambient;
    }

    public boolean tick(EntityLiving par1EntityLiving)
    {
        if (this.duration > 0)
        {
            if (MobEffectList.byId[this.effectId].a(this.duration, this.amplification))
            {
                this.b(par1EntityLiving);
            }

            this.g();
        }

        return this.duration > 0;
    }

    private int g()
    {
        return --this.duration;
    }

    public void b(EntityLiving par1EntityLiving)
    {
        if (this.duration > 0)
        {
            MobEffectList.byId[this.effectId].tick(par1EntityLiving, this.amplification);
        }
    }

    public String f()
    {
        return MobEffectList.byId[this.effectId].a();
    }

    public int hashCode()
    {
        return this.effectId;
    }

    public String toString()
    {
        String var1 = "";

        if (this.getAmplifier() > 0)
        {
            var1 = this.f() + " x " + (this.getAmplifier() + 1) + ", Duration: " + this.getDuration();
        }
        else
        {
            var1 = this.f() + ", Duration: " + this.getDuration();
        }

        if (this.splash)
        {
            var1 = var1 + ", Splash: true";
        }

        return MobEffectList.byId[this.effectId].i() ? "(" + var1 + ")" : var1;
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof MobEffect))
        {
            return false;
        }
        else
        {
            MobEffect var2 = (MobEffect)par1Obj;
            return this.effectId == var2.effectId && this.amplification == var2.amplification && this.duration == var2.duration && this.splash == var2.splash && this.ambient == var2.ambient;
        }
    }

    /**
     * Write a custom potion effect to a potion item's NBT data.
     */
    public NBTTagCompound a(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Id", (byte)this.getEffectId());
        par1NBTTagCompound.setByte("Amplifier", (byte)this.getAmplifier());
        par1NBTTagCompound.setInt("Duration", this.getDuration());
        par1NBTTagCompound.setBoolean("Ambient", this.isAmbient());
        return par1NBTTagCompound;
    }

    /**
     * Read a custom potion effect from a potion item's NBT data.
     */
    public static MobEffect b(NBTTagCompound par0NBTTagCompound)
    {
        byte var1 = par0NBTTagCompound.getByte("Id");
        byte var2 = par0NBTTagCompound.getByte("Amplifier");
        int var3 = par0NBTTagCompound.getInt("Duration");
        boolean var4 = par0NBTTagCompound.getBoolean("Ambient");
        return new MobEffect(var1, var3, var2, var4);
    }
}
