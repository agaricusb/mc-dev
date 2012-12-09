package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportIntCacheSize implements Callable
{
    final CrashReport a;

    CrashReportIntCacheSize(CrashReport par1CrashReport)
    {
        this.a = par1CrashReport;
    }

    public String a()
    {
        return IntCache.b();
    }

    public Object call()
    {
        return this.a();
    }
}
