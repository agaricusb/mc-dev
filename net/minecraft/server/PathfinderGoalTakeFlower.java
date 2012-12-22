package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalTakeFlower extends PathfinderGoal
{
    private EntityVillager a;
    private EntityIronGolem b;
    private int c;
    private boolean d = false;

    public PathfinderGoalTakeFlower(EntityVillager par1EntityVillager)
    {
        this.a = par1EntityVillager;
        this.a(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.a.getAge() >= 0)
        {
            return false;
        }
        else if (!this.a.world.u())
        {
            return false;
        }
        else
        {
            List var1 = this.a.world.a(EntityIronGolem.class, this.a.boundingBox.grow(6.0D, 2.0D, 6.0D));

            if (var1.isEmpty())
            {
                return false;
            }
            else
            {
                Iterator var2 = var1.iterator();

                while (var2.hasNext())
                {
                    EntityIronGolem var3 = (EntityIronGolem)var2.next();

                    if (var3.o() > 0)
                    {
                        this.b = var3;
                        break;
                    }
                }

                return this.b != null;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.b.o() > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.c = this.a.aB().nextInt(320);
        this.d = false;
        this.b.getNavigation().g();
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.b = null;
        this.a.getNavigation().g();
    }

    /**
     * Updates the task
     */
    public void e()
    {
        this.a.getControllerLook().a(this.b, 30.0F, 30.0F);

        if (this.b.o() == this.c)
        {
            this.a.getNavigation().a(this.b, 0.15F);
            this.d = true;
        }

        if (this.d && this.a.e(this.b) < 4.0D)
        {
            this.b.f(false);
            this.a.getNavigation().g();
        }
    }
}
