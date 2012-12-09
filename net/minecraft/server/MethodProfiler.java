package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MethodProfiler
{
    /** List of parent sections */
    private final List b = new ArrayList();

    /** List of timestamps (System.nanoTime) */
    private final List c = new ArrayList();

    /** Flag profiling enabled */
    public boolean a = false;

    /** Current profiling section */
    private String d = "";

    /** Profiling map */
    private final Map e = new HashMap();

    /**
     * Clear profiling.
     */
    public void a()
    {
        this.e.clear();
        this.d = "";
        this.b.clear();
    }

    /**
     * Start section
     */
    public void a(String par1Str)
    {
        if (this.a)
        {
            if (this.d.length() > 0)
            {
                this.d = this.d + ".";
            }

            this.d = this.d + par1Str;
            this.b.add(this.d);
            this.c.add(Long.valueOf(System.nanoTime()));
        }
    }

    /**
     * End section
     */
    public void b()
    {
        if (this.a)
        {
            long var1 = System.nanoTime();
            long var3 = ((Long)this.c.remove(this.c.size() - 1)).longValue();
            this.b.remove(this.b.size() - 1);
            long var5 = var1 - var3;

            if (this.e.containsKey(this.d))
            {
                this.e.put(this.d, Long.valueOf(((Long)this.e.get(this.d)).longValue() + var5));
            }
            else
            {
                this.e.put(this.d, Long.valueOf(var5));
            }

            if (var5 > 100000000L)
            {
                System.out.println("Something\'s taking too long! \'" + this.d + "\' took aprox " + (double)var5 / 1000000.0D + " ms");
            }

            this.d = !this.b.isEmpty() ? (String)this.b.get(this.b.size() - 1) : "";
        }
    }

    /**
     * Get profiling data
     */
    public List b(String par1Str)
    {
        if (!this.a)
        {
            return null;
        }
        else
        {
            long var3 = this.e.containsKey("root") ? ((Long)this.e.get("root")).longValue() : 0L;
            long var5 = this.e.containsKey(par1Str) ? ((Long)this.e.get(par1Str)).longValue() : -1L;
            ArrayList var7 = new ArrayList();

            if (par1Str.length() > 0)
            {
                par1Str = par1Str + ".";
            }

            long var8 = 0L;
            Iterator var10 = this.e.keySet().iterator();

            while (var10.hasNext())
            {
                String var11 = (String)var10.next();

                if (var11.length() > par1Str.length() && var11.startsWith(par1Str) && var11.indexOf(".", par1Str.length() + 1) < 0)
                {
                    var8 += ((Long)this.e.get(var11)).longValue();
                }
            }

            float var21 = (float)var8;

            if (var8 < var5)
            {
                var8 = var5;
            }

            if (var3 < var8)
            {
                var3 = var8;
            }

            Iterator var20 = this.e.keySet().iterator();
            String var12;

            while (var20.hasNext())
            {
                var12 = (String)var20.next();

                if (var12.length() > par1Str.length() && var12.startsWith(par1Str) && var12.indexOf(".", par1Str.length() + 1) < 0)
                {
                    long var13 = ((Long)this.e.get(var12)).longValue();
                    double var15 = (double)var13 * 100.0D / (double)var8;
                    double var17 = (double)var13 * 100.0D / (double)var3;
                    String var19 = var12.substring(par1Str.length());
                    var7.add(new ProfilerInfo(var19, var15, var17));
                }
            }

            var20 = this.e.keySet().iterator();

            while (var20.hasNext())
            {
                var12 = (String)var20.next();
                this.e.put(var12, Long.valueOf(((Long)this.e.get(var12)).longValue() * 999L / 1000L));
            }

            if ((float)var8 > var21)
            {
                var7.add(new ProfilerInfo("unspecified", (double)((float)var8 - var21) * 100.0D / (double)var8, (double)((float)var8 - var21) * 100.0D / (double)var3));
            }

            Collections.sort(var7);
            var7.add(0, new ProfilerInfo(par1Str, 100.0D, (double)var8 * 100.0D / (double)var3));
            return var7;
        }
    }

    /**
     * End current section and start a new section
     */
    public void c(String par1Str)
    {
        this.b();
        this.a(par1Str);
    }

    public String c()
    {
        return this.b.size() == 0 ? "[UNKNOWN]" : (String)this.b.get(this.b.size() - 1);
    }
}
