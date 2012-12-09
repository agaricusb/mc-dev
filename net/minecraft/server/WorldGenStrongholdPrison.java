package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdPrison extends WorldGenStrongholdPiece
{
    protected final WorldGenStrongholdDoorType a;

    public WorldGenStrongholdPrison(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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

    public static WorldGenStrongholdPrison a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -1, -1, 0, 9, 5, 11, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenStrongholdPrison(par6, par1Random, var7, par5) : null;
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
            this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 4, 10, true, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par2Random, par3StructureBoundingBox, this.a, 1, 1, 0);
            this.a(par1World, par3StructureBoundingBox, 1, 1, 10, 3, 3, 10, 0, 0, false);
            this.a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 1, false, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par3StructureBoundingBox, 4, 1, 3, 4, 3, 3, false, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par3StructureBoundingBox, 4, 1, 7, 4, 3, 7, false, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par3StructureBoundingBox, 4, 1, 9, 4, 3, 9, false, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par3StructureBoundingBox, 4, 1, 4, 4, 3, 6, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
            this.a(par1World, par3StructureBoundingBox, 5, 1, 5, 7, 3, 5, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
            this.a(par1World, Block.IRON_FENCE.id, 0, 4, 3, 2, par3StructureBoundingBox);
            this.a(par1World, Block.IRON_FENCE.id, 0, 4, 3, 8, par3StructureBoundingBox);
            this.a(par1World, Block.IRON_DOOR_BLOCK.id, this.c(Block.IRON_DOOR_BLOCK.id, 3), 4, 1, 2, par3StructureBoundingBox);
            this.a(par1World, Block.IRON_DOOR_BLOCK.id, this.c(Block.IRON_DOOR_BLOCK.id, 3) + 8, 4, 2, 2, par3StructureBoundingBox);
            this.a(par1World, Block.IRON_DOOR_BLOCK.id, this.c(Block.IRON_DOOR_BLOCK.id, 3), 4, 1, 8, par3StructureBoundingBox);
            this.a(par1World, Block.IRON_DOOR_BLOCK.id, this.c(Block.IRON_DOOR_BLOCK.id, 3) + 8, 4, 2, 8, par3StructureBoundingBox);
            return true;
        }
    }
}
