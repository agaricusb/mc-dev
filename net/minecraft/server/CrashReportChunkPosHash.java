package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportChunkPosHash implements Callable
{
    final int a;

    final int b;

    final StructureGenerator c;

    CrashReportChunkPosHash(StructureGenerator par1MapGenStructure, int par2, int par3)
    {
        this.c = par1MapGenStructure;
        this.a = par2;
        this.b = par3;
    }

    public String a()
    {
        return String.valueOf(ChunkCoordIntPair.a(this.a, this.b));
    }

    public Object call()
    {
        return this.a();
    }
}
