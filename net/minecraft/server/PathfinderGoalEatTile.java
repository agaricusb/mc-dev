package net.minecraft.server;

public class PathfinderGoalEatTile extends PathfinderGoal
{
    private EntityLiving b;
    private World c;

    /** A decrementing tick used for the sheep's head offset and animation. */
    int a = 0;

    public PathfinderGoalEatTile(EntityLiving par1EntityLiving)
    {
        this.b = par1EntityLiving;
        this.c = par1EntityLiving.world;
        this.a(7);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (this.b.aB().nextInt(this.b.isBaby() ? 50 : 1000) != 0)
        {
            return false;
        }
        else
        {
            int var1 = MathHelper.floor(this.b.locX);
            int var2 = MathHelper.floor(this.b.locY);
            int var3 = MathHelper.floor(this.b.locZ);
            return this.c.getTypeId(var1, var2, var3) == Block.LONG_GRASS.id && this.c.getData(var1, var2, var3) == 1 ? true : this.c.getTypeId(var1, var2 - 1, var3) == Block.GRASS.id;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a = 40;
        this.c.broadcastEntityEffect(this.b, (byte) 10);
        this.b.getNavigation().g();
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.a > 0;
    }

    public int f()
    {
        return this.a;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        this.a = Math.max(0, this.a - 1);

        if (this.a == 4)
        {
            int var1 = MathHelper.floor(this.b.locX);
            int var2 = MathHelper.floor(this.b.locY);
            int var3 = MathHelper.floor(this.b.locZ);

            if (this.c.getTypeId(var1, var2, var3) == Block.LONG_GRASS.id)
            {
                this.c.triggerEffect(2001, var1, var2, var3, Block.LONG_GRASS.id + 4096);
                this.c.setTypeId(var1, var2, var3, 0);
                this.b.aH();
            }
            else if (this.c.getTypeId(var1, var2 - 1, var3) == Block.GRASS.id)
            {
                this.c.triggerEffect(2001, var1, var2 - 1, var3, Block.GRASS.id);
                this.c.setTypeId(var1, var2 - 1, var3, Block.DIRT.id);
                this.b.aH();
            }
        }
    }
}
