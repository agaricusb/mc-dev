package net.minecraft.server;

import java.util.HashSet;
import java.util.Set;

public class IntHashMap
{
    /** An array of HashEntries representing the heads of hash slot lists */
    private transient IntHashMapEntry[] a = new IntHashMapEntry[16];

    /** The number of items stored in this map */
    private transient int b;

    /** The grow threshold */
    private int c = 12;

    /** The scale factor used to determine when to grow the table */
    private final float d = 0.75F;

    /** A serial stamp used to mark changes */
    private transient volatile int e;

    /** The set of all the keys stored in this MCHash object */
    private Set f = new HashSet();

    /**
     * Makes the passed in integer suitable for hashing by a number of shifts
     */
    private static int g(int par0)
    {
        par0 ^= par0 >>> 20 ^ par0 >>> 12;
        return par0 ^ par0 >>> 7 ^ par0 >>> 4;
    }

    /**
     * Computes the index of the slot for the hash and slot count passed in.
     */
    private static int a(int par0, int par1)
    {
        return par0 & par1 - 1;
    }

    /**
     * Returns the object associated to a key
     */
    public Object get(int par1)
    {
        int var2 = g(par1);

        for (IntHashMapEntry var3 = this.a[a(var2, this.a.length)]; var3 != null; var3 = var3.c)
        {
            if (var3.a == par1)
            {
                return var3.b;
            }
        }

        return null;
    }

    /**
     * Returns true if this hash table contains the specified item.
     */
    public boolean b(int par1)
    {
        return this.c(par1) != null;
    }

    /**
     * Returns the internal entry for a key
     */
    final IntHashMapEntry c(int par1)
    {
        int var2 = g(par1);

        for (IntHashMapEntry var3 = this.a[a(var2, this.a.length)]; var3 != null; var3 = var3.c)
        {
            if (var3.a == par1)
            {
                return var3;
            }
        }

        return null;
    }

    /**
     * Adds a key and associated value to this map
     */
    public void a(int par1, Object par2Obj)
    {
        this.f.add(Integer.valueOf(par1));
        int var3 = g(par1);
        int var4 = a(var3, this.a.length);

        for (IntHashMapEntry var5 = this.a[var4]; var5 != null; var5 = var5.c)
        {
            if (var5.a == par1)
            {
                var5.b = par2Obj;
                return;
            }
        }

        ++this.e;
        this.a(var3, par1, par2Obj, var4);
    }

    /**
     * Increases the number of hash slots
     */
    private void h(int par1)
    {
        IntHashMapEntry[] var2 = this.a;
        int var3 = var2.length;

        if (var3 == 1073741824)
        {
            this.c = Integer.MAX_VALUE;
        }
        else
        {
            IntHashMapEntry[] var4 = new IntHashMapEntry[par1];
            this.a(var4);
            this.a = var4;
            this.c = (int)((float)par1 * this.d);
        }
    }

    /**
     * Copies the hash slots to a new array
     */
    private void a(IntHashMapEntry[] par1ArrayOfIntHashMapEntry)
    {
        IntHashMapEntry[] var2 = this.a;
        int var3 = par1ArrayOfIntHashMapEntry.length;

        for (int var4 = 0; var4 < var2.length; ++var4)
        {
            IntHashMapEntry var5 = var2[var4];

            if (var5 != null)
            {
                var2[var4] = null;
                IntHashMapEntry var6;

                do
                {
                    var6 = var5.c;
                    int var7 = a(var5.d, var3);
                    var5.c = par1ArrayOfIntHashMapEntry[var7];
                    par1ArrayOfIntHashMapEntry[var7] = var5;
                    var5 = var6;
                }
                while (var6 != null);
            }
        }
    }

    /**
     * Removes the specified object from the map and returns it
     */
    public Object d(int par1)
    {
        this.f.remove(Integer.valueOf(par1));
        IntHashMapEntry var2 = this.e(par1);
        return var2 == null ? null : var2.b;
    }

    /**
     * Removes the specified entry from the map and returns it
     */
    final IntHashMapEntry e(int par1)
    {
        int var2 = g(par1);
        int var3 = a(var2, this.a.length);
        IntHashMapEntry var4 = this.a[var3];
        IntHashMapEntry var5;
        IntHashMapEntry var6;

        for (var5 = var4; var5 != null; var5 = var6)
        {
            var6 = var5.c;

            if (var5.a == par1)
            {
                ++this.e;
                --this.b;

                if (var4 == var5)
                {
                    this.a[var3] = var6;
                }
                else
                {
                    var4.c = var6;
                }

                return var5;
            }

            var4 = var5;
        }

        return var5;
    }

    /**
     * Removes all entries from the map
     */
    public void c()
    {
        ++this.e;
        IntHashMapEntry[] var1 = this.a;

        for (int var2 = 0; var2 < var1.length; ++var2)
        {
            var1[var2] = null;
        }

        this.b = 0;
    }

    /**
     * Adds an object to a slot
     */
    private void a(int par1, int par2, Object par3Obj, int par4)
    {
        IntHashMapEntry var5 = this.a[par4];
        this.a[par4] = new IntHashMapEntry(par1, par2, par3Obj, var5);

        if (this.b++ >= this.c)
        {
            this.h(2 * this.a.length);
        }
    }

    /**
     * Returns the hash code for a key
     */
    static int f(int par0)
    {
        return g(par0);
    }
}
