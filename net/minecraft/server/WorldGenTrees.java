package net.minecraft.server;

import java.util.Random;

public class WorldGenTrees extends WorldGenerator
{
    /** The minimum height of a generated tree. */
    private final int a;

    /** True if this tree should grow Vines. */
    private final boolean b;

    /** The metadata value of the wood to use in tree generation. */
    private final int c;

    /** The metadata value of the leaves to use in tree generation. */
    private final int d;

    public WorldGenTrees(boolean par1)
    {
        this(par1, 4, 0, 0, false);
    }

    public WorldGenTrees(boolean par1, int par2, int par3, int par4, boolean par5)
    {
        super(par1);
        this.a = par2;
        this.c = par3;
        this.d = par4;
        this.b = par5;
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par2Random.nextInt(3) + this.a;
        boolean var7 = true;

        if (par4 >= 1 && par4 + var6 + 1 <= 256)
        {
            int var8;
            byte var9;
            int var11;
            int var12;

            for (var8 = par4; var8 <= par4 + 1 + var6; ++var8)
            {
                var9 = 1;

                if (var8 == par4)
                {
                    var9 = 0;
                }

                if (var8 >= par4 + 1 + var6 - 2)
                {
                    var9 = 2;
                }

                for (int var10 = par3 - var9; var10 <= par3 + var9 && var7; ++var10)
                {
                    for (var11 = par5 - var9; var11 <= par5 + var9 && var7; ++var11)
                    {
                        if (var8 >= 0 && var8 < 256)
                        {
                            var12 = par1World.getTypeId(var10, var8, var11);

                            if (var12 != 0 && var12 != Block.LEAVES.id && var12 != Block.GRASS.id && var12 != Block.DIRT.id && var12 != Block.LOG.id)
                            {
                                var7 = false;
                            }
                        }
                        else
                        {
                            var7 = false;
                        }
                    }
                }
            }

            if (!var7)
            {
                return false;
            }
            else
            {
                var8 = par1World.getTypeId(par3, par4 - 1, par5);

                if ((var8 == Block.GRASS.id || var8 == Block.DIRT.id) && par4 < 256 - var6 - 1)
                {
                    this.setType(par1World, par3, par4 - 1, par5, Block.DIRT.id);
                    var9 = 3;
                    byte var18 = 0;
                    int var13;
                    int var14;
                    int var15;

                    for (var11 = par4 - var9 + var6; var11 <= par4 + var6; ++var11)
                    {
                        var12 = var11 - (par4 + var6);
                        var13 = var18 + 1 - var12 / 2;

                        for (var14 = par3 - var13; var14 <= par3 + var13; ++var14)
                        {
                            var15 = var14 - par3;

                            for (int var16 = par5 - var13; var16 <= par5 + var13; ++var16)
                            {
                                int var17 = var16 - par5;

                                if ((Math.abs(var15) != var13 || Math.abs(var17) != var13 || par2Random.nextInt(2) != 0 && var12 != 0) && par1World.isEmpty(var14, var11, var16))
                                {
                                    this.setTypeAndData(par1World, var14, var11, var16, Block.LEAVES.id, this.d);
                                }
                            }
                        }
                    }

                    for (var11 = 0; var11 < var6; ++var11)
                    {
                        var12 = par1World.getTypeId(par3, par4 + var11, par5);

                        if (var12 == 0 || var12 == Block.LEAVES.id)
                        {
                            this.setTypeAndData(par1World, par3, par4 + var11, par5, Block.LOG.id, this.c);

                            if (this.b && var11 > 0)
                            {
                                if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3 - 1, par4 + var11, par5))
                                {
                                    this.setTypeAndData(par1World, par3 - 1, par4 + var11, par5, Block.VINE.id, 8);
                                }

                                if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3 + 1, par4 + var11, par5))
                                {
                                    this.setTypeAndData(par1World, par3 + 1, par4 + var11, par5, Block.VINE.id, 2);
                                }

                                if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3, par4 + var11, par5 - 1))
                                {
                                    this.setTypeAndData(par1World, par3, par4 + var11, par5 - 1, Block.VINE.id, 1);
                                }

                                if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3, par4 + var11, par5 + 1))
                                {
                                    this.setTypeAndData(par1World, par3, par4 + var11, par5 + 1, Block.VINE.id, 4);
                                }
                            }
                        }
                    }

                    if (this.b)
                    {
                        for (var11 = par4 - 3 + var6; var11 <= par4 + var6; ++var11)
                        {
                            var12 = var11 - (par4 + var6);
                            var13 = 2 - var12 / 2;

                            for (var14 = par3 - var13; var14 <= par3 + var13; ++var14)
                            {
                                for (var15 = par5 - var13; var15 <= par5 + var13; ++var15)
                                {
                                    if (par1World.getTypeId(var14, var11, var15) == Block.LEAVES.id)
                                    {
                                        if (par2Random.nextInt(4) == 0 && par1World.getTypeId(var14 - 1, var11, var15) == 0)
                                        {
                                            this.b(par1World, var14 - 1, var11, var15, 8);
                                        }

                                        if (par2Random.nextInt(4) == 0 && par1World.getTypeId(var14 + 1, var11, var15) == 0)
                                        {
                                            this.b(par1World, var14 + 1, var11, var15, 2);
                                        }

                                        if (par2Random.nextInt(4) == 0 && par1World.getTypeId(var14, var11, var15 - 1) == 0)
                                        {
                                            this.b(par1World, var14, var11, var15 - 1, 1);
                                        }

                                        if (par2Random.nextInt(4) == 0 && par1World.getTypeId(var14, var11, var15 + 1) == 0)
                                        {
                                            this.b(par1World, var14, var11, var15 + 1, 4);
                                        }
                                    }
                                }
                            }
                        }

                        if (par2Random.nextInt(5) == 0 && var6 > 5)
                        {
                            for (var11 = 0; var11 < 2; ++var11)
                            {
                                for (var12 = 0; var12 < 4; ++var12)
                                {
                                    if (par2Random.nextInt(4 - var11) == 0)
                                    {
                                        var13 = par2Random.nextInt(3);
                                        this.setTypeAndData(par1World, par3 + Direction.a[Direction.f[var12]], par4 + var6 - 5 + var11, par5 + Direction.b[Direction.f[var12]], Block.COCOA.id, var13 << 2 | var12);
                                    }
                                }
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
        }
        else
        {
            return false;
        }
    }

    /**
     * Grows vines downward from the given block for a given length. Args: World, x, starty, z, vine-length
     */
    private void b(World par1World, int par2, int par3, int par4, int par5)
    {
        this.setTypeAndData(par1World, par2, par3, par4, Block.VINE.id, par5);
        int var6 = 4;

        while (true)
        {
            --par3;

            if (par1World.getTypeId(par2, par3, par4) != 0 || var6 <= 0)
            {
                return;
            }

            this.setTypeAndData(par1World, par2, par3, par4, Block.VINE.id, par5);
            --var6;
        }
    }
}
