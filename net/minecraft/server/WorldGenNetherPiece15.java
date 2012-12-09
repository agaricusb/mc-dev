package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece15 extends WorldGenNetherPiece1
{
    /** Instance of StructureNetherBridgePieceWeight. */
    public WorldGenNetherPieceWeight a;

    /**
     * Contains the list of valid piece weights for the set of nether bridge structure pieces.
     */
    public List b = new ArrayList();

    /**
     * Contains the list of valid piece weights for the secondary set of nether bridge structure pieces.
     */
    public List c;
    public ArrayList d = new ArrayList();

    public WorldGenNetherPiece15(Random par1Random, int par2, int par3)
    {
        super(par1Random, par2, par3);
        WorldGenNetherPieceWeight[] var4 = WorldGenNetherPieces.a();
        int var5 = var4.length;
        int var6;
        WorldGenNetherPieceWeight var7;

        for (var6 = 0; var6 < var5; ++var6)
        {
            var7 = var4[var6];
            var7.c = 0;
            this.b.add(var7);
        }

        this.c = new ArrayList();
        var4 = WorldGenNetherPieces.b();
        var5 = var4.length;

        for (var6 = 0; var6 < var5; ++var6)
        {
            var7 = var4[var6];
            var7.c = 0;
            this.c.add(var7);
        }
    }
}
