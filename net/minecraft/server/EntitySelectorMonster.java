package net.minecraft.server;

final class EntitySelectorMonster implements IEntitySelector
{
    /**
     * Return whether the specified entity is applicable to this filter.
     */
    public boolean a(Entity par1Entity)
    {
        return par1Entity instanceof IMonster;
    }
}
