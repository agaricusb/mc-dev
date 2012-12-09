package net.minecraft.server;

class WorldGenStrongholdPieceWeight
{
    public Class a;

    /**
     * This basically keeps track of the 'epicness' of a structure. Epic structure components have a higher 'weight',
     * and Structures may only grow up to a certain 'weight' before generation is stopped
     */
    public final int b;
    public int c;

    /** How many Structure Pieces of this type may spawn in a structure */
    public int d;

    public WorldGenStrongholdPieceWeight(Class par1Class, int par2, int par3)
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
