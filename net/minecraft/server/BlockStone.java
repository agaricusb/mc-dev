package net.minecraft.server;

import java.util.Random;

public class BlockStone extends Block
{
    public BlockStone(int par1, int par2)
    {
        super(par1, par2, Material.STONE);
        this.a(CreativeModeTab.b);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.COBBLESTONE.id;
    }
}
