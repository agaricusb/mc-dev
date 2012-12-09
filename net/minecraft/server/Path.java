package net.minecraft.server;

public class Path
{
    /** Contains the points in this path */
    private PathPoint[] a = new PathPoint[1024];

    /** The number of points in this path */
    private int b = 0;

    /**
     * Adds a point to the path
     */
    public PathPoint a(PathPoint par1PathPoint)
    {
        if (par1PathPoint.d >= 0)
        {
            throw new IllegalStateException("OW KNOWS!");
        }
        else
        {
            if (this.b == this.a.length)
            {
                PathPoint[] var2 = new PathPoint[this.b << 1];
                System.arraycopy(this.a, 0, var2, 0, this.b);
                this.a = var2;
            }

            this.a[this.b] = par1PathPoint;
            par1PathPoint.d = this.b;
            this.a(this.b++);
            return par1PathPoint;
        }
    }

    /**
     * Clears the path
     */
    public void a()
    {
        this.b = 0;
    }

    /**
     * Returns and removes the first point in the path
     */
    public PathPoint c()
    {
        PathPoint var1 = this.a[0];
        this.a[0] = this.a[--this.b];
        this.a[this.b] = null;

        if (this.b > 0)
        {
            this.b(0);
        }

        var1.d = -1;
        return var1;
    }

    /**
     * Changes the provided point's distance to target
     */
    public void a(PathPoint par1PathPoint, float par2)
    {
        float var3 = par1PathPoint.g;
        par1PathPoint.g = par2;

        if (par2 < var3)
        {
            this.a(par1PathPoint.d);
        }
        else
        {
            this.b(par1PathPoint.d);
        }
    }

    /**
     * Sorts a point to the left
     */
    private void a(int par1)
    {
        PathPoint var2 = this.a[par1];
        int var4;

        for (float var3 = var2.g; par1 > 0; par1 = var4)
        {
            var4 = par1 - 1 >> 1;
            PathPoint var5 = this.a[var4];

            if (var3 >= var5.g)
            {
                break;
            }

            this.a[par1] = var5;
            var5.d = par1;
        }

        this.a[par1] = var2;
        var2.d = par1;
    }

    /**
     * Sorts a point to the right
     */
    private void b(int par1)
    {
        PathPoint var2 = this.a[par1];
        float var3 = var2.g;

        while (true)
        {
            int var4 = 1 + (par1 << 1);
            int var5 = var4 + 1;

            if (var4 >= this.b)
            {
                break;
            }

            PathPoint var6 = this.a[var4];
            float var7 = var6.g;
            PathPoint var8;
            float var9;

            if (var5 >= this.b)
            {
                var8 = null;
                var9 = Float.POSITIVE_INFINITY;
            }
            else
            {
                var8 = this.a[var5];
                var9 = var8.g;
            }

            if (var7 < var9)
            {
                if (var7 >= var3)
                {
                    break;
                }

                this.a[par1] = var6;
                var6.d = par1;
                par1 = var4;
            }
            else
            {
                if (var9 >= var3)
                {
                    break;
                }

                this.a[par1] = var8;
                var8.d = par1;
                par1 = var5;
            }
        }

        this.a[par1] = var2;
        var2.d = par1;
    }

    /**
     * Returns true if this path contains no points
     */
    public boolean e()
    {
        return this.b == 0;
    }
}
