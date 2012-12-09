package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece9 extends WorldGenNetherPiece
{
    public WorldGenNetherPiece9(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
    public static WorldGenNetherPiece9 a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenNetherPiece9(par6, par1Random, var7, par5) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 4, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 4, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 4, 1, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 3, 3, 0, 4, 3, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 3, 1, 4, 4, 1, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 4, 3, 3, 4, 4, 3, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);

        for (int var4 = 0; var4 <= 4; ++var4)
        {
            for (int var5 = 0; var5 <= 4; ++var5)
            {
                this.b(par1World, Block.NETHER_BRICK.id, 0, var4, -1, var5, par3StructureBoundingBox);
            }
        }

        return true;
    }
}
