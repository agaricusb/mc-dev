package net.minecraft.server;

public class ItemSeeds extends Item
{
    /**
     * The type of block this seed turns into (wheat or pumpkin stems for instance)
     */
    private int id;

    /** BlockID of the block the seeds can be planted on. */
    private int b;

    public ItemSeeds(int par1, int par2, int par3)
    {
        super(par1);
        this.id = par2;
        this.b = par3;
        this.a(CreativeModeTab.l);
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

            if (var11 == this.b && par3World.isEmpty(par4, par5 + 1, par6))
            {
                par3World.setTypeId(par4, par5 + 1, par6, this.id);
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
