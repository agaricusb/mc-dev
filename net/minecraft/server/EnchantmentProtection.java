package net.minecraft.server;

public class EnchantmentProtection extends Enchantment
{
    /** Holds the name to be translated of each protection type. */
    private static final String[] C = new String[] {"all", "fire", "fall", "explosion", "projectile"};

    /**
     * Holds the base factor of enchantability needed to be able to use the enchant.
     */
    private static final int[] D = new int[] {1, 10, 5, 5, 3};

    /**
     * Holds how much each level increased the enchantability factor to be able to use this enchant.
     */
    private static final int[] E = new int[] {11, 8, 6, 8, 6};

    /**
     * Used on the formula of base enchantability, this is the 'window' factor of values to be able to use thing
     * enchant.
     */
    private static final int[] F = new int[] {20, 12, 10, 12, 15};

    /**
     * Defines the type of protection of the enchantment, 0 = all, 1 = fire, 2 = fall (feather fall), 3 = explosion and
     * 4 = projectile.
     */
    public final int a;

    public EnchantmentProtection(int par1, int par2, int par3)
    {
        super(par1, par2, EnchantmentSlotType.ARMOR);
        this.a = par3;

        if (par3 == 2)
        {
            this.slot = EnchantmentSlotType.ARMOR_FEET;
        }
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
        return 4;
    }

    /**
     * Calculates de damage protection of the enchantment based on level and damage source passed.
     */
    public int a(int par1, DamageSource par2DamageSource)
    {
        if (par2DamageSource.ignoresInvulnerability())
        {
            return 0;
        }
        else
        {
            float var3 = (float)(6 + par1 * par1) / 3.0F;
            return this.a == 0 ? MathHelper.d(var3 * 0.75F) : (this.a == 1 && par2DamageSource.k() ? MathHelper.d(var3 * 1.25F) : (this.a == 2 && par2DamageSource == DamageSource.FALL ? MathHelper.d(var3 * 2.5F) : ((this.a != 3 || par2DamageSource != DamageSource.EXPLOSION) && par2DamageSource != DamageSource.EXPLOSION2 ? (this.a == 4 && par2DamageSource.a() ? MathHelper.d(var3 * 1.5F) : 0) : MathHelper.d(var3 * 1.5F))));
        }
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    public String a()
    {
        return "enchantment.protect." + C[this.a];
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean a(Enchantment par1Enchantment)
    {
        if (par1Enchantment instanceof EnchantmentProtection)
        {
            EnchantmentProtection var2 = (EnchantmentProtection)par1Enchantment;
            return var2.a == this.a ? false : this.a == 2 || var2.a == 2;
        }
        else
        {
            return super.a(par1Enchantment);
        }
    }

    public static int a(Entity par0Entity, int par1)
    {
        int var2 = EnchantmentManager.getEnchantmentLevel(Enchantment.PROTECTION_FIRE.id, par0Entity.getEquipment());

        if (var2 > 0)
        {
            par1 -= MathHelper.d((float) par1 * (float) var2 * 0.15F);
        }

        return par1;
    }

    public static double a(Entity par0Entity, double par1)
    {
        int var3 = EnchantmentManager.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS.id, par0Entity.getEquipment());

        if (var3 > 0)
        {
            par1 -= (double) MathHelper.floor(par1 * (double) ((float) var3 * 0.15F));
        }

        return par1;
    }
}
