package net.minecraft.server;

public class DispenseBehaviorMonsterEgg extends DispenseBehaviorItem
{
    /** Reference to the MinecraftServer object. */
    final MinecraftServer b;

    public DispenseBehaviorMonsterEgg(MinecraftServer par1)
    {
        this.b = par1;
    }

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    public ItemStack b(ISourceBlock par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing var3 = EnumFacing.a(par1IBlockSource.h());
        double var4 = par1IBlockSource.getX() + (double)var3.c();
        double var6 = (double)((float)par1IBlockSource.getBlockY() + 0.2F);
        double var8 = par1IBlockSource.getZ() + (double)var3.e();
        ItemMonsterEgg.a(par1IBlockSource.k(), par2ItemStack.getData(), var4, var6, var8);
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
}
