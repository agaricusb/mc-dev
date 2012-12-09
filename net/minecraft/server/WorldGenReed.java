package net.minecraft.server;

import java.util.Random;

public class WorldGenReed extends WorldGenerator
{
    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 20; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var8 = par4;
            int var9 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);

            if (par1World.isEmpty(var7, par4, var9) && (par1World.getMaterial(var7 - 1, par4 - 1, var9) == Material.WATER || par1World.getMaterial(var7 + 1, par4 - 1, var9) == Material.WATER || par1World.getMaterial(var7, par4 - 1, var9 - 1) == Material.WATER || par1World.getMaterial(var7, par4 - 1, var9 + 1) == Material.WATER))
            {
                int var10 = 2 + par2Random.nextInt(par2Random.nextInt(3) + 1);

                for (int var11 = 0; var11 < var10; ++var11)
                {
                    if (Block.SUGAR_CANE_BLOCK.d(par1World, var7, var8 + var11, var9))
                    {
                        par1World.setRawTypeId(var7, var8 + var11, var9, Block.SUGAR_CANE_BLOCK.id);
                    }
                }
            }
        }

        return true;
    }
}
