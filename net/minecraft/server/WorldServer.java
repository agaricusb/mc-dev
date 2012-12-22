package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class WorldServer extends World
{
    private final MinecraftServer server;
    private final EntityTracker tracker;
    private final PlayerChunkMap manager;
    private Set L;

    /** All work to do in future ticks. */
    private TreeSet M;
    public ChunkProviderServer chunkProviderServer;

    /** Whether or not level saving is enabled */
    public boolean savingDisabled;

    /** is false if there are no players */
    private boolean N;
    private int emptyTime = 0;
    private final PortalTravelAgent P;

    /**
     * Double buffer of ServerBlockEventList[] for holding pending BlockEventData's
     */
    private NoteDataList[] Q = new NoteDataList[] {new NoteDataList((EmptyClass2)null), new NoteDataList((EmptyClass2)null)};

    /**
     * The index into the blockEventCache; either 0, or 1, toggled in sendBlockEventPackets  where all BlockEvent are
     * applied locally and send to clients.
     */
    private int R = 0;
    private static final StructurePieceTreasure[] S = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.STICK.id, 0, 1, 3, 10), new StructurePieceTreasure(Block.WOOD.id, 0, 1, 3, 10), new StructurePieceTreasure(Block.LOG.id, 0, 1, 3, 10), new StructurePieceTreasure(Item.STONE_AXE.id, 0, 1, 1, 3), new StructurePieceTreasure(Item.WOOD_AXE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.STONE_PICKAXE.id, 0, 1, 1, 3), new StructurePieceTreasure(Item.WOOD_PICKAXE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.APPLE.id, 0, 2, 3, 5), new StructurePieceTreasure(Item.BREAD.id, 0, 2, 3, 3)};

    /** An IntHashMap of entity IDs (integers) to their Entity objects. */
    private IntHashMap entitiesById;

    public WorldServer(MinecraftServer par1MinecraftServer, IDataManager par2ISaveHandler, String par3Str, int par4, WorldSettings par5WorldSettings, MethodProfiler par6Profiler)
    {
        super(par2ISaveHandler, par3Str, par5WorldSettings, WorldProvider.byDimension(par4), par6Profiler);
        this.server = par1MinecraftServer;
        this.tracker = new EntityTracker(this);
        this.manager = new PlayerChunkMap(this, par1MinecraftServer.getPlayerList().o());

        if (this.entitiesById == null)
        {
            this.entitiesById = new IntHashMap();
        }

        if (this.L == null)
        {
            this.L = new HashSet();
        }

        if (this.M == null)
        {
            this.M = new TreeSet();
        }

        this.P = new PortalTravelAgent(this);
    }

    /**
     * Runs a single tick for the world
     */
    public void doTick()
    {
        super.doTick();

        if (this.getWorldData().isHardcore() && this.difficulty < 3)
        {
            this.difficulty = 3;
        }

        this.worldProvider.d.b();

        if (this.everyoneDeeplySleeping())
        {
            boolean var1 = false;

            if (this.allowMonsters && this.difficulty >= 1)
            {
                ;
            }

            if (!var1)
            {
                long var2 = this.worldData.getDayTime() + 24000L;
                this.worldData.setDayTime(var2 - var2 % 24000L);
                this.d();
            }
        }

        this.methodProfiler.a("mobSpawner");

        if (this.getGameRules().getBoolean("doMobSpawning"))
        {
            SpawnerCreature.spawnEntities(this, this.allowMonsters, this.allowAnimals, this.worldData.getTime() % 400L == 0L);
        }

        this.methodProfiler.c("chunkSource");
        this.chunkProvider.unloadChunks();
        int var4 = this.a(1.0F);

        if (var4 != this.j)
        {
            this.j = var4;
        }

        this.V();
        this.worldData.setTime(this.worldData.getTime() + 1L);
        this.worldData.setDayTime(this.worldData.getDayTime() + 1L);
        this.methodProfiler.c("tickPending");
        this.a(false);
        this.methodProfiler.c("tickTiles");
        this.g();
        this.methodProfiler.c("chunkMap");
        this.manager.flush();
        this.methodProfiler.c("village");
        this.villages.tick();
        this.siegeManager.a();
        this.methodProfiler.c("portalForcer");
        this.P.a(this.getTime());
        this.methodProfiler.b();
        this.V();
    }

    /**
     * only spawns creatures allowed by the chunkProvider
     */
    public BiomeMeta a(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        List var5 = this.I().getMobsFor(par1EnumCreatureType, par2, par3, par4);
        return var5 != null && !var5.isEmpty() ? (BiomeMeta) WeightedRandom.a(this.random, var5) : null;
    }

    /**
     * Updates the flag that indicates whether or not all players in the world are sleeping.
     */
    public void everyoneSleeping()
    {
        this.N = !this.players.isEmpty();
        Iterator var1 = this.players.iterator();

        while (var1.hasNext())
        {
            EntityHuman var2 = (EntityHuman)var1.next();

            if (!var2.isSleeping())
            {
                this.N = false;
                break;
            }
        }
    }

    protected void d()
    {
        this.N = false;
        Iterator var1 = this.players.iterator();

        while (var1.hasNext())
        {
            EntityHuman var2 = (EntityHuman)var1.next();

            if (var2.isSleeping())
            {
                var2.a(false, false, true);
            }
        }

        this.U();
    }

    private void U()
    {
        this.worldData.setWeatherDuration(0);
        this.worldData.setStorm(false);
        this.worldData.setThunderDuration(0);
        this.worldData.setThundering(false);
    }

    public boolean everyoneDeeplySleeping()
    {
        if (this.N && !this.isStatic)
        {
            Iterator var1 = this.players.iterator();
            EntityHuman var2;

            do
            {
                if (!var1.hasNext())
                {
                    return true;
                }

                var2 = (EntityHuman)var1.next();
            }
            while (var2.isDeeplySleeping());

            return false;
        }
        else
        {
            return false;
        }
    }

    /**
     * plays random cave ambient sounds and runs updateTick on random blocks within each chunk in the vacinity of a
     * player
     */
    protected void g()
    {
        super.g();
        int var1 = 0;
        int var2 = 0;
        Iterator var3 = this.chunkTickList.iterator();

        while (var3.hasNext())
        {
            ChunkCoordIntPair var4 = (ChunkCoordIntPair)var3.next();
            int var5 = var4.x * 16;
            int var6 = var4.z * 16;
            this.methodProfiler.a("getChunk");
            Chunk var7 = this.getChunkAt(var4.x, var4.z);
            this.a(var5, var6, var7);
            this.methodProfiler.c("tickChunk");
            var7.k();
            this.methodProfiler.c("thunder");
            int var8;
            int var9;
            int var10;
            int var11;

            if (this.random.nextInt(100000) == 0 && this.N() && this.M())
            {
                this.k = this.k * 3 + 1013904223;
                var8 = this.k >> 2;
                var9 = var5 + (var8 & 15);
                var10 = var6 + (var8 >> 8 & 15);
                var11 = this.h(var9, var10);

                if (this.D(var9, var11, var10))
                {
                    this.strikeLightning(new EntityLightning(this, (double) var9, (double) var11, (double) var10));
                }
            }

            this.methodProfiler.c("iceandsnow");
            int var13;

            if (this.random.nextInt(16) == 0)
            {
                this.k = this.k * 3 + 1013904223;
                var8 = this.k >> 2;
                var9 = var8 & 15;
                var10 = var8 >> 8 & 15;
                var11 = this.h(var9 + var5, var10 + var6);

                if (this.x(var9 + var5, var11 - 1, var10 + var6))
                {
                    this.setTypeId(var9 + var5, var11 - 1, var10 + var6, Block.ICE.id);
                }

                if (this.N() && this.y(var9 + var5, var11, var10 + var6))
                {
                    this.setTypeId(var9 + var5, var11, var10 + var6, Block.SNOW.id);
                }

                if (this.N())
                {
                    BiomeBase var12 = this.getBiome(var9 + var5, var10 + var6);

                    if (var12.d())
                    {
                        var13 = this.getTypeId(var9 + var5, var11 - 1, var10 + var6);

                        if (var13 != 0)
                        {
                            Block.byId[var13].f(this, var9 + var5, var11 - 1, var10 + var6);
                        }
                    }
                }
            }

            this.methodProfiler.c("tickTiles");
            ChunkSection[] var19 = var7.i();
            var9 = var19.length;

            for (var10 = 0; var10 < var9; ++var10)
            {
                ChunkSection var21 = var19[var10];

                if (var21 != null && var21.b())
                {
                    for (int var20 = 0; var20 < 3; ++var20)
                    {
                        this.k = this.k * 3 + 1013904223;
                        var13 = this.k >> 2;
                        int var14 = var13 & 15;
                        int var15 = var13 >> 8 & 15;
                        int var16 = var13 >> 16 & 15;
                        int var17 = var21.a(var14, var16, var15);
                        ++var2;
                        Block var18 = Block.byId[var17];

                        if (var18 != null && var18.isTicking())
                        {
                            ++var1;
                            var18.b(this, var14 + var5, var16 + var21.d(), var15 + var6, this.random);
                        }
                    }
                }
            }

            this.methodProfiler.b();
        }
    }

    /**
     * Used to schedule a call to the updateTick method on the specified block.
     */
    public void a(int par1, int par2, int par3, int par4, int par5)
    {
        this.a(par1, par2, par3, par4, par5, 0);
    }

    public void a(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        NextTickListEntry var7 = new NextTickListEntry(par1, par2, par3, par4);
        byte var8 = 8;

        if (this.d && par4 > 0)
        {
            if (Block.byId[par4].l())
            {
                if (this.d(var7.a - var8, var7.b - var8, var7.c - var8, var7.a + var8, var7.b + var8, var7.c + var8))
                {
                    int var9 = this.getTypeId(var7.a, var7.b, var7.c);

                    if (var9 == var7.d && var9 > 0)
                    {
                        Block.byId[var9].b(this, var7.a, var7.b, var7.c, this.random);
                    }
                }

                return;
            }

            par5 = 1;
        }

        if (this.d(par1 - var8, par2 - var8, par3 - var8, par1 + var8, par2 + var8, par3 + var8))
        {
            if (par4 > 0)
            {
                var7.a((long) par5 + this.worldData.getTime());
                var7.a(par6);
            }

            if (!this.L.contains(var7))
            {
                this.L.add(var7);
                this.M.add(var7);
            }
        }
    }

    /**
     * Schedules a block update from the saved information in a chunk. Called when the chunk is loaded.
     */
    public void b(int par1, int par2, int par3, int par4, int par5)
    {
        NextTickListEntry var6 = new NextTickListEntry(par1, par2, par3, par4);

        if (par4 > 0)
        {
            var6.a((long) par5 + this.worldData.getTime());
        }

        if (!this.L.contains(var6))
        {
            this.L.add(var6);
            this.M.add(var6);
        }
    }

    /**
     * Updates (and cleans up) entities and tile entities
     */
    public void tickEntities()
    {
        if (this.players.isEmpty())
        {
            if (this.emptyTime++ >= 1200)
            {
                return;
            }
        }
        else
        {
            this.i();
        }

        super.tickEntities();
    }

    /**
     * Resets the updateEntityTick field to 0
     */
    public void i()
    {
        this.emptyTime = 0;
    }

    /**
     * Runs through the list of updates to run and ticks them
     */
    public boolean a(boolean par1)
    {
        int var2 = this.M.size();

        if (var2 != this.L.size())
        {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        else
        {
            if (var2 > 1000)
            {
                var2 = 1000;
            }

            for (int var3 = 0; var3 < var2; ++var3)
            {
                NextTickListEntry var4 = (NextTickListEntry)this.M.first();

                if (!par1 && var4.e > this.worldData.getTime())
                {
                    break;
                }

                this.M.remove(var4);
                this.L.remove(var4);
                byte var5 = 8;

                if (this.d(var4.a - var5, var4.b - var5, var4.c - var5, var4.a + var5, var4.b + var5, var4.c + var5))
                {
                    int var6 = this.getTypeId(var4.a, var4.b, var4.c);

                    if (var6 == var4.d && var6 > 0)
                    {
                        try
                        {
                            Block.byId[var6].b(this, var4.a, var4.b, var4.c, this.random);
                        }
                        catch (Throwable var13)
                        {
                            CrashReport var8 = CrashReport.a(var13, "Exception while ticking a block");
                            CrashReportSystemDetails var9 = var8.a("Block being ticked");
                            int var10;

                            try
                            {
                                var10 = this.getData(var4.a, var4.b, var4.c);
                            }
                            catch (Throwable var12)
                            {
                                var10 = -1;
                            }

                            CrashReportSystemDetails.a(var9, var4.a, var4.b, var4.c, var6, var10);
                            throw new ReportedException(var8);
                        }
                    }
                }
            }

            return !this.M.isEmpty();
        }
    }

    public List a(Chunk par1Chunk, boolean par2)
    {
        ArrayList var3 = null;
        ChunkCoordIntPair var4 = par1Chunk.l();
        int var5 = var4.x << 4;
        int var6 = var5 + 16;
        int var7 = var4.z << 4;
        int var8 = var7 + 16;
        Iterator var9 = this.M.iterator();

        while (var9.hasNext())
        {
            NextTickListEntry var10 = (NextTickListEntry)var9.next();

            if (var10.a >= var5 && var10.a < var6 && var10.c >= var7 && var10.c < var8)
            {
                if (par2)
                {
                    this.L.remove(var10);
                    var9.remove();
                }

                if (var3 == null)
                {
                    var3 = new ArrayList();
                }

                var3.add(var10);
            }
        }

        return var3;
    }

    /**
     * Will update the entity in the world if the chunk the entity is in is currently loaded or its forced to update.
     * Args: entity, forceUpdate
     */
    public void entityJoinedWorld(Entity par1Entity, boolean par2)
    {
        if (!this.server.getSpawnAnimals() && (par1Entity instanceof EntityAnimal || par1Entity instanceof EntityWaterAnimal))
        {
            par1Entity.die();
        }

        if (!this.server.getSpawnNPCs() && par1Entity instanceof NPC)
        {
            par1Entity.die();
        }

        if (!(par1Entity.passenger instanceof EntityHuman))
        {
            super.entityJoinedWorld(par1Entity, par2);
        }
    }

    /**
     * direct call to super.updateEntityWithOptionalForce
     */
    public void vehicleEnteredWorld(Entity par1Entity, boolean par2)
    {
        super.entityJoinedWorld(par1Entity, par2);
    }

    /**
     * Creates the chunk provider for this world. Called in the constructor. Retrieves provider from worldProvider?
     */
    protected IChunkProvider j()
    {
        IChunkLoader var1 = this.dataManager.createChunkLoader(this.worldProvider);
        this.chunkProviderServer = new ChunkProviderServer(this, var1, this.worldProvider.getChunkProvider());
        return this.chunkProviderServer;
    }

    /**
     * get a list of tileEntity's
     */
    public List getTileEntities(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        ArrayList var7 = new ArrayList();

        for (int var8 = 0; var8 < this.tileEntityList.size(); ++var8)
        {
            TileEntity var9 = (TileEntity)this.tileEntityList.get(var8);

            if (var9.x >= par1 && var9.y >= par2 && var9.z >= par3 && var9.x < par4 && var9.y < par5 && var9.z < par6)
            {
                var7.add(var9);
            }
        }

        return var7;
    }

    /**
     * Called when checking if a certain block can be mined or not. The 'spawn safe zone' check is located here.
     */
    public boolean a(EntityHuman par1EntityPlayer, int par2, int par3, int par4)
    {
        int var5 = MathHelper.a(par2 - this.worldData.c());
        int var6 = MathHelper.a(par4 - this.worldData.e());

        if (var5 > var6)
        {
            var6 = var5;
        }

        return var6 > 16 || this.server.getPlayerList().isOp(par1EntityPlayer.name) || this.server.I();
    }

    protected void a(WorldSettings par1WorldSettings)
    {
        if (this.entitiesById == null)
        {
            this.entitiesById = new IntHashMap();
        }

        if (this.L == null)
        {
            this.L = new HashSet();
        }

        if (this.M == null)
        {
            this.M = new TreeSet();
        }

        this.b(par1WorldSettings);
        super.a(par1WorldSettings);
    }

    /**
     * creates a spawn position at random within 256 blocks of 0,0
     */
    protected void b(WorldSettings par1WorldSettings)
    {
        if (!this.worldProvider.e())
        {
            this.worldData.setSpawn(0, this.worldProvider.getSeaLevel(), 0);
        }
        else
        {
            this.isLoading = true;
            WorldChunkManager var2 = this.worldProvider.d;
            List var3 = var2.a();
            Random var4 = new Random(this.getSeed());
            ChunkPosition var5 = var2.a(0, 0, 256, var3, var4);
            int var6 = 0;
            int var7 = this.worldProvider.getSeaLevel();
            int var8 = 0;

            if (var5 != null)
            {
                var6 = var5.x;
                var8 = var5.z;
            }
            else
            {
                System.out.println("Unable to find spawn biome");
            }

            int var9 = 0;

            while (!this.worldProvider.canSpawn(var6, var8))
            {
                var6 += var4.nextInt(64) - var4.nextInt(64);
                var8 += var4.nextInt(64) - var4.nextInt(64);
                ++var9;

                if (var9 == 1000)
                {
                    break;
                }
            }

            this.worldData.setSpawn(var6, var7, var8);
            this.isLoading = false;

            if (par1WorldSettings.c())
            {
                this.k();
            }
        }
    }

    /**
     * Creates the bonus chest in the world.
     */
    protected void k()
    {
        WorldGenBonusChest var1 = new WorldGenBonusChest(S, 10);

        for (int var2 = 0; var2 < 10; ++var2)
        {
            int var3 = this.worldData.c() + this.random.nextInt(6) - this.random.nextInt(6);
            int var4 = this.worldData.e() + this.random.nextInt(6) - this.random.nextInt(6);
            int var5 = this.i(var3, var4) + 1;

            if (var1.a(this, this.random, var3, var5, var4))
            {
                break;
            }
        }
    }

    /**
     * Gets the hard-coded portal location to use when entering this dimension.
     */
    public ChunkCoordinates getDimensionSpawn()
    {
        return this.worldProvider.h();
    }

    /**
     * Saves all chunks to disk while updating progress bar.
     */
    public void save(boolean par1, IProgressUpdate par2IProgressUpdate) throws ExceptionWorldConflict
    {
        if (this.chunkProvider.canSave())
        {
            if (par2IProgressUpdate != null)
            {
                par2IProgressUpdate.a("Saving level");
            }

            this.a();

            if (par2IProgressUpdate != null)
            {
                par2IProgressUpdate.c("Saving chunks");
            }

            this.chunkProvider.saveChunks(par1, par2IProgressUpdate);
        }
    }

    /**
     * Saves the chunks to disk.
     */
    protected void a() throws ExceptionWorldConflict
    {
        this.D();
        this.dataManager.saveWorldData(this.worldData, this.server.getPlayerList().q());
        this.worldMaps.a();
    }

    /**
     * Start the skin for this entity downloading, if necessary, and increment its reference counter
     */
    protected void a(Entity par1Entity)
    {
        super.a(par1Entity);
        this.entitiesById.a(par1Entity.id, par1Entity);
        Entity[] var2 = par1Entity.ao();

        if (var2 != null)
        {
            for (int var3 = 0; var3 < var2.length; ++var3)
            {
                this.entitiesById.a(var2[var3].id, var2[var3]);
            }
        }
    }

    /**
     * Decrement the reference counter for this entity's skin image data
     */
    protected void b(Entity par1Entity)
    {
        super.b(par1Entity);
        this.entitiesById.d(par1Entity.id);
        Entity[] var2 = par1Entity.ao();

        if (var2 != null)
        {
            for (int var3 = 0; var3 < var2.length; ++var3)
            {
                this.entitiesById.d(var2[var3].id);
            }
        }
    }

    /**
     * Returns the Entity with the given ID, or null if it doesn't exist in this World.
     */
    public Entity getEntity(int par1)
    {
        return (Entity)this.entitiesById.get(par1);
    }

    /**
     * adds a lightning bolt to the list of lightning bolts in this world.
     */
    public boolean strikeLightning(Entity par1Entity)
    {
        if (super.strikeLightning(par1Entity))
        {
            this.server.getPlayerList().sendPacketNearby(par1Entity.locX, par1Entity.locY, par1Entity.locZ, 512.0D, this.worldProvider.dimension, new Packet71Weather(par1Entity));
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * sends a Packet 38 (Entity Status) to all tracked players of that entity
     */
    public void broadcastEntityEffect(Entity par1Entity, byte par2)
    {
        Packet38EntityStatus var3 = new Packet38EntityStatus(par1Entity.id, par2);
        this.getTracker().sendPacketToEntity(par1Entity, var3);
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
        var11.a(false);

        if (!par10)
        {
            var11.blocks.clear();
        }

        Iterator var12 = this.players.iterator();

        while (var12.hasNext())
        {
            EntityHuman var13 = (EntityHuman)var12.next();

            if (var13.e(par2, par4, par6) < 4096.0D)
            {
                ((EntityPlayer)var13).playerConnection.sendPacket(new Packet60Explosion(par2, par4, par6, par8, var11.blocks, (Vec3D) var11.b().get(var13)));
            }
        }

        return var11;
    }

    /**
     * Adds a block event with the given Args to the blockEventCache. During the next tick(), the block specified will
     * have its onBlockEvent handler called with the given parameters. Args: X,Y,Z, BlockID, EventID, EventParameter
     */
    public void playNote(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        NoteBlockData var7 = new NoteBlockData(par1, par2, par3, par4, par5, par6);
        Iterator var8 = this.Q[this.R].iterator();
        NoteBlockData var9;

        do
        {
            if (!var8.hasNext())
            {
                this.Q[this.R].add(var7);
                return;
            }

            var9 = (NoteBlockData)var8.next();
        }
        while (!var9.equals(var7));
    }

    /**
     * Send and apply locally all pending BlockEvents to each player with 64m radius of the event.
     */
    private void V()
    {
        while (!this.Q[this.R].isEmpty())
        {
            int var1 = this.R;
            this.R ^= 1;
            Iterator var2 = this.Q[var1].iterator();

            while (var2.hasNext())
            {
                NoteBlockData var3 = (NoteBlockData)var2.next();

                if (this.a(var3))
                {
                    this.server.getPlayerList().sendPacketNearby((double) var3.a(), (double) var3.b(), (double) var3.c(), 64.0D, this.worldProvider.dimension, new Packet54PlayNoteBlock(var3.a(), var3.b(), var3.c(), var3.f(), var3.d(), var3.e()));
                }
            }

            this.Q[var1].clear();
        }
    }

    /**
     * Called to apply a pending BlockEvent to apply to the current world.
     */
    private boolean a(NoteBlockData par1BlockEventData)
    {
        int var2 = this.getTypeId(par1BlockEventData.a(), par1BlockEventData.b(), par1BlockEventData.c());

        if (var2 == par1BlockEventData.f())
        {
            Block.byId[var2].b(this, par1BlockEventData.a(), par1BlockEventData.b(), par1BlockEventData.c(), par1BlockEventData.d(), par1BlockEventData.e());
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Syncs all changes to disk and wait for completion.
     */
    public void saveLevel()
    {
        this.dataManager.a();
    }

    /**
     * Updates all weather states.
     */
    protected void n()
    {
        boolean var1 = this.N();
        super.n();

        if (var1 != this.N())
        {
            if (var1)
            {
                this.server.getPlayerList().sendAll(new Packet70Bed(2, 0));
            }
            else
            {
                this.server.getPlayerList().sendAll(new Packet70Bed(1, 0));
            }
        }
    }

    /**
     * Gets the MinecraftServer.
     */
    public MinecraftServer getMinecraftServer()
    {
        return this.server;
    }

    /**
     * Gets the EntityTracker
     */
    public EntityTracker getTracker()
    {
        return this.tracker;
    }

    public PlayerChunkMap getPlayerChunkMap()
    {
        return this.manager;
    }

    public PortalTravelAgent s()
    {
        return this.P;
    }
}
