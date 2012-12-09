package net.minecraft.server;

public class ItemHanging extends Item
{
    private final Class a;

    public ItemHanging(int par1, Class par2Class)
    {
        super(par1);
        this.a = par2Class;
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
        else if (par7 == 1)
        {
            return false;
        }
        else
        {
            int var11 = Direction.e[par7];
            EntityHanging var12 = this.a(par3World, par4, par5, par6, var11);

            if (!par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack))
            {
                return false;
            }
            else
            {
                if (var12 != null && var12.survives())
                {
                    if (!par3World.isStatic)
                    {
                        par3World.addEntity(var12);
                    }

                    --par1ItemStack.count;
                }

                return true;
            }
        }
    }

    /**
     * Create the hanging entity associated to this item.
     */
    private EntityHanging a(World par1World, int par2, int par3, int par4, int par5)
    {
        return (EntityHanging)(this.a == EntityPainting.class ? new EntityPainting(par1World, par2, par3, par4, par5) : (this.a == EntityItemFrame.class ? new EntityItemFrame(par1World, par2, par3, par4, par5) : null));
    }
}
