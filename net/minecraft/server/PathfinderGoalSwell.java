package net.minecraft.server;

public class PathfinderGoalSwell extends PathfinderGoal
{
    /** The creeper that is swelling. */
    EntityCreeper a;

    /**
     * The creeper's attack target. This is used for the changing of the creeper's state.
     */
    EntityLiving b;

    public PathfinderGoalSwell(EntityCreeper par1EntityCreeper)
    {
        this.a = par1EntityCreeper;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        EntityLiving var1 = this.a.aG();
        return this.a.o() > 0 || var1 != null && this.a.e(var1) < 9.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().g();
        this.b = this.a.aG();
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
        if (this.b == null)
        {
            this.a.a(-1);
        }
        else if (this.a.e(this.b) > 49.0D)
        {
            this.a.a(-1);
        }
        else if (!this.a.aA().canSee(this.b))
        {
            this.a.a(-1);
        }
        else
        {
            this.a.a(1);
        }
    }
}
