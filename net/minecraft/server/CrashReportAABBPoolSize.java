package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportAABBPoolSize implements Callable
{
    final CrashReport a;

    CrashReportAABBPoolSize(CrashReport par1CrashReport)
    {
        this.a = par1CrashReport;
    }

    public String a()
    {
        int var1 = AxisAlignedBB.a().c();
        int var2 = 56 * var1;
        int var3 = var2 / 1024 / 1024;
        int var4 = AxisAlignedBB.a().d();
        int var5 = 56 * var4;
        int var6 = var5 / 1024 / 1024;
        return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
    }

    public Object call()
    {
        return this.a();
    }
}
