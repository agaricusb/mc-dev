package net.minecraft.server;

import java.util.Random;

public class BiomeDecorator
{
    /** The world the BiomeDecorator is currently decorating */
    protected World a;

    /** The Biome Decorator's random number generator. */
    protected Random b;

    /** The X-coordinate of the chunk currently being decorated */
    protected int c;

    /** The Z-coordinate of the chunk currently being decorated */
    protected int d;

    /** The biome generator object. */
    protected BiomeBase e;

    /** The clay generator. */
    protected WorldGenerator f = new WorldGenClay(4);

    /** The sand generator. */
    protected WorldGenerator g;

    /** The gravel generator. */
    protected WorldGenerator h;

    /** The dirt generator. */
    protected WorldGenerator i;
    protected WorldGenerator j;
    protected WorldGenerator k;
    protected WorldGenerator l;

    /** Field that holds gold WorldGenMinable */
    protected WorldGenerator m;

    /** Field that holds redstone WorldGenMinable */
    protected WorldGenerator n;

    /** Field that holds diamond WorldGenMinable */
    protected WorldGenerator o;

    /** Field that holds Lapis WorldGenMinable */
    protected WorldGenerator p;

    /** Field that holds one of the plantYellow WorldGenFlowers */
    protected WorldGenerator q;

    /** Field that holds one of the plantRed WorldGenFlowers */
    protected WorldGenerator r;

    /** Field that holds mushroomBrown WorldGenFlowers */
    protected WorldGenerator s;

    /** Field that holds mushroomRed WorldGenFlowers */
    protected WorldGenerator t;

    /** Field that holds big mushroom generator */
    protected WorldGenerator u;

    /** Field that holds WorldGenReed */
    protected WorldGenerator v;

    /** Field that holds WorldGenCactus */
    protected WorldGenerator w;

    /** The water lily generation! */
    protected WorldGenerator x;

    /** Amount of waterlilys per chunk. */
    protected int y;

    /**
     * The number of trees to attempt to generate per chunk. Up to 10 in forests, none in deserts.
     */
    protected int z;

    /**
     * The number of yellow flower patches to generate per chunk. The game generates much less than this number, since
     * it attempts to generate them at a random altitude.
     */
    protected int A;

    /** The amount of tall grass to generate per chunk. */
    protected int B;

    /**
     * The number of dead bushes to generate per chunk. Used in deserts and swamps.
     */
    protected int C;

    /**
     * The number of extra mushroom patches per chunk. It generates 1/4 this number in brown mushroom patches, and 1/8
     * this number in red mushroom patches. These mushrooms go beyond the default base number of mushrooms.
     */
    protected int D;

    /**
     * The number of reeds to generate per chunk. Reeds won't generate if the randomly selected placement is unsuitable.
     */
    protected int E;

    /**
     * The number of cactus plants to generate per chunk. Cacti only work on sand.
     */
    protected int F;

    /**
     * The number of sand patches to generate per chunk. Sand patches only generate when part of it is underwater.
     */
    protected int G;

    /**
     * The number of sand patches to generate per chunk. Sand patches only generate when part of it is underwater. There
     * appear to be two separate fields for this.
     */
    protected int H;

    /**
     * The number of clay patches to generate per chunk. Only generates when part of it is underwater.
     */
    protected int I;

    /** Amount of big mushrooms per chunk */
    protected int J;

    /** True if decorator should generate surface lava & water */
    public boolean K;

    public BiomeDecorator(BiomeBase par1BiomeGenBase)
    {
        this.g = new WorldGenSand(7, Block.SAND.id);
        this.h = new WorldGenSand(6, Block.GRAVEL.id);
        this.i = new WorldGenMinable(Block.DIRT.id, 32);
        this.j = new WorldGenMinable(Block.GRAVEL.id, 32);
        this.k = new WorldGenMinable(Block.COAL_ORE.id, 16);
        this.l = new WorldGenMinable(Block.IRON_ORE.id, 8);
        this.m = new WorldGenMinable(Block.GOLD_ORE.id, 8);
        this.n = new WorldGenMinable(Block.REDSTONE_ORE.id, 7);
        this.o = new WorldGenMinable(Block.DIAMOND_ORE.id, 7);
        this.p = new WorldGenMinable(Block.LAPIS_ORE.id, 6);
        this.q = new WorldGenFlowers(Block.YELLOW_FLOWER.id);
        this.r = new WorldGenFlowers(Block.RED_ROSE.id);
        this.s = new WorldGenFlowers(Block.BROWN_MUSHROOM.id);
        this.t = new WorldGenFlowers(Block.RED_MUSHROOM.id);
        this.u = new WorldGenHugeMushroom();
        this.v = new WorldGenReed();
        this.w = new WorldGenCactus();
        this.x = new WorldGenWaterLily();
        this.y = 0;
        this.z = 0;
        this.A = 2;
        this.B = 1;
        this.C = 0;
        this.D = 0;
        this.E = 0;
        this.F = 0;
        this.G = 1;
        this.H = 3;
        this.I = 1;
        this.J = 0;
        this.K = true;
        this.e = par1BiomeGenBase;
    }

