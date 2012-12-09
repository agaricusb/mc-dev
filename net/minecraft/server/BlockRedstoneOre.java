package net.minecraft.server;

import java.util.Random;

public class BlockRedstoneOre extends Block
{
    private boolean a;

    public BlockRedstoneOre(int par1, int par2, boolean par3)
    {
        super(par1, par2, Material.STONE);

        if (par3)
        {
            this.b(true);
        }

        this.a = par3;
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 30;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void attack(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer)
    {
        this.l(par1World, par2, par3, par4);
        super.attack(par1World, par2, par3, par4, par5EntityPlayer);
    }

    /**
     * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
     */
    public void b(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        this.l(par1World, par2, par3, par4);
        super.b(par1World, par2, par3, par4, par5Entity);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        this.l(par1World, par2, par3, par4);
        return super.interact(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    }

    /**
     * The redstone ore glows.
     */
    private void l(World par1World, int par2, int par3, int par4)
    {
        this.n(par1World, par2, par3, par4);

        if (this.id == Block.REDSTONE_ORE.id)
        {
            par1World.setTypeId(par2, par3, par4, Block.GLOWING_REDSTONE_ORE.id);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (this.id == Block.GLOWING_REDSTONE_ORE.id)
        {
            par1World.setTypeId(par2, par3, par4, Block.REDSTONE_ORE.id);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.REDSTONE.id;
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int getDropCount(int par1, Random par2Random)
    {
        return this.a(par2Random) + par2Random.nextInt(par1 + 1);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 4 + par1Random.nextInt(2);
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropNaturally(par1World, par2, par3, par4, par5, par6, par7);

        if (this.getDropType(par5, par1World.random, par7) != this.id)
        {
            int var8 = 1 + par1World.random.nextInt(5);
            this.f(par1World, par2, par3, par4, var8);
        }
    }

    /**
     * The redstone ore sparkles.
     */
    private void n(World par1World, int par2, int par3, int par4)
    {
        Random var5 = par1World.random;
        double var6 = 0.0625D;

        for (int var8 = 0; var8 < 6; ++var8)
        {
            double var9 = (double)((float)par2 + var5.nextFloat());
            double var11 = (double)((float)par3 + var5.nextFloat());
            double var13 = (double)((float)par4 + var5.nextFloat());

            if (var8 == 0 && !par1World.s(par2, par3 + 1, par4))
            {
                var11 = (double)(par3 + 1) + var6;
            }

            if (var8 == 1 && !par1World.s(par2, par3 - 1, par4))
            {
                var11 = (double)(par3 + 0) - var6;
            }

            if (var8 == 2 && !par1World.s(par2, par3, par4 + 1))
            {
                var13 = (double)(par4 + 1) + var6;
            }

            if (var8 == 3 && !par1World.s(par2, par3, par4 - 1))
            {
                var13 = (double)(par4 + 0) - var6;
            }

            if (var8 == 4 && !par1World.s(par2 + 1, par3, par4))
            {
                var9 = (double)(par2 + 1) + var6;
            }

            if (var8 == 5 && !par1World.s(par2 - 1, par3, par4))
            {
                var9 = (double)(par2 + 0) - var6;
            }

            if (var9 < (double)par2 || var9 > (double)(par2 + 1) || var11 < 0.0D || var11 > (double)(par3 + 1) || var13 < (double)par4 || var13 > (double)(par4 + 1))
            {
                par1World.addParticle("reddust", var9, var11, var13, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack f_(int par1)
    {
        return new ItemStack(Block.REDSTONE_ORE);
    }
}
