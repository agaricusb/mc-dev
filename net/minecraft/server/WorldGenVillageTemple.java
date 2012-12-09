package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenVillageTemple extends WorldGenVillagePiece
{
    private int a = -1;

    public WorldGenVillageTemple(WorldGenVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.f = par5;
        this.e = par4StructureBoundingBox;
    }

    public static WorldGenVillageTemple a(WorldGenVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        StructureBoundingBox var8 = StructureBoundingBox.a(par3, par4, par5, 0, 0, 0, 5, 12, 9, par6);
        return a(var8) && StructurePiece.a(par1List, var8) == null ? new WorldGenVillageTemple(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
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

            this.e.a(0, this.a - this.e.e + 12 - 1, 0);
        }

        this.a(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 3, 7, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 1, 5, 1, 3, 9, 3, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 1, 0, 0, 3, 0, 8, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 1, 0, 3, 10, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 10, 3, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 10, 3, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 0, 4, 0, 4, 7, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 0, 4, 4, 4, 7, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 1, 8, 3, 4, 8, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 5, 4, 3, 10, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 5, 5, 3, 5, 7, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 9, 0, 4, 9, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 4, 0, 4, 4, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(par1World, Block.COBBLESTONE.id, 0, 0, 11, 2, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 4, 11, 2, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 2, 11, 0, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 2, 11, 4, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 1, 1, 6, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 1, 1, 7, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 2, 1, 7, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 3, 1, 6, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE.id, 0, 3, 1, 7, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 1, 1, 5, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 2, 1, 6, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 3, 1, 5, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 1), 1, 2, 7, par3StructureBoundingBox);
        this.a(par1World, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 0), 3, 2, 7, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 2, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 3, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 4, 2, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 4, 3, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 6, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 7, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 4, 6, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 4, 7, 2, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 2, 6, 0, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 2, 7, 0, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 2, 6, 4, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 2, 7, 4, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 0, 3, 6, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 4, 3, 6, par3StructureBoundingBox);
        this.a(par1World, Block.THIN_GLASS.id, 0, 2, 3, 8, par3StructureBoundingBox);
        this.a(par1World, Block.TORCH.id, 0, 2, 4, 7, par3StructureBoundingBox);
        this.a(par1World, Block.TORCH.id, 0, 1, 4, 6, par3StructureBoundingBox);
        this.a(par1World, Block.TORCH.id, 0, 3, 4, 6, par3StructureBoundingBox);
        this.a(par1World, Block.TORCH.id, 0, 2, 4, 5, par3StructureBoundingBox);
        int var4 = this.c(Block.LADDER.id, 4);
        int var5;

        for (var5 = 1; var5 <= 9; ++var5)
        {
            this.a(par1World, Block.LADDER.id, var4, 3, var5, 3, par3StructureBoundingBox);
        }

        this.a(par1World, 0, 0, 2, 1, 0, par3StructureBoundingBox);
        this.a(par1World, 0, 0, 2, 2, 0, par3StructureBoundingBox);
        this.a(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, this.c(Block.WOODEN_DOOR.id, 1));

        if (this.a(par1World, 2, 0, -1, par3StructureBoundingBox) == 0 && this.a(par1World, 2, -1, -1, par3StructureBoundingBox) != 0)
        {
            this.a(par1World, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, par3StructureBoundingBox);
        }

        for (var5 = 0; var5 < 9; ++var5)
        {
            for (int var6 = 0; var6 < 5; ++var6)
            {
                this.b(par1World, var6, 12, var5, par3StructureBoundingBox);
                this.b(par1World, Block.COBBLESTONE.id, 0, var6, -1, var5, par3StructureBoundingBox);
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
        return 2;
    }
}
