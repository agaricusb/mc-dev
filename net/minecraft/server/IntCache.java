package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class IntCache
{
    private static int a = 256;

    /**
     * A list of pre-allocated int[256] arrays that are currently unused and can be returned by getIntCache()
     */
    private static List b = new ArrayList();

    /**
     * A list of pre-allocated int[256] arrays that were previously returned by getIntCache() and which will not be re-
     * used again until resetIntCache() is called.
     */
    private static List c = new ArrayList();

    /**
     * A list of pre-allocated int[cacheSize] arrays that are currently unused and can be returned by getIntCache()
     */
    private static List d = new ArrayList();

    /**
     * A list of pre-allocated int[cacheSize] arrays that were previously returned by getIntCache() and which will not
     * be re-used again until resetIntCache() is called.
     */
    private static List e = new ArrayList();

    public static synchronized int[] a(int par0)
    {
        int[] var1;

        if (par0 <= 256)
        {
            if (b.isEmpty())
            {
                var1 = new int[256];
                c.add(var1);
                return var1;
            }
            else
            {
                var1 = (int[]) b.remove(b.size() - 1);
                c.add(var1);
                return var1;
            }
        }
        else if (par0 > a)
        {
            a = par0;
            d.clear();
            e.clear();
            var1 = new int[a];
            e.add(var1);
            return var1;
        }
        else if (d.isEmpty())
        {
            var1 = new int[a];
            e.add(var1);
            return var1;
        }
        else
        {
            var1 = (int[]) d.remove(d.size() - 1);
            e.add(var1);
            return var1;
        }
    }

    /**
     * Mark all pre-allocated arrays as available for re-use by moving them to the appropriate free lists.
     */
    public static synchronized void a()
    {
        if (!d.isEmpty())
        {
            d.remove(d.size() - 1);
        }

        if (!b.isEmpty())
        {
            b.remove(b.size() - 1);
        }

        d.addAll(e);
        b.addAll(c);
        e.clear();
        c.clear();
    }

    public static synchronized String b()
    {
        return "cache: " + d.size() + ", tcache: " + b.size() + ", allocated: " + e.size() + ", tallocated: " + c.size();
    }
}
