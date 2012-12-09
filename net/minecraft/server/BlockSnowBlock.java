package net.minecraft.server;

import java.util.Random;

public class BlockSnowBlock extends Block
{
    protected BlockSnowBlock(int par1, int par2)
    {
        super(par1, par2, Material.SNOW_BLOCK);
        this.b(true);
        this.a(CreativeModeTab.b);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.SNOW_BALL.id;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 4;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.b(EnumSkyBlock.BLOCK, par2, par3, par4) > 11)
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
        }
    }
}
