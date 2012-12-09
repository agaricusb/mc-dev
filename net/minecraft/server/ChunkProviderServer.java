package net.minecraft.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChunkProviderServer implements IChunkProvider
{
    private Set unloadQueue = new HashSet();

    /** a dummy chunk, returned in place of an actual chunk. */
    private Chunk emptyChunk;

    /**
     * chunk generator object. Calls to load nonexistent chunks are forwarded to this object.
     */
    private IChunkProvider chunkProvider;
    private IChunkLoader e;

    /**
     * if set, this flag forces a request to load a chunk to load the chunk rather than defaulting to the dummy if
     * possible
     */
    public boolean forceChunkLoad = true;

    /** map of chunk Id's to Chunk instances */
    private LongHashMap chunks = new LongHashMap();
    private List chunkList = new ArrayList();
    private WorldServer world;

    public ChunkProviderServer(WorldServer par1WorldServer, IChunkLoader par2IChunkLoader, IChunkProvider par3IChunkProvider)
    {
        this.emptyChunk = new EmptyChunk(par1WorldServer, 0, 0);
        this.world = par1WorldServer;
        this.e = par2IChunkLoader;
        this.chunkProvider = par3IChunkProvider;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean isChunkLoaded(int par1, int par2)
    {
        return this.chunks.contains(ChunkCoordIntPair.a(par1, par2));
    }

    public void queueUnload(int par1, int par2)
    {
        if (this.world.worldProvider.e())
        {
            ChunkCoordinates var3 = this.world.getSpawn();
            int var4 = par1 * 16 + 8 - var3.x;
            int var5 = par2 * 16 + 8 - var3.z;
            short var6 = 128;

            if (var4 < -var6 || var4 > var6 || var5 < -var6 || var5 > var6)
            {
                this.unloadQueue.add(Long.valueOf(ChunkCoordIntPair.a(par1, par2)));
            }
        }
        else
        {
            this.unloadQueue.add(Long.valueOf(ChunkCoordIntPair.a(par1, par2)));
        }
    }

    /**
     * marks all chunks for unload, ignoring those near the spawn
     */
    public void a()
    {
        Iterator var1 = this.chunkList.iterator();

        while (var1.hasNext())
        {
            Chunk var2 = (Chunk)var1.next();
            this.queueUnload(var2.x, var2.z);
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk getChunkAt(int par1, int par2)
    {
        long var3 = ChunkCoordIntPair.a(par1, par2);
        this.unloadQueue.remove(Long.valueOf(var3));
        Chunk var5 = (Chunk)this.chunks.getEntry(var3);

        if (var5 == null)
        {
            var5 = this.loadChunk(par1, par2);

            if (var5 == null)
            {
                if (this.chunkProvider == null)
                {
                    var5 = this.emptyChunk;
                }
                else
                {
                    try
                    {
                        var5 = this.chunkProvider.getOrCreateChunk(par1, par2);
                    }
                    catch (Throwable var9)
                    {
                        CrashReport var7 = CrashReport.a(var9, "Exception generating new chunk");
                        CrashReportSystemDetails var8 = var7.a("Chunk to be generated");
                        var8.a("Location", String.format("%d,%d", new Object[]{Integer.valueOf(par1), Integer.valueOf(par2)}));
                        var8.a("Position hash", Long.valueOf(var3));
                        var8.a("Generator", this.chunkProvider.getName());
                        throw new ReportedException(var7);
                    }
                }
            }

            this.chunks.put(var3, var5);
            this.chunkList.add(var5);

            if (var5 != null)
            {
                var5.addEntities();
            }

            var5.a(this, this, par1, par2);
        }

        return var5;
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk getOrCreateChunk(int par1, int par2)
    {
        Chunk var3 = (Chunk)this.chunks.getEntry(ChunkCoordIntPair.a(par1, par2));
        return var3 == null ? (!this.world.isLoading && !this.forceChunkLoad ? this.emptyChunk : this.getChunkAt(par1, par2)) : var3;
    }

    private Chunk loadChunk(int par1, int par2)
    {
        if (this.e == null)
        {
            return null;
        }
        else
        {
            try
            {
                Chunk var3 = this.e.a(this.world, par1, par2);

                if (var3 != null)
                {
                    var3.n = this.world.getTime();

                    if (this.chunkProvider != null)
                    {
                        this.chunkProvider.recreateStructures(par1, par2);
                    }
                }

                return var3;
            }
            catch (Exception var4)
            {
                var4.printStackTrace();
                return null;
            }
        }
    }

    private void saveChunkNOP(Chunk par1Chunk)
    {
        if (this.e != null)
        {
            try
            {
                this.e.b(this.world, par1Chunk);
            }
            catch (Exception var3)
            {
                var3.printStackTrace();
            }
        }
    }

    private void saveChunk(Chunk par1Chunk)
    {
        if (this.e != null)
        {
            try
            {
                par1Chunk.n = this.world.getTime();
                this.e.a(this.world, par1Chunk);
            }
            catch (IOException var3)
            {
                var3.printStackTrace();
            }
            catch (ExceptionWorldConflict var4)
            {
                var4.printStackTrace();
            }
        }
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void getChunkAt(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        Chunk var4 = this.getOrCreateChunk(par2, par3);

        if (!var4.done)
        {
            var4.done = true;

            if (this.chunkProvider != null)
            {
                this.chunkProvider.getChunkAt(par1IChunkProvider, par2, par3);
                var4.e();
            }
        }
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        int var3 = 0;

        for (int var4 = 0; var4 < this.chunkList.size(); ++var4)
        {
            Chunk var5 = (Chunk)this.chunkList.get(var4);

            if (par1)
            {
                this.saveChunkNOP(var5);
            }

            if (var5.a(par1))
            {
                this.saveChunk(var5);
                var5.l = false;
                ++var3;

                if (var3 == 24 && !par1)
                {
                    return false;
                }
            }
        }

        if (par1)
        {
            if (this.e == null)
            {
                return true;
            }

            this.e.b();
        }

        return true;
    }

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with chunkSet.add() never being called it thinks the list
     * is always empty and will not remove any chunks.
     */
    public boolean unloadChunks()
    {
        if (!this.world.savingDisabled)
        {
            for (int var1 = 0; var1 < 100; ++var1)
            {
                if (!this.unloadQueue.isEmpty())
                {
                    Long var2 = (Long)this.unloadQueue.iterator().next();
                    Chunk var3 = (Chunk)this.chunks.getEntry(var2.longValue());
                    var3.removeEntities();
                    this.saveChunk(var3);
                    this.saveChunkNOP(var3);
                    this.unloadQueue.remove(var2);
                    this.chunks.remove(var2.longValue());
                    this.chunkList.remove(var3);
                }
            }

            if (this.e != null)
            {
                this.e.a();
            }
        }

        return this.chunkProvider.unloadChunks();
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return !this.world.savingDisabled;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String getName()
    {
        return "ServerChunkCache: " + this.chunks.count() + " Drop: " + this.unloadQueue.size();
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getMobsFor(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        return this.chunkProvider.getMobsFor(par1EnumCreatureType, par2, par3, par4);
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition findNearestMapFeature(World par1World, String par2Str, int par3, int par4, int par5)
    {
        return this.chunkProvider.findNearestMapFeature(par1World, par2Str, par3, par4, par5);
    }

    public int getLoadedChunks()
    {
        return this.chunks.count();
    }

    public void recreateStructures(int par1, int par2) {}
}
