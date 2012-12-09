package net.minecraft.server;

public class ItemMultiTexture extends ItemBlock
{
    private final Block a;
    private final String[] b;

    public ItemMultiTexture(int par1, Block par2Block, String[] par3ArrayOfStr)
    {
        super(par1);
        this.a = par2Block;
        this.b = par3ArrayOfStr;
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
        int var2 = par1ItemStack.getData();

        if (var2 < 0 || var2 >= this.b.length)
        {
            var2 = 0;
        }

        return super.getName() + "." + this.b[var2];
    }
}
