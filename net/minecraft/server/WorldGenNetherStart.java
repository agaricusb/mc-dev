package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;

class WorldGenNetherStart extends StructureStart
{
    public WorldGenNetherStart(World par1World, Random par2Random, int par3, int par4)
    {
        WorldGenNetherPiece15 var5 = new WorldGenNetherPiece15(par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
        this.a.add(var5);
        var5.a(var5, this.a, par2Random);
        ArrayList var6 = var5.d;

        while (!var6.isEmpty())
        {
            int var7 = par2Random.nextInt(var6.size());
            StructurePiece var8 = (StructurePiece)var6.remove(var7);
            var8.a(var5, this.a, par2Random);
        }

        this.c();
        this.a(par1World, par2Random, 48, 70);
    }
}
