package net.minecraft.server;

public class Tuple
{
    /** First Object in the Tuple */
    private Object a;

    /** Second Object in the Tuple */
    private Object b;

    public Tuple(Object par1Obj, Object par2Obj)
    {
        this.a = par1Obj;
        this.b = par2Obj;
    }

    /**
     * Get the first Object in the Tuple
     */
    public Object a()
    {
        return this.a;
    }

    /**
     * Get the second Object in the Tuple
     */
    public Object b()
    {
        return this.b;
    }
}
