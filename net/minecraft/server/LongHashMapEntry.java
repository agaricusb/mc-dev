package net.minecraft.server;

class LongHashMapEntry
{
    /**
     * the key as a long (for playerInstances it is the x in the most significant 32 bits and then y)
     */
    final long a;

    /** the value held by the hash at the specified key */
    Object b;

    /** the next hashentry in the table */
    LongHashMapEntry c;
    final int d;

    LongHashMapEntry(int par1, long par2, Object par4Obj, LongHashMapEntry par5LongHashMapEntry)
    {
        this.b = par4Obj;
        this.c = par5LongHashMapEntry;
        this.a = par2;
        this.d = par1;
    }

    public final long a()
    {
        return this.a;
    }

    public final Object b()
    {
        return this.b;
    }

    public final boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof LongHashMapEntry))
        {
            return false;
        }
        else
        {
            LongHashMapEntry var2 = (LongHashMapEntry)par1Obj;
            Long var3 = Long.valueOf(this.a());
            Long var4 = Long.valueOf(var2.a());

            if (var3 == var4 || var3 != null && var3.equals(var4))
            {
                Object var5 = this.b();
                Object var6 = var2.b();

                if (var5 == var6 || var5 != null && var5.equals(var6))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public final int hashCode()
    {
        return LongHashMap.f(this.a);
    }

    public final String toString()
    {
        return this.a() + "=" + this.b();
    }
}
