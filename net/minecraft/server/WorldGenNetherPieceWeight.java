package net.minecraft.server;

class WorldGenNetherPieceWeight
{
    public Class a;
    public final int b;
    public int c;
    public int d;
    public boolean e;

    public WorldGenNetherPieceWeight(Class par1Class, int par2, int par3, boolean par4)
    {
        this.a = par1Class;
        this.b = par2;
        this.d = par3;
        this.e = par4;
    }

    public WorldGenNetherPieceWeight(Class par1Class, int par2, int par3)
    {
        this(par1Class, par2, par3, false);
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
