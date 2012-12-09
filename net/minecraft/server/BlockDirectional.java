package net.minecraft.server;

public abstract class BlockDirectional extends Block
{
    protected BlockDirectional(int par1, int par2, Material par3Material)
    {
        super(par1, par2, par3Material);
    }

    protected BlockDirectional(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }

    /**
     * Returns the orentation value from the specified metadata
     */
    public static int e(int par0)
    {
        return par0 & 3;
    }
}
