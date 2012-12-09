package net.minecraft.server;

public class EnchantmentInstance extends WeightedRandomChoice
{
    /** Enchantment object associated with this EnchantmentData */
    public final Enchantment enchantment;

    /** Enchantment level associated with this EnchantmentData */
    public final int level;

    public EnchantmentInstance(Enchantment par1Enchantment, int par2)
    {
        super(par1Enchantment.getRandomWeight());
        this.enchantment = par1Enchantment;
        this.level = par2;
    }
}
