package net.minecraft.server;

public class PathEntity
{
    /** The actual points in the path */
    private final PathPoint[] a;

    /** PathEntity Array Index the Entity is currently targeting */
    private int b;

    /** The total length of the path */
    private int c;

    public PathEntity(PathPoint[] par1ArrayOfPathPoint)
    {
        this.a = par1ArrayOfPathPoint;
        this.c = par1ArrayOfPathPoint.length;
    }

    /**
     * Directs this path to the next point in its array
     */
    public void a()
    {
        ++this.b;
    }

    /**
     * Returns true if this path has reached the end
     */
    public boolean b()
    {
        return this.b >= this.c;
    }

    /**
     * returns the last PathPoint of the Array
     */
    public PathPoint c()
    {
        return this.c > 0 ? this.a[this.c - 1] : null;
    }

    /**
     * return the PathPoint located at the specified PathIndex, usually the current one
     */
    public PathPoint a(int par1)
    {
        return this.a[par1];
    }

    public int d()
    {
        return this.c;
    }

    public void b(int par1)
    {
        this.c = par1;
    }

    public int e()
    {
        return this.b;
    }

    public void c(int par1)
    {
        this.b = par1;
    }

    /**
     * Gets the vector of the PathPoint associated with the given index.
     */
    public Vec3D a(Entity par1Entity, int par2)
    {
        double var3 = (double)this.a[par2].a + (double)((int)(par1Entity.width + 1.0F)) * 0.5D;
        double var5 = (double)this.a[par2].b;
        double var7 = (double)this.a[par2].c + (double)((int)(par1Entity.width + 1.0F)) * 0.5D;
        return par1Entity.world.getVec3DPool().create(var3, var5, var7);
    }

    /**
     * returns the current PathEntity target node as Vec3D
     */
    public Vec3D a(Entity par1Entity)
    {
        return this.a(par1Entity, this.b);
    }

    /**
     * Returns true if the EntityPath are the same. Non instance related equals.
     */
    public boolean a(PathEntity par1PathEntity)
    {
        if (par1PathEntity == null)
        {
            return false;
        }
        else if (par1PathEntity.a.length != this.a.length)
        {
            return false;
        }
        else
        {
            for (int var2 = 0; var2 < this.a.length; ++var2)
            {
                if (this.a[var2].a != par1PathEntity.a[var2].a || this.a[var2].b != par1PathEntity.a[var2].b || this.a[var2].c != par1PathEntity.a[var2].c)
                {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Returns true if the final PathPoint in the PathEntity is equal to Vec3D coords.
     */
    public boolean b(Vec3D par1Vec3)
    {
        PathPoint var2 = this.c();
        return var2 == null ? false : var2.a == (int)par1Vec3.c && var2.c == (int)par1Vec3.e;
    }
}
