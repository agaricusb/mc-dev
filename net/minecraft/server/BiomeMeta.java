package net.minecraft.server;

public class BiomeMeta extends WeightedRandomChoice
{
    /** Holds the class of the entity to be spawned. */
    public Class b;
    public int c;
    public int d;

    public BiomeMeta(Class par1Class, int par2, int par3, int par4)
    {
        super(par2);
        this.b = par1Class;
        this.c = par3;
        this.d = par4;
    }
}
