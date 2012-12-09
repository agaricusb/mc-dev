package net.minecraft.server;

public abstract class WorldProvider
{
    /** world object being used */
    public World a;
    public WorldType type;
    public String c;

    /** World chunk manager being used to generate chunks */
    public WorldChunkManager d;

    /**
     * States whether the Hell world provider is used(true) or if the normal world provider is used(false)
     */
    public boolean e = false;

    /**
     * A boolean that tells if a world does not have a sky. Used in calculating weather and skylight
     */
    public boolean f = false;

    /** Light to brightness conversion table */
    public float[] g = new float[16];

    /** The id for the dimension (ex. -1: Nether, 0: Overworld, 1: The End) */
    public int dimension = 0;

    /** Array for sunrise/sunset colors (RGBA) */
    private float[] i = new float[4];

    /**
     * associate an existing world with a World provider, and setup its lightbrightness table
     */
    public final void a(World par1World)
    {
        this.a = par1World;
        this.type = par1World.getWorldData().getType();
        this.c = par1World.getWorldData().getGeneratorOptions();
        this.b();
        this.a();
    }

    /**
     * Creates the light to brightness table
     */
    protected void a()
    {
        float var1 = 0.0F;

        for (int var2 = 0; var2 <= 15; ++var2)
        {
            float var3 = 1.0F - (float)var2 / 15.0F;
            this.g[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
        }
    }

    /**
     * creates a new world chunk manager for WorldProvider
     */
    protected void b()
    {
        if (this.a.getWorldData().getType() == WorldType.FLAT)
        {
            WorldGenFlatInfo var1 = WorldGenFlatInfo.a(this.a.getWorldData().getGeneratorOptions());
            this.d = new WorldChunkManagerHell(BiomeBase.biomes[var1.a()], 0.5F, 0.5F);
        }
        else
        {
            this.d = new WorldChunkManager(this.a);
        }
    }

    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkProvider getChunkProvider()
    {
        return (IChunkProvider)(this.type == WorldType.FLAT ? new ChunkProviderFlat(this.a, this.a.getSeed(), this.a.getWorldData().shouldGenerateMapFeatures(), this.c) : new ChunkProviderGenerate(this.a, this.a.getSeed(), this.a.getWorldData().shouldGenerateMapFeatures()));
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canSpawn(int par1, int par2)
    {
        int var3 = this.a.b(par1, par2);
        return var3 == Block.GRASS.id;
    }

    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    public float a(long par1, float par3)
    {
        int var4 = (int)(par1 % 24000L);
        float var5 = ((float)var4 + par3) / 24000.0F - 0.25F;

        if (var5 < 0.0F)
        {
            ++var5;
        }

        if (var5 > 1.0F)
        {
            --var5;
        }

        float var6 = var5;
        var5 = 1.0F - (float)((Math.cos((double)var5 * Math.PI) + 1.0D) / 2.0D);
        var5 = var6 + (var5 - var6) / 3.0F;
        return var5;
    }

    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
     */
    public boolean d()
    {
        return true;
    }

    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean e()
    {
        return true;
    }

    public static WorldProvider byDimension(int par0)
    {
        return (WorldProvider)(par0 == -1 ? new WorldProviderHell() : (par0 == 0 ? new WorldProviderNormal() : (par0 == 1 ? new WorldProviderTheEnd() : null)));
    }

    /**
     * Gets the hard-coded portal location to use when entering this dimension.
     */
    public ChunkCoordinates h()
    {
        return null;
    }

    public int getSeaLevel()
    {
        return this.type == WorldType.FLAT ? 4 : 64;
    }

    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    public abstract String getName();
}
