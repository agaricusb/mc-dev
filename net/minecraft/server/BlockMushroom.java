package net.minecraft.server;

import java.util.Random;

public class BlockMushroom extends BlockFlower
{
    protected BlockMushroom(int par1, int par2)
    {
        super(par1, par2);
        float var3 = 0.2F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
        this.b(true);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par5Random.nextInt(25) == 0)
        {
            byte var6 = 4;
            int var7 = 5;
            int var8;
            int var9;
            int var10;

            for (var8 = par2 - var6; var8 <= par2 + var6; ++var8)
            {
                for (var9 = par4 - var6; var9 <= par4 + var6; ++var9)
                {
                    for (var10 = par3 - 1; var10 <= par3 + 1; ++var10)
                    {
                        if (par1World.getTypeId(var8, var10, var9) == this.id)
                        {
                            --var7;

                            if (var7 <= 0)
                            {
                                return;
                            }
                        }
                    }
                }
            }

            var8 = par2 + par5Random.nextInt(3) - 1;
            var9 = par3 + par5Random.nextInt(2) - par5Random.nextInt(2);
            var10 = par4 + par5Random.nextInt(3) - 1;

            for (int var11 = 0; var11 < 4; ++var11)
            {
                if (par1World.isEmpty(var8, var9, var10) && this.d(par1World, var8, var9, var10))
                {
                    par2 = var8;
                    par3 = var9;
                    par4 = var10;
                }

                var8 = par2 + par5Random.nextInt(3) - 1;
                var9 = par3 + par5Random.nextInt(2) - par5Random.nextInt(2);
                var10 = par4 + par5Random.nextInt(3) - 1;
            }

            if (par1World.isEmpty(var8, var9, var10) && this.d(par1World, var8, var9, var10))
            {
                par1World.setTypeId(var8, var9, var10, this.id);
            }
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return super.canPlace(par1World, par2, par3, par4) && this.d(par1World, par2, par3, par4);
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean d_(int par1)
    {
        return Block.q[par1];
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean d(World par1World, int par2, int par3, int par4)
    {
        if (par3 >= 0 && par3 < 256)
        {
            int var5 = par1World.getTypeId(par2, par3 - 1, par4);
            return var5 == Block.MYCEL.id || par1World.l(par2, par3, par4) < 13 && this.d_(var5);
        }
        else
        {
            return false;
        }
    }

    /**
     * Fertilize the mushroom.
     */
    public boolean grow(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int var6 = par1World.getData(par2, par3, par4);
        par1World.setRawTypeId(par2, par3, par4, 0);
        WorldGenHugeMushroom var7 = null;

        if (this.id == Block.BROWN_MUSHROOM.id)
        {
            var7 = new WorldGenHugeMushroom(0);
        }
        else if (this.id == Block.RED_MUSHROOM.id)
        {
            var7 = new WorldGenHugeMushroom(1);
        }

        if (var7 != null && var7.a(par1World, par5Random, par2, par3, par4))
        {
            return true;
        }
        else
        {
            par1World.setRawTypeIdAndData(par2, par3, par4, this.id, var6);
            return false;
        }
    }
}
