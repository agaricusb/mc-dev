package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportIsFeatureChunk implements Callable
{
    final int a;

    final int b;

    final StructureGenerator c;

    CrashReportIsFeatureChunk(StructureGenerator par1MapGenStructure, int par2, int par3)
    {
        this.c = par1MapGenStructure;
        this.a = par2;
        this.b = par3;
    }

    public String a()
    {
        return this.c.a(this.a, this.b) ? "True" : "False";
    }

    public Object call()
    {
        return this.a();
    }
}
