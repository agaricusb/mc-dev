package net.minecraft.server;

public class InventoryLargeChest implements IInventory
{
    /** Name of the chest. */
    private String a;

    /** Inventory object corresponding to double chest upper part */
    private IInventory left;

    /** Inventory object corresponding to double chest lower part */
    private IInventory right;

    public InventoryLargeChest(String par1Str, IInventory par2IInventory, IInventory par3IInventory)
    {
        this.a = par1Str;

        if (par2IInventory == null)
        {
            par2IInventory = par3IInventory;
        }

        if (par3IInventory == null)
        {
            par3IInventory = par2IInventory;
        }

        this.left = par2IInventory;
        this.right = par3IInventory;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return this.left.getSize() + this.right.getSize();
    }

    /**
     * Return whether the given inventory is part of this large chest.
     */
    public boolean a(IInventory par1IInventory)
    {
        return this.left == par1IInventory || this.right == par1IInventory;
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return this.a;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int par1)
    {
        return par1 >= this.left.getSize() ? this.right.getItem(par1 - this.left.getSize()) : this.left.getItem(par1);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack splitStack(int par1, int par2)
    {
        return par1 >= this.left.getSize() ? this.right.splitStack(par1 - this.left.getSize(), par2) : this.left.splitStack(par1, par2);
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int par1)
    {
        return par1 >= this.left.getSize() ? this.right.splitWithoutUpdate(par1 - this.left.getSize()) : this.left.splitWithoutUpdate(par1);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int par1, ItemStack par2ItemStack)
    {
        if (par1 >= this.left.getSize())
        {
            this.right.setItem(par1 - this.left.getSize(), par2ItemStack);
        }
        else
        {
            this.left.setItem(par1, par2ItemStack);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return this.left.getMaxStackSize();
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void update()
    {
        this.left.update();
        this.right.update();
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman par1EntityPlayer)
    {
        return this.left.a_(par1EntityPlayer) && this.right.a_(par1EntityPlayer);
    }

    public void startOpen()
    {
        this.left.startOpen();
        this.right.startOpen();
    }

    public void f()
    {
        this.left.f();
        this.right.f();
    }
}
