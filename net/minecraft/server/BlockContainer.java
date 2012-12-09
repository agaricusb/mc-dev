package net.minecraft.server;

public abstract class BlockContainer extends Block
{
    protected BlockContainer(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.isTileEntity = true;
    }

    protected BlockContainer(int par1, int par2, Material par3Material)
    {
        super(par1, par2, par3Material);
        this.isTileEntity = true;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World par1World, int par2, int par3, int par4)
    {
        super.onPlace(par1World, par2, par3, par4);
        par1World.setTileEntity(par2, par3, par4, this.a(par1World));
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.remove(par1World, par2, par3, par4, par5, par6);
        par1World.r(par2, par3, par4);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public abstract TileEntity a(World var1);

    /**
     * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
     * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
     */
    public void b(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.b(par1World, par2, par3, par4, par5, par6);
        TileEntity var7 = par1World.getTileEntity(par2, par3, par4);

        if (var7 != null)
        {
            var7.b(par5, par6);
        }
    }
}
