package net.minecraft.server;

public class AxisAlignedBB
{
    /** ThreadLocal AABBPool */
    private static final ThreadLocal g = new AABBPoolThreadLocal();
    public double a;
    public double b;
    public double c;
    public double d;
    public double e;
    public double f;

    /**
     * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
     */
    public static AxisAlignedBB a(double par0, double par2, double par4, double par6, double par8, double par10)
    {
        return new AxisAlignedBB(par0, par2, par4, par6, par8, par10);
    }

    /**
     * Gets the ThreadLocal AABBPool
     */
    public static AABBPool a()
    {
        return (AABBPool) g.get();
    }

    protected AxisAlignedBB(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        this.a = par1;
        this.b = par3;
        this.c = par5;
        this.d = par7;
        this.e = par9;
        this.f = par11;
    }

    /**
     * Sets the bounds of the bounding box. Args: minX, minY, minZ, maxX, maxY, maxZ
     */
    public AxisAlignedBB b(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        this.a = par1;
        this.b = par3;
        this.c = par5;
        this.d = par7;
        this.e = par9;
        this.f = par11;
        return this;
    }

    /**
     * Adds the coordinates to the bounding box extending it if the point lies outside the current ranges. Args: x, y, z
     */
    public AxisAlignedBB a(double par1, double par3, double par5)
    {
        double var7 = this.a;
        double var9 = this.b;
        double var11 = this.c;
        double var13 = this.d;
        double var15 = this.e;
        double var17 = this.f;

        if (par1 < 0.0D)
        {
            var7 += par1;
        }

        if (par1 > 0.0D)
        {
            var13 += par1;
        }

        if (par3 < 0.0D)
        {
            var9 += par3;
        }

        if (par3 > 0.0D)
        {
            var15 += par3;
        }

        if (par5 < 0.0D)
        {
            var11 += par5;
        }

        if (par5 > 0.0D)
        {
            var17 += par5;
        }

        return a().a(var7, var9, var11, var13, var15, var17);
    }

    /**
     * Returns a bounding box expanded by the specified vector (if negative numbers are given it will shrink). Args: x,
     * y, z
     */
    public AxisAlignedBB grow(double par1, double par3, double par5)
    {
        double var7 = this.a - par1;
        double var9 = this.b - par3;
        double var11 = this.c - par5;
        double var13 = this.d + par1;
        double var15 = this.e + par3;
        double var17 = this.f + par5;
        return a().a(var7, var9, var11, var13, var15, var17);
    }

