package net.minecraft.server;

import java.util.Random;

public class WorldGenFire extends WorldGenerator
{
    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 64; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isEmpty(var7, var8, var9) && par1World.getTypeId(var7, var8 - 1, var9) == Block.NETHERRACK.id)
            {
                par1World.setTypeId(var7, var8, var9, Block.FIRE.id);
            }
        }

        return true;
    }
}
