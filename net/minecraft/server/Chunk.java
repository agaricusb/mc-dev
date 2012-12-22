package net.minecraft.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Chunk
{
    /**
     * Determines if the chunk is lit or not at a light value greater than 0.
     */
    public static boolean a;

    /**
     * Used to store block IDs, block MSBs, Sky-light maps, Block-light maps, and metadata. Each entry corresponds to a
     * logical segment of 16x16x16 blocks, stacked vertically.
     */
    private ChunkSection[] sections;

    /**
     * Contains a 16x16 mapping on the X/Z plane of the biome ID to which each colum belongs.
     */
    private byte[] s;

    /**
     * A map, similar to heightMap, that tracks how far down precipitation can fall.
     */
    public int[] b;

    /** Which columns need their skylightMaps updated. */
    public boolean[] c;

    /** Whether or not this Chunk is currently loaded into the World */
    public boolean d;

    /** Reference to the World object. */
    public World world;
    public int[] heightMap;

    /** The x coordinate of the chunk. */
    public final int x;

    /** The z coordinate of the chunk. */
    public final int z;
    private boolean t;

    /** A Map of ChunkPositions to TileEntities in this chunk */
    public Map tileEntities;

    /**
     * Array of Lists containing the entities in this Chunk. Each List represents a 16 block subchunk.
     */
    public List[] entitySlices;

    /** Boolean value indicating if the terrain is populated. */
    public boolean done;

    /**
     * Set to true if the chunk has been modified and needs to be updated internally.
     */
    public boolean l;

    /**
     * Whether this Chunk has any Entities and thus requires saving on every tick
     */
    public boolean m;

    /** The time according to World.worldTime when this chunk was last saved */
    public long n;
    public boolean seenByPlayer;
    public int p;

    /**
     * Contains the current round-robin relight check index, and is implied as the relight check location as well.
     */
    private int u;
    boolean q;

    public Chunk(World par1World, int par2, int par3)
    {
        this.sections = new ChunkSection[16];
        this.s = new byte[256];
        this.b = new int[256];
        this.c = new boolean[256];
        this.t = false;
        this.tileEntities = new HashMap();
        this.done = false;
        this.l = false;
        this.m = false;
        this.n = 0L;
        this.seenByPlayer = false;
        this.p = 0;
        this.u = 4096;
        this.q = false;
        this.entitySlices = new List[16];
        this.world = par1World;
        this.x = par2;
        this.z = par3;
        this.heightMap = new int[256];

        for (int var4 = 0; var4 < this.entitySlices.length; ++var4)
        {
            this.entitySlices[var4] = new ArrayList();
        }

        Arrays.fill(this.b, -999);
        Arrays.fill(this.s, (byte) - 1);
    }

    public Chunk(World par1World, byte[] par2ArrayOfByte, int par3, int par4)
    {
        this(par1World, par3, par4);
        int var5 = par2ArrayOfByte.length / 256;

        for (int var6 = 0; var6 < 16; ++var6)
        {
            for (int var7 = 0; var7 < 16; ++var7)
            {
                for (int var8 = 0; var8 < var5; ++var8)
                {
                    byte var9 = par2ArrayOfByte[var6 << 11 | var7 << 7 | var8];

                    if (var9 != 0)
                    {
                        int var10 = var8 >> 4;

                        if (this.sections[var10] == null)
                        {
                            this.sections[var10] = new ChunkSection(var10 << 4, !par1World.worldProvider.f);
                        }

                        this.sections[var10].a(var6, var8 & 15, var7, var9);
                    }
                }
            }
        }
    }

    /**
     * Checks whether the chunk is at the X/Z location specified
     */
    public boolean a(int par1, int par2)
    {
        return par1 == this.x && par2 == this.z;
    }

    /**
     * Returns the value in the height map at this x, z coordinate in the chunk
     */
    public int b(int par1, int par2)
    {
        return this.heightMap[par2 << 4 | par1];
    }

    /**
     * Returns the topmost ExtendedBlockStorage instance for this Chunk that actually contains a block.
     */
    public int h()
    {
        for (int var1 = this.sections.length - 1; var1 >= 0; --var1)
        {
            if (this.sections[var1] != null)
            {
                return this.sections[var1].d();
            }
        }

        return 0;
    }

    /**
     * Returns the ExtendedBlockStorage array for this Chunk.
     */
    public ChunkSection[] i()
    {
        return this.sections;
    }

    /**
     * Generates the initial skylight map for the chunk upon generation or load.
     */
    public void initLighting()
    {
        int var1 = this.h();
        this.p = Integer.MAX_VALUE;
        int var2;
        int var3;

        for (var2 = 0; var2 < 16; ++var2)
        {
            var3 = 0;

            while (var3 < 16)
            {
                this.b[var2 + (var3 << 4)] = -999;
                int var4 = var1 + 16 - 1;

                while (true)
                {
                    if (var4 > 0)
                    {
                        if (this.b(var2, var4 - 1, var3) == 0)
                        {
                            --var4;
                            continue;
                        }

                        this.heightMap[var3 << 4 | var2] = var4;

                        if (var4 < this.p)
                        {
                            this.p = var4;
                        }
                    }

                    if (!this.world.worldProvider.f)
                    {
                        var4 = 15;
                        int var5 = var1 + 16 - 1;

                        do
                        {
                            var4 -= this.b(var2, var5, var3);

                            if (var4 > 0)
                            {
                                ChunkSection var6 = this.sections[var5 >> 4];

                                if (var6 != null)
                                {
                                    var6.c(var2, var5 & 15, var3, var4);
                                    this.world.o((this.x << 4) + var2, var5, (this.z << 4) + var3);
                                }
                            }

                            --var5;
                        }
                        while (var5 > 0 && var4 > 0);
                    }

                    ++var3;
                    break;
                }
            }
        }

        this.l = true;

        for (var2 = 0; var2 < 16; ++var2)
        {
            for (var3 = 0; var3 < 16; ++var3)
            {
                this.e(var2, var3);
            }
        }
    }

    /**
     * Propagates a given sky-visible block's light value downward and upward to neighboring blocks as necessary.
     */
    private void e(int par1, int par2)
    {
        this.c[par1 + par2 * 16] = true;
        this.t = true;
    }

    /**
     * Runs delayed skylight updates.
     */
    private void q()
    {
        this.world.methodProfiler.a("recheckGaps");

        if (this.world.areChunksLoaded(this.x * 16 + 8, 0, this.z * 16 + 8, 16))
        {
            for (int var1 = 0; var1 < 16; ++var1)
            {
                for (int var2 = 0; var2 < 16; ++var2)
                {
                    if (this.c[var1 + var2 * 16])
                    {
                        this.c[var1 + var2 * 16] = false;
                        int var3 = this.b(var1, var2);
                        int var4 = this.x * 16 + var1;
                        int var5 = this.z * 16 + var2;
                        int var6 = this.world.g(var4 - 1, var5);
                        int var7 = this.world.g(var4 + 1, var5);
                        int var8 = this.world.g(var4, var5 - 1);
                        int var9 = this.world.g(var4, var5 + 1);

                        if (var7 < var6)
                        {
                            var6 = var7;
                        }

                        if (var8 < var6)
                        {
                            var6 = var8;
                        }

                        if (var9 < var6)
                        {
                            var6 = var9;
                        }

                        this.g(var4, var5, var6);
                        this.g(var4 - 1, var5, var3);
                        this.g(var4 + 1, var5, var3);
                        this.g(var4, var5 - 1, var3);
                        this.g(var4, var5 + 1, var3);
                    }
                }
            }

            this.t = false;
        }

        this.world.methodProfiler.b();
    }

    /**
     * Checks the height of a block next to a sky-visible block and schedules a lighting update as necessary.
     */
    private void g(int par1, int par2, int par3)
    {
        int var4 = this.world.getHighestBlockYAt(par1, par2);

        if (var4 > par3)
        {
            this.d(par1, par2, par3, var4 + 1);
        }
        else if (var4 < par3)
        {
            this.d(par1, par2, var4, par3 + 1);
        }
    }

    private void d(int par1, int par2, int par3, int par4)
    {
        if (par4 > par3 && this.world.areChunksLoaded(par1, 0, par2, 16))
        {
            for (int var5 = par3; var5 < par4; ++var5)
            {
                this.world.c(EnumSkyBlock.SKY, par1, var5, par2);
            }

            this.l = true;
        }
    }

    /**
     * Initiates the recalculation of both the block-light and sky-light for a given block inside a chunk.
     */
    private void h(int par1, int par2, int par3)
    {
        int var4 = this.heightMap[par3 << 4 | par1] & 255;
        int var5 = var4;

        if (par2 > var4)
        {
            var5 = par2;
        }

        while (var5 > 0 && this.b(par1, var5 - 1, par3) == 0)
        {
            --var5;
        }

        if (var5 != var4)
        {
            this.world.g(par1 + this.x * 16, par3 + this.z * 16, var5, var4);
            this.heightMap[par3 << 4 | par1] = var5;
            int var6 = this.x * 16 + par1;
            int var7 = this.z * 16 + par3;
            int var8;
            int var12;

            if (!this.world.worldProvider.f)
            {
                ChunkSection var9;

                if (var5 < var4)
                {
                    for (var8 = var5; var8 < var4; ++var8)
                    {
                        var9 = this.sections[var8 >> 4];

                        if (var9 != null)
                        {
                            var9.c(par1, var8 & 15, par3, 15);
                            this.world.o((this.x << 4) + par1, var8, (this.z << 4) + par3);
                        }
                    }
                }
                else
                {
                    for (var8 = var4; var8 < var5; ++var8)
                    {
                        var9 = this.sections[var8 >> 4];

                        if (var9 != null)
                        {
                            var9.c(par1, var8 & 15, par3, 0);
                            this.world.o((this.x << 4) + par1, var8, (this.z << 4) + par3);
                        }
                    }
                }

                var8 = 15;

                while (var5 > 0 && var8 > 0)
                {
                    --var5;
                    var12 = this.b(par1, var5, par3);

                    if (var12 == 0)
                    {
                        var12 = 1;
                    }

                    var8 -= var12;

                    if (var8 < 0)
                    {
                        var8 = 0;
                    }

                    ChunkSection var10 = this.sections[var5 >> 4];

                    if (var10 != null)
                    {
                        var10.c(par1, var5 & 15, par3, var8);
                    }
                }
            }

            var8 = this.heightMap[par3 << 4 | par1];
            var12 = var4;
            int var13 = var8;

            if (var8 < var4)
            {
                var12 = var8;
                var13 = var4;
            }

            if (var8 < this.p)
            {
                this.p = var8;
            }

            if (!this.world.worldProvider.f)
            {
                this.d(var6 - 1, var7, var12, var13);
                this.d(var6 + 1, var7, var12, var13);
                this.d(var6, var7 - 1, var12, var13);
                this.d(var6, var7 + 1, var12, var13);
                this.d(var6, var7, var12, var13);
            }

            this.l = true;
        }
    }

    public int b(int par1, int par2, int par3)
    {
        return Block.lightBlock[this.getTypeId(par1, par2, par3)];
    }

    /**
     * Return the ID of a block in the chunk.
     */
    public int getTypeId(int par1, int par2, int par3)
    {
        if (par2 >> 4 >= this.sections.length)
        {
            return 0;
        }
        else
        {
            ChunkSection var4 = this.sections[par2 >> 4];
            return var4 != null ? var4.a(par1, par2 & 15, par3) : 0;
        }
    }

    /**
     * Return the metadata corresponding to the given coordinates inside a chunk.
     */
    public int getData(int par1, int par2, int par3)
    {
        if (par2 >> 4 >= this.sections.length)
        {
            return 0;
        }
        else
        {
            ChunkSection var4 = this.sections[par2 >> 4];
            return var4 != null ? var4.b(par1, par2 & 15, par3) : 0;
        }
    }

    /**
     * Sets a blockID for a position in the chunk. Args: x, y, z, blockID
     */
    public boolean a(int par1, int par2, int par3, int par4)
    {
        return this.a(par1, par2, par3, par4, 0);
    }

    /**
     * Sets a blockID of a position within a chunk with metadata. Args: x, y, z, blockID, metadata
     */
    public boolean a(int par1, int par2, int par3, int par4, int par5)
    {
        int var6 = par3 << 4 | par1;

        if (par2 >= this.b[var6] - 1)
        {
            this.b[var6] = -999;
        }

        int var7 = this.heightMap[var6];
        int var8 = this.getTypeId(par1, par2, par3);
        int var9 = this.getData(par1, par2, par3);

        if (var8 == par4 && var9 == par5)
        {
            return false;
        }
        else
        {
            ChunkSection var10 = this.sections[par2 >> 4];
            boolean var11 = false;

            if (var10 == null)
            {
                if (par4 == 0)
                {
                    return false;
                }

                var10 = this.sections[par2 >> 4] = new ChunkSection(par2 >> 4 << 4, !this.world.worldProvider.f);
                var11 = par2 >= var7;
            }

            int var12 = this.x * 16 + par1;
            int var13 = this.z * 16 + par3;

            if (var8 != 0 && !this.world.isStatic)
            {
                Block.byId[var8].h(this.world, var12, par2, var13, var9);
            }

            var10.a(par1, par2 & 15, par3, par4);

            if (var8 != 0)
            {
                if (!this.world.isStatic)
                {
                    Block.byId[var8].remove(this.world, var12, par2, var13, var8, var9);
                }
                else if (Block.byId[var8] instanceof BlockContainer && var8 != par4)
                {
                    this.world.r(var12, par2, var13);
                }
            }

            if (var10.a(par1, par2 & 15, par3) != par4)
            {
                return false;
            }
            else
            {
                var10.b(par1, par2 & 15, par3, par5);

                if (var11)
                {
                    this.initLighting();
                }
                else
                {
                    if (Block.lightBlock[par4 & 4095] > 0)
                    {
                        if (par2 >= var7)
                        {
                            this.h(par1, par2 + 1, par3);
                        }
                    }
                    else if (par2 == var7 - 1)
                    {
                        this.h(par1, par2, par3);
                    }

                    this.e(par1, par3);
                }

                TileEntity var14;

                if (par4 != 0)
                {
                    if (!this.world.isStatic)
                    {
                        Block.byId[par4].onPlace(this.world, var12, par2, var13);
                    }

                    if (Block.byId[par4] instanceof BlockContainer)
                    {
                        var14 = this.e(par1, par2, par3);

                        if (var14 == null)
                        {
                            var14 = ((BlockContainer) Block.byId[par4]).a(this.world);
                            this.world.setTileEntity(var12, par2, var13, var14);
                        }

                        if (var14 != null)
                        {
                            var14.h();
                        }
                    }
                }
                else if (var8 > 0 && Block.byId[var8] instanceof BlockContainer)
                {
                    var14 = this.e(par1, par2, par3);

                    if (var14 != null)
                    {
                        var14.h();
                    }
                }

                this.l = true;
                return true;
            }
        }
    }

    /**
     * Set the metadata of a block in the chunk
     */
    public boolean b(int par1, int par2, int par3, int par4)
    {
        ChunkSection var5 = this.sections[par2 >> 4];

        if (var5 == null)
        {
            return false;
        }
        else
        {
            int var6 = var5.b(par1, par2 & 15, par3);

            if (var6 == par4)
            {
                return false;
            }
            else
            {
                this.l = true;
                var5.b(par1, par2 & 15, par3, par4);
                int var7 = var5.a(par1, par2 & 15, par3);

                if (var7 > 0 && Block.byId[var7] instanceof BlockContainer)
                {
                    TileEntity var8 = this.e(par1, par2, par3);

                    if (var8 != null)
                    {
                        var8.h();
                        var8.p = par4;
                    }
                }

                return true;
            }
        }
    }

    /**
     * Gets the amount of light saved in this block (doesn't adjust for daylight)
     */
    public int getBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
    {
        ChunkSection var5 = this.sections[par3 >> 4];
        return var5 == null ? (this.d(par2, par3, par4) ? par1EnumSkyBlock.c : 0) : (par1EnumSkyBlock == EnumSkyBlock.SKY ? (this.world.worldProvider.f ? 0 : var5.c(par2, par3 & 15, par4)) : (par1EnumSkyBlock == EnumSkyBlock.BLOCK ? var5.d(par2, par3 & 15, par4) : par1EnumSkyBlock.c));
    }

    /**
     * Sets the light value at the coordinate. If enumskyblock is set to sky it sets it in the skylightmap and if its a
     * block then into the blocklightmap. Args enumSkyBlock, x, y, z, lightValue
     */
    public void a(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5)
    {
        ChunkSection var6 = this.sections[par3 >> 4];

        if (var6 == null)
        {
            var6 = this.sections[par3 >> 4] = new ChunkSection(par3 >> 4 << 4, !this.world.worldProvider.f);
            this.initLighting();
        }

        this.l = true;

        if (par1EnumSkyBlock == EnumSkyBlock.SKY)
        {
            if (!this.world.worldProvider.f)
            {
                var6.c(par2, par3 & 15, par4, par5);
            }
        }
        else if (par1EnumSkyBlock == EnumSkyBlock.BLOCK)
        {
            var6.d(par2, par3 & 15, par4, par5);
        }
    }

    /**
     * Gets the amount of light on a block taking into account sunlight
     */
    public int c(int par1, int par2, int par3, int par4)
    {
        ChunkSection var5 = this.sections[par2 >> 4];

        if (var5 == null)
        {
            return !this.world.worldProvider.f && par4 < EnumSkyBlock.SKY.c ? EnumSkyBlock.SKY.c - par4 : 0;
        }
        else
        {
            int var6 = this.world.worldProvider.f ? 0 : var5.c(par1, par2 & 15, par3);

            if (var6 > 0)
            {
                a = true;
            }

            var6 -= par4;
            int var7 = var5.d(par1, par2 & 15, par3);

            if (var7 > var6)
            {
                var6 = var7;
            }

            return var6;
        }
    }

    /**
     * Adds an entity to the chunk. Args: entity
     */
    public void a(Entity par1Entity)
    {
        this.m = true;
        int var2 = MathHelper.floor(par1Entity.locX / 16.0D);
        int var3 = MathHelper.floor(par1Entity.locZ / 16.0D);

        if (var2 != this.x || var3 != this.z)
        {
            System.out.println("Wrong location! " + par1Entity);
            Thread.dumpStack();
        }

        int var4 = MathHelper.floor(par1Entity.locY / 16.0D);

        if (var4 < 0)
        {
            var4 = 0;
        }

        if (var4 >= this.entitySlices.length)
        {
            var4 = this.entitySlices.length - 1;
        }

        par1Entity.ah = true;
        par1Entity.ai = this.x;
        par1Entity.aj = var4;
        par1Entity.ak = this.z;
        this.entitySlices[var4].add(par1Entity);
    }

    /**
     * removes entity using its y chunk coordinate as its index
     */
    public void b(Entity par1Entity)
    {
        this.a(par1Entity, par1Entity.aj);
    }

    /**
     * Removes entity at the specified index from the entity array.
     */
    public void a(Entity par1Entity, int par2)
    {
        if (par2 < 0)
        {
            par2 = 0;
        }

        if (par2 >= this.entitySlices.length)
        {
            par2 = this.entitySlices.length - 1;
        }

        this.entitySlices[par2].remove(par1Entity);
    }

    /**
     * Returns whether is not a block above this one blocking sight to the sky (done via checking against the heightmap)
     */
    public boolean d(int par1, int par2, int par3)
    {
        return par2 >= this.heightMap[par3 << 4 | par1];
    }

    /**
     * Gets the TileEntity for a given block in this chunk
     */
    public TileEntity e(int par1, int par2, int par3)
    {
        ChunkPosition var4 = new ChunkPosition(par1, par2, par3);
        TileEntity var5 = (TileEntity)this.tileEntities.get(var4);

        if (var5 == null)
        {
            int var6 = this.getTypeId(par1, par2, par3);

            if (var6 <= 0 || !Block.byId[var6].u())
            {
                return null;
            }

            if (var5 == null)
            {
                var5 = ((BlockContainer) Block.byId[var6]).a(this.world);
                this.world.setTileEntity(this.x * 16 + par1, par2, this.z * 16 + par3, var5);
            }

            var5 = (TileEntity)this.tileEntities.get(var4);
        }

        if (var5 != null && var5.r())
        {
            this.tileEntities.remove(var4);
            return null;
        }
        else
        {
            return var5;
        }
    }

    /**
     * Adds a TileEntity to a chunk
     */
    public void a(TileEntity par1TileEntity)
    {
        int var2 = par1TileEntity.x - this.x * 16;
        int var3 = par1TileEntity.y;
        int var4 = par1TileEntity.z - this.z * 16;
        this.a(var2, var3, var4, par1TileEntity);

        if (this.d)
        {
            this.world.tileEntityList.add(par1TileEntity);
        }
    }

    /**
     * Sets the TileEntity for a given block in this chunk
     */
    public void a(int par1, int par2, int par3, TileEntity par4TileEntity)
    {
        ChunkPosition var5 = new ChunkPosition(par1, par2, par3);
        par4TileEntity.b(this.world);
        par4TileEntity.x = this.x * 16 + par1;
        par4TileEntity.y = par2;
        par4TileEntity.z = this.z * 16 + par3;

        if (this.getTypeId(par1, par2, par3) != 0 && Block.byId[this.getTypeId(par1, par2, par3)] instanceof BlockContainer)
        {
            par4TileEntity.s();
            this.tileEntities.put(var5, par4TileEntity);
        }
    }

    /**
     * Removes the TileEntity for a given block in this chunk
     */
    public void f(int par1, int par2, int par3)
    {
        ChunkPosition var4 = new ChunkPosition(par1, par2, par3);

        if (this.d)
        {
            TileEntity var5 = (TileEntity)this.tileEntities.remove(var4);

            if (var5 != null)
            {
                var5.w_();
            }
        }
    }

    /**
     * Called when this Chunk is loaded by the ChunkProvider
     */
    public void addEntities()
    {
        this.d = true;
        this.world.a(this.tileEntities.values());

        for (int var1 = 0; var1 < this.entitySlices.length; ++var1)
        {
            this.world.a(this.entitySlices[var1]);
        }
    }

    /**
     * Called when this Chunk is unloaded by the ChunkProvider
     */
    public void removeEntities()
    {
        this.d = false;
        Iterator var1 = this.tileEntities.values().iterator();

        while (var1.hasNext())
        {
            TileEntity var2 = (TileEntity)var1.next();
            this.world.a(var2);
        }

        for (int var3 = 0; var3 < this.entitySlices.length; ++var3)
        {
            this.world.b(this.entitySlices[var3]);
        }
    }

    /**
     * Sets the isModified flag for this Chunk
     */
    public void e()
    {
        this.l = true;
    }

    /**
     * Fills the given list of all entities that intersect within the given bounding box that aren't the passed entity
     * Args: entity, aabb, listToFill
     */
    public void a(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, List par3List)
    {
        int var4 = MathHelper.floor((par2AxisAlignedBB.b - 2.0D) / 16.0D);
        int var5 = MathHelper.floor((par2AxisAlignedBB.e + 2.0D) / 16.0D);

        if (var4 < 0)
        {
            var4 = 0;
        }

        if (var5 >= this.entitySlices.length)
        {
            var5 = this.entitySlices.length - 1;
        }

        for (int var6 = var4; var6 <= var5; ++var6)
        {
            List var7 = this.entitySlices[var6];

            for (int var8 = 0; var8 < var7.size(); ++var8)
            {
                Entity var9 = (Entity)var7.get(var8);

                if (var9 != par1Entity && var9.boundingBox.a(par2AxisAlignedBB))
                {
                    par3List.add(var9);
                    Entity[] var10 = var9.ao();

                    if (var10 != null)
                    {
                        for (int var11 = 0; var11 < var10.length; ++var11)
                        {
                            var9 = var10[var11];

                            if (var9 != par1Entity && var9.boundingBox.a(par2AxisAlignedBB))
                            {
                                par3List.add(var9);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets all entities that can be assigned to the specified class. Args: entityClass, aabb, listToFill
     */
    public void a(Class par1Class, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector)
    {
        int var5 = MathHelper.floor((par2AxisAlignedBB.b - 2.0D) / 16.0D);
        int var6 = MathHelper.floor((par2AxisAlignedBB.e + 2.0D) / 16.0D);

        if (var5 < 0)
        {
            var5 = 0;
        }
        else if (var5 >= this.entitySlices.length)
        {
            var5 = this.entitySlices.length - 1;
        }

        if (var6 >= this.entitySlices.length)
        {
            var6 = this.entitySlices.length - 1;
        }
        else if (var6 < 0)
        {
            var6 = 0;
        }

        for (int var7 = var5; var7 <= var6; ++var7)
        {
            List var8 = this.entitySlices[var7];

            for (int var9 = 0; var9 < var8.size(); ++var9)
            {
                Entity var10 = (Entity)var8.get(var9);

                if (par1Class.isAssignableFrom(var10.getClass()) && var10.boundingBox.a(par2AxisAlignedBB) && (par4IEntitySelector == null || par4IEntitySelector.a(var10)))
                {
                    par3List.add(var10);
                }
            }
        }
    }

    /**
     * Returns true if this Chunk needs to be saved
     */
    public boolean a(boolean par1)
    {
        if (par1)
        {
            if (this.m && this.world.getTime() != this.n)
            {
                return true;
            }
        }
        else if (this.m && this.world.getTime() >= this.n + 600L)
        {
            return true;
        }

        return this.l;
    }

    public Random a(long par1)
    {
        return new Random(this.world.getSeed() + (long)(this.x * this.x * 4987142) + (long)(this.x * 5947611) + (long)(this.z * this.z) * 4392871L + (long)(this.z * 389711) ^ par1);
    }

    public boolean isEmpty()
    {
        return false;
    }

    public void a(IChunkProvider par1IChunkProvider, IChunkProvider par2IChunkProvider, int par3, int par4)
    {
        if (!this.done && par1IChunkProvider.isChunkLoaded(par3 + 1, par4 + 1) && par1IChunkProvider.isChunkLoaded(par3, par4 + 1) && par1IChunkProvider.isChunkLoaded(par3 + 1, par4))
        {
            par1IChunkProvider.getChunkAt(par2IChunkProvider, par3, par4);
        }

        if (par1IChunkProvider.isChunkLoaded(par3 - 1, par4) && !par1IChunkProvider.getOrCreateChunk(par3 - 1, par4).done && par1IChunkProvider.isChunkLoaded(par3 - 1, par4 + 1) && par1IChunkProvider.isChunkLoaded(par3, par4 + 1) && par1IChunkProvider.isChunkLoaded(par3 - 1, par4 + 1))
        {
            par1IChunkProvider.getChunkAt(par2IChunkProvider, par3 - 1, par4);
        }

        if (par1IChunkProvider.isChunkLoaded(par3, par4 - 1) && !par1IChunkProvider.getOrCreateChunk(par3, par4 - 1).done && par1IChunkProvider.isChunkLoaded(par3 + 1, par4 - 1) && par1IChunkProvider.isChunkLoaded(par3 + 1, par4 - 1) && par1IChunkProvider.isChunkLoaded(par3 + 1, par4))
        {
            par1IChunkProvider.getChunkAt(par2IChunkProvider, par3, par4 - 1);
        }

        if (par1IChunkProvider.isChunkLoaded(par3 - 1, par4 - 1) && !par1IChunkProvider.getOrCreateChunk(par3 - 1, par4 - 1).done && par1IChunkProvider.isChunkLoaded(par3, par4 - 1) && par1IChunkProvider.isChunkLoaded(par3 - 1, par4))
        {
            par1IChunkProvider.getChunkAt(par2IChunkProvider, par3 - 1, par4 - 1);
        }
    }

    /**
     * Gets the height to which rain/snow will fall. Calculates it if not already stored.
     */
    public int d(int par1, int par2)
    {
        int var3 = par1 | par2 << 4;
        int var4 = this.b[var3];

        if (var4 == -999)
        {
            int var5 = this.h() + 15;
            var4 = -1;

            while (var5 > 0 && var4 == -1)
            {
                int var6 = this.getTypeId(par1, var5, par2);
                Material var7 = var6 == 0 ? Material.AIR : Block.byId[var6].material;

                if (!var7.isSolid() && !var7.isLiquid())
                {
                    --var5;
                }
                else
                {
                    var4 = var5 + 1;
                }
            }

            this.b[var3] = var4;
        }

        return var4;
    }

    /**
     * Checks whether skylight needs updated; if it does, calls updateSkylight_do
     */
    public void k()
    {
        if (this.t && !this.world.worldProvider.f)
        {
            this.q();
        }
    }

    /**
     * Gets a ChunkCoordIntPair representing the Chunk's position.
     */
    public ChunkCoordIntPair l()
    {
        return new ChunkCoordIntPair(this.x, this.z);
    }

    /**
     * Returns whether the ExtendedBlockStorages containing levels (in blocks) from arg 1 to arg 2 are fully empty
     * (true) or not (false).
     */
    public boolean c(int par1, int par2)
    {
        if (par1 < 0)
        {
            par1 = 0;
        }

        if (par2 >= 256)
        {
            par2 = 255;
        }

        for (int var3 = par1; var3 <= par2; var3 += 16)
        {
            ChunkSection var4 = this.sections[var3 >> 4];

            if (var4 != null && !var4.a())
            {
                return false;
            }
        }

        return true;
    }

    public void a(ChunkSection[] par1ArrayOfExtendedBlockStorage)
    {
        this.sections = par1ArrayOfExtendedBlockStorage;
    }

    /**
     * This method retrieves the biome at a set of coordinates
     */
    public BiomeBase a(int par1, int par2, WorldChunkManager par3WorldChunkManager)
    {
        int var4 = this.s[par2 << 4 | par1] & 255;

        if (var4 == 255)
        {
            BiomeBase var5 = par3WorldChunkManager.getBiome((this.x << 4) + par1, (this.z << 4) + par2);
            var4 = var5.id;
            this.s[par2 << 4 | par1] = (byte)(var4 & 255);
        }

        return BiomeBase.biomes[var4] == null ? BiomeBase.PLAINS : BiomeBase.biomes[var4];
    }

    /**
     * Returns an array containing a 16x16 mapping on the X/Z of block positions in this Chunk to biome IDs.
     */
    public byte[] m()
    {
        return this.s;
    }

    /**
     * Accepts a 256-entry array that contains a 16x16 mapping on the X/Z plane of block positions in this Chunk to
     * biome IDs.
     */
    public void a(byte[] par1ArrayOfByte)
    {
        this.s = par1ArrayOfByte;
    }

    /**
     * Resets the relight check index to 0 for this Chunk.
     */
    public void n()
    {
        this.u = 0;
    }

    /**
     * Called once-per-chunk-per-tick, and advances the round-robin relight check index by up to 8 blocks at a time. In
     * a worst-case scenario, can potentially take up to 25.6 seconds, calculated via (4096/8)/20, to re-check all
     * blocks in a chunk, which may explain lagging light updates on initial world generation.
     */
    public void o()
    {
        for (int var1 = 0; var1 < 8; ++var1)
        {
            if (this.u >= 4096)
            {
                return;
            }

            int var2 = this.u % 16;
            int var3 = this.u / 16 % 16;
            int var4 = this.u / 256;
            ++this.u;
            int var5 = (this.x << 4) + var3;
            int var6 = (this.z << 4) + var4;

            for (int var7 = 0; var7 < 16; ++var7)
            {
                int var8 = (var2 << 4) + var7;

                if (this.sections[var2] == null && (var7 == 0 || var7 == 15 || var3 == 0 || var3 == 15 || var4 == 0 || var4 == 15) || this.sections[var2] != null && this.sections[var2].a(var3, var7, var4) == 0)
                {
                    if (Block.lightEmission[this.world.getTypeId(var5, var8 - 1, var6)] > 0)
                    {
                        this.world.z(var5, var8 - 1, var6);
                    }

                    if (Block.lightEmission[this.world.getTypeId(var5, var8 + 1, var6)] > 0)
                    {
                        this.world.z(var5, var8 + 1, var6);
                    }

                    if (Block.lightEmission[this.world.getTypeId(var5 - 1, var8, var6)] > 0)
                    {
                        this.world.z(var5 - 1, var8, var6);
                    }

                    if (Block.lightEmission[this.world.getTypeId(var5 + 1, var8, var6)] > 0)
                    {
                        this.world.z(var5 + 1, var8, var6);
                    }

                    if (Block.lightEmission[this.world.getTypeId(var5, var8, var6 - 1)] > 0)
                    {
                        this.world.z(var5, var8, var6 - 1);
                    }

                    if (Block.lightEmission[this.world.getTypeId(var5, var8, var6 + 1)] > 0)
                    {
                        this.world.z(var5, var8, var6 + 1);
                    }

                    this.world.z(var5, var8, var6);
                }
            }
        }
    }
}
