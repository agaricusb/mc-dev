package net.minecraft.server;

import java.util.Random;

public class WorldGenClay extends WorldGenerator
{
    /** The block ID for clay. */
    private int a;

    /** The number of blocks to generate. */
    private int b;

    public WorldGenClay(int par1)
    {
        this.a = Block.CLAY.id;
        this.b = par1;
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (par1World.getMaterial(par3, par4, par5) != Material.WATER)
        {
            return false;
        }
        else
        {
            int var6 = par2Random.nextInt(this.b - 2) + 2;
            byte var7 = 1;

            for (int var8 = par3 - var6; var8 <= par3 + var6; ++var8)
            {
                for (int var9 = par5 - var6; var9 <= par5 + var6; ++var9)
                {
                    int var10 = var8 - par3;
                    int var11 = var9 - par5;

                    if (var10 * var10 + var11 * var11 <= var6 * var6)
                    {
                        for (int var12 = par4 - var7; var12 <= par4 + var7; ++var12)
                        {
                            int var13 = par1World.getTypeId(var8, var12, var9);

                            if (var13 == Block.DIRT.id || var13 == Block.CLAY.id)
                            {
                                par1World.setRawTypeId(var8, var12, var9, this.a);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
