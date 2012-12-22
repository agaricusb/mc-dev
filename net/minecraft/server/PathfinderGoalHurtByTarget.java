package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget
{
    boolean a;

    /** The PathNavigate of our entity. */
    EntityLiving b;

    public PathfinderGoalHurtByTarget(EntityLiving par1EntityLiving, boolean par2)
    {
        super(par1EntityLiving, 16.0F, false);
        this.a = par2;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        return this.a(this.d.aC(), true);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.d.aC() != null && this.d.aC() != this.b;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.d.b(this.d.aC());
        this.b = this.d.aC();

        if (this.a)
        {
            List var1 = this.d.world.a(this.d.getClass(), AxisAlignedBB.a().a(this.d.locX, this.d.locY, this.d.locZ, this.d.locX + 1.0D, this.d.locY + 1.0D, this.d.locZ + 1.0D).grow((double) this.e, 4.0D, (double) this.e));
            Iterator var2 = var1.iterator();

            while (var2.hasNext())
            {
                EntityLiving var3 = (EntityLiving)var2.next();

                if (this.d != var3 && var3.aG() == null)
                {
                    var3.b(this.d.aC());
                }
            }
        }

        super.c();
    }

    /**
     * Resets the task
     */
    public void d()
    {
        if (this.d.aG() != null && this.d.aG() instanceof EntityHuman && ((EntityHuman)this.d.aG()).abilities.isInvulnerable)
        {
            super.d();
        }
    }
}
