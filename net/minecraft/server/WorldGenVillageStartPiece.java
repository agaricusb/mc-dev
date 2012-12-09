package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenVillageStartPiece extends WorldGenVillageWell
{
    public final WorldChunkManager a;

    /** Boolean that determines if the village is in a desert or not. */
    public final boolean b;

    /** World terrain type, 0 for normal, 1 for flap map */
    public final int c;
    public WorldGenVillagePieceWeight d;

    /**
     * Contains List of all spawnable Structure Piece Weights. If no more Pieces of a type can be spawned, they are
     * removed from this list
     */
    public ArrayList h;
    public ArrayList i = new ArrayList();
    public ArrayList j = new ArrayList();

    public WorldGenVillageStartPiece(WorldChunkManager par1WorldChunkManager, int par2, Random par3Random, int par4, int par5, ArrayList par6ArrayList, int par7)
    {
        super((WorldGenVillageStartPiece)null, 0, par3Random, par4, par5);
        this.a = par1WorldChunkManager;
        this.h = par6ArrayList;
        this.c = par7;
        BiomeBase var8 = par1WorldChunkManager.getBiome(par4, par5);
        this.b = var8 == BiomeBase.DESERT || var8 == BiomeBase.DESERT_HILLS;
        this.k = this;
    }

    public WorldChunkManager d()
    {
        return this.a;
    }
}
