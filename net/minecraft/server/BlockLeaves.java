package net.minecraft.server;

import java.util.Random;

public class BlockLeaves extends BlockTransparant
{
    /**
     * The base index in terrain.png corresponding to the fancy version of the leaf texture. This is stored so we can
     * switch the displayed version between fancy and fast graphics (fast is this index + 1).
     */
    private int cD;
    public static final String[] a = new String[] {"oak", "spruce", "birch", "jungle"};
    int[] b;

    protected BlockLeaves(int par1, int par2)
    {
        super(par1, par2, Material.LEAVES, false);
        this.cD = par2;
        this.b(true);
        this.a(CreativeModeTab.c);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        byte var7 = 1;
        int var8 = var7 + 1;

        if (par1World.d(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
        {
            for (int var9 = -var7; var9 <= var7; ++var9)
            {
                for (int var10 = -var7; var10 <= var7; ++var10)
                {
                    for (int var11 = -var7; var11 <= var7; ++var11)
                    {
                        int var12 = par1World.getTypeId(par2 + var9, par3 + var10, par4 + var11);

                        if (var12 == Block.LEAVES.id)
                        {
                            int var13 = par1World.getData(par2 + var9, par3 + var10, par4 + var11);
                            par1World.setRawData(par2 + var9, par3 + var10, par4 + var11, var13 | 8);
                        }
                    }
                }
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isStatic)
        {
            int var6 = par1World.getData(par2, par3, par4);

            if ((var6 & 8) != 0 && (var6 & 4) == 0)
            {
                byte var7 = 4;
                int var8 = var7 + 1;
                byte var9 = 32;
                int var10 = var9 * var9;
                int var11 = var9 / 2;

                if (this.b == null)
                {
                    this.b = new int[var9 * var9 * var9];
                }

                int var12;

                if (par1World.d(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
                {
                    int var13;
                    int var14;
                    int var15;

                    for (var12 = -var7; var12 <= var7; ++var12)
                    {
                        for (var13 = -var7; var13 <= var7; ++var13)
                        {
                            for (var14 = -var7; var14 <= var7; ++var14)
                            {
                                var15 = par1World.getTypeId(par2 + var12, par3 + var13, par4 + var14);

                                if (var15 == Block.LOG.id)
                                {
                                    this.b[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
                                }
                                else if (var15 == Block.LEAVES.id)
                                {
                                    this.b[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
                                }
                                else
                                {
                                    this.b[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
                                }
                            }
                        }
                    }

                    for (var12 = 1; var12 <= 4; ++var12)
                    {
                        for (var13 = -var7; var13 <= var7; ++var13)
                        {
                            for (var14 = -var7; var14 <= var7; ++var14)
                            {
                                for (var15 = -var7; var15 <= var7; ++var15)
                                {
                                    if (this.b[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1)
                                    {
                                        if (this.b[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
                                        {
                                            this.b[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.b[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
                                        {
                                            this.b[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.b[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2)
                                        {
                                            this.b[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.b[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2)
                                        {
                                            this.b[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.b[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2)
                                        {
                                            this.b[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;
                                        }

                                        if (this.b[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2)
                                        {
                                            this.b[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                var12 = this.b[var11 * var10 + var11 * var9 + var11];

                if (var12 >= 0)
                {
                    par1World.setRawData(par2, par3, par4, var6 & -9);
                }
                else
                {
                    this.l(par1World, par2, par3, par4);
                }
            }
        }
    }

    private void l(World par1World, int par2, int par3, int par4)
    {
        this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
        par1World.setTypeId(par2, par3, par4, 0);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return par1Random.nextInt(20) == 0 ? 1 : 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.SAPLING.id;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!par1World.isStatic)
        {
            byte var8 = 20;

            if ((par5 & 3) == 3)
            {
                var8 = 40;
            }

            if (par1World.random.nextInt(var8) == 0)
            {
                int var9 = this.getDropType(par5, par1World.random, par7);
                this.b(par1World, par2, par3, par4, new ItemStack(var9, 1, this.getDropData(par5)));
            }

            if ((par5 & 3) == 0 && par1World.random.nextInt(200) == 0)
            {
                this.b(par1World, par2, par3, par4, new ItemStack(Item.APPLE, 1, 0));
            }
        }
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void a(World par1World, EntityHuman par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        if (!par1World.isStatic && par2EntityPlayer.bT() != null && par2EntityPlayer.bT().id == Item.SHEARS.id)
        {
            par2EntityPlayer.a(StatisticList.C[this.id], 1);
            this.b(par1World, par3, par4, par5, new ItemStack(Block.LEAVES.id, 1, par6 & 3));
        }
        else
        {
            super.a(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int par1)
    {
        return par1 & 3;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return !this.c;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return (par2 & 3) == 1 ? this.textureId + 80 : ((par2 & 3) == 3 ? this.textureId + 144 : this.textureId);
    }
}
