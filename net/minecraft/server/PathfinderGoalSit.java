package net.minecraft.server;

public class PathfinderGoalSit extends PathfinderGoal
{
    private EntityTameableAnimal a;

    /** If the EntityTameable is sitting. */
    private boolean b = false;

    public PathfinderGoalSit(EntityTameableAnimal par1EntityTameable)
    {
        this.a = par1EntityTameable;
        this.a(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (!this.a.isTamed())
        {
            return false;
        }
        else if (this.a.H())
        {
            return false;
        }
        else if (!this.a.onGround)
        {
            return false;
        }
        else
        {
            EntityLiving var1 = this.a.getOwner();
            return var1 == null ? true : (this.a.e(var1) < 144.0D && var1.aC() != null ? false : this.b);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().g();
        this.a.setSitting(true);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a.setSitting(false);
    }

    /**
     * Sets the sitting flag.
     */
    public void a(boolean par1)
    {
        this.b = par1;
    }
}
