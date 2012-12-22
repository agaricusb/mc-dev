package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageFarm2 extends WorldGenVillagePiece
{
    private int a = -1;

    /** First crop type for this field. */
    private int b;

    /** Second crop type for this field. */
    private int c;

    /** Third crop type for this field. */
    private int d;

    /** Fourth crop type for this field. */
    private int h;

    public WorldGenVillageFarm2(WorldGenVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.f = par5;
        this.e = par4StructureBoundingBox;
        this.b = this.a(par3Random);
        this.c = this.a(par3Random);
        this.d = this.a(par3Random);
        this.h = this.a(par3Random);
    }

    /**
     * Returns a crop type to be planted on this field.
     */
    private int a(Random par1Random)
    {
        switch (par1Random.nextInt(5))
        {
            case 0:
                return Block.CARROTS.id;

            case 1:
                return Block.POTATOES.id;

            default:
                return Block.CROPS.id;
        }
    }

    public static WorldGenVillageFarm2 a(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        StructureBoundingBox var8 = StructureBoundingBox.a(par3, par4, par5, 0, 0, 0, 13, 4, 9, par6);
        return a(var8) && StructurePiece.a(par1List, var8) == null ? new WorldGenVillageFarm2(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
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

        this.a(par1World, par3StructureBoundingBox, 0, 1, 0, 12, 4, 8, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 7, Block.SOIL.id, Block.SOIL.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 0, 1, 5, 0, 7, Block.SOIL.id, Block.SOIL.id, false);
        this.a(par1World, par3StructureBoundingBox, 7, 0, 1, 8, 0, 7, Block.SOIL.id, Block.SOIL.id, false);
        this.a(par1World, par3StructureBoundingBox, 10, 0, 1, 11, 0, 7, Block.SOIL.id, Block.SOIL.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 0, 8, Block.LOG.id, Block.LOG.id, false);
        this.a(par1World, par3StructureBoundingBox, 6, 0, 0, 6, 0, 8, Block.LOG.id, Block.LOG.id, false);
        this.a(par1World, par3StructureBoundingBox, 12, 0, 0, 12, 0, 8, Block.LOG.id, Block.LOG.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 0, 0, 11, 0, 0, Block.LOG.id, Block.LOG.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 0, 8, 11, 0, 8, Block.LOG.id, Block.LOG.id, false);
        this.a(par1World, par3StructureBoundingBox, 3, 0, 1, 3, 0, 7, Block.WATER.id, Block.WATER.id, false);
        this.a(par1World, par3StructureBoundingBox, 9, 0, 1, 9, 0, 7, Block.WATER.id, Block.WATER.id, false);
        int var4;

        for (var4 = 1; var4 <= 7; ++var4)
        {
            this.a(par1World, this.b, MathHelper.nextInt(par2Random, 2, 7), 1, 1, var4, par3StructureBoundingBox);
            this.a(par1World, this.b, MathHelper.nextInt(par2Random, 2, 7), 2, 1, var4, par3StructureBoundingBox);
            this.a(par1World, this.c, MathHelper.nextInt(par2Random, 2, 7), 4, 1, var4, par3StructureBoundingBox);
            this.a(par1World, this.c, MathHelper.nextInt(par2Random, 2, 7), 5, 1, var4, par3StructureBoundingBox);
            this.a(par1World, this.d, MathHelper.nextInt(par2Random, 2, 7), 7, 1, var4, par3StructureBoundingBox);
            this.a(par1World, this.d, MathHelper.nextInt(par2Random, 2, 7), 8, 1, var4, par3StructureBoundingBox);
            this.a(par1World, this.h, MathHelper.nextInt(par2Random, 2, 7), 10, 1, var4, par3StructureBoundingBox);
            this.a(par1World, this.h, MathHelper.nextInt(par2Random, 2, 7), 11, 1, var4, par3StructureBoundingBox);
        }

        for (var4 = 0; var4 < 9; ++var4)
        {
            for (int var5 = 0; var5 < 13; ++var5)
            {
                this.b(par1World, var5, 4, var4, par3StructureBoundingBox);
                this.b(par1World, Block.DIRT.id, 0, var5, -1, var4, par3StructureBoundingBox);
            }
        }

        return true;
    }
}
