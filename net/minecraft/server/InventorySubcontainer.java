package net.minecraft.server;

import java.util.List;

public class InventorySubcontainer implements IInventory
{
    private String a;
    private int b;
    private ItemStack[] items;
    private List d;

    public InventorySubcontainer(String par1Str, int par2)
    {
        this.a = par1Str;
        this.b = par2;
        this.items = new ItemStack[par2];
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int par1)
    {
        return this.items[par1];
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
                this.update();
                return var3;
            }
            else
            {
                var3 = this.items[par1].a(par2);

                if (this.items[par1].count == 0)
                {
                    this.items[par1] = null;
                }

                this.update();
                return var3;
            }
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
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int par1, ItemStack par2ItemStack)
    {
        this.items[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.count > this.getMaxStackSize())
        {
            par2ItemStack.count = this.getMaxStackSize();
        }

        this.update();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return this.b;
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return this.a;
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
    public void update()
    {
        if (this.d != null)
        {
            for (int var1 = 0; var1 < this.d.size(); ++var1)
            {
                ((IInventoryListener)this.d.get(var1)).a(this);
            }
        }
    }

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
