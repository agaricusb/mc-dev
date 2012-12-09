package net.minecraft.server;

import java.util.Random;

public class BlockCrops extends BlockFlower
{
    protected BlockCrops(int par1, int par2)
    {
        super(par1, par2);
        this.textureId = par2;
        this.b(true);
        float var3 = 0.5F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.25F, 0.5F + var3);
        this.a((CreativeModeTab) null);
        this.c(0.0F);
        this.a(g);
        this.D();
        this.r();
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean d_(int par1)
    {
        return par1 == Block.SOIL.id;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.b(par1World, par2, par3, par4, par5Random);

        if (par1World.getLightLevel(par2, par3 + 1, par4) >= 9)
        {
            int var6 = par1World.getData(par2, par3, par4);

            if (var6 < 7)
            {
                float var7 = this.l(par1World, par2, par3, par4);

                if (par5Random.nextInt((int)(25.0F / var7) + 1) == 0)
                {
                    ++var6;
                    par1World.setData(par2, par3, par4, var6);
                }
            }
        }
    }

    /**
     * Apply bonemeal to the crops.
     */
    public void c_(World par1World, int par2, int par3, int par4)
    {
        par1World.setData(par2, par3, par4, 7);
    }

    /**
     * Gets the growth rate for the crop. Setup to encourage rows by halving growth rate if there is diagonals, crops on
     * different sides that aren't opposing, and by adding growth for every crop next to this one (and for crop below
     * this one). Args: x, y, z
     */
    private float l(World par1World, int par2, int par3, int par4)
    {
        float var5 = 1.0F;
        int var6 = par1World.getTypeId(par2, par3, par4 - 1);
        int var7 = par1World.getTypeId(par2, par3, par4 + 1);
        int var8 = par1World.getTypeId(par2 - 1, par3, par4);
        int var9 = par1World.getTypeId(par2 + 1, par3, par4);
        int var10 = par1World.getTypeId(par2 - 1, par3, par4 - 1);
        int var11 = par1World.getTypeId(par2 + 1, par3, par4 - 1);
        int var12 = par1World.getTypeId(par2 + 1, par3, par4 + 1);
        int var13 = par1World.getTypeId(par2 - 1, par3, par4 + 1);
        boolean var14 = var8 == this.id || var9 == this.id;
        boolean var15 = var6 == this.id || var7 == this.id;
        boolean var16 = var10 == this.id || var11 == this.id || var12 == this.id || var13 == this.id;

        for (int var17 = par2 - 1; var17 <= par2 + 1; ++var17)
        {
            for (int var18 = par4 - 1; var18 <= par4 + 1; ++var18)
            {
                int var19 = par1World.getTypeId(var17, par3 - 1, var18);
                float var20 = 0.0F;

                if (var19 == Block.SOIL.id)
                {
                    var20 = 1.0F;

                    if (par1World.getData(var17, par3 - 1, var18) > 0)
                    {
                        var20 = 3.0F;
                    }
                }

                if (var17 != par2 || var18 != par4)
                {
                    var20 /= 4.0F;
                }

                var5 += var20;
            }
        }

        if (var16 || var14 && var15)
        {
            var5 /= 2.0F;
        }

        return var5;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        if (par2 < 0)
        {
            par2 = 7;
        }

        return this.textureId + par2;
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 6;
    }

    /**
     * Generate a seed ItemStack for this crop.
     */
    protected int h()
    {
        return Item.SEEDS.id;
    }

    /**
     * Generate a crop produce ItemStack for this crop.
     */
    protected int j()
    {
        return Item.WHEAT.id;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropNaturally(par1World, par2, par3, par4, par5, par6, 0);

        if (!par1World.isStatic)
        {
            if (par5 >= 7)
            {
                int var8 = 3 + par7;

                for (int var9 = 0; var9 < var8; ++var9)
                {
                    if (par1World.random.nextInt(15) <= par5)
                    {
                        this.b(par1World, par2, par3, par4, new ItemStack(this.h(), 1, 0));
                    }
                }
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return par1 == 7 ? this.j() : this.h();
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 1;
    }
}
