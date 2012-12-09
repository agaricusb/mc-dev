package net.minecraft.server;

public class WorldData
{
    /** Holds the seed of the currently world. */
    private long seed;
    private WorldType type;
    private String generatorOptions;

    /** The spawn zone position X coordinate. */
    private int spawnX;

    /** The spawn zone position Y coordinate. */
    private int spawnY;

    /** The spawn zone position Z coordinate. */
    private int spawnZ;

    /** Total time for this world. */
    private long time;

    /** The current world time in ticks, ranging from 0 to 23999. */
    private long dayTime;

    /** The last time the player was in this world. */
    private long lastPlayed;

    /** The size of entire save of current world on the disk, isn't exactly. */
    private long sizeOnDisk;
    private NBTTagCompound playerData;
    private int dimension;

    /** The name of the save defined at world creation. */
    private String name;

    /** Introduced in beta 1.3, is the save version for future control. */
    private int version;

    /** True if it's raining, false otherwise. */
    private boolean isRaining;

    /** Number of ticks until next rain. */
    private int rainTicks;

    /** Is thunderbolts failing now? */
    private boolean isThundering;

    /** Number of ticks untils next thunderbolt. */
    private int thunderTicks;

    /** The Game Type. */
    private EnumGamemode gameType;

    /**
     * Whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    private boolean useMapFeatures;

    /** Hardcore mode flag */
    private boolean hardcore;
    private boolean allowCommands;
    private boolean initialized;
    private GameRules gameRules;

    protected WorldData()
    {
        this.type = WorldType.NORMAL;
        this.generatorOptions = "";
        this.gameRules = new GameRules();
    }

    public WorldData(NBTTagCompound par1NBTTagCompound)
    {
        this.type = WorldType.NORMAL;
        this.generatorOptions = "";
        this.gameRules = new GameRules();
        this.seed = par1NBTTagCompound.getLong("RandomSeed");

        if (par1NBTTagCompound.hasKey("generatorName"))
        {
            String var2 = par1NBTTagCompound.getString("generatorName");
            this.type = WorldType.getType(var2);

            if (this.type == null)
            {
                this.type = WorldType.NORMAL;
            }
            else if (this.type.e())
            {
                int var3 = 0;

                if (par1NBTTagCompound.hasKey("generatorVersion"))
                {
                    var3 = par1NBTTagCompound.getInt("generatorVersion");
                }

                this.type = this.type.a(var3);
            }

            if (par1NBTTagCompound.hasKey("generatorOptions"))
            {
                this.generatorOptions = par1NBTTagCompound.getString("generatorOptions");
            }
        }

        this.gameType = EnumGamemode.a(par1NBTTagCompound.getInt("GameType"));

        if (par1NBTTagCompound.hasKey("MapFeatures"))
        {
            this.useMapFeatures = par1NBTTagCompound.getBoolean("MapFeatures");
        }
        else
        {
            this.useMapFeatures = true;
        }

        this.spawnX = par1NBTTagCompound.getInt("SpawnX");
        this.spawnY = par1NBTTagCompound.getInt("SpawnY");
        this.spawnZ = par1NBTTagCompound.getInt("SpawnZ");
        this.time = par1NBTTagCompound.getLong("Time");

        if (par1NBTTagCompound.hasKey("DayTime"))
        {
            this.dayTime = par1NBTTagCompound.getLong("DayTime");
        }
        else
        {
            this.dayTime = this.time;
        }

        this.lastPlayed = par1NBTTagCompound.getLong("LastPlayed");
        this.sizeOnDisk = par1NBTTagCompound.getLong("SizeOnDisk");
        this.name = par1NBTTagCompound.getString("LevelName");
        this.version = par1NBTTagCompound.getInt("version");
        this.rainTicks = par1NBTTagCompound.getInt("rainTime");
        this.isRaining = par1NBTTagCompound.getBoolean("raining");
        this.thunderTicks = par1NBTTagCompound.getInt("thunderTime");
        this.isThundering = par1NBTTagCompound.getBoolean("thundering");
        this.hardcore = par1NBTTagCompound.getBoolean("hardcore");

        if (par1NBTTagCompound.hasKey("initialized"))
        {
            this.initialized = par1NBTTagCompound.getBoolean("initialized");
        }
        else
        {
            this.initialized = true;
        }

        if (par1NBTTagCompound.hasKey("allowCommands"))
        {
            this.allowCommands = par1NBTTagCompound.getBoolean("allowCommands");
        }
        else
        {
            this.allowCommands = this.gameType == EnumGamemode.CREATIVE;
        }

        if (par1NBTTagCompound.hasKey("Player"))
        {
            this.playerData = par1NBTTagCompound.getCompound("Player");
            this.dimension = this.playerData.getInt("Dimension");
        }

        if (par1NBTTagCompound.hasKey("GameRules"))
        {
            this.gameRules.a(par1NBTTagCompound.getCompound("GameRules"));
        }
    }

