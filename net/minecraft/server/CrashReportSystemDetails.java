package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

public class CrashReportSystemDetails
{
    private final CrashReport a;
    private final String b;
    private final List c = new ArrayList();
    private StackTraceElement[] d = new StackTraceElement[0];

    public CrashReportSystemDetails(CrashReport par1CrashReport, String par2Str)
    {
        this.a = par1CrashReport;
        this.b = par2Str;
    }

    public static String a(int par0, int par1, int par2)
    {
        StringBuilder var3 = new StringBuilder();

        try
        {
            var3.append(String.format("World: (%d,%d,%d)", new Object[] {Integer.valueOf(par0), Integer.valueOf(par1), Integer.valueOf(par2)}));
        }
        catch (Throwable var16)
        {
            var3.append("(Error finding world loc)");
        }

        var3.append(", ");
        int var4;
        int var5;
        int var6;
        int var7;
        int var8;
        int var9;
        int var10;
        int var11;
        int var12;

        try
        {
            var4 = par0 >> 4;
            var5 = par2 >> 4;
            var6 = par0 & 15;
            var7 = par1 >> 4;
            var8 = par2 & 15;
            var9 = var4 << 4;
            var10 = var5 << 4;
            var11 = (var4 + 1 << 4) - 1;
            var12 = (var5 + 1 << 4) - 1;
            var3.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", new Object[] {Integer.valueOf(var6), Integer.valueOf(var7), Integer.valueOf(var8), Integer.valueOf(var4), Integer.valueOf(var5), Integer.valueOf(var9), Integer.valueOf(var10), Integer.valueOf(var11), Integer.valueOf(var12)}));
        }
        catch (Throwable var15)
        {
            var3.append("(Error finding chunk loc)");
        }

        var3.append(", ");

        try
        {
            var4 = par0 >> 9;
            var5 = par2 >> 9;
            var6 = var4 << 5;
            var7 = var5 << 5;
            var8 = (var4 + 1 << 5) - 1;
            var9 = (var5 + 1 << 5) - 1;
            var10 = var4 << 9;
            var11 = var5 << 9;
            var12 = (var4 + 1 << 9) - 1;
            int var13 = (var5 + 1 << 9) - 1;
            var3.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", new Object[] {Integer.valueOf(var4), Integer.valueOf(var5), Integer.valueOf(var6), Integer.valueOf(var7), Integer.valueOf(var8), Integer.valueOf(var9), Integer.valueOf(var10), Integer.valueOf(var11), Integer.valueOf(var12), Integer.valueOf(var13)}));
        }
        catch (Throwable var14)
        {
            var3.append("(Error finding world loc)");
        }

        return var3.toString();
    }

    /**
     * Adds a Crashreport section with the given name with the value set to the result of the given Callable;
     */
    public void a(String par1Str, Callable par2Callable)
    {
        try
        {
            this.a(par1Str, par2Callable.call());
        }
        catch (Throwable var4)
        {
            this.a(par1Str, var4);
        }
    }

    /**
     * Adds a Crashreport section with the given name with the given value (convered .toString())
     */
    public void a(String par1Str, Object par2Obj)
    {
        this.c.add(new CrashReportDetail(par1Str, par2Obj));
    }

    /**
     * Adds a Crashreport section with the given name with the given Throwable
     */
    public void a(String par1Str, Throwable par2Throwable)
    {
        this.a(par1Str, par2Throwable);
    }

    public int a(int par1)
    {
        StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
        this.d = new StackTraceElement[var2.length - 3 - par1];
        System.arraycopy(var2, 3 + par1, this.d, 0, this.d.length);
        return this.d.length;
    }

    public boolean a(StackTraceElement par1StackTraceElement, StackTraceElement par2StackTraceElement)
    {
        if (this.d.length != 0 && par1StackTraceElement != null)
        {
            StackTraceElement var3 = this.d[0];

            if (var3.isNativeMethod() == par1StackTraceElement.isNativeMethod() && var3.getClassName().equals(par1StackTraceElement.getClassName()) && var3.getFileName().equals(par1StackTraceElement.getFileName()) && var3.getMethodName().equals(par1StackTraceElement.getMethodName()))
            {
                if (par2StackTraceElement != null != this.d.length > 1)
                {
                    return false;
                }
                else if (par2StackTraceElement != null && !this.d[1].equals(par2StackTraceElement))
                {
                    return false;
                }
                else
                {
                    this.d[0] = par1StackTraceElement;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public void b(int par1)
    {
        StackTraceElement[] var2 = new StackTraceElement[this.d.length - par1];
        System.arraycopy(this.d, 0, var2, 0, var2.length);
        this.d = var2;
    }

    public void a(StringBuilder par1StringBuilder)
    {
        par1StringBuilder.append("-- ").append(this.b).append(" --\n");
        par1StringBuilder.append("Details:");
        Iterator var2 = this.c.iterator();

        while (var2.hasNext())
        {
            CrashReportDetail var3 = (CrashReportDetail)var2.next();
            par1StringBuilder.append("\n\t");
            par1StringBuilder.append(var3.a());
            par1StringBuilder.append(": ");
            par1StringBuilder.append(var3.b());
        }

        if (this.d != null && this.d.length > 0)
        {
            par1StringBuilder.append("\nStacktrace:");
            StackTraceElement[] var6 = this.d;
            int var7 = var6.length;

            for (int var4 = 0; var4 < var7; ++var4)
            {
                StackTraceElement var5 = var6[var4];
                par1StringBuilder.append("\n\tat ");
                par1StringBuilder.append(var5.toString());
            }
        }
    }

    public static void a(CrashReportSystemDetails par0CrashReportCategory, int par1, int par2, int par3, int par4, int par5)
    {
        par0CrashReportCategory.a("Block type", new CrashReportBlockType(par4));
        par0CrashReportCategory.a("Block data value", new CrashReportBlockDataValue(par5));
        par0CrashReportCategory.a("Block location", new CrashReportBlockLocation(par1, par2, par3));
    }
}
