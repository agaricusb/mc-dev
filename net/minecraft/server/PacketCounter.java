package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;

public class PacketCounter
{
    /** If false, countPacket does nothing */
    public static boolean a = true;

    /** A count of the total number of each packet sent grouped by IDs. */
    private static final Map b = new HashMap();

    /** A count of the total size of each packet sent grouped by IDs. */
    private static final Map c = new HashMap();

    /** Used to make threads queue to add packets */
    private static final Object d = new Object();

    public static void a(int par0, long par1)
    {
        if (a)
        {
            Object var3 = d;

            synchronized (d)
            {
                if (b.containsKey(Integer.valueOf(par0)))
                {
                    b.put(Integer.valueOf(par0), Long.valueOf(((Long) b.get(Integer.valueOf(par0))).longValue() + 1L));
                    c.put(Integer.valueOf(par0), Long.valueOf(((Long) c.get(Integer.valueOf(par0))).longValue() + par1));
                }
                else
                {
                    b.put(Integer.valueOf(par0), Long.valueOf(1L));
                    c.put(Integer.valueOf(par0), Long.valueOf(par1));
                }
            }
        }
    }
}
