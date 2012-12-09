package net.minecraft.server;

public class WorldGenVillagePieceWeight
{
    /** The Class object for the represantation of this village piece. */
    public Class a;
    public final int b;
    public int c;
    public int d;

    public WorldGenVillagePieceWeight(Class par1Class, int par2, int par3)
    {
        this.a = par1Class;
        this.b = par2;
        this.d = par3;
    }

    public boolean a(int par1)
    {
        return this.d == 0 || this.c < this.d;
    }

    public boolean a()
    {
        return this.d == 0 || this.c < this.d;
    }
}
