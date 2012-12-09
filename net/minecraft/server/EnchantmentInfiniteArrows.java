package net.minecraft.server;

public class EnchantmentInfiniteArrows extends Enchantment
{
    public EnchantmentInfiniteArrows(int par1, int par2)
    {
        super(par1, par2, EnchantmentSlotType.BOW);
        this.b("arrowInfinite");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return 20;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int b(int par1)
    {
        return 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }
}
