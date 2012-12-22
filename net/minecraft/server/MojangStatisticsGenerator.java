package net.minecraft.server;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

public class MojangStatisticsGenerator
{
    /** String map for report data */
    private Map a = new HashMap();
    private final String b = UUID.randomUUID().toString();

    /** URL of the server to send the report to */
    private final URL c;
    private final IMojangStatistics d;

    /** set to fire the snooperThread every 15 mins */
    private final Timer e = new Timer("Snooper Timer", true);
    private final Object f = new Object();
    private boolean g = false;

    /** incremented on every getSelfCounterFor */
    private int h = 0;

    public MojangStatisticsGenerator(String par1Str, IMojangStatistics par2IPlayerUsage)
    {
        try
        {
            this.c = new URL("http://snoop.minecraft.net/" + par1Str + "?version=" + 1);
        }
        catch (MalformedURLException var4)
        {
            throw new IllegalArgumentException();
        }

        this.d = par2IPlayerUsage;
    }

    /**
     * Note issuing start multiple times is not an error.
     */
    public void a()
    {
        if (!this.g)
        {
            this.g = true;
            this.g();
            this.e.schedule(new MojangStatisticsTask(this), 0L, 900000L);
        }
    }

    private void g()
    {
        this.h();
        this.a("snooper_token", this.b);
        this.a("os_name", System.getProperty("os.name"));
        this.a("os_version", System.getProperty("os.version"));
        this.a("os_architecture", System.getProperty("os.arch"));
        this.a("java_version", System.getProperty("java.version"));
        this.a("version", "1.4.6");
        this.d.b(this);
    }

    private void h()
    {
        RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
        List var2 = var1.getInputArguments();
        int var3 = 0;
        Iterator var4 = var2.iterator();

        while (var4.hasNext())
        {
            String var5 = (String)var4.next();

            if (var5.startsWith("-X"))
            {
                this.a("jvm_arg[" + var3++ + "]", var5);
            }
        }

        this.a("jvm_args", Integer.valueOf(var3));
    }

    public void b()
    {
        this.a("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
        this.a("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
        this.a("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
        this.a("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
        this.d.a(this);
    }

    /**
     * Adds information to the report
     */
    public void a(String par1Str, Object par2Obj)
    {
        Object var3 = this.f;

        synchronized (this.f)
        {
            this.a.put(par1Str, par2Obj);
        }
    }

    public boolean d()
    {
        return this.g;
    }

    public void e()
    {
        this.e.cancel();
    }

    static IMojangStatistics a(MojangStatisticsGenerator par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.d;
    }

    static Object b(MojangStatisticsGenerator par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.f;
    }

    static Map c(MojangStatisticsGenerator par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.a;
    }

    /**
     * returns a value indicating how many times this function has been run on the snooper
     */
    static int d(MojangStatisticsGenerator par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.h++;
    }

    static URL e(MojangStatisticsGenerator par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.c;
    }
}
