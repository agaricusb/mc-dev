package net.minecraft.server;

import java.util.Random;

class WorldGenJungleTemplePiece extends StructurePieceBlockSelector
{
    private WorldGenJungleTemplePiece() {}

    /**
     * picks Block Ids and Metadata (Silverfish)
     */
    public void a(Random par1Random, int par2, int par3, int par4, boolean par5)
    {
        if (par1Random.nextFloat() < 0.4F)
        {
            this.a = Block.COBBLESTONE.id;
        }
        else
        {
            this.a = Block.MOSSY_COBBLESTONE.id;
        }
    }

    WorldGenJungleTemplePiece(WorldGenJungleTempleUnknown par1ComponentScatteredFeaturePieces2)
    {
        this();
    }
}
