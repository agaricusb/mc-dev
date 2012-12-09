package net.minecraft.server;

public class NextTickListEntry implements Comparable
{
    /** The id number for the next tick entry */
    private static long g = 0L;

    /** X position this tick is occuring at */
    public int a;

    /** Y position this tick is occuring at */
    public int b;

    /** Z position this tick is occuring at */
    public int c;

    /**
     * blockID of the scheduled tick (ensures when the tick occurs its still for this block)
     */
    public int d;

    /** Time this tick is scheduled to occur at */
    public long e;
    public int f;

    /** The id of the tick entry */
    private long h;

    public NextTickListEntry(int par1, int par2, int par3, int par4)
    {
        this.h = (long)(g++);
        this.a = par1;
        this.b = par2;
        this.c = par3;
        this.d = par4;
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof NextTickListEntry))
        {
            return false;
        }
        else
        {
            NextTickListEntry var2 = (NextTickListEntry)par1Obj;
            return this.a == var2.a && this.b == var2.b && this.c == var2.c && this.d == var2.d;
        }
    }

    public int hashCode()
    {
        return (this.a * 1024 * 1024 + this.c * 1024 + this.b) * 256 + this.d;
    }

    /**
     * Sets the scheduled time for this tick entry
     */
    public NextTickListEntry a(long par1)
    {
        this.e = par1;
        return this;
    }

    public void a(int par1)
    {
        this.f = par1;
    }

    /**
     * Compares this tick entry to another tick entry for sorting purposes. Compared first based on the scheduled time
     * and second based on tickEntryID.
     */
    public int compareTo(NextTickListEntry par1NextTickListEntry)
    {
        return this.e < par1NextTickListEntry.e ? -1 : (this.e > par1NextTickListEntry.e ? 1 : (this.f != par1NextTickListEntry.f ? this.f - par1NextTickListEntry.f : (this.h < par1NextTickListEntry.h ? -1 : (this.h > par1NextTickListEntry.h ? 1 : 0))));
    }

    public int compareTo(Object par1Obj)
    {
        return this.compareTo((NextTickListEntry) par1Obj);
    }
}
