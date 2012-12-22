package net.minecraft.server;

public class ItemBook extends Item
{
    public ItemBook(int par1)
    {
        super(par1);
    }

    /**
     * Checks isDamagable and if it cannot be stacked
     */
    public boolean d_(ItemStack par1ItemStack)
    {
        return par1ItemStack.count == 1;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int c()
    {
        return 1;
    }
}
