package net.minecraft.server;

public interface IInventoryListener
{
    /**
     * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
     */
    void a(InventorySubcontainer var1);
}
