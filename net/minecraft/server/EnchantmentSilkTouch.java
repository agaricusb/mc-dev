package net.minecraft.server;

public class EnchantmentSilkTouch extends Enchantment
{
    protected EnchantmentSilkTouch(int par1, int par2)
    {
        super(par1, par2, EnchantmentSlotType.DIGGER);
        this.b("untouching");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return 15;
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
        return 1;
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean a(Enchantment par1Enchantment)
    {
        return super.a(par1Enchantment) && par1Enchantment.id != LOOT_BONUS_BLOCKS.id;
    }

    public boolean canEnchant(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem().id == Item.SHEARS.id ? true : super.canEnchant(par1ItemStack);
    }
}
