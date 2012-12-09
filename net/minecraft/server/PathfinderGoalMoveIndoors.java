package net.minecraft.server;

public class PathfinderGoalMoveIndoors extends PathfinderGoal
{
    private EntityCreature a;
    private VillageDoor b;
    private int c = -1;
    private int d = -1;

    public PathfinderGoalMoveIndoors(EntityCreature par1EntityCreature)
    {
        this.a = par1EntityCreature;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if ((!this.a.world.u() || this.a.world.N()) && !this.a.world.worldProvider.f)
        {
            if (this.a.aB().nextInt(50) != 0)
            {
                return false;
            }
            else if (this.c != -1 && this.a.e((double) this.c, this.a.locY, (double) this.d) < 4.0D)
            {
                return false;
            }
            else
            {
                Village var1 = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 14);

                if (var1 == null)
                {
                    return false;
                }
                else
                {
                    this.b = var1.c(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
                    return this.b != null;
                }
            }
        }
        else
        {
            return false;
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
        this.c = -1;

        if (this.a.e((double) this.b.getIndoorsX(), (double) this.b.locY, (double) this.b.getIndoorsZ()) > 256.0D)
        {
            Vec3D var1 = RandomPositionGenerator.a(this.a, 14, 3, this.a.world.getVec3DPool().create((double) this.b.getIndoorsX() + 0.5D, (double) this.b.getIndoorsY(), (double) this.b.getIndoorsZ() + 0.5D));

            if (var1 != null)
            {
                this.a.getNavigation().a(var1.c, var1.d, var1.e, 0.3F);
            }
        }
        else
        {
            this.a.getNavigation().a((double) this.b.getIndoorsX() + 0.5D, (double) this.b.getIndoorsY(), (double) this.b.getIndoorsZ() + 0.5D, 0.3F);
        }
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.c = this.b.getIndoorsX();
        this.d = this.b.getIndoorsZ();
        this.b = null;
    }
}
