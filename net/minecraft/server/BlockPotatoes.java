package net.minecraft.server;

public class BlockPotatoes extends BlockCrops
{
    public BlockPotatoes(int par1)
    {
        super(par1, 200);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        if (par2 < 7)
        {
            if (par2 == 6)
            {
                par2 = 5;
            }

            return this.textureId + (par2 >> 1);
        }
        else
        {
            return this.textureId + 4;
        }
    }

    /**
     * Generate a seed ItemStack for this crop.
     */
    protected int h()
    {
        return Item.POTATO.id;
    }

    /**
     * Generate a crop produce ItemStack for this crop.
     */
    protected int j()
    {
        return Item.POTATO.id;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropNaturally(par1World, par2, par3, par4, par5, par6, 0);

        if (!par1World.isStatic)
        {
            if (par5 >= 7 && par1World.random.nextInt(50) == 0)
            {
                this.b(par1World, par2, par3, par4, new ItemStack(Item.POTATO_POISON));
            }
        }
    }
}
