package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageLibrary extends WorldGenVillagePiece
{
    private int a = -1;

    public WorldGenVillageLibrary(WorldGenVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.f = par5;
        this.e = par4StructureBoundingBox;
    }

    public static WorldGenVillageLibrary a(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        StructureBoundingBox var8 = StructureBoundingBox.a(par3, par4, par5, 0, 0, 0, 9, 9, 6, par6);
        return a(var8) && StructurePiece.a(par1List, var8) == null ? new WorldGenVillageLibrary(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
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

            this.e.a(0, this.a - this.e.e + 9 - 1, 0);
        }

        this.a(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 5, 4, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 0, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 5, 0, 8, 5, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 6, 1, 8, 6, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 7, 2, 8, 7, 3, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        int var4 = this.c(Block.WOOD_STAIRS.id, 3);
        int var5 = this.c(Block.WOOD_STAIRS.id, 2);
        int var6;
        int var7;

        for (var6 = -1; var6 <= 2; ++var6)
        {
            for (var7 = 0; var7 <= 8; ++var7)
            {
                this.a(par1World, Block.WOOD_STAIRS.id, var4, var7, 6 + var6, var6, par3StructureBoundingBox);
                this.a(par1World, Block.WOOD_STAIRS.id, var5, var7, 6 + var6, 5 - var6, par3StructureBoundingBox);
            }
        }

        this.a(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 1, 5, 8, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 8, 1, 0, 8, 1, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 2, 1, 0, 7, 1, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 4, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 5, 0, 4, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 8, 2, 5, 8, 4, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 8, 2, 0, 8, 4, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 1, 0, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 2, 5, 7, 4, 5, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 8, 2, 1, 8, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 4, 0, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, Block.THIN_GLASS.id, 0, 4, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 5, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 6, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 4, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 5, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 6, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 2, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 2, 3, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 3, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 3, 3, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 8, 2, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 8, 2, 3, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 8, 3, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 8, 3, 3, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 2, 2, 5, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 3, 2, 5, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 5, 2, 5, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 6, 2, 5, par3StructureBoundingBox);
        this.a(par1World, par3StructureBoundingBox, 1, 4, 1, 7, 4, 1, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 4, 4, 7, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 3, 4, 7, 3, 4, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
        this.a(par1World, Block.WOOD.id, 0, 7, 1, 4, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD_STAIRS.id, this.c(Block.WOOD_STAIRS.id, 0), 7, 1, 3, par3StructureBoundingBox);
        var6 = this.c(Block.WOOD_STAIRS.id, 3);
        this.a(par1World, Block.WOOD_STAIRS.id, var6, 6, 1, 4, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD_STAIRS.id, var6, 5, 1, 4, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD_STAIRS.id, var6, 4, 1, 4, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD_STAIRS.id, var6, 3, 1, 4, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 6, 1, 3, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD_PLATE.id, 0, 6, 2, 3, par3StructureBoundingBox);
        this.a(par1World, Block.FENCE.id, 0, 4, 1, 3, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD_PLATE.id, 0, 4, 2, 3, par3StructureBoundingBox);
        this.a(par1World, Block.WORKBENCH.id, 0, 7, 1, 1, par3StructureBoundingBox);
        this.a(par1World, 0, 0, 1, 1, 0, par3StructureBoundingBox);
        this.a(par1World, 0, 0, 1, 2, 0, par3StructureBoundingBox);
        this.a(par1World, par3StructureBoundingBox, par2Random, 1, 1, 0, this.c(Block.WOODEN_DOOR.id, 1));

        if (this.a(par1World, 1, 0, -1, par3StructureBoundingBox) == 0 && this.a(par1World, 1, -1, -1, par3StructureBoundingBox) != 0)
        {
            this.a(par1World, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 1, 0, -1, par3StructureBoundingBox);
        }

        for (var7 = 0; var7 < 6; ++var7)
        {
            for (int var8 = 0; var8 < 9; ++var8)
            {
                this.b(par1World, var8, 9, var7, par3StructureBoundingBox);
                this.b(par1World, Block.COBBLESTONE.id, 0, var8, -1, var7, par3StructureBoundingBox);
            }
        }

        this.a(par1World, par3StructureBoundingBox, 2, 1, 2, 1);
        return true;
    }

    /**
     * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
     */
    protected int b(int par1)
    {
        return 1;
    }
}
