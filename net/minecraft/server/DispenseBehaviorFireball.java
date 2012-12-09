package net.minecraft.server;

import java.util.Random;

public class DispenseBehaviorFireball extends DispenseBehaviorItem
{
    final MinecraftServer b;

    public DispenseBehaviorFireball(MinecraftServer par1MinecraftServer)
    {
        this.b = par1MinecraftServer;
    }

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    public ItemStack b(ISourceBlock par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing var3 = EnumFacing.a(par1IBlockSource.h());
        IPosition var4 = BlockDispenser.a(par1IBlockSource);
        double var5 = var4.getX() + (double)((float)var3.c() * 0.3F);
        double var7 = var4.getY();
        double var9 = var4.getZ() + (double)((float)var3.e() * 0.3F);
        World var11 = par1IBlockSource.k();
        Random var12 = var11.random;
        double var13 = var12.nextGaussian() * 0.05D + (double)var3.c();
        double var15 = var12.nextGaussian() * 0.05D;
        double var17 = var12.nextGaussian() * 0.05D + (double)var3.e();
        var11.addEntity(new EntitySmallFireball(var11, var5, var7, var9, var13, var15, var17));
        par2ItemStack.a(1);
        return par2ItemStack;
    }

    /**
     * Play the dispense sound from the specified block.
     */
    protected void a(ISourceBlock par1IBlockSource)
    {
        par1IBlockSource.k().triggerEffect(1009, par1IBlockSource.getBlockX(), par1IBlockSource.getBlockY(), par1IBlockSource.getBlockZ(), 0);
    }
}
