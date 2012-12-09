package net.minecraft.server;

import java.util.Random;

public class WorldGenBigTree extends WorldGenerator
{
    /**
     * Contains three sets of two values that provide complimentary indices for a given 'major' index - 1 and 2 for 0, 0
     * and 2 for 1, and 0 and 1 for 2.
     */
    static final byte[] a = new byte[] {(byte)2, (byte)0, (byte)0, (byte)1, (byte)2, (byte)1};

    /** random seed for GenBigTree */
    Random b = new Random();

    /** Reference to the World object. */
    World world;
    int[] d = new int[] {0, 0, 0};
    int e = 0;
    int f;
    double g = 0.618D;
    double h = 1.0D;
    double i = 0.381D;
    double j = 1.0D;
    double k = 1.0D;

    /**
     * Currently always 1, can be set to 2 in the class constructor to generate a double-sized tree trunk for big trees.
     */
    int l = 1;

    /**
     * Sets the limit of the random value used to initialize the height limit.
     */
    int m = 12;

    /**
     * Sets the distance limit for how far away the generator will populate leaves from the base leaf node.
     */
    int n = 4;

    /** Contains a list of a points at which to generate groups of leaves. */
    int[][] o;

    public WorldGenBigTree(boolean par1)
    {
        super(par1);
    }

    /**
     * Generates a list of leaf nodes for the tree, to be populated by generateLeaves.
     */
    void a()
    {
        this.f = (int)((double)this.e * this.g);

        if (this.f >= this.e)
        {
            this.f = this.e - 1;
        }

        int var1 = (int)(1.382D + Math.pow(this.k * (double)this.e / 13.0D, 2.0D));

        if (var1 < 1)
        {
            var1 = 1;
        }

        int[][] var2 = new int[var1 * this.e][4];
        int var3 = this.d[1] + this.e - this.n;
        int var4 = 1;
        int var5 = this.d[1] + this.f;
        int var6 = var3 - this.d[1];
        var2[0][0] = this.d[0];
        var2[0][1] = var3;
        var2[0][2] = this.d[2];
        var2[0][3] = var5;
        --var3;

        while (var6 >= 0)
        {
            int var7 = 0;
            float var8 = this.a(var6);

            if (var8 < 0.0F)
            {
                --var3;
                --var6;
            }
            else
            {
                for (double var9 = 0.5D; var7 < var1; ++var7)
                {
                    double var11 = this.j * (double)var8 * ((double)this.b.nextFloat() + 0.328D);
                    double var13 = (double)this.b.nextFloat() * 2.0D * Math.PI;
                    int var15 = MathHelper.floor(var11 * Math.sin(var13) + (double) this.d[0] + var9);
                    int var16 = MathHelper.floor(var11 * Math.cos(var13) + (double) this.d[2] + var9);
                    int[] var17 = new int[] {var15, var3, var16};
                    int[] var18 = new int[] {var15, var3 + this.n, var16};

                    if (this.a(var17, var18) == -1)
                    {
                        int[] var19 = new int[] {this.d[0], this.d[1], this.d[2]};
                        double var20 = Math.sqrt(Math.pow((double)Math.abs(this.d[0] - var17[0]), 2.0D) + Math.pow((double)Math.abs(this.d[2] - var17[2]), 2.0D));
                        double var22 = var20 * this.i;

                        if ((double)var17[1] - var22 > (double)var5)
                        {
                            var19[1] = var5;
                        }
                        else
                        {
                            var19[1] = (int)((double)var17[1] - var22);
                        }

                        if (this.a(var19, var17) == -1)
                        {
                            var2[var4][0] = var15;
                            var2[var4][1] = var3;
                            var2[var4][2] = var16;
                            var2[var4][3] = var19[1];
                            ++var4;
                        }
                    }
                }

                --var3;
                --var6;
            }
        }

        this.o = new int[var4][4];
        System.arraycopy(var2, 0, this.o, 0, var4);
    }

