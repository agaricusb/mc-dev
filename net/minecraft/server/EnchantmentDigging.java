package net.minecraft.server;

public class EnchantmentDigging extends Enchantment
{
    protected EnchantmentDigging(int par1, int par2)
    {
        super(par1, par2, EnchantmentSlotType.DIGGER);
        this.b("digging");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return 1 + 10 * (par1 - 1);
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int b(int par1)
    {
        return super.a(par1) + 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 5;
    }

    public boolean canEnchant(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem().id == Item.SHEARS.id ? true : super.canEnchant(par1ItemStack);
    }
}
