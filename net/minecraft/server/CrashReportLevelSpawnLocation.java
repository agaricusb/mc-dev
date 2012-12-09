package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportLevelSpawnLocation implements Callable
{
    final WorldData a;

    CrashReportLevelSpawnLocation(WorldData par1WorldInfo)
    {
        this.a = par1WorldInfo;
    }

    public String a()
    {
        return CrashReportSystemDetails.a(WorldData.d(this.a), WorldData.e(this.a), WorldData.f(this.a));
    }

    public Object call()
    {
        return this.a();
    }
}
