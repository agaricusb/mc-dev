package net.minecraft.server;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class WorldGenVillage extends StructureGenerator
{
    /** A list of all the biomes villages can spawn in. */
    public static final List e = Arrays.asList(new BiomeBase[] {BiomeBase.PLAINS, BiomeBase.DESERT});

    /** World terrain type, 0 for normal, 1 for flat map */
    private int f;
    private int g;
    private int h;

    public WorldGenVillage()
    {
        this.f = 0;
        this.g = 32;
        this.h = 8;
    }

    public WorldGenVillage(Map par1Map)
    {
        this();
        Iterator var2 = par1Map.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();

            if (((String)var3.getKey()).equals("size"))
            {
                this.f = MathHelper.a((String) var3.getValue(), this.f, 0);
            }
            else if (((String)var3.getKey()).equals("distance"))
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
        Random var7 = this.c.F(var5, var6, 10387312);
        var5 *= this.g;
        var6 *= this.g;
        var5 += var7.nextInt(this.g - this.h);
        var6 += var7.nextInt(this.g - this.h);

        if (var3 == var5 && var4 == var6)
        {
            boolean var8 = this.c.getWorldChunkManager().a(var3 * 16 + 8, var4 * 16 + 8, 0, e);

            if (var8)
            {
                return true;
            }
        }

        return false;
    }

    protected StructureStart b(int par1, int par2)
    {
        return new WorldGenVillageStart(this.c, this.b, par1, par2, this.f);
    }
}
