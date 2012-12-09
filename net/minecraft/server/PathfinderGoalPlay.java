package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalPlay extends PathfinderGoal
{
    private EntityVillager a;
    private EntityLiving b;
    private float c;
    private int d;

    public PathfinderGoalPlay(EntityVillager par1EntityVillager, float par2)
    {
        this.a = par1EntityVillager;
        this.c = par2;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.a.aE() >= 0)
        {
            return false;
        }
        else if (this.a.aB().nextInt(400) != 0)
        {
            return false;
        }
        else
        {
            List var1 = this.a.world.a(EntityVillager.class, this.a.boundingBox.grow(6.0D, 3.0D, 6.0D));
            double var2 = Double.MAX_VALUE;
            Iterator var4 = var1.iterator();

            while (var4.hasNext())
            {
                EntityVillager var5 = (EntityVillager)var4.next();

                if (var5 != this.a && !var5.o() && var5.aE() < 0)
                {
                    double var6 = var5.e(this.a);

                    if (var6 <= var2)
                    {
                        var2 = var6;
                        this.b = var5;
                    }
                }
            }

            if (this.b == null)
            {
                Vec3D var8 = RandomPositionGenerator.a(this.a, 16, 3);

                if (var8 == null)
                {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.d > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        if (this.b != null)
        {
            this.a.g(true);
        }

        this.d = 1000;
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a.g(false);
        this.b = null;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        --this.d;

        if (this.b != null)
        {
            if (this.a.e(this.b) > 4.0D)
            {
                this.a.getNavigation().a(this.b, this.c);
            }
        }
        else if (this.a.getNavigation().f())
        {
            Vec3D var1 = RandomPositionGenerator.a(this.a, 16, 3);

            if (var1 == null)
            {
                return;
            }

            this.a.getNavigation().a(var1.c, var1.d, var1.e, this.c);
        }
    }
}
