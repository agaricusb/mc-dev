package net.minecraft.server;

public class ItemCloth extends ItemBlock
{
    public ItemCloth(int par1)
    {
        super(par1);
        this.setMaxDurability(0);
        this.a(true);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int filterData(int par1)
    {
        return par1;
    }

    public String c_(ItemStack par1ItemStack)
    {
        return super.getName() + "." + ItemDye.a[BlockCloth.e_(par1ItemStack.getData())];
    }
}
