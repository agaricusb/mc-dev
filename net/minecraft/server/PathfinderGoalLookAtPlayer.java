package net.minecraft.server;

public class PathfinderGoalLookAtPlayer extends PathfinderGoal
{
    private EntityLiving b;

    /** The closest entity which is being watched by this one. */
    protected Entity a;
    private float c;
    private int d;
    private float e;
    private Class f;

    public PathfinderGoalLookAtPlayer(EntityLiving par1EntityLiving, Class par2Class, float par3)
    {
        this.b = par1EntityLiving;
        this.f = par2Class;
        this.c = par3;
        this.e = 0.02F;
        this.a(2);
    }

    public PathfinderGoalLookAtPlayer(EntityLiving par1EntityLiving, Class par2Class, float par3, float par4)
    {
        this.b = par1EntityLiving;
        this.f = par2Class;
        this.c = par3;
        this.e = par4;
        this.a(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.b.aB().nextFloat() >= this.e)
        {
            return false;
        }
        else
        {
            if (this.f == EntityHuman.class)
            {
                this.a = this.b.world.findNearbyPlayer(this.b, (double) this.c);
            }
            else
            {
                this.a = this.b.world.a(this.f, this.b.boundingBox.grow((double) this.c, 3.0D, (double) this.c), this.b);
            }

            return this.a != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.a.isAlive() ? false : (this.b.e(this.a) > (double)(this.c * this.c) ? false : this.d > 0);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.d = 40 + this.b.aB().nextInt(40);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a = null;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        this.b.getControllerLook().a(this.a.locX, this.a.locY + (double) this.a.getHeadHeight(), this.a.locZ, 10.0F, (float) this.b.bp());
        --this.d;
    }
}
