package net.minecraft.server;

public class DispenseBehaviorArrow extends DispenseBehaviorProjectile
{
    /** Reference to the MinecraftServer object. */
    final MinecraftServer b;

    public DispenseBehaviorArrow(MinecraftServer par1)
    {
        this.b = par1;
    }

    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected IProjectile a(World par1World, IPosition par2IPosition)
    {
        EntityArrow var3 = new EntityArrow(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        var3.fromPlayer = 1;
        return var3;
    }
}
