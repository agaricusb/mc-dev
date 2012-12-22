package net.minecraft.server;


public class DispenseBehaviorSnowBall extends DispenseBehaviorProjectile
{
    /** Instance of MinecraftServer. */
    final MinecraftServer b;

    public DispenseBehaviorSnowBall(MinecraftServer par1)
    {
        this.b = par1;
    }

    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected IProjectile a(World par1World, IPosition par2IPosition)
    {
        return new EntitySnowball(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }
}
