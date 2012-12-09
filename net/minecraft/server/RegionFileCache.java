package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RegionFileCache
{
    /** A map containing Files as keys and RegionFiles as values */
    private static final Map a = new HashMap();

    public static synchronized RegionFile a(File par0File, int par1, int par2)
    {
        File var3 = new File(par0File, "region");
        File var4 = new File(var3, "r." + (par1 >> 5) + "." + (par2 >> 5) + ".mca");
        RegionFile var5 = (RegionFile) a.get(var4);

        if (var5 != null)
        {
            return var5;
        }
        else
        {
            if (!var3.exists())
            {
                var3.mkdirs();
            }

            if (a.size() >= 256)
            {
                a();
            }

            RegionFile var6 = new RegionFile(var4);
            a.put(var4, var6);
            return var6;
        }
    }

    /**
     * clears region file references
     */
    public static synchronized void a()
    {
        Iterator var0 = a.values().iterator();

        while (var0.hasNext())
        {
            RegionFile var1 = (RegionFile)var0.next();

            try
            {
                if (var1 != null)
                {
                    var1.c();
                }
            }
            catch (IOException var3)
            {
                var3.printStackTrace();
            }
        }

        a.clear();
    }

    /**
     * Returns an input stream for the specified chunk. Args: worldDir, chunkX, chunkZ
     */
    public static DataInputStream c(File par0File, int par1, int par2)
    {
        RegionFile var3 = a(par0File, par1, par2);
        return var3.a(par1 & 31, par2 & 31);
    }

    /**
     * Returns an output stream for the specified chunk. Args: worldDir, chunkX, chunkZ
     */
    public static DataOutputStream d(File par0File, int par1, int par2)
    {
        RegionFile var3 = a(par0File, par1, par2);
        return var3.b(par1 & 31, par2 & 31);
    }
}
