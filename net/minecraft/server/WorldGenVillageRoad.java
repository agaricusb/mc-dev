package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageRoad extends WorldGenVillageRoadPiece
{
    private int a;

    public WorldGenVillageRoad(WorldGenVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.f = par5;
        this.e = par4StructureBoundingBox;
        this.a = Math.max(par4StructureBoundingBox.b(), par4StructureBoundingBox.d());
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        boolean var4 = false;
        int var5;
        StructurePiece var6;

        for (var5 = par3Random.nextInt(5); var5 < this.a - 8; var5 += 2 + par3Random.nextInt(5))
        {
            var6 = this.a((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, 0, var5);

            if (var6 != null)
            {
                var5 += Math.max(var6.e.b(), var6.e.d());
                var4 = true;
            }
        }

        for (var5 = par3Random.nextInt(5); var5 < this.a - 8; var5 += 2 + par3Random.nextInt(5))
        {
            var6 = this.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, 0, var5);

            if (var6 != null)
            {
                var5 += Math.max(var6.e.b(), var6.e.d());
                var4 = true;
            }
        }

        if (var4 && par3Random.nextInt(3) > 0)
        {
            switch (this.f)
            {
                case 0:
                    WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b, this.e.f - 2, 1, this.c());
                    break;

                case 1:
                    WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.a, this.e.b, this.e.c - 1, 2, this.c());
                    break;

                case 2:
                    WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.b, this.e.c, 1, this.c());
                    break;

                case 3:
                    WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.d - 2, this.e.b, this.e.c - 1, 2, this.c());
            }
        }

        if (var4 && par3Random.nextInt(3) > 0)
        {
            switch (this.f)
            {
                case 0:
                    WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b, this.e.f - 2, 3, this.c());
                    break;

                case 1:
                    WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.a, this.e.b, this.e.f + 1, 0, this.c());
                    break;

                case 2:
                    WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.b, this.e.c, 3, this.c());
                    break;

                case 3:
                    WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.d - 2, this.e.b, this.e.f + 1, 0, this.c());
            }
        }
    }

    public static StructureBoundingBox a(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6)
    {
        for (int var7 = 7 * MathHelper.nextInt(par2Random, 3, 5); var7 >= 7; var7 -= 7)
        {
            StructureBoundingBox var8 = StructureBoundingBox.a(par3, par4, par5, 0, 0, 0, 3, 3, var7, par6);

            if (StructurePiece.a(par1List, var8) == null)
            {
                return var8;
            }
        }

        return null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        int var4 = this.d(Block.GRAVEL.id, 0);

        for (int var5 = this.e.a; var5 <= this.e.d; ++var5)
        {
            for (int var6 = this.e.c; var6 <= this.e.f; ++var6)
            {
                if (par3StructureBoundingBox.b(var5, 64, var6))
                {
                    int var7 = par1World.i(var5, var6) - 1;
                    par1World.setRawTypeId(var5, var7, var6, var4);
                }
            }
        }

        return true;
    }
}
