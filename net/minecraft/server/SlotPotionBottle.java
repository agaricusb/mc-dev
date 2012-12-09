package net.minecraft.server;

class SlotPotionBottle extends Slot
{
    /** The player that has this container open. */
    private EntityHuman a;

    public SlotPotionBottle(EntityHuman par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.a = par1EntityPlayer;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isAllowed(ItemStack par1ItemStack)
    {
        return a_(par1ItemStack);
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int a()
    {
        return 1;
    }

    public void a(EntityHuman par1EntityPlayer, ItemStack par2ItemStack)
    {
        if (par2ItemStack.id == Item.POTION.id && par2ItemStack.getData() > 0)
        {
            this.a.a(AchievementList.A, 1);
        }

        super.a(par1EntityPlayer, par2ItemStack);
    }

    /**
     * Returns true if this itemstack can be filled with a potion
     */
    public static boolean a_(ItemStack par0ItemStack)
    {
        return par0ItemStack != null && (par0ItemStack.id == Item.POTION.id || par0ItemStack.id == Item.GLASS_BOTTLE.id);
    }
}
