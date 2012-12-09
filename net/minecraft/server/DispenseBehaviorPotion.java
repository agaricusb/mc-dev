package net.minecraft.server;

public class DispenseBehaviorPotion implements IDispenseBehavior
{
    /** Reference to the BehaviorDefaultDispenseItem object. */
    private final DispenseBehaviorItem c;

    /** Reference to the MinecraftServer object. */
    final MinecraftServer b;

    public DispenseBehaviorPotion(MinecraftServer par1)
    {
        this.b = par1;
        this.c = new DispenseBehaviorItem();
    }

    /**
     * Dispenses the specified ItemStack from a dispenser.
     */
    public ItemStack a(ISourceBlock par1IBlockSource, ItemStack par2ItemStack)
    {
        return ItemPotion.g(par2ItemStack.getData()) ? (new DispenseBehaviorThrownPotion(this, par2ItemStack)).a(par1IBlockSource, par2ItemStack) : this.c.a(par1IBlockSource, par2ItemStack);
    }
}
