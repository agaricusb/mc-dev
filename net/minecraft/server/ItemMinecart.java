package net.minecraft.server;

public class ItemMinecart extends Item
{
    public int a;

    public ItemMinecart(int par1, int par2)
    {
        super(par1);
        this.maxStackSize = 1;
        this.a = par2;
        this.a(CreativeModeTab.e);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int var11 = par3World.getTypeId(par4, par5, par6);

        if (BlockMinecartTrack.d(var11))
        {
            if (!par3World.isStatic)
            {
                par3World.addEntity(new EntityMinecart(par3World, (double) ((float) par4 + 0.5F), (double) ((float) par5 + 0.5F), (double) ((float) par6 + 0.5F), this.a));
            }

            --par1ItemStack.count;
            return true;
        }
        else
        {
            return false;
        }
    }
}
