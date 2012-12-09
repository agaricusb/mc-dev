package net.minecraft.server;

import java.util.Random;

public class BlockMelon extends Block
{
    protected BlockMelon(int par1)
    {
        super(par1, Material.PUMPKIN);
        this.textureId = 136;
        this.a(CreativeModeTab.b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par1 != 1 && par1 != 0 ? 136 : 137;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 != 1 && par1 != 0 ? 136 : 137;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.MELON.id;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 3 + par1Random.nextInt(5);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int getDropCount(int par1, Random par2Random)
    {
        int var3 = this.a(par2Random) + par2Random.nextInt(1 + par1);

        if (var3 > 9)
        {
            var3 = 9;
        }

        return var3;
    }
}
