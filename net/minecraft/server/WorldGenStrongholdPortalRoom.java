package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdPortalRoom extends WorldGenStrongholdPiece
{
    private boolean a;

    public WorldGenStrongholdPortalRoom(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
        if (par1StructureComponent != null)
        {
            ((WorldGenStrongholdStart)par1StructureComponent).b = this;
        }
    }

    public static WorldGenStrongholdPortalRoom a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox var7 = StructureBoundingBox.a(par2, par3, par4, -4, -1, 0, 11, 8, 16, par5);
        return a(var7) && StructurePiece.a(par0List, var7) == null ? new WorldGenStrongholdPortalRoom(par6, par1Random, var7, par5) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        this.a(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 7, 15, false, par2Random, WorldGenStrongholdPieces.b());
        this.a(par1World, par2Random, par3StructureBoundingBox, WorldGenStrongholdDoorType.c, 4, 1, 0);
        byte var4 = 6;
        this.a(par1World, par3StructureBoundingBox, 1, var4, 1, 1, var4, 14, false, par2Random, WorldGenStrongholdPieces.b());
        this.a(par1World, par3StructureBoundingBox, 9, var4, 1, 9, var4, 14, false, par2Random, WorldGenStrongholdPieces.b());
        this.a(par1World, par3StructureBoundingBox, 2, var4, 1, 8, var4, 2, false, par2Random, WorldGenStrongholdPieces.b());
        this.a(par1World, par3StructureBoundingBox, 2, var4, 14, 8, var4, 14, false, par2Random, WorldGenStrongholdPieces.b());
        this.a(par1World, par3StructureBoundingBox, 1, 1, 1, 2, 1, 4, false, par2Random, WorldGenStrongholdPieces.b());
        this.a(par1World, par3StructureBoundingBox, 8, 1, 1, 9, 1, 4, false, par2Random, WorldGenStrongholdPieces.b());
        this.a(par1World, par3StructureBoundingBox, 1, 1, 1, 1, 1, 3, Block.LAVA.id, Block.LAVA.id, false);
        this.a(par1World, par3StructureBoundingBox, 9, 1, 1, 9, 1, 3, Block.LAVA.id, Block.LAVA.id, false);
        this.a(par1World, par3StructureBoundingBox, 3, 1, 8, 7, 1, 12, false, par2Random, WorldGenStrongholdPieces.b());
        this.a(par1World, par3StructureBoundingBox, 4, 1, 9, 6, 1, 11, Block.LAVA.id, Block.LAVA.id, false);
        int var5;

        for (var5 = 3; var5 < 14; var5 += 2)
        {
            this.a(par1World, par3StructureBoundingBox, 0, 3, var5, 0, 4, var5, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
            this.a(par1World, par3StructureBoundingBox, 10, 3, var5, 10, 4, var5, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
        }

        for (var5 = 2; var5 < 9; var5 += 2)
        {
            this.a(par1World, par3StructureBoundingBox, var5, 3, 15, var5, 4, 15, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
        }

        var5 = this.c(Block.STONE_STAIRS.id, 3);
        this.a(par1World, par3StructureBoundingBox, 4, 1, 5, 6, 1, 7, false, par2Random, WorldGenStrongholdPieces.b());
        this.a(par1World, par3StructureBoundingBox, 4, 2, 6, 6, 2, 7, false, par2Random, WorldGenStrongholdPieces.b());
        this.a(par1World, par3StructureBoundingBox, 4, 3, 7, 6, 3, 7, false, par2Random, WorldGenStrongholdPieces.b());

        for (int var6 = 4; var6 <= 6; ++var6)
        {
            this.a(par1World, Block.STONE_STAIRS.id, var5, var6, 1, 4, par3StructureBoundingBox);
            this.a(par1World, Block.STONE_STAIRS.id, var5, var6, 2, 5, par3StructureBoundingBox);
            this.a(par1World, Block.STONE_STAIRS.id, var5, var6, 3, 6, par3StructureBoundingBox);
        }

        byte var14 = 2;
        byte var7 = 0;
        byte var8 = 3;
        byte var9 = 1;

        switch (this.f)
        {
            case 0:
                var14 = 0;
                var7 = 2;
                break;

            case 1:
                var14 = 1;
                var7 = 3;
                var8 = 0;
                var9 = 2;

            case 2:
            default:
                break;

            case 3:
                var14 = 3;
                var7 = 1;
                var8 = 0;
                var9 = 2;
        }

        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var14 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 4, 3, 8, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var14 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 3, 8, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var14 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 6, 3, 8, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var7 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 4, 3, 12, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var7 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 3, 12, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var7 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 6, 3, 12, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var8 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 9, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var8 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 10, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var8 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 11, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var9 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 9, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var9 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 10, par3StructureBoundingBox);
        this.a(par1World, Block.ENDER_PORTAL_FRAME.id, var9 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 11, par3StructureBoundingBox);

        if (!this.a)
        {
            int var13 = this.a(3);
            int var10 = this.a(5, 6);
            int var11 = this.b(5, 6);

            if (par3StructureBoundingBox.b(var10, var13, var11))
            {
                this.a = true;
                par1World.setTypeId(var10, var13, var11, Block.MOB_SPAWNER.id);
                TileEntityMobSpawner var12 = (TileEntityMobSpawner)par1World.getTileEntity(var10, var13, var11);

                if (var12 != null)
                {
                    var12.a("Silverfish");
                }
            }
        }

        return true;
    }
}
