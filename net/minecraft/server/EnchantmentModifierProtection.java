package net.minecraft.server;

final class EnchantmentModifierProtection implements EnchantmentModifier
{
    /**
     * Used to calculate the damage modifier (extra armor) on enchantments that the player have on equipped armors.
     */
    public int a;

    /**
     * Used as parameter to calculate the damage modifier (extra armor) on enchantments that the player have on equipped
     * armors.
     */
    public DamageSource b;

    private EnchantmentModifierProtection() {}

    /**
     * Generic method use to calculate modifiers of offensive or defensive enchantment values.
     */
    public void a(Enchantment par1Enchantment, int par2)
    {
        this.a += par1Enchantment.a(par2, this.b);
    }

    EnchantmentModifierProtection(EmptyClass2 par1Empty3)
    {
        this();
    }
}
