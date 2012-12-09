package net.minecraft.server;

class ContainerAnvilInventory extends InventorySubcontainer
{
    /** Container of this anvil's block. */
    final ContainerAnvil a;

    ContainerAnvilInventory(ContainerAnvil par1ContainerRepair, String par2Str, int par3)
    {
        super(par2Str, par3);
        this.a = par1ContainerRepair;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void update()
    {
        super.update();
        this.a.a(this);
    }
}
