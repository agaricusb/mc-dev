package net.minecraft.server;

import java.util.Random;

public class BlockStem extends BlockFlower
{
    /** Defines if it is a Melon or a Pumpkin that the stem is producing. */
    private Block blockFruit;

    protected BlockStem(int par1, Block par2Block)
    {
        super(par1, 111);
        this.blockFruit = par2Block;
        this.b(true);
        float var3 = 0.125F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.25F, 0.5F + var3);
        this.a((CreativeModeTab) null);
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
            float var6 = this.n(par1World, par2, par3, par4);

            if (par5Random.nextInt((int)(25.0F / var6) + 1) == 0)
            {
                int var7 = par1World.getData(par2, par3, par4);

                if (var7 < 7)
                {
                    ++var7;
                    par1World.setData(par2, par3, par4, var7);
                }
                else
                {
                    if (par1World.getTypeId(par2 - 1, par3, par4) == this.blockFruit.id)
                    {
                        return;
                    }

                    if (par1World.getTypeId(par2 + 1, par3, par4) == this.blockFruit.id)
                    {
                        return;
                    }

                    if (par1World.getTypeId(par2, par3, par4 - 1) == this.blockFruit.id)
                    {
                        return;
                    }

                    if (par1World.getTypeId(par2, par3, par4 + 1) == this.blockFruit.id)
                    {
                        return;
                    }

                    int var8 = par5Random.nextInt(4);
                    int var9 = par2;
                    int var10 = par4;

                    if (var8 == 0)
                    {
                        var9 = par2 - 1;
                    }

                    if (var8 == 1)
                    {
                        ++var9;
                    }

                    if (var8 == 2)
                    {
                        var10 = par4 - 1;
                    }

                    if (var8 == 3)
                    {
                        ++var10;
                    }

                    int var11 = par1World.getTypeId(var9, par3 - 1, var10);

                    if (par1World.getTypeId(var9, par3, var10) == 0 && (var11 == Block.SOIL.id || var11 == Block.DIRT.id || var11 == Block.GRASS.id))
                    {
                        par1World.setTypeId(var9, par3, var10, this.blockFruit.id);
                    }
                }
            }
        }
    }

    public void l(World par1World, int par2, int par3, int par4)
    {
        par1World.setData(par2, par3, par4, 7);
    }

    private float n(World par1World, int par2, int par3, int par4)
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
        return this.textureId;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f()
    {
        float var1 = 0.125F;
        this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.maxY = (double)((float)(par1IBlockAccess.getData(par2, par3, par4) * 2 + 2) / 16.0F);
        float var5 = 0.125F;
        this.a(0.5F - var5, 0.0F, 0.5F - var5, 0.5F + var5, (float) this.maxY, 0.5F + var5);
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 19;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropNaturally(par1World, par2, par3, par4, par5, par6, par7);

        if (!par1World.isStatic)
        {
            Item var8 = null;

            if (this.blockFruit == Block.PUMPKIN)
            {
                var8 = Item.PUMPKIN_SEEDS;
            }

            if (this.blockFruit == Block.MELON)
            {
                var8 = Item.MELON_SEEDS;
            }

            for (int var9 = 0; var9 < 3; ++var9)
            {
                if (par1World.random.nextInt(15) <= par5)
                {
                    this.b(par1World, par2, par3, par4, new ItemStack(var8));
                }
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return -1;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 1;
    }
}
