package net.minecraft.server;


public class DispenseBehaviorEgg extends DispenseBehaviorProjectile
{
    final MinecraftServer b;

    public DispenseBehaviorEgg(MinecraftServer par1MinecraftServer)
    {
        this.b = par1MinecraftServer;
    }

    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected IProjectile a(World par1World, IPosition par2IPosition)
    {
        return new EntityEgg(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }
}
