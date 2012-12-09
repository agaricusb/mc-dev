package net.minecraft.server;

public class PathfinderGoalBreakDoor extends PathfinderGoalDoorInteract
{
    private int i;
    private int j = -1;

    public PathfinderGoalBreakDoor(EntityLiving par1EntityLiving)
    {
        super(par1EntityLiving);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        return !super.a() ? false : (!this.a.world.getGameRules().getBoolean("mobGriefing") ? false : !this.e.a_(this.a.world, this.b, this.c, this.d));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        super.c();
        this.i = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        double var1 = this.a.e((double) this.b, (double) this.c, (double) this.d);
        return this.i <= 240 && !this.e.a_(this.a.world, this.b, this.c, this.d) && var1 < 4.0D;
    }

    /**
     * Resets the task
     */
    public void d()
    {
        super.d();
        this.a.world.g(this.a.id, this.b, this.c, this.d, -1);
    }

    /**
     * Updates the task
     */
    public void e()
    {
        super.e();

        if (this.a.aB().nextInt(20) == 0)
        {
            this.a.world.triggerEffect(1010, this.b, this.c, this.d, 0);
        }

        ++this.i;
        int var1 = (int)((float)this.i / 240.0F * 10.0F);

        if (var1 != this.j)
        {
            this.a.world.g(this.a.id, this.b, this.c, this.d, var1);
            this.j = var1;
        }

        if (this.i == 240 && this.a.world.difficulty == 3)
        {
            this.a.world.setTypeId(this.b, this.c, this.d, 0);
            this.a.world.triggerEffect(1012, this.b, this.c, this.d, 0);
            this.a.world.triggerEffect(2001, this.b, this.c, this.d, this.e.id);
        }
    }
}
