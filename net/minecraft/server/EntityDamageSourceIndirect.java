package net.minecraft.server;

public class EntityDamageSourceIndirect extends EntityDamageSource
{
    private Entity owner;

    public EntityDamageSourceIndirect(String par1Str, Entity par2Entity, Entity par3Entity)
    {
        super(par1Str, par2Entity);
        this.owner = par3Entity;
    }

    public Entity f()
    {
        return this.r;
    }

    public Entity getEntity()
    {
        return this.owner;
    }

    /**
     * Returns the message to be displayed on player death.
     */
    public String getLocalizedDeathMessage(EntityHuman par1EntityPlayer)
    {
        return LocaleI18n.get("death." + this.translationIndex, new Object[]{par1EntityPlayer.name, this.owner == null ? this.r.getLocalizedName() : this.owner.getLocalizedName()});
    }
}
