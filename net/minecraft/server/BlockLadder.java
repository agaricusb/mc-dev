package net.minecraft.server;

import java.util.Random;

public class BlockLadder extends Block
{
    protected BlockLadder(int par1, int par2)
    {
        super(par1, par2, Material.ORIENTABLE);
        this.a(CreativeModeTab.c);
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
        this.d(par1IBlockAccess.getData(par2, par3, par4));
    }

    /**
     * Update the ladder block bounds based on the given metadata value.
     */
    public void d(int par1)
    {
        float var3 = 0.125F;

        if (par1 == 2)
        {
            this.a(0.0F, 0.0F, 1.0F - var3, 1.0F, 1.0F, 1.0F);
        }

        if (par1 == 3)
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var3);
        }

        if (par1 == 4)
        {
            this.a(1.0F - var3, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

        if (par1 == 5)
        {
            this.a(0.0F, 0.0F, 0.0F, var3, 1.0F, 1.0F);
        }
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
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 8;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return par1World.t(par2 - 1, par3, par4) ? true : (par1World.t(par2 + 1, par3, par4) ? true : (par1World.t(par2, par3, par4 - 1) ? true : par1World.t(par2, par3, par4 + 1)));
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int getPlacedData(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int var10 = par9;

        if ((par9 == 0 || par5 == 2) && par1World.t(par2, par3, par4 + 1))
        {
            var10 = 2;
        }

        if ((var10 == 0 || par5 == 3) && par1World.t(par2, par3, par4 - 1))
        {
            var10 = 3;
        }

        if ((var10 == 0 || par5 == 4) && par1World.t(par2 + 1, par3, par4))
        {
            var10 = 4;
        }

        if ((var10 == 0 || par5 == 5) && par1World.t(par2 - 1, par3, par4))
        {
            var10 = 5;
        }

        return var10;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = par1World.getData(par2, par3, par4);
        boolean var7 = false;

        if (var6 == 2 && par1World.t(par2, par3, par4 + 1))
        {
            var7 = true;
        }

        if (var6 == 3 && par1World.t(par2, par3, par4 - 1))
        {
            var7 = true;
        }

        if (var6 == 4 && par1World.t(par2 + 1, par3, par4))
        {
            var7 = true;
        }

        if (var6 == 5 && par1World.t(par2 - 1, par3, par4))
        {
            var7 = true;
        }

        if (!var7)
        {
            this.c(par1World, par2, par3, par4, var6, 0);
            par1World.setTypeId(par2, par3, par4, 0);
        }

        super.doPhysics(par1World, par2, par3, par4, par5);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 1;
    }
}
