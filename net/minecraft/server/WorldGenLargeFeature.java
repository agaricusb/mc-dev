package net.minecraft.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class WorldGenLargeFeature extends StructureGenerator
{
    private static List e = Arrays.asList(new BiomeBase[] {BiomeBase.DESERT, BiomeBase.DESERT_HILLS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS, BiomeBase.SWAMPLAND});

    /** contains possible spawns for scattered features */
    private List f;

    /** the maximum distance between scattered features */
    private int g;

    /** the minimum distance between scattered features */
    private int h;

    public WorldGenLargeFeature()
    {
        this.f = new ArrayList();
        this.g = 32;
        this.h = 8;
        this.f.add(new BiomeMeta(EntityWitch.class, 1, 1, 1));
    }

    public WorldGenLargeFeature(Map par1Map)
    {
        this();
        Iterator var2 = par1Map.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();

            if (((String)var3.getKey()).equals("distance"))
            {
                this.g = MathHelper.a((String) var3.getValue(), this.g, this.h + 1);
            }
        }
    }

    protected boolean a(int par1, int par2)
    {
        int var3 = par1;
        int var4 = par2;

        if (par1 < 0)
        {
            par1 -= this.g - 1;
        }

        if (par2 < 0)
        {
            par2 -= this.g - 1;
        }

        int var5 = par1 / this.g;
        int var6 = par2 / this.g;
        Random var7 = this.c.F(var5, var6, 14357617);
        var5 *= this.g;
        var6 *= this.g;
        var5 += var7.nextInt(this.g - this.h);
        var6 += var7.nextInt(this.g - this.h);

        if (var3 == var5 && var4 == var6)
        {
            BiomeBase var8 = this.c.getWorldChunkManager().getBiome(var3 * 16 + 8, var4 * 16 + 8);
            Iterator var9 = e.iterator();

            while (var9.hasNext())
            {
                BiomeBase var10 = (BiomeBase)var9.next();

                if (var8 == var10)
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected StructureStart b(int par1, int par2)
    {
        return new WorldGenLargeFeatureStart(this.c, this.b, par1, par2);
    }

    /**
     * returns possible spawns for scattered features
     */
    public List a()
    {
        return this.f;
    }
}
