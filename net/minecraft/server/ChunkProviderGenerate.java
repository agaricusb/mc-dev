package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class ChunkProviderGenerate implements IChunkProvider
{
    /** RNG. */
    private Random k;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves l;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves m;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves n;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves o;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves a;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves b;
    public NoiseGeneratorOctaves c;

    /** Reference to the World object. */
    private World p;

    /** are map structures going to be generated (e.g. strongholds) */
    private final boolean q;

    /** Holds the overall noise array used in chunk generation */
    private double[] r;
    private double[] s = new double[256];
    private WorldGenBase t = new WorldGenCaves();

    /** Holds Stronghold Generator */
    private WorldGenStronghold u = new WorldGenStronghold();

    /** Holds Village Generator */
    private WorldGenVillage v = new WorldGenVillage();

    /** Holds Mineshaft Generator */
    private WorldGenMineshaft w = new WorldGenMineshaft();
    private WorldGenLargeFeature x = new WorldGenLargeFeature();

    /** Holds ravine generator */
    private WorldGenBase y = new WorldGenCanyon();

    /** The biomes that are used to generate the chunk */
    private BiomeBase[] z;

    /** A double array that hold terrain noise from noiseGen3 */
    double[] d;

    /** A double array that hold terrain noise */
    double[] e;

    /** A double array that hold terrain noise from noiseGen2 */
    double[] f;

    /** A double array that hold terrain noise from noiseGen5 */
    double[] g;

    /** A double array that holds terrain noise from noiseGen6 */
    double[] h;

    /**
     * Used to store the 5x5 parabolic field that is used during terrain generation.
     */
    float[] i;
    int[][] j = new int[32][32];

    public ChunkProviderGenerate(World par1World, long par2, boolean par4)
    {
        this.p = par1World;
        this.q = par4;
        this.k = new Random(par2);
        this.l = new NoiseGeneratorOctaves(this.k, 16);
        this.m = new NoiseGeneratorOctaves(this.k, 16);
        this.n = new NoiseGeneratorOctaves(this.k, 8);
        this.o = new NoiseGeneratorOctaves(this.k, 4);
        this.a = new NoiseGeneratorOctaves(this.k, 10);
        this.b = new NoiseGeneratorOctaves(this.k, 16);
        this.c = new NoiseGeneratorOctaves(this.k, 8);
    }

    /**
     * Generates the shape of the terrain for the chunk though its all stone though the water is frozen if the
     * temperature is low enough
     */
    public void a(int par1, int par2, byte[] par3ArrayOfByte)
    {
        byte var4 = 4;
        byte var5 = 16;
        byte var6 = 63;
        int var7 = var4 + 1;
        byte var8 = 17;
        int var9 = var4 + 1;
        this.z = this.p.getWorldChunkManager().getBiomes(this.z, par1 * 4 - 2, par2 * 4 - 2, var7 + 5, var9 + 5);
        this.r = this.a(this.r, par1 * var4, 0, par2 * var4, var7, var8, var9);

        for (int var10 = 0; var10 < var4; ++var10)
        {
            for (int var11 = 0; var11 < var4; ++var11)
            {
                for (int var12 = 0; var12 < var5; ++var12)
                {
                    double var13 = 0.125D;
                    double var15 = this.r[((var10 + 0) * var9 + var11 + 0) * var8 + var12 + 0];
                    double var17 = this.r[((var10 + 0) * var9 + var11 + 1) * var8 + var12 + 0];
                    double var19 = this.r[((var10 + 1) * var9 + var11 + 0) * var8 + var12 + 0];
                    double var21 = this.r[((var10 + 1) * var9 + var11 + 1) * var8 + var12 + 0];
                    double var23 = (this.r[((var10 + 0) * var9 + var11 + 0) * var8 + var12 + 1] - var15) * var13;
                    double var25 = (this.r[((var10 + 0) * var9 + var11 + 1) * var8 + var12 + 1] - var17) * var13;
                    double var27 = (this.r[((var10 + 1) * var9 + var11 + 0) * var8 + var12 + 1] - var19) * var13;
                    double var29 = (this.r[((var10 + 1) * var9 + var11 + 1) * var8 + var12 + 1] - var21) * var13;

                    for (int var31 = 0; var31 < 8; ++var31)
                    {
                        double var32 = 0.25D;
                        double var34 = var15;
                        double var36 = var17;
                        double var38 = (var19 - var15) * var32;
                        double var40 = (var21 - var17) * var32;

                        for (int var42 = 0; var42 < 4; ++var42)
                        {
                            int var43 = var42 + var10 * 4 << 11 | 0 + var11 * 4 << 7 | var12 * 8 + var31;
                            short var44 = 128;
                            var43 -= var44;
                            double var45 = 0.25D;
                            double var49 = (var36 - var34) * var45;
                            double var47 = var34 - var49;

                            for (int var51 = 0; var51 < 4; ++var51)
                            {
                                if ((var47 += var49) > 0.0D)
                                {
                                    par3ArrayOfByte[var43 += var44] = (byte) Block.STONE.id;
                                }
                                else if (var12 * 8 + var31 < var6)
                                {
                                    par3ArrayOfByte[var43 += var44] = (byte) Block.STATIONARY_WATER.id;
                                }
                                else
                                {
                                    par3ArrayOfByte[var43 += var44] = 0;
                                }
                            }

                            var34 += var38;
                            var36 += var40;
                        }

                        var15 += var23;
                        var17 += var25;
                        var19 += var27;
                        var21 += var29;
                    }
                }
            }
        }
    }

    /**
     * Replaces the stone that was placed in with blocks that match the biome
     */
    public void a(int par1, int par2, byte[] par3ArrayOfByte, BiomeBase[] par4ArrayOfBiomeGenBase)
    {
        byte var5 = 63;
        double var6 = 0.03125D;
        this.s = this.o.a(this.s, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

        for (int var8 = 0; var8 < 16; ++var8)
        {
            for (int var9 = 0; var9 < 16; ++var9)
            {
                BiomeBase var10 = par4ArrayOfBiomeGenBase[var9 + var8 * 16];
                float var11 = var10.j();
                int var12 = (int)(this.s[var8 + var9 * 16] / 3.0D + 3.0D + this.k.nextDouble() * 0.25D);
                int var13 = -1;
                byte var14 = var10.A;
                byte var15 = var10.B;

                for (int var16 = 127; var16 >= 0; --var16)
                {
                    int var17 = (var9 * 16 + var8) * 128 + var16;

                    if (var16 <= 0 + this.k.nextInt(5))
                    {
                        par3ArrayOfByte[var17] = (byte) Block.BEDROCK.id;
                    }
                    else
                    {
                        byte var18 = par3ArrayOfByte[var17];

                        if (var18 == 0)
                        {
                            var13 = -1;
                        }
                        else if (var18 == Block.STONE.id)
                        {
                            if (var13 == -1)
                            {
                                if (var12 <= 0)
                                {
                                    var14 = 0;
                                    var15 = (byte) Block.STONE.id;
                                }
                                else if (var16 >= var5 - 4 && var16 <= var5 + 1)
                                {
                                    var14 = var10.A;
                                    var15 = var10.B;
                                }

                                if (var16 < var5 && var14 == 0)
                                {
                                    if (var11 < 0.15F)
                                    {
                                        var14 = (byte) Block.ICE.id;
                                    }
                                    else
                                    {
                                        var14 = (byte) Block.STATIONARY_WATER.id;
                                    }
                                }

                                var13 = var12;

                                if (var16 >= var5 - 1)
                                {
                                    par3ArrayOfByte[var17] = var14;
                                }
                                else
                                {
                                    par3ArrayOfByte[var17] = var15;
                                }
                            }
                            else if (var13 > 0)
                            {
                                --var13;
                                par3ArrayOfByte[var17] = var15;

                                if (var13 == 0 && var15 == Block.SAND.id)
                                {
                                    var13 = this.k.nextInt(4);
                                    var15 = (byte) Block.SANDSTONE.id;
                                }
                            }
                        }
                    }
                }
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
        this.k.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        byte[] var3 = new byte[32768];
        this.a(par1, par2, var3);
        this.z = this.p.getWorldChunkManager().getBiomeBlock(this.z, par1 * 16, par2 * 16, 16, 16);
        this.a(par1, par2, var3, this.z);
        this.t.a(this, this.p, par1, par2, var3);
        this.y.a(this, this.p, par1, par2, var3);

        if (this.q)
        {
            this.w.a(this, this.p, par1, par2, var3);
            this.v.a(this, this.p, par1, par2, var3);
            this.u.a(this, this.p, par1, par2, var3);
            this.x.a(this, this.p, par1, par2, var3);
        }

        Chunk var4 = new Chunk(this.p, var3, par1, par2);
        byte[] var5 = var4.m();

        for (int var6 = 0; var6 < var5.length; ++var6)
        {
            var5[var6] = (byte)this.z[var6].id;
        }

        var4.initLighting();
        return var4;
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] a(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        if (this.i == null)
        {
            this.i = new float[25];

            for (int var8 = -2; var8 <= 2; ++var8)
            {
                for (int var9 = -2; var9 <= 2; ++var9)
                {
                    float var10 = 10.0F / MathHelper.c((float) (var8 * var8 + var9 * var9) + 0.2F);
                    this.i[var8 + 2 + (var9 + 2) * 5] = var10;
                }
            }
        }

        double var44 = 684.412D;
        double var45 = 684.412D;
        this.g = this.a.a(this.g, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
        this.h = this.b.a(this.h, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
        this.d = this.n.a(this.d, par2, par3, par4, par5, par6, par7, var44 / 80.0D, var45 / 160.0D, var44 / 80.0D);
        this.e = this.l.a(this.e, par2, par3, par4, par5, par6, par7, var44, var45, var44);
        this.f = this.m.a(this.f, par2, par3, par4, par5, par6, par7, var44, var45, var44);
        boolean var43 = false;
        boolean var42 = false;
        int var12 = 0;
        int var13 = 0;

        for (int var14 = 0; var14 < par5; ++var14)
        {
            for (int var15 = 0; var15 < par7; ++var15)
            {
                float var16 = 0.0F;
                float var17 = 0.0F;
                float var18 = 0.0F;
                byte var19 = 2;
                BiomeBase var20 = this.z[var14 + 2 + (var15 + 2) * (par5 + 5)];

                for (int var21 = -var19; var21 <= var19; ++var21)
                {
                    for (int var22 = -var19; var22 <= var19; ++var22)
                    {
                        BiomeBase var23 = this.z[var14 + var21 + 2 + (var15 + var22 + 2) * (par5 + 5)];
                        float var24 = this.i[var21 + 2 + (var22 + 2) * 5] / (var23.D + 2.0F);

                        if (var23.D > var20.D)
                        {
                            var24 /= 2.0F;
                        }

                        var16 += var23.E * var24;
                        var17 += var23.D * var24;
                        var18 += var24;
                    }
                }

                var16 /= var18;
                var17 /= var18;
                var16 = var16 * 0.9F + 0.1F;
                var17 = (var17 * 4.0F - 1.0F) / 8.0F;
                double var47 = this.h[var13] / 8000.0D;

                if (var47 < 0.0D)
                {
                    var47 = -var47 * 0.3D;
                }

                var47 = var47 * 3.0D - 2.0D;

                if (var47 < 0.0D)
                {
                    var47 /= 2.0D;

                    if (var47 < -1.0D)
                    {
                        var47 = -1.0D;
                    }

                    var47 /= 1.4D;
                    var47 /= 2.0D;
                }
                else
                {
                    if (var47 > 1.0D)
                    {
                        var47 = 1.0D;
                    }

                    var47 /= 8.0D;
                }

                ++var13;

                for (int var46 = 0; var46 < par6; ++var46)
                {
                    double var48 = (double)var17;
                    double var26 = (double)var16;
                    var48 += var47 * 0.2D;
                    var48 = var48 * (double)par6 / 16.0D;
                    double var28 = (double)par6 / 2.0D + var48 * 4.0D;
                    double var30 = 0.0D;
                    double var32 = ((double)var46 - var28) * 12.0D * 128.0D / 128.0D / var26;

                    if (var32 < 0.0D)
                    {
                        var32 *= 4.0D;
                    }

                    double var34 = this.e[var12] / 512.0D;
                    double var36 = this.f[var12] / 512.0D;
                    double var38 = (this.d[var12] / 10.0D + 1.0D) / 2.0D;

                    if (var38 < 0.0D)
                    {
                        var30 = var34;
                    }
                    else if (var38 > 1.0D)
                    {
                        var30 = var36;
                    }
                    else
                    {
                        var30 = var34 + (var36 - var34) * var38;
                    }

                    var30 -= var32;

                    if (var46 > par6 - 4)
                    {
                        double var40 = (double)((float)(var46 - (par6 - 4)) / 3.0F);
                        var30 = var30 * (1.0D - var40) + -10.0D * var40;
                    }

                    par1ArrayOfDouble[var12] = var30;
                    ++var12;
                }
            }
        }

        return par1ArrayOfDouble;
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
        BlockSand.instaFall = true;
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        BiomeBase var6 = this.p.getBiome(var4 + 16, var5 + 16);
        this.k.setSeed(this.p.getSeed());
        long var7 = this.k.nextLong() / 2L * 2L + 1L;
        long var9 = this.k.nextLong() / 2L * 2L + 1L;
        this.k.setSeed((long)par2 * var7 + (long)par3 * var9 ^ this.p.getSeed());
        boolean var11 = false;

        if (this.q)
        {
            this.w.a(this.p, this.k, par2, par3);
            var11 = this.v.a(this.p, this.k, par2, par3);
            this.u.a(this.p, this.k, par2, par3);
            this.x.a(this.p, this.k, par2, par3);
        }

        int var12;
        int var13;
        int var14;

        if (!var11 && this.k.nextInt(4) == 0)
        {
            var12 = var4 + this.k.nextInt(16) + 8;
            var13 = this.k.nextInt(128);
            var14 = var5 + this.k.nextInt(16) + 8;
            (new WorldGenLakes(Block.STATIONARY_WATER.id)).a(this.p, this.k, var12, var13, var14);
        }

        if (!var11 && this.k.nextInt(8) == 0)
        {
            var12 = var4 + this.k.nextInt(16) + 8;
            var13 = this.k.nextInt(this.k.nextInt(120) + 8);
            var14 = var5 + this.k.nextInt(16) + 8;

            if (var13 < 63 || this.k.nextInt(10) == 0)
            {
                (new WorldGenLakes(Block.STATIONARY_LAVA.id)).a(this.p, this.k, var12, var13, var14);
            }
        }

        for (var12 = 0; var12 < 8; ++var12)
        {
            var13 = var4 + this.k.nextInt(16) + 8;
            var14 = this.k.nextInt(128);
            int var15 = var5 + this.k.nextInt(16) + 8;

            if ((new WorldGenDungeons()).a(this.p, this.k, var13, var14, var15))
            {
                ;
            }
        }

        var6.a(this.p, this.k, var4, var5);
        SpawnerCreature.a(this.p, var6, var4 + 8, var5 + 8, 16, 16, this.k);
        var4 += 8;
        var5 += 8;

        for (var12 = 0; var12 < 16; ++var12)
        {
            for (var13 = 0; var13 < 16; ++var13)
            {
                var14 = this.p.h(var4 + var12, var5 + var13);

                if (this.p.w(var12 + var4, var14 - 1, var13 + var5))
                {
                    this.p.setTypeId(var12 + var4, var14 - 1, var13 + var5, Block.ICE.id);
                }

                if (this.p.y(var12 + var4, var14, var13 + var5))
                {
                    this.p.setTypeId(var12 + var4, var14, var13 + var5, Block.SNOW.id);
                }
            }
        }

        BlockSand.instaFall = false;
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
        return "RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getMobsFor(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        BiomeBase var5 = this.p.getBiome(par2, par4);
        return var5 == null ? null : (var5 == BiomeBase.SWAMPLAND && par1EnumCreatureType == EnumCreatureType.MONSTER && this.x.a(par2, par3, par4) ? this.x.a() : var5.getMobs(par1EnumCreatureType));
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition findNearestMapFeature(World par1World, String par2Str, int par3, int par4, int par5)
    {
        return "Stronghold".equals(par2Str) && this.u != null ? this.u.getNearestGeneratedFeature(par1World, par3, par4, par5) : null;
    }

    public int getLoadedChunks()
    {
        return 0;
    }

    public void recreateStructures(int par1, int par2)
    {
        if (this.q)
        {
            this.w.a(this, this.p, par1, par2, (byte[]) null);
            this.v.a(this, this.p, par1, par2, (byte[]) null);
            this.u.a(this, this.p, par1, par2, (byte[]) null);
            this.x.a(this, this.p, par1, par2, (byte[]) null);
        }
    }
}
