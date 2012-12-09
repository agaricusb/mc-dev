package net.minecraft.server;

import java.util.Random;

public class WorldGenWitchHut extends WorldGenScatteredPiece
{
    /** Whether this swamp hut has a witch. */
    private boolean h;

    public WorldGenWitchHut(Random par1Random, int par2, int par3)
    {
        super(par1Random, par2, 64, par3, 7, 5, 9);
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean a(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        if (!this.a(par1World, par3StructureBoundingBox, 0))
        {
            return false;
        }
        else
        {
            this.a(par1World, par3StructureBoundingBox, 1, 1, 1, 5, 1, 7, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
            this.a(par1World, par3StructureBoundingBox, 1, 4, 2, 5, 4, 7, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
            this.a(par1World, par3StructureBoundingBox, 2, 1, 0, 4, 1, 0, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
            this.a(par1World, par3StructureBoundingBox, 2, 2, 2, 3, 3, 2, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
            this.a(par1World, par3StructureBoundingBox, 1, 2, 3, 1, 3, 6, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
            this.a(par1World, par3StructureBoundingBox, 5, 2, 3, 5, 3, 6, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
            this.a(par1World, par3StructureBoundingBox, 2, 2, 7, 4, 3, 7, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
            this.a(par1World, par3StructureBoundingBox, 1, 0, 2, 1, 3, 2, Block.LOG.id, Block.LOG.id, false);
            this.a(par1World, par3StructureBoundingBox, 5, 0, 2, 5, 3, 2, Block.LOG.id, Block.LOG.id, false);
            this.a(par1World, par3StructureBoundingBox, 1, 0, 7, 1, 3, 7, Block.LOG.id, Block.LOG.id, false);
            this.a(par1World, par3StructureBoundingBox, 5, 0, 7, 5, 3, 7, Block.LOG.id, Block.LOG.id, false);
            this.a(par1World, Block.FENCE.id, 0, 2, 3, 2, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 3, 3, 7, par3StructureBoundingBox);
            this.a(par1World, 0, 0, 1, 3, 4, par3StructureBoundingBox);
            this.a(par1World, 0, 0, 5, 3, 4, par3StructureBoundingBox);
            this.a(par1World, 0, 0, 5, 3, 5, par3StructureBoundingBox);
            this.a(par1World, Block.FLOWER_POT.id, 7, 1, 3, 5, par3StructureBoundingBox);
            this.a(par1World, Block.WORKBENCH.id, 0, 3, 2, 6, par3StructureBoundingBox);
            this.a(par1World, Block.CAULDRON.id, 0, 4, 2, 6, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 1, 2, 1, par3StructureBoundingBox);
            this.a(par1World, Block.FENCE.id, 0, 5, 2, 1, par3StructureBoundingBox);
            int var4 = this.c(Block.WOOD_STAIRS.id, 3);
            int var5 = this.c(Block.WOOD_STAIRS.id, 1);
            int var6 = this.c(Block.WOOD_STAIRS.id, 0);
            int var7 = this.c(Block.WOOD_STAIRS.id, 2);
            this.a(par1World, par3StructureBoundingBox, 0, 4, 1, 6, 4, 1, Block.SPRUCE_WOOD_STAIRS.id, var4, Block.SPRUCE_WOOD_STAIRS.id, var4, false);
            this.a(par1World, par3StructureBoundingBox, 0, 4, 2, 0, 4, 7, Block.SPRUCE_WOOD_STAIRS.id, var6, Block.SPRUCE_WOOD_STAIRS.id, var6, false);
            this.a(par1World, par3StructureBoundingBox, 6, 4, 2, 6, 4, 7, Block.SPRUCE_WOOD_STAIRS.id, var5, Block.SPRUCE_WOOD_STAIRS.id, var5, false);
            this.a(par1World, par3StructureBoundingBox, 0, 4, 8, 6, 4, 8, Block.SPRUCE_WOOD_STAIRS.id, var7, Block.SPRUCE_WOOD_STAIRS.id, var7, false);
            int var8;
            int var9;

            for (var8 = 2; var8 <= 7; var8 += 5)
            {
                for (var9 = 1; var9 <= 5; var9 += 4)
                {
                    this.b(par1World, Block.LOG.id, 0, var9, -1, var8, par3StructureBoundingBox);
                }
            }

            if (!this.h)
            {
                var8 = this.a(2, 5);
                var9 = this.a(2);
                int var10 = this.b(2, 5);

                if (par3StructureBoundingBox.b(var8, var9, var10))
                {
                    this.h = true;
                    EntityWitch var11 = new EntityWitch(par1World);
                    var11.setPositionRotation((double) var8 + 0.5D, (double) var9, (double) var10 + 0.5D, 0.0F, 0.0F);
                    var11.bG();
                    par1World.addEntity(var11);
                }
            }

            return true;
        }
    }
}
