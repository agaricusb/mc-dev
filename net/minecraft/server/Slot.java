package net.minecraft.server;

public class Slot
{
    /** The index of the slot in the inventory. */
    private final int index;

    /** The inventory we want to extract a slot from. */
    public final IInventory inventory;

    /** the id of the slot(also the index in the inventory arraylist) */
    public int g;

    /** display position of the inventory slot on the screen x axis */
    public int h;

    /** display position of the inventory slot on the screen y axis */
    public int i;

    public Slot(IInventory par1IInventory, int par2, int par3, int par4)
    {
        this.inventory = par1IInventory;
        this.index = par2;
        this.h = par3;
        this.i = par4;
    }

    /**
     * if par2 has more items than par1, onCrafting(item,countIncrease) is called
     */
    public void a(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        if (par1ItemStack != null && par2ItemStack != null)
        {
            if (par1ItemStack.id == par2ItemStack.id)
            {
                int var3 = par2ItemStack.count - par1ItemStack.count;

                if (var3 > 0)
                {
                    this.a(par1ItemStack, var3);
                }
            }
        }
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void a(ItemStack par1ItemStack, int par2) {}

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void b(ItemStack par1ItemStack) {}

    public void a(EntityHuman par1EntityPlayer, ItemStack par2ItemStack)
    {
        this.e();
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isAllowed(ItemStack par1ItemStack)
    {
        return true;
    }

    /**
     * Helper fnct to get the stack in the slot.
     */
    public ItemStack getItem()
    {
        return this.inventory.getItem(this.index);
    }

    /**
     * Returns if this slot contains a stack.
     */
    public boolean d()
    {
        return this.getItem() != null;
    }

    /**
     * Helper method to put a stack in the slot.
     */
    public void set(ItemStack par1ItemStack)
    {
        this.inventory.setItem(this.index, par1ItemStack);
        this.e();
    }

    /**
     * Called when the stack in a Slot changes
     */
    public void e()
    {
        this.inventory.update();
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int a()
    {
        return this.inventory.getMaxStackSize();
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack a(int par1)
    {
        return this.inventory.splitStack(this.index, par1);
    }

    /**
     * returns true if the slot exists in the given inventory and location
     */
    public boolean a(IInventory par1IInventory, int par2)
    {
        return par1IInventory == this.inventory && par2 == this.index;
    }

    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        return true;
    }
}