    /**
     * Decorates the world. Calls code that was formerly (pre-1.8) in ChunkProviderGenerate.populate
     */
    public void a(World par1World, Random par2Random, int par3, int par4)
    {
        if (this.a != null)
        {
            throw new RuntimeException("Already decorating!!");
        }
        else
        {
            this.a = par1World;
            this.b = par2Random;
            this.c = par3;
            this.d = par4;
            this.a();
            this.a = null;
            this.b = null;
        }
    }

    /**
     * The method that does the work of actually decorating chunks
     */
    protected void a()
    {
        this.b();
        int var1;
        int var2;
        int var3;

        for (var1 = 0; var1 < this.H; ++var1)
        {
            var2 = this.c + this.b.nextInt(16) + 8;
            var3 = this.d + this.b.nextInt(16) + 8;
            this.g.a(this.a, this.b, var2, this.a.i(var2, var3), var3);
        }

        for (var1 = 0; var1 < this.I; ++var1)
        {
            var2 = this.c + this.b.nextInt(16) + 8;
            var3 = this.d + this.b.nextInt(16) + 8;
            this.f.a(this.a, this.b, var2, this.a.i(var2, var3), var3);
        }

        for (var1 = 0; var1 < this.G; ++var1)
        {
            var2 = this.c + this.b.nextInt(16) + 8;
            var3 = this.d + this.b.nextInt(16) + 8;
            this.g.a(this.a, this.b, var2, this.a.i(var2, var3), var3);
        }

        var1 = this.z;

        if (this.b.nextInt(10) == 0)
        {
            ++var1;
        }

        int var4;

        for (var2 = 0; var2 < var1; ++var2)
        {
            var3 = this.c + this.b.nextInt(16) + 8;
            var4 = this.d + this.b.nextInt(16) + 8;
            WorldGenerator var5 = this.e.a(this.b);
            var5.a(1.0D, 1.0D, 1.0D);
            var5.a(this.a, this.b, var3, this.a.getHighestBlockYAt(var3, var4), var4);
        }

        for (var2 = 0; var2 < this.J; ++var2)
        {
            var3 = this.c + this.b.nextInt(16) + 8;
            var4 = this.d + this.b.nextInt(16) + 8;
            this.u.a(this.a, this.b, var3, this.a.getHighestBlockYAt(var3, var4), var4);
        }

        int var7;

        for (var2 = 0; var2 < this.A; ++var2)
        {
            var3 = this.c + this.b.nextInt(16) + 8;
            var4 = this.b.nextInt(128);
            var7 = this.d + this.b.nextInt(16) + 8;
            this.q.a(this.a, this.b, var3, var4, var7);

            if (this.b.nextInt(4) == 0)
            {
                var3 = this.c + this.b.nextInt(16) + 8;
                var4 = this.b.nextInt(128);
                var7 = this.d + this.b.nextInt(16) + 8;
                this.r.a(this.a, this.b, var3, var4, var7);
            }
        }

        for (var2 = 0; var2 < this.B; ++var2)
        {
            var3 = this.c + this.b.nextInt(16) + 8;
            var4 = this.b.nextInt(128);
            var7 = this.d + this.b.nextInt(16) + 8;
            WorldGenerator var6 = this.e.b(this.b);
            var6.a(this.a, this.b, var3, var4, var7);
        }

        for (var2 = 0; var2 < this.C; ++var2)
        {
            var3 = this.c + this.b.nextInt(16) + 8;
            var4 = this.b.nextInt(128);
            var7 = this.d + this.b.nextInt(16) + 8;
            (new WorldGenDeadBush(Block.DEAD_BUSH.id)).a(this.a, this.b, var3, var4, var7);
        }

        for (var2 = 0; var2 < this.y; ++var2)
        {
            var3 = this.c + this.b.nextInt(16) + 8;
            var4 = this.d + this.b.nextInt(16) + 8;

            for (var7 = this.b.nextInt(128); var7 > 0 && this.a.getTypeId(var3, var7 - 1, var4) == 0; --var7)
            {
                ;
            }

            this.x.a(this.a, this.b, var3, var7, var4);
        }

        for (var2 = 0; var2 < this.D; ++var2)
        {
            if (this.b.nextInt(4) == 0)
            {
                var3 = this.c + this.b.nextInt(16) + 8;
                var4 = this.d + this.b.nextInt(16) + 8;
                var7 = this.a.getHighestBlockYAt(var3, var4);
                this.s.a(this.a, this.b, var3, var7, var4);
            }

            if (this.b.nextInt(8) == 0)
            {
                var3 = this.c + this.b.nextInt(16) + 8;
                var4 = this.d + this.b.nextInt(16) + 8;
                var7 = this.b.nextInt(128);
                this.t.a(this.a, this.b, var3, var7, var4);
            }
        }

        if (this.b.nextInt(4) == 0)
        {
            var2 = this.c + this.b.nextInt(16) + 8;
            var3 = this.b.nextInt(128);
            var4 = this.d + this.b.nextInt(16) + 8;
            this.s.a(this.a, this.b, var2, var3, var4);
        }

        if (this.b.nextInt(8) == 0)
        {
            var2 = this.c + this.b.nextInt(16) + 8;
            var3 = this.b.nextInt(128);
            var4 = this.d + this.b.nextInt(16) + 8;
            this.t.a(this.a, this.b, var2, var3, var4);
        }

        for (var2 = 0; var2 < this.E; ++var2)
        {
            var3 = this.c + this.b.nextInt(16) + 8;
            var4 = this.d + this.b.nextInt(16) + 8;
            var7 = this.b.nextInt(128);
            this.v.a(this.a, this.b, var3, var7, var4);
        }

        for (var2 = 0; var2 < 10; ++var2)
        {
            var3 = this.c + this.b.nextInt(16) + 8;
            var4 = this.b.nextInt(128);
            var7 = this.d + this.b.nextInt(16) + 8;
            this.v.a(this.a, this.b, var3, var4, var7);
        }

        if (this.b.nextInt(32) == 0)
        {
            var2 = this.c + this.b.nextInt(16) + 8;
            var3 = this.b.nextInt(128);
            var4 = this.d + this.b.nextInt(16) + 8;
            (new WorldGenPumpkin()).a(this.a, this.b, var2, var3, var4);
        }

        for (var2 = 0; var2 < this.F; ++var2)
        {
            var3 = this.c + this.b.nextInt(16) + 8;
            var4 = this.b.nextInt(128);
            var7 = this.d + this.b.nextInt(16) + 8;
            this.w.a(this.a, this.b, var3, var4, var7);
        }

        if (this.K)
        {
            for (var2 = 0; var2 < 50; ++var2)
            {
                var3 = this.c + this.b.nextInt(16) + 8;
                var4 = this.b.nextInt(this.b.nextInt(120) + 8);
                var7 = this.d + this.b.nextInt(16) + 8;
                (new WorldGenLiquids(Block.WATER.id)).a(this.a, this.b, var3, var4, var7);
            }

            for (var2 = 0; var2 < 20; ++var2)
            {
                var3 = this.c + this.b.nextInt(16) + 8;
                var4 = this.b.nextInt(this.b.nextInt(this.b.nextInt(112) + 8) + 8);
                var7 = this.d + this.b.nextInt(16) + 8;
                (new WorldGenLiquids(Block.LAVA.id)).a(this.a, this.b, var3, var4, var7);
            }
        }
    }

