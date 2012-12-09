package net.minecraft.server;

public class WorldGenFlatLayerInfo
{
    /** Amount of layers for this set of layers. */
    private int a;

    /** Block type used on this set of layers. */
    private int b;

    /** Block metadata used on this set of laeyrs. */
    private int c;
    private int d;

    public WorldGenFlatLayerInfo(int par1, int par2)
    {
        this.a = 1;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.a = par1;
        this.b = par2;
    }

    public WorldGenFlatLayerInfo(int par1, int par2, int par3)
    {
        this(par1, par2);
        this.c = par3;
    }

    /**
     * Return the amount of layers for this set of layers.
     */
    public int a()
    {
        return this.a;
    }

    /**
     * Return the block type used on this set of layers.
     */
    public int b()
    {
        return this.b;
    }

    /**
     * Return the block metadata used on this set of layers.
     */
    public int c()
    {
        return this.c;
    }

    /**
     * Return the minimum Y coordinate for this layer, set during generation.
     */
    public int d()
    {
        return this.d;
    }

    /**
     * Set the minimum Y coordinate for this layer.
     */
    public void d(int par1)
    {
        this.d = par1;
    }

    public String toString()
    {
        String var1 = Integer.toString(this.b);

        if (this.a > 1)
        {
            var1 = this.a + "x" + var1;
        }

        if (this.c > 0)
        {
            var1 = var1 + ":" + this.c;
        }

        return var1;
    }
}
