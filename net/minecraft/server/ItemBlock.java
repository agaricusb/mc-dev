package net.minecraft.server;

public class ItemBlock extends Item
{
    /** The block ID of the Block associated with this ItemBlock */
    private int id;

    public ItemBlock(int par1)
    {
        super(par1);
        this.id = par1 + 256;
        this.c(Block.byId[par1 + 256].a(2));
    }

    /**
     * Returns the blockID for this Item
     */
    public int g()
    {
        return this.id;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int var11 = par3World.getTypeId(par4, par5, par6);

        if (var11 == Block.SNOW.id)
        {
            par7 = 1;
        }
        else if (var11 != Block.VINE.id && var11 != Block.LONG_GRASS.id && var11 != Block.DEAD_BUSH.id)
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
        }

        if (par1ItemStack.count == 0)
        {
            return false;
        }
        else if (!par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else if (par5 == 255 && Block.byId[this.id].material.isBuildable())
        {
            return false;
        }
        else if (par3World.mayPlace(this.id, par4, par5, par6, false, par7, par2EntityPlayer))
        {
            Block var12 = Block.byId[this.id];
            int var13 = this.filterData(par1ItemStack.getData());
            int var14 = Block.byId[this.id].getPlacedData(par3World, par4, par5, par6, par7, par8, par9, par10, var13);

            if (par3World.setTypeIdAndData(par4, par5, par6, this.id, var14))
            {
                if (par3World.getTypeId(par4, par5, par6) == this.id)
                {
                    Block.byId[this.id].postPlace(par3World, par4, par5, par6, par2EntityPlayer);
                    Block.byId[this.id].postPlace(par3World, par4, par5, par6, var14);
                }

                par3World.makeSound((double) ((float) par4 + 0.5F), (double) ((float) par5 + 0.5F), (double) ((float) par6 + 0.5F), var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume1() + 1.0F) / 2.0F, var12.stepSound.getVolume2() * 0.8F);
                --par1ItemStack.count;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public String d(ItemStack par1ItemStack)
    {
        return Block.byId[this.id].a();
    }

    public String getName()
    {
        return Block.byId[this.id].a();
    }
}
