package net.minecraft.server;

public class PathfinderGoalFollowOwner extends PathfinderGoal
{
    private EntityTameableAnimal d;
    private EntityLiving e;
    World a;
    private float f;
    private Navigation g;
    private int h;
    float b;
    float c;
    private boolean i;

    public PathfinderGoalFollowOwner(EntityTameableAnimal par1EntityTameable, float par2, float par3, float par4)
    {
        this.d = par1EntityTameable;
        this.a = par1EntityTameable.world;
        this.f = par2;
        this.g = par1EntityTameable.getNavigation();
        this.c = par3;
        this.b = par4;
        this.a(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        EntityLiving var1 = this.d.getOwner();

        if (var1 == null)
        {
            return false;
        }
        else if (this.d.isSitting())
        {
            return false;
        }
        else if (this.d.e(var1) < (double)(this.c * this.c))
        {
            return false;
        }
        else
        {
            this.e = var1;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.g.f() && this.d.e(this.e) > (double)(this.b * this.b) && !this.d.isSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.h = 0;
        this.i = this.d.getNavigation().a();
        this.d.getNavigation().a(false);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.e = null;
        this.g.g();
        this.d.getNavigation().a(this.i);
    }

    /**
     * Updates the task
     */
    public void e()
    {
        this.d.getControllerLook().a(this.e, 10.0F, (float) this.d.bp());

        if (!this.d.isSitting())
        {
            if (--this.h <= 0)
            {
                this.h = 10;

                if (!this.g.a(this.e, this.f))
                {
                    if (this.d.e(this.e) >= 144.0D)
                    {
                        int var1 = MathHelper.floor(this.e.locX) - 2;
                        int var2 = MathHelper.floor(this.e.locZ) - 2;
                        int var3 = MathHelper.floor(this.e.boundingBox.b);

                        for (int var4 = 0; var4 <= 4; ++var4)
                        {
                            for (int var5 = 0; var5 <= 4; ++var5)
                            {
                                if ((var4 < 1 || var5 < 1 || var4 > 3 || var5 > 3) && this.a.v(var1 + var4, var3 - 1, var2 + var5) && !this.a.t(var1 + var4, var3, var2 + var5) && !this.a.t(var1 + var4, var3 + 1, var2 + var5))
                                {
                                    this.d.setPositionRotation((double) ((float) (var1 + var4) + 0.5F), (double) var3, (double) ((float) (var2 + var5) + 0.5F), this.d.yaw, this.d.pitch);
                                    this.g.g();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
