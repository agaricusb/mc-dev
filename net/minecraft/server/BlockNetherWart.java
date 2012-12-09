package net.minecraft.server;

import java.util.Random;

public class BlockNetherWart extends BlockFlower
{
    protected BlockNetherWart(int par1)
    {
        super(par1, 226);
        this.b(true);
        float var2 = 0.5F;
        this.a(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.25F, 0.5F + var2);
        this.a((CreativeModeTab) null);
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean d_(int par1)
    {
        return par1 == Block.SOUL_SAND.id;
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean d(World par1World, int par2, int par3, int par4)
    {
        return this.d_(par1World.getTypeId(par2, par3 - 1, par4));
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int var6 = par1World.getData(par2, par3, par4);

        if (var6 < 3 && par5Random.nextInt(10) == 0)
        {
            ++var6;
            par1World.setData(par2, par3, par4, var6);
        }

        super.b(par1World, par2, par3, par4, par5Random);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par2 >= 3 ? this.textureId + 2 : (par2 > 0 ? this.textureId + 1 : this.textureId);
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 6;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!par1World.isStatic)
        {
            int var8 = 1;

            if (par5 >= 3)
            {
                var8 = 2 + par1World.random.nextInt(3);

                if (par7 > 0)
                {
                    var8 += par1World.random.nextInt(par7 + 1);
                }
            }

            for (int var9 = 0; var9 < var8; ++var9)
            {
                this.b(par1World, par2, par3, par4, new ItemStack(Item.NETHER_STALK));
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 0;
    }
}