    public WorldData(WorldSettings par1WorldSettings, String par2Str)
    {
        this.type = WorldType.NORMAL;
        this.generatorOptions = "";
        this.gameRules = new GameRules();
        this.seed = par1WorldSettings.d();
        this.gameType = par1WorldSettings.e();
        this.useMapFeatures = par1WorldSettings.g();
        this.name = par2Str;
        this.hardcore = par1WorldSettings.f();
        this.type = par1WorldSettings.h();
        this.generatorOptions = par1WorldSettings.j();
        this.allowCommands = par1WorldSettings.i();
        this.initialized = false;
    }

    public WorldData(WorldData par1WorldInfo)
    {
        this.type = WorldType.NORMAL;
        this.generatorOptions = "";
        this.gameRules = new GameRules();
        this.seed = par1WorldInfo.seed;
        this.type = par1WorldInfo.type;
        this.generatorOptions = par1WorldInfo.generatorOptions;
        this.gameType = par1WorldInfo.gameType;
        this.useMapFeatures = par1WorldInfo.useMapFeatures;
        this.spawnX = par1WorldInfo.spawnX;
        this.spawnY = par1WorldInfo.spawnY;
        this.spawnZ = par1WorldInfo.spawnZ;
        this.time = par1WorldInfo.time;
        this.dayTime = par1WorldInfo.dayTime;
        this.lastPlayed = par1WorldInfo.lastPlayed;
        this.sizeOnDisk = par1WorldInfo.sizeOnDisk;
        this.playerData = par1WorldInfo.playerData;
        this.dimension = par1WorldInfo.dimension;
        this.name = par1WorldInfo.name;
        this.version = par1WorldInfo.version;
        this.rainTicks = par1WorldInfo.rainTicks;
        this.isRaining = par1WorldInfo.isRaining;
        this.thunderTicks = par1WorldInfo.thunderTicks;
        this.isThundering = par1WorldInfo.isThundering;
        this.hardcore = par1WorldInfo.hardcore;
        this.allowCommands = par1WorldInfo.allowCommands;
        this.initialized = par1WorldInfo.initialized;
        this.gameRules = par1WorldInfo.gameRules;
    }

