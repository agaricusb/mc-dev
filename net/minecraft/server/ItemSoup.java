package net.minecraft.server;

public class ItemSoup extends ItemFood
{
    public ItemSoup(int par1, int par2)
    {
        super(par1, par2, false);
        this.d(1);
    }

    public ItemStack b(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        super.b(par1ItemStack, par2World, par3EntityPlayer);
        return new ItemStack(Item.BOWL);
    }
}
