package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportType implements Callable
{
    final DedicatedServer a;

    CrashReportType(DedicatedServer par1DedicatedServer)
    {
        this.a = par1DedicatedServer;
    }

    public String a()
    {
        return "Dedicated Server (map_server.txt)";
    }

    public Object call()
    {
        return this.a();
    }
}
