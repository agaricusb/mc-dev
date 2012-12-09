package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece3 extends WorldGenNetherPiece
{
    public WorldGenNetherPiece3(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
        this.a((WorldGenNetherPiece15) par1StructureComponent, par2List, par3Random, 1, 3, false);
    }

    /**
     * Creates and returns a new component piece. Or null if it could not find enough room to place it.
     */
    public static WorldGenNetherPiece3 a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -1, -3, 0, 5, 10, 19, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenNetherPiece3(par6, par1Random, var7, par5) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        this.a(par1World, par3StructureBoundingBox, 0, 3, 0, 4, 4, 18, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 5, 0, 3, 7, 18, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, 18, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, 18, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 2, 5, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 13, 4, 2, 18, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 0, 15, 4, 1, 18, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);

        for (int var4 = 0; var4 <= 4; ++var4)
        {
            for (int var5 = 0; var5 <= 2; ++var5)
            {
                this.b(par1World, Block.NETHER_BRICK.id, 0, var4, -1, var5, par3StructureBoundingBox);
                this.b(par1World, Block.NETHER_BRICK.id, 0, var4, -1, 18 - var5, par3StructureBoundingBox);
            }
        }

        this.a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 4, 1, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 3, 4, 0, 4, 4, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 3, 14, 0, 4, 14, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 1, 17, 0, 4, 17, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 4, 1, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 3, 4, 4, 4, 4, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 3, 14, 4, 4, 14, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 1, 17, 4, 4, 17, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        return true;
    }
}
