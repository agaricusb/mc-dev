package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalFleeSun extends PathfinderGoal
{
    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private float e;
    private World f;

    public PathfinderGoalFleeSun(EntityCreature par1EntityCreature, float par2)
    {
        this.a = par1EntityCreature;
        this.e = par2;
        this.f = par1EntityCreature.world;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (!this.f.u())
        {
            return false;
        }
        else if (!this.a.isBurning())
        {
            return false;
        }
        else if (!this.f.k(MathHelper.floor(this.a.locX), (int) this.a.boundingBox.b, MathHelper.floor(this.a.locZ)))
        {
            return false;
        }
        else
        {
            Vec3D var1 = this.f();

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.b = var1.c;
                this.c = var1.d;
                this.d = var1.e;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.a.getNavigation().f();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().a(this.b, this.c, this.d, this.e);
    }

    private Vec3D f()
    {
        Random var1 = this.a.aB();

        for (int var2 = 0; var2 < 10; ++var2)
        {
            int var3 = MathHelper.floor(this.a.locX + (double) var1.nextInt(20) - 10.0D);
            int var4 = MathHelper.floor(this.a.boundingBox.b + (double) var1.nextInt(6) - 3.0D);
            int var5 = MathHelper.floor(this.a.locZ + (double) var1.nextInt(20) - 10.0D);

            if (!this.f.k(var3, var4, var5) && this.a.a(var3, var4, var5) < 0.0F)
            {
                return this.f.getVec3DPool().create((double) var3, (double) var4, (double) var5);
            }
        }

        return null;
    }
}
