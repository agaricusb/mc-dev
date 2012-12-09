package net.minecraft.server;

public class BlockHalfTransparant extends Block
{
    private boolean a;

    protected BlockHalfTransparant(int par1, int par2, Material par3Material, boolean par4)
    {
        super(par1, par2, par3Material);
        this.a = par4;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return false;
    }
}
