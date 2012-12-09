package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChunkProviderFlat implements IChunkProvider
{
    private World a;
    private Random b;
    private final byte[] c = new byte[256];
    private final byte[] d = new byte[256];
    private final WorldGenFlatInfo e;
    private final List f = new ArrayList();
    private final boolean g;
    private final boolean h;
    private WorldGenLakes i;
    private WorldGenLakes j;

    public ChunkProviderFlat(World par1World, long par2, boolean par4, String par5Str)
    {
        this.a = par1World;
        this.b = new Random(par2);
        this.e = WorldGenFlatInfo.a(par5Str);

        if (par4)
        {
            Map var6 = this.e.b();

            if (var6.containsKey("village"))
            {
                Map var7 = (Map)var6.get("village");

                if (!var7.containsKey("size"))
                {
                    var7.put("size", "1");
                }

                this.f.add(new WorldGenVillage(var7));
            }

            if (var6.containsKey("biome_1"))
            {
                this.f.add(new WorldGenLargeFeature((Map)var6.get("biome_1")));
            }

            if (var6.containsKey("mineshaft"))
            {
                this.f.add(new WorldGenMineshaft((Map)var6.get("mineshaft")));
            }

            if (var6.containsKey("stronghold"))
            {
                this.f.add(new WorldGenStronghold((Map)var6.get("stronghold")));
            }
        }

        this.g = this.e.b().containsKey("decoration");

        if (this.e.b().containsKey("lake"))
        {
            this.i = new WorldGenLakes(Block.STATIONARY_WATER.id);
        }

        if (this.e.b().containsKey("lava_lake"))
        {
            this.j = new WorldGenLakes(Block.STATIONARY_LAVA.id);
        }

        this.h = this.e.b().containsKey("dungeon");
        Iterator var9 = this.e.c().iterator();

        while (var9.hasNext())
        {
            WorldGenFlatLayerInfo var10 = (WorldGenFlatLayerInfo)var9.next();

            for (int var8 = var10.d(); var8 < var10.d() + var10.a(); ++var8)
            {
                this.c[var8] = (byte)(var10.b() & 255);
                this.d[var8] = (byte)var10.c();
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk getChunkAt(int par1, int par2)
    {
        return this.getOrCreateChunk(par1, par2);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk getOrCreateChunk(int par1, int par2)
    {
        Chunk var3 = new Chunk(this.a, par1, par2);

        for (int var4 = 0; var4 < this.c.length; ++var4)
        {
            int var5 = var4 >> 4;
            ChunkSection var6 = var3.i()[var5];

            if (var6 == null)
            {
                var6 = new ChunkSection(var4);
                var3.i()[var5] = var6;
            }

            for (int var7 = 0; var7 < 16; ++var7)
            {
                for (int var8 = 0; var8 < 16; ++var8)
                {
                    var6.a(var7, var4 & 15, var8, this.c[var4] & 255);
                    var6.b(var7, var4 & 15, var8, this.d[var4]);
                }
            }
        }

        var3.initLighting();
        BiomeBase[] var9 = this.a.getWorldChunkManager().getBiomeBlock((BiomeBase[]) null, par1 * 16, par2 * 16, 16, 16);
        byte[] var10 = var3.m();

        for (int var11 = 0; var11 < var10.length; ++var11)
        {
            var10[var11] = (byte)var9[var11].id;
        }

        Iterator var12 = this.f.iterator();

        while (var12.hasNext())
        {
            StructureGenerator var13 = (StructureGenerator)var12.next();
            var13.a(this, this.a, par1, par2, (byte[]) null);
        }

        var3.initLighting();
        return var3;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean isChunkLoaded(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void getChunkAt(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        BiomeBase var6 = this.a.getBiome(var4 + 16, var5 + 16);
        boolean var7 = false;
        this.b.setSeed(this.a.getSeed());
        long var8 = this.b.nextLong() / 2L * 2L + 1L;
        long var10 = this.b.nextLong() / 2L * 2L + 1L;
        this.b.setSeed((long)par2 * var8 + (long)par3 * var10 ^ this.a.getSeed());
        Iterator var12 = this.f.iterator();

        while (var12.hasNext())
        {
            StructureGenerator var13 = (StructureGenerator)var12.next();
            boolean var14 = var13.a(this.a, this.b, par2, par3);

            if (var13 instanceof WorldGenVillage)
            {
                var7 |= var14;
            }
        }

        int var17;
        int var16;
        int var18;

        if (this.i != null && !var7 && this.b.nextInt(4) == 0)
        {
            var16 = var4 + this.b.nextInt(16) + 8;
            var17 = this.b.nextInt(128);
            var18 = var5 + this.b.nextInt(16) + 8;
            this.i.a(this.a, this.b, var16, var17, var18);
        }

        if (this.j != null && !var7 && this.b.nextInt(8) == 0)
        {
            var16 = var4 + this.b.nextInt(16) + 8;
            var17 = this.b.nextInt(this.b.nextInt(120) + 8);
            var18 = var5 + this.b.nextInt(16) + 8;

            if (var17 < 63 || this.b.nextInt(10) == 0)
            {
                this.j.a(this.a, this.b, var16, var17, var18);
            }
        }

        if (this.h)
        {
            for (var16 = 0; var16 < 8; ++var16)
            {
                var17 = var4 + this.b.nextInt(16) + 8;
                var18 = this.b.nextInt(128);
                int var15 = var5 + this.b.nextInt(16) + 8;
                (new WorldGenDungeons()).a(this.a, this.b, var17, var18, var15);
            }
        }

        if (this.g)
        {
            var6.a(this.a, this.b, var4, var5);
        }
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with chunkSet.add() never being called it thinks the list
     * is always empty and will not remove any chunks.
     */
    public boolean unloadChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String getName()
    {
        return "FlatLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getMobsFor(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        BiomeBase var5 = this.a.getBiome(par2, par4);
        return var5 == null ? null : var5.getMobs(par1EnumCreatureType);
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition findNearestMapFeature(World par1World, String par2Str, int par3, int par4, int par5)
    {
        if ("Stronghold".equals(par2Str))
        {
            Iterator var6 = this.f.iterator();

            while (var6.hasNext())
            {
                StructureGenerator var7 = (StructureGenerator)var6.next();

                if (var7 instanceof WorldGenStronghold)
                {
                    return var7.getNearestGeneratedFeature(par1World, par3, par4, par5);
                }
            }
        }

        return null;
    }

    public int getLoadedChunks()
    {
        return 0;
    }

    public void recreateStructures(int par1, int par2)
    {
        Iterator var3 = this.f.iterator();

        while (var3.hasNext())
        {
            StructureGenerator var4 = (StructureGenerator)var3.next();
            var4.a(this, this.a, par1, par2, (byte[]) null);
        }
    }
}