    void a(int par1, int par2, int par3, float par4, byte par5, int par6)
    {
        int var7 = (int)((double)par4 + 0.618D);
        byte var8 = a[par5];
        byte var9 = a[par5 + 3];
        int[] var10 = new int[] {par1, par2, par3};
        int[] var11 = new int[] {0, 0, 0};
        int var12 = -var7;
        int var13 = -var7;

        for (var11[par5] = var10[par5]; var12 <= var7; ++var12)
        {
            var11[var8] = var10[var8] + var12;
            var13 = -var7;

            while (var13 <= var7)
            {
                double var15 = Math.pow((double)Math.abs(var12) + 0.5D, 2.0D) + Math.pow((double)Math.abs(var13) + 0.5D, 2.0D);

                if (var15 > (double)(par4 * par4))
                {
                    ++var13;
                }
                else
                {
                    var11[var9] = var10[var9] + var13;
                    int var14 = this.world.getTypeId(var11[0], var11[1], var11[2]);

                    if (var14 != 0 && var14 != Block.LEAVES.id)
                    {
                        ++var13;
                    }
                    else
                    {
                        this.setTypeAndData(this.world, var11[0], var11[1], var11[2], par6, 0);
                        ++var13;
                    }
                }
            }
        }
    }

    /**
     * Gets the rough size of a layer of the tree.
     */
    float a(int par1)
    {
        if ((double)par1 < (double)((float)this.e) * 0.3D)
        {
            return -1.618F;
        }
        else
        {
            float var2 = (float)this.e / 2.0F;
            float var3 = (float)this.e / 2.0F - (float)par1;
            float var4;

            if (var3 == 0.0F)
            {
                var4 = var2;
            }
            else if (Math.abs(var3) >= var2)
            {
                var4 = 0.0F;
            }
            else
            {
                var4 = (float)Math.sqrt(Math.pow((double)Math.abs(var2), 2.0D) - Math.pow((double)Math.abs(var3), 2.0D));
            }

            var4 *= 0.5F;
            return var4;
        }
    }

    float b(int par1)
    {
        return par1 >= 0 && par1 < this.n ? (par1 != 0 && par1 != this.n - 1 ? 3.0F : 2.0F) : -1.0F;
    }

    /**
     * Generates the leaves surrounding an individual entry in the leafNodes list.
     */
    void a(int par1, int par2, int par3)
    {
        int var4 = par2;

        for (int var5 = par2 + this.n; var4 < var5; ++var4)
        {
            float var6 = this.b(var4 - par2);
            this.a(par1, var4, par3, var6, (byte) 1, Block.LEAVES.id);
        }
    }

    /**
     * Places a line of the specified block ID into the world from the first coordinate triplet to the second.
     */
    void a(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, int par3)
    {
        int[] var4 = new int[] {0, 0, 0};
        byte var5 = 0;
        byte var6;

        for (var6 = 0; var5 < 3; ++var5)
        {
            var4[var5] = par2ArrayOfInteger[var5] - par1ArrayOfInteger[var5];

            if (Math.abs(var4[var5]) > Math.abs(var4[var6]))
            {
                var6 = var5;
            }
        }

        if (var4[var6] != 0)
        {
            byte var7 = a[var6];
            byte var8 = a[var6 + 3];
            byte var9;

            if (var4[var6] > 0)
            {
                var9 = 1;
            }
            else
            {
                var9 = -1;
            }

            double var10 = (double)var4[var7] / (double)var4[var6];
            double var12 = (double)var4[var8] / (double)var4[var6];
            int[] var14 = new int[] {0, 0, 0};
            int var15 = 0;

            for (int var16 = var4[var6] + var9; var15 != var16; var15 += var9)
            {
                var14[var6] = MathHelper.floor((double) (par1ArrayOfInteger[var6] + var15) + 0.5D);
                var14[var7] = MathHelper.floor((double) par1ArrayOfInteger[var7] + (double) var15 * var10 + 0.5D);
                var14[var8] = MathHelper.floor((double) par1ArrayOfInteger[var8] + (double) var15 * var12 + 0.5D);
                byte var17 = 0;
                int var18 = Math.abs(var14[0] - par1ArrayOfInteger[0]);
                int var19 = Math.abs(var14[2] - par1ArrayOfInteger[2]);
                int var20 = Math.max(var18, var19);

                if (var20 > 0)
                {
                    if (var18 == var20)
                    {
                        var17 = 4;
                    }
                    else if (var19 == var20)
                    {
                        var17 = 8;
                    }
                }

                this.setTypeAndData(this.world, var14[0], var14[1], var14[2], par3, var17);
            }
        }
    }

    /**
     * Generates the leaf portion of the tree as specified by the leafNodes list.
     */
    void b()
    {
        int var1 = 0;

        for (int var2 = this.o.length; var1 < var2; ++var1)
        {
            int var3 = this.o[var1][0];
            int var4 = this.o[var1][1];
            int var5 = this.o[var1][2];
            this.a(var3, var4, var5);
        }
    }

    /**
     * Indicates whether or not a leaf node requires additional wood to be added to preserve integrity.
     */
    boolean c(int par1)
    {
        return (double)par1 >= (double)this.e * 0.2D;
    }

