package net.minecraft.server;

public class PathfinderGoalPanic extends PathfinderGoal
{
    private EntityCreature a;
    private float b;
    private double c;
    private double d;
    private double e;

    public PathfinderGoalPanic(EntityCreature par1EntityCreature, float par2)
    {
        this.a = par1EntityCreature;
        this.b = par2;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.a.aC() == null && !this.a.isBurning())
        {
            return false;
        }
        else
        {
            Vec3D var1 = RandomPositionGenerator.a(this.a, 5, 4);

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.c = var1.c;
                this.d = var1.d;
                this.e = var1.e;
                return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().a(this.c, this.d, this.e, this.b);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.a.getNavigation().f();
    }
}
