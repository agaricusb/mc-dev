package net.minecraft.server;

import java.util.Random;

public class BlockGlass extends BlockHalfTransparant
{
    public BlockGlass(int par1, int par2, Material par3Material, boolean par4)
    {
        super(par1, par2, par3Material, par4);
        this.a(CreativeModeTab.b);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 0;
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
     * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
     */
    protected boolean s_()
    {
        return true;
    }
}
