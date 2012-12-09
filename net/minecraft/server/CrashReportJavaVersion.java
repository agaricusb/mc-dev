package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportJavaVersion implements Callable
{
    /** Reference to the CrashReport object. */
    final CrashReport a;

    CrashReportJavaVersion(CrashReport par1CrashReport)
    {
        this.a = par1CrashReport;
    }

    /**
     * Returns the Java VM Information as a String.  Includes the Version and Vender.
     */
    public String a()
    {
        return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
    }

    public Object call()
    {
        return this.a();
    }
}
