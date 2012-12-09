package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

class WorldGenVillageStart extends StructureStart
{
    /** well ... thats what it does */
    private boolean c = false;

    public WorldGenVillageStart(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        ArrayList var6 = WorldGenVillagePieces.a(par2Random, par5);
        WorldGenVillageStartPiece var7 = new WorldGenVillageStartPiece(par1World.getWorldChunkManager(), 0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2, var6, par5);
        this.a.add(var7);
        var7.a(var7, this.a, par2Random);
        ArrayList var8 = var7.j;
        ArrayList var9 = var7.i;
        int var10;

        while (!var8.isEmpty() || !var9.isEmpty())
        {
            StructurePiece var11;

            if (var8.isEmpty())
            {
                var10 = par2Random.nextInt(var9.size());
                var11 = (StructurePiece)var9.remove(var10);
                var11.a(var7, this.a, par2Random);
            }
            else
            {
                var10 = par2Random.nextInt(var8.size());
                var11 = (StructurePiece)var8.remove(var10);
                var11.a(var7, this.a, par2Random);
            }
        }

        this.c();
        var10 = 0;
        Iterator var13 = this.a.iterator();

        while (var13.hasNext())
        {
            StructurePiece var12 = (StructurePiece)var13.next();

            if (!(var12 instanceof WorldGenVillageRoadPiece))
            {
                ++var10;
            }
        }

        this.c = var10 > 2;
    }

    /**
     * currently only defined for Villages, returns true if Village has more than 2 non-road components
     */
    public boolean d()
    {
        return this.c;
    }
}
