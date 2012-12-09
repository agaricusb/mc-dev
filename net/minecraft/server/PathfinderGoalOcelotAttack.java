package net.minecraft.server;

public class PathfinderGoalOcelotAttack extends PathfinderGoal
{
    World a;
    EntityLiving b;
    EntityLiving c;
    int d = 0;

    public PathfinderGoalOcelotAttack(EntityLiving par1EntityLiving)
    {
        this.b = par1EntityLiving;
        this.a = par1EntityLiving.world;
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
        else
        {
            this.c = var1;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.c.isAlive() ? false : (this.b.e(this.c) > 225.0D ? false : !this.b.getNavigation().f() || this.a());
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
        double var1 = (double)(this.b.width * 2.0F * this.b.width * 2.0F);
        double var3 = this.b.e(this.c.locX, this.c.boundingBox.b, this.c.locZ);
        float var5 = 0.23F;

        if (var3 > var1 && var3 < 16.0D)
        {
            var5 = 0.4F;
        }
        else if (var3 < 225.0D)
        {
            var5 = 0.18F;
        }

        this.b.getNavigation().a(this.c, var5);
        this.d = Math.max(this.d - 1, 0);

        if (var3 <= var1)
        {
            if (this.d <= 0)
            {
                this.d = 20;
                this.b.m(this.c);
            }
        }
    }
}
