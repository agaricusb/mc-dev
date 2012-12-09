package net.minecraft.server;

public class EnchantmentLootBonus extends Enchantment
{
    protected EnchantmentLootBonus(int par1, int par2, EnchantmentSlotType par3EnumEnchantmentType)
    {
        super(par1, par2, par3EnumEnchantmentType);
        this.b("lootBonus");

        if (par3EnumEnchantmentType == EnchantmentSlotType.DIGGER)
        {
            this.b("lootBonusDigger");
        }
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return 15 + (par1 - 1) * 9;
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

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean a(Enchantment par1Enchantment)
    {
        return super.a(par1Enchantment) && par1Enchantment.id != SILK_TOUCH.id;
    }
}
