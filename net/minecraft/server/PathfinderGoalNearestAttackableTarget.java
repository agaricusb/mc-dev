package net.minecraft.server;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PathfinderGoalNearestAttackableTarget extends PathfinderGoalTarget
{
    EntityLiving a;
    Class b;
    int c;
    private final IEntitySelector g;

    /** Instance of EntityAINearestAttackableTargetSorter. */
    private DistanceComparator h;

    public PathfinderGoalNearestAttackableTarget(EntityLiving par1EntityLiving, Class par2Class, float par3, int par4, boolean par5)
    {
        this(par1EntityLiving, par2Class, par3, par4, par5, false);
    }

    public PathfinderGoalNearestAttackableTarget(EntityLiving par1EntityLiving, Class par2Class, float par3, int par4, boolean par5, boolean par6)
    {
        this(par1EntityLiving, par2Class, par3, par4, par5, par6, (IEntitySelector)null);
    }

    public PathfinderGoalNearestAttackableTarget(EntityLiving par1, Class par2, float par3, int par4, boolean par5, boolean par6, IEntitySelector par7IEntitySelector)
    {
        super(par1, par3, par5, par6);
        this.b = par2;
        this.e = par3;
        this.c = par4;
        this.h = new DistanceComparator(this, par1);
        this.g = par7IEntitySelector;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.c > 0 && this.d.aB().nextInt(this.c) != 0)
        {
            return false;
        }
        else
        {
            if (this.b == EntityHuman.class)
            {
                EntityHuman var1 = this.d.world.findNearbyVulnerablePlayer(this.d, (double) this.e);

                if (this.a(var1, false))
                {
                    this.a = var1;
                    return true;
                }
            }
            else
            {
                List var5 = this.d.world.a(this.b, this.d.boundingBox.grow((double) this.e, 4.0D, (double) this.e), this.g);
                Collections.sort(var5, this.h);
                Iterator var2 = var5.iterator();

                while (var2.hasNext())
                {
                    Entity var3 = (Entity)var2.next();
                    EntityLiving var4 = (EntityLiving)var3;

                    if (this.a(var4, false))
                    {
                        this.a = var4;
                        return true;
                    }
                }
            }

            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.d.b(this.a);
        super.c();
    }
}
