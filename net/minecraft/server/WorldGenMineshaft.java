package net.minecraft.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class WorldGenMineshaft extends StructureGenerator
{
    private double e = 0.01D;

    public WorldGenMineshaft() {}

    public WorldGenMineshaft(Map par1Map)
    {
        Iterator var2 = par1Map.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();

            if (((String)var3.getKey()).equals("chance"))
            {
                this.e = MathHelper.a((String) var3.getValue(), this.e);
            }
        }
    }

    protected boolean a(int par1, int par2)
    {
        return this.b.nextDouble() < this.e && this.b.nextInt(80) < Math.max(Math.abs(par1), Math.abs(par2));
    }

    protected StructureStart b(int par1, int par2)
    {
        return new WorldGenMineshaftStart(this.c, this.b, par1, par2);
    }
}
