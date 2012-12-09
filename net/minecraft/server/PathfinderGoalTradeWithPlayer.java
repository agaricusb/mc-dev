package net.minecraft.server;

public class PathfinderGoalTradeWithPlayer extends PathfinderGoal
{
    private EntityVillager a;

    public PathfinderGoalTradeWithPlayer(EntityVillager par1EntityVillager)
    {
        this.a = par1EntityVillager;
        this.a(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (!this.a.isAlive())
        {
            return false;
        }
        else if (this.a.H())
        {
            return false;
        }
        else if (!this.a.onGround)
        {
            return false;
        }
        else if (this.a.velocityChanged)
        {
            return false;
        }
        else
        {
            EntityHuman var1 = this.a.m_();
            return var1 == null ? false : (this.a.e(var1) > 16.0D ? false : var1.activeContainer instanceof Container);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().g();
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a.b_((EntityHuman) null);
    }
}
