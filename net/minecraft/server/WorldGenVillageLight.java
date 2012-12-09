package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageLight extends WorldGenVillagePiece
{
    private int a = -1;

    public WorldGenVillageLight(WorldGenVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.f = par5;
        this.e = par4StructureBoundingBox;
    }

    public static StructureBoundingBox a(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par3, par4, par5, 0, 0, 0, 3, 4, 2, par6);
        return StructurePiece.a(par1List, var7) != null ? null : var7;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        if (this.a < 0)
        {
            this.a = this.b(par1World, par3StructureBoundingBox);

            if (this.a < 0)
            {
                return true;
            }

            this.e.a(0, this.a - this.e.e + 4 - 1, 0);
        }

        this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 3, 1, 0, 0, false);
        this.a(par1World, Block.FENCE.id, 0, 1, 0, 0, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 1, 1, 0, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 1, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.WOOL.id, 15, 1, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.TORCH.id, 15, 0, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.TORCH.id, 15, 1, 3, 1, par3StructureBoundingBox);
        this.a(par1World, Block.TORCH.id, 15, 2, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.TORCH.id, 15, 1, 3, -1, par3StructureBoundingBox);
        return true;
    }
}
