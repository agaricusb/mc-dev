package net.minecraft.server;

public class Vec3D
{
    public static final Vec3DPool a = new Vec3DPool(-1, -1);
    public final Vec3DPool b;

    /** X coordinate of Vec3D */
    public double c;

    /** Y coordinate of Vec3D */
    public double d;

    /** Z coordinate of Vec3D */
    public double e;

    /**
     * Static method for creating a new Vec3D given the three x,y,z values. This is only called from the other static
     * method which creates and places it in the list.
     */
    public static Vec3D a(double par0, double par2, double par4)
    {
        return new Vec3D(a, par0, par2, par4);
    }

    protected Vec3D(Vec3DPool par1Vec3Pool, double par2, double par4, double par6)
    {
        if (par2 == -0.0D)
        {
            par2 = 0.0D;
        }

        if (par4 == -0.0D)
        {
            par4 = 0.0D;
        }

        if (par6 == -0.0D)
        {
            par6 = 0.0D;
        }

        this.c = par2;
        this.d = par4;
        this.e = par6;
        this.b = par1Vec3Pool;
    }

    /**
     * Sets the x,y,z components of the vector as specified.
     */
    protected Vec3D b(double par1, double par3, double par5)
    {
        this.c = par1;
        this.d = par3;
        this.e = par5;
        return this;
    }

    /**
     * Normalizes the vector to a length of 1 (except if it is the zero vector)
     */
    public Vec3D a()
    {
        double var1 = (double) MathHelper.sqrt(this.c * this.c + this.d * this.d + this.e * this.e);
        return var1 < 1.0E-4D ? this.b.create(0.0D, 0.0D, 0.0D) : this.b.create(this.c / var1, this.d / var1, this.e / var1);
    }

    public double b(Vec3D par1Vec3)
    {
        return this.c * par1Vec3.c + this.d * par1Vec3.d + this.e * par1Vec3.e;
    }

    /**
     * Adds the specified x,y,z vector components to this vector and returns the resulting vector. Does not change this
     * vector.
     */
    public Vec3D add(double par1, double par3, double par5)
    {
        return this.b.create(this.c + par1, this.d + par3, this.e + par5);
    }

    /**
     * Euclidean distance between this and the specified vector, returned as double.
     */
    public double d(Vec3D par1Vec3)
    {
        double var2 = par1Vec3.c - this.c;
        double var4 = par1Vec3.d - this.d;
        double var6 = par1Vec3.e - this.e;
        return (double) MathHelper.sqrt(var2 * var2 + var4 * var4 + var6 * var6);
    }

    /**
     * The square of the Euclidean distance between this and the specified vector.
     */
    public double distanceSquared(Vec3D par1Vec3)
    {
        double var2 = par1Vec3.c - this.c;
        double var4 = par1Vec3.d - this.d;
        double var6 = par1Vec3.e - this.e;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }

    /**
     * The square of the Euclidean distance between this and the vector of x,y,z components passed in.
     */
    public double d(double par1, double par3, double par5)
    {
        double var7 = par1 - this.c;
        double var9 = par3 - this.d;
        double var11 = par5 - this.e;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    /**
     * Returns the length of the vector.
     */
    public double b()
    {
        return (double) MathHelper.sqrt(this.c * this.c + this.d * this.d + this.e * this.e);
    }

    /**
     * Returns a new vector with x value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Vec3D b(Vec3D par1Vec3, double par2)
    {
        double var4 = par1Vec3.c - this.c;
        double var6 = par1Vec3.d - this.d;
        double var8 = par1Vec3.e - this.e;

        if (var4 * var4 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double var10 = (par2 - this.c) / var4;
            return var10 >= 0.0D && var10 <= 1.0D ? this.b.create(this.c + var4 * var10, this.d + var6 * var10, this.e + var8 * var10) : null;
        }
    }

    /**
     * Returns a new vector with y value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Vec3D c(Vec3D par1Vec3, double par2)
    {
        double var4 = par1Vec3.c - this.c;
        double var6 = par1Vec3.d - this.d;
        double var8 = par1Vec3.e - this.e;

        if (var6 * var6 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double var10 = (par2 - this.d) / var6;
            return var10 >= 0.0D && var10 <= 1.0D ? this.b.create(this.c + var4 * var10, this.d + var6 * var10, this.e + var8 * var10) : null;
        }
    }

    /**
     * Returns a new vector with z value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Vec3D d(Vec3D par1Vec3, double par2)
    {
        double var4 = par1Vec3.c - this.c;
        double var6 = par1Vec3.d - this.d;
        double var8 = par1Vec3.e - this.e;

        if (var8 * var8 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double var10 = (par2 - this.e) / var8;
            return var10 >= 0.0D && var10 <= 1.0D ? this.b.create(this.c + var4 * var10, this.d + var6 * var10, this.e + var8 * var10) : null;
        }
    }

    public String toString()
    {
        return "(" + this.c + ", " + this.d + ", " + this.e + ")";
    }

    /**
     * Rotates the vector around the x axis by the specified angle.
     */
    public void a(float par1)
    {
        float var2 = MathHelper.cos(par1);
        float var3 = MathHelper.sin(par1);
        double var4 = this.c;
        double var6 = this.d * (double)var2 + this.e * (double)var3;
        double var8 = this.e * (double)var2 - this.d * (double)var3;
        this.c = var4;
        this.d = var6;
        this.e = var8;
    }

    /**
     * Rotates the vector around the y axis by the specified angle.
     */
    public void b(float par1)
    {
        float var2 = MathHelper.cos(par1);
        float var3 = MathHelper.sin(par1);
        double var4 = this.c * (double)var2 + this.e * (double)var3;
        double var6 = this.d;
        double var8 = this.e * (double)var2 - this.c * (double)var3;
        this.c = var4;
        this.d = var6;
        this.e = var8;
    }
}
