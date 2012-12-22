package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdChestCorridor extends WorldGenStrongholdPiece
{
    /** List of items that Stronghold chests can contain. */
    private static final StructurePieceTreasure[] a = new StructurePieceTreasure[] {new StructurePieceTreasure(Item.ENDER_PEARL.id, 0, 1, 1, 10), new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 3, 3), new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.REDSTONE.id, 0, 4, 9, 5), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.APPLE.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_SWORD.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_CHESTPLATE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_HELMET.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_LEGGINGS.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_BOOTS.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.GOLDEN_APPLE.id, 0, 1, 1, 1)};
    private final WorldGenStrongholdDoorType b;
    private boolean c;

    public WorldGenStrongholdChestCorridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.b = this.a(par2Random);
        this.e = par3StructureBoundingBox;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        this.a((WorldGenStrongholdStart) par1StructureComponent, par2List, par3Random, 1, 1);
    }

    public static WorldGenStrongholdChestCorridor a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -1, -1, 0, 5, 5, 7, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenStrongholdChestCorridor(par6, par1Random, var7, par5) : null;
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
            this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, WorldGenStrongholdPieces.b());
            this.a(par1World, par2Random, par3StructureBoundingBox, this.b, 1, 1, 0);
            this.a(par1World, par2Random, par3StructureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 6);
            this.a(par1World, par3StructureBoundingBox, 3, 1, 2, 3, 1, 4, Block.SMOOTH_BRICK.id, Block.SMOOTH_BRICK.id, false);
            this.a(par1World, Block.STEP.id, 5, 3, 1, 1, par3StructureBoundingBox);
            this.a(par1World, Block.STEP.id, 5, 3, 1, 5, par3StructureBoundingBox);
            this.a(par1World, Block.STEP.id, 5, 3, 2, 2, par3StructureBoundingBox);
            this.a(par1World, Block.STEP.id, 5, 3, 2, 4, par3StructureBoundingBox);
            int var4;

            for (var4 = 2; var4 <= 4; ++var4)
            {
                this.a(par1World, Block.STEP.id, 5, 2, 1, var4, par3StructureBoundingBox);
            }

            if (!this.c)
            {
                var4 = this.a(2);
                int var5 = this.a(3, 3);
                int var6 = this.b(3, 3);

                if (par3StructureBoundingBox.b(var5, var4, var6))
                {
                    this.c = true;
                    this.a(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, StructurePieceTreasure.a(a, new StructurePieceTreasure[]{Item.ENCHANTED_BOOK.b(par2Random)}), 2 + par2Random.nextInt(2));
                }
            }

            return true;
        }
    }
}
