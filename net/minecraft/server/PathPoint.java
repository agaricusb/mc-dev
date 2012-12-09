package net.minecraft.server;

public class PathPoint
{
    /** The x coordinate of this point */
    public final int a;

    /** The y coordinate of this point */
    public final int b;

    /** The z coordinate of this point */
    public final int c;

    /** A hash of the coordinates used to identify this point */
    private final int j;

    /** The index of this point in its assigned path */
    int d = -1;

    /** The distance along the path to this point */
    float e;

    /** The linear distance to the next point */
    float f;

    /** The distance to the target */
    float g;

    /** The point preceding this in its assigned path */
    PathPoint h;

    /** Indicates this is the origin */
    public boolean i = false;

    public PathPoint(int par1, int par2, int par3)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
        this.j = a(par1, par2, par3);
    }

    public static int a(int par0, int par1, int par2)
    {
        return par1 & 255 | (par0 & 32767) << 8 | (par2 & 32767) << 24 | (par0 < 0 ? Integer.MIN_VALUE : 0) | (par2 < 0 ? 32768 : 0);
    }

    /**
     * Returns the linear distance to another path point
     */
    public float a(PathPoint par1PathPoint)
    {
        float var2 = (float)(par1PathPoint.a - this.a);
        float var3 = (float)(par1PathPoint.b - this.b);
        float var4 = (float)(par1PathPoint.c - this.c);
        return MathHelper.c(var2 * var2 + var3 * var3 + var4 * var4);
    }

    public float b(PathPoint par1PathPoint)
    {
        float var2 = (float)(par1PathPoint.a - this.a);
        float var3 = (float)(par1PathPoint.b - this.b);
        float var4 = (float)(par1PathPoint.c - this.c);
        return var2 * var2 + var3 * var3 + var4 * var4;
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof PathPoint))
        {
            return false;
        }
        else
        {
            PathPoint var2 = (PathPoint)par1Obj;
            return this.j == var2.j && this.a == var2.a && this.b == var2.b && this.c == var2.c;
        }
    }

    public int hashCode()
    {
        return this.j;
    }

    /**
     * Returns true if this point has already been assigned to a path
     */
    public boolean a()
    {
        return this.d >= 0;
    }

    public String toString()
    {
        return this.a + ", " + this.b + ", " + this.c;
    }
}
