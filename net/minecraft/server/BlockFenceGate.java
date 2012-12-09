package net.minecraft.server;

public class BlockFenceGate extends BlockDirectional
{
    public BlockFenceGate(int par1, int par2)
    {
        super(par1, par2, Material.WOOD);
        this.a(CreativeModeTab.d);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return !par1World.getMaterial(par2, par3 - 1, par4).isBuildable() ? false : super.canPlace(par1World, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getData(par2, par3, par4);
        return c(var5) ? null : (var5 != 2 && var5 != 0 ? AxisAlignedBB.a().a((double) ((float) par2 + 0.375F), (double) par3, (double) par4, (double) ((float) par2 + 0.625F), (double) ((float) par3 + 1.5F), (double) (par4 + 1)) : AxisAlignedBB.a().a((double) par2, (double) par3, (double) ((float) par4 + 0.375F), (double) (par2 + 1), (double) ((float) par3 + 1.5F), (double) ((float) par4 + 0.625F)));
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = e(par1IBlockAccess.getData(par2, par3, par4));

        if (var5 != 2 && var5 != 0)
        {
            this.a(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
        }
        else
        {
            this.a(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
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

    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return c(par1IBlockAccess.getData(par2, par3, par4));
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 21;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void postPlace(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = (MathHelper.floor((double) (par5EntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
        par1World.setData(par2, par3, par4, var6);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        int var10 = par1World.getData(par2, par3, par4);

        if (c(var10))
        {
            par1World.setData(par2, par3, par4, var10 & -5);
        }
        else
        {
            int var11 = (MathHelper.floor((double) (par5EntityPlayer.yaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
            int var12 = e(var10);

            if (var12 == (var11 + 2) % 4)
            {
                var10 = var11;
            }

            par1World.setData(par2, par3, par4, var10 | 4);
        }

        par1World.a(par5EntityPlayer, 1003, par2, par3, par4, 0);
        return true;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isStatic)
        {
            int var6 = par1World.getData(par2, par3, par4);
            boolean var7 = par1World.isBlockIndirectlyPowered(par2, par3, par4);

            if (var7 || par5 > 0 && Block.byId[par5].isPowerSource() || par5 == 0)
            {
                if (var7 && !c(var6))
                {
                    par1World.setData(par2, par3, par4, var6 | 4);
                    par1World.a((EntityHuman) null, 1003, par2, par3, par4, 0);
                }
                else if (!var7 && c(var6))
                {
                    par1World.setData(par2, par3, par4, var6 & -5);
                    par1World.a((EntityHuman) null, 1003, par2, par3, par4, 0);
                }
            }
        }
    }

    /**
     * Returns if the fence gate is open according to its metadata.
     */
    public static boolean c(int par0)
    {
        return (par0 & 4) != 0;
    }
}
