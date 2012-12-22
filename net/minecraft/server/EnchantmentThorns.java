package net.minecraft.server;

import java.util.Random;

public class EnchantmentThorns extends Enchantment
{
    public EnchantmentThorns(int par1, int par2)
    {
        super(par1, par2, EnchantmentSlotType.ARMOR_TORSO);
        this.b("thorns");
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
        return 3;
    }

    public boolean canEnchant(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem() instanceof ItemArmor ? true : super.canEnchant(par1ItemStack);
    }

    public static boolean a(int par0, Random par1Random)
    {
        return par0 <= 0 ? false : par1Random.nextFloat() < 0.15F * (float)par0;
    }

    public static int b(int par0, Random par1)
    {
        return par0 > 10 ? par0 - 10 : 1 + par1.nextInt(4);
    }

    public static void a(Entity par0Entity, EntityLiving par1, Random par2Random)
    {
        int var3 = EnchantmentManager.getThornsEnchantmentLevel(par1);
        ItemStack var4 = EnchantmentManager.a(Enchantment.THORNS, par1);

        if (a(var3, par2Random))
        {
            par0Entity.damageEntity(DamageSource.a(par1), b(var3, par2Random));
            par0Entity.makeSound("damage.thorns", 0.5F, 1.0F);

            if (var4 != null)
            {
                var4.damage(3, par1);
            }
        }
        else if (var4 != null)
        {
            var4.damage(1, par1);
        }
    }
}
