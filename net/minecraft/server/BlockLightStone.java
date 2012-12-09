package net.minecraft.server;

import java.util.Random;

public class BlockLightStone extends Block
{
    public BlockLightStone(int par1, int par2, Material par3Material)
    {
        super(par1, par2, par3Material);
        this.a(CreativeModeTab.b);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int getDropCount(int par1, Random par2Random)
    {
        return MathHelper.a(this.a(par2Random) + par2Random.nextInt(par1 + 1), 1, 4);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 2 + par1Random.nextInt(3);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.GLOWSTONE_DUST.id;
    }
}
