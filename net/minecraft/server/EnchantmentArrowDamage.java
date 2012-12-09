package net.minecraft.server;

public class EnchantmentArrowDamage extends Enchantment
{
    public EnchantmentArrowDamage(int par1, int par2)
    {
        super(par1, par2, EnchantmentSlotType.BOW);
        this.b("arrowDamage");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return 1 + (par1 - 1) * 10;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int b(int par1)
    {
        return this.a(par1) + 15;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 5;
    }
}
