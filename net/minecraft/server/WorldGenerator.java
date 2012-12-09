package net.minecraft.server;

import java.util.Random;

public abstract class WorldGenerator
{
    /**
     * Sets wither or not the generator should notify blocks of blocks it changes. When the world is first generated,
     * this is false, when saplings grow, this is true.
     */
    private final boolean a;
    private int b = 1;

    public WorldGenerator()
    {
        this.a = false;
    }

    public WorldGenerator(boolean par1)
    {
        this.a = par1;
    }

    public abstract boolean a(World var1, Random var2, int var3, int var4, int var5);

    /**
     * Rescales the generator settings, only used in WorldGenBigTree
     */
    public void a(double par1, double par3, double par5) {}

    /**
     * Sets the block without metadata in the world, notifying neighbors if enabled.
     */
    protected void setType(World par1World, int par2, int par3, int par4, int par5)
    {
        this.setTypeAndData(par1World, par2, par3, par4, par5, 0);
    }

    /**
     * Sets the block in the world, notifying neighbors if enabled.
     */
    protected void setTypeAndData(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (this.a)
        {
            par1World.setTypeIdAndData(par2, par3, par4, par5, par6);
        }
        else
        {
            par1World.setRawTypeIdAndData(par2, par3, par4, par5, par6);
        }
    }
}
