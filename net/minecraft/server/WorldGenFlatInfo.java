package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WorldGenFlatInfo
{
    /** List of layers on this preset. */
    private final List layers = new ArrayList();

    /** List of world features enabled on this preset. */
    private final Map structures = new HashMap();
    private int c = 0;

    /**
     * Return the biome used on this preset.
     */
    public int a()
    {
        return this.c;
    }

    /**
     * Set the biome used on this preset.
     */
    public void a(int par1)
    {
        this.c = par1;
    }

    /**
     * Return the list of world features enabled on this preset.
     */
    public Map b()
    {
        return this.structures;
    }

    /**
     * Return the list of layers on this preset.
     */
    public List c()
    {
        return this.layers;
    }

    public void d()
    {
        int var1 = 0;
        WorldGenFlatLayerInfo var3;

        for (Iterator var2 = this.layers.iterator(); var2.hasNext(); var1 += var3.a())
        {
            var3 = (WorldGenFlatLayerInfo)var2.next();
            var3.d(var1);
        }
    }

    public String toString()
    {
        StringBuilder var1 = new StringBuilder();
        var1.append(2);
        var1.append(";");
        int var2;

        for (var2 = 0; var2 < this.layers.size(); ++var2)
        {
            if (var2 > 0)
            {
                var1.append(",");
            }

            var1.append(((WorldGenFlatLayerInfo)this.layers.get(var2)).toString());
        }

        var1.append(";");
        var1.append(this.c);

        if (!this.structures.isEmpty())
        {
            var1.append(";");
            var2 = 0;
            Iterator var3 = this.structures.entrySet().iterator();

            while (var3.hasNext())
            {
                Entry var4 = (Entry)var3.next();

                if (var2++ > 0)
                {
                    var1.append(",");
                }

                var1.append(((String)var4.getKey()).toLowerCase());
                Map var5 = (Map)var4.getValue();

                if (!var5.isEmpty())
                {
                    var1.append("(");
                    int var6 = 0;
                    Iterator var7 = var5.entrySet().iterator();

                    while (var7.hasNext())
                    {
                        Entry var8 = (Entry)var7.next();

                        if (var6++ > 0)
                        {
                            var1.append(" ");
                        }

                        var1.append((String)var8.getKey());
                        var1.append("=");
                        var1.append((String)var8.getValue());
                    }

                    var1.append(")");
                }
            }
        }
        else
        {
            var1.append(";");
        }

        return var1.toString();
    }

    private static WorldGenFlatLayerInfo a(String par0Str, int par1)
    {
        String[] var2 = par0Str.split("x", 2);
        int var3 = 1;
        int var5 = 0;

        if (var2.length == 2)
        {
            try
            {
                var3 = Integer.parseInt(var2[0]);

                if (par1 + var3 >= 256)
                {
                    var3 = 256 - par1;
                }

                if (var3 < 0)
                {
                    var3 = 0;
                }
            }
            catch (Throwable var7)
            {
                return null;
            }
        }

        int var4;

        try
        {
            String var6 = var2[var2.length - 1];
            var2 = var6.split(":", 2);
            var4 = Integer.parseInt(var2[0]);

            if (var2.length > 1)
            {
                var5 = Integer.parseInt(var2[1]);
            }

            if (Block.byId[var4] == null)
            {
                var4 = 0;
                var5 = 0;
            }

            if (var5 < 0 || var5 > 15)
            {
                var5 = 0;
            }
        }
        catch (Throwable var8)
        {
            return null;
        }

        WorldGenFlatLayerInfo var9 = new WorldGenFlatLayerInfo(var3, var4, var5);
        var9.d(par1);
        return var9;
    }

    private static List b(String par0Str)
    {
        if (par0Str != null && par0Str.length() >= 1)
        {
            ArrayList var1 = new ArrayList();
            String[] var2 = par0Str.split(",");
            int var3 = 0;
            String[] var4 = var2;
            int var5 = var2.length;

            for (int var6 = 0; var6 < var5; ++var6)
            {
                String var7 = var4[var6];
                WorldGenFlatLayerInfo var8 = a(var7, var3);

                if (var8 == null)
                {
                    return null;
                }

                var1.add(var8);
                var3 += var8.a();
            }

            return var1;
        }
        else
        {
            return null;
        }
    }

    public static WorldGenFlatInfo a(String par0Str)
    {
        if (par0Str == null)
        {
            return e();
        }
        else
        {
            String[] var1 = par0Str.split(";", -1);
            int var2 = var1.length == 1 ? 0 : MathHelper.a(var1[0], 0);

            if (var2 >= 0 && var2 <= 2)
            {
                WorldGenFlatInfo var3 = new WorldGenFlatInfo();
                int var4 = var1.length == 1 ? 0 : 1;
                List var5 = b(var1[var4++]);

                if (var5 != null && !var5.isEmpty())
                {
                    var3.c().addAll(var5);
                    var3.d();
                    int var6 = BiomeBase.PLAINS.id;

                    if (var2 > 0 && var1.length > var4)
                    {
                        var6 = MathHelper.a(var1[var4++], var6);
                    }

                    var3.a(var6);

                    if (var2 > 0 && var1.length > var4)
                    {
                        String[] var7 = var1[var4++].toLowerCase().split(",");
                        String[] var8 = var7;
                        int var9 = var7.length;

                        for (int var10 = 0; var10 < var9; ++var10)
                        {
                            String var11 = var8[var10];
                            String[] var12 = var11.split("\\(", 2);
                            HashMap var13 = new HashMap();

                            if (var12[0].length() > 0)
                            {
                                var3.b().put(var12[0], var13);

                                if (var12.length > 1 && var12[1].endsWith(")") && var12[1].length() > 1)
                                {
                                    String[] var14 = var12[1].substring(0, var12[1].length() - 1).split(" ");

                                    for (int var15 = 0; var15 < var14.length; ++var15)
                                    {
                                        String[] var16 = var14[var15].split("=", 2);

                                        if (var16.length == 2)
                                        {
                                            var13.put(var16[0], var16[1]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        var3.b().put("village", new HashMap());
                    }

                    return var3;
                }
                else
                {
                    return e();
                }
            }
            else
            {
                return e();
            }
        }
    }

    public static WorldGenFlatInfo e()
    {
        WorldGenFlatInfo var0 = new WorldGenFlatInfo();
        var0.a(BiomeBase.PLAINS.id);
        var0.c().add(new WorldGenFlatLayerInfo(1, Block.BEDROCK.id));
        var0.c().add(new WorldGenFlatLayerInfo(2, Block.DIRT.id));
        var0.c().add(new WorldGenFlatLayerInfo(1, Block.GRASS.id));
        var0.d();
        var0.b().put("village", new HashMap());
        return var0;
    }
}
