package net.minecraft.server;

public class ItemPiston extends ItemBlock
{
    public ItemPiston(int par1)
    {
        super(par1);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int filterData(int par1)
    {
        return 7;
    }
}
