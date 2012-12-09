package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class AABBPool
{
    /**
     * Maximum number of times the pool can be "cleaned" before the list is shrunk
     */
    private final int a;

    /**
     * Number of Pool entries to remove when cleanPool is called maxNumCleans times.
     */
    private final int b;

    /** List of AABB stored in this Pool */
    private final List pool = new ArrayList();

    /** Next index to use when adding a Pool Entry. */
    private int d = 0;

    /**
     * Largest index reached by this Pool (can be reset to 0 upon calling cleanPool)
     */
    private int largestSize = 0;

    /** Number of times this Pool has been cleaned */
    private int resizeTime = 0;

    public AABBPool(int par1, int par2)
    {
        this.a = par1;
        this.b = par2;
    }

    /**
     * Adds a AABB to the pool, or if there is an available AABB, updates an existing AABB entry to specified
     * coordinates
     */
    public AxisAlignedBB a(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        AxisAlignedBB var13;

        if (this.d >= this.pool.size())
        {
            var13 = new AxisAlignedBB(par1, par3, par5, par7, par9, par11);
            this.pool.add(var13);
        }
        else
        {
            var13 = (AxisAlignedBB)this.pool.get(this.d);
            var13.b(par1, par3, par5, par7, par9, par11);
        }

        ++this.d;
        return var13;
    }

    /**
     * Marks the pool as "empty", starting over when adding new entries. If this is called maxNumCleans times, the list
     * size is reduced
     */
    public void a()
    {
        if (this.d > this.largestSize)
        {
            this.largestSize = this.d;
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

        this.d = 0;
    }

    public int c()
    {
        return this.pool.size();
    }

    public int d()
    {
        return this.d;
    }
}
