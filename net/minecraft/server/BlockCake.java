package net.minecraft.server;

import java.util.Random;

public class BlockCake extends Block
{
    protected BlockCake(int par1, int par2)
    {
        super(par1, par2, Material.CAKE);
        this.b(true);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4);
        float var6 = 0.0625F;
        float var7 = (float)(1 + var5 * 2) / 16.0F;
        float var8 = 0.5F;
        this.a(var7, 0.0F, var6, 1.0F - var6, var8, 1.0F - var6);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void f()
    {
        float var1 = 0.0625F;
        float var2 = 0.5F;
        this.a(var1, 0.0F, var1, 1.0F - var1, var2, 1.0F - var1);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getData(par2, par3, par4);
        float var6 = 0.0625F;
        float var7 = (float)(1 + var5 * 2) / 16.0F;
        float var8 = 0.5F;
        return AxisAlignedBB.a().a((double) ((float) par2 + var7), (double) par3, (double) ((float) par4 + var6), (double) ((float) (par2 + 1) - var6), (double) ((float) par3 + var8 - var6), (double) ((float) (par4 + 1) - var6));
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par1 == 1 ? this.textureId : (par1 == 0 ? this.textureId + 3 : (par2 > 0 && par1 == 4 ? this.textureId + 2 : this.textureId + 1));
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 == 1 ? this.textureId : (par1 == 0 ? this.textureId + 3 : this.textureId + 1);
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
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        this.b(par1World, par2, par3, par4, par5EntityPlayer);
        return true;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void attack(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer)
    {
        this.b(par1World, par2, par3, par4, par5EntityPlayer);
    }

    /**
     * Heals the player and removes a slice from the cake.
     */
    private void b(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer)
    {
        if (par5EntityPlayer.g(false))
        {
            par5EntityPlayer.getFoodData().eat(2, 0.1F);
            int var6 = par1World.getData(par2, par3, par4) + 1;

            if (var6 >= 6)
            {
                par1World.setTypeId(par2, par3, par4, 0);
            }
            else
            {
                par1World.setData(par2, par3, par4, var6);
                par1World.j(par2, par3, par4);
            }
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return !super.canPlace(par1World, par2, par3, par4) ? false : this.d(par1World, par2, par3, par4);
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

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean d(World par1World, int par2, int par3, int par4)
    {
        return par1World.getMaterial(par2, par3 - 1, par4).isBuildable();
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return 0;
    }
}
