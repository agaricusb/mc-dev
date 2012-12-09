package net.minecraft.server;

public class PathfinderGoalRandomTargetNonTamed extends PathfinderGoalNearestAttackableTarget
{
    private EntityTameableAnimal g;

    public PathfinderGoalRandomTargetNonTamed(EntityTameableAnimal par1EntityTameable, Class par2Class, float par3, int par4, boolean par5)
    {
        super(par1EntityTameable, par2Class, par3, par4, par5);
        this.g = par1EntityTameable;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        return this.g.isTamed() ? false : super.a();
    }
}
