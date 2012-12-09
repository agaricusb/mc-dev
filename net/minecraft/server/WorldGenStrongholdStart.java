package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenStrongholdStart extends WorldGenStrongholdStairs2
{
    public WorldGenStrongholdPieceWeight a;
    public WorldGenStrongholdPortalRoom b;
    public ArrayList c = new ArrayList();

    public WorldGenStrongholdStart(int par1, Random par2Random, int par3, int par4)
    {
        super(0, par2Random, par3, par4);
    }

    public ChunkPosition a()
    {
        return this.b != null ? this.b.a() : super.a();
    }
}
