package net.minecraft.server;

class IntHashMapEntry
{
    /** The hash code of this entry */
    final int a;

    /** The object stored in this entry */
    Object b;

    /** The next entry in this slot */
    IntHashMapEntry c;

    /** The id of the hash slot computed from the hash */
    final int d;

    IntHashMapEntry(int par1, int par2, Object par3Obj, IntHashMapEntry par4IntHashMapEntry)
    {
        this.b = par3Obj;
        this.c = par4IntHashMapEntry;
        this.a = par2;
        this.d = par1;
    }

    /**
     * Returns the hash code for this entry
     */
    public final int a()
    {
        return this.a;
    }

    /**
     * Returns the object stored in this entry
     */
    public final Object b()
    {
        return this.b;
    }

    public final boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof IntHashMapEntry))
        {
            return false;
        }
        else
        {
            IntHashMapEntry var2 = (IntHashMapEntry)par1Obj;
            Integer var3 = Integer.valueOf(this.a());
            Integer var4 = Integer.valueOf(var2.a());

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
        return IntHashMap.f(this.a);
    }

    public final String toString()
    {
        return this.a() + "=" + this.b();
    }
}
