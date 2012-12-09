package net.minecraft.server;

public class BlockBeacon extends BlockContainer
{
    public BlockBeacon(int par1)
    {
        super(par1, 41, Material.SHATTERABLE);
        this.c(3.0F);
        this.a(CreativeModeTab.f);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity a(World par1World)
    {
        return new TileEntityBeacon();
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isStatic)
        {
            return true;
        }
        else
        {
            TileEntityBeacon var10 = (TileEntityBeacon)par1World.getTileEntity(par2, par3, par4);

            if (var10 != null)
            {
                par5EntityPlayer.openBeacon(var10);
            }

            return true;
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
        return 34;
    }
}
