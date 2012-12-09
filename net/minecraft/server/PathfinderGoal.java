package net.minecraft.server;

public abstract class PathfinderGoal
{
    /**
     * A bitmask telling which other tasks may not run concurrently. The test is a simple bitwise AND - if it yields
     * zero, the two tasks may run concurrently, if not - they must run exclusively from each other.
     */
    private int a = 0;

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public abstract boolean a();

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.a();
    }

    /**
     * Returns whether the task requires multiple updates or not
     */
    public boolean i()
    {
        return true;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c() {}

    /**
     * Resets the task
     */
    public void d() {}

    /**
     * Updates the task
     */
    public void e() {}

    /**
     * Sets a bitmask telling which other tasks may not run concurrently. The test is a simple bitwise AND - if it
     * yields zero, the two tasks may run concurrently, if not - they must run exclusively from each other.
     */
    public void a(int par1)
    {
        this.a = par1;
    }

    /**
     * Get a bitmask telling which other tasks may not run concurrently. The test is a simple bitwise AND - if it yields
     * zero, the two tasks may run concurrently, if not - they must run exclusively from each other.
     */
    public int j()
    {
        return this.a;
    }
}
