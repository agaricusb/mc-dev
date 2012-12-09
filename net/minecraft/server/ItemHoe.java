package net.minecraft.server;

public class ItemHoe extends Item
{
    protected EnumToolMaterial a;

    public ItemHoe(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1);
        this.a = par2EnumToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDurability(par2EnumToolMaterial.a());
        this.a(CreativeModeTab.i);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (!par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            int var11 = par3World.getTypeId(par4, par5, par6);
            int var12 = par3World.getTypeId(par4, par5 + 1, par6);

            if ((par7 == 0 || var12 != 0 || var11 != Block.GRASS.id) && var11 != Block.DIRT.id)
            {
                return false;
            }
            else
            {
                Block var13 = Block.SOIL;
                par3World.makeSound((double) ((float) par4 + 0.5F), (double) ((float) par5 + 0.5F), (double) ((float) par6 + 0.5F), var13.stepSound.getStepSound(), (var13.stepSound.getVolume1() + 1.0F) / 2.0F, var13.stepSound.getVolume2() * 0.8F);

                if (par3World.isStatic)
                {
                    return true;
                }
                else
                {
                    par3World.setTypeId(par4, par5, par6, var13.id);
                    par1ItemStack.damage(1, par2EntityPlayer);
                    return true;
                }
            }
        }
    }

    public String g()
    {
        return this.a.toString();
    }
}
