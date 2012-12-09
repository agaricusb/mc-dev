package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class Vec3DPool
{
    private final int a;
    private final int b;

    /** items at and above nextFreeSpace are assumed to be available */
    private final List pool = new ArrayList();
    private int position = 0;
    private int largestSize = 0;
    private int resizeTime = 0;

    public Vec3DPool(int par1, int par2)
    {
        this.a = par1;
        this.b = par2;
    }

    /**
     * extends the pool if all vecs are currently "out"
     */
    public Vec3D create(double par1, double par3, double par5)
    {
        if (this.e())
        {
            return new Vec3D(this, par1, par3, par5);
        }
        else
        {
            Vec3D var7;

            if (this.position >= this.pool.size())
            {
                var7 = new Vec3D(this, par1, par3, par5);
                this.pool.add(var7);
            }
            else
            {
                var7 = (Vec3D)this.pool.get(this.position);
                var7.b(par1, par3, par5);
            }

            ++this.position;
            return var7;
        }
    }

    /**
     * Will truncate the array everyN clears to the maximum size observed since the last truncation.
     */
    public void a()
    {
        if (!this.e())
        {
            if (this.position > this.largestSize)
            {
                this.largestSize = this.position;
            }

            if (this.resizeTime++ == this.a)
            {
                int var1 = Math.max(this.largestSize, this.pool.size() - this.b);

                while (this.pool.size() > var1)
                {
                    this.pool.remove(var1);
                }

                this.largestSize = 0;
                this.resizeTime = 0;
            }

            this.position = 0;
        }
    }

    public int c()
    {
        return this.pool.size();
    }

    public int d()
    {
        return this.position;
    }

    private boolean e()
    {
        return this.b < 0 || this.a < 0;
    }
}
