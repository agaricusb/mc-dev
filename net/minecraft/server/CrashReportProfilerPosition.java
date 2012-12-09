package net.minecraft.server;

import java.util.concurrent.Callable;

public class CrashReportProfilerPosition implements Callable
{
    /** Reference to the MinecraftServer object. */
    final MinecraftServer a;

    public CrashReportProfilerPosition(MinecraftServer par1MinecraftServer)
    {
        this.a = par1MinecraftServer;
    }

    public String a()
    {
        return this.a.methodProfiler.a ? this.a.methodProfiler.c() : "N/A (disabled)";
    }

    public Object call()
    {
        return this.a();
    }
}
