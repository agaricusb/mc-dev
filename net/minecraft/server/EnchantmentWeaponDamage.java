package net.minecraft.server;

public class EnchantmentWeaponDamage extends Enchantment
{
    /** Holds the name to be translated of each protection type. */
    private static final String[] C = new String[] {"all", "undead", "arthropods"};

    /**
     * Holds the base factor of enchantability needed to be able to use the enchant.
     */
    private static final int[] D = new int[] {1, 5, 5};

    /**
     * Holds how much each level increased the enchantability factor to be able to use this enchant.
     */
    private static final int[] E = new int[] {11, 8, 8};

    /**
     * Used on the formula of base enchantability, this is the 'window' factor of values to be able to use thing
     * enchant.
     */
    private static final int[] F = new int[] {20, 20, 20};

    /**
     * Defines the type of damage of the enchantment, 0 = all, 1 = undead, 3 = arthropods
     */
    public final int a;

    public EnchantmentWeaponDamage(int par1, int par2, int par3)
    {
        super(par1, par2, EnchantmentSlotType.WEAPON);
        this.a = par3;
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return D[this.a] + (par1 - 1) * E[this.a];
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int b(int par1)
    {
        return this.a(par1) + F[this.a];
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 5;
    }

    /**
     * Calculates de (magic) damage done by the enchantment on a living entity based on level and entity passed.
     */
    public int a(int par1, EntityLiving par2EntityLiving)
    {
        return this.a == 0 ? MathHelper.d((float) par1 * 2.75F) : (this.a == 1 && par2EntityLiving.getMonsterType() == EnumMonsterType.UNDEAD ? MathHelper.d((float) par1 * 4.5F) : (this.a == 2 && par2EntityLiving.getMonsterType() == EnumMonsterType.ARTHROPOD ? MathHelper.d((float) par1 * 4.5F) : 0));
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    public String a()
    {
        return "enchantment.damage." + C[this.a];
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean a(Enchantment par1Enchantment)
    {
        return !(par1Enchantment instanceof EnchantmentWeaponDamage);
    }

    public boolean canEnchant(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem() instanceof ItemAxe ? true : super.canEnchant(par1ItemStack);
    }
}
