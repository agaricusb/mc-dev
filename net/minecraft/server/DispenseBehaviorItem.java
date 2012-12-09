package net.minecraft.server;

public class DispenseBehaviorItem implements IDispenseBehavior
{
    /**
     * Dispenses the specified ItemStack from a dispenser.
     */
    public final ItemStack a(ISourceBlock par1IBlockSource, ItemStack par2ItemStack)
    {
        ItemStack var3 = this.b(par1IBlockSource, par2ItemStack);
        this.a(par1IBlockSource);
        this.a(par1IBlockSource, EnumFacing.a(par1IBlockSource.h()));
        return var3;
    }

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    protected ItemStack b(ISourceBlock par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing var3 = EnumFacing.a(par1IBlockSource.h());
        IPosition var4 = BlockDispenser.a(par1IBlockSource);
        ItemStack var5 = par2ItemStack.a(1);
        a(par1IBlockSource.k(), var5, 6, var3, var4);
        return par2ItemStack;
    }

    public static void a(World par0World, ItemStack par1ItemStack, int par2, EnumFacing par3EnumFacing, IPosition par4IPosition)
    {
        double var5 = par4IPosition.getX();
        double var7 = par4IPosition.getY();
        double var9 = par4IPosition.getZ();
        EntityItem var11 = new EntityItem(par0World, var5, var7 - 0.3D, var9, par1ItemStack);
        double var12 = par0World.random.nextDouble() * 0.1D + 0.2D;
        var11.motX = (double)par3EnumFacing.c() * var12;
        var11.motY = 0.20000000298023224D;
        var11.motZ = (double)par3EnumFacing.e() * var12;
        var11.motX += par0World.random.nextGaussian() * 0.007499999832361937D * (double)par2;
        var11.motY += par0World.random.nextGaussian() * 0.007499999832361937D * (double)par2;
        var11.motZ += par0World.random.nextGaussian() * 0.007499999832361937D * (double)par2;
        par0World.addEntity(var11);
    }

    /**
     * Play the dispense sound from the specified block.
     */
    protected void a(ISourceBlock par1IBlockSource)
    {
        par1IBlockSource.k().triggerEffect(1000, par1IBlockSource.getBlockX(), par1IBlockSource.getBlockY(), par1IBlockSource.getBlockZ(), 0);
    }

    /**
     * Order clients to display dispense particles from the specified block and facing.
     */
    protected void a(ISourceBlock par1IBlockSource, EnumFacing par2EnumFacing)
    {
        par1IBlockSource.k().triggerEffect(2000, par1IBlockSource.getBlockX(), par1IBlockSource.getBlockY(), par1IBlockSource.getBlockZ(), this.a(par2EnumFacing));
    }

    private int a(EnumFacing par1EnumFacing)
    {
        return par1EnumFacing.c() + 1 + (par1EnumFacing.e() + 1) * 3;
    }
}
