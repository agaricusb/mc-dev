package net.minecraft.server;

public class PathfinderGoalRestrictSun extends PathfinderGoal
{
    private EntityCreature a;

    public PathfinderGoalRestrictSun(EntityCreature par1EntityCreature)
    {
        this.a = par1EntityCreature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        return this.a.world.u();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().d(true);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a.getNavigation().d(false);
    }
}
