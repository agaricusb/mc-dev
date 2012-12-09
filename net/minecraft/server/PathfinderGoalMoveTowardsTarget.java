package net.minecraft.server;

public class PathfinderGoalMoveTowardsTarget extends PathfinderGoal
{
    private EntityCreature a;
    private EntityLiving b;
    private double c;
    private double d;
    private double e;
    private float f;
    private float g;

    public PathfinderGoalMoveTowardsTarget(EntityCreature par1EntityCreature, float par2, float par3)
    {
        this.a = par1EntityCreature;
        this.f = par2;
        this.g = par3;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        this.b = this.a.aG();

        if (this.b == null)
        {
            return false;
        }
        else if (this.b.e(this.a) > (double)(this.g * this.g))
        {
            return false;
        }
        else
        {
            Vec3D var1 = RandomPositionGenerator.a(this.a, 16, 7, this.a.world.getVec3DPool().create(this.b.locX, this.b.locY, this.b.locZ));

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
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.a.getNavigation().f() && this.b.isAlive() && this.b.e(this.a) < (double)(this.g * this.g);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.b = null;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().a(this.c, this.d, this.e, this.f);
    }
}
