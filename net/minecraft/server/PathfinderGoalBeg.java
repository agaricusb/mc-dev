package net.minecraft.server;

public class PathfinderGoalBeg extends PathfinderGoal
{
    private EntityWolf a;
    private EntityHuman b;
    private World c;
    private float d;
    private int e;

    public PathfinderGoalBeg(EntityWolf par1EntityWolf, float par2)
    {
        this.a = par1EntityWolf;
        this.c = par1EntityWolf.world;
        this.d = par2;
        this.a(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        this.b = this.c.findNearbyPlayer(this.a, (double) this.d);
        return this.b == null ? false : this.a(this.b);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return !this.b.isAlive() ? false : (this.a.e(this.b) > (double)(this.d * this.d) ? false : this.e > 0 && this.a(this.b));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.j(true);
        this.e = 40 + this.a.aB().nextInt(40);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a.j(false);
        this.b = null;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        this.a.getControllerLook().a(this.b.locX, this.b.locY + (double) this.b.getHeadHeight(), this.b.locZ, 10.0F, (float) this.a.bp());
        --this.e;
    }

    /**
     * Gets if the Player has the Bone in the hand.
     */
    private boolean a(EntityHuman par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getItemInHand();
        return var2 == null ? false : (!this.a.isTamed() && var2.id == Item.BONE.id ? true : this.a.c(var2));
    }
}
