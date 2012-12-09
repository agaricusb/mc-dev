package net.minecraft.server;

public class PathfinderGoalRandomStroll extends PathfinderGoal
{
    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private float e;

    public PathfinderGoalRandomStroll(EntityCreature par1EntityCreature, float par2)
    {
        this.a = par1EntityCreature;
        this.e = par2;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.a.aE() >= 100)
        {
            return false;
        }
        else if (this.a.aB().nextInt(120) != 0)
        {
            return false;
        }
        else
        {
            Vec3D var1 = RandomPositionGenerator.a(this.a, 10, 7);

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.b = var1.c;
                this.c = var1.d;
                this.d = var1.e;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.a.getNavigation().f();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().a(this.b, this.c, this.d, this.e);
    }
}