    /**
     * Gets the NBTTagCompound for the worldInfo
     */
    public NBTTagCompound a()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.a(var1, this.playerData);
        return var1;
    }

    /**
     * Creates a new NBTTagCompound for the world, with the given NBTTag as the "Player"
     */
    public NBTTagCompound a(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagCompound var2 = new NBTTagCompound();
        this.a(var2, par1NBTTagCompound);
        return var2;
    }

    private void a(NBTTagCompound par1NBTTagCompound, NBTTagCompound par2NBTTagCompound)
    {
        par1NBTTagCompound.setLong("RandomSeed", this.seed);
        par1NBTTagCompound.setString("generatorName", this.type.name());
        par1NBTTagCompound.setInt("generatorVersion", this.type.getVersion());
        par1NBTTagCompound.setString("generatorOptions", this.generatorOptions);
        par1NBTTagCompound.setInt("GameType", this.gameType.a());
        par1NBTTagCompound.setBoolean("MapFeatures", this.useMapFeatures);
        par1NBTTagCompound.setInt("SpawnX", this.spawnX);
        par1NBTTagCompound.setInt("SpawnY", this.spawnY);
        par1NBTTagCompound.setInt("SpawnZ", this.spawnZ);
        par1NBTTagCompound.setLong("Time", this.time);
        par1NBTTagCompound.setLong("DayTime", this.dayTime);
        par1NBTTagCompound.setLong("SizeOnDisk", this.sizeOnDisk);
        par1NBTTagCompound.setLong("LastPlayed", System.currentTimeMillis());
        par1NBTTagCompound.setString("LevelName", this.name);
        par1NBTTagCompound.setInt("version", this.version);
        par1NBTTagCompound.setInt("rainTime", this.rainTicks);
        par1NBTTagCompound.setBoolean("raining", this.isRaining);
        par1NBTTagCompound.setInt("thunderTime", this.thunderTicks);
        par1NBTTagCompound.setBoolean("thundering", this.isThundering);
        par1NBTTagCompound.setBoolean("hardcore", this.hardcore);
        par1NBTTagCompound.setBoolean("allowCommands", this.allowCommands);
        par1NBTTagCompound.setBoolean("initialized", this.initialized);
        par1NBTTagCompound.setCompound("GameRules", this.gameRules.a());

        if (par2NBTTagCompound != null)
        {
            par1NBTTagCompound.setCompound("Player", par2NBTTagCompound);
        }
    }

    /**
     * Returns the seed of current world.
     */
    public long getSeed()
    {
        return this.seed;
    }

    /**
     * Returns the x spawn position
     */
    public int c()
    {
        return this.spawnX;
    }

    /**
     * Return the Y axis spawning point of the player.
     */
    public int d()
    {
        return this.spawnY;
    }

    /**
     * Returns the z spawn position
     */
    public int e()
    {
        return this.spawnZ;
    }

    public long getTime()
    {
        return this.time;
    }

    /**
     * Get current world time
     */
    public long getDayTime()
    {
        return this.dayTime;
    }

    /**
     * Returns the player's NBTTagCompound to be loaded
     */
    public NBTTagCompound i()
    {
        return this.playerData;
    }

    public int j()
    {
        return this.dimension;
    }

    public void setTime(long par1)
    {
        this.time = par1;
    }

    /**
     * Set current world time
     */
    public void setDayTime(long par1)
    {
        this.dayTime = par1;
    }

    /**
     * Sets the spawn zone position. Args: x, y, z
     */
    public void setSpawn(int par1, int par2, int par3)
    {
        this.spawnX = par1;
        this.spawnY = par2;
        this.spawnZ = par3;
    }

    /**
     * Get current world name
     */
    public String getName()
    {
        return this.name;
    }

    public void setName(String par1Str)
    {
        this.name = par1Str;
    }

    /**
     * Returns the save version of this world
     */
    public int l()
    {
        return this.version;
    }

    /**
     * Sets the save version of the world
     */
    public void e(int par1)
    {
        this.version = par1;
    }

    /**
     * Returns true if it is thundering, false otherwise.
     */
    public boolean isThundering()
    {
        return this.isThundering;
    }

    /**
     * Sets whether it is thundering or not.
     */
    public void setThundering(boolean par1)
    {
        this.isThundering = par1;
    }

    /**
     * Returns the number of ticks until next thunderbolt.
     */
    public int getThunderDuration()
    {
        return this.thunderTicks;
    }

    /**
     * Defines the number of ticks until next thunderbolt.
     */
    public void setThunderDuration(int par1)
    {
        this.thunderTicks = par1;
    }

    /**
     * Returns true if it is raining, false otherwise.
     */
    public boolean hasStorm()
    {
        return this.isRaining;
    }

    /**
     * Sets whether it is raining or not.
     */
    public void setStorm(boolean par1)
    {
        this.isRaining = par1;
    }

    /**
     * Return the number of ticks until rain.
     */
    public int getWeatherDuration()
    {
        return this.rainTicks;
    }

    /**
     * Sets the number of ticks until rain.
     */
    public void setWeatherDuration(int par1)
    {
        this.rainTicks = par1;
    }

    /**
     * Gets the GameType.
     */
    public EnumGamemode getGameType()
    {
        return this.gameType;
    }

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    public boolean shouldGenerateMapFeatures()
    {
        return this.useMapFeatures;
    }

    /**
     * Sets the GameType.
     */
    public void setGameType(EnumGamemode par1EnumGameType)
    {
        this.gameType = par1EnumGameType;
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean isHardcore()
    {
        return this.hardcore;
    }

    public WorldType getType()
    {
        return this.type;
    }

    public void setType(WorldType par1WorldType)
    {
        this.type = par1WorldType;
    }

    public String getGeneratorOptions()
    {
        return this.generatorOptions;
    }

    /**
     * Returns true if commands are allowed on this World.
     */
    public boolean allowCommands()
    {
        return this.allowCommands;
    }

    /**
     * Returns true if the World is initialized.
     */
    public boolean isInitialized()
    {
        return this.initialized;
    }

    /**
     * Sets the initialization status of the World.
     */
    public void d(boolean par1)
    {
        this.initialized = par1;
    }

    /**
     * Gets the GameRules class Instance.
     */
    public GameRules getGameRules()
    {
        return this.gameRules;
    }

    public void a(CrashReportSystemDetails par1CrashReportCategory)
    {
        par1CrashReportCategory.a("Level seed", new CrashReportLevelSeed(this));
        par1CrashReportCategory.a("Level generator", new CrashReportLevelGenerator(this));
        par1CrashReportCategory.a("Level generator options", new CrashReportLevelGeneratorOptions(this));
        par1CrashReportCategory.a("Level spawn location", new CrashReportLevelSpawnLocation(this));
        par1CrashReportCategory.a("Level time", new CrashReportLevelTime(this));
        par1CrashReportCategory.a("Level dimension", new CrashReportLevelDimension(this));
        par1CrashReportCategory.a("Level storage version", new CrashReportLevelStorageVersion(this));
        par1CrashReportCategory.a("Level weather", new CrashReportLevelWeather(this));
        par1CrashReportCategory.a("Level game mode", new CrashReportLevelGameMode(this));
    }

    static WorldType a(WorldData par0WorldInfo)
    {
        return par0WorldInfo.type;
    }

    static boolean b(WorldData par0WorldInfo)
    {
        return par0WorldInfo.useMapFeatures;
    }

    static String c(WorldData par0WorldInfo)
    {
        return par0WorldInfo.generatorOptions;
    }

    static int d(WorldData par0WorldInfo)
    {
        return par0WorldInfo.spawnX;
    }

    static int e(WorldData par0WorldInfo)
    {
        return par0WorldInfo.spawnY;
    }

    static int f(WorldData par0WorldInfo)
    {
        return par0WorldInfo.spawnZ;
    }

    static long g(WorldData par0WorldInfo)
    {
        return par0WorldInfo.time;
    }

    static long h(WorldData par0WorldInfo)
    {
        return par0WorldInfo.dayTime;
    }

    static int i(WorldData par0WorldInfo)
    {
        return par0WorldInfo.dimension;
    }

    static int j(WorldData par0WorldInfo)
    {
        return par0WorldInfo.version;
    }

    static int k(WorldData par0WorldInfo)
    {
        return par0WorldInfo.rainTicks;
    }

    static boolean l(WorldData par0WorldInfo)
    {
        return par0WorldInfo.isRaining;
    }

    static int m(WorldData par0WorldInfo)
    {
        return par0WorldInfo.thunderTicks;
    }

    static boolean n(WorldData par0WorldInfo)
    {
        return par0WorldInfo.isThundering;
    }

    static EnumGamemode o(WorldData par0WorldInfo)
    {
        return par0WorldInfo.gameType;
    }

    static boolean p(WorldData par0WorldInfo)
    {
        return par0WorldInfo.hardcore;
    }

    static boolean q(WorldData par0WorldInfo)
    {
        return par0WorldInfo.allowCommands;
    }
}
