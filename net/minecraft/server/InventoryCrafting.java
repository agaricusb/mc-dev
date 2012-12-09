package net.minecraft.server;

public class InventoryCrafting implements IInventory
{
    /** List of the stacks in the crafting matrix. */
    private ItemStack[] items;

    /** the width of the crafting inventory */
    private int b;

    /**
     * Class containing the callbacks for the events on_GUIClosed and on_CraftMaxtrixChanged.
     */
    private Container c;

    public InventoryCrafting(Container par1Container, int par2, int par3)
    {
        int var4 = par2 * par3;
        this.items = new ItemStack[var4];
        this.c = par1Container;
        this.b = par2;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return this.items.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int par1)
    {
        return par1 >= this.getSize() ? null : this.items[par1];
    }

    /**
     * Returns the itemstack in the slot specified (Top left is 0, 0). Args: row, column
     */
    public ItemStack b(int par1, int par2)
    {
        if (par1 >= 0 && par1 < this.b)
        {
            int var3 = par1 + par2 * this.b;
            return this.getItem(var3);
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "container.crafting";
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int par1)
    {
        if (this.items[par1] != null)
        {
            ItemStack var2 = this.items[par1];
            this.items[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack splitStack(int par1, int par2)
    {
        if (this.items[par1] != null)
        {
            ItemStack var3;

            if (this.items[par1].count <= par2)
            {
                var3 = this.items[par1];
                this.items[par1] = null;
                this.c.a(this);
                return var3;
            }
            else
            {
                var3 = this.items[par1].a(par2);

                if (this.items[par1].count == 0)
                {
                    this.items[par1] = null;
                }

                this.c.a(this);
                return var3;
            }
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
        this.items[par1] = par2ItemStack;
        this.c.a(this);
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
