package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece4 extends WorldGenNetherPiece
{
    public WorldGenNetherPiece4(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.e = par3StructureBoundingBox;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void a(StructurePiece par1StructureComponent, List par2List, Random par3Random)
    {
        this.a((WorldGenNetherPiece15) par1StructureComponent, par2List, par3Random, 1, 0, true);
    }

    /**
     * Creates and returns a new component piece. Or null if it could not find enough room to place it.
     */
    public static WorldGenNetherPiece4 a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -1, -7, 0, 5, 14, 10, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenNetherPiece4(par6, par1Random, var7, par5) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        int var4 = this.c(Block.NETHER_BRICK_STAIRS.id, 2);

        for (int var5 = 0; var5 <= 9; ++var5)
        {
            int var6 = Math.max(1, 7 - var5);
            int var7 = Math.min(Math.max(var6 + 5, 14 - var5), 13);
            int var8 = var5;
            this.a(par1World, par3StructureBoundingBox, 0, 0, var5, 4, var6, var5, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            this.a(par1World, par3StructureBoundingBox, 1, var6 + 1, var5, 3, var7 - 1, var5, 0, 0, false);

            if (var5 <= 6)
            {
                this.a(par1World, Block.NETHER_BRICK_STAIRS.id, var4, 1, var6 + 1, var5, par3StructureBoundingBox);
                this.a(par1World, Block.NETHER_BRICK_STAIRS.id, var4, 2, var6 + 1, var5, par3StructureBoundingBox);
                this.a(par1World, Block.NETHER_BRICK_STAIRS.id, var4, 3, var6 + 1, var5, par3StructureBoundingBox);
            }

            this.a(par1World, par3StructureBoundingBox, 0, var7, var5, 4, var7, var5, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            this.a(par1World, par3StructureBoundingBox, 0, var6 + 1, var5, 0, var7 - 1, var5, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            this.a(par1World, par3StructureBoundingBox, 4, var6 + 1, var5, 4, var7 - 1, var5, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);

            if ((var5 & 1) == 0)
            {
                this.a(par1World, par3StructureBoundingBox, 0, var6 + 2, var5, 0, var6 + 3, var5, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
                this.a(par1World, par3StructureBoundingBox, 4, var6 + 2, var5, 4, var6 + 3, var5, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            }

            for (int var9 = 0; var9 <= 4; ++var9)
            {
                this.b(par1World, Block.NETHER_BRICK.id, 0, var9, -1, var8, par3StructureBoundingBox);
            }
        }

        return true;
    }
}
