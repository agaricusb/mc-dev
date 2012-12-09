package net.minecraft.server;

import java.util.Random;

public class BlockObsidian extends BlockStone
{
    public BlockObsidian(int par1, int par2)
    {
        super(par1, par2);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.OBSIDIAN.id;
    }
}
