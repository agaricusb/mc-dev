package net.minecraft.server;

final class WorldGenStrongholdUnknown extends WorldGenStrongholdPieceWeight
{
    WorldGenStrongholdUnknown(Class par1Class, int par2, int par3)
    {
        super(par1Class, par2, par3);
    }

    public boolean a(int par1)
    {
        return super.a(par1) && par1 > 4;
    }
}
