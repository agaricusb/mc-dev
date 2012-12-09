package net.minecraft.server;

public abstract class PathfinderGoalDoorInteract extends PathfinderGoal
{
    protected EntityLiving a;
    protected int b;
    protected int c;
    protected int d;
    protected BlockDoor e;

    /**
     * If is true then the Entity has stopped Door Interaction and compoleted the task.
     */
    boolean f;
    float g;
    float h;

    public PathfinderGoalDoorInteract(EntityLiving par1EntityLiving)
    {
        this.a = par1EntityLiving;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (!this.a.positionChanged)
        {
            return false;
        }
        else
        {
            Navigation var1 = this.a.getNavigation();
            PathEntity var2 = var1.d();

            if (var2 != null && !var2.b() && var1.c())
            {
                for (int var3 = 0; var3 < Math.min(var2.e() + 2, var2.d()); ++var3)
                {
                    PathPoint var4 = var2.a(var3);
                    this.b = var4.a;
                    this.c = var4.b + 1;
                    this.d = var4.c;

                    if (this.a.e((double) this.b, this.a.locY, (double) this.d) <= 2.25D)
                    {
                        this.e = this.a(this.b, this.c, this.d);

                        if (this.e != null)
                        {
                            return true;
                        }
                    }
                }

                this.b = MathHelper.floor(this.a.locX);
                this.c = MathHelper.floor(this.a.locY + 1.0D);
                this.d = MathHelper.floor(this.a.locZ);
                this.e = this.a(this.b, this.c, this.d);
                return this.e != null;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.f;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.f = false;
        this.g = (float)((double)((float)this.b + 0.5F) - this.a.locX);
        this.h = (float)((double)((float)this.d + 0.5F) - this.a.locZ);
    }

    /**
     * Updates the task
     */
    public void e()
    {
        float var1 = (float)((double)((float)this.b + 0.5F) - this.a.locX);
        float var2 = (float)((double)((float)this.d + 0.5F) - this.a.locZ);
        float var3 = this.g * var1 + this.h * var2;

        if (var3 < 0.0F)
        {
            this.f = true;
        }
    }

    /**
     * Determines if a door can be broken with AI.
     */
    private BlockDoor a(int par1, int par2, int par3)
    {
        int var4 = this.a.world.getTypeId(par1, par2, par3);
        return var4 != Block.WOODEN_DOOR.id ? null : (BlockDoor) Block.byId[var4];
    }
}
