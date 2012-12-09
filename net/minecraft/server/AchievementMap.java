package net.minecraft.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AchievementMap
{
    /** Holds the singleton instance of AchievementMap. */
    public static AchievementMap a = new AchievementMap();

    /** Maps a achievement id with it's unique GUID. */
    private Map b = new HashMap();

    private AchievementMap()
    {
        try
        {
            BufferedReader var1 = new BufferedReader(new InputStreamReader(AchievementMap.class.getResourceAsStream("/achievement/map.txt")));
            String var2;

            while ((var2 = var1.readLine()) != null)
            {
                String[] var3 = var2.split(",");
                int var4 = Integer.parseInt(var3[0]);
                this.b.put(Integer.valueOf(var4), var3[1]);
            }

            var1.close();
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }
    }

    /**
     * Returns the unique GUID of a achievement id.
     */
    public static String a(int par0)
    {
        return (String) a.b.get(Integer.valueOf(par0));
    }
}
