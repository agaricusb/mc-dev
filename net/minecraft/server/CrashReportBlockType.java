package net.minecraft.server;

import java.util.concurrent.Callable;

final class CrashReportBlockType implements Callable
{
    final int a;

    CrashReportBlockType(int par1)
    {
        this.a = par1;
    }

    public String a()
    {
        try
        {
            return String.format("ID #%d (%s // %s)", new Object[] {Integer.valueOf(this.a), Block.byId[this.a].a(), Block.byId[this.a].getClass().getCanonicalName()});
        }
        catch (Throwable var2)
        {
            return "ID #" + this.a;
        }
    }

    public Object call()
    {
        return this.a();
    }
}
