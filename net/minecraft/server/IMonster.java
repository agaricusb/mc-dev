package net.minecraft.server;

public interface IMonster extends IAnimal
{
    /** Entity selector for IMob types. */
    IEntitySelector a = new EntitySelectorMonster();
}
