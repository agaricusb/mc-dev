package net.minecraft.server;

public class ItemBed extends Item
{
    public ItemBed(int par1)
    {
        super(par1);
        this.a(CreativeModeTab.c);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isStatic)
        {
            return true;
        }
        else if (par7 != 1)
        {
            return false;
        }
        else
        {
            ++par5;
            BlockBed var11 = (BlockBed) Block.BED;
            int var12 = MathHelper.floor((double) (par2EntityPlayer.yaw * 4.0F / 360.0F) + 0.5D) & 3;
            byte var13 = 0;
            byte var14 = 0;

            if (var12 == 0)
            {
                var14 = 1;
            }

            if (var12 == 1)
            {
                var13 = -1;
            }

            if (var12 == 2)
            {
                var14 = -1;
            }

            if (var12 == 3)
            {
                var13 = 1;
            }

            if (par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.a(par4 + var13, par5, par6 + var14, par7, par1ItemStack))
            {
                if (par3World.isEmpty(par4, par5, par6) && par3World.isEmpty(par4 + var13, par5, par6 + var14) && par3World.v(par4, par5 - 1, par6) && par3World.v(par4 + var13, par5 - 1, par6 + var14))
                {
                    par3World.setTypeIdAndData(par4, par5, par6, var11.id, var12);

                    if (par3World.getTypeId(par4, par5, par6) == var11.id)
                    {
                        par3World.setTypeIdAndData(par4 + var13, par5, par6 + var14, var11.id, var12 + 8);
                    }

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
}
