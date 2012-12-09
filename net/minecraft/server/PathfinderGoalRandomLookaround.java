package net.minecraft.server;

public class PathfinderGoalRandomLookaround extends PathfinderGoal
{
    /** The entity that is looking idle. */
    private EntityLiving a;

    /** X offset to look at */
    private double b;

    /** Z offset to look at */
    private double c;

    /**
     * A decrementing tick that stops the entity from being idle once it reaches 0.
     */
    private int d = 0;

    public PathfinderGoalRandomLookaround(EntityLiving par1EntityLiving)
    {
        this.a = par1EntityLiving;
        this.a(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        return this.a.aB().nextFloat() < 0.02F;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.d >= 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        double var1 = (Math.PI * 2D) * this.a.aB().nextDouble();
        this.b = Math.cos(var1);
        this.c = Math.sin(var1);
        this.d = 20 + this.a.aB().nextInt(20);
    }

    /**
     * Updates the task
     */
    public void e()
    {
        --this.d;
        this.a.getControllerLook().a(this.a.locX + this.b, this.a.locY + (double) this.a.getHeadHeight(), this.a.locZ + this.c, 10.0F, (float) this.a.bp());
    }
}
