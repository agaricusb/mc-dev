package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportLevelSeed implements Callable
{
    final WorldData a;

    CrashReportLevelSeed(WorldData par1WorldInfo)
    {
        this.a = par1WorldInfo;
    }

    public String a()
    {
        return String.valueOf(this.a.getSeed());
    }

    public Object call()
    {
        return this.a();
    }
}
