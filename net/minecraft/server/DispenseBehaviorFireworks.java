package net.minecraft.server;


public class DispenseBehaviorFireworks extends DispenseBehaviorItem
{
    final MinecraftServer b;

    public DispenseBehaviorFireworks(MinecraftServer par1MinecraftServer)
    {
        this.b = par1MinecraftServer;
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
        EntityFireworks var10 = new EntityFireworks(par1IBlockSource.k(), var4, var6, var8, par2ItemStack);
        par1IBlockSource.k().addEntity(var10);
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
