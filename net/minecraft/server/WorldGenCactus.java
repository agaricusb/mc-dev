package net.minecraft.server;

import java.util.Random;

public class WorldGenCactus extends WorldGenerator
{
    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 10; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isEmpty(var7, var8, var9))
            {
                int var10 = 1 + par2Random.nextInt(par2Random.nextInt(3) + 1);

                for (int var11 = 0; var11 < var10; ++var11)
                {
                    if (Block.CACTUS.d(par1World, var7, var8 + var11, var9))
                    {
                        par1World.setRawTypeId(var7, var8 + var11, var9, Block.CACTUS.id);
                    }
                }
            }
        }

        return true;
    }
}
