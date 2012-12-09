package net.minecraft.server;

public class ReportedException extends RuntimeException
{
    /** Instance of CrashReport. */
    private final CrashReport a;

    public ReportedException(CrashReport par1CrashReport)
    {
        this.a = par1CrashReport;
    }

    /**
     * Gets the CrashReport wrapped by this exception.
     */
    public CrashReport a()
    {
        return this.a;
    }

    public Throwable getCause()
    {
        return this.a.b();
    }

    public String getMessage()
    {
        return this.a.a();
    }
}
