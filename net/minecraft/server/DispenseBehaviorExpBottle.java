package net.minecraft.server;


public class DispenseBehaviorExpBottle extends DispenseBehaviorProjectile
{
    /** Reference to the MinecraftServer object. */
    final MinecraftServer b;

    public DispenseBehaviorExpBottle(MinecraftServer par1)
    {
        this.b = par1;
    }

    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected IProjectile a(World par1World, IPosition par2IPosition)
    {
        return new EntityThrownExpBottle(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }

    protected float a()
    {
        return super.a() * 0.5F;
    }

    protected float b()
    {
        return super.b() * 1.25F;
    }
}
