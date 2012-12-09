package net.minecraft.server;

public class InventoryCraftResult implements IInventory
{
    /** A list of one item containing the result of the crafting formula */
    private ItemStack[] items = new ItemStack[1];

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return 1;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int par1)
    {
        return this.items[0];
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "Result";
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack splitStack(int par1, int par2)
    {
        if (this.items[0] != null)
        {
            ItemStack var3 = this.items[0];
            this.items[0] = null;
            return var3;
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int par1)
    {
        if (this.items[0] != null)
        {
            ItemStack var2 = this.items[0];
            this.items[0] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int par1, ItemStack par2ItemStack)
    {
        this.items[0] = par2ItemStack;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return 64;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void update() {}

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman par1EntityPlayer)
    {
        return true;
    }

    public void startOpen() {}

    public void f() {}
}
