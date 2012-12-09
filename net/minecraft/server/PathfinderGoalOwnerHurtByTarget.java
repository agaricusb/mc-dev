package net.minecraft.server;

public class PathfinderGoalOwnerHurtByTarget extends PathfinderGoalTarget
{
    EntityTameableAnimal a;
    EntityLiving b;

    public PathfinderGoalOwnerHurtByTarget(EntityTameableAnimal par1EntityTameable)
    {
        super(par1EntityTameable, 32.0F, false);
        this.a = par1EntityTameable;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (!this.a.isTamed())
        {
            return false;
        }
        else
        {
            EntityLiving var1 = this.a.getOwner();

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.b = var1.aC();
                return this.a(this.b, false);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.d.b(this.b);
        super.c();
    }
}
