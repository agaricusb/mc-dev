package net.minecraft.server;

public abstract class DispenseBehaviorProjectile extends DispenseBehaviorItem
{
    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    public ItemStack b(ISourceBlock par1IBlockSource, ItemStack par2ItemStack)
    {
        World var3 = par1IBlockSource.k();
        IPosition var4 = BlockDispenser.a(par1IBlockSource);
        EnumFacing var5 = EnumFacing.a(par1IBlockSource.h());
        IProjectile var6 = this.a(var3, var4);
        var6.shoot((double) var5.c(), 0.10000000149011612D, (double) var5.e(), this.b(), this.a());
        var3.addEntity((Entity) var6);
        par2ItemStack.a(1);
        return par2ItemStack;
    }

    /**
     * Play the dispense sound from the specified block.
     */
    protected void a(ISourceBlock par1IBlockSource)
    {
        par1IBlockSource.k().triggerEffect(1002, par1IBlockSource.getBlockX(), par1IBlockSource.getBlockY(), par1IBlockSource.getBlockZ(), 0);
    }

    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected abstract IProjectile a(World var1, IPosition var2);

    protected float a()
    {
        return 6.0F;
    }

    protected float b()
    {
        return 1.1F;
    }
}
