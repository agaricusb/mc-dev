package net.minecraft.server;

public enum EnumToolMaterial
{
    WOOD(0, 59, 2.0F, 0, 15),
    STONE(1, 131, 4.0F, 1, 5),
    IRON(2, 250, 6.0F, 2, 14),
    DIAMOND(3, 1561, 8.0F, 3, 10),
    GOLD(0, 32, 12.0F, 0, 22);

    /**
     * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = IRON/GOLD)
     */
    private final int f;

    /**
     * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
     */
    private final int g;

    /**
     * The strength of this tool material against blocks which it is effective against.
     */
    private final float h;

    /** Damage versus entities. */
    private final int i;

    /** Defines the natural enchantability factor of the material. */
    private final int j;

    private EnumToolMaterial(int par3, int par4, float par5, int par6, int par7)
    {
        this.f = par3;
        this.g = par4;
        this.h = par5;
        this.i = par6;
        this.j = par7;
    }

    /**
     * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
     */
    public int a()
    {
        return this.g;
    }

    /**
     * The strength of this tool material against blocks which it is effective against.
     */
    public float b()
    {
        return this.h;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int c()
    {
        return this.i;
    }

    /**
     * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = IRON/GOLD)
     */
    public int d()
    {
        return this.f;
    }

    /**
     * Return the natural enchantability factor of the material.
     */
    public int e()
    {
        return this.j;
    }

    /**
     * Return the crafting material for this tool material, used to determine the item that can be used to repair a tool
     * with an anvil
     */
    public int f()
    {
        return this == WOOD ? Block.WOOD.id : (this == STONE ? Block.COBBLESTONE.id : (this == GOLD ? Item.GOLD_INGOT.id : (this == IRON ? Item.IRON_INGOT.id : (this == DIAMOND ? Item.DIAMOND.id : 0))));
    }
}
