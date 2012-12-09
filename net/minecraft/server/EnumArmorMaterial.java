package net.minecraft.server;

public enum EnumArmorMaterial
{
    CLOTH(5, new int[]{1, 3, 2, 1}, 15),
    CHAIN(15, new int[]{2, 5, 4, 1}, 12),
    IRON(15, new int[]{2, 6, 5, 2}, 9),
    GOLD(7, new int[]{2, 5, 3, 1}, 25),
    DIAMOND(33, new int[]{3, 8, 6, 3}, 10);

    /**
     * Holds the maximum damage factor (each piece multiply this by it's own value) of the material, this is the item
     * damage (how much can absorb before breaks)
     */
    private int f;

    /**
     * Holds the damage reduction (each 1 points is half a shield on gui) of each piece of armor (helmet, plate, legs
     * and boots)
     */
    private int[] g;

    /** Return the enchantability factor of the material */
    private int h;

    private EnumArmorMaterial(int par3, int[] par4ArrayOfInteger, int par5)
    {
        this.f = par3;
        this.g = par4ArrayOfInteger;
        this.h = par5;
    }

    /**
     * Returns the durability for a armor slot of for this type.
     */
    public int a(int par1)
    {
        return ItemArmor.e()[par1] * this.f;
    }

    /**
     * Return the damage reduction (each 1 point is a half a shield on gui) of the piece index passed (0 = helmet, 1 =
     * plate, 2 = legs and 3 = boots)
     */
    public int b(int par1)
    {
        return this.g[par1];
    }

    /**
     * Return the enchantability factor of the material.
     */
    public int a()
    {
        return this.h;
    }

    /**
     * Return the crafting material for this armor material, used to determine the item that can be used to repair an
     * armor piece with an anvil
     */
    public int b()
    {
        return this == CLOTH ? Item.LEATHER.id : (this == CHAIN ? Item.IRON_INGOT.id : (this == GOLD ? Item.GOLD_INGOT.id : (this == IRON ? Item.IRON_INGOT.id : (this == DIAMOND ? Item.DIAMOND.id : 0))));
    }
}
