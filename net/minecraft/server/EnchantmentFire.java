package net.minecraft.server;

public class EnchantmentFire extends Enchantment
{
    protected EnchantmentFire(int par1, int par2)
    {
        super(par1, par2, EnchantmentSlotType.WEAPON);
        this.b("fire");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return 10 + 20 * (par1 - 1);
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
        return 2;
    }
}
