package net.minecraft.server;

public class PathfinderGoalOpenDoor extends PathfinderGoalDoorInteract
{
    boolean i;
    int j;

    public PathfinderGoalOpenDoor(EntityLiving par1EntityLiving, boolean par2)
    {
        super(par1EntityLiving);
        this.a = par1EntityLiving;
        this.i = par2;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.i && this.j > 0 && super.b();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.j = 20;
        this.e.setDoor(this.a.world, this.b, this.c, this.d, true);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        if (this.i)
        {
            this.e.setDoor(this.a.world, this.b, this.c, this.d, false);
        }
    }

    /**
     * Updates the task
     */
    public void e()
    {
        --this.j;
        super.e();
    }
}
