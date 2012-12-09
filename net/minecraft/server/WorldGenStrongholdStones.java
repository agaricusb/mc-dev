package net.minecraft.server;

import java.util.Random;

class WorldGenStrongholdStones extends StructurePieceBlockSelector
{
    private WorldGenStrongholdStones() {}

    /**
     * picks Block Ids and Metadata (Silverfish)
     */
    public void a(Random par1Random, int par2, int par3, int par4, boolean par5)
    {
        if (par5)
        {
            this.a = Block.SMOOTH_BRICK.id;
            float var6 = par1Random.nextFloat();

            if (var6 < 0.2F)
            {
                this.b = 2;
            }
            else if (var6 < 0.5F)
            {
                this.b = 1;
            }
            else if (var6 < 0.55F)
            {
                this.a = Block.MONSTER_EGGS.id;
                this.b = 2;
            }
            else
            {
                this.b = 0;
            }
        }
        else
        {
            this.a = 0;
            this.b = 0;
        }
    }

    WorldGenStrongholdStones(WorldGenStrongholdUnknown par1StructureStrongholdPieceWeight2)
    {
        this();
    }
}
