package net.minecraft.server;

public class EntityDamageSource extends DamageSource
{
    protected Entity r;

    public EntityDamageSource(String par1Str, Entity par2Entity)
    {
        super(par1Str);
        this.r = par2Entity;
    }

    public Entity getEntity()
    {
        return this.r;
    }

    /**
     * Returns the message to be displayed on player death.
     */
    public String getLocalizedDeathMessage(EntityHuman par1EntityPlayer)
    {
        return LocaleI18n.get("death." + this.translationIndex, new Object[]{par1EntityPlayer.name, this.r.getLocalizedName()});
    }

    public boolean n()
    {
        return this.r != null && this.r instanceof EntityLiving && !(this.r instanceof EntityHuman);
    }
}
