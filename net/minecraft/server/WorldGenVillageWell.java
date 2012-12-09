package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageWell extends WorldGenVillagePiece
{
    private final boolean a = true;
    private int b = -1;

    public WorldGenVillageWell(WorldGenVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, int par4, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.f = par3Random.nextInt(4);

        switch (this.f)
        {
            case 0:
            case 2:
                this.e = new StructureBoundingBox(par4, 64, par5, par4 + 6 - 1, 78, par5 + 6 - 1);
                break;

            default:
                this.e = new StructureBoundingBox(par4, 64, par5, par4 + 6 - 1, 78, par5 + 6 - 1);
        }
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.a - 1, this.e.e - 4, this.e.c + 1, 1, this.c());
        WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.d + 1, this.e.e - 4, this.e.c + 1, 3, this.c());
        WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.a + 1, this.e.e - 4, this.e.c - 1, 2, this.c());
        WorldGenVillagePieces.b((WorldGenVillageStartPiece) par1StructureComponent, par2List, par3Random, this.e.a + 1, this.e.e - 4, this.e.f + 1, 0, this.c());
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        if (this.b < 0)
        {
            this.b = this.b(par1World, par3StructureBoundingBox);

            if (this.b < 0)
            {
                return true;
            }

            this.e.a(0, this.b - this.e.e + 3, 0);
        }

        this.a(par1World, par3StructureBoundingBox, 1, 0, 1, 4, 12, 4, Block.COBBLESTONE.id, Block.WATER.id, false);
        this.a(par1World, 0, 0, 2, 12, 2, par3StructureBoundingBox);
        this.a(par1World, 0, 0, 3, 12, 2, par3StructureBoundingBox);
        this.a(par1World, 0, 0, 2, 12, 3, par3StructureBoundingBox);
        this.a(par1World, 0, 0, 3, 12, 3, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 1, 13, 1, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 1, 14, 1, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 4, 13, 1, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 4, 14, 1, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 1, 13, 4, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 1, 14, 4, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 4, 13, 4, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 4, 14, 4, par3StructureBoundingBox);
        this.a(par1World, par3StructureBoundingBox, 1, 15, 1, 4, 15, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);

        for (int var4 = 0; var4 <= 5; ++var4)
        {
            for (int var5 = 0; var5 <= 5; ++var5)
            {
                if (var5 == 0 || var5 == 5 || var4 == 0 || var4 == 5)
                {
                    this.a(par1World, Block.GRAVEL.id, 0, var5, 11, var4, par3StructureBoundingBox);
                    this.b(par1World, var5, 12, var4, par3StructureBoundingBox);
                }
            }
        }

        return true;
    }
}
