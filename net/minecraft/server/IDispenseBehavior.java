package net.minecraft.server;

public interface IDispenseBehavior
{
    IDispenseBehavior a = new DispenseBehaviorNoop();

    /**
     * Dispenses the specified ItemStack from a dispenser.
     */
    ItemStack a(ISourceBlock var1, ItemStack var2);
}
