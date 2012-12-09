package net.minecraft.server;

class PathfinderGoalSelectorItem
{
    /** The EntityAIBase object. */
    public PathfinderGoal a;

    /** Priority of the EntityAIBase */
    public int b;

    /** The EntityAITasks object of which this is an entry. */
    final PathfinderGoalSelector c;

    public PathfinderGoalSelectorItem(PathfinderGoalSelector par1EntityAITasks, int par2, PathfinderGoal par3EntityAIBase)
    {
        this.c = par1EntityAITasks;
        this.b = par2;
        this.a = par3EntityAIBase;
    }
}
