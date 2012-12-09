package net.minecraft.server;

class DispenseBehaviorThrownPotion extends DispenseBehaviorProjectile
{
    final ItemStack b;

    /** Reference to the BehaviorPotionDispense object. */
    final DispenseBehaviorPotion c;

    DispenseBehaviorThrownPotion(DispenseBehaviorPotion par1, ItemStack par2)
    {
        this.c = par1;
        this.b = par2;
    }

    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected IProjectile a(World par1World, IPosition par2IPosition)
    {
        return new EntityPotion(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ(), this.b.cloneItemStack());
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
