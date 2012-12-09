package net.minecraft.server;

public interface IInventory
{
    /**
     * Returns the number of slots in the inventory.
     */
    int getSize();

    /**
     * Returns the stack in slot i
     */
    ItemStack getItem(int var1);

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    ItemStack splitStack(int var1, int var2);

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    ItemStack splitWithoutUpdate(int var1);

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    void setItem(int var1, ItemStack var2);

    /**
     * Returns the name of the inventory.
     */
    String getName();

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    int getMaxStackSize();

    /**
     * Called when an the contents of an Inventory change, usually
     */
    void update();

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    boolean a_(EntityHuman var1);

    void startOpen();

    void f();
}
