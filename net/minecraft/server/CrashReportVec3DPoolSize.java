package net.minecraft.server;

import java.util.concurrent.Callable;

public class CrashReportVec3DPoolSize implements Callable
{
    final MinecraftServer a;

    public CrashReportVec3DPoolSize(MinecraftServer par1MinecraftServer)
    {
        this.a = par1MinecraftServer;
    }

    public String a()
    {
        int var1 = this.a.worldServer[0].getVec3DPool().c();
        int var2 = 56 * var1;
        int var3 = var2 / 1024 / 1024;
        int var4 = this.a.worldServer[0].getVec3DPool().d();
        int var5 = 56 * var4;
        int var6 = var5 / 1024 / 1024;
        return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
    }

    public Object call()
    {
        return this.a();
    }
}
