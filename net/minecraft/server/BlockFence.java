package net.minecraft.server;

public class BlockFence extends Block
{
    public BlockFence(int par1, int par2)
    {
        super(par1, par2, Material.WOOD);
        this.a(CreativeModeTab.c);
    }

    public BlockFence(int par1, int par2, Material par3Material)
    {
        super(par1, par2, par3Material);
        this.a(CreativeModeTab.c);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        boolean var5 = this.d(par1World, par2, par3, par4 - 1);
        boolean var6 = this.d(par1World, par2, par3, par4 + 1);
        boolean var7 = this.d(par1World, par2 - 1, par3, par4);
        boolean var8 = this.d(par1World, par2 + 1, par3, par4);
        float var9 = 0.375F;
        float var10 = 0.625F;
        float var11 = 0.375F;
        float var12 = 0.625F;

        if (var5)
        {
            var11 = 0.0F;
        }

        if (var6)
        {
            var12 = 1.0F;
        }

        if (var7)
        {
            var9 = 0.0F;
        }

        if (var8)
        {
            var10 = 1.0F;
        }

        return AxisAlignedBB.a().a((double) ((float) par2 + var9), (double) par3, (double) ((float) par4 + var11), (double) ((float) par2 + var10), (double) ((float) par3 + 1.5F), (double) ((float) par4 + var12));
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        boolean var5 = this.d(par1IBlockAccess, par2, par3, par4 - 1);
        boolean var6 = this.d(par1IBlockAccess, par2, par3, par4 + 1);
        boolean var7 = this.d(par1IBlockAccess, par2 - 1, par3, par4);
        boolean var8 = this.d(par1IBlockAccess, par2 + 1, par3, par4);
        float var9 = 0.375F;
        float var10 = 0.625F;
        float var11 = 0.375F;
        float var12 = 0.625F;

        if (var5)
        {
            var11 = 0.0F;
        }

        if (var6)
        {
            var12 = 1.0F;
        }

        if (var7)
        {
            var9 = 0.0F;
        }

        if (var8)
        {
            var10 = 1.0F;
        }

        this.a(var9, 0.0F, var11, var10, 1.0F, var12);
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

    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 11;
    }

    /**
     * Returns true if the specified block can be connected by a fence
     */
    public boolean d(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getTypeId(par2, par3, par4);

        if (var5 != this.id && var5 != Block.FENCE_GATE.id)
        {
            Block var6 = Block.byId[var5];
            return var6 != null && var6.material.k() && var6.b() ? var6.material != Material.PUMPKIN : false;
        }
        else
        {
            return true;
        }
    }

    public static boolean c(int par0)
    {
        return par0 == Block.FENCE.id || par0 == Block.NETHER_FENCE.id;
    }
}
