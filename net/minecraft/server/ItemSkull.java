package net.minecraft.server;

public class ItemSkull extends Item
{
    private static final String[] a = new String[] {"skeleton", "wither", "zombie", "char", "creeper"};
    private static final int[] b = new int[] {224, 225, 226, 227, 228};

    public ItemSkull(int par1)
    {
        super(par1);
        this.a(CreativeModeTab.c);
        this.setMaxDurability(0);
        this.a(true);
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
            else if (!Block.SKULL.canPlace(par3World, par4, par5, par6))
            {
                return false;
            }
            else
            {
                par3World.setTypeIdAndData(par4, par5, par6, Block.SKULL.id, par7);
                int var11 = 0;

                if (par7 == 1)
                {
                    var11 = MathHelper.floor((double) (par2EntityPlayer.yaw * 16.0F / 360.0F) + 0.5D) & 15;
                }

                TileEntity var12 = par3World.getTileEntity(par4, par5, par6);

                if (var12 != null && var12 instanceof TileEntitySkull)
                {
                    String var13 = "";

                    if (par1ItemStack.hasTag() && par1ItemStack.getTag().hasKey("SkullOwner"))
                    {
                        var13 = par1ItemStack.getTag().getString("SkullOwner");
                    }

                    ((TileEntitySkull)var12).setSkullType(par1ItemStack.getData(), var13);
                    ((TileEntitySkull)var12).setRotation(var11);
                    ((BlockSkull) Block.SKULL).a(par3World, par4, par5, par6, (TileEntitySkull) var12);
                }

                --par1ItemStack.count;
                return true;
            }
        }
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int filterData(int par1)
    {
        return par1;
    }

    public String d(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.getData();

        if (var2 < 0 || var2 >= a.length)
        {
            var2 = 0;
        }

        return super.getName() + "." + a[var2];
    }

    public String l(ItemStack par1ItemStack)
    {
        return par1ItemStack.getData() == 3 && par1ItemStack.hasTag() && par1ItemStack.getTag().hasKey("SkullOwner") ? LocaleI18n.get("item.skull.player.name", new Object[]{par1ItemStack.getTag().getString("SkullOwner")}): super.l(par1ItemStack);
    }
}
