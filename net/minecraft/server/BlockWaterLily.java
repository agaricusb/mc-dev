package net.minecraft.server;

import java.util.List;

public class BlockWaterLily extends BlockFlower
{
    protected BlockWaterLily(int par1, int par2)
    {
        super(par1, par2);
        float var3 = 0.5F;
        float var4 = 0.015625F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
        this.a(CreativeModeTab.c);
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 23;
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        if (par7Entity == null || !(par7Entity instanceof EntityBoat))
        {
            super.a(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.a().a((double) par2 + this.minX, (double) par3 + this.minY, (double) par4 + this.minZ, (double) par2 + this.maxX, (double) par3 + this.maxY, (double) par4 + this.maxZ);
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean d_(int par1)
    {
        return par1 == Block.STATIONARY_WATER.id;
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean d(World par1World, int par2, int par3, int par4)
    {
        return par3 >= 0 && par3 < 256 ? par1World.getMaterial(par2, par3 - 1, par4) == Material.WATER && par1World.getData(par2, par3 - 1, par4) == 0 : false;
    }
}
