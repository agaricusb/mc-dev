package net.minecraft.server;

final class EnchantmentModifierDamage implements EnchantmentModifier
{
    /**
     * Used to calculate the (magic) extra damage based on enchantments of current equipped player item.
     */
    public int a;
    public EntityLiving b;

    private EnchantmentModifierDamage() {}

    /**
     * Generic method use to calculate modifiers of offensive or defensive enchantment values.
     */
    public void a(Enchantment par1Enchantment, int par2)
    {
        this.a += par1Enchantment.a(par2, this.b);
    }

    EnchantmentModifierDamage(EmptyClass2 par1Empty3)
    {
        this();
    }
}
