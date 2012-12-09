package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportSourceBlockType implements Callable
{
    final int a;

    /** Reference to the World object. */
    final World b;

    CrashReportSourceBlockType(World par1World, int par2)
    {
        this.b = par1World;
        this.a = par2;
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
