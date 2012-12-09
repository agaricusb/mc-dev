package net.minecraft.server;

public class WorldType
{
    /** List of world types. */
    public static final WorldType[] types = new WorldType[16];

    /** Default world type. */
    public static final WorldType NORMAL = (new WorldType(0, "default", 1)).g();

    /** Flat world type. */
    public static final WorldType FLAT = new WorldType(1, "flat");

    /** Large Biome world Type. */
    public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");

    /** Default (1.1) world type. */
    public static final WorldType NORMAL_1_1 = (new WorldType(8, "default_1_1", 0)).a(false);

    /** ID for this world type. */
    private final int f;
    private final String name;

    /** The int version of the ChunkProvider that generated this world. */
    private final int version;

    /**
     * Whether this world type can be generated. Normally true; set to false for out-of-date generator versions.
     */
    private boolean i;

    /** Whether this WorldType has a version or not. */
    private boolean j;

    private WorldType(int par1, String par2Str)
    {
        this(par1, par2Str, 0);
    }

    private WorldType(int par1, String par2Str, int par3)
    {
        this.name = par2Str;
        this.version = par3;
        this.i = true;
        this.f = par1;
        types[par1] = this;
    }

    public String name()
    {
        return this.name;
    }

    /**
     * Returns generatorVersion.
     */
    public int getVersion()
    {
        return this.version;
    }

    public WorldType a(int par1)
    {
        return this == NORMAL && par1 == 0 ? NORMAL_1_1 : this;
    }

    /**
     * Sets canBeCreated to the provided value, and returns this.
     */
    private WorldType a(boolean par1)
    {
        this.i = par1;
        return this;
    }

    /**
     * Flags this world type as having an associated version.
     */
    private WorldType g()
    {
        this.j = true;
        return this;
    }

    /**
     * Returns true if this world Type has a version associated with it.
     */
    public boolean e()
    {
        return this.j;
    }

    public static WorldType getType(String par0Str)
    {
        for (int var1 = 0; var1 < types.length; ++var1)
        {
            if (types[var1] != null && types[var1].name.equalsIgnoreCase(par0Str))
            {
                return types[var1];
            }
        }

        return null;
    }

    public int f()
    {
        return this.f;
    }
}
