package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportStructureType implements Callable
{
    final StructureGenerator a;

    CrashReportStructureType(StructureGenerator par1MapGenStructure)
    {
        this.a = par1MapGenStructure;
    }

    public String a()
    {
        return this.a.getClass().getCanonicalName();
    }

    public Object call()
    {
        return this.a();
    }
}
