package net.minecraft.server;

public class BlockTransparant extends Block
{
    /**
     * Used to determine how to display leaves based on the graphics level. May also be used in rendering for
     * transparency, not sure.
     */
    protected boolean c;

    protected BlockTransparant(int par1, int par2, Material par3Material, boolean par4)
    {
        super(par1, par2, par3Material);
        this.c = par4;
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
