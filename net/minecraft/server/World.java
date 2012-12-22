package net.minecraft.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class World implements IBlockAccess
{
    /**
     * boolean; if true updates scheduled by scheduleBlockUpdate happen immediately
     */
    public boolean d = false;

    /** A list of all Entities in all currently-loaded chunks */
    public List entityList = new ArrayList();
    protected List f = new ArrayList();

    /** A list of all TileEntities in all currently-loaded chunks */
    public List tileEntityList = new ArrayList();
    private List a = new ArrayList();

    /** Entities marked for removal. */
    private List b = new ArrayList();

    /** Array list of players in the world. */
    public List players = new ArrayList();

    /** a list of all the lightning entities */
    public List i = new ArrayList();
    private long c = 16777215L;

    /** How much light is subtracted from full daylight */
    public int j = 0;

    /**
     * Contains the current Linear Congruential Generator seed for block updates. Used with an A value of 3 and a C
     * value of 0x3c6ef35f, producing a highly planar series of values ill-suited for choosing random blocks in a
     * 16x128x16 field.
     */
    protected int k = (new Random()).nextInt();

    /**
     * magic number used to generate fast random numbers for 3d distribution within a chunk
     */
    protected final int l = 1013904223;
    protected float m;
    protected float n;
    protected float o;
    protected float p;

    /**
     * Set to 2 whenever a lightning bolt is generated in SSP. Decrements if > 0 in updateWeather(). Value appears to be
     * unused.
     */
    public int q = 0;

    /** true while the server is editing blocks */
    public boolean suppressPhysics = false;

    /** Whether monsters are enabled or not. (1 = on, 0 = off) */
    public int difficulty;

    /** RNG for World. */
    public Random random = new Random();

    /** The WorldProvider instance that World uses. */
    public final WorldProvider worldProvider;
    protected List v = new ArrayList();

    /** Handles chunk operations and caching */
    protected IChunkProvider chunkProvider;
    protected final IDataManager dataManager;

    /**
     * holds information about a world (size on disk, time, spawn point, seed, ...)
     */
    protected WorldData worldData;

    /**
     * if set, this flag forces a request to load a chunk to load the chunk rather than defaulting to the world's
     * chunkprovider's dummy if possible
     */
    public boolean isLoading;
    public WorldMapCollection worldMaps;
    public final VillageCollection villages;
    protected final VillageSiege siegeManager = new VillageSiege(this);
    public final MethodProfiler methodProfiler;

    /** The world-local pool of vectors */
    private final Vec3DPool J = new Vec3DPool(300, 2000);
    private final Calendar K = Calendar.getInstance();
    private ArrayList L = new ArrayList();
    private boolean M;

    /** indicates if enemies are spawned or not */
    protected boolean allowMonsters = true;

    /** A flag indicating whether we should spawn peaceful mobs. */
    protected boolean allowAnimals = true;

    /** populated by chunks that are within 9 chunks of any player */
    protected Set chunkTickList = new HashSet();

    /** number of ticks until the next random ambients play */
    private int N;

    /**
     * is a temporary list of blocks and light values used when updating light levels. Holds up to 32x32x32 blocks (the
     * maximum influence of a light source.) Every element is a packed bit value: 0000000000LLLLzzzzzzyyyyyyxxxxxx. The
     * 4-bit L is a light level used when darkening blocks. 6-bit numbers x, y and z represent the block's offset from
     * the original block, plus 32 (i.e. value of 31 would mean a -1 offset
     */
    int[] H;

    /**
     * entities within AxisAlignedBB excluding one, set and returned in getEntitiesWithinAABBExcludingEntity(Entity
     * var1, AxisAlignedBB var2)
     */
    private List O;

    /** This is set to true for client worlds, and false for server worlds. */
    public boolean isStatic;

    /**
     * Gets the biome for a given set of x/z coordinates
     */
    public BiomeBase getBiome(int par1, int par2)
    {
        if (this.isLoaded(par1, 0, par2))
        {
            Chunk var3 = this.getChunkAtWorldCoords(par1, par2);

            if (var3 != null)
            {
                return var3.a(par1 & 15, par2 & 15, this.worldProvider.d);
            }
        }

        return this.worldProvider.d.getBiome(par1, par2);
    }

    public WorldChunkManager getWorldChunkManager()
    {
        return this.worldProvider.d;
    }

    public World(IDataManager par1ISaveHandler, String par2Str, WorldSettings par3WorldSettings, WorldProvider par4WorldProvider, MethodProfiler par5Profiler)
    {
        this.N = this.random.nextInt(12000);
        this.H = new int[32768];
        this.O = new ArrayList();
        this.isStatic = false;
        this.dataManager = par1ISaveHandler;
        this.methodProfiler = par5Profiler;
        this.worldMaps = new WorldMapCollection(par1ISaveHandler);
        this.worldData = par1ISaveHandler.getWorldData();

        if (par4WorldProvider != null)
        {
            this.worldProvider = par4WorldProvider;
        }
        else if (this.worldData != null && this.worldData.j() != 0)
        {
            this.worldProvider = WorldProvider.byDimension(this.worldData.j());
        }
        else
        {
            this.worldProvider = WorldProvider.byDimension(0);
        }

        if (this.worldData == null)
        {
            this.worldData = new WorldData(par3WorldSettings, par2Str);
        }
        else
        {
            this.worldData.setName(par2Str);
        }

        this.worldProvider.a(this);
        this.chunkProvider = this.j();

        if (!this.worldData.isInitialized())
        {
            try
            {
                this.a(par3WorldSettings);
            }
            catch (Throwable var10)
            {
                CrashReport var7 = CrashReport.a(var10, "Exception initializing level");

                try
                {
                    this.a(var7);
                }
                catch (Throwable var9)
                {
                    ;
                }

                throw new ReportedException(var7);
            }

            this.worldData.d(true);
        }

        VillageCollection var6 = (VillageCollection)this.worldMaps.get(VillageCollection.class, "villages");

        if (var6 == null)
        {
            this.villages = new VillageCollection(this);
            this.worldMaps.a("villages", this.villages);
        }
        else
        {
            this.villages = var6;
            this.villages.a(this);
        }

        this.x();
        this.a();
    }

    /**
     * Creates the chunk provider for this world. Called in the constructor. Retrieves provider from worldProvider?
     */
    protected abstract IChunkProvider j();

    protected void a(WorldSettings par1WorldSettings)
    {
        this.worldData.d(true);
    }

    /**
     * Returns the block ID of the first block at this (x,z) location with air above it, searching from sea level
     * upwards.
     */
    public int b(int par1, int par2)
    {
        int var3;

        for (var3 = 63; !this.isEmpty(par1, var3 + 1, par2); ++var3)
        {
            ;
        }

        return this.getTypeId(par1, var3, par2);
    }

    /**
     * Returns the block ID at coords x,y,z
     */
    public int getTypeId(int par1, int par2, int par3)
    {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
        {
            if (par2 < 0)
            {
                return 0;
            }
            else if (par2 >= 256)
            {
                return 0;
            }
            else
            {
                Chunk var4 = null;

                try
                {
                    var4 = this.getChunkAt(par1 >> 4, par3 >> 4);
                    return var4.getTypeId(par1 & 15, par2, par3 & 15);
                }
                catch (Throwable var8)
                {
                    CrashReport var6 = CrashReport.a(var8, "Exception getting block type in world");
                    CrashReportSystemDetails var7 = var6.a("Requested block coordinates");
                    var7.a("Found chunk", Boolean.valueOf(var4 == null));
                    var7.a("Location", CrashReportSystemDetails.a(par1, par2, par3));
                    throw new ReportedException(var6);
                }
            }
        }
        else
        {
            return 0;
        }
    }

    public int b(int par1, int par2, int par3)
    {
        return par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000 ? (par2 < 0 ? 0 : (par2 >= 256 ? 0 : this.getChunkAt(par1 >> 4, par3 >> 4).b(par1 & 15, par2, par3 & 15))) : 0;
    }

    /**
     * Returns true if the block at the specified coordinates is empty
     */
    public boolean isEmpty(int par1, int par2, int par3)
    {
        return this.getTypeId(par1, par2, par3) == 0;
    }

    /**
     * Checks if a block at a given position should have a tile entity.
     */
    public boolean isTileEntity(int par1, int par2, int par3)
    {
        int var4 = this.getTypeId(par1, par2, par3);
        return Block.byId[var4] != null && Block.byId[var4].u();
    }

    public int e(int par1, int par2, int par3)
    {
        int var4 = this.getTypeId(par1, par2, par3);
        return Block.byId[var4] != null ? Block.byId[var4].d() : -1;
    }

    /**
     * Returns whether a block exists at world coordinates x, y, z
     */
    public boolean isLoaded(int par1, int par2, int par3)
    {
        return par2 >= 0 && par2 < 256 ? this.isChunkLoaded(par1 >> 4, par3 >> 4) : false;
    }

    /**
     * Checks if any of the chunks within distance (argument 4) blocks of the given block exist
     */
    public boolean areChunksLoaded(int par1, int par2, int par3, int par4)
    {
        return this.d(par1 - par4, par2 - par4, par3 - par4, par1 + par4, par2 + par4, par3 + par4);
    }

    /**
     * Checks between a min and max all the chunks inbetween actually exist. Args: minX, minY, minZ, maxX, maxY, maxZ
     */
    public boolean d(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        if (par5 >= 0 && par2 < 256)
        {
            par1 >>= 4;
            par3 >>= 4;
            par4 >>= 4;
            par6 >>= 4;

            for (int var7 = par1; var7 <= par4; ++var7)
            {
                for (int var8 = par3; var8 <= par6; ++var8)
                {
                    if (!this.isChunkLoaded(var7, var8))
                    {
                        return false;
                    }
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns whether a chunk exists at chunk coordinates x, y
     */
    protected boolean isChunkLoaded(int par1, int par2)
    {
        return this.chunkProvider.isChunkLoaded(par1, par2);
    }

    /**
     * Returns a chunk looked up by block coordinates. Args: x, z
     */
    public Chunk getChunkAtWorldCoords(int par1, int par2)
    {
        return this.getChunkAt(par1 >> 4, par2 >> 4);
    }

    /**
     * Returns back a chunk looked up by chunk coordinates Args: x, y
     */
    public Chunk getChunkAt(int par1, int par2)
    {
        return this.chunkProvider.getOrCreateChunk(par1, par2);
    }

    /**
     * Sets the block ID and metadata of a block in global coordinates
     */
    public boolean setRawTypeIdAndData(int par1, int par2, int par3, int par4, int par5)
    {
        return this.setRawTypeIdAndData(par1, par2, par3, par4, par5, true);
    }

    /**
     * Sets the block ID and metadata of a block, optionally marking it as needing update. Args: X,Y,Z, blockID,
     * metadata, needsUpdate
     */
    public boolean setRawTypeIdAndData(int par1, int par2, int par3, int par4, int par5, boolean par6)
    {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
        {
            if (par2 < 0)
            {
                return false;
            }
            else if (par2 >= 256)
            {
                return false;
            }
            else
            {
                Chunk var7 = this.getChunkAt(par1 >> 4, par3 >> 4);
                boolean var8 = var7.a(par1 & 15, par2, par3 & 15, par4, par5);
                this.methodProfiler.a("checkLight");
                this.z(par1, par2, par3);
                this.methodProfiler.b();

                if (par6 && var8 && (this.isStatic || var7.seenByPlayer))
                {
                    this.notify(par1, par2, par3);
                }

                return var8;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Sets the block to the specified blockID at the block coordinates Args x, y, z, blockID
     */
    public boolean setRawTypeId(int par1, int par2, int par3, int par4)
    {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
        {
            if (par2 < 0)
            {
                return false;
            }
            else if (par2 >= 256)
            {
                return false;
            }
            else
            {
                Chunk var5 = this.getChunkAt(par1 >> 4, par3 >> 4);
                boolean var6 = var5.a(par1 & 15, par2, par3 & 15, par4);
                this.methodProfiler.a("checkLight");
                this.z(par1, par2, par3);
                this.methodProfiler.b();

                if (var6 && (this.isStatic || var5.seenByPlayer))
                {
                    this.notify(par1, par2, par3);
                }

                return var6;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the block's material.
     */
    public Material getMaterial(int par1, int par2, int par3)
    {
        int var4 = this.getTypeId(par1, par2, par3);
        return var4 == 0 ? Material.AIR : Block.byId[var4].material;
    }

    /**
     * Returns the block metadata at coords x,y,z
     */
    public int getData(int par1, int par2, int par3)
    {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
        {
            if (par2 < 0)
            {
                return 0;
            }
            else if (par2 >= 256)
            {
                return 0;
            }
            else
            {
                Chunk var4 = this.getChunkAt(par1 >> 4, par3 >> 4);
                par1 &= 15;
                par3 &= 15;
                return var4.getData(par1, par2, par3);
            }
        }
        else
        {
            return 0;
        }
    }

    /**
     * Sets the blocks metadata and if set will then notify blocks that this block changed. Args: x, y, z, metadata
     */
    public void setData(int par1, int par2, int par3, int par4)
    {
        if (this.setRawData(par1, par2, par3, par4))
        {
            this.update(par1, par2, par3, this.getTypeId(par1, par2, par3));
        }
    }

    /**
     * Set the metadata of a block in global coordinates
     */
    public boolean setRawData(int par1, int par2, int par3, int par4)
    {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
        {
            if (par2 < 0)
            {
                return false;
            }
            else if (par2 >= 256)
            {
                return false;
            }
            else
            {
                Chunk var5 = this.getChunkAt(par1 >> 4, par3 >> 4);
                int var6 = par1 & 15;
                int var7 = par3 & 15;
                boolean var8 = var5.b(var6, par2, var7, par4);

                if (var8 && (this.isStatic || var5.seenByPlayer && Block.u[var5.getTypeId(var6, par2, var7) & 4095]))
                {
                    this.notify(par1, par2, par3);
                }

                return var8;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Sets a block and notifies relevant systems with the block change  Args: x, y, z, blockID
     */
    public boolean setTypeId(int par1, int par2, int par3, int par4)
    {
        if (this.setRawTypeId(par1, par2, par3, par4))
        {
            this.update(par1, par2, par3, par4);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Sets the block ID and metadata, then notifies neighboring blocks of the change Params: x, y, z, BlockID, Metadata
     */
    public boolean setTypeIdAndData(int par1, int par2, int par3, int par4, int par5)
    {
        if (this.setRawTypeIdAndData(par1, par2, par3, par4, par5))
        {
            this.update(par1, par2, par3, par4);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * On the client, re-renders the block. On the server, sends the block to the client (which will re-render it),
     * including the tile entity description packet if applicable. Args: x, y, z
     */
    public void notify(int par1, int par2, int par3)
    {
        for (int var4 = 0; var4 < this.v.size(); ++var4)
        {
            ((IWorldAccess)this.v.get(var4)).a(par1, par2, par3);
        }
    }

    /**
     * The block type change and need to notify other systems  Args: x, y, z, blockID
     */
    public void update(int par1, int par2, int par3, int par4)
    {
        this.applyPhysics(par1, par2, par3, par4);
    }

    /**
     * marks a vertical line of blocks as dirty
     */
    public void g(int par1, int par2, int par3, int par4)
    {
        int var5;

        if (par3 > par4)
        {
            var5 = par4;
            par4 = par3;
            par3 = var5;
        }

        if (!this.worldProvider.f)
        {
            for (var5 = par3; var5 <= par4; ++var5)
            {
                this.c(EnumSkyBlock.SKY, par1, var5, par2);
            }
        }

        this.e(par1, par3, par2, par1, par4, par2);
    }

    /**
     * On the client, re-renders this block. On the server, does nothing. Appears to be redundant.
     */
    public void j(int par1, int par2, int par3)
    {
        for (int var4 = 0; var4 < this.v.size(); ++var4)
        {
            ((IWorldAccess)this.v.get(var4)).a(par1, par2, par3, par1, par2, par3);
        }
    }

    /**
     * On the client, re-renders all blocks in this range, inclusive. On the server, does nothing. Args: min x, min y,
     * min z, max x, max y, max z
     */
    public void e(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        for (int var7 = 0; var7 < this.v.size(); ++var7)
        {
            ((IWorldAccess)this.v.get(var7)).a(par1, par2, par3, par4, par5, par6);
        }
    }

    /**
     * Notifies neighboring blocks that this specified block changed  Args: x, y, z, blockID
     */
    public void applyPhysics(int par1, int par2, int par3, int par4)
    {
        this.m(par1 - 1, par2, par3, par4);
        this.m(par1 + 1, par2, par3, par4);
        this.m(par1, par2 - 1, par3, par4);
        this.m(par1, par2 + 1, par3, par4);
        this.m(par1, par2, par3 - 1, par4);
        this.m(par1, par2, par3 + 1, par4);
    }

    /**
     * Notifies a block that one of its neighbor change to the specified type Args: x, y, z, blockID
     */
    private void m(int par1, int par2, int par3, int par4)
    {
        if (!this.suppressPhysics && !this.isStatic)
        {
            int var5 = this.getTypeId(par1, par2, par3);
            Block var6 = Block.byId[var5];

            if (var6 != null)
            {
                try
                {
                    var6.doPhysics(this, par1, par2, par3, par4);
                }
                catch (Throwable var13)
                {
                    CrashReport var8 = CrashReport.a(var13, "Exception while updating neighbours");
                    CrashReportSystemDetails var9 = var8.a("Block being updated");
                    int var10;

                    try
                    {
                        var10 = this.getData(par1, par2, par3);
                    }
                    catch (Throwable var12)
                    {
                        var10 = -1;
                    }

                    var9.a("Source block type", new CrashReportSourceBlockType(this, par4));
                    CrashReportSystemDetails.a(var9, par1, par2, par3, var5, var10);
                    throw new ReportedException(var8);
                }
            }
        }
    }

    /**
     * Checks if the specified block is able to see the sky
     */
    public boolean k(int par1, int par2, int par3)
    {
        return this.getChunkAt(par1 >> 4, par3 >> 4).d(par1 & 15, par2, par3 & 15);
    }

    /**
     * gets the block's light value - without the _do function's checks.
     */
    public int l(int par1, int par2, int par3)
    {
        if (par2 < 0)
        {
            return 0;
        }
        else
        {
            if (par2 >= 256)
            {
                par2 = 255;
            }

            return this.getChunkAt(par1 >> 4, par3 >> 4).c(par1 & 15, par2, par3 & 15, 0);
        }
    }

    /**
     * Gets the light value of a block location
     */
    public int getLightLevel(int par1, int par2, int par3)
    {
        return this.a(par1, par2, par3, true);
    }

    /**
     * Gets the light value of a block location. This is the actual function that gets the value and has a bool flag
     * that indicates if its a half step block to get the maximum light value of a direct neighboring block (left,
     * right, forward, back, and up)
     */
    public int a(int par1, int par2, int par3, boolean par4)
    {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
        {
            if (par4)
            {
                int var5 = this.getTypeId(par1, par2, par3);

                if (var5 == Block.STEP.id || var5 == Block.WOOD_STEP.id || var5 == Block.SOIL.id || var5 == Block.COBBLESTONE_STAIRS.id || var5 == Block.WOOD_STAIRS.id)
                {
                    int var6 = this.a(par1, par2 + 1, par3, false);
                    int var7 = this.a(par1 + 1, par2, par3, false);
                    int var8 = this.a(par1 - 1, par2, par3, false);
                    int var9 = this.a(par1, par2, par3 + 1, false);
                    int var10 = this.a(par1, par2, par3 - 1, false);

                    if (var7 > var6)
                    {
                        var6 = var7;
                    }

                    if (var8 > var6)
                    {
                        var6 = var8;
                    }

                    if (var9 > var6)
                    {
                        var6 = var9;
                    }

                    if (var10 > var6)
                    {
                        var6 = var10;
                    }

                    return var6;
                }
            }

            if (par2 < 0)
            {
                return 0;
            }
            else
            {
                if (par2 >= 256)
                {
                    par2 = 255;
                }

                Chunk var11 = this.getChunkAt(par1 >> 4, par3 >> 4);
                par1 &= 15;
                par3 &= 15;
                return var11.c(par1, par2, par3, this.j);
            }
        }
        else
        {
            return 15;
        }
    }

    /**
     * Returns the y coordinate with a block in it at this x, z coordinate
     */
    public int getHighestBlockYAt(int par1, int par2)
    {
        if (par1 >= -30000000 && par2 >= -30000000 && par1 < 30000000 && par2 < 30000000)
        {
            if (!this.isChunkLoaded(par1 >> 4, par2 >> 4))
            {
                return 0;
            }
            else
            {
                Chunk var3 = this.getChunkAt(par1 >> 4, par2 >> 4);
                return var3.b(par1 & 15, par2 & 15);
            }
        }
        else
        {
            return 0;
        }
    }

    public int g(int par1, int par2)
    {
        if (par1 >= -30000000 && par2 >= -30000000 && par1 < 30000000 && par2 < 30000000)
        {
            if (!this.isChunkLoaded(par1 >> 4, par2 >> 4))
            {
                return 0;
            }
            else
            {
                Chunk var3 = this.getChunkAt(par1 >> 4, par2 >> 4);
                return var3.p;
            }
        }
        else
        {
            return 0;
        }
    }

    /**
     * Returns saved light value without taking into account the time of day.  Either looks in the sky light map or
     * block light map based on the enumSkyBlock arg.
     */
    public int b(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
    {
        if (par3 < 0)
        {
            par3 = 0;
        }

        if (par3 >= 256)
        {
            par3 = 255;
        }

        if (par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 < 30000000)
        {
            int var5 = par2 >> 4;
            int var6 = par4 >> 4;

            if (!this.isChunkLoaded(var5, var6))
            {
                return par1EnumSkyBlock.c;
            }
            else
            {
                Chunk var7 = this.getChunkAt(var5, var6);
                return var7.getBrightness(par1EnumSkyBlock, par2 & 15, par3, par4 & 15);
            }
        }
        else
        {
            return par1EnumSkyBlock.c;
        }
    }

    /**
     * Sets the light value either into the sky map or block map depending on if enumSkyBlock is set to sky or block.
     * Args: enumSkyBlock, x, y, z, lightValue
     */
    public void b(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5)
    {
        if (par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 < 30000000)
        {
            if (par3 >= 0)
            {
                if (par3 < 256)
                {
                    if (this.isChunkLoaded(par2 >> 4, par4 >> 4))
                    {
                        Chunk var6 = this.getChunkAt(par2 >> 4, par4 >> 4);
                        var6.a(par1EnumSkyBlock, par2 & 15, par3, par4 & 15, par5);

                        for (int var7 = 0; var7 < this.v.size(); ++var7)
                        {
                            ((IWorldAccess)this.v.get(var7)).b(par2, par3, par4);
                        }
                    }
                }
            }
        }
    }

    /**
     * On the client, re-renders this block. On the server, does nothing. Used for lighting updates.
     */
    public void o(int par1, int par2, int par3)
    {
        for (int var4 = 0; var4 < this.v.size(); ++var4)
        {
            ((IWorldAccess)this.v.get(var4)).b(par1, par2, par3);
        }
    }

    /**
     * Returns how bright the block is shown as which is the block's light value looked up in a lookup table (light
     * values aren't linear for brightness). Args: x, y, z
     */
    public float p(int par1, int par2, int par3)
    {
        return this.worldProvider.g[this.getLightLevel(par1, par2, par3)];
    }

    /**
     * Checks whether its daytime by seeing if the light subtracted from the skylight is less than 4
     */
    public boolean u()
    {
        return this.j < 4;
    }

    /**
     * ray traces all blocks, including non-collideable ones
     */
    public MovingObjectPosition a(Vec3D par1Vec3, Vec3D par2Vec3)
    {
        return this.rayTrace(par1Vec3, par2Vec3, false, false);
    }

    public MovingObjectPosition rayTrace(Vec3D par1Vec3, Vec3D par2Vec3, boolean par3)
    {
        return this.rayTrace(par1Vec3, par2Vec3, par3, false);
    }

    public MovingObjectPosition rayTrace(Vec3D par1Vec3, Vec3D par2Vec3, boolean par3, boolean par4)
    {
        if (!Double.isNaN(par1Vec3.c) && !Double.isNaN(par1Vec3.d) && !Double.isNaN(par1Vec3.e))
        {
            if (!Double.isNaN(par2Vec3.c) && !Double.isNaN(par2Vec3.d) && !Double.isNaN(par2Vec3.e))
            {
                int var5 = MathHelper.floor(par2Vec3.c);
                int var6 = MathHelper.floor(par2Vec3.d);
                int var7 = MathHelper.floor(par2Vec3.e);
                int var8 = MathHelper.floor(par1Vec3.c);
                int var9 = MathHelper.floor(par1Vec3.d);
                int var10 = MathHelper.floor(par1Vec3.e);
                int var11 = this.getTypeId(var8, var9, var10);
                int var12 = this.getData(var8, var9, var10);
                Block var13 = Block.byId[var11];

                if ((!par4 || var13 == null || var13.e(this, var8, var9, var10) != null) && var11 > 0 && var13.a(var12, par3))
                {
                    MovingObjectPosition var14 = var13.a(this, var8, var9, var10, par1Vec3, par2Vec3);

                    if (var14 != null)
                    {
                        return var14;
                    }
                }

                var11 = 200;

                while (var11-- >= 0)
                {
                    if (Double.isNaN(par1Vec3.c) || Double.isNaN(par1Vec3.d) || Double.isNaN(par1Vec3.e))
                    {
                        return null;
                    }

                    if (var8 == var5 && var9 == var6 && var10 == var7)
                    {
                        return null;
                    }

                    boolean var39 = true;
                    boolean var40 = true;
                    boolean var41 = true;
                    double var15 = 999.0D;
                    double var17 = 999.0D;
                    double var19 = 999.0D;

                    if (var5 > var8)
                    {
                        var15 = (double)var8 + 1.0D;
                    }
                    else if (var5 < var8)
                    {
                        var15 = (double)var8 + 0.0D;
                    }
                    else
                    {
                        var39 = false;
                    }

                    if (var6 > var9)
                    {
                        var17 = (double)var9 + 1.0D;
                    }
                    else if (var6 < var9)
                    {
                        var17 = (double)var9 + 0.0D;
                    }
                    else
                    {
                        var40 = false;
                    }

                    if (var7 > var10)
                    {
                        var19 = (double)var10 + 1.0D;
                    }
                    else if (var7 < var10)
                    {
                        var19 = (double)var10 + 0.0D;
                    }
                    else
                    {
                        var41 = false;
                    }

                    double var21 = 999.0D;
                    double var23 = 999.0D;
                    double var25 = 999.0D;
                    double var27 = par2Vec3.c - par1Vec3.c;
                    double var29 = par2Vec3.d - par1Vec3.d;
                    double var31 = par2Vec3.e - par1Vec3.e;

                    if (var39)
                    {
                        var21 = (var15 - par1Vec3.c) / var27;
                    }

                    if (var40)
                    {
                        var23 = (var17 - par1Vec3.d) / var29;
                    }

                    if (var41)
                    {
                        var25 = (var19 - par1Vec3.e) / var31;
                    }

                    boolean var33 = false;
                    byte var42;

                    if (var21 < var23 && var21 < var25)
                    {
                        if (var5 > var8)
                        {
                            var42 = 4;
                        }
                        else
                        {
                            var42 = 5;
                        }

                        par1Vec3.c = var15;
                        par1Vec3.d += var29 * var21;
                        par1Vec3.e += var31 * var21;
                    }
                    else if (var23 < var25)
                    {
                        if (var6 > var9)
                        {
                            var42 = 0;
                        }
                        else
                        {
                            var42 = 1;
                        }

                        par1Vec3.c += var27 * var23;
                        par1Vec3.d = var17;
                        par1Vec3.e += var31 * var23;
                    }
                    else
                    {
                        if (var7 > var10)
                        {
                            var42 = 2;
                        }
                        else
                        {
                            var42 = 3;
                        }

                        par1Vec3.c += var27 * var25;
                        par1Vec3.d += var29 * var25;
                        par1Vec3.e = var19;
                    }

                    Vec3D var34 = this.getVec3DPool().create(par1Vec3.c, par1Vec3.d, par1Vec3.e);
                    var8 = (int)(var34.c = (double) MathHelper.floor(par1Vec3.c));

                    if (var42 == 5)
                    {
                        --var8;
                        ++var34.c;
                    }

                    var9 = (int)(var34.d = (double) MathHelper.floor(par1Vec3.d));

                    if (var42 == 1)
                    {
                        --var9;
                        ++var34.d;
                    }

                    var10 = (int)(var34.e = (double) MathHelper.floor(par1Vec3.e));

                    if (var42 == 3)
                    {
                        --var10;
                        ++var34.e;
                    }

                    int var35 = this.getTypeId(var8, var9, var10);
                    int var36 = this.getData(var8, var9, var10);
                    Block var37 = Block.byId[var35];

                    if ((!par4 || var37 == null || var37.e(this, var8, var9, var10) != null) && var35 > 0 && var37.a(var36, par3))
                    {
                        MovingObjectPosition var38 = var37.a(this, var8, var9, var10, par1Vec3, par2Vec3);

                        if (var38 != null)
                        {
                            return var38;
                        }
                    }
                }

                return null;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * Plays a sound at the entity's position. Args: entity, sound, volume (relative to 1.0), and frequency (or pitch,
     * also relative to 1.0).
     */
    public void makeSound(Entity par1Entity, String par2Str, float par3, float par4)
    {
        if (par1Entity != null && par2Str != null)
        {
            for (int var5 = 0; var5 < this.v.size(); ++var5)
            {
                ((IWorldAccess)this.v.get(var5)).a(par2Str, par1Entity.locX, par1Entity.locY - (double) par1Entity.height, par1Entity.locZ, par3, par4);
            }
        }
    }

    /**
     * Plays sound to all near players except the player reference given
     */
    public void a(EntityHuman par1EntityPlayer, String par2Str, float par3, float par4)
    {
        if (par1EntityPlayer != null && par2Str != null)
        {
            for (int var5 = 0; var5 < this.v.size(); ++var5)
            {
                ((IWorldAccess)this.v.get(var5)).a(par1EntityPlayer, par2Str, par1EntityPlayer.locX, par1EntityPlayer.locY - (double) par1EntityPlayer.height, par1EntityPlayer.locZ, par3, par4);
            }
        }
    }

    /**
     * Play a sound effect. Many many parameters for this function. Not sure what they do, but a classic call is :
     * (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 'random.door_open', 1.0F, world.rand.nextFloat() * 0.1F +
     * 0.9F with i,j,k position of the block.
     */
    public void makeSound(double par1, double par3, double par5, String par7Str, float par8, float par9)
    {
        if (par7Str != null)
        {
            for (int var10 = 0; var10 < this.v.size(); ++var10)
            {
                ((IWorldAccess)this.v.get(var10)).a(par7Str, par1, par3, par5, par8, par9);
            }
        }
    }

    /**
     * par8 is loudness, all pars passed to minecraftInstance.sndManager.playSound
     */
    public void a(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10) {}

    /**
     * Plays a record at the specified coordinates of the specified name. Args: recordName, x, y, z
     */
    public void a(String par1Str, int par2, int par3, int par4)
    {
        for (int var5 = 0; var5 < this.v.size(); ++var5)
        {
            ((IWorldAccess)this.v.get(var5)).a(par1Str, par2, par3, par4);
        }
    }

    /**
     * Spawns a particle.  Args particleName, x, y, z, velX, velY, velZ
     */
    public void addParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        for (int var14 = 0; var14 < this.v.size(); ++var14)
        {
            ((IWorldAccess)this.v.get(var14)).a(par1Str, par2, par4, par6, par8, par10, par12);
        }
    }

    /**
     * adds a lightning bolt to the list of lightning bolts in this world.
     */
    public boolean strikeLightning(Entity par1Entity)
    {
        this.i.add(par1Entity);
        return true;
    }

    /**
     * Called when an entity is spawned in the world. This includes players.
     */
    public boolean addEntity(Entity par1Entity)
    {
        int var2 = MathHelper.floor(par1Entity.locX / 16.0D);
        int var3 = MathHelper.floor(par1Entity.locZ / 16.0D);
        boolean var4 = false;

        if (par1Entity instanceof EntityHuman)
        {
            var4 = true;
        }

        if (!var4 && !this.isChunkLoaded(var2, var3))
        {
            return false;
        }
        else
        {
            if (par1Entity instanceof EntityHuman)
            {
                EntityHuman var5 = (EntityHuman)par1Entity;
                this.players.add(var5);
                this.everyoneSleeping();
            }

            this.getChunkAt(var2, var3).a(par1Entity);
            this.entityList.add(par1Entity);
            this.a(par1Entity);
            return true;
        }
    }

    /**
     * Start the skin for this entity downloading, if necessary, and increment its reference counter
     */
    protected void a(Entity par1Entity)
    {
        for (int var2 = 0; var2 < this.v.size(); ++var2)
        {
            ((IWorldAccess)this.v.get(var2)).a(par1Entity);
        }
    }

    /**
     * Decrement the reference counter for this entity's skin image data
     */
    protected void b(Entity par1Entity)
    {
        for (int var2 = 0; var2 < this.v.size(); ++var2)
        {
            ((IWorldAccess)this.v.get(var2)).b(par1Entity);
        }
    }

    /**
     * Dismounts the entity (and anything riding the entity), sets the dead flag, and removes the player entity from the
     * player entity list. Called by the playerLoggedOut function.
     */
    public void kill(Entity par1Entity)
    {
        if (par1Entity.passenger != null)
        {
            par1Entity.passenger.mount((Entity) null);
        }

        if (par1Entity.vehicle != null)
        {
            par1Entity.mount((Entity) null);
        }

        par1Entity.die();

        if (par1Entity instanceof EntityHuman)
        {
            this.players.remove(par1Entity);
            this.everyoneSleeping();
        }
    }

    /**
     * remove dat player from dem servers
     */
    public void removeEntity(Entity par1Entity)
    {
        par1Entity.die();

        if (par1Entity instanceof EntityHuman)
        {
            this.players.remove(par1Entity);
            this.everyoneSleeping();
        }

        int var2 = par1Entity.ai;
        int var3 = par1Entity.ak;

        if (par1Entity.ah && this.isChunkLoaded(var2, var3))
        {
            this.getChunkAt(var2, var3).b(par1Entity);
        }

        this.entityList.remove(par1Entity);
        this.b(par1Entity);
    }

    /**
     * Adds a IWorldAccess to the list of worldAccesses
     */
    public void addIWorldAccess(IWorldAccess par1IWorldAccess)
    {
        this.v.add(par1IWorldAccess);
    }

    /**
     * Returns a list of bounding boxes that collide with aabb excluding the passed in entity's collision. Args: entity,
     * aabb
     */
    public List getCubes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB)
    {
        this.L.clear();
        int var3 = MathHelper.floor(par2AxisAlignedBB.a);
        int var4 = MathHelper.floor(par2AxisAlignedBB.d + 1.0D);
        int var5 = MathHelper.floor(par2AxisAlignedBB.b);
        int var6 = MathHelper.floor(par2AxisAlignedBB.e + 1.0D);
        int var7 = MathHelper.floor(par2AxisAlignedBB.c);
        int var8 = MathHelper.floor(par2AxisAlignedBB.f + 1.0D);

        for (int var9 = var3; var9 < var4; ++var9)
        {
            for (int var10 = var7; var10 < var8; ++var10)
            {
                if (this.isLoaded(var9, 64, var10))
                {
                    for (int var11 = var5 - 1; var11 < var6; ++var11)
                    {
                        Block var12 = Block.byId[this.getTypeId(var9, var11, var10)];

                        if (var12 != null)
                        {
                            var12.a(this, var9, var11, var10, par2AxisAlignedBB, this.L, par1Entity);
                        }
                    }
                }
            }
        }

        double var14 = 0.25D;
        List var16 = this.getEntities(par1Entity, par2AxisAlignedBB.grow(var14, var14, var14));

        for (int var15 = 0; var15 < var16.size(); ++var15)
        {
            AxisAlignedBB var13 = ((Entity)var16.get(var15)).E();

            if (var13 != null && var13.a(par2AxisAlignedBB))
            {
                this.L.add(var13);
            }

            var13 = par1Entity.g((Entity) var16.get(var15));

            if (var13 != null && var13.a(par2AxisAlignedBB))
            {
                this.L.add(var13);
            }
        }

        return this.L;
    }

    /**
     * calculates and returns a list of colliding bounding boxes within a given AABB
     */
    public List a(AxisAlignedBB par1AxisAlignedBB)
    {
        this.L.clear();
        int var2 = MathHelper.floor(par1AxisAlignedBB.a);
        int var3 = MathHelper.floor(par1AxisAlignedBB.d + 1.0D);
        int var4 = MathHelper.floor(par1AxisAlignedBB.b);
        int var5 = MathHelper.floor(par1AxisAlignedBB.e + 1.0D);
        int var6 = MathHelper.floor(par1AxisAlignedBB.c);
        int var7 = MathHelper.floor(par1AxisAlignedBB.f + 1.0D);

        for (int var8 = var2; var8 < var3; ++var8)
        {
            for (int var9 = var6; var9 < var7; ++var9)
            {
                if (this.isLoaded(var8, 64, var9))
                {
                    for (int var10 = var4 - 1; var10 < var5; ++var10)
                    {
                        Block var11 = Block.byId[this.getTypeId(var8, var10, var9)];

                        if (var11 != null)
                        {
                            var11.a(this, var8, var10, var9, par1AxisAlignedBB, this.L, (Entity) null);
                        }
                    }
                }
            }
        }

        return this.L;
    }

    /**
     * Returns the amount of skylight subtracted for the current time
     */
    public int a(float par1)
    {
        float var2 = this.c(par1);
        float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.5F);

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        var3 = 1.0F - var3;
        var3 = (float)((double)var3 * (1.0D - (double)(this.j(par1) * 5.0F) / 16.0D));
        var3 = (float)((double)var3 * (1.0D - (double)(this.i(par1) * 5.0F) / 16.0D));
        var3 = 1.0F - var3;
        return (int)(var3 * 11.0F);
    }

    /**
     * calls calculateCelestialAngle
     */
    public float c(float par1)
    {
        return this.worldProvider.a(this.worldData.getDayTime(), par1);
    }

    /**
     * Gets the height to which rain/snow will fall. Calculates it if not already stored.
     */
    public int h(int par1, int par2)
    {
        return this.getChunkAtWorldCoords(par1, par2).d(par1 & 15, par2 & 15);
    }

    /**
     * Finds the highest block on the x, z coordinate that is solid and returns its y coord. Args x, z
     */
    public int i(int par1, int par2)
    {
        Chunk var3 = this.getChunkAtWorldCoords(par1, par2);
        int var4 = var3.h() + 15;
        par1 &= 15;

        for (par2 &= 15; var4 > 0; --var4)
        {
            int var5 = var3.getTypeId(par1, var4, par2);

            if (var5 != 0 && Block.byId[var5].material.isSolid() && Block.byId[var5].material != Material.LEAVES)
            {
                return var4 + 1;
            }
        }

        return -1;
    }

    /**
     * Used to schedule a call to the updateTick method on the specified block.
     */
    public void a(int par1, int par2, int par3, int par4, int par5) {}

    public void a(int par1, int par2, int par3, int par4, int par5, int par6) {}

    /**
     * Schedules a block update from the saved information in a chunk. Called when the chunk is loaded.
     */
    public void b(int par1, int par2, int par3, int par4, int par5) {}

    /**
     * Updates (and cleans up) entities and tile entities
     */
    public void tickEntities()
    {
        this.methodProfiler.a("entities");
        this.methodProfiler.a("global");
        int var1;
        Entity var2;
        CrashReport var4;
        CrashReportSystemDetails var5;

        for (var1 = 0; var1 < this.i.size(); ++var1)
        {
            var2 = (Entity)this.i.get(var1);

            try
            {
                ++var2.ticksLived;
                var2.j_();
            }
            catch (Throwable var8)
            {
                var4 = CrashReport.a(var8, "Ticking entity");
                var5 = var4.a("Entity being ticked");

                if (var2 == null)
                {
                    var5.a("Entity", "~~NULL~~");
                }
                else
                {
                    var2.a(var5);
                }

                throw new ReportedException(var4);
            }

            if (var2.dead)
            {
                this.i.remove(var1--);
            }
        }

        this.methodProfiler.c("remove");
        this.entityList.removeAll(this.f);
        int var3;
        int var13;

        for (var1 = 0; var1 < this.f.size(); ++var1)
        {
            var2 = (Entity)this.f.get(var1);
            var3 = var2.ai;
            var13 = var2.ak;

            if (var2.ah && this.isChunkLoaded(var3, var13))
            {
                this.getChunkAt(var3, var13).b(var2);
            }
        }

        for (var1 = 0; var1 < this.f.size(); ++var1)
        {
            this.b((Entity) this.f.get(var1));
        }

        this.f.clear();
        this.methodProfiler.c("regular");

        for (var1 = 0; var1 < this.entityList.size(); ++var1)
        {
            var2 = (Entity)this.entityList.get(var1);

            if (var2.vehicle != null)
            {
                if (!var2.vehicle.dead && var2.vehicle.passenger == var2)
                {
                    continue;
                }

                var2.vehicle.passenger = null;
                var2.vehicle = null;
            }

            this.methodProfiler.a("tick");

            if (!var2.dead)
            {
                try
                {
                    this.playerJoinedWorld(var2);
                }
                catch (Throwable var6)
                {
                    var4 = CrashReport.a(var6, "Ticking entity");
                    var5 = var4.a("Entity being ticked");

                    if (var2 == null)
                    {
                        var5.a("Entity", "~~NULL~~");
                    }
                    else
                    {
                        var2.a(var5);
                    }

                    throw new ReportedException(var4);
                }
            }

            this.methodProfiler.b();
            this.methodProfiler.a("remove");

            if (var2.dead)
            {
                var3 = var2.ai;
                var13 = var2.ak;

                if (var2.ah && this.isChunkLoaded(var3, var13))
                {
                    this.getChunkAt(var3, var13).b(var2);
                }

                this.entityList.remove(var1--);
                this.b(var2);
            }

            this.methodProfiler.b();
        }

        this.methodProfiler.c("tileEntities");
        this.M = true;
        Iterator var14 = this.tileEntityList.iterator();

        while (var14.hasNext())
        {
            TileEntity var9 = (TileEntity)var14.next();

            if (!var9.r() && var9.o() && this.isLoaded(var9.x, var9.y, var9.z))
            {
                try
                {
                    var9.g();
                }
                catch (Throwable var7)
                {
                    var4 = CrashReport.a(var7, "Ticking tile entity");
                    var5 = var4.a("Tile entity being ticked");

                    if (var9 == null)
                    {
                        var5.a("Tile entity", "~~NULL~~");
                    }
                    else
                    {
                        var9.a(var5);
                    }

                    throw new ReportedException(var4);
                }
            }

            if (var9.r())
            {
                var14.remove();

                if (this.isChunkLoaded(var9.x >> 4, var9.z >> 4))
                {
                    Chunk var11 = this.getChunkAt(var9.x >> 4, var9.z >> 4);

                    if (var11 != null)
                    {
                        var11.f(var9.x & 15, var9.y, var9.z & 15);
                    }
                }
            }
        }

        this.M = false;

        if (!this.b.isEmpty())
        {
            this.tileEntityList.removeAll(this.b);
            this.b.clear();
        }

        this.methodProfiler.c("pendingTileEntities");

        if (!this.a.isEmpty())
        {
            for (int var10 = 0; var10 < this.a.size(); ++var10)
            {
                TileEntity var12 = (TileEntity)this.a.get(var10);

                if (!var12.r())
                {
                    if (!this.tileEntityList.contains(var12))
                    {
                        this.tileEntityList.add(var12);
                    }

                    if (this.isChunkLoaded(var12.x >> 4, var12.z >> 4))
                    {
                        Chunk var15 = this.getChunkAt(var12.x >> 4, var12.z >> 4);

                        if (var15 != null)
                        {
                            var15.a(var12.x & 15, var12.y, var12.z & 15, var12);
                        }
                    }

                    this.notify(var12.x, var12.y, var12.z);
                }
            }

            this.a.clear();
        }

        this.methodProfiler.b();
        this.methodProfiler.b();
    }

    public void a(Collection par1Collection)
    {
        if (this.M)
        {
            this.a.addAll(par1Collection);
        }
        else
        {
            this.tileEntityList.addAll(par1Collection);
        }
    }

    /**
     * Will update the entity in the world if the chunk the entity is in is currently loaded. Args: entity
     */
    public void playerJoinedWorld(Entity par1Entity)
    {
        this.entityJoinedWorld(par1Entity, true);
    }

    /**
     * Will update the entity in the world if the chunk the entity is in is currently loaded or its forced to update.
     * Args: entity, forceUpdate
     */
    public void entityJoinedWorld(Entity par1Entity, boolean par2)
    {
        int var3 = MathHelper.floor(par1Entity.locX);
        int var4 = MathHelper.floor(par1Entity.locZ);
        byte var5 = 32;

        if (!par2 || this.d(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5))
        {
            par1Entity.T = par1Entity.locX;
            par1Entity.U = par1Entity.locY;
            par1Entity.V = par1Entity.locZ;
            par1Entity.lastYaw = par1Entity.yaw;
            par1Entity.lastPitch = par1Entity.pitch;

            if (par2 && par1Entity.ah)
            {
                if (par1Entity.vehicle != null)
                {
                    par1Entity.U();
                }
                else
                {
                    ++par1Entity.ticksLived;
                    par1Entity.j_();
                }
            }

            this.methodProfiler.a("chunkCheck");

            if (Double.isNaN(par1Entity.locX) || Double.isInfinite(par1Entity.locX))
            {
                par1Entity.locX = par1Entity.T;
            }

            if (Double.isNaN(par1Entity.locY) || Double.isInfinite(par1Entity.locY))
            {
                par1Entity.locY = par1Entity.U;
            }

            if (Double.isNaN(par1Entity.locZ) || Double.isInfinite(par1Entity.locZ))
            {
                par1Entity.locZ = par1Entity.V;
            }

            if (Double.isNaN((double)par1Entity.pitch) || Double.isInfinite((double)par1Entity.pitch))
            {
                par1Entity.pitch = par1Entity.lastPitch;
            }

            if (Double.isNaN((double)par1Entity.yaw) || Double.isInfinite((double)par1Entity.yaw))
            {
                par1Entity.yaw = par1Entity.lastYaw;
            }

            int var6 = MathHelper.floor(par1Entity.locX / 16.0D);
            int var7 = MathHelper.floor(par1Entity.locY / 16.0D);
            int var8 = MathHelper.floor(par1Entity.locZ / 16.0D);

            if (!par1Entity.ah || par1Entity.ai != var6 || par1Entity.aj != var7 || par1Entity.ak != var8)
            {
                if (par1Entity.ah && this.isChunkLoaded(par1Entity.ai, par1Entity.ak))
                {
                    this.getChunkAt(par1Entity.ai, par1Entity.ak).a(par1Entity, par1Entity.aj);
                }

                if (this.isChunkLoaded(var6, var8))
                {
                    par1Entity.ah = true;
                    this.getChunkAt(var6, var8).a(par1Entity);
                }
                else
                {
                    par1Entity.ah = false;
                }
            }

            this.methodProfiler.b();

            if (par2 && par1Entity.ah && par1Entity.passenger != null)
            {
                if (!par1Entity.passenger.dead && par1Entity.passenger.vehicle == par1Entity)
                {
                    this.playerJoinedWorld(par1Entity.passenger);
                }
                else
                {
                    par1Entity.passenger.vehicle = null;
                    par1Entity.passenger = null;
                }
            }
        }
    }

    /**
     * Returns true if there are no solid, live entities in the specified AxisAlignedBB
     */
    public boolean b(AxisAlignedBB par1AxisAlignedBB)
    {
        return this.a(par1AxisAlignedBB, (Entity) null);
    }

    /**
     * Returns true if there are no solid, live entities in the specified AxisAlignedBB, excluding the given entity
     */
    public boolean a(AxisAlignedBB par1AxisAlignedBB, Entity par2Entity)
    {
        List var3 = this.getEntities((Entity) null, par1AxisAlignedBB);

        for (int var4 = 0; var4 < var3.size(); ++var4)
        {
            Entity var5 = (Entity)var3.get(var4);

            if (!var5.dead && var5.m && var5 != par2Entity)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if there are any blocks in the region constrained by an AxisAlignedBB
     */
    public boolean c(AxisAlignedBB par1AxisAlignedBB)
    {
        int var2 = MathHelper.floor(par1AxisAlignedBB.a);
        int var3 = MathHelper.floor(par1AxisAlignedBB.d + 1.0D);
        int var4 = MathHelper.floor(par1AxisAlignedBB.b);
        int var5 = MathHelper.floor(par1AxisAlignedBB.e + 1.0D);
        int var6 = MathHelper.floor(par1AxisAlignedBB.c);
        int var7 = MathHelper.floor(par1AxisAlignedBB.f + 1.0D);

        if (par1AxisAlignedBB.a < 0.0D)
        {
            --var2;
        }

        if (par1AxisAlignedBB.b < 0.0D)
        {
            --var4;
        }

        if (par1AxisAlignedBB.c < 0.0D)
        {
            --var6;
        }

        for (int var8 = var2; var8 < var3; ++var8)
        {
            for (int var9 = var4; var9 < var5; ++var9)
            {
                for (int var10 = var6; var10 < var7; ++var10)
                {
                    Block var11 = Block.byId[this.getTypeId(var8, var9, var10)];

                    if (var11 != null)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Returns if any of the blocks within the aabb are liquids. Args: aabb
     */
    public boolean containsLiquid(AxisAlignedBB par1AxisAlignedBB)
    {
        int var2 = MathHelper.floor(par1AxisAlignedBB.a);
        int var3 = MathHelper.floor(par1AxisAlignedBB.d + 1.0D);
        int var4 = MathHelper.floor(par1AxisAlignedBB.b);
        int var5 = MathHelper.floor(par1AxisAlignedBB.e + 1.0D);
        int var6 = MathHelper.floor(par1AxisAlignedBB.c);
        int var7 = MathHelper.floor(par1AxisAlignedBB.f + 1.0D);

        if (par1AxisAlignedBB.a < 0.0D)
        {
            --var2;
        }

        if (par1AxisAlignedBB.b < 0.0D)
        {
            --var4;
        }

        if (par1AxisAlignedBB.c < 0.0D)
        {
            --var6;
        }

        for (int var8 = var2; var8 < var3; ++var8)
        {
            for (int var9 = var4; var9 < var5; ++var9)
            {
                for (int var10 = var6; var10 < var7; ++var10)
                {
                    Block var11 = Block.byId[this.getTypeId(var8, var9, var10)];

                    if (var11 != null && var11.material.isLiquid())
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Returns whether or not the given bounding box is on fire or not
     */
    public boolean e(AxisAlignedBB par1AxisAlignedBB)
    {
        int var2 = MathHelper.floor(par1AxisAlignedBB.a);
        int var3 = MathHelper.floor(par1AxisAlignedBB.d + 1.0D);
        int var4 = MathHelper.floor(par1AxisAlignedBB.b);
        int var5 = MathHelper.floor(par1AxisAlignedBB.e + 1.0D);
        int var6 = MathHelper.floor(par1AxisAlignedBB.c);
        int var7 = MathHelper.floor(par1AxisAlignedBB.f + 1.0D);

        if (this.d(var2, var4, var6, var3, var5, var7))
        {
            for (int var8 = var2; var8 < var3; ++var8)
            {
                for (int var9 = var4; var9 < var5; ++var9)
                {
                    for (int var10 = var6; var10 < var7; ++var10)
                    {
                        int var11 = this.getTypeId(var8, var9, var10);

                        if (var11 == Block.FIRE.id || var11 == Block.LAVA.id || var11 == Block.STATIONARY_LAVA.id)
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * handles the acceleration of an object whilst in water. Not sure if it is used elsewhere.
     */
    public boolean a(AxisAlignedBB par1AxisAlignedBB, Material par2Material, Entity par3Entity)
    {
        int var4 = MathHelper.floor(par1AxisAlignedBB.a);
        int var5 = MathHelper.floor(par1AxisAlignedBB.d + 1.0D);
        int var6 = MathHelper.floor(par1AxisAlignedBB.b);
        int var7 = MathHelper.floor(par1AxisAlignedBB.e + 1.0D);
        int var8 = MathHelper.floor(par1AxisAlignedBB.c);
        int var9 = MathHelper.floor(par1AxisAlignedBB.f + 1.0D);

        if (!this.d(var4, var6, var8, var5, var7, var9))
        {
            return false;
        }
        else
        {
            boolean var10 = false;
            Vec3D var11 = this.getVec3DPool().create(0.0D, 0.0D, 0.0D);

            for (int var12 = var4; var12 < var5; ++var12)
            {
                for (int var13 = var6; var13 < var7; ++var13)
                {
                    for (int var14 = var8; var14 < var9; ++var14)
                    {
                        Block var15 = Block.byId[this.getTypeId(var12, var13, var14)];

                        if (var15 != null && var15.material == par2Material)
                        {
                            double var16 = (double)((float)(var13 + 1) - BlockFluids.e(this.getData(var12, var13, var14)));

                            if ((double)var7 >= var16)
                            {
                                var10 = true;
                                var15.a(this, var12, var13, var14, par3Entity, var11);
                            }
                        }
                    }
                }
            }

            if (var11.b() > 0.0D)
            {
                var11 = var11.a();
                double var18 = 0.014D;
                par3Entity.motX += var11.c * var18;
                par3Entity.motY += var11.d * var18;
                par3Entity.motZ += var11.e * var18;
            }

            return var10;
        }
    }

    /**
     * Returns true if the given bounding box contains the given material
     */
    public boolean a(AxisAlignedBB par1AxisAlignedBB, Material par2Material)
    {
        int var3 = MathHelper.floor(par1AxisAlignedBB.a);
        int var4 = MathHelper.floor(par1AxisAlignedBB.d + 1.0D);
        int var5 = MathHelper.floor(par1AxisAlignedBB.b);
        int var6 = MathHelper.floor(par1AxisAlignedBB.e + 1.0D);
        int var7 = MathHelper.floor(par1AxisAlignedBB.c);
        int var8 = MathHelper.floor(par1AxisAlignedBB.f + 1.0D);

        for (int var9 = var3; var9 < var4; ++var9)
        {
            for (int var10 = var5; var10 < var6; ++var10)
            {
                for (int var11 = var7; var11 < var8; ++var11)
                {
                    Block var12 = Block.byId[this.getTypeId(var9, var10, var11)];

                    if (var12 != null && var12.material == par2Material)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * checks if the given AABB is in the material given. Used while swimming.
     */
    public boolean b(AxisAlignedBB par1AxisAlignedBB, Material par2Material)
    {
        int var3 = MathHelper.floor(par1AxisAlignedBB.a);
        int var4 = MathHelper.floor(par1AxisAlignedBB.d + 1.0D);
        int var5 = MathHelper.floor(par1AxisAlignedBB.b);
        int var6 = MathHelper.floor(par1AxisAlignedBB.e + 1.0D);
        int var7 = MathHelper.floor(par1AxisAlignedBB.c);
        int var8 = MathHelper.floor(par1AxisAlignedBB.f + 1.0D);

        for (int var9 = var3; var9 < var4; ++var9)
        {
            for (int var10 = var5; var10 < var6; ++var10)
            {
                for (int var11 = var7; var11 < var8; ++var11)
                {
                    Block var12 = Block.byId[this.getTypeId(var9, var10, var11)];

                    if (var12 != null && var12.material == par2Material)
                    {
                        int var13 = this.getData(var9, var10, var11);
                        double var14 = (double)(var10 + 1);

                        if (var13 < 8)
                        {
                            var14 = (double)(var10 + 1) - (double)var13 / 8.0D;
                        }

                        if (var14 >= par1AxisAlignedBB.b)
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Creates an explosion. Args: entity, x, y, z, strength
     */
    public Explosion explode(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9)
    {
        return this.createExplosion(par1Entity, par2, par4, par6, par8, false, par9);
    }

    /**
     * returns a new explosion. Does initiation (at time of writing Explosion is not finished)
     */
    public Explosion createExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10)
    {
        Explosion var11 = new Explosion(this, par1Entity, par2, par4, par6, par8);
        var11.a = par9;
        var11.b = par10;
        var11.a();
        var11.a(true);
        return var11;
    }

    /**
     * Gets the percentage of real blocks within within a bounding box, along a specified vector.
     */
    public float a(Vec3D par1Vec3, AxisAlignedBB par2AxisAlignedBB)
    {
        double var3 = 1.0D / ((par2AxisAlignedBB.d - par2AxisAlignedBB.a) * 2.0D + 1.0D);
        double var5 = 1.0D / ((par2AxisAlignedBB.e - par2AxisAlignedBB.b) * 2.0D + 1.0D);
        double var7 = 1.0D / ((par2AxisAlignedBB.f - par2AxisAlignedBB.c) * 2.0D + 1.0D);
        int var9 = 0;
        int var10 = 0;

        for (float var11 = 0.0F; var11 <= 1.0F; var11 = (float)((double)var11 + var3))
        {
            for (float var12 = 0.0F; var12 <= 1.0F; var12 = (float)((double)var12 + var5))
            {
                for (float var13 = 0.0F; var13 <= 1.0F; var13 = (float)((double)var13 + var7))
                {
                    double var14 = par2AxisAlignedBB.a + (par2AxisAlignedBB.d - par2AxisAlignedBB.a) * (double)var11;
                    double var16 = par2AxisAlignedBB.b + (par2AxisAlignedBB.e - par2AxisAlignedBB.b) * (double)var12;
                    double var18 = par2AxisAlignedBB.c + (par2AxisAlignedBB.f - par2AxisAlignedBB.c) * (double)var13;

                    if (this.a(this.getVec3DPool().create(var14, var16, var18), par1Vec3) == null)
                    {
                        ++var9;
                    }

                    ++var10;
                }
            }
        }

        return (float)var9 / (float)var10;
    }

    /**
     * If the block in the given direction of the given coordinate is fire, extinguish it. Args: Player, X,Y,Z,
     * blockDirection
     */
    public boolean douseFire(EntityHuman par1EntityPlayer, int par2, int par3, int par4, int par5)
    {
        if (par5 == 0)
        {
            --par3;
        }

        if (par5 == 1)
        {
            ++par3;
        }

        if (par5 == 2)
        {
            --par4;
        }

        if (par5 == 3)
        {
            ++par4;
        }

        if (par5 == 4)
        {
            --par2;
        }

        if (par5 == 5)
        {
            ++par2;
        }

        if (this.getTypeId(par2, par3, par4) == Block.FIRE.id)
        {
            this.a(par1EntityPlayer, 1004, par2, par3, par4, 0);
            this.setTypeId(par2, par3, par4, 0);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the TileEntity associated with a given block in X,Y,Z coordinates, or null if no TileEntity exists
     */
    public TileEntity getTileEntity(int par1, int par2, int par3)
    {
        if (par2 >= 256)
        {
            return null;
        }
        else
        {
            Chunk var4 = this.getChunkAt(par1 >> 4, par3 >> 4);

            if (var4 == null)
            {
                return null;
            }
            else
            {
                TileEntity var5 = var4.e(par1 & 15, par2, par3 & 15);

                if (var5 == null)
                {
                    for (int var6 = 0; var6 < this.a.size(); ++var6)
                    {
                        TileEntity var7 = (TileEntity)this.a.get(var6);

                        if (!var7.r() && var7.x == par1 && var7.y == par2 && var7.z == par3)
                        {
                            var5 = var7;
                            break;
                        }
                    }
                }

                return var5;
            }
        }
    }

    /**
     * Sets the TileEntity for a given block in X, Y, Z coordinates
     */
    public void setTileEntity(int par1, int par2, int par3, TileEntity par4TileEntity)
    {
        if (par4TileEntity != null && !par4TileEntity.r())
        {
            if (this.M)
            {
                par4TileEntity.x = par1;
                par4TileEntity.y = par2;
                par4TileEntity.z = par3;
                this.a.add(par4TileEntity);
            }
            else
            {
                this.tileEntityList.add(par4TileEntity);
                Chunk var5 = this.getChunkAt(par1 >> 4, par3 >> 4);

                if (var5 != null)
                {
                    var5.a(par1 & 15, par2, par3 & 15, par4TileEntity);
                }
            }
        }
    }

    /**
     * Removes the TileEntity for a given block in X,Y,Z coordinates
     */
    public void r(int par1, int par2, int par3)
    {
        TileEntity var4 = this.getTileEntity(par1, par2, par3);

        if (var4 != null && this.M)
        {
            var4.w_();
            this.a.remove(var4);
        }
        else
        {
            if (var4 != null)
            {
                this.a.remove(var4);
                this.tileEntityList.remove(var4);
            }

            Chunk var5 = this.getChunkAt(par1 >> 4, par3 >> 4);

            if (var5 != null)
            {
                var5.f(par1 & 15, par2, par3 & 15);
            }
        }
    }

    /**
     * Adds TileEntity to despawn list
     */
    public void a(TileEntity par1TileEntity)
    {
        this.b.add(par1TileEntity);
    }

    /**
     * Returns true if the block at the specified coordinates is an opaque cube. Args: x, y, z
     */
    public boolean s(int par1, int par2, int par3)
    {
        Block var4 = Block.byId[this.getTypeId(par1, par2, par3)];
        return var4 == null ? false : var4.c();
    }

    /**
     * Returns true if the block at the specified coordinates is an opaque cube. Args: x, y, z
     */
    public boolean t(int par1, int par2, int par3)
    {
        return Block.i(this.getTypeId(par1, par2, par3));
    }

    public boolean u(int par1, int par2, int par3)
    {
        int var4 = this.getTypeId(par1, par2, par3);

        if (var4 != 0 && Block.byId[var4] != null)
        {
            AxisAlignedBB var5 = Block.byId[var4].e(this, par1, par2, par3);
            return var5 != null && var5.b() >= 1.0D;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns true if the block at the given coordinate has a solid (buildable) top surface.
     */
    public boolean v(int par1, int par2, int par3)
    {
        Block var4 = Block.byId[this.getTypeId(par1, par2, par3)];
        return var4 == null ? false : (var4.material.k() && var4.b() ? true : (var4 instanceof BlockStairs ? (this.getData(par1, par2, par3) & 4) == 4 : (var4 instanceof BlockStepAbstract ? (this.getData(par1, par2, par3) & 8) == 8 : false)));
    }

    /**
     * Checks if the block is a solid, normal cube. If the chunk does not exist, or is not loaded, it returns the
     * boolean parameter.
     */
    public boolean b(int par1, int par2, int par3, boolean par4)
    {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
        {
            Chunk var5 = this.chunkProvider.getOrCreateChunk(par1 >> 4, par3 >> 4);

            if (var5 != null && !var5.isEmpty())
            {
                Block var6 = Block.byId[this.getTypeId(par1, par2, par3)];
                return var6 == null ? false : var6.material.k() && var6.b();
            }
            else
            {
                return par4;
            }
        }
        else
        {
            return par4;
        }
    }

    /**
     * Called on construction of the World class to setup the initial skylight values
     */
    public void x()
    {
        int var1 = this.a(1.0F);

        if (var1 != this.j)
        {
            this.j = var1;
        }
    }

    /**
     * first boolean for hostile mobs and second for peaceful mobs
     */
    public void setSpawnFlags(boolean par1, boolean par2)
    {
        this.allowMonsters = par1;
        this.allowAnimals = par2;
    }

    /**
     * Runs a single tick for the world
     */
    public void doTick()
    {
        this.n();
    }

    /**
     * Called from World constructor to set rainingStrength and thunderingStrength
     */
    private void a()
    {
        if (this.worldData.hasStorm())
        {
            this.n = 1.0F;

            if (this.worldData.isThundering())
            {
                this.p = 1.0F;
            }
        }
    }

    /**
     * Updates all weather states.
     */
    protected void n()
    {
        if (!this.worldProvider.f)
        {
            int var1 = this.worldData.getThunderDuration();

            if (var1 <= 0)
            {
                if (this.worldData.isThundering())
                {
                    this.worldData.setThunderDuration(this.random.nextInt(12000) + 3600);
                }
                else
                {
                    this.worldData.setThunderDuration(this.random.nextInt(168000) + 12000);
                }
            }
            else
            {
                --var1;
                this.worldData.setThunderDuration(var1);

                if (var1 <= 0)
                {
                    this.worldData.setThundering(!this.worldData.isThundering());
                }
            }

            int var2 = this.worldData.getWeatherDuration();

            if (var2 <= 0)
            {
                if (this.worldData.hasStorm())
                {
                    this.worldData.setWeatherDuration(this.random.nextInt(12000) + 12000);
                }
                else
                {
                    this.worldData.setWeatherDuration(this.random.nextInt(168000) + 12000);
                }
            }
            else
            {
                --var2;
                this.worldData.setWeatherDuration(var2);

                if (var2 <= 0)
                {
                    this.worldData.setStorm(!this.worldData.hasStorm());
                }
            }

            this.m = this.n;

            if (this.worldData.hasStorm())
            {
                this.n = (float)((double)this.n + 0.01D);
            }
            else
            {
                this.n = (float)((double)this.n - 0.01D);
            }

            if (this.n < 0.0F)
            {
                this.n = 0.0F;
            }

            if (this.n > 1.0F)
            {
                this.n = 1.0F;
            }

            this.o = this.p;

            if (this.worldData.isThundering())
            {
                this.p = (float)((double)this.p + 0.01D);
            }
            else
            {
                this.p = (float)((double)this.p - 0.01D);
            }

            if (this.p < 0.0F)
            {
                this.p = 0.0F;
            }

            if (this.p > 1.0F)
            {
                this.p = 1.0F;
            }
        }
    }

    /**
     * start precipitation in this world (2 ticks after command posted)
     */
    public void y()
    {
        this.worldData.setWeatherDuration(1);
    }

    protected void z()
    {
        this.chunkTickList.clear();
        this.methodProfiler.a("buildList");
        int var1;
        EntityHuman var2;
        int var3;
        int var4;

        for (var1 = 0; var1 < this.players.size(); ++var1)
        {
            var2 = (EntityHuman)this.players.get(var1);
            var3 = MathHelper.floor(var2.locX / 16.0D);
            var4 = MathHelper.floor(var2.locZ / 16.0D);
            byte var5 = 7;

            for (int var6 = -var5; var6 <= var5; ++var6)
            {
                for (int var7 = -var5; var7 <= var5; ++var7)
                {
                    this.chunkTickList.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
                }
            }
        }

        this.methodProfiler.b();

        if (this.N > 0)
        {
            --this.N;
        }

        this.methodProfiler.a("playerCheckLight");

        if (!this.players.isEmpty())
        {
            var1 = this.random.nextInt(this.players.size());
            var2 = (EntityHuman)this.players.get(var1);
            var3 = MathHelper.floor(var2.locX) + this.random.nextInt(11) - 5;
            var4 = MathHelper.floor(var2.locY) + this.random.nextInt(11) - 5;
            int var8 = MathHelper.floor(var2.locZ) + this.random.nextInt(11) - 5;
            this.z(var3, var4, var8);
        }

        this.methodProfiler.b();
    }

    protected void a(int par1, int par2, Chunk par3Chunk)
    {
        this.methodProfiler.c("moodSound");

        if (this.N == 0 && !this.isStatic)
        {
            this.k = this.k * 3 + 1013904223;
            int var4 = this.k >> 2;
            int var5 = var4 & 15;
            int var6 = var4 >> 8 & 15;
            int var7 = var4 >> 16 & 127;
            int var8 = par3Chunk.getTypeId(var5, var7, var6);
            var5 += par1;
            var6 += par2;

            if (var8 == 0 && this.l(var5, var7, var6) <= this.random.nextInt(8) && this.b(EnumSkyBlock.SKY, var5, var7, var6) <= 0)
            {
                EntityHuman var9 = this.findNearbyPlayer((double) var5 + 0.5D, (double) var7 + 0.5D, (double) var6 + 0.5D, 8.0D);

                if (var9 != null && var9.e((double) var5 + 0.5D, (double) var7 + 0.5D, (double) var6 + 0.5D) > 4.0D)
                {
                    this.makeSound((double) var5 + 0.5D, (double) var7 + 0.5D, (double) var6 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.random.nextFloat() * 0.2F);
                    this.N = this.random.nextInt(12000) + 6000;
                }
            }
        }

        this.methodProfiler.c("checkLight");
        par3Chunk.o();
    }

    /**
     * plays random cave ambient sounds and runs updateTick on random blocks within each chunk in the vacinity of a
     * player
     */
    protected void g()
    {
        this.z();
    }

    /**
     * checks to see if a given block is both water and is cold enough to freeze
     */
    public boolean w(int par1, int par2, int par3)
    {
        return this.c(par1, par2, par3, false);
    }

    /**
     * checks to see if a given block is both water and has at least one immediately adjacent non-water block
     */
    public boolean x(int par1, int par2, int par3)
    {
        return this.c(par1, par2, par3, true);
    }

    /**
     * checks to see if a given block is both water, and cold enough to freeze - if the par4 boolean is set, this will
     * only return true if there is a non-water block immediately adjacent to the specified block
     */
    public boolean c(int par1, int par2, int par3, boolean par4)
    {
        BiomeBase var5 = this.getBiome(par1, par3);
        float var6 = var5.j();

        if (var6 > 0.15F)
        {
            return false;
        }
        else
        {
            if (par2 >= 0 && par2 < 256 && this.b(EnumSkyBlock.BLOCK, par1, par2, par3) < 10)
            {
                int var7 = this.getTypeId(par1, par2, par3);

                if ((var7 == Block.STATIONARY_WATER.id || var7 == Block.WATER.id) && this.getData(par1, par2, par3) == 0)
                {
                    if (!par4)
                    {
                        return true;
                    }

                    boolean var8 = true;

                    if (var8 && this.getMaterial(par1 - 1, par2, par3) != Material.WATER)
                    {
                        var8 = false;
                    }

                    if (var8 && this.getMaterial(par1 + 1, par2, par3) != Material.WATER)
                    {
                        var8 = false;
                    }

                    if (var8 && this.getMaterial(par1, par2, par3 - 1) != Material.WATER)
                    {
                        var8 = false;
                    }

                    if (var8 && this.getMaterial(par1, par2, par3 + 1) != Material.WATER)
                    {
                        var8 = false;
                    }

                    if (!var8)
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    /**
     * Tests whether or not snow can be placed at a given location
     */
    public boolean y(int par1, int par2, int par3)
    {
        BiomeBase var4 = this.getBiome(par1, par3);
        float var5 = var4.j();

        if (var5 > 0.15F)
        {
            return false;
        }
        else
        {
            if (par2 >= 0 && par2 < 256 && this.b(EnumSkyBlock.BLOCK, par1, par2, par3) < 10)
            {
                int var6 = this.getTypeId(par1, par2 - 1, par3);
                int var7 = this.getTypeId(par1, par2, par3);

                if (var7 == 0 && Block.SNOW.canPlace(this, par1, par2, par3) && var6 != 0 && var6 != Block.ICE.id && Block.byId[var6].material.isSolid())
                {
                    return true;
                }
            }

            return false;
        }
    }

    public void z(int par1, int par2, int par3)
    {
        if (!this.worldProvider.f)
        {
            this.c(EnumSkyBlock.SKY, par1, par2, par3);
        }

        this.c(EnumSkyBlock.BLOCK, par1, par2, par3);
    }

    private int b(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        int var7 = 0;

        if (this.k(par2, par3, par4))
        {
            var7 = 15;
        }
        else
        {
            if (par6 == 0)
            {
                par6 = 1;
            }

            int var8 = this.b(EnumSkyBlock.SKY, par2 - 1, par3, par4) - par6;
            int var9 = this.b(EnumSkyBlock.SKY, par2 + 1, par3, par4) - par6;
            int var10 = this.b(EnumSkyBlock.SKY, par2, par3 - 1, par4) - par6;
            int var11 = this.b(EnumSkyBlock.SKY, par2, par3 + 1, par4) - par6;
            int var12 = this.b(EnumSkyBlock.SKY, par2, par3, par4 - 1) - par6;
            int var13 = this.b(EnumSkyBlock.SKY, par2, par3, par4 + 1) - par6;

            if (var8 > var7)
            {
                var7 = var8;
            }

            if (var9 > var7)
            {
                var7 = var9;
            }

            if (var10 > var7)
            {
                var7 = var10;
            }

            if (var11 > var7)
            {
                var7 = var11;
            }

            if (var12 > var7)
            {
                var7 = var12;
            }

            if (var13 > var7)
            {
                var7 = var13;
            }
        }

        return var7;
    }

    private int g(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        int var7 = Block.lightEmission[par5];
        int var8 = this.b(EnumSkyBlock.BLOCK, par2 - 1, par3, par4) - par6;
        int var9 = this.b(EnumSkyBlock.BLOCK, par2 + 1, par3, par4) - par6;
        int var10 = this.b(EnumSkyBlock.BLOCK, par2, par3 - 1, par4) - par6;
        int var11 = this.b(EnumSkyBlock.BLOCK, par2, par3 + 1, par4) - par6;
        int var12 = this.b(EnumSkyBlock.BLOCK, par2, par3, par4 - 1) - par6;
        int var13 = this.b(EnumSkyBlock.BLOCK, par2, par3, par4 + 1) - par6;

        if (var8 > var7)
        {
            var7 = var8;
        }

        if (var9 > var7)
        {
            var7 = var9;
        }

        if (var10 > var7)
        {
            var7 = var10;
        }

        if (var11 > var7)
        {
            var7 = var11;
        }

        if (var12 > var7)
        {
            var7 = var12;
        }

        if (var13 > var7)
        {
            var7 = var13;
        }

        return var7;
    }

    public void c(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
    {
        if (this.areChunksLoaded(par2, par3, par4, 17))
        {
            int var5 = 0;
            int var6 = 0;
            this.methodProfiler.a("getBrightness");
            int var7 = this.b(par1EnumSkyBlock, par2, par3, par4);
            boolean var8 = false;
            int var9 = this.getTypeId(par2, par3, par4);
            int var10 = this.b(par2, par3, par4);

            if (var10 == 0)
            {
                var10 = 1;
            }

            boolean var11 = false;
            int var24;

            if (par1EnumSkyBlock == EnumSkyBlock.SKY)
            {
                var24 = this.b(var7, par2, par3, par4, var9, var10);
            }
            else
            {
                var24 = this.g(var7, par2, par3, par4, var9, var10);
            }

            int var12;
            int var13;
            int var14;
            int var15;
            int var17;
            int var16;
            int var19;
            int var18;

            if (var24 > var7)
            {
                this.H[var6++] = 133152;
            }
            else if (var24 < var7)
            {
                if (par1EnumSkyBlock != EnumSkyBlock.BLOCK)
                {
                    ;
                }

                this.H[var6++] = 133152 + (var7 << 18);

                while (var5 < var6)
                {
                    var9 = this.H[var5++];
                    var10 = (var9 & 63) - 32 + par2;
                    var24 = (var9 >> 6 & 63) - 32 + par3;
                    var12 = (var9 >> 12 & 63) - 32 + par4;
                    var13 = var9 >> 18 & 15;
                    var14 = this.b(par1EnumSkyBlock, var10, var24, var12);

                    if (var14 == var13)
                    {
                        this.b(par1EnumSkyBlock, var10, var24, var12, 0);

                        if (var13 > 0)
                        {
                            var15 = var10 - par2;
                            var16 = var24 - par3;
                            var17 = var12 - par4;

                            if (var15 < 0)
                            {
                                var15 = -var15;
                            }

                            if (var16 < 0)
                            {
                                var16 = -var16;
                            }

                            if (var17 < 0)
                            {
                                var17 = -var17;
                            }

                            if (var15 + var16 + var17 < 17)
                            {
                                for (var18 = 0; var18 < 6; ++var18)
                                {
                                    var19 = var18 % 2 * 2 - 1;
                                    int var20 = var10 + var18 / 2 % 3 / 2 * var19;
                                    int var21 = var24 + (var18 / 2 + 1) % 3 / 2 * var19;
                                    int var22 = var12 + (var18 / 2 + 2) % 3 / 2 * var19;
                                    var14 = this.b(par1EnumSkyBlock, var20, var21, var22);
                                    int var23 = Block.lightBlock[this.getTypeId(var20, var21, var22)];

                                    if (var23 == 0)
                                    {
                                        var23 = 1;
                                    }

                                    if (var14 == var13 - var23 && var6 < this.H.length)
                                    {
                                        this.H[var6++] = var20 - par2 + 32 + (var21 - par3 + 32 << 6) + (var22 - par4 + 32 << 12) + (var13 - var23 << 18);
                                    }
                                }
                            }
                        }
                    }
                }

                var5 = 0;
            }

            this.methodProfiler.b();
            this.methodProfiler.a("checkedPosition < toCheckCount");

            while (var5 < var6)
            {
                var9 = this.H[var5++];
                var10 = (var9 & 63) - 32 + par2;
                var24 = (var9 >> 6 & 63) - 32 + par3;
                var12 = (var9 >> 12 & 63) - 32 + par4;
                var13 = this.b(par1EnumSkyBlock, var10, var24, var12);
                var14 = this.getTypeId(var10, var24, var12);
                var15 = Block.lightBlock[var14];

                if (var15 == 0)
                {
                    var15 = 1;
                }

                boolean var25 = false;

                if (par1EnumSkyBlock == EnumSkyBlock.SKY)
                {
                    var16 = this.b(var13, var10, var24, var12, var14, var15);
                }
                else
                {
                    var16 = this.g(var13, var10, var24, var12, var14, var15);
                }

                if (var16 != var13)
                {
                    this.b(par1EnumSkyBlock, var10, var24, var12, var16);

                    if (var16 > var13)
                    {
                        var17 = var10 - par2;
                        var18 = var24 - par3;
                        var19 = var12 - par4;

                        if (var17 < 0)
                        {
                            var17 = -var17;
                        }

                        if (var18 < 0)
                        {
                            var18 = -var18;
                        }

                        if (var19 < 0)
                        {
                            var19 = -var19;
                        }

                        if (var17 + var18 + var19 < 17 && var6 < this.H.length - 6)
                        {
                            if (this.b(par1EnumSkyBlock, var10 - 1, var24, var12) < var16)
                            {
                                this.H[var6++] = var10 - 1 - par2 + 32 + (var24 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
                            }

                            if (this.b(par1EnumSkyBlock, var10 + 1, var24, var12) < var16)
                            {
                                this.H[var6++] = var10 + 1 - par2 + 32 + (var24 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
                            }

                            if (this.b(par1EnumSkyBlock, var10, var24 - 1, var12) < var16)
                            {
                                this.H[var6++] = var10 - par2 + 32 + (var24 - 1 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
                            }

                            if (this.b(par1EnumSkyBlock, var10, var24 + 1, var12) < var16)
                            {
                                this.H[var6++] = var10 - par2 + 32 + (var24 + 1 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
                            }

                            if (this.b(par1EnumSkyBlock, var10, var24, var12 - 1) < var16)
                            {
                                this.H[var6++] = var10 - par2 + 32 + (var24 - par3 + 32 << 6) + (var12 - 1 - par4 + 32 << 12);
                            }

                            if (this.b(par1EnumSkyBlock, var10, var24, var12 + 1) < var16)
                            {
                                this.H[var6++] = var10 - par2 + 32 + (var24 - par3 + 32 << 6) + (var12 + 1 - par4 + 32 << 12);
                            }
                        }
                    }
                }
            }

            this.methodProfiler.b();
        }
    }

    /**
     * Runs through the list of updates to run and ticks them
     */
    public boolean a(boolean par1)
    {
        return false;
    }

    public List a(Chunk par1Chunk, boolean par2)
    {
        return null;
    }

    /**
     * Will get all entities within the specified AABB excluding the one passed into it. Args: entityToExclude, aabb
     */
    public List getEntities(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB)
    {
        this.O.clear();
        int var3 = MathHelper.floor((par2AxisAlignedBB.a - 2.0D) / 16.0D);
        int var4 = MathHelper.floor((par2AxisAlignedBB.d + 2.0D) / 16.0D);
        int var5 = MathHelper.floor((par2AxisAlignedBB.c - 2.0D) / 16.0D);
        int var6 = MathHelper.floor((par2AxisAlignedBB.f + 2.0D) / 16.0D);

        for (int var7 = var3; var7 <= var4; ++var7)
        {
            for (int var8 = var5; var8 <= var6; ++var8)
            {
                if (this.isChunkLoaded(var7, var8))
                {
                    this.getChunkAt(var7, var8).a(par1Entity, par2AxisAlignedBB, this.O);
                }
            }
        }

        return this.O;
    }

    /**
     * Returns all entities of the specified class type which intersect with the AABB. Args: entityClass, aabb
     */
    public List a(Class par1Class, AxisAlignedBB par2AxisAlignedBB)
    {
        return this.a(par1Class, par2AxisAlignedBB, (IEntitySelector) null);
    }

    public List a(Class par1Class, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector)
    {
        int var4 = MathHelper.floor((par2AxisAlignedBB.a - 2.0D) / 16.0D);
        int var5 = MathHelper.floor((par2AxisAlignedBB.d + 2.0D) / 16.0D);
        int var6 = MathHelper.floor((par2AxisAlignedBB.c - 2.0D) / 16.0D);
        int var7 = MathHelper.floor((par2AxisAlignedBB.f + 2.0D) / 16.0D);
        ArrayList var8 = new ArrayList();

        for (int var9 = var4; var9 <= var5; ++var9)
        {
            for (int var10 = var6; var10 <= var7; ++var10)
            {
                if (this.isChunkLoaded(var9, var10))
                {
                    this.getChunkAt(var9, var10).a(par1Class, par2AxisAlignedBB, var8, par3IEntitySelector);
                }
            }
        }

        return var8;
    }

    public Entity a(Class par1Class, AxisAlignedBB par2AxisAlignedBB, Entity par3Entity)
    {
        List var4 = this.a(par1Class, par2AxisAlignedBB);
        Entity var5 = null;
        double var6 = Double.MAX_VALUE;

        for (int var8 = 0; var8 < var4.size(); ++var8)
        {
            Entity var9 = (Entity)var4.get(var8);

            if (var9 != par3Entity)
            {
                double var10 = par3Entity.e(var9);

                if (var10 <= var6)
                {
                    var5 = var9;
                    var6 = var10;
                }
            }
        }

        return var5;
    }

    /**
     * Returns the Entity with the given ID, or null if it doesn't exist in this World.
     */
    public abstract Entity getEntity(int var1);

    /**
     * marks the chunk that contains this tilentity as modified and then calls worldAccesses.doNothingWithTileEntity
     */
    public void b(int par1, int par2, int par3, TileEntity par4TileEntity)
    {
        if (this.isLoaded(par1, par2, par3))
        {
            this.getChunkAtWorldCoords(par1, par3).e();
        }
    }

    /**
     * Counts how many entities of an entity class exist in the world. Args: entityClass
     */
    public int a(Class par1Class)
    {
        int var2 = 0;

        for (int var3 = 0; var3 < this.entityList.size(); ++var3)
        {
            Entity var4 = (Entity)this.entityList.get(var3);

            if (par1Class.isAssignableFrom(var4.getClass()))
            {
                ++var2;
            }
        }

        return var2;
    }

    /**
     * adds entities to the loaded entities list, and loads thier skins.
     */
    public void a(List par1List)
    {
        this.entityList.addAll(par1List);

        for (int var2 = 0; var2 < par1List.size(); ++var2)
        {
            this.a((Entity) par1List.get(var2));
        }
    }

    /**
     * adds entities to the list of unloaded entities
     */
    public void b(List par1List)
    {
        this.f.addAll(par1List);
    }

    /**
     * Returns true if the given Entity can be placed on the given side of the given block position.
     */
    public boolean mayPlace(int par1, int par2, int par3, int par4, boolean par5, int par6, Entity par7Entity)
    {
        int var8 = this.getTypeId(par2, par3, par4);
        Block var9 = Block.byId[var8];
        Block var10 = Block.byId[par1];
        AxisAlignedBB var11 = var10.e(this, par2, par3, par4);

        if (par5)
        {
            var11 = null;
        }

        if (var11 != null && !this.a(var11, par7Entity))
        {
            return false;
        }
        else
        {
            if (var9 != null && (var9 == Block.WATER || var9 == Block.STATIONARY_WATER || var9 == Block.LAVA || var9 == Block.STATIONARY_LAVA || var9 == Block.FIRE || var9.material.isReplaceable()))
            {
                var9 = null;
            }

            return var9 != null && var9.material == Material.ORIENTABLE && var10 == Block.ANVIL ? true : par1 > 0 && var9 == null && var10.canPlace(this, par2, par3, par4, par6);
        }
    }

    public PathEntity findPath(Entity par1Entity, Entity par2Entity, float par3, boolean par4, boolean par5, boolean par6, boolean par7)
    {
        this.methodProfiler.a("pathfind");
        int var8 = MathHelper.floor(par1Entity.locX);
        int var9 = MathHelper.floor(par1Entity.locY + 1.0D);
        int var10 = MathHelper.floor(par1Entity.locZ);
        int var11 = (int)(par3 + 16.0F);
        int var12 = var8 - var11;
        int var13 = var9 - var11;
        int var14 = var10 - var11;
        int var15 = var8 + var11;
        int var16 = var9 + var11;
        int var17 = var10 + var11;
        ChunkCache var18 = new ChunkCache(this, var12, var13, var14, var15, var16, var17);
        PathEntity var19 = (new Pathfinder(var18, par4, par5, par6, par7)).a(par1Entity, par2Entity, par3);
        this.methodProfiler.b();
        return var19;
    }

    public PathEntity a(Entity par1Entity, int par2, int par3, int par4, float par5, boolean par6, boolean par7, boolean par8, boolean par9)
    {
        this.methodProfiler.a("pathfind");
        int var10 = MathHelper.floor(par1Entity.locX);
        int var11 = MathHelper.floor(par1Entity.locY);
        int var12 = MathHelper.floor(par1Entity.locZ);
        int var13 = (int)(par5 + 8.0F);
        int var14 = var10 - var13;
        int var15 = var11 - var13;
        int var16 = var12 - var13;
        int var17 = var10 + var13;
        int var18 = var11 + var13;
        int var19 = var12 + var13;
        ChunkCache var20 = new ChunkCache(this, var14, var15, var16, var17, var18, var19);
        PathEntity var21 = (new Pathfinder(var20, par6, par7, par8, par9)).a(par1Entity, par2, par3, par4, par5);
        this.methodProfiler.b();
        return var21;
    }

    /**
     * Is this block powering in the specified direction Args: x, y, z, direction
     */
    public boolean isBlockFacePowered(int par1, int par2, int par3, int par4)
    {
        int var5 = this.getTypeId(par1, par2, par3);
        return var5 == 0 ? false : Block.byId[var5].c(this, par1, par2, par3, par4);
    }

    /**
     * Whether one of the neighboring blocks is putting power into this block. Args: x, y, z
     */
    public boolean isBlockPowered(int par1, int par2, int par3)
    {
        return this.isBlockFacePowered(par1, par2 - 1, par3, 0) ? true : (this.isBlockFacePowered(par1, par2 + 1, par3, 1) ? true : (this.isBlockFacePowered(par1, par2, par3 - 1, 2) ? true : (this.isBlockFacePowered(par1, par2, par3 + 1, 3) ? true : (this.isBlockFacePowered(par1 - 1, par2, par3, 4) ? true : this.isBlockFacePowered(par1 + 1, par2, par3, 5)))));
    }

    /**
     * Is a block next to you getting powered (if its an attachable block) or is it providing power directly to you.
     * Args: x, y, z, direction
     */
    public boolean isBlockFaceIndirectlyPowered(int par1, int par2, int par3, int par4)
    {
        if (this.t(par1, par2, par3))
        {
            return this.isBlockPowered(par1, par2, par3);
        }
        else
        {
            int var5 = this.getTypeId(par1, par2, par3);
            return var5 == 0 ? false : Block.byId[var5].b(this, par1, par2, par3, par4);
        }
    }

    /**
     * Used to see if one of the blocks next to you or your block is getting power from a neighboring block. Used by
     * items like TNT or Doors so they don't have redstone going straight into them.  Args: x, y, z
     */
    public boolean isBlockIndirectlyPowered(int par1, int par2, int par3)
    {
        return this.isBlockFaceIndirectlyPowered(par1, par2 - 1, par3, 0) ? true : (this.isBlockFaceIndirectlyPowered(par1, par2 + 1, par3, 1) ? true : (this.isBlockFaceIndirectlyPowered(par1, par2, par3 - 1, 2) ? true : (this.isBlockFaceIndirectlyPowered(par1, par2, par3 + 1, 3) ? true : (this.isBlockFaceIndirectlyPowered(par1 - 1, par2, par3, 4) ? true : this.isBlockFaceIndirectlyPowered(par1 + 1, par2, par3, 5)))));
    }

    /**
     * Gets the closest player to the entity within the specified distance (if distance is less than 0 then ignored).
     * Args: entity, dist
     */
    public EntityHuman findNearbyPlayer(Entity par1Entity, double par2)
    {
        return this.findNearbyPlayer(par1Entity.locX, par1Entity.locY, par1Entity.locZ, par2);
    }

    /**
     * Gets the closest player to the point within the specified distance (distance can be set to less than 0 to not
     * limit the distance). Args: x, y, z, dist
     */
    public EntityHuman findNearbyPlayer(double par1, double par3, double par5, double par7)
    {
        double var9 = -1.0D;
        EntityHuman var11 = null;

        for (int var12 = 0; var12 < this.players.size(); ++var12)
        {
            EntityHuman var13 = (EntityHuman)this.players.get(var12);
            double var14 = var13.e(par1, par3, par5);

            if ((par7 < 0.0D || var14 < par7 * par7) && (var9 == -1.0D || var14 < var9))
            {
                var9 = var14;
                var11 = var13;
            }
        }

        return var11;
    }

    /**
     * Returns the closest vulnerable player to this entity within the given radius, or null if none is found
     */
    public EntityHuman findNearbyVulnerablePlayer(Entity par1Entity, double par2)
    {
        return this.findNearbyVulnerablePlayer(par1Entity.locX, par1Entity.locY, par1Entity.locZ, par2);
    }

    /**
     * Returns the closest vulnerable player within the given radius, or null if none is found.
     */
    public EntityHuman findNearbyVulnerablePlayer(double par1, double par3, double par5, double par7)
    {
        double var9 = -1.0D;
        EntityHuman var11 = null;

        for (int var12 = 0; var12 < this.players.size(); ++var12)
        {
            EntityHuman var13 = (EntityHuman)this.players.get(var12);

            if (!var13.abilities.isInvulnerable && var13.isAlive())
            {
                double var14 = var13.e(par1, par3, par5);
                double var16 = par7;

                if (var13.isSneaking())
                {
                    var16 = par7 * 0.800000011920929D;
                }

                if (var13.isInvisible())
                {
                    float var18 = var13.bR();

                    if (var18 < 0.1F)
                    {
                        var18 = 0.1F;
                    }

                    var16 *= (double)(0.7F * var18);
                }

                if ((par7 < 0.0D || var14 < var16 * var16) && (var9 == -1.0D || var14 < var9))
                {
                    var9 = var14;
                    var11 = var13;
                }
            }
        }

        return var11;
    }

    /**
     * Find a player by name in this world.
     */
    public EntityHuman a(String par1Str)
    {
        for (int var2 = 0; var2 < this.players.size(); ++var2)
        {
            if (par1Str.equals(((EntityHuman)this.players.get(var2)).name))
            {
                return (EntityHuman)this.players.get(var2);
            }
        }

        return null;
    }

    /**
     * Checks whether the session lock file was modified by another process
     */
    public void D() throws ExceptionWorldConflict
    {
        this.dataManager.checkSession();
    }

    /**
     * gets the random world seed
     */
    public long getSeed()
    {
        return this.worldData.getSeed();
    }

    public long getTime()
    {
        return this.worldData.getTime();
    }

    public long getDayTime()
    {
        return this.worldData.getDayTime();
    }

    /**
     * Sets the world time.
     */
    public void setDayTime(long par1)
    {
        this.worldData.setDayTime(par1);
    }

    /**
     * Returns the coordinates of the spawn point
     */
    public ChunkCoordinates getSpawn()
    {
        return new ChunkCoordinates(this.worldData.c(), this.worldData.d(), this.worldData.e());
    }

    /**
     * Called when checking if a certain block can be mined or not. The 'spawn safe zone' check is located here.
     */
    public boolean a(EntityHuman par1EntityPlayer, int par2, int par3, int par4)
    {
        return true;
    }

    /**
     * sends a Packet 38 (Entity Status) to all tracked players of that entity
     */
    public void broadcastEntityEffect(Entity par1Entity, byte par2) {}

    /**
     * gets the world's chunk provider
     */
    public IChunkProvider I()
    {
        return this.chunkProvider;
    }

    /**
     * Adds a block event with the given Args to the blockEventCache. During the next tick(), the block specified will
     * have its onBlockEvent handler called with the given parameters. Args: X,Y,Z, BlockID, EventID, EventParameter
     */
    public void playNote(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        if (par4 > 0)
        {
            Block.byId[par4].b(this, par1, par2, par3, par5, par6);
        }
    }

    /**
     * Returns this world's current save handler
     */
    public IDataManager getDataManager()
    {
        return this.dataManager;
    }

    /**
     * Returns the world's WorldInfo object
     */
    public WorldData getWorldData()
    {
        return this.worldData;
    }

    /**
     * Gets the GameRules instance.
     */
    public GameRules getGameRules()
    {
        return this.worldData.getGameRules();
    }

    /**
     * Updates the flag that indicates whether or not all players in the world are sleeping.
     */
    public void everyoneSleeping() {}

    public float i(float par1)
    {
        return (this.o + (this.p - this.o) * par1) * this.j(par1);
    }

    /**
     * Not sure about this actually. Reverting this one myself.
     */
    public float j(float par1)
    {
        return this.m + (this.n - this.m) * par1;
    }

    /**
     * Returns true if the current thunder strength (weighted with the rain strength) is greater than 0.9
     */
    public boolean M()
    {
        return (double)this.i(1.0F) > 0.9D;
    }

    /**
     * Returns true if the current rain strength is greater than 0.2
     */
    public boolean N()
    {
        return (double)this.j(1.0F) > 0.2D;
    }

    public boolean D(int par1, int par2, int par3)
    {
        if (!this.N())
        {
            return false;
        }
        else if (!this.k(par1, par2, par3))
        {
            return false;
        }
        else if (this.h(par1, par3) > par2)
        {
            return false;
        }
        else
        {
            BiomeBase var4 = this.getBiome(par1, par3);
            return var4.c() ? false : var4.d();
        }
    }

    /**
     * Checks to see if the biome rainfall values for a given x,y,z coordinate set are extremely high
     */
    public boolean E(int par1, int par2, int par3)
    {
        BiomeBase var4 = this.getBiome(par1, par3);
        return var4.e();
    }

    /**
     * Assigns the given String id to the given MapDataBase using the MapStorage, removing any existing ones of the same
     * id.
     */
    public void a(String par1Str, WorldMapBase par2WorldSavedData)
    {
        this.worldMaps.a(par1Str, par2WorldSavedData);
    }

    /**
     * Loads an existing MapDataBase corresponding to the given String id from disk using the MapStorage, instantiating
     * the given Class, or returns null if none such file exists. args: Class to instantiate, String dataid
     */
    public WorldMapBase a(Class par1Class, String par2Str)
    {
        return this.worldMaps.get(par1Class, par2Str);
    }

    /**
     * Returns an unique new data id from the MapStorage for the given prefix and saves the idCounts map to the
     * 'idcounts' file.
     */
    public int b(String par1Str)
    {
        return this.worldMaps.a(par1Str);
    }

    public void e(int par1, int par2, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < this.v.size(); ++var6)
        {
            ((IWorldAccess)this.v.get(var6)).a(par1, par2, par3, par4, par5);
        }
    }

    /**
     * Plays a sound or particle effect. Parameters: Effect ID, X, Y, Z, Data. For a list of ids and data, see
     * http://wiki.vg/Protocol#Effects
     */
    public void triggerEffect(int par1, int par2, int par3, int par4, int par5)
    {
        this.a((EntityHuman) null, par1, par2, par3, par4, par5);
    }

    /**
     * See description for playAuxSFX.
     */
    public void a(EntityHuman par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
    {
        try
        {
            for (int var7 = 0; var7 < this.v.size(); ++var7)
            {
                ((IWorldAccess)this.v.get(var7)).a(par1EntityPlayer, par2, par3, par4, par5, par6);
            }
        }
        catch (Throwable var10)
        {
            CrashReport var8 = CrashReport.a(var10, "Playing level event");
            CrashReportSystemDetails var9 = var8.a("Level event being played");
            var9.a("Block coordinates", CrashReportSystemDetails.a(par3, par4, par5));
            var9.a("Event source", par1EntityPlayer);
            var9.a("Event type", Integer.valueOf(par2));
            var9.a("Event data", Integer.valueOf(par6));
            throw new ReportedException(var8);
        }
    }

    /**
     * Returns maximum world height.
     */
    public int getHeight()
    {
        return 256;
    }

    /**
     * Returns current world height.
     */
    public int P()
    {
        return this.worldProvider.f ? 128 : 256;
    }

    public IUpdatePlayerListBox a(EntityMinecart par1EntityMinecart)
    {
        return null;
    }

    /**
     * puts the World Random seed to a specific state dependant on the inputs
     */
    public Random F(int par1, int par2, int par3)
    {
        long var4 = (long)par1 * 341873128712L + (long)par2 * 132897987541L + this.getWorldData().getSeed() + (long)par3;
        this.random.setSeed(var4);
        return this.random;
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition b(String par1Str, int par2, int par3, int par4)
    {
        return this.I().findNearestMapFeature(this, par1Str, par2, par3, par4);
    }

    /**
     * Adds some basic stats of the world to the given crash report.
     */
    public CrashReportSystemDetails a(CrashReport par1CrashReport)
    {
        CrashReportSystemDetails var2 = par1CrashReport.a("Affected level", 1);
        var2.a("Level name", this.worldData == null ? "????" : this.worldData.getName());
        var2.a("All players", new CrashReportPlayers(this));
        var2.a("Chunk stats", new CrashReportChunkStats(this));

        try
        {
            this.worldData.a(var2);
        }
        catch (Throwable var4)
        {
            var2.a("Level Data Unobtainable", var4);
        }

        return var2;
    }

    /**
     * Starts (or continues) destroying a block with given ID at the given coordinates for the given partially destroyed
     * value
     */
    public void g(int par1, int par2, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < this.v.size(); ++var6)
        {
            IWorldAccess var7 = (IWorldAccess)this.v.get(var6);
            var7.b(par1, par2, par3, par4, par5);
        }
    }

    /**
     * Return the Vec3Pool object for this world.
     */
    public Vec3DPool getVec3DPool()
    {
        return this.J;
    }

    /**
     * returns a calendar object containing the current date
     */
    public Calendar T()
    {
        if (this.getTime() % 600L == 0L)
        {
            this.K.setTimeInMillis(System.currentTimeMillis());
        }

        return this.K;
    }
}
