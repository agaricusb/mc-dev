package net.minecraft.server;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer
{
    public BlockPistonMoving(int par1)
    {
        super(par1, Material.PISTON);
        this.c(-1.0F);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World par1World)
    {
        return null;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4) {}

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        TileEntity var7 = par1World.getTileEntity(par2, par3, par4);

        if (var7 instanceof TileEntityPiston)
        {
            ((TileEntityPiston)var7).f();
        }
        else
        {
            super.remove(par1World, par2, par3, par4, par5, par6);
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return false;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4, int par5)
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int d()
    {
        return -1;
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
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (!par1World.isStatic && par1World.getTileEntity(par2, par3, par4) == null)
        {
            par1World.setTypeId(par2, par3, par4, 0);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!par1World.isStatic)
        {
            TileEntityPiston var8 = this.d((IBlockAccess) par1World, par2, par3, par4);

            if (var8 != null)
            {
                Block.byId[var8.a()].c(par1World, par2, par3, par4, var8.p(), 0);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isStatic && par1World.getTileEntity(par2, par3, par4) == null)
        {
            ;
        }
    }

    /**
     * gets a new TileEntityPiston created with the arguments provided.
     */
    public static TileEntity a(int par0, int par1, int par2, boolean par3, boolean par4)
    {
        return new TileEntityPiston(par0, par1, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        TileEntityPiston var5 = this.d((IBlockAccess) par1World, par2, par3, par4);

        if (var5 == null)
        {
            return null;
        }
        else
        {
            float var6 = var5.a(0.0F);

            if (var5.b())
            {
                var6 = 1.0F - var6;
            }

            return this.b(par1World, par2, par3, par4, var5.a(), var6, var5.c());
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        TileEntityPiston var5 = this.d(par1IBlockAccess, par2, par3, par4);

        if (var5 != null)
        {
            Block var6 = Block.byId[var5.a()];

            if (var6 == null || var6 == this)
            {
                return;
            }

            var6.updateShape(par1IBlockAccess, par2, par3, par4);
            float var7 = var5.a(0.0F);

            if (var5.b())
            {
                var7 = 1.0F - var7;
            }

            int var8 = var5.c();
            this.minX = var6.v() - (double)((float) Facing.b[var8] * var7);
            this.minY = var6.x() - (double)((float) Facing.c[var8] * var7);
            this.minZ = var6.z() - (double)((float) Facing.d[var8] * var7);
            this.maxX = var6.w() - (double)((float) Facing.b[var8] * var7);
            this.maxY = var6.y() - (double)((float) Facing.c[var8] * var7);
            this.maxZ = var6.A() - (double)((float) Facing.d[var8] * var7);
        }
    }

    public AxisAlignedBB b(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (par5 != 0 && par5 != this.id)
        {
            AxisAlignedBB var8 = Block.byId[par5].e(par1World, par2, par3, par4);

            if (var8 == null)
            {
                return null;
            }
            else
            {
                if (Facing.b[par7] < 0)
                {
                    var8.a -= (double)((float) Facing.b[par7] * par6);
                }
                else
                {
                    var8.d -= (double)((float) Facing.b[par7] * par6);
                }

                if (Facing.c[par7] < 0)
                {
                    var8.b -= (double)((float) Facing.c[par7] * par6);
                }
                else
                {
                    var8.e -= (double)((float) Facing.c[par7] * par6);
                }

                if (Facing.d[par7] < 0)
                {
                    var8.c -= (double)((float) Facing.d[par7] * par6);
                }
                else
                {
                    var8.f -= (double)((float) Facing.d[par7] * par6);
                }

                return var8;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * gets the piston tile entity at the specified location
     */
    private TileEntityPiston d(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        TileEntity var5 = par1IBlockAccess.getTileEntity(par2, par3, par4);
        return var5 instanceof TileEntityPiston ? (TileEntityPiston)var5 : null;
    }
}
