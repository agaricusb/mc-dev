package net.minecraft.server;

import java.util.concurrent.Callable;

final class CrashReportBlockLocation implements Callable
{
    final int a;

    final int b;

    final int c;

    CrashReportBlockLocation(int par1, int par2, int par3)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
    }

    public String a()
    {
        return CrashReportSystemDetails.a(this.a, this.b, this.c);
    }

    public Object call()
    {
        return this.a();
    }
}
