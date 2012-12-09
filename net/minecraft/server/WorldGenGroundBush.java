package net.minecraft.server;

import java.util.Random;

public class WorldGenGroundBush extends WorldGenerator
{
    private int a;
    private int b;

    public WorldGenGroundBush(int par1, int par2)
    {
        this.b = par1;
        this.a = par2;
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var15;

        for (boolean var6 = false; ((var15 = par1World.getTypeId(par3, par4, par5)) == 0 || var15 == Block.LEAVES.id) && par4 > 0; --par4)
        {
            ;
        }

        int var7 = par1World.getTypeId(par3, par4, par5);

        if (var7 == Block.DIRT.id || var7 == Block.GRASS.id)
        {
            ++par4;
            this.setTypeAndData(par1World, par3, par4, par5, Block.LOG.id, this.b);

            for (int var8 = par4; var8 <= par4 + 2; ++var8)
            {
                int var9 = var8 - par4;
                int var10 = 2 - var9;

                for (int var11 = par3 - var10; var11 <= par3 + var10; ++var11)
                {
                    int var12 = var11 - par3;

                    for (int var13 = par5 - var10; var13 <= par5 + var10; ++var13)
                    {
                        int var14 = var13 - par5;

                        if ((Math.abs(var12) != var10 || Math.abs(var14) != var10 || par2Random.nextInt(2) != 0) && !Block.q[par1World.getTypeId(var11, var8, var13)])
                        {
                            this.setTypeAndData(par1World, var11, var8, var13, Block.LEAVES.id, this.a);
                        }
                    }
                }
            }
        }

        return true;
    }
}
