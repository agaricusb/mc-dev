package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class BlockEnderPortal extends BlockContainer
{
    /**
     * true if the enderdragon has been killed - allows end portal blocks to be created in the end
     */
    public static boolean a = false;

    protected BlockEnderPortal(int par1, Material par2Material)
    {
        super(par1, 0, par2Material);
        this.a(1.0F);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World par1World)
    {
        return new TileEntityEnderPortal();
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        float var5 = 0.0625F;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, var5, 1.0F);
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void a(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {}

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
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 0;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity.vehicle == null && par5Entity.passenger == null && !par1World.isStatic)
        {
            par5Entity.b(1);
        }
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return -1;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        if (!a)
        {
            if (par1World.worldProvider.dimension != 0)
            {
                par1World.setTypeId(par2, par3, par4, 0);
            }
        }
    }
}
