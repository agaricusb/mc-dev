package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageHouse2 extends WorldGenVillagePiece
{
    private int a = -1;

    public WorldGenVillageHouse2(WorldGenVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.f = par5;
        this.e = par4StructureBoundingBox;
    }

    public static WorldGenVillageHouse2 a(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        StructureBoundingBox var8 = StructureBoundingBox.a(par3, par4, par5, 0, 0, 0, 9, 7, 12, par6);
        return a(var8) && StructurePiece.a(par1List, var8) == null ? new WorldGenVillageHouse2(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
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

            this.e.a(0, this.a - this.e.e + 7 - 1, 0);
        }

        this.a(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 4, 4, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 2, 1, 6, 8, 4, 10, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 2, 0, 5, 8, 0, 10, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 0, 1, 7, 0, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 3, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 8, 0, 0, 8, 3, 10, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 0, 0, 7, 2, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 0, 5, 2, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 2, 0, 6, 2, 3, 10, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 3, 0, 10, 7, 3, 10, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 3, 0, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 2, 5, 2, 3, 5, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 4, 1, 8, 4, 1, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 4, 4, 3, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 5, 2, 8, 5, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, Block.WOOD.id, 0, 0, 4, 2, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 0, 4, 3, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 8, 4, 2, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 8, 4, 3, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 8, 4, 4, par3StructureBoundingBox);
        int var4 = this.c(Block.WOOD_STAIRS.id, 3);
        int var5 = this.c(Block.WOOD_STAIRS.id, 2);
        int var6;
        int var7;

        for (var6 = -1; var6 <= 2; ++var6)
        {
            for (var7 = 0; var7 <= 8; ++var7)
            {
                this.a(par1World, Block.WOOD_STAIRS.id, var4, var7, 4 + var6, var6, par3StructureBoundingBox);

                if ((var6 > -1 || var7 <= 1) && (var6 > 0 || var7 <= 3) && (var6 > 1 || var7 <= 4 || var7 >= 6))
                {
                    this.a(par1World, Block.WOOD_STAIRS.id, var5, var7, 4 + var6, 5 - var6, par3StructureBoundingBox);
                }
            }
        }

        this.a(par1World, par3StructureBoundingBox, 3, 4, 5, 3, 4, 10, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 7, 4, 2, 7, 4, 10, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 5, 4, 4, 5, 10, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 6, 5, 4, 6, 5, 10, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 5, 6, 3, 5, 6, 10, Block.WOOD.id, Block.WOOD.id, false);
        var6 = this.c(Block.WOOD_STAIRS.id, 0);
        int var8;

        for (var7 = 4; var7 >= 1; --var7)
        {
            this.a(par1World, Block.WOOD.id, 0, var7, 2 + var7, 7 - var7, par3StructureBoundingBox);

            for (var8 = 8 - var7; var8 <= 10; ++var8)
            {
                this.a(par1World, Block.WOOD_STAIRS.id, var6, var7, 2 + var7, var8, par3StructureBoundingBox);
            }
        }

        var7 = this.c(Block.WOOD_STAIRS.id, 1);
        this.a(par1World, Block.WOOD.id, 0, 6, 6, 3, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 7, 5, 4, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD_STAIRS.id, var7, 6, 6, 4, par3StructureBoundingBox);
        int var9;

        for (var8 = 6; var8 <= 8; ++var8)
        {
            for (var9 = 5; var9 <= 10; ++var9)
            {
                this.a(par1World, Block.WOOD_STAIRS.id, var7, var8, 12 - var8, var9, par3StructureBoundingBox);
            }
        }

        this.a(par1World, Block.LOG.id, 0, 0, 2, 1, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 0, 2, 4, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 2, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 2, 3, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 4, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 5, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 6, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 8, 2, 1, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 8, 2, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 8, 2, 3, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 8, 2, 4, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 8, 2, 5, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 8, 2, 6, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 8, 2, 7, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 8, 2, 8, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 8, 2, 9, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 2, 2, 6, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 2, 2, 7, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 2, 2, 8, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 2, 2, 9, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 4, 4, 10, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 5, 4, 10, par3StructureBoundingBox);
        this.a(par1World, Block.LOG.id, 0, 6, 4, 10, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 5, 5, 10, par3StructureBoundingBox);
        this.a(par1World, 0, 0, 2, 1, 0, par3StructureBoundingBox);
        this.a(par1World, 0, 0, 2, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.TORCH.id, 0, 2, 3, 1, par3StructureBoundingBox);
        this.a(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, this.c(Block.WOODEN_DOOR.id, 1));
        this.a(par1World, par3StructureBoundingBox, 1, 0, -1, 3, 2, -1, 0, 0, false);

        if (this.a(par1World, 2, 0, -1, par3StructureBoundingBox) == 0 && this.a(par1World, 2, -1, -1, par3StructureBoundingBox) != 0)
        {
            this.a(par1World, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, par3StructureBoundingBox);
        }

        for (var8 = 0; var8 < 5; ++var8)
        {
            for (var9 = 0; var9 < 9; ++var9)
            {
                this.b(par1World, var9, 7, var8, par3StructureBoundingBox);
                this.b(par1World, Block.COBBLESTONE.id, 0, var9, -1, var8, par3StructureBoundingBox);
            }
        }

        for (var8 = 5; var8 < 11; ++var8)
        {
            for (var9 = 2; var9 < 9; ++var9)
            {
                this.b(par1World, var9, 7, var8, par3StructureBoundingBox);
                this.b(par1World, Block.COBBLESTONE.id, 0, var9, -1, var8, par3StructureBoundingBox);
            }
        }

        this.a(par1World, par3StructureBoundingBox, 4, 1, 2, 2);
        return true;
    }
}
