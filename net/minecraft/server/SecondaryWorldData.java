package net.minecraft.server;

public class SecondaryWorldData extends WorldData
{
    /** Instance of WorldInfo. */
    private final WorldData a;

    public SecondaryWorldData(WorldData par1WorldInfo)
    {
        this.a = par1WorldInfo;
    }

    /**
     * Gets the NBTTagCompound for the worldInfo
     */
    public NBTTagCompound a()
    {
        return this.a.a();
    }

    /**
     * Creates a new NBTTagCompound for the world, with the given NBTTag as the "Player"
     */
    public NBTTagCompound a(NBTTagCompound par1NBTTagCompound)
    {
        return this.a.a(par1NBTTagCompound);
    }

    /**
     * Returns the seed of current world.
     */
    public long getSeed()
    {
        return this.a.getSeed();
    }

    /**
     * Returns the x spawn position
     */
    public int c()
    {
        return this.a.c();
    }

    /**
     * Return the Y axis spawning point of the player.
     */
    public int d()
    {
        return this.a.d();
    }

    /**
     * Returns the z spawn position
     */
    public int e()
    {
        return this.a.e();
    }

    public long getTime()
    {
        return this.a.getTime();
    }

    /**
     * Get current world time
     */
    public long getDayTime()
    {
        return this.a.getDayTime();
    }

    /**
     * Returns the player's NBTTagCompound to be loaded
     */
    public NBTTagCompound i()
    {
        return this.a.i();
    }

    public int j()
    {
        return this.a.j();
    }

    /**
     * Get current world name
     */
    public String getName()
    {
        return this.a.getName();
    }

    /**
     * Returns the save version of this world
     */
    public int l()
    {
        return this.a.l();
    }

    /**
     * Returns true if it is thundering, false otherwise.
     */
    public boolean isThundering()
    {
        return this.a.isThundering();
    }

    /**
     * Returns the number of ticks until next thunderbolt.
     */
    public int getThunderDuration()
    {
        return this.a.getThunderDuration();
    }

    /**
     * Returns true if it is raining, false otherwise.
     */
    public boolean hasStorm()
    {
        return this.a.hasStorm();
    }

    /**
     * Return the number of ticks until rain.
     */
    public int getWeatherDuration()
    {
        return this.a.getWeatherDuration();
    }

    /**
     * Gets the GameType.
     */
    public EnumGamemode getGameType()
    {
        return this.a.getGameType();
    }

    public void setTime(long par1) {}

    /**
     * Set current world time
     */
    public void setDayTime(long par1) {}

    /**
     * Sets the spawn zone position. Args: x, y, z
     */
    public void setSpawn(int par1, int par2, int par3) {}

    public void setName(String par1Str) {}

    /**
     * Sets the save version of the world
     */
    public void e(int par1) {}

    /**
     * Sets whether it is thundering or not.
     */
    public void setThundering(boolean par1) {}

    /**
     * Defines the number of ticks until next thunderbolt.
     */
    public void setThunderDuration(int par1) {}

    /**
     * Sets whether it is raining or not.
     */
    public void setStorm(boolean par1) {}

    /**
     * Sets the number of ticks until rain.
     */
    public void setWeatherDuration(int par1) {}

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    public boolean shouldGenerateMapFeatures()
    {
        return this.a.shouldGenerateMapFeatures();
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean isHardcore()
    {
        return this.a.isHardcore();
    }

    public WorldType getType()
    {
        return this.a.getType();
    }

    public void setType(WorldType par1WorldType) {}

    /**
     * Returns true if commands are allowed on this World.
     */
    public boolean allowCommands()
    {
        return this.a.allowCommands();
    }

    /**
     * Returns true if the World is initialized.
     */
    public boolean isInitialized()
    {
        return this.a.isInitialized();
    }

    /**
     * Sets the initialization status of the World.
     */
    public void d(boolean par1) {}

    /**
     * Gets the GameRules class Instance.
     */
    public GameRules getGameRules()
    {
        return this.a.getGameRules();
    }
}
