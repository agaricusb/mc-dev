package net.minecraft.server;

import java.util.Random;

public class TileEntityDispenser extends TileEntity implements IInventory
{
    private ItemStack[] items = new ItemStack[9];

    /**
     * random number generator for instance. Used in random item stack selection.
     */
    private Random b = new Random();

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return 9;
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

    public int i()
    {
        int var1 = -1;
        int var2 = 1;

        for (int var3 = 0; var3 < this.items.length; ++var3)
        {
            if (this.items[var3] != null && this.b.nextInt(var2++) == 0)
            {
                var1 = var3;
            }
        }

        return var1;
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
     * Add item stack in first available inventory slot
     */
    public int addItem(ItemStack par1ItemStack)
    {
        for (int var2 = 0; var2 < this.items.length; ++var2)
        {
            if (this.items[var2] == null || this.items[var2].id == 0)
            {
                this.items[var2] = par1ItemStack;
                return var2;
            }
        }

        return -1;
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "container.dispenser";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getList("Items");
        this.items = new ItemStack[this.getSize()];

        for (int var3 = 0; var3 < var2.size(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.items.length)
            {
                this.items[var5] = ItemStack.a(var4);
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.items.length; ++var3)
        {
            if (this.items[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.items[var3].save(var4);
                var2.add(var4);
            }
        }

        par1NBTTagCompound.set("Items", var2);
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
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman par1EntityPlayer)
    {
        return this.world.getTileEntity(this.x, this.y, this.z) != this ? false : par1EntityPlayer.e((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
    }

    public void startOpen() {}

    public void f() {}
}
