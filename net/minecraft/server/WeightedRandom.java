package net.minecraft.server;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class WeightedRandom
{
    /**
     * Returns the total weight of all items in a collection.
     */
    public static int a(Collection par0Collection)
    {
        int var1 = 0;
        WeightedRandomChoice var3;

        for (Iterator var2 = par0Collection.iterator(); var2.hasNext(); var1 += var3.a)
        {
            var3 = (WeightedRandomChoice)var2.next();
        }

        return var1;
    }

    /**
     * Returns a random choice from the input items, with a total weight value.
     */
    public static WeightedRandomChoice a(Random par0Random, Collection par1Collection, int par2)
    {
        if (par2 <= 0)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            int var3 = par0Random.nextInt(par2);
            Iterator var4 = par1Collection.iterator();
            WeightedRandomChoice var5;

            do
            {
                if (!var4.hasNext())
                {
                    return null;
                }

                var5 = (WeightedRandomChoice)var4.next();
                var3 -= var5.a;
            }
            while (var3 >= 0);

            return var5;
        }
    }

    /**
     * Returns a random choice from the input items.
     */
    public static WeightedRandomChoice a(Random par0Random, Collection par1Collection)
    {
        return a(par0Random, par1Collection, a(par1Collection));
    }

    /**
     * Returns the total weight of all items in a array.
     */
    public static int a(WeightedRandomChoice[] par0ArrayOfWeightedRandomItem)
    {
        int var1 = 0;
        WeightedRandomChoice[] var2 = par0ArrayOfWeightedRandomItem;
        int var3 = par0ArrayOfWeightedRandomItem.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            WeightedRandomChoice var5 = var2[var4];
            var1 += var5.a;
        }

        return var1;
    }

    /**
     * Returns a random choice from the input array of items, with a total weight value.
     */
    public static WeightedRandomChoice a(Random par0Random, WeightedRandomChoice[] par1ArrayOfWeightedRandomItem, int par2)
    {
        if (par2 <= 0)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            int var3 = par0Random.nextInt(par2);
            WeightedRandomChoice[] var4 = par1ArrayOfWeightedRandomItem;
            int var5 = par1ArrayOfWeightedRandomItem.length;

            for (int var6 = 0; var6 < var5; ++var6)
            {
                WeightedRandomChoice var7 = var4[var6];
                var3 -= var7.a;

                if (var3 < 0)
                {
                    return var7;
                }
            }

            return null;
        }
    }

    /**
     * Returns a random choice from the input items.
     */
    public static WeightedRandomChoice a(Random par0Random, WeightedRandomChoice[] par1ArrayOfWeightedRandomItem)
    {
        return a(par0Random, par1ArrayOfWeightedRandomItem, a(par1ArrayOfWeightedRandomItem));
    }
}
