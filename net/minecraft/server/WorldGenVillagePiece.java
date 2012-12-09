package net.minecraft.server;

import java.util.List;
import java.util.Random;

abstract class WorldGenVillagePiece extends StructurePiece
{
    /** The number of villagers that have been spawned in this component. */
    private int a;

    /** The starting piece of the village. */
    protected WorldGenVillageStartPiece k;

    protected WorldGenVillagePiece(WorldGenVillageStartPiece par1ComponentVillageStartPiece, int par2)
    {
        super(par2);
        this.k = par1ComponentVillageStartPiece;
    }

    /**
     * Gets the next village component, with the bounding box shifted -1 in the X and Z direction.
     */
    protected StructurePiece a(WorldGenVillageStartPiece par1ComponentVillageStartPiece, List par2List, Random par3Random, int par4, int par5)
    {
        switch (this.f)
        {
            case 0:
                return WorldGenVillagePieces.a(par1ComponentVillageStartPiece, par2List, par3Random, this.e.a - 1, this.e.b + par4, this.e.c + par5, 1, this.c());

            case 1:
                return WorldGenVillagePieces.a(par1ComponentVillageStartPiece, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.c - 1, 2, this.c());

            case 2:
                return WorldGenVillagePieces.a(par1ComponentVillageStartPiece, par2List, par3Random, this.e.a - 1, this.e.b + par4, this.e.c + par5, 1, this.c());

            case 3:
                return WorldGenVillagePieces.a(par1ComponentVillageStartPiece, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.c - 1, 2, this.c());

            default:
                return null;
        }
    }

    /**
     * Gets the next village component, with the bounding box shifted +1 in the X and Z direction.
     */
    protected StructurePiece b(WorldGenVillageStartPiece par1ComponentVillageStartPiece, List par2List, Random par3Random, int par4, int par5)
    {
        switch (this.f)
        {
            case 0:
                return WorldGenVillagePieces.a(par1ComponentVillageStartPiece, par2List, par3Random, this.e.d + 1, this.e.b + par4, this.e.c + par5, 3, this.c());

            case 1:
                return WorldGenVillagePieces.a(par1ComponentVillageStartPiece, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.f + 1, 0, this.c());

            case 2:
                return WorldGenVillagePieces.a(par1ComponentVillageStartPiece, par2List, par3Random, this.e.d + 1, this.e.b + par4, this.e.c + par5, 3, this.c());

            case 3:
                return WorldGenVillagePieces.a(par1ComponentVillageStartPiece, par2List, par3Random, this.e.a + par5, this.e.b + par4, this.e.f + 1, 0, this.c());

            default:
                return null;
        }
    }

    /**
     * Discover the y coordinate that will serve as the ground level of the supplied BoundingBox. (A median of all the
     * levels in the BB's horizontal rectangle).
     */
    protected int b(World par1World, StructureBoundingBox par2StructureBoundingBox)
    {
        int var3 = 0;
        int var4 = 0;

        for (int var5 = this.e.c; var5 <= this.e.f; ++var5)
        {
            for (int var6 = this.e.a; var6 <= this.e.d; ++var6)
            {
                if (par2StructureBoundingBox.b(var6, 64, var5))
                {
                    var3 += Math.max(par1World.i(var6, var5), par1World.worldProvider.getSeaLevel());
                    ++var4;
                }
            }
        }

        if (var4 == 0)
        {
            return -1;
        }
        else
        {
            return var3 / var4;
        }
    }

    protected static boolean a(StructureBoundingBox par0StructureBoundingBox)
    {
        return par0StructureBoundingBox != null && par0StructureBoundingBox.b > 10;
    }

    /**
     * Spawns a number of villagers in this component. Parameters: world, component bounding box, x offset, y offset, z
     * offset, number of villagers
     */
    protected void a(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6)
    {
        if (this.a < par6)
        {
            for (int var7 = this.a; var7 < par6; ++var7)
            {
                int var8 = this.a(par3 + var7, par5);
                int var9 = this.a(par4);
                int var10 = this.b(par3 + var7, par5);

                if (!par2StructureBoundingBox.b(var8, var9, var10))
                {
                    break;
                }

                ++this.a;
                EntityVillager var11 = new EntityVillager(par1World, this.b(var7));
                var11.setPositionRotation((double) var8 + 0.5D, (double) var9, (double) var10 + 0.5D, 0.0F, 0.0F);
                par1World.addEntity(var11);
            }
        }
    }

    /**
     * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
     */
    protected int b(int par1)
    {
        return 0;
    }

    /**
     * Gets the replacement block for the current biome
     */
    protected int d(int par1, int par2)
    {
        if (this.k.b)
        {
            if (par1 == Block.LOG.id)
            {
                return Block.SANDSTONE.id;
            }

            if (par1 == Block.COBBLESTONE.id)
            {
                return Block.SANDSTONE.id;
            }

            if (par1 == Block.WOOD.id)
            {
                return Block.SANDSTONE.id;
            }

            if (par1 == Block.WOOD_STAIRS.id)
            {
                return Block.SANDSTONE_STAIRS.id;
            }

            if (par1 == Block.COBBLESTONE_STAIRS.id)
            {
                return Block.SANDSTONE_STAIRS.id;
            }

            if (par1 == Block.GRAVEL.id)
            {
                return Block.SANDSTONE.id;
            }
        }

        return par1;
    }

    /**
     * Gets the replacement block metadata for the current biome
     */
    protected int e(int par1, int par2)
    {
        if (this.k.b)
        {
            if (par1 == Block.LOG.id)
            {
                return 0;
            }

            if (par1 == Block.COBBLESTONE.id)
            {
                return 0;
            }

            if (par1 == Block.WOOD.id)
            {
                return 2;
            }
        }

        return par2;
    }

    /**
     * current Position depends on currently set Coordinates mode, is computed here
     */
    protected void a(World par1World, int par2, int par3, int par4, int par5, int par6, StructureBoundingBox par7StructureBoundingBox)
    {
        int var8 = this.d(par2, par3);
        int var9 = this.e(par2, par3);
        super.a(par1World, var8, var9, par4, par5, par6, par7StructureBoundingBox);
    }

    /**
     * arguments: (World worldObj, StructureBoundingBox structBB, int minX, int minY, int minZ, int maxX, int maxY, int
     * maxZ, int placeBlockId, int replaceBlockId, boolean alwaysreplace)
     */
    protected void a(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, int par9, int par10, boolean par11)
    {
        int var12 = this.d(par9, 0);
        int var13 = this.e(par9, 0);
        int var14 = this.d(par10, 0);
        int var15 = this.e(par10, 0);
        super.a(par1World, par2StructureBoundingBox, par3, par4, par5, par6, par7, par8, var12, var13, var14, var15, par11);
    }

    /**
     * Overwrites air and liquids from selected position downwards, stops at hitting anything else.
     */
    protected void b(World par1World, int par2, int par3, int par4, int par5, int par6, StructureBoundingBox par7StructureBoundingBox)
    {
        int var8 = this.d(par2, par3);
        int var9 = this.e(par2, par3);
        super.b(par1World, var8, var9, par4, par5, par6, par7StructureBoundingBox);
    }
}
