package net.minecraft.server;

import java.util.Random;

public class BlockCocoa extends BlockDirectional
{
    public BlockCocoa(int par1)
    {
        super(par1, 168, Material.PLANT);
        this.b(true);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!this.d(par1World, par2, par3, par4))
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
        }
        else if (par1World.random.nextInt(5) == 0)
        {
            int var6 = par1World.getData(par2, par3, par4);
            int var7 = c(var6);

            if (var7 < 2)
            {
                ++var7;
                par1World.setData(par2, par3, par4, var7 << 2 | e(var6));
            }
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean d(World par1World, int par2, int par3, int par4)
    {
        int var5 = e(par1World.getData(par2, par3, par4));
        par2 += Direction.a[var5];
        par4 += Direction.b[var5];
        int var6 = par1World.getTypeId(par2, par3, par4);
        return var6 == Block.LOG.id && BlockLog.e(par1World.getData(par2, par3, par4)) == 3;
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 28;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return false;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        this.updateShape(par1World, par2, par3, par4);
        return super.e(par1World, par2, par3, par4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4);
        int var6 = e(var5);
        int var7 = c(var5);
        int var8 = 4 + var7 * 2;
        int var9 = 5 + var7 * 2;
        float var10 = (float)var8 / 2.0F;

        switch (var6)
        {
            case 0:
                this.a((8.0F - var10) / 16.0F, (12.0F - (float) var9) / 16.0F, (15.0F - (float) var8) / 16.0F, (8.0F + var10) / 16.0F, 0.75F, 0.9375F);
                break;

            case 1:
                this.a(0.0625F, (12.0F - (float) var9) / 16.0F, (8.0F - var10) / 16.0F, (1.0F + (float) var8) / 16.0F, 0.75F, (8.0F + var10) / 16.0F);
                break;

            case 2:
                this.a((8.0F - var10) / 16.0F, (12.0F - (float) var9) / 16.0F, 0.0625F, (8.0F + var10) / 16.0F, 0.75F, (1.0F + (float) var8) / 16.0F);
                break;

            case 3:
                this.a((15.0F - (float) var8) / 16.0F, (12.0F - (float) var9) / 16.0F, (8.0F - var10) / 16.0F, 0.9375F, 0.75F, (8.0F + var10) / 16.0F);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = ((MathHelper.floor((double) (par5EntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 3) + 0) % 4;
        par1World.setData(par2, par3, par4, var6);
    }

    public int getPlacedData(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        if (par5 == 1 || par5 == 0)
        {
            par5 = 2;
        }

        return Direction.f[Direction.e[par5]];
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!this.d(par1World, par2, par3, par4))
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
        }
    }

    public static int c(int par0)
    {
        return (par0 & 12) >> 2;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        int var8 = c(par5);
        byte var9 = 1;

        if (var8 >= 2)
        {
            var9 = 3;
        }

        for (int var10 = 0; var10 < var9; ++var10)
        {
            this.b(par1World, par2, par3, par4, new ItemStack(Item.INK_SACK, 1, 3));
        }
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDropData(World par1World, int par2, int par3, int par4)
    {
        return 3;
    }
}
