package net.minecraft.server;

public class PathfinderGoalOfferFlower extends PathfinderGoal
{
    private EntityIronGolem a;
    private EntityVillager b;
    private int c;

    public PathfinderGoalOfferFlower(EntityIronGolem par1EntityIronGolem)
    {
        this.a = par1EntityIronGolem;
        this.a(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (!this.a.world.u())
        {
            return false;
        }
        else if (this.a.aB().nextInt(8000) != 0)
        {
            return false;
        }
        else
        {
            this.b = (EntityVillager)this.a.world.a(EntityVillager.class, this.a.boundingBox.grow(6.0D, 2.0D, 6.0D), this.a);
            return this.b != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.c > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.c = 400;
        this.a.f(true);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a.f(false);
        this.b = null;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        this.a.getControllerLook().a(this.b, 30.0F, 30.0F);
        --this.c;
    }
}
