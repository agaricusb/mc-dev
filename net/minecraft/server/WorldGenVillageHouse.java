package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageHouse extends WorldGenVillagePiece
{
    private int a = -1;
    private final boolean b;

    public WorldGenVillageHouse(WorldGenVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.f = par5;
        this.e = par4StructureBoundingBox;
        this.b = par3Random.nextBoolean();
    }

    public static WorldGenVillageHouse a(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        StructureBoundingBox var8 = StructureBoundingBox.a(par3, par4, par5, 0, 0, 0, 5, 6, 5, par6);
        return StructurePiece.a(par1List, var8) != null ? null : new WorldGenVillageHouse(par0ComponentVillageStartPiece, par7, par2Random, var8, par6);
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

            this.e.a(0, this.a - this.e.e + 6 - 1, 0);
        }

        this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 0, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 4, 0, 4, 4, 4, Block.LOG.id, Block.LOG.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 4, 1, 3, 4, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, Block.COBBLESTONE.id, 0, 0, 1, 0, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 0, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 0, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 4, 1, 0, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 4, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 4, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 0, 1, 4, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 0, 2, 4, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 0, 3, 4, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 4, 1, 4, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 4, 2, 4, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 4, 3, 4, par3StructureBoundingBox);
        this.a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 1, 4, 3, 3, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 2, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 2, 2, 4, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 4, 2, 2, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 1, 1, 0, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 1, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 1, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 2, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 3, 3, 0, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 3, 2, 0, par3StructureBoundingBox);
        this.a(par1World, Block.WOOD.id, 0, 3, 1, 0, par3StructureBoundingBox);

        if (this.a(par1World, 2, 0, -1, par3StructureBoundingBox) == 0 && this.a(par1World, 2, -1, -1, par3StructureBoundingBox) != 0)
        {
            this.a(par1World, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, par3StructureBoundingBox);
        }

        this.a(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 3, 3, 0, 0, false);

        if (this.b)
        {
            this.a(par1World, Block.FENCE.id, 0, 0, 5, 0, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 1, 5, 0, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 2, 5, 0, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 3, 5, 0, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 4, 5, 0, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 0, 5, 4, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 1, 5, 4, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 2, 5, 4, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 3, 5, 4, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 4, 5, 4, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 4, 5, 1, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 4, 5, 2, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 4, 5, 3, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 0, 5, 1, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 0, 5, 2, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 0, 5, 3, par3StructureBoundingBox);
        }

        int var4;

        if (this.b)
        {
            var4 = this.c(Block.LADDER.id, 3);
            this.a(par1World, Block.LADDER.id, var4, 3, 1, 3, par3StructureBoundingBox);
            this.a(par1World, Block.LADDER.id, var4, 3, 2, 3, par3StructureBoundingBox);
            this.a(par1World, Block.LADDER.id, var4, 3, 3, 3, par3StructureBoundingBox);
            this.a(par1World, Block.LADDER.id, var4, 3, 4, 3, par3StructureBoundingBox);
        }

        this.a(par1World, Block.TORCH.id, 0, 2, 3, 1, par3StructureBoundingBox);

        for (var4 = 0; var4 < 5; ++var4)
        {
            for (int var5 = 0; var5 < 5; ++var5)
            {
                this.b(par1World, var5, 6, var4, par3StructureBoundingBox);
                this.b(par1World, Block.COBBLESTONE.id, 0, var5, -1, var4, par3StructureBoundingBox);
            }
        }

        this.a(par1World, par3StructureBoundingBox, 1, 1, 2, 1);
        return true;
    }
}
