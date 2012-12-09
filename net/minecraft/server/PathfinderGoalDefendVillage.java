package net.minecraft.server;

public class PathfinderGoalDefendVillage extends PathfinderGoalTarget
{
    EntityIronGolem a;

    /**
     * The aggressor of the iron golem's village which is now the golem's attack target.
     */
    EntityLiving b;

    public PathfinderGoalDefendVillage(EntityIronGolem par1EntityIronGolem)
    {
        super(par1EntityIronGolem, 16.0F, false, true);
        this.a = par1EntityIronGolem;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        Village var1 = this.a.m();

        if (var1 == null)
        {
            return false;
        }
        else
        {
            this.b = var1.b(this.a);

            if (!this.a(this.b, false))
            {
                if (this.d.aB().nextInt(20) == 0)
                {
                    this.b = var1.c(this.a);
                    return this.a(this.b, false);
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.b(this.b);
        super.c();
    }
}
