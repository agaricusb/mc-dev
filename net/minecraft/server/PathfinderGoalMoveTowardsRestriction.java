package net.minecraft.server;

public class PathfinderGoalMoveTowardsRestriction extends PathfinderGoal
{
    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private float e;

    public PathfinderGoalMoveTowardsRestriction(EntityCreature par1EntityCreature, float par2)
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
        if (this.a.aI())
        {
            return false;
        }
        else
        {
            ChunkCoordinates var1 = this.a.aJ();
            Vec3D var2 = RandomPositionGenerator.a(this.a, 16, 7, this.a.world.getVec3DPool().create((double) var1.x, (double) var1.y, (double) var1.z));

            if (var2 == null)
            {
                return false;
            }
            else
            {
                this.b = var2.c;
                this.c = var2.d;
                this.d = var2.e;
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
