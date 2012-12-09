package net.minecraft.server;

import java.util.concurrent.Callable;

final class CrashReportBlockDataValue implements Callable
{
    final int a;

    CrashReportBlockDataValue(int par1)
    {
        this.a = par1;
    }

    public String a()
    {
        if (this.a < 0)
        {
            return "Unknown? (Got " + this.a + ")";
        }
        else
        {
            String var1 = String.format("%4s", new Object[] {Integer.toBinaryString(this.a)}).replace(" ", "0");
            return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] {Integer.valueOf(this.a), var1});
        }
    }

    public Object call()
    {
        return this.a();
    }
}
