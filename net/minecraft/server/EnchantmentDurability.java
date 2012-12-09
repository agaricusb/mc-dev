package net.minecraft.server;

public class EnchantmentDurability extends Enchantment
{
    protected EnchantmentDurability(int par1, int par2)
    {
        super(par1, par2, EnchantmentSlotType.DIGGER);
        this.b("durability");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return 5 + (par1 - 1) * 8;
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
        return 3;
    }
}
