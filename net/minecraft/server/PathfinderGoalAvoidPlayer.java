package net.minecraft.server;

import java.util.List;

public class PathfinderGoalAvoidPlayer extends PathfinderGoal
{
    /** The entity we are attached to */
    private EntityCreature a;
    private float b;
    private float c;
    private Entity d;
    private float e;

    /** The PathEntity of our entity */
    private PathEntity f;

    /** The PathNavigate of our entity */
    private Navigation g;

    /** The class of the entity we should avoid */
    private Class h;

    public PathfinderGoalAvoidPlayer(EntityCreature par1EntityCreature, Class par2Class, float par3, float par4, float par5)
    {
        this.a = par1EntityCreature;
        this.h = par2Class;
        this.e = par3;
        this.b = par4;
        this.c = par5;
        this.g = par1EntityCreature.getNavigation();
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.h == EntityHuman.class)
        {
            if (this.a instanceof EntityTameableAnimal && ((EntityTameableAnimal)this.a).isTamed())
            {
                return false;
            }

            this.d = this.a.world.findNearbyPlayer(this.a, (double) this.e);

            if (this.d == null)
            {
                return false;
            }
        }
        else
        {
            List var1 = this.a.world.a(this.h, this.a.boundingBox.grow((double) this.e, 3.0D, (double) this.e));

            if (var1.isEmpty())
            {
                return false;
            }

            this.d = (Entity)var1.get(0);
        }

        if (!this.a.aA().canSee(this.d))
        {
            return false;
        }
        else
        {
            Vec3D var2 = RandomPositionGenerator.b(this.a, 16, 7, this.a.world.getVec3DPool().create(this.d.locX, this.d.locY, this.d.locZ));

            if (var2 == null)
            {
                return false;
            }
            else if (this.d.e(var2.c, var2.d, var2.e) < this.d.e(this.a))
            {
                return false;
            }
            else
            {
                this.f = this.g.a(var2.c, var2.d, var2.e);
                return this.f == null ? false : this.f.b(var2);
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.g.f();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.g.a(this.f, this.b);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.d = null;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        if (this.a.e(this.d) < 49.0D)
        {
            this.a.getNavigation().a(this.c);
        }
        else
        {
            this.a.getNavigation().a(this.b);
        }
    }
}
