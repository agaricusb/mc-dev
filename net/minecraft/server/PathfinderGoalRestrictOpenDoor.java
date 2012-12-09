package net.minecraft.server;

public class PathfinderGoalRestrictOpenDoor extends PathfinderGoal
{
    private EntityCreature a;
    private VillageDoor b;

    public PathfinderGoalRestrictOpenDoor(EntityCreature par1EntityCreature)
    {
        this.a = par1EntityCreature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.a.world.u())
        {
            return false;
        }
        else
        {
            Village var1 = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 16);

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.b = var1.b(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
                return this.b == null ? false : (double)this.b.c(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ)) < 2.25D;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.a.world.u() ? false : !this.b.removed && this.b.a(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locZ));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().b(false);
        this.a.getNavigation().c(false);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a.getNavigation().b(true);
        this.a.getNavigation().c(true);
        this.b = null;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        this.b.e();
    }
}
