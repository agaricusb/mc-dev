package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class BiomeCache
{
    /** Reference to the WorldChunkManager */
    private final WorldChunkManager a;

    /** The last time this BiomeCache was cleaned, in milliseconds. */
    private long b = 0L;

    /**
     * The map of keys to BiomeCacheBlocks. Keys are based on the chunk x, z coordinates as (x | z << 32).
     */
    private LongHashMap c = new LongHashMap();

    /** The list of cached BiomeCacheBlocks */
    private List d = new ArrayList();

    public BiomeCache(WorldChunkManager par1WorldChunkManager)
    {
        this.a = par1WorldChunkManager;
    }

    /**
     * Returns a biome cache block at location specified.
     */
    public BiomeCacheBlock a(int par1, int par2)
    {
        par1 >>= 4;
        par2 >>= 4;
        long var3 = (long)par1 & 4294967295L | ((long)par2 & 4294967295L) << 32;
        BiomeCacheBlock var5 = (BiomeCacheBlock)this.c.getEntry(var3);

        if (var5 == null)
        {
            var5 = new BiomeCacheBlock(this, par1, par2);
            this.c.put(var3, var5);
            this.d.add(var5);
        }

        var5.f = System.currentTimeMillis();
        return var5;
    }

    /**
     * Returns the BiomeGenBase related to the x, z position from the cache.
     */
    public BiomeBase b(int par1, int par2)
    {
        return this.a(par1, par2).a(par1, par2);
    }

    /**
     * Removes BiomeCacheBlocks from this cache that haven't been accessed in at least 30 seconds.
     */
    public void a()
    {
        long var1 = System.currentTimeMillis();
        long var3 = var1 - this.b;

        if (var3 > 7500L || var3 < 0L)
        {
            this.b = var1;

            for (int var5 = 0; var5 < this.d.size(); ++var5)
            {
                BiomeCacheBlock var6 = (BiomeCacheBlock)this.d.get(var5);
                long var7 = var1 - var6.f;

                if (var7 > 30000L || var7 < 0L)
                {
                    this.d.remove(var5--);
                    long var9 = (long)var6.d & 4294967295L | ((long)var6.e & 4294967295L) << 32;
                    this.c.remove(var9);
                }
            }
        }
    }

    /**
     * Returns the array of cached biome types in the BiomeCacheBlock at the given location.
     */
    public BiomeBase[] e(int par1, int par2)
    {
        return this.a(par1, par2).c;
    }

    /**
     * Get the world chunk manager object for a biome list.
     */
    static WorldChunkManager a(BiomeCache par0BiomeCache)
    {
        return par0BiomeCache.a;
    }
}
