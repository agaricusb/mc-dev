package net.minecraft.server;

public class NoteBlockData
{
    private int a;
    private int b;
    private int c;
    private int d;

    /** Different for each blockID */
    private int e;

    /** Different for each blockID, eventID */
    private int f;

    public NoteBlockData(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
        this.e = par5;
        this.f = par6;
        this.d = par4;
    }

    /**
     * Get the X coordinate.
     */
    public int a()
    {
        return this.a;
    }

    /**
     * Get the Y coordinate.
     */
    public int b()
    {
        return this.b;
    }

    /**
     * Get the Z coordinate.
     */
    public int c()
    {
        return this.c;
    }

    /**
     * Get the Event ID (different for each BlockID)
     */
    public int d()
    {
        return this.e;
    }

    /**
     * Get the Event Parameter (different for each BlockID,EventID)
     */
    public int e()
    {
        return this.f;
    }

    /**
     * Gets the BlockID for this BlockEventData
     */
    public int f()
    {
        return this.d;
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof NoteBlockData))
        {
            return false;
        }
        else
        {
            NoteBlockData var2 = (NoteBlockData)par1Obj;
            return this.a == var2.a && this.b == var2.b && this.c == var2.c && this.e == var2.e && this.f == var2.f && this.d == var2.d;
        }
    }
}
