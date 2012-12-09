package net.minecraft.server;

public interface IEntitySelector
{
    /**
     * Return whether the specified entity is applicable to this filter.
     */
    boolean a(Entity var1);
}
