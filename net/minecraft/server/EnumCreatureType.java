package net.minecraft.server;

public enum EnumCreatureType
{
    MONSTER(IMonster.class, 70, Material.AIR, false, false),
    CREATURE(EntityAnimal.class, 10, Material.AIR, true, true),
    AMBIENT(EntityAmbient.class, 15, Material.AIR, true, false),
    WATER_CREATURE(EntityWaterAnimal.class, 5, Material.WATER, true, false);

    /**
     * The root class of creatures associated with this EnumCreatureType (IMobs for aggressive creatures, EntityAnimals
     * for friendly ones)
     */
    private final Class e;
    private final int f;
    private final Material g;

    /** A flag indicating whether this creature type is peaceful. */
    private final boolean h;

    /** Whether this creature type is an animal. */
    private final boolean i;

    private EnumCreatureType(Class par3Class, int par4, Material par5Material, boolean par6, boolean par7)
    {
        this.e = par3Class;
        this.f = par4;
        this.g = par5Material;
        this.h = par6;
        this.i = par7;
    }

    public Class a()
    {
        return this.e;
    }

    public int b()
    {
        return this.f;
    }

    public Material c()
    {
        return this.g;
    }

    /**
     * Gets whether or not this creature type is peaceful.
     */
    public boolean d()
    {
        return this.h;
    }

    /**
     * Return whether this creature type is an animal.
     */
    public boolean e()
    {
        return this.i;
    }
}
