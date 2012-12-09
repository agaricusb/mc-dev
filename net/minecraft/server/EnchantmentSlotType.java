package net.minecraft.server;

public enum EnchantmentSlotType
{
    ALL,
    ARMOR,
    ARMOR_FEET,
    ARMOR_LEGS,
    ARMOR_TORSO,
    ARMOR_HEAD,
    WEAPON,
    DIGGER,
    BOW;

    /**
     * Return true if the item passed can be enchanted by a enchantment of this type.
     */
    public boolean canEnchant(Item par1Item)
    {
        if (this == ALL)
        {
            return true;
        }
        else if (par1Item instanceof ItemArmor)
        {
            if (this == ARMOR)
            {
                return true;
            }
            else
            {
                ItemArmor var2 = (ItemArmor)par1Item;
                return var2.a == 0 ? this == ARMOR_HEAD : (var2.a == 2 ? this == ARMOR_LEGS : (var2.a == 1 ? this == ARMOR_TORSO : (var2.a == 3 ? this == ARMOR_FEET : false)));
            }
        }
        else
        {
            return par1Item instanceof ItemSword ? this == WEAPON : (par1Item instanceof ItemTool ? this == DIGGER : (par1Item instanceof ItemBow ? this == BOW : false));
        }
    }
}
