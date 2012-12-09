package net.minecraft.server;

class SlotBrewing extends Slot
{
    /** The brewing stand this slot belongs to. */
    final ContainerBrewingStand a;

    public SlotBrewing(ContainerBrewingStand par1ContainerBrewingStand, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.a = par1ContainerBrewingStand;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isAllowed(ItemStack par1ItemStack)
    {
        return par1ItemStack != null ? Item.byId[par1ItemStack.id].v() : false;
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int a()
    {
        return 64;
    }
}
