package net.minecraft.server;

public class WatchableObject
{
    private final int a;

    /** id of max 31 */
    private final int b;
    private Object c;
    private boolean d;

    public WatchableObject(int par1, int par2, Object par3Obj)
    {
        this.b = par2;
        this.c = par3Obj;
        this.a = par1;
        this.d = true;
    }

    public int a()
    {
        return this.b;
    }

    public void a(Object par1Obj)
    {
        this.c = par1Obj;
    }

    public Object b()
    {
        return this.c;
    }

    public int c()
    {
        return this.a;
    }

    public boolean d()
    {
        return this.d;
    }

    public void a(boolean par1)
    {
        this.d = par1;
    }

    /**
     * Set whether the specified watchable object is being watched.
     */
    static boolean a(WatchableObject par0WatchableObject, boolean par1)
    {
        return par0WatchableObject.d = par1;
    }
}
