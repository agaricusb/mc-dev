package net.minecraft.server;

public class ItemCarrotStick extends Item
{
    public ItemCarrotStick(int par1)
    {
        super(par1);
        this.a(CreativeModeTab.e);
        this.d(1);
        this.setMaxDurability(25);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (par3EntityPlayer.ag() && par3EntityPlayer.vehicle instanceof EntityPig)
        {
            EntityPig var4 = (EntityPig)par3EntityPlayer.vehicle;

            if (var4.n().h() && par1ItemStack.k() - par1ItemStack.getData() >= 7)
            {
                var4.n().g();
                par1ItemStack.damage(7, par3EntityPlayer);

                if (par1ItemStack.count == 0)
                {
                    ItemStack var5 = new ItemStack(Item.FISHING_ROD);
                    var5.setTag(par1ItemStack.tag);
                    return var5;
                }
            }
        }

        return par1ItemStack;
    }
}
