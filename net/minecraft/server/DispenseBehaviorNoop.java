package net.minecraft.server;

final class DispenseBehaviorNoop implements IDispenseBehavior
{
    /**
     * Dispenses the specified ItemStack from a dispenser.
     */
    public ItemStack a(ISourceBlock par1IBlockSource, ItemStack par2ItemStack)
    {
        return par2ItemStack;
    }
}
