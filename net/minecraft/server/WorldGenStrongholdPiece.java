package net.minecraft.server;

import java.util.List;
import java.util.Random;

abstract class WorldGenStrongholdPiece extends StructurePiece
{
    protected WorldGenStrongholdPiece(int par1)
    {
        super(par1);
    }

    /**
     * builds a door of the enumerated types (empty opening is a door)
     */
    protected void a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox, WorldGenStrongholdDoorType par4EnumDoor, int par5, int par6, int par7)
    {
        switch (WorldGenStrongholdPieceWeight3.a[par4EnumDoor.ordinal()])
        {
            case 1:
            default:
                this.a(par1World, par3StructureBoundingBox, par5, par6, par7, par5 + 3 - 1, par6 + 3 - 1, par7, 0, 0, false);
                break;

            case 2:
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5, par6, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                this.a(par1World, Block.WOODEN_DOOR.id, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                this.a(par1World, Block.WOODEN_DOOR.id, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                break;

            case 3:
                this.a(par1World, 0, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                this.a(par1World, 0, 0, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                this.a(par1World, Block.IRON_FENCE.id, 0, par5, par6, par7, par3StructureBoundingBox);
                this.a(par1World, Block.IRON_FENCE.id, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                this.a(par1World, Block.IRON_FENCE.id, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                this.a(par1World, Block.IRON_FENCE.id, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                this.a(par1World, Block.IRON_FENCE.id, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                this.a(par1World, Block.IRON_FENCE.id, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                this.a(par1World, Block.IRON_FENCE.id, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                break;

            case 4:
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5, par6, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                this.a(par1World, Block.SMOOTH_BRICK.id, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                this.a(par1World, Block.IRON_DOOR_BLOCK.id, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                this.a(par1World, Block.IRON_DOOR_BLOCK.id, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                this.a(par1World, Block.STONE_BUTTON.id, this.c(Block.STONE_BUTTON.id, 4), par5 + 2, par6 + 1, par7 + 1, par3StructureBoundingBox);
                this.a(par1World, Block.STONE_BUTTON.id, this.c(Block.STONE_BUTTON.id, 3), par5 + 2, par6 + 1, par7 - 1, par3StructureBoundingBox);
        }
    }

    protected WorldGenStrongholdDoorType a(Random par1Random)
    {
        int var2 = par1Random.nextInt(5);

        switch (var2)
        {
            case 0:
            case 1:
            default:
                return WorldGenStrongholdDoorType.a;

            case 2:
                return WorldGenStrongholdDoorType.b;

            case 3:
                return WorldGenStrongholdDoorType.c;

            case 4:
                return WorldGenStrongholdDoorType.d;
        }
    }

    /**
     * Gets the next component in any cardinal direction
     */
    protected StructurePiece a(WorldGenStrongholdStart par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5)
    {
        switch (this.f)
        {
            case 0:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.a + par4, this.e.b + par5, this.e.f + 1, this.f, this.c());

            case 1:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.a - 1, this.e.b + par5, this.e.c + par4, this.f, this.c());

            case 2:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.a + par4, this.e.b + par5, this.e.c - 1, this.f, this.c());

            case 3:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.d + 1, this.e.b + par5, this.e.c + par4, this.f, this.c());

            default:
                return null;
        }
    }

    /**
     * Gets the next component in the +/- X direction
     */
    protected StructurePiece b(WorldGenStrongholdStart par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5)
    {
        switch (this.f)
        {
            case 0:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.a - 1, this.e.b + par4, this.e.c + par5, 1, this.c());

            case 1:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.c - 1, 2, this.c());

            case 2:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.a - 1, this.e.b + par4, this.e.c + par5, 1, this.c());

            case 3:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.c - 1, 2, this.c());

            default:
                return null;
        }
    }

    /**
     * Gets the next component in the +/- Z direction
     */
    protected StructurePiece c(WorldGenStrongholdStart par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5)
    {
        switch (this.f)
        {
            case 0:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.d + 1, this.e.b + par4, this.e.c + par5, 3, this.c());

            case 1:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.f + 1, 0, this.c());

            case 2:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.d + 1, this.e.b + par4, this.e.c + par5, 3, this.c());

            case 3:
                return WorldGenStrongholdPieces.a(par1ComponentStrongholdStairs2, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.f + 1, 0, this.c());

            default:
                return null;
        }
    }

    /**
     * returns false if the Structure Bounding Box goes below 10
     */
    protected static boolean a(StructureBoundingBox par0StructureBoundingBox)
    {
        return par0StructureBoundingBox != null && par0StructureBoundingBox.b > 10;
    }
}
