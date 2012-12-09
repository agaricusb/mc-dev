package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalFollowParent extends PathfinderGoal
{
    /** The child that is following its parent. */
    EntityAnimal a;
    EntityAnimal b;
    float c;
    private int d;

    public PathfinderGoalFollowParent(EntityAnimal par1EntityAnimal, float par2)
    {
        this.a = par1EntityAnimal;
        this.c = par2;
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
        else
        {
            List var1 = this.a.world.a(this.a.getClass(), this.a.boundingBox.grow(8.0D, 4.0D, 8.0D));
            EntityAnimal var2 = null;
            double var3 = Double.MAX_VALUE;
            Iterator var5 = var1.iterator();

            while (var5.hasNext())
            {
                EntityAnimal var6 = (EntityAnimal)var5.next();

                if (var6.aE() >= 0)
                {
                    double var7 = this.a.e(var6);

                    if (var7 <= var3)
                    {
                        var3 = var7;
                        var2 = var6;
                    }
                }
            }

            if (var2 == null)
            {
                return false;
            }
            else if (var3 < 9.0D)
            {
                return false;
            }
            else
            {
                this.b = var2;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        if (!this.b.isAlive())
        {
            return false;
        }
        else
        {
            double var1 = this.a.e(this.b);
            return var1 >= 9.0D && var1 <= 256.0D;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.d = 0;
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.b = null;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        if (--this.d <= 0)
        {
            this.d = 10;
            this.a.getNavigation().a(this.b, this.c);
        }
    }
}
