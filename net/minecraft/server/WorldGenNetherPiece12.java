package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece12 extends WorldGenNetherPiece
{
    private boolean a;

    public WorldGenNetherPiece12(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        this.f = par4;
        this.e = par3StructureBoundingBox;
    }

    /**
     * Creates and returns a new component piece. Or null if it could not find enough room to place it.
     */
    public static WorldGenNetherPiece12 a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -2, 0, 0, 7, 8, 9, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenNetherPiece12(par6, par1Random, var7, par5) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        this.a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 7, 7, 0, 0, false);
        this.a(par1World, par3StructureBoundingBox, 1, 0, 0, 5, 1, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 2, 1, 5, 2, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 3, 2, 5, 3, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 4, 3, 5, 4, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 2, 0, 1, 4, 2, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 5, 2, 0, 5, 4, 2, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 5, 2, 1, 5, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 5, 5, 2, 5, 5, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 0, 5, 3, 0, 5, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 6, 5, 3, 6, 5, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 5, 8, 5, 5, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(par1World, Block.NETHER_FENCE.id, 0, 1, 6, 3, par3StructureBoundingBox);
        this.a(par1World, Block.NETHER_FENCE.id, 0, 5, 6, 3, par3StructureBoundingBox);
        this.a(par1World, par3StructureBoundingBox, 0, 6, 3, 0, 6, 8, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 6, 6, 3, 6, 6, 8, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 1, 6, 8, 5, 7, 8, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(par1World, par3StructureBoundingBox, 2, 8, 8, 4, 8, 8, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        int var4;
        int var5;

        if (!this.a)
        {
            var4 = this.a(5);
            var5 = this.a(3, 5);
            int var6 = this.b(3, 5);

            if (par3StructureBoundingBox.b(var5, var4, var6))
            {
                this.a = true;
                par1World.setTypeId(var5, var4, var6, Block.MOB_SPAWNER.id);
                TileEntityMobSpawner var7 = (TileEntityMobSpawner)par1World.getTileEntity(var5, var4, var6);

                if (var7 != null)
                {
                    var7.a("Blaze");
                }
            }
        }

        for (var4 = 0; var4 <= 6; ++var4)
        {
            for (var5 = 0; var5 <= 6; ++var5)
            {
                this.b(par1World, Block.NETHER_BRICK.id, 0, var4, -1, var5, par3StructureBoundingBox);
            }
        }

        return true;
    }
}
