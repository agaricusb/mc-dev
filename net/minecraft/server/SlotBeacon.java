package net.minecraft.server;

class SlotBeacon extends Slot
{
    /** The beacon this slot belongs to. */
    final ContainerBeacon a;

    public SlotBeacon(ContainerBeacon par1ContainerBeacon, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.a = par1ContainerBeacon;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isAllowed(ItemStack par1ItemStack)
    {
        return par1ItemStack == null ? false : par1ItemStack.id == Item.EMERALD.id || par1ItemStack.id == Item.DIAMOND.id || par1ItemStack.id == Item.GOLD_INGOT.id || par1ItemStack.id == Item.IRON_INGOT.id;
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int a()
    {
        return 1;
    }
}
