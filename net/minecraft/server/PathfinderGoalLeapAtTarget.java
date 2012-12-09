package net.minecraft.server;

public class PathfinderGoalLeapAtTarget extends PathfinderGoal
{
    /** The entity that is leaping. */
    EntityLiving a;

    /** The entity that the leaper is leaping towards. */
    EntityLiving b;

    /** The entity's motionY after leaping. */
    float c;

    public PathfinderGoalLeapAtTarget(EntityLiving par1EntityLiving, float par2)
    {
        this.a = par1EntityLiving;
        this.c = par2;
        this.a(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        this.b = this.a.aG();

        if (this.b == null)
        {
            return false;
        }
        else
        {
            double var1 = this.a.e(this.b);
            return var1 >= 4.0D && var1 <= 16.0D ? (!this.a.onGround ? false : this.a.aB().nextInt(5) == 0) : false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.a.onGround;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        double var1 = this.b.locX - this.a.locX;
        double var3 = this.b.locZ - this.a.locZ;
        float var5 = MathHelper.sqrt(var1 * var1 + var3 * var3);
        this.a.motX += var1 / (double)var5 * 0.5D * 0.800000011920929D + this.a.motX * 0.20000000298023224D;
        this.a.motZ += var3 / (double)var5 * 0.5D * 0.800000011920929D + this.a.motZ * 0.20000000298023224D;
        this.a.motY = (double)this.c;
    }
}
