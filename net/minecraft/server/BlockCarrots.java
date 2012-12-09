package net.minecraft.server;

public class BlockCarrots extends BlockCrops
{
    public BlockCarrots(int par1)
    {
        super(par1, 200);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        if (par2 < 7)
        {
            if (par2 == 6)
            {
                par2 = 5;
            }

            return this.textureId + (par2 >> 1);
        }
        else
        {
            return this.textureId + 3;
        }
    }

    /**
     * Generate a seed ItemStack for this crop.
     */
    protected int h()
    {
        return Item.CARROT.id;
    }

    /**
     * Generate a crop produce ItemStack for this crop.
     */
    protected int j()
    {
        return Item.CARROT.id;
    }
}
