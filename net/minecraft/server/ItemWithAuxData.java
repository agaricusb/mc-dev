package net.minecraft.server;

public class ItemWithAuxData extends ItemBlock
{
    private final Block a;
    private String[] b;

    public ItemWithAuxData(int par1, boolean par2)
    {
        super(par1);
        this.a = Block.byId[this.g()];

        if (par2)
        {
            this.setMaxDurability(0);
            this.a(true);
        }
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int filterData(int par1)
    {
        return par1;
    }

    /**
     * Sets the array of strings to be used for name lookups from item damage to metadata
     */
    public ItemWithAuxData a(String[] par1ArrayOfStr)
    {
        this.b = par1ArrayOfStr;
        return this;
    }

    public String c_(ItemStack par1ItemStack)
    {
        if (this.b == null)
        {
            return super.c_(par1ItemStack);
        }
        else
        {
            int var2 = par1ItemStack.getData();
            return var2 >= 0 && var2 < this.b.length ? super.c_(par1ItemStack) + "." + this.b[var2] : super.c_(par1ItemStack);
        }
    }
}
