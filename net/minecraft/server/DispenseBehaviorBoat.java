package net.minecraft.server;


public class DispenseBehaviorBoat extends DispenseBehaviorItem
{
    /** Reference to the BehaviorDefaultDispenseItem object. */
    private final DispenseBehaviorItem c;

    /** Reference to the MinecraftServer object. */
    final MinecraftServer b;

    public DispenseBehaviorBoat(MinecraftServer par1MinecraftServer)
    {
        this.b = par1MinecraftServer;
        this.c = new DispenseBehaviorItem();
    }

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    public ItemStack b(ISourceBlock par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing var3 = EnumFacing.a(par1IBlockSource.h());
        World var4 = par1IBlockSource.k();
        double var5 = par1IBlockSource.getX() + (double)((float)var3.c() * 1.125F);
        double var7 = par1IBlockSource.getY();
        double var9 = par1IBlockSource.getZ() + (double)((float)var3.e() * 1.125F);
        int var11 = par1IBlockSource.getBlockX() + var3.c();
        int var12 = par1IBlockSource.getBlockY();
        int var13 = par1IBlockSource.getBlockZ() + var3.e();
        Material var14 = var4.getMaterial(var11, var12, var13);
        double var15;

        if (Material.WATER.equals(var14))
        {
            var15 = 1.0D;
        }
        else
        {
            if (!Material.AIR.equals(var14) || !Material.WATER.equals(var4.getMaterial(var11, var12 - 1, var13)))
            {
                return this.c.a(par1IBlockSource, par2ItemStack);
            }

            var15 = 0.0D;
        }

        EntityBoat var17 = new EntityBoat(var4, var5, var7 + var15, var9);
        var4.addEntity(var17);
        par2ItemStack.a(1);
        return par2ItemStack;
    }

    /**
     * Play the dispense sound from the specified block.
     */
    protected void a(ISourceBlock par1IBlockSource)
    {
        par1IBlockSource.k().triggerEffect(1000, par1IBlockSource.getBlockX(), par1IBlockSource.getBlockY(), par1IBlockSource.getBlockZ(), 0);
    }
}
