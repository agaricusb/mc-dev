package net.minecraft.server;

public class LongHashMap
{
    /** the array of all elements in the hash */
    private transient LongHashMapEntry[] entries = new LongHashMapEntry[16];

    /** the number of elements in the hash array */
    private transient int count;

    /**
     * the maximum amount of elements in the hash (probably 3/4 the size due to meh hashing function)
     */
    private int c = 12;

    /**
     * percent of the hasharray that can be used without hash colliding probably
     */
    private final float d = 0.75F;

    /** count of times elements have been added/removed */
    private transient volatile int e;

    /**
     * returns the hashed key given the original key
     */
    private static int g(long par0)
    {
        return a((int) (par0 ^ par0 >>> 32));
    }

    /**
     * the hash function
     */
    private static int a(int par0)
    {
        par0 ^= par0 >>> 20 ^ par0 >>> 12;
        return par0 ^ par0 >>> 7 ^ par0 >>> 4;
    }

    /**
     * gets the index in the hash given the array length and the hashed key
     */
    private static int a(int par0, int par1)
    {
        return par0 & par1 - 1;
    }

    public int count()
    {
        return this.count;
    }

    /**
     * get the value from the map given the key
     */
    public Object getEntry(long par1)
    {
        int var3 = g(par1);

        for (LongHashMapEntry var4 = this.entries[a(var3, this.entries.length)]; var4 != null; var4 = var4.c)
        {
            if (var4.a == par1)
            {
                return var4.b;
            }
        }

        return null;
    }

    public boolean contains(long par1)
    {
        return this.getEntry(par1) != null;
    }

    final LongHashMapEntry c(long par1)
    {
        int var3 = g(par1);

        for (LongHashMapEntry var4 = this.entries[a(var3, this.entries.length)]; var4 != null; var4 = var4.c)
        {
            if (var4.a == par1)
            {
                return var4;
            }
        }

        return null;
    }

    /**
     * Add a key-value pair.
     */
    public void put(long par1, Object par3Obj)
    {
        int var4 = g(par1);
        int var5 = a(var4, this.entries.length);

        for (LongHashMapEntry var6 = this.entries[var5]; var6 != null; var6 = var6.c)
        {
            if (var6.a == par1)
            {
                var6.b = par3Obj;
                return;
            }
        }

        ++this.e;
        this.a(var4, par1, par3Obj, var5);
    }

    /**
     * resizes the table
     */
    private void b(int par1)
    {
        LongHashMapEntry[] var2 = this.entries;
        int var3 = var2.length;

        if (var3 == 1073741824)
        {
            this.c = Integer.MAX_VALUE;
        }
        else
        {
            LongHashMapEntry[] var4 = new LongHashMapEntry[par1];
            this.a(var4);
            this.entries = var4;
            this.c = (int)((float)par1 * this.d);
        }
    }

    /**
     * copies the hash table to the specified array
     */
    private void a(LongHashMapEntry[] par1ArrayOfLongHashMapEntry)
    {
        LongHashMapEntry[] var2 = this.entries;
        int var3 = par1ArrayOfLongHashMapEntry.length;

        for (int var4 = 0; var4 < var2.length; ++var4)
        {
            LongHashMapEntry var5 = var2[var4];

            if (var5 != null)
            {
                var2[var4] = null;
                LongHashMapEntry var6;

                do
                {
                    var6 = var5.c;
                    int var7 = a(var5.d, var3);
                    var5.c = par1ArrayOfLongHashMapEntry[var7];
                    par1ArrayOfLongHashMapEntry[var7] = var5;
                    var5 = var6;
                }
                while (var6 != null);
            }
        }
    }

    /**
     * calls the removeKey method and returns removed object
     */
    public Object remove(long par1)
    {
        LongHashMapEntry var3 = this.e(par1);
        return var3 == null ? null : var3.b;
    }

    /**
     * removes the key from the hash linked list
     */
    final LongHashMapEntry e(long par1)
    {
        int var3 = g(par1);
        int var4 = a(var3, this.entries.length);
        LongHashMapEntry var5 = this.entries[var4];
        LongHashMapEntry var6;
        LongHashMapEntry var7;

        for (var6 = var5; var6 != null; var6 = var7)
        {
            var7 = var6.c;

            if (var6.a == par1)
            {
                ++this.e;
                --this.count;

                if (var5 == var6)
                {
                    this.entries[var4] = var7;
                }
                else
                {
                    var5.c = var7;
                }

                return var6;
            }

            var5 = var6;
        }

        return var6;
    }

    /**
     * creates the key in the hash table
     */
    private void a(int par1, long par2, Object par4Obj, int par5)
    {
        LongHashMapEntry var6 = this.entries[par5];
        this.entries[par5] = new LongHashMapEntry(par1, par2, par4Obj, var6);

        if (this.count++ >= this.c)
        {
            this.b(2 * this.entries.length);
        }
    }

    /**
     * public method to get the hashed key(hashCode)
     */
    static int f(long par0)
    {
        return g(par0);
    }
}