    /**
     * Returns a bounding box offseted by the specified vector (if negative numbers are given it will shrink). Args: x,
     * y, z
     */
    public AxisAlignedBB c(double par1, double par3, double par5)
    {
        return a().a(this.a + par1, this.b + par3, this.c + par5, this.d + par1, this.e + par3, this.f + par5);
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
     * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double a(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.e > this.b && par1AxisAlignedBB.b < this.e)
        {
            if (par1AxisAlignedBB.f > this.c && par1AxisAlignedBB.c < this.f)
            {
                double var4;

                if (par2 > 0.0D && par1AxisAlignedBB.d <= this.a)
                {
                    var4 = this.a - par1AxisAlignedBB.d;

                    if (var4 < par2)
                    {
                        par2 = var4;
                    }
                }

                if (par2 < 0.0D && par1AxisAlignedBB.a >= this.d)
                {
                    var4 = this.d - par1AxisAlignedBB.a;

                    if (var4 > par2)
                    {
                        par2 = var4;
                    }
                }

                return par2;
            }
            else
            {
                return par2;
            }
        }
        else
        {
            return par2;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
     * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double b(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.d > this.a && par1AxisAlignedBB.a < this.d)
        {
            if (par1AxisAlignedBB.f > this.c && par1AxisAlignedBB.c < this.f)
            {
                double var4;

                if (par2 > 0.0D && par1AxisAlignedBB.e <= this.b)
                {
                    var4 = this.b - par1AxisAlignedBB.e;

                    if (var4 < par2)
                    {
                        par2 = var4;
                    }
                }

                if (par2 < 0.0D && par1AxisAlignedBB.b >= this.e)
                {
                    var4 = this.e - par1AxisAlignedBB.b;

                    if (var4 > par2)
                    {
                        par2 = var4;
                    }
                }

                return par2;
            }
            else
            {
                return par2;
            }
        }
        else
        {
            return par2;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
     * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double c(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.d > this.a && par1AxisAlignedBB.a < this.d)
        {
            if (par1AxisAlignedBB.e > this.b && par1AxisAlignedBB.b < this.e)
            {
                double var4;

                if (par2 > 0.0D && par1AxisAlignedBB.f <= this.c)
                {
                    var4 = this.c - par1AxisAlignedBB.f;

                    if (var4 < par2)
                    {
                        par2 = var4;
                    }
                }

                if (par2 < 0.0D && par1AxisAlignedBB.c >= this.f)
                {
                    var4 = this.f - par1AxisAlignedBB.c;

                    if (var4 > par2)
                    {
                        par2 = var4;
                    }
                }

                return par2;
            }
            else
            {
                return par2;
            }
        }
        else
        {
            return par2;
        }
    }

    /**
     * Returns whether the given bounding box intersects with this one. Args: axisAlignedBB
     */
    public boolean a(AxisAlignedBB par1AxisAlignedBB)
    {
        return par1AxisAlignedBB.d > this.a && par1AxisAlignedBB.a < this.d ? (par1AxisAlignedBB.e > this.b && par1AxisAlignedBB.b < this.e ? par1AxisAlignedBB.f > this.c && par1AxisAlignedBB.c < this.f : false) : false;
    }

    /**
     * Offsets the current bounding box by the specified coordinates. Args: x, y, z
     */
    public AxisAlignedBB d(double par1, double par3, double par5)
    {
        this.a += par1;
        this.b += par3;
        this.c += par5;
        this.d += par1;
        this.e += par3;
        this.f += par5;
        return this;
    }

    /**
     * Returns if the supplied Vec3D is completely inside the bounding box
     */
    public boolean a(Vec3D par1Vec3)
    {
        return par1Vec3.c > this.a && par1Vec3.c < this.d ? (par1Vec3.d > this.b && par1Vec3.d < this.e ? par1Vec3.e > this.c && par1Vec3.e < this.f : false) : false;
    }

    /**
     * Returns the average length of the edges of the bounding box.
     */
    public double b()
    {
        double var1 = this.d - this.a;
        double var3 = this.e - this.b;
        double var5 = this.f - this.c;
        return (var1 + var3 + var5) / 3.0D;
    }

    /**
     * Returns a bounding box that is inset by the specified amounts
     */
    public AxisAlignedBB shrink(double par1, double par3, double par5)
    {
        double var7 = this.a + par1;
        double var9 = this.b + par3;
        double var11 = this.c + par5;
        double var13 = this.d - par1;
        double var15 = this.e - par3;
        double var17 = this.f - par5;
        return a().a(var7, var9, var11, var13, var15, var17);
    }

    /**
     * Returns a copy of the bounding box.
     */
    public AxisAlignedBB clone()
    {
        return a().a(this.a, this.b, this.c, this.d, this.e, this.f);
    }

    public MovingObjectPosition a(Vec3D par1Vec3, Vec3D par2Vec3)
    {
        Vec3D var3 = par1Vec3.b(par2Vec3, this.a);
        Vec3D var4 = par1Vec3.b(par2Vec3, this.d);
        Vec3D var5 = par1Vec3.c(par2Vec3, this.b);
        Vec3D var6 = par1Vec3.c(par2Vec3, this.e);
        Vec3D var7 = par1Vec3.d(par2Vec3, this.c);
        Vec3D var8 = par1Vec3.d(par2Vec3, this.f);

        if (!this.b(var3))
        {
            var3 = null;
        }

        if (!this.b(var4))
        {
            var4 = null;
        }

        if (!this.c(var5))
        {
            var5 = null;
        }

        if (!this.c(var6))
        {
            var6 = null;
        }

        if (!this.d(var7))
        {
            var7 = null;
        }

        if (!this.d(var8))
        {
            var8 = null;
        }

        Vec3D var9 = null;

        if (var3 != null && (var9 == null || par1Vec3.distanceSquared(var3) < par1Vec3.distanceSquared(var9)))
        {
            var9 = var3;
        }

        if (var4 != null && (var9 == null || par1Vec3.distanceSquared(var4) < par1Vec3.distanceSquared(var9)))
        {
            var9 = var4;
        }

        if (var5 != null && (var9 == null || par1Vec3.distanceSquared(var5) < par1Vec3.distanceSquared(var9)))
        {
            var9 = var5;
        }

        if (var6 != null && (var9 == null || par1Vec3.distanceSquared(var6) < par1Vec3.distanceSquared(var9)))
        {
            var9 = var6;
        }

        if (var7 != null && (var9 == null || par1Vec3.distanceSquared(var7) < par1Vec3.distanceSquared(var9)))
        {
            var9 = var7;
        }

        if (var8 != null && (var9 == null || par1Vec3.distanceSquared(var8) < par1Vec3.distanceSquared(var9)))
        {
            var9 = var8;
        }

        if (var9 == null)
        {
            return null;
        }
        else
        {
            byte var10 = -1;

            if (var9 == var3)
            {
                var10 = 4;
            }

            if (var9 == var4)
            {
                var10 = 5;
            }

            if (var9 == var5)
            {
                var10 = 0;
            }

            if (var9 == var6)
            {
                var10 = 1;
            }

            if (var9 == var7)
            {
                var10 = 2;
            }

            if (var9 == var8)
            {
                var10 = 3;
            }

            return new MovingObjectPosition(0, 0, 0, var10, var9);
        }
    }

    /**
     * Checks if the specified vector is within the YZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean b(Vec3D par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.d >= this.b && par1Vec3.d <= this.e && par1Vec3.e >= this.c && par1Vec3.e <= this.f;
    }

    /**
     * Checks if the specified vector is within the XZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean c(Vec3D par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.c >= this.a && par1Vec3.c <= this.d && par1Vec3.e >= this.c && par1Vec3.e <= this.f;
    }

    /**
     * Checks if the specified vector is within the XY dimensions of the bounding box. Args: Vec3D
     */
    private boolean d(Vec3D par1Vec3)
    {
        return par1Vec3 == null ? false : par1Vec3.c >= this.a && par1Vec3.c <= this.d && par1Vec3.d >= this.b && par1Vec3.d <= this.e;
    }

    /**
     * Sets the bounding box to the same bounds as the bounding box passed in. Args: axisAlignedBB
     */
    public void c(AxisAlignedBB par1AxisAlignedBB)
    {
        this.a = par1AxisAlignedBB.a;
        this.b = par1AxisAlignedBB.b;
        this.c = par1AxisAlignedBB.c;
        this.d = par1AxisAlignedBB.d;
        this.e = par1AxisAlignedBB.e;
        this.f = par1AxisAlignedBB.f;
    }

    public String toString()
    {
        return "box[" + this.a + ", " + this.b + ", " + this.c + " -> " + this.d + ", " + this.e + ", " + this.f + "]";
    }
}
