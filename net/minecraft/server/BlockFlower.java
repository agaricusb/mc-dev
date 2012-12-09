package net.minecraft.server;

import java.util.Random;

public class BlockFlower extends Block
{
    protected BlockFlower(int par1, int par2, Material par3Material)
    {
        super(par1, par3Material);
        this.textureId = par2;
        this.b(true);
        float var4 = 0.2F;
        this.a(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);
        this.a(CreativeModeTab.c);
    }

    protected BlockFlower(int par1, int par2)
    {
        this(par1, par2, Material.PLANT);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return super.canPlace(par1World, par2, par3, par4) && this.d_(par1World.getTypeId(par2, par3 - 1, par4));
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean d_(int par1)
    {
        return par1 == Block.GRASS.id || par1 == Block.DIRT.id || par1 == Block.SOIL.id;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        super.doPhysics(par1World, par2, par3, par4, par5);
        this.c(par1World, par2, par3, par4);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        this.c(par1World, par2, par3, par4);
    }

    protected final void c(World par1World, int par2, int par3, int par4)
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
        return (par1World.l(par2, par3, par4) >= 8 || par1World.k(par2, par3, par4)) && this.d_(par1World.getTypeId(par2, par3 - 1, par4));
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        return null;
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
        return 1;
    }
}
