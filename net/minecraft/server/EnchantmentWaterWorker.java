package net.minecraft.server;

public class EnchantmentWaterWorker extends Enchantment
{
    public EnchantmentWaterWorker(int par1, int par2)
    {
        super(par1, par2, EnchantmentSlotType.ARMOR_HEAD);
        this.b("waterWorker");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return 1;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int b(int par1)
    {
        return this.a(par1) + 40;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }
}
