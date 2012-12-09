package net.minecraft.server;

public class PathfinderGoalMeleeAttack extends PathfinderGoal
{
    World a;
    EntityLiving b;
    EntityLiving c;

    /**
     * An amount of decrementing ticks that allows the entity to attack once the tick reaches 0.
     */
    int d;
    float e;
    boolean f;

    /** The PathEntity of our entity. */
    PathEntity g;
    Class h;
    private int i;

    public PathfinderGoalMeleeAttack(EntityLiving par1EntityLiving, Class par2Class, float par3, boolean par4)
    {
        this(par1EntityLiving, par3, par4);
        this.h = par2Class;
    }

    public PathfinderGoalMeleeAttack(EntityLiving par1EntityLiving, float par2, boolean par3)
    {
        this.d = 0;
        this.b = par1EntityLiving;
        this.a = par1EntityLiving.world;
        this.e = par2;
        this.f = par3;
        this.a(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        EntityLiving var1 = this.b.aG();

        if (var1 == null)
        {
            return false;
        }
        else if (this.h != null && !this.h.isAssignableFrom(var1.getClass()))
        {
            return false;
        }
        else
        {
            this.c = var1;
            this.g = this.b.getNavigation().a(this.c);
            return this.g != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        EntityLiving var1 = this.b.aG();
        return var1 == null ? false : (!this.c.isAlive() ? false : (!this.f ? !this.b.getNavigation().f() : this.b.e(MathHelper.floor(this.c.locX), MathHelper.floor(this.c.locY), MathHelper.floor(this.c.locZ))));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.b.getNavigation().a(this.g, this.e);
        this.i = 0;
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.c = null;
        this.b.getNavigation().g();
    }

    /**
     * Updates the task
     */
    public void e()
    {
        this.b.getControllerLook().a(this.c, 30.0F, 30.0F);

        if ((this.f || this.b.aA().canSee(this.c)) && --this.i <= 0)
        {
            this.i = 4 + this.b.aB().nextInt(7);
            this.b.getNavigation().a(this.c, this.e);
        }

        this.d = Math.max(this.d - 1, 0);
        double var1 = (double)(this.b.width * 2.0F * this.b.width * 2.0F);

        if (this.b.e(this.c.locX, this.c.boundingBox.b, this.c.locZ) <= var1)
        {
            if (this.d <= 0)
            {
                this.d = 20;

                if (this.b.bD() != null)
                {
                    this.b.bH();
                }

                this.b.m(this.c);
            }
        }
    }
}
