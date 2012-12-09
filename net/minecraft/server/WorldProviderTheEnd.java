package net.minecraft.server;

public class WorldProviderTheEnd extends WorldProvider
{
    /**
     * creates a new world chunk manager for WorldProvider
     */
    public void b()
    {
        this.d = new WorldChunkManagerHell(BiomeBase.SKY, 0.5F, 0.0F);
        this.dimension = 1;
        this.f = true;
    }

    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkProvider getChunkProvider()
    {
        return new ChunkProviderTheEnd(this.a, this.a.getSeed());
    }

    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    public float a(long par1, float par3)
    {
        return 0.0F;
    }

    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean e()
    {
        return false;
    }

    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
     */
    public boolean d()
    {
        return false;
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canSpawn(int par1, int par2)
    {
        int var3 = this.a.b(par1, par2);
        return var3 == 0 ? false : Block.byId[var3].material.isSolid();
    }

    /**
     * Gets the hard-coded portal location to use when entering this dimension.
     */
    public ChunkCoordinates h()
    {
        return new ChunkCoordinates(100, 50, 0);
    }

    public int getSeaLevel()
    {
        return 50;
    }

    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    public String getName()
    {
        return "The End";
    }
}
