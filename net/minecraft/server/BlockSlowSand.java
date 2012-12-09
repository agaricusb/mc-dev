package net.minecraft.server;

public class BlockSlowSand extends Block
{
    public BlockSlowSand(int par1, int par2)
    {
        super(par1, par2, Material.SAND);
        this.a(CreativeModeTab.b);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        float var5 = 0.125F;
        return AxisAlignedBB.a().a((double) par2, (double) par3, (double) par4, (double) (par2 + 1), (double) ((float) (par3 + 1) - var5), (double) (par4 + 1));
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        par5Entity.motX *= 0.4D;
        par5Entity.motZ *= 0.4D;
    }
}