    /**
     * Places the trunk for the big tree that is being generated. Able to generate double-sized trunks by changing a
     * field that is always 1 to 2.
     */
    void c()
    {
        int var1 = this.d[0];
        int var2 = this.d[1];
        int var3 = this.d[1] + this.f;
        int var4 = this.d[2];
        int[] var5 = new int[] {var1, var2, var4};
        int[] var6 = new int[] {var1, var3, var4};
        this.a(var5, var6, Block.LOG.id);

        if (this.l == 2)
        {
            ++var5[0];
            ++var6[0];
            this.a(var5, var6, Block.LOG.id);
            ++var5[2];
            ++var6[2];
            this.a(var5, var6, Block.LOG.id);
            var5[0] += -1;
            var6[0] += -1;
            this.a(var5, var6, Block.LOG.id);
        }
    }

    /**
     * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
     */
    void d()
    {
        int var1 = 0;
        int var2 = this.o.length;

        for (int[] var3 = new int[] {this.d[0], this.d[1], this.d[2]}; var1 < var2; ++var1)
        {
            int[] var4 = this.o[var1];
            int[] var5 = new int[] {var4[0], var4[1], var4[2]};
            var3[1] = var4[3];
            int var6 = var3[1] - this.d[1];

            if (this.c(var6))
            {
                this.a(var3, var5, (byte) Block.LOG.id);
            }
        }
    }

    /**
     * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
     * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
     */
    int a(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger)
    {
        int[] var3 = new int[] {0, 0, 0};
        byte var4 = 0;
        byte var5;

        for (var5 = 0; var4 < 3; ++var4)
        {
            var3[var4] = par2ArrayOfInteger[var4] - par1ArrayOfInteger[var4];

            if (Math.abs(var3[var4]) > Math.abs(var3[var5]))
            {
                var5 = var4;
            }
        }

        if (var3[var5] == 0)
        {
            return -1;
        }
        else
        {
            byte var6 = a[var5];
            byte var7 = a[var5 + 3];
            byte var8;

            if (var3[var5] > 0)
            {
                var8 = 1;
            }
            else
            {
                var8 = -1;
            }

            double var9 = (double)var3[var6] / (double)var3[var5];
            double var11 = (double)var3[var7] / (double)var3[var5];
            int[] var13 = new int[] {0, 0, 0};
            int var14 = 0;
            int var15;

            for (var15 = var3[var5] + var8; var14 != var15; var14 += var8)
            {
                var13[var5] = par1ArrayOfInteger[var5] + var14;
                var13[var6] = MathHelper.floor((double) par1ArrayOfInteger[var6] + (double) var14 * var9);
                var13[var7] = MathHelper.floor((double) par1ArrayOfInteger[var7] + (double) var14 * var11);
                int var16 = this.world.getTypeId(var13[0], var13[1], var13[2]);

                if (var16 != 0 && var16 != Block.LEAVES.id)
                {
                    break;
                }
            }

            return var14 == var15 ? -1 : Math.abs(var14);
        }
    }

    /**
     * Returns a boolean indicating whether or not the current location for the tree, spanning basePos to to the height
     * limit, is valid.
     */
    boolean e()
    {
        int[] var1 = new int[] {this.d[0], this.d[1], this.d[2]};
        int[] var2 = new int[] {this.d[0], this.d[1] + this.e - 1, this.d[2]};
        int var3 = this.world.getTypeId(this.d[0], this.d[1] - 1, this.d[2]);

        if (var3 != 2 && var3 != 3)
        {
            return false;
        }
        else
        {
            int var4 = this.a(var1, var2);

            if (var4 == -1)
            {
                return true;
            }
            else if (var4 < 6)
            {
                return false;
            }
            else
            {
                this.e = var4;
                return true;
            }
        }
    }

    /**
     * Rescales the generator settings, only used in WorldGenBigTree
     */
    public void a(double par1, double par3, double par5)
    {
        this.m = (int)(par1 * 12.0D);

        if (par1 > 0.5D)
        {
            this.n = 5;
        }

        this.j = par3;
        this.k = par5;
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        this.world = par1World;
        long var6 = par2Random.nextLong();
        this.b.setSeed(var6);
        this.d[0] = par3;
        this.d[1] = par4;
        this.d[2] = par5;

        if (this.e == 0)
        {
            this.e = 5 + this.b.nextInt(this.m);
        }

        if (!this.e())
        {
            return false;
        }
        else
        {
            this.a();
            this.b();
            this.c();
            this.d();
            return true;
        }
    }
}
