package net.minecraft.server;

public class ItemSeedFood extends ItemFood
{
    /** Block ID of the crop this seed food should place. */
    private int b;

    /** Block ID of the soil this seed food should be planted on. */
    private int c;

    public ItemSeedFood(int par1, int par2, float par3, int par4, int par5)
    {
        super(par1, par2, par3, false);
        this.b = par4;
        this.c = par5;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else if (par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.a(par4, par5 + 1, par6, par7, par1ItemStack))
        {
            int var11 = par3World.getTypeId(par4, par5, par6);

            if (var11 == this.c && par3World.isEmpty(par4, par5 + 1, par6))
            {
                par3World.setTypeId(par4, par5 + 1, par6, this.b);
                --par1ItemStack.count;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}
