package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdStairs2 extends WorldGenStrongholdPiece
{
    private final boolean a;
    private final WorldGenStrongholdDoorType b;

    public WorldGenStrongholdStairs2(int par1, Random par2Random, int par3, int par4)
    {
        super(par1);
        this.a = true;
        this.f = par2Random.nextInt(4);
        this.b = WorldGenStrongholdDoorType.a;

        switch (this.f)
        {
            case 0:
            case 2:
                this.e = new StructureBoundingBox(par3, 64, par4, par3 + 5 - 1, 74, par4 + 5 - 1);
                break;

            default:
                this.e = new StructureBoundingBox(par3, 64, par4, par3 + 5 - 1, 74, par4 + 5 - 1);
        }
    }

    public WorldGenStrongholdStairs2(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.a = false;
        this.f = par4;
        this.b = this.a(par2Random);
        this.e = par3StructureBoundingBox;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        if (this.a)
        {
            WorldGenStrongholdPieces.a(WorldGenStrongholdCrossing.class);
        }

        this.a((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 1, 1);
    }

    /**
     * performs some checks, then gives out a fresh Stairs component
     */
    public static WorldGenStrongholdStairs2 a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -1, -7, 0, 5, 11, 5, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenStrongholdStairs2(par6, par1Random, var7, par5) : null;
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
            this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 10, 4, true, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par2Random, par3StructureBoundingBox, this.b, 1, 7, 0);
            this.a(par1World, par2Random, par3StructureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 4);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 2, 6, 1, par3StructureBoundingBox);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 1, 5, 1, par3StructureBoundingBox);
            this.a(par1World, Block.STEP.id, 0, 1, 6, 1, par3StructureBoundingBox);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 1, 5, 2, par3StructureBoundingBox);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 1, 4, 3, par3StructureBoundingBox);
            this.a(par1World, Block.STEP.id, 0, 1, 5, 3, par3StructureBoundingBox);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 2, 4, 3, par3StructureBoundingBox);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 3, 3, 3, par3StructureBoundingBox);
            this.a(par1World, Block.STEP.id, 0, 3, 4, 3, par3StructureBoundingBox);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 3, 3, 2, par3StructureBoundingBox);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 3, 2, 1, par3StructureBoundingBox);
            this.a(par1World, Block.STEP.id, 0, 3, 3, 1, par3StructureBoundingBox);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 2, 2, 1, par3StructureBoundingBox);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 1, 1, 1, par3StructureBoundingBox);
            this.a(par1World, Block.STEP.id, 0, 1, 2, 1, par3StructureBoundingBox);
            this.a(par1World, Block.SMOOTH_BRICK.id, 0, 1, 1, 2, par3StructureBoundingBox);
            this.a(par1World, Block.STEP.id, 0, 1, 1, 3, par3StructureBoundingBox);
            return true;
        }
    }
}
