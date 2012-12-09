package net.minecraft.server;

public class PathfinderGoalLookAtTradingPlayer extends PathfinderGoalLookAtPlayer
{
    private final EntityVillager b;

    public PathfinderGoalLookAtTradingPlayer(EntityVillager par1EntityVillager)
    {
        super(par1EntityVillager, EntityHuman.class, 8.0F);
        this.b = par1EntityVillager;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.b.p())
        {
            this.a = this.b.m_();
            return true;
        }
        else
        {
            return false;
        }
    }
}
