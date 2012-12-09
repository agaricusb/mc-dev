package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportModded implements Callable
{
    /** Reference to the DecitatedServer object. */
    final DedicatedServer a;

    CrashReportModded(DedicatedServer par1DedicatedServer)
    {
        this.a = par1DedicatedServer;
    }

    public String a()
    {
        String var1 = this.a.getServerModName();
        return !var1.equals("vanilla") ? "Definitely; Server brand changed to \'" + var1 + "\'" : "Unknown (can\'t tell)";
    }

    public Object call()
    {
        return this.a();
    }
}
