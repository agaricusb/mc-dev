package net.minecraft.server;

import java.util.Random;

public class BlockHugeMushroom extends Block
{
    /** The mushroom type. 0 for brown, 1 for red. */
    private int a;

    public BlockHugeMushroom(int par1, Material par2Material, int par3, int par4)
    {
        super(par1, par3, par2Material);
        this.a = par4;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par2 == 10 && par1 > 1 ? this.textureId - 1 : (par2 >= 1 && par2 <= 9 && par1 == 1 ? this.textureId - 16 - this.a : (par2 >= 1 && par2 <= 3 && par1 == 2 ? this.textureId - 16 - this.a : (par2 >= 7 && par2 <= 9 && par1 == 3 ? this.textureId - 16 - this.a : ((par2 == 1 || par2 == 4 || par2 == 7) && par1 == 4 ? this.textureId - 16 - this.a : ((par2 == 3 || par2 == 6 || par2 == 9) && par1 == 5 ? this.textureId - 16 - this.a : (par2 == 14 ? this.textureId - 16 - this.a : (par2 == 15 ? this.textureId - 1 : this.textureId)))))));
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        int var2 = par1Random.nextInt(10) - 7;

        if (var2 < 0)
        {
            var2 = 0;
        }

        return var2;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.BROWN_MUSHROOM.id + this.a;
    }
}
