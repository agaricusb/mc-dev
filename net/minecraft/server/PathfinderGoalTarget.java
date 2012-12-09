package net.minecraft.server;

public abstract class PathfinderGoalTarget extends PathfinderGoal
{
    /** The entity that this task belongs to */
    protected EntityLiving d;
    protected float e;

    /**
     * If true, EntityAI targets must be able to be seen (cannot be blocked by walls) to be suitable targets.
     */
    protected boolean f;
    private boolean a;
    private int b;
    private int c;
    private int g;

    public PathfinderGoalTarget(EntityLiving par1EntityLiving, float par2, boolean par3)
    {
        this(par1EntityLiving, par2, par3, false);
    }

    public PathfinderGoalTarget(EntityLiving par1EntityLiving, float par2, boolean par3, boolean par4)
    {
        this.b = 0;
        this.c = 0;
        this.g = 0;
        this.d = par1EntityLiving;
        this.e = par2;
        this.f = par3;
        this.a = par4;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        EntityLiving var1 = this.d.aG();

        if (var1 == null)
        {
            return false;
        }
        else if (!var1.isAlive())
        {
            return false;
        }
        else if (this.d.e(var1) > (double)(this.e * this.e))
        {
            return false;
        }
        else
        {
            if (this.f)
            {
                if (this.d.aA().canSee(var1))
                {
                    this.g = 0;
                }
                else if (++this.g > 60)
                {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.b = 0;
        this.c = 0;
        this.g = 0;
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.d.b((EntityLiving) null);
    }

    /**
     * A method used to see if an entity is a suitable target through a number of checks.
     */
    protected boolean a(EntityLiving par1EntityLiving, boolean par2)
    {
        if (par1EntityLiving == null)
        {
            return false;
        }
        else if (par1EntityLiving == this.d)
        {
            return false;
        }
        else if (!par1EntityLiving.isAlive())
        {
            return false;
        }
        else if (!this.d.a(par1EntityLiving.getClass()))
        {
            return false;
        }
        else
        {
            if (this.d instanceof EntityTameableAnimal && ((EntityTameableAnimal)this.d).isTamed())
            {
                if (par1EntityLiving instanceof EntityTameableAnimal && ((EntityTameableAnimal)par1EntityLiving).isTamed())
                {
                    return false;
                }

                if (par1EntityLiving == ((EntityTameableAnimal)this.d).getOwner())
                {
                    return false;
                }
            }
            else if (par1EntityLiving instanceof EntityHuman && !par2 && ((EntityHuman)par1EntityLiving).abilities.isInvulnerable)
            {
                return false;
            }

            if (!this.d.e(MathHelper.floor(par1EntityLiving.locX), MathHelper.floor(par1EntityLiving.locY), MathHelper.floor(par1EntityLiving.locZ)))
            {
                return false;
            }
            else if (this.f && !this.d.aA().canSee(par1EntityLiving))
            {
                return false;
            }
            else
            {
                if (this.a)
                {
                    if (--this.c <= 0)
                    {
                        this.b = 0;
                    }

                    if (this.b == 0)
                    {
                        this.b = this.a(par1EntityLiving) ? 1 : 2;
                    }

                    if (this.b == 2)
                    {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    private boolean a(EntityLiving par1EntityLiving)
    {
        this.c = 10 + this.d.aB().nextInt(5);
        PathEntity var2 = this.d.getNavigation().a(par1EntityLiving);

        if (var2 == null)
        {
            return false;
        }
        else
        {
            PathPoint var3 = var2.c();

            if (var3 == null)
            {
                return false;
            }
            else
            {
                int var4 = var3.a - MathHelper.floor(par1EntityLiving.locX);
                int var5 = var3.c - MathHelper.floor(par1EntityLiving.locZ);
                return (double)(var4 * var4 + var5 * var5) <= 2.25D;
            }
        }
    }
}
