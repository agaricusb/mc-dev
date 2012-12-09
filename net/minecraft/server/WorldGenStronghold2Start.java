package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;

class WorldGenStronghold2Start extends StructureStart
{
    public WorldGenStronghold2Start(World par1World, Random par2Random, int par3, int par4)
    {
        WorldGenStrongholdPieces.a();
        WorldGenStrongholdStart var5 = new WorldGenStrongholdStart(0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
        this.a.add(var5);
        var5.a(var5, this.a, par2Random);
        ArrayList var6 = var5.c;

        while (!var6.isEmpty())
        {
            int var7 = par2Random.nextInt(var6.size());
            StructurePiece var8 = (StructurePiece)var6.remove(var7);
            var8.a(var5, this.a, par2Random);
        }

        this.c();
        this.a(par1World, par2Random, 10);
    }
}
