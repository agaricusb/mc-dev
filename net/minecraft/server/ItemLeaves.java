package net.minecraft.server;

public class ItemLeaves extends ItemBlock
{
    public ItemLeaves(int par1)
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
        return par1 | 4;
    }

    public String c_(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.getData();

        if (var2 < 0 || var2 >= BlockLeaves.a.length)
        {
            var2 = 0;
        }

        return super.getName() + "." + BlockLeaves.a[var2];
    }
}
