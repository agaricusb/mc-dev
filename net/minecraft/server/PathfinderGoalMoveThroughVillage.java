package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PathfinderGoalMoveThroughVillage extends PathfinderGoal
{
    private EntityCreature a;
    private float b;

    /** The PathNavigate of our entity. */
    private PathEntity c;
    private VillageDoor d;
    private boolean e;
    private List f = new ArrayList();

    public PathfinderGoalMoveThroughVillage(EntityCreature par1EntityCreature, float par2, boolean par3)
    {
        this.a = par1EntityCreature;
        this.b = par2;
        this.e = par3;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        this.f();

        if (this.e && this.a.world.u())
        {
            return false;
        }
        else
        {
            Village var1 = this.a.world.villages.getClosestVillage(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ), 0);

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.d = this.a(var1);

                if (this.d == null)
                {
                    return false;
                }
                else
                {
                    boolean var2 = this.a.getNavigation().c();
                    this.a.getNavigation().b(false);
                    this.c = this.a.getNavigation().a((double) this.d.locX, (double) this.d.locY, (double) this.d.locZ);
                    this.a.getNavigation().b(var2);

                    if (this.c != null)
                    {
                        return true;
                    }
                    else
                    {
                        Vec3D var3 = RandomPositionGenerator.a(this.a, 10, 7, this.a.world.getVec3DPool().create((double) this.d.locX, (double) this.d.locY, (double) this.d.locZ));

                        if (var3 == null)
                        {
                            return false;
                        }
                        else
                        {
                            this.a.getNavigation().b(false);
                            this.c = this.a.getNavigation().a(var3.c, var3.d, var3.e);
                            this.a.getNavigation().b(var2);
                            return this.c != null;
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        if (this.a.getNavigation().f())
        {
            return false;
        }
        else
        {
            float var1 = this.a.width + 4.0F;
            return this.a.e((double) this.d.locX, (double) this.d.locY, (double) this.d.locZ) > (double)(var1 * var1);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().a(this.c, this.b);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        if (this.a.getNavigation().f() || this.a.e((double) this.d.locX, (double) this.d.locY, (double) this.d.locZ) < 16.0D)
        {
            this.f.add(this.d);
        }
    }

    private VillageDoor a(Village par1Village)
    {
        VillageDoor var2 = null;
        int var3 = Integer.MAX_VALUE;
        List var4 = par1Village.getDoors();
        Iterator var5 = var4.iterator();

        while (var5.hasNext())
        {
            VillageDoor var6 = (VillageDoor)var5.next();
            int var7 = var6.b(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));

            if (var7 < var3 && !this.a(var6))
            {
                var2 = var6;
                var3 = var7;
            }
        }

        return var2;
    }

    private boolean a(VillageDoor par1VillageDoorInfo)
    {
        Iterator var2 = this.f.iterator();
        VillageDoor var3;

        do
        {
            if (!var2.hasNext())
            {
                return false;
            }

            var3 = (VillageDoor)var2.next();
        }
        while (par1VillageDoorInfo.locX != var3.locX || par1VillageDoorInfo.locY != var3.locY || par1VillageDoorInfo.locZ != var3.locZ);

        return true;
    }

    private void f()
    {
        if (this.f.size() > 15)
        {
            this.f.remove(0);
        }
    }
}
