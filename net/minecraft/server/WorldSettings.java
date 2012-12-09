package net.minecraft.server;

public final class WorldSettings
{
    /** The seed for the map. */
    private final long a;

    /** The EnumGameType. */
    private final EnumGamemode b;

    /**
     * Switch for the map features. 'true' for enabled, 'false' for disabled.
     */
    private final boolean c;

    /** True if hardcore mode is enabled */
    private final boolean d;
    private final WorldType e;

    /** True if Commands (cheats) are allowed. */
    private boolean f;

    /** True if the Bonus Chest is enabled. */
    private boolean g;
    private String h;

    public WorldSettings(long par1, EnumGamemode par3EnumGameType, boolean par4, boolean par5, WorldType par6WorldType)
    {
        this.h = "";
        this.a = par1;
        this.b = par3EnumGameType;
        this.c = par4;
        this.d = par5;
        this.e = par6WorldType;
    }

    public WorldSettings(WorldData par1WorldInfo)
    {
        this(par1WorldInfo.getSeed(), par1WorldInfo.getGameType(), par1WorldInfo.shouldGenerateMapFeatures(), par1WorldInfo.isHardcore(), par1WorldInfo.getType());
    }

    /**
     * Enables the bonus chest.
     */
    public WorldSettings a()
    {
        this.g = true;
        return this;
    }

    public WorldSettings a(String par1Str)
    {
        this.h = par1Str;
        return this;
    }

    /**
     * Returns true if the Bonus Chest is enabled.
     */
    public boolean c()
    {
        return this.g;
    }

    /**
     * Get the seed of the map.
     */
    public long d()
    {
        return this.a;
    }

    /**
     * Gets the game type.
     */
    public EnumGamemode e()
    {
        return this.b;
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean f()
    {
        return this.d;
    }

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    public boolean g()
    {
        return this.c;
    }

    public WorldType h()
    {
        return this.e;
    }

    /**
     * Returns true if Commands (cheats) are allowed.
     */
    public boolean i()
    {
        return this.f;
    }

    /**
     * Gets the GameType by ID
     */
    public static EnumGamemode a(int par0)
    {
        return EnumGamemode.a(par0);
    }

    public String j()
    {
        return this.h;
    }
}
