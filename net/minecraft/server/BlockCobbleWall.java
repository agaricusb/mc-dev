package net.minecraft.server;

public class BlockCobbleWall extends Block
{
    /** The types of the wall. */
    public static final String[] a = new String[] {"normal", "mossy"};

    public BlockCobbleWall(int par1, Block par2Block)
    {
        super(par1, par2Block.textureId, par2Block.material);
        this.c(par2Block.strength);
        this.b(par2Block.durability / 3.0F);
        this.a(par2Block.stepSound);
        this.a(CreativeModeTab.b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par2 == 1 ? Block.MOSSY_COBBLESTONE.textureId : super.a(par1);
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 32;
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
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return false;
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
        float var9 = 0.25F;
        float var10 = 0.75F;
        float var11 = 0.25F;
        float var12 = 0.75F;
        float var13 = 1.0F;

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

        if (var5 && var6 && !var7 && !var8)
        {
            var13 = 0.8125F;
            var9 = 0.3125F;
            var10 = 0.6875F;
        }
        else if (!var5 && !var6 && var7 && var8)
        {
            var13 = 0.8125F;
            var11 = 0.3125F;
            var12 = 0.6875F;
        }

        this.a(var9, 0.0F, var11, var10, var13, var12);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        this.updateShape(par1World, par2, par3, par4);
        this.maxY = 1.5D;
        return super.e(par1World, par2, par3, par4);
    }

    /**
     * Return whether an adjacent block can connect to a wall.
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

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int par1)
    {
        return par1;
    }
}
