package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece13 extends WorldGenNetherPiece
{
    public WorldGenNetherPiece13(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
        this.a((WorldGenNetherPiece15) par1StructureComponent, par2List, par3Random, 2, 0, false);
        this.b((WorldGenNetherPiece15) par1StructureComponent, par2List, par3Random, 0, 2, false);
        this.c((WorldGenNetherPiece15) par1StructureComponent, par2List, par3Random, 0, 2, false);
    }

    /**
     * Creates and returns a new component piece. Or null if it could not find enough room to place it.
     */
    public static WorldGenNetherPiece13 a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -2, 0, 0, 7, 9, 7, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenNetherPiece13(par6, par1Random, var7, par5) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 6, 1, 6, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 7, 6, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 0, 1, 6, 0, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 6, 1, 6, 6, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 5, 2, 0, 6, 6, 0, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 5, 2, 6, 6, 6, 6, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 6, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 5, 0, 6, 6, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 6, 2, 0, 6, 6, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 6, 2, 5, 6, 6, 6, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 2, 6, 0, 4, 6, 0, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 5, 0, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 2, 6, 6, 4, 6, 6, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 2, 5, 6, 4, 5, 6, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 6, 2, 0, 6, 4, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 5, 2, 0, 5, 4, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 6, 6, 2, 6, 6, 4, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 6, 5, 2, 6, 5, 4, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);

        for (int var4 = 0; var4 <= 6; ++var4)
        {
            for (int var5 = 0; var5 <= 6; ++var5)
            {
                this.b(par1World, Block.NETHER_BRICK.id, 0, var4, -1, var5, par3StructureBoundingBox);
            }
        }

        return true;
    }
}
