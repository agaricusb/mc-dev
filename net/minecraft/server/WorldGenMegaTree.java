package net.minecraft.server;

import java.util.Random;

public class WorldGenMegaTree extends WorldGenerator
{
    /** The base height of the tree */
    private final int a;

    /** Sets the metadata for the wood blocks used */
    private final int b;

    /** Sets the metadata for the leaves used in huge trees */
    private final int c;

    public WorldGenMegaTree(boolean par1, int par2, int par3, int par4)
    {
        super(par1);
        this.a = par2;
        this.b = par3;
        this.c = par4;
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par2Random.nextInt(3) + this.a;
        boolean var7 = true;

        if (par4 >= 1 && par4 + var6 + 1 <= 256)
        {
            int var8;
            int var10;
            int var11;
            int var12;

            for (var8 = par4; var8 <= par4 + 1 + var6; ++var8)
            {
                byte var9 = 2;

                if (var8 == par4)
                {
                    var9 = 1;
                }

                if (var8 >= par4 + 1 + var6 - 2)
                {
                    var9 = 2;
                }

                for (var10 = par3 - var9; var10 <= par3 + var9 && var7; ++var10)
                {
                    for (var11 = par5 - var9; var11 <= par5 + var9 && var7; ++var11)
                    {
                        if (var8 >= 0 && var8 < 256)
                        {
                            var12 = par1World.getTypeId(var10, var8, var11);

                            if (var12 != 0 && var12 != Block.LEAVES.id && var12 != Block.GRASS.id && var12 != Block.DIRT.id && var12 != Block.LOG.id && var12 != Block.SAPLING.id)
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
                    par1World.setRawTypeId(par3, par4 - 1, par5, Block.DIRT.id);
                    par1World.setRawTypeId(par3 + 1, par4 - 1, par5, Block.DIRT.id);
                    par1World.setRawTypeId(par3, par4 - 1, par5 + 1, Block.DIRT.id);
                    par1World.setRawTypeId(par3 + 1, par4 - 1, par5 + 1, Block.DIRT.id);
                    this.a(par1World, par3, par5, par4 + var6, 2, par2Random);

                    for (int var14 = par4 + var6 - 2 - par2Random.nextInt(4); var14 > par4 + var6 / 2; var14 -= 2 + par2Random.nextInt(4))
                    {
                        float var15 = par2Random.nextFloat() * (float)Math.PI * 2.0F;
                        var11 = par3 + (int)(0.5F + MathHelper.cos(var15) * 4.0F);
                        var12 = par5 + (int)(0.5F + MathHelper.sin(var15) * 4.0F);
                        this.a(par1World, var11, var12, var14, 0, par2Random);

                        for (int var13 = 0; var13 < 5; ++var13)
                        {
                            var11 = par3 + (int)(1.5F + MathHelper.cos(var15) * (float)var13);
                            var12 = par5 + (int)(1.5F + MathHelper.sin(var15) * (float)var13);
                            this.setTypeAndData(par1World, var11, var14 - 3 + var13 / 2, var12, Block.LOG.id, this.b);
                        }
                    }

                    for (var10 = 0; var10 < var6; ++var10)
                    {
                        var11 = par1World.getTypeId(par3, par4 + var10, par5);

                        if (var11 == 0 || var11 == Block.LEAVES.id)
                        {
                            this.setTypeAndData(par1World, par3, par4 + var10, par5, Block.LOG.id, this.b);

                            if (var10 > 0)
                            {
                                if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3 - 1, par4 + var10, par5))
                                {
                                    this.setTypeAndData(par1World, par3 - 1, par4 + var10, par5, Block.VINE.id, 8);
                                }

                                if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3, par4 + var10, par5 - 1))
                                {
                                    this.setTypeAndData(par1World, par3, par4 + var10, par5 - 1, Block.VINE.id, 1);
                                }
                            }
                        }

                        if (var10 < var6 - 1)
                        {
                            var11 = par1World.getTypeId(par3 + 1, par4 + var10, par5);

                            if (var11 == 0 || var11 == Block.LEAVES.id)
                            {
                                this.setTypeAndData(par1World, par3 + 1, par4 + var10, par5, Block.LOG.id, this.b);

                                if (var10 > 0)
                                {
                                    if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3 + 2, par4 + var10, par5))
                                    {
                                        this.setTypeAndData(par1World, par3 + 2, par4 + var10, par5, Block.VINE.id, 2);
                                    }

                                    if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3 + 1, par4 + var10, par5 - 1))
                                    {
                                        this.setTypeAndData(par1World, par3 + 1, par4 + var10, par5 - 1, Block.VINE.id, 1);
                                    }
                                }
                            }

                            var11 = par1World.getTypeId(par3 + 1, par4 + var10, par5 + 1);

                            if (var11 == 0 || var11 == Block.LEAVES.id)
                            {
                                this.setTypeAndData(par1World, par3 + 1, par4 + var10, par5 + 1, Block.LOG.id, this.b);

                                if (var10 > 0)
                                {
                                    if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3 + 2, par4 + var10, par5 + 1))
                                    {
                                        this.setTypeAndData(par1World, par3 + 2, par4 + var10, par5 + 1, Block.VINE.id, 2);
                                    }

                                    if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3 + 1, par4 + var10, par5 + 2))
                                    {
                                        this.setTypeAndData(par1World, par3 + 1, par4 + var10, par5 + 2, Block.VINE.id, 4);
                                    }
                                }
                            }

                            var11 = par1World.getTypeId(par3, par4 + var10, par5 + 1);

                            if (var11 == 0 || var11 == Block.LEAVES.id)
                            {
                                this.setTypeAndData(par1World, par3, par4 + var10, par5 + 1, Block.LOG.id, this.b);

                                if (var10 > 0)
                                {
                                    if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3 - 1, par4 + var10, par5 + 1))
                                    {
                                        this.setTypeAndData(par1World, par3 - 1, par4 + var10, par5 + 1, Block.VINE.id, 8);
                                    }

                                    if (par2Random.nextInt(3) > 0 && par1World.isEmpty(par3, par4 + var10, par5 + 2))
                                    {
                                        this.setTypeAndData(par1World, par3, par4 + var10, par5 + 2, Block.VINE.id, 4);
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

    private void a(World par1World, int par2, int par3, int par4, int par5, Random par6Random)
    {
        byte var7 = 2;

        for (int var8 = par4 - var7; var8 <= par4; ++var8)
        {
            int var9 = var8 - par4;
            int var10 = par5 + 1 - var9;

            for (int var11 = par2 - var10; var11 <= par2 + var10 + 1; ++var11)
            {
                int var12 = var11 - par2;

                for (int var13 = par3 - var10; var13 <= par3 + var10 + 1; ++var13)
                {
                    int var14 = var13 - par3;

                    if ((var12 >= 0 || var14 >= 0 || var12 * var12 + var14 * var14 <= var10 * var10) && (var12 <= 0 && var14 <= 0 || var12 * var12 + var14 * var14 <= (var10 + 1) * (var10 + 1)) && (par6Random.nextInt(4) != 0 || var12 * var12 + var14 * var14 <= (var10 - 1) * (var10 - 1)) && !Block.q[par1World.getTypeId(var11, var8, var13)])
                    {
                        this.setTypeAndData(par1World, var11, var8, var13, Block.LEAVES.id, this.c);
                    }
                }
            }
        }
    }
}
