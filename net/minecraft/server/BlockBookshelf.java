package net.minecraft.server;

import java.util.Random;

public class BlockBookshelf extends Block
{
    public BlockBookshelf(int par1, int par2)
    {
        super(par1, par2, Material.WOOD);
        this.a(CreativeModeTab.b);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 <= 1 ? 4 : this.textureId;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 3;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.BOOK.id;
    }
}
