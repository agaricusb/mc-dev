package net.minecraft.server;

import java.util.Random;

public class WorldGenHugeMushroom extends WorldGenerator
{
    /** The mushroom type. 0 for brown, 1 for red. */
    private int a = -1;

    public WorldGenHugeMushroom(int par1)
    {
        super(true);
        this.a = par1;
    }

    public WorldGenHugeMushroom()
    {
        super(false);
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par2Random.nextInt(2);

        if (this.a >= 0)
        {
            var6 = this.a;
        }

        int var7 = par2Random.nextInt(3) + 4;
        boolean var8 = true;

        if (par4 >= 1 && par4 + var7 + 1 < 256)
        {
            int var9;
            int var11;
            int var12;
            int var13;

            for (var9 = par4; var9 <= par4 + 1 + var7; ++var9)
            {
                byte var10 = 3;

                if (var9 <= par4 + 3)
                {
                    var10 = 0;
                }

                for (var11 = par3 - var10; var11 <= par3 + var10 && var8; ++var11)
                {
                    for (var12 = par5 - var10; var12 <= par5 + var10 && var8; ++var12)
                    {
                        if (var9 >= 0 && var9 < 256)
                        {
                            var13 = par1World.getTypeId(var11, var9, var12);

                            if (var13 != 0 && var13 != Block.LEAVES.id)
                            {
                                var8 = false;
                            }
                        }
                        else
                        {
                            var8 = false;
                        }
                    }
                }
            }

            if (!var8)
            {
                return false;
            }
            else
            {
                var9 = par1World.getTypeId(par3, par4 - 1, par5);

                if (var9 != Block.DIRT.id && var9 != Block.GRASS.id && var9 != Block.MYCEL.id)
                {
                    return false;
                }
                else
                {
                    int var16 = par4 + var7;

                    if (var6 == 1)
                    {
                        var16 = par4 + var7 - 3;
                    }

                    for (var11 = var16; var11 <= par4 + var7; ++var11)
                    {
                        var12 = 1;

                        if (var11 < par4 + var7)
                        {
                            ++var12;
                        }

                        if (var6 == 0)
                        {
                            var12 = 3;
                        }

                        for (var13 = par3 - var12; var13 <= par3 + var12; ++var13)
                        {
                            for (int var14 = par5 - var12; var14 <= par5 + var12; ++var14)
                            {
                                int var15 = 5;

                                if (var13 == par3 - var12)
                                {
                                    --var15;
                                }

                                if (var13 == par3 + var12)
                                {
                                    ++var15;
                                }

                                if (var14 == par5 - var12)
                                {
                                    var15 -= 3;
                                }

                                if (var14 == par5 + var12)
                                {
                                    var15 += 3;
                                }

                                if (var6 == 0 || var11 < par4 + var7)
                                {
                                    if ((var13 == par3 - var12 || var13 == par3 + var12) && (var14 == par5 - var12 || var14 == par5 + var12))
                                    {
                                        continue;
                                    }

                                    if (var13 == par3 - (var12 - 1) && var14 == par5 - var12)
                                    {
                                        var15 = 1;
                                    }

                                    if (var13 == par3 - var12 && var14 == par5 - (var12 - 1))
                                    {
                                        var15 = 1;
                                    }

                                    if (var13 == par3 + (var12 - 1) && var14 == par5 - var12)
                                    {
                                        var15 = 3;
                                    }

                                    if (var13 == par3 + var12 && var14 == par5 - (var12 - 1))
                                    {
                                        var15 = 3;
                                    }

                                    if (var13 == par3 - (var12 - 1) && var14 == par5 + var12)
                                    {
                                        var15 = 7;
                                    }

                                    if (var13 == par3 - var12 && var14 == par5 + (var12 - 1))
                                    {
                                        var15 = 7;
                                    }

                                    if (var13 == par3 + (var12 - 1) && var14 == par5 + var12)
                                    {
                                        var15 = 9;
                                    }

                                    if (var13 == par3 + var12 && var14 == par5 + (var12 - 1))
                                    {
                                        var15 = 9;
                                    }
                                }

                                if (var15 == 5 && var11 < par4 + var7)
                                {
                                    var15 = 0;
                                }

                                if ((var15 != 0 || par4 >= par4 + var7 - 1) && !Block.q[par1World.getTypeId(var13, var11, var14)])
                                {
                                    this.setTypeAndData(par1World, var13, var11, var14, Block.BIG_MUSHROOM_1.id + var6, var15);
                                }
                            }
                        }
                    }

                    for (var11 = 0; var11 < var7; ++var11)
                    {
                        var12 = par1World.getTypeId(par3, par4 + var11, par5);

                        if (!Block.q[var12])
                        {
                            this.setTypeAndData(par1World, par3, par4 + var11, par5, Block.BIG_MUSHROOM_1.id + var6, 10);
                        }
                    }

                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
