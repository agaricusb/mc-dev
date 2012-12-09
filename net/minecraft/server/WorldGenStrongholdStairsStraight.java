package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdStairsStraight extends WorldGenStrongholdPiece
{
    private final WorldGenStrongholdDoorType a;

    public WorldGenStrongholdStairsStraight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.a = this.a(par2Random);
        this.e = par3StructureBoundingBox;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        this.a((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 1, 1);
    }

    public static WorldGenStrongholdStairsStraight a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -1, -7, 0, 5, 11, 8, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenStrongholdStairsStraight(par6, par1Random, var7, par5) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        if (this.a(par1World, par3StructureBoundingBox))
        {
            return false;
        }
        else
        {
            this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 10, 7, true, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par2Random, par3StructureBoundingBox, this.a, 1, 7, 0);
            this.a(par1World, par2Random, par3StructureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 7);
            int var4 = this.c(Block.COBBLESTONE_STAIRS.id, 2);

            for (int var5 = 0; var5 < 6; ++var5)
            {
                this.a(par1World, Block.COBBLESTONE_STAIRS.id, var4, 1, 6 - var5, 1 + var5, par3StructureBoundingBox);
                this.a(par1World, Block.COBBLESTONE_STAIRS.id, var4, 2, 6 - var5, 1 + var5, par3StructureBoundingBox);
                this.a(par1World, Block.COBBLESTONE_STAIRS.id, var4, 3, 6 - var5, 1 + var5, par3StructureBoundingBox);

                if (var5 < 5)
                {
                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 1, 5 - var5, 1 + var5, par3StructureBoundingBox);
                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 2, 5 - var5, 1 + var5, par3StructureBoundingBox);
                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 3, 5 - var5, 1 + var5, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }
}
