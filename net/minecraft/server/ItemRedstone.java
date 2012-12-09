package net.minecraft.server;

public class ItemRedstone extends Item
{
    public ItemRedstone(int par1)
    {
        super(par1);
        this.a(CreativeModeTab.d);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.getTypeId(par4, par5, par6) != Block.SNOW.id)
        {
            if (par7 == 0)
            {
                --par5;
            }

            if (par7 == 1)
            {
                ++par5;
            }

            if (par7 == 2)
            {
                --par6;
            }

            if (par7 == 3)
            {
                ++par6;
            }

            if (par7 == 4)
            {
                --par4;
            }

            if (par7 == 5)
            {
                ++par4;
            }

            if (!par3World.isEmpty(par4, par5, par6))
            {
                return false;
            }
        }

        if (!par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            if (Block.REDSTONE_WIRE.canPlace(par3World, par4, par5, par6))
            {
                --par1ItemStack.count;
                par3World.setTypeId(par4, par5, par6, Block.REDSTONE_WIRE.id);
            }

            return true;
        }
    }
}
