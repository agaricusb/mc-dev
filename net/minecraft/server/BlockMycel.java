package net.minecraft.server;

import java.util.Random;

public class BlockMycel extends Block
{
    protected BlockMycel(int par1)
    {
        super(par1, Material.GRASS);
        this.textureId = 77;
        this.b(true);
        this.a(CreativeModeTab.b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par1 == 1 ? 78 : (par1 == 0 ? 2 : 77);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isStatic)
        {
            if (par1World.getLightLevel(par2, par3 + 1, par4) < 4 && Block.lightBlock[par1World.getTypeId(par2, par3 + 1, par4)] > 2)
            {
                par1World.setTypeId(par2, par3, par4, Block.DIRT.id);
            }
            else if (par1World.getLightLevel(par2, par3 + 1, par4) >= 9)
            {
                for (int var6 = 0; var6 < 4; ++var6)
                {
                    int var7 = par2 + par5Random.nextInt(3) - 1;
                    int var8 = par3 + par5Random.nextInt(5) - 3;
                    int var9 = par4 + par5Random.nextInt(3) - 1;
                    int var10 = par1World.getTypeId(var7, var8 + 1, var9);

                    if (par1World.getTypeId(var7, var8, var9) == Block.DIRT.id && par1World.getLightLevel(var7, var8 + 1, var9) >= 4 && Block.lightBlock[var10] <= 2)
                    {
                        par1World.setTypeId(var7, var8, var9, this.id);
                    }
                }
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.DIRT.getDropType(0, par2Random, par3);
    }
}
