package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece6 extends WorldGenNetherPiece
{
    public WorldGenNetherPiece6(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
        this.a((WorldGenNetherPiece15) par1StructureComponent, par2List, par3Random, 5, 3, true);
    }

    /**
     * Creates and returns a new component piece. Or null if it could not find enough room to place it.
     */
    public static WorldGenNetherPiece6 a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -5, -3, 0, 13, 14, 13, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenNetherPiece6(par6, par1Random, var7, par5) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        this.a(par1World, par3StructureBoundingBox, 0, 3, 0, 12, 4, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 5, 0, 12, 13, 12, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 0, 5, 0, 1, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 11, 5, 0, 12, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 2, 5, 11, 4, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 8, 5, 11, 10, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 5, 9, 11, 7, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 12, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 12, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 5, 9, 0, 7, 12, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 2, 11, 2, 10, 12, 10, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 5, 8, 0, 7, 8, 0, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        int var4;

        for (var4 = 1; var4 <= 11; var4 += 2)
        {
            this.a(par1World, par3StructureBoundingBox, var4, 10, 0, var4, 11, 0, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            this.a(par1World, par3StructureBoundingBox, var4, 10, 12, var4, 11, 12, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            this.a(par1World, par3StructureBoundingBox, 0, 10, var4, 0, 11, var4, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            this.a(par1World, par3StructureBoundingBox, 12, 10, var4, 12, 11, var4, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            this.a(par1World, Block.NETHER_BRICK.id, 0, var4, 13, 0, par3StructureBoundingBox);
            this.a(par1World, Block.NETHER_BRICK.id, 0, var4, 13, 12, par3StructureBoundingBox);
            this.a(par1World, Block.NETHER_BRICK.id, 0, 0, 13, var4, par3StructureBoundingBox);
            this.a(par1World, Block.NETHER_BRICK.id, 0, 12, 13, var4, par3StructureBoundingBox);
            this.a(par1World, Block.NETHER_FENCE.id, 0, var4 + 1, 13, 0, par3StructureBoundingBox);
            this.a(par1World, Block.NETHER_FENCE.id, 0, var4 + 1, 13, 12, par3StructureBoundingBox);
            this.a(par1World, Block.NETHER_FENCE.id, 0, 0, 13, var4 + 1, par3StructureBoundingBox);
            this.a(par1World, Block.NETHER_FENCE.id, 0, 12, 13, var4 + 1, par3StructureBoundingBox);
        }

        this.a(par1World, Block.NETHER_FENCE.id, 0, 0, 13, 0, par3StructureBoundingBox);
        this.a(par1World, Block.NETHER_FENCE.id, 0, 0, 13, 12, par3StructureBoundingBox);
        this.a(par1World, Block.NETHER_FENCE.id, 0, 0, 13, 0, par3StructureBoundingBox);
        this.a(par1World, Block.NETHER_FENCE.id, 0, 12, 13, 0, par3StructureBoundingBox);

        for (var4 = 3; var4 <= 9; var4 += 2)
        {
            this.a(par1World, par3StructureBoundingBox, 1, 7, var4, 1, 8, var4, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            this.a(par1World, par3StructureBoundingBox, 11, 7, var4, 11, 8, var4, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        }

        this.a(par1World, par3StructureBoundingBox, 4, 2, 0, 8, 2, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 4, 12, 2, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 0, 0, 8, 1, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 0, 9, 8, 1, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 0, 4, 3, 1, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 9, 0, 4, 12, 1, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        int var5;

        for (var4 = 4; var4 <= 8; ++var4)
        {
            for (var5 = 0; var5 <= 2; ++var5)
            {
                this.b(par1World, Block.NETHER_BRICK.id, 0, var4, -1, var5, par3StructureBoundingBox);
                this.b(par1World, Block.NETHER_BRICK.id, 0, var4, -1, 12 - var5, par3StructureBoundingBox);
            }
        }

        for (var4 = 0; var4 <= 2; ++var4)
        {
            for (var5 = 4; var5 <= 8; ++var5)
            {
                this.b(par1World, Block.NETHER_BRICK.id, 0, var4, -1, var5, par3StructureBoundingBox);
                this.b(par1World, Block.NETHER_BRICK.id, 0, 12 - var4, -1, var5, par3StructureBoundingBox);
            }
        }

        this.a(par1World, par3StructureBoundingBox, 5, 5, 5, 7, 5, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 6, 1, 6, 6, 4, 6, 0, 0, false);
        this.a(par1World, Block.NETHER_BRICK.id, 0, 6, 0, 6, par3StructureBoundingBox);
        this.a(par1World, Block.LAVA.id, 0, 6, 5, 6, par3StructureBoundingBox);
        var4 = this.a(6, 6);
        var5 = this.a(5);
        int var6 = this.b(6, 6);

        if (par3StructureBoundingBox.b(var4, var5, var6))
        {
            par1World.d = true;
            Block.byId[Block.LAVA.id].b(par1World, var4, var5, var6, par2Random);
            par1World.d = false;
        }

        return true;
    }
}
