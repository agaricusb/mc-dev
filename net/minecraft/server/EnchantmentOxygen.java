package net.minecraft.server;

public class EnchantmentOxygen extends Enchantment
{
    public EnchantmentOxygen(int par1, int par2)
    {
        super(par1, par2, EnchantmentSlotType.ARMOR_HEAD);
        this.b("oxygen");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return 10 * par1;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int b(int par1)
    {
        return this.a(par1) + 30;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 3;
    }
}
