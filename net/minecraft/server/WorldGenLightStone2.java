package net.minecraft.server;

import java.util.Random;

public class WorldGenLightStone2 extends WorldGenerator
{
    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (!par1World.isEmpty(par3, par4, par5))
        {
            return false;
        }
        else if (par1World.getTypeId(par3, par4 + 1, par5) != Block.NETHERRACK.id)
        {
            return false;
        }
        else
        {
            par1World.setTypeId(par3, par4, par5, Block.GLOWSTONE.id);

            for (int var6 = 0; var6 < 1500; ++var6)
            {
                int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
                int var8 = par4 - par2Random.nextInt(12);
                int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

                if (par1World.getTypeId(var7, var8, var9) == 0)
                {
                    int var10 = 0;

                    for (int var11 = 0; var11 < 6; ++var11)
                    {
                        int var12 = 0;

                        if (var11 == 0)
                        {
                            var12 = par1World.getTypeId(var7 - 1, var8, var9);
                        }

                        if (var11 == 1)
                        {
                            var12 = par1World.getTypeId(var7 + 1, var8, var9);
                        }

                        if (var11 == 2)
                        {
                            var12 = par1World.getTypeId(var7, var8 - 1, var9);
                        }

                        if (var11 == 3)
                        {
                            var12 = par1World.getTypeId(var7, var8 + 1, var9);
                        }

                        if (var11 == 4)
                        {
                            var12 = par1World.getTypeId(var7, var8, var9 - 1);
                        }

                        if (var11 == 5)
                        {
                            var12 = par1World.getTypeId(var7, var8, var9 + 1);
                        }

                        if (var12 == Block.GLOWSTONE.id)
                        {
                            ++var10;
                        }
                    }

                    if (var10 == 1)
                    {
                        par1World.setTypeId(var7, var8, var9, Block.GLOWSTONE.id);
                    }
                }
            }

            return true;
        }
    }
}
