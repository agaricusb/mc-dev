package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportJavaVMVersion implements Callable
{
    /** Reference to the CrashReport object. */
    final CrashReport a;

    CrashReportJavaVMVersion(CrashReport par1CrashReport)
    {
        this.a = par1CrashReport;
    }

    /**
     * Retuns the Java VM Information as a String.  Includes the VM Name, VM Info and VM Vendor.
     */
    public String a()
    {
        return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
    }

    public Object call()
    {
        return this.a();
    }
}
