package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdRoomCrossing extends WorldGenStrongholdPiece
{
    /**
     * Items that could generate in the chest that is located in Stronghold Room Crossing.
     */
    private static final StructurePieceTreasure[] c = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.REDSTONE.id, 0, 4, 9, 5), new StructurePieceTreasure(Item.COAL.id, 0, 3, 8, 10), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.APPLE.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 1)};
    protected final WorldGenStrongholdDoorType a;
    protected final int b;

    public WorldGenStrongholdRoomCrossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.a = this.a(par2Random);
        this.e = par3StructureBoundingBox;
        this.b = par2Random.nextInt(5);
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        this.a((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 4, 1);
        this.b((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 1, 4);
        this.c((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 1, 4);
    }

    public static WorldGenStrongholdRoomCrossing a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -4, -1, 0, 11, 7, 11, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenStrongholdRoomCrossing(par6, par1Random, var7, par5) : null;
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
            this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 6, 10, true, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par2Random, par3StructureBoundingBox, this.a, 4, 1, 0);
            this.a(par1World, par3StructureBoundingBox, 4, 1, 10, 6, 3, 10, 0, 0, false);
            this.a(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 6, 0, 0, false);
            this.a(par1World, par3StructureBoundingBox, 10, 1, 4, 10, 3, 6, 0, 0, false);
            int var4;

            switch (this.b)
            {
                case 0:
                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 5, 1, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 5, 2, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 5, 3, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.TORCH.id, 0, 4, 3, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.TORCH.id, 0, 6, 3, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.TORCH.id, 0, 5, 3, 4, par3StructureBoundingBox);
                    this.a(par1World, Block.TORCH.id, 0, 5, 3, 6, par3StructureBoundingBox);
                    this.a(par1World, Block.STEP.id, 0, 4, 1, 4, par3StructureBoundingBox);
                    this.a(par1World, Block.STEP.id, 0, 4, 1, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.STEP.id, 0, 4, 1, 6, par3StructureBoundingBox);
                    this.a(par1World, Block.STEP.id, 0, 6, 1, 4, par3StructureBoundingBox);
                    this.a(par1World, Block.STEP.id, 0, 6, 1, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.STEP.id, 0, 6, 1, 6, par3StructureBoundingBox);
                    this.a(par1World, Block.STEP.id, 0, 5, 1, 4, par3StructureBoundingBox);
                    this.a(par1World, Block.STEP.id, 0, 5, 1, 6, par3StructureBoundingBox);
                    break;

                case 1:
                    for (var4 = 0; var4 < 5; ++var4)
                    {
                        this.a(par1World, Block.SMOOTH_BRICK.id, 0, 3, 1, 3 + var4, par3StructureBoundingBox);
                        this.a(par1World, Block.SMOOTH_BRICK.id, 0, 7, 1, 3 + var4, par3StructureBoundingBox);
                        this.a(par1World, Block.SMOOTH_BRICK.id, 0, 3 + var4, 1, 3, par3StructureBoundingBox);
                        this.a(par1World, Block.SMOOTH_BRICK.id, 0, 3 + var4, 1, 7, par3StructureBoundingBox);
                    }

                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 5, 1, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 5, 2, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.SMOOTH_BRICK.id, 0, 5, 3, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.WATER.id, 0, 5, 4, 5, par3StructureBoundingBox);
                    break;

                case 2:
                    for (var4 = 1; var4 <= 9; ++var4)
                    {
                        this.a(par1World, Block.COBBLESTONE.id, 0, 1, 3, var4, par3StructureBoundingBox);
                        this.a(par1World, Block.COBBLESTONE.id, 0, 9, 3, var4, par3StructureBoundingBox);
                    }

                    for (var4 = 1; var4 <= 9; ++var4)
                    {
                        this.a(par1World, Block.COBBLESTONE.id, 0, var4, 3, 1, par3StructureBoundingBox);
                        this.a(par1World, Block.COBBLESTONE.id, 0, var4, 3, 9, par3StructureBoundingBox);
                    }

                    this.a(par1World, Block.COBBLESTONE.id, 0, 5, 1, 4, par3StructureBoundingBox);
                    this.a(par1World, Block.COBBLESTONE.id, 0, 5, 1, 6, par3StructureBoundingBox);
                    this.a(par1World, Block.COBBLESTONE.id, 0, 5, 3, 4, par3StructureBoundingBox);
                    this.a(par1World, Block.COBBLESTONE.id, 0, 5, 3, 6, par3StructureBoundingBox);
                    this.a(par1World, Block.COBBLESTONE.id, 0, 4, 1, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.COBBLESTONE.id, 0, 6, 1, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.COBBLESTONE.id, 0, 4, 3, 5, par3StructureBoundingBox);
                    this.a(par1World, Block.COBBLESTONE.id, 0, 6, 3, 5, par3StructureBoundingBox);

                    for (var4 = 1; var4 <= 3; ++var4)
                    {
                        this.a(par1World, Block.COBBLESTONE.id, 0, 4, var4, 4, par3StructureBoundingBox);
                        this.a(par1World, Block.COBBLESTONE.id, 0, 6, var4, 4, par3StructureBoundingBox);
                        this.a(par1World, Block.COBBLESTONE.id, 0, 4, var4, 6, par3StructureBoundingBox);
                        this.a(par1World, Block.COBBLESTONE.id, 0, 6, var4, 6, par3StructureBoundingBox);
                    }

                    this.a(par1World, Block.TORCH.id, 0, 5, 3, 5, par3StructureBoundingBox);

                    for (var4 = 2; var4 <= 8; ++var4)
                    {
                        this.a(par1World, Block.WOOD.id, 0, 2, 3, var4, par3StructureBoundingBox);
                        this.a(par1World, Block.WOOD.id, 0, 3, 3, var4, par3StructureBoundingBox);

                        if (var4 <= 3 || var4 >= 7)
                        {
                            this.a(par1World, Block.WOOD.id, 0, 4, 3, var4, par3StructureBoundingBox);
                            this.a(par1World, Block.WOOD.id, 0, 5, 3, var4, par3StructureBoundingBox);
                            this.a(par1World, Block.WOOD.id, 0, 6, 3, var4, par3StructureBoundingBox);
                        }

                        this.a(par1World, Block.WOOD.id, 0, 7, 3, var4, par3StructureBoundingBox);
                        this.a(par1World, Block.WOOD.id, 0, 8, 3, var4, par3StructureBoundingBox);
                    }

                    this.a(par1World, Block.LADDER.id, this.c(Block.LADDER.id, 4), 9, 1, 3, par3StructureBoundingBox);
                    this.a(par1World, Block.LADDER.id, this.c(Block.LADDER.id, 4), 9, 2, 3, par3StructureBoundingBox);
                    this.a(par1World, Block.LADDER.id, this.c(Block.LADDER.id, 4), 9, 3, 3, par3StructureBoundingBox);
                    this.a(par1World, par3StructureBoundingBox, par2Random, 3, 4, 8, StructurePieceTreasure.a(c, new StructurePieceTreasure[]{Item.ENCHANTED_BOOK.b(par2Random)}), 1 + par2Random.nextInt(4));
            }

            return true;
        }
    }
}
