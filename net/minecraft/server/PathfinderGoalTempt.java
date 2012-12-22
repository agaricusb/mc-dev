package net.minecraft.server;

public class PathfinderGoalTempt extends PathfinderGoal
{
    /** The entity using this AI that is tempted by the player. */
    private EntityCreature a;
    private float b;
    private double c;
    private double d;
    private double e;
    private double f;
    private double g;

    /** The player that is tempting the entity that is using this AI. */
    private EntityHuman h;

    /**
     * A counter that is decremented each time the shouldExecute method is called. The shouldExecute method will always
     * return false if delayTemptCounter is greater than 0.
     */
    private int i = 0;
    private boolean j;

    /**
     * This field saves the ID of the items that can be used to breed entities with this behaviour.
     */
    private int k;

    /**
     * Whether the entity using this AI will be scared by the tempter's sudden movement.
     */
    private boolean l;
    private boolean m;

    public PathfinderGoalTempt(EntityCreature par1EntityCreature, float par2, int par3, boolean par4)
    {
        this.a = par1EntityCreature;
        this.b = par2;
        this.k = par3;
        this.l = par4;
        this.a(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.i > 0)
        {
            --this.i;
            return false;
        }
        else
        {
            this.h = this.a.world.findNearbyPlayer(this.a, 10.0D);

            if (this.h == null)
            {
                return false;
            }
            else
            {
                ItemStack var1 = this.h.bS();
                return var1 == null ? false : var1.id == this.k;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        if (this.l)
        {
            if (this.a.e(this.h) < 36.0D)
            {
                if (this.h.e(this.c, this.d, this.e) > 0.010000000000000002D)
                {
                    return false;
                }

                if (Math.abs((double)this.h.pitch - this.f) > 5.0D || Math.abs((double)this.h.yaw - this.g) > 5.0D)
                {
                    return false;
                }
            }
            else
            {
                this.c = this.h.locX;
                this.d = this.h.locY;
                this.e = this.h.locZ;
            }

            this.f = (double)this.h.pitch;
            this.g = (double)this.h.yaw;
        }

        return this.a();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.c = this.h.locX;
        this.d = this.h.locY;
        this.e = this.h.locZ;
        this.j = true;
        this.m = this.a.getNavigation().a();
        this.a.getNavigation().a(false);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.h = null;
        this.a.getNavigation().g();
        this.i = 100;
        this.j = false;
        this.a.getNavigation().a(this.m);
    }

    /**
     * Updates the task
     */
    public void e()
    {
        this.a.getControllerLook().a(this.h, 30.0F, (float) this.a.bp());

        if (this.a.e(this.h) < 6.25D)
        {
            this.a.getNavigation().g();
        }
        else
        {
            this.a.getNavigation().a(this.h, this.b);
        }
    }

    public boolean f()
    {
        return this.j;
    }
}
