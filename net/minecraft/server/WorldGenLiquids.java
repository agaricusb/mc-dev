package net.minecraft.server;

import java.util.Random;

public class WorldGenLiquids extends WorldGenerator
{
    /** The ID of the liquid block used in this liquid generator. */
    private int a;

    public WorldGenLiquids(int par1)
    {
        this.a = par1;
    }

    public boolean a(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (par1World.getTypeId(par3, par4 + 1, par5) != Block.STONE.id)
        {
            return false;
        }
        else if (par1World.getTypeId(par3, par4 - 1, par5) != Block.STONE.id)
        {
            return false;
        }
        else if (par1World.getTypeId(par3, par4, par5) != 0 && par1World.getTypeId(par3, par4, par5) != Block.STONE.id)
        {
            return false;
        }
        else
        {
            int var6 = 0;

            if (par1World.getTypeId(par3 - 1, par4, par5) == Block.STONE.id)
            {
                ++var6;
            }

            if (par1World.getTypeId(par3 + 1, par4, par5) == Block.STONE.id)
            {
                ++var6;
            }

            if (par1World.getTypeId(par3, par4, par5 - 1) == Block.STONE.id)
            {
                ++var6;
            }

            if (par1World.getTypeId(par3, par4, par5 + 1) == Block.STONE.id)
            {
                ++var6;
            }

            int var7 = 0;

            if (par1World.isEmpty(par3 - 1, par4, par5))
            {
                ++var7;
            }

            if (par1World.isEmpty(par3 + 1, par4, par5))
            {
                ++var7;
            }

            if (par1World.isEmpty(par3, par4, par5 - 1))
            {
                ++var7;
            }

            if (par1World.isEmpty(par3, par4, par5 + 1))
            {
                ++var7;
            }

            if (var6 == 3 && var7 == 1)
            {
                par1World.setTypeId(par3, par4, par5, this.a);
                par1World.d = true;
                Block.byId[this.a].b(par1World, par3, par4, par5, par2Random);
                par1World.d = false;
            }

            return true;
        }
    }
}
