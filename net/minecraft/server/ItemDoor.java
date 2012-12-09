package net.minecraft.server;

public class ItemDoor extends Item
{
    private Material a;

    public ItemDoor(int par1, Material par2Material)
    {
        super(par1);
        this.a = par2Material;
        this.maxStackSize = 1;
        this.a(CreativeModeTab.d);
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
        else
        {
            ++par5;
            Block var11;

            if (this.a == Material.WOOD)
            {
                var11 = Block.WOODEN_DOOR;
            }
            else
            {
                var11 = Block.IRON_DOOR_BLOCK;
            }

            if (par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.a(par4, par5 + 1, par6, par7, par1ItemStack))
            {
                if (!var11.canPlace(par3World, par4, par5, par6))
                {
                    return false;
                }
                else
                {
                    int var12 = MathHelper.floor((double) ((par2EntityPlayer.yaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    place(par3World, par4, par5, par6, var12, var11);
                    --par1ItemStack.count;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    public static void place(World par0World, int par1, int par2, int par3, int par4, Block par5Block)
    {
        byte var6 = 0;
        byte var7 = 0;

        if (par4 == 0)
        {
            var7 = 1;
        }

        if (par4 == 1)
        {
            var6 = -1;
        }

        if (par4 == 2)
        {
            var7 = -1;
        }

        if (par4 == 3)
        {
            var6 = 1;
        }

        int var8 = (par0World.t(par1 - var6, par2, par3 - var7) ? 1 : 0) + (par0World.t(par1 - var6, par2 + 1, par3 - var7) ? 1 : 0);
        int var9 = (par0World.t(par1 + var6, par2, par3 + var7) ? 1 : 0) + (par0World.t(par1 + var6, par2 + 1, par3 + var7) ? 1 : 0);
        boolean var10 = par0World.getTypeId(par1 - var6, par2, par3 - var7) == par5Block.id || par0World.getTypeId(par1 - var6, par2 + 1, par3 - var7) == par5Block.id;
        boolean var11 = par0World.getTypeId(par1 + var6, par2, par3 + var7) == par5Block.id || par0World.getTypeId(par1 + var6, par2 + 1, par3 + var7) == par5Block.id;
        boolean var12 = false;

        if (var10 && !var11)
        {
            var12 = true;
        }
        else if (var9 > var8)
        {
            var12 = true;
        }

        par0World.suppressPhysics = true;
        par0World.setTypeIdAndData(par1, par2, par3, par5Block.id, par4);
        par0World.setTypeIdAndData(par1, par2 + 1, par3, par5Block.id, 8 | (var12 ? 1 : 0));
        par0World.suppressPhysics = false;
        par0World.applyPhysics(par1, par2, par3, par5Block.id);
        par0World.applyPhysics(par1, par2 + 1, par3, par5Block.id);
    }
}
