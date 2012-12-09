package net.minecraft.server;

public class PathfinderGoalFloat extends PathfinderGoal
{
    private EntityLiving a;

    public PathfinderGoalFloat(EntityLiving par1EntityLiving)
    {
        this.a = par1EntityLiving;
        this.a(4);
        par1EntityLiving.getNavigation().e(true);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        return this.a.H() || this.a.J();
    }

    /**
     * Updates the task
     */
    public void e()
    {
        if (this.a.aB().nextFloat() < 0.8F)
        {
            this.a.getControllerJump().a();
        }
    }
}
