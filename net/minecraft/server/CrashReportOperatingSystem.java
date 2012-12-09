package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportOperatingSystem implements Callable
{
    /** Reference to the CrashReport object. */
    final CrashReport a;

    CrashReportOperatingSystem(CrashReport par1CrashReport)
    {
        this.a = par1CrashReport;
    }

    public String a()
    {
        return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
    }

    public Object call()
    {
        return this.a();
    }
}
