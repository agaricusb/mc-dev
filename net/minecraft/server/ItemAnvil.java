package net.minecraft.server;

public class ItemAnvil extends ItemMultiTexture
{
    public ItemAnvil(Block par1Block)
    {
        super(par1Block.id - 256, par1Block, BlockAnvil.a);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int filterData(int par1)
    {
        return par1 << 2;
    }
}
