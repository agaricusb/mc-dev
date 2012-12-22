package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportVersion implements Callable
{
    /** Reference to the CrashReport object. */
    final CrashReport a;

    CrashReportVersion(CrashReport par1CrashReport)
    {
        this.a = par1CrashReport;
    }

    /**
     * The current version of Minecraft
     */
    public String a()
    {
        return "1.4.6";
    }

    public Object call()
    {
        return this.a();
    }
}