    /**
     * Standard ore generation helper. Generates most ores.
     */
    protected void a(int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
    {
        for (int var5 = 0; var5 < par1; ++var5)
        {
            int var6 = this.c + this.b.nextInt(16);
            int var7 = this.b.nextInt(par4 - par3) + par3;
            int var8 = this.d + this.b.nextInt(16);
            par2WorldGenerator.a(this.a, this.b, var6, var7, var8);
        }
    }

    /**
     * Standard ore generation helper. Generates Lapis Lazuli.
     */
    protected void b(int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
    {
        for (int var5 = 0; var5 < par1; ++var5)
        {
            int var6 = this.c + this.b.nextInt(16);
            int var7 = this.b.nextInt(par4) + this.b.nextInt(par4) + (par3 - par4);
            int var8 = this.d + this.b.nextInt(16);
            par2WorldGenerator.a(this.a, this.b, var6, var7, var8);
        }
    }

    /**
     * Generates ores in the current chunk
     */
    protected void b()
    {
        this.a(20, this.i, 0, 128);
        this.a(10, this.j, 0, 128);
        this.a(20, this.k, 0, 128);
        this.a(20, this.l, 0, 64);
        this.a(2, this.m, 0, 32);
        this.a(8, this.n, 0, 16);
        this.a(1, this.o, 0, 16);
        this.b(1, this.p, 16, 16);
    }
}
