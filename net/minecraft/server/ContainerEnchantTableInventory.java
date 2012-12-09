package net.minecraft.server;

class ContainerEnchantTableInventory extends InventorySubcontainer
{
    /** The brewing stand this slot belongs to. */
    final ContainerEnchantTable enchantTable;

    ContainerEnchantTableInventory(ContainerEnchantTable par1ContainerEnchantment, String par2Str, int par3)
    {
        super(par2Str, par3);
        this.enchantTable = par1ContainerEnchantment;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return 1;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void update()
    {
        super.update();
        this.enchantTable.a(this);
    }
}
