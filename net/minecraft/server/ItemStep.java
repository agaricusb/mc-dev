package net.minecraft.server;

public class ItemStep extends ItemBlock
{
    private final boolean a;

    /** Instance of BlockHalfSlab. */
    private final BlockStepAbstract b;

    /** Instance of BlockHalfSlab. */
    private final BlockStepAbstract c;

    public ItemStep(int par1, BlockStepAbstract par2BlockHalfSlab, BlockStepAbstract par3BlockHalfSlab, boolean par4)
    {
        super(par1);
        this.b = par2BlockHalfSlab;
        this.c = par3BlockHalfSlab;
        this.a = par4;
        this.setMaxDurability(0);
        this.a(true);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int filterData(int par1)
    {
        return par1;
    }

    public String c_(ItemStack par1ItemStack)
    {
        return this.b.d(par1ItemStack.getData());
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (this.a)
        {
            return super.interactWith(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        }
        else if (par1ItemStack.count == 0)
        {
            return false;
        }
        else if (!par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            int var11 = par3World.getTypeId(par4, par5, par6);
            int var12 = par3World.getData(par4, par5, par6);
            int var13 = var12 & 7;
            boolean var14 = (var12 & 8) != 0;

            if ((par7 == 1 && !var14 || par7 == 0 && var14) && var11 == this.b.id && var13 == par1ItemStack.getData())
            {
                if (par3World.b(this.c.e(par3World, par4, par5, par6)) && par3World.setTypeIdAndData(par4, par5, par6, this.c.id, var13))
                {
                    par3World.makeSound((double) ((float) par4 + 0.5F), (double) ((float) par5 + 0.5F), (double) ((float) par6 + 0.5F), this.c.stepSound.getPlaceSound(), (this.c.stepSound.getVolume1() + 1.0F) / 2.0F, this.c.stepSound.getVolume2() * 0.8F);
                    --par1ItemStack.count;
                }

                return true;
            }
            else
            {
                return this.a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7) ? true : super.interactWith(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
            }
        }
    }

    private boolean a(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
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

        int var8 = par3World.getTypeId(par4, par5, par6);
        int var9 = par3World.getData(par4, par5, par6);
        int var10 = var9 & 7;

        if (var8 == this.b.id && var10 == par1ItemStack.getData())
        {
            if (par3World.b(this.c.e(par3World, par4, par5, par6)) && par3World.setTypeIdAndData(par4, par5, par6, this.c.id, var10))
            {
                par3World.makeSound((double) ((float) par4 + 0.5F), (double) ((float) par5 + 0.5F), (double) ((float) par6 + 0.5F), this.c.stepSound.getPlaceSound(), (this.c.stepSound.getVolume1() + 1.0F) / 2.0F, this.c.stepSound.getVolume2() * 0.8F);
                --par1ItemStack.count;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
