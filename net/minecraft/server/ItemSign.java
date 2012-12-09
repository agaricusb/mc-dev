package net.minecraft.server;

public class ItemSign extends Item
{
    public ItemSign(int par1)
    {
        super(par1);
        this.maxStackSize = 16;
        this.a(CreativeModeTab.c);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 == 0)
        {
            return false;
        }
        else if (!par3World.getMaterial(par4, par5, par6).isBuildable())
        {
            return false;
        }
        else
        {
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

            if (!par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack))
            {
                return false;
            }
            else if (!Block.SIGN_POST.canPlace(par3World, par4, par5, par6))
            {
                return false;
            }
            else
            {
                if (par7 == 1)
                {
                    int var11 = MathHelper.floor((double) ((par2EntityPlayer.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                    par3World.setTypeIdAndData(par4, par5, par6, Block.SIGN_POST.id, var11);
                }
                else
                {
                    par3World.setTypeIdAndData(par4, par5, par6, Block.WALL_SIGN.id, par7);
                }

                --par1ItemStack.count;
                TileEntitySign var12 = (TileEntitySign)par3World.getTileEntity(par4, par5, par6);

                if (var12 != null)
                {
                    par2EntityPlayer.a(var12);
                }

                return true;
            }
        }
    }
}
