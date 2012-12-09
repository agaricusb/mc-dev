package net.minecraft.server;

import java.util.Random;

public class WorldGenDesertWell extends WorldGenerator
{
    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        while (par1World.isEmpty(par3, par4, par5) && par4 > 2)
        {
            --par4;
        }

        int var6 = par1World.getTypeId(par3, par4, par5);

        if (var6 != Block.SAND.id)
        {
            return false;
        }
        else
        {
            int var7;
            int var8;

            for (var7 = -2; var7 <= 2; ++var7)
            {
                for (var8 = -2; var8 <= 2; ++var8)
                {
                    if (par1World.isEmpty(par3 + var7, par4 - 1, par5 + var8) && par1World.isEmpty(par3 + var7, par4 - 2, par5 + var8))
                    {
                        return false;
                    }
                }
            }

            for (var7 = -1; var7 <= 0; ++var7)
            {
                for (var8 = -2; var8 <= 2; ++var8)
                {
                    for (int var9 = -2; var9 <= 2; ++var9)
                    {
                        par1World.setRawTypeId(par3 + var8, par4 + var7, par5 + var9, Block.SANDSTONE.id);
                    }
                }
            }

            par1World.setRawTypeId(par3, par4, par5, Block.WATER.id);
            par1World.setRawTypeId(par3 - 1, par4, par5, Block.WATER.id);
            par1World.setRawTypeId(par3 + 1, par4, par5, Block.WATER.id);
            par1World.setRawTypeId(par3, par4, par5 - 1, Block.WATER.id);
            par1World.setRawTypeId(par3, par4, par5 + 1, Block.WATER.id);

            for (var7 = -2; var7 <= 2; ++var7)
            {
                for (var8 = -2; var8 <= 2; ++var8)
                {
                    if (var7 == -2 || var7 == 2 || var8 == -2 || var8 == 2)
                    {
                        par1World.setRawTypeId(par3 + var7, par4 + 1, par5 + var8, Block.SANDSTONE.id);
                    }
                }
            }

            par1World.setRawTypeIdAndData(par3 + 2, par4 + 1, par5, Block.STEP.id, 1);
            par1World.setRawTypeIdAndData(par3 - 2, par4 + 1, par5, Block.STEP.id, 1);
            par1World.setRawTypeIdAndData(par3, par4 + 1, par5 + 2, Block.STEP.id, 1);
            par1World.setRawTypeIdAndData(par3, par4 + 1, par5 - 2, Block.STEP.id, 1);

            for (var7 = -1; var7 <= 1; ++var7)
            {
                for (var8 = -1; var8 <= 1; ++var8)
                {
                    if (var7 == 0 && var8 == 0)
                    {
                        par1World.setRawTypeId(par3 + var7, par4 + 4, par5 + var8, Block.SANDSTONE.id);
                    }
                    else
                    {
                        par1World.setRawTypeIdAndData(par3 + var7, par4 + 4, par5 + var8, Block.STEP.id, 1);
                    }
                }
            }

            for (var7 = 1; var7 <= 3; ++var7)
            {
                par1World.setRawTypeId(par3 - 1, par4 + var7, par5 - 1, Block.SANDSTONE.id);
                par1World.setRawTypeId(par3 - 1, par4 + var7, par5 + 1, Block.SANDSTONE.id);
                par1World.setRawTypeId(par3 + 1, par4 + var7, par5 - 1, Block.SANDSTONE.id);
                par1World.setRawTypeId(par3 + 1, par4 + var7, par5 + 1, Block.SANDSTONE.id);
            }

            return true;
        }
    }
}
