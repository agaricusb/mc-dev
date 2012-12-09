package net.minecraft.server;

public class DispenseBehaviorFilledBucket extends DispenseBehaviorItem
{
    /** Reference to the BehaviorDefaultDispenseItem object. */
    private final DispenseBehaviorItem c;

    /** Reference to the MinecraftServer object. */
    final MinecraftServer b;

    public DispenseBehaviorFilledBucket(MinecraftServer par1)
    {
        this.b = par1;
        this.c = new DispenseBehaviorItem();
    }

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    public ItemStack b(ISourceBlock par1IBlockSource, ItemStack par2ItemStack)
    {
        ItemBucket var3 = (ItemBucket)par2ItemStack.getItem();
        int var4 = par1IBlockSource.getBlockX();
        int var5 = par1IBlockSource.getBlockY();
        int var6 = par1IBlockSource.getBlockZ();
        EnumFacing var7 = EnumFacing.a(par1IBlockSource.h());

        if (var3.a(par1IBlockSource.k(), (double) var4, (double) var5, (double) var6, var4 + var7.c(), var5, var6 + var7.e()))
        {
            par2ItemStack.id = Item.BUCKET.id;
            par2ItemStack.count = 1;
            return par2ItemStack;
        }
        else
        {
            return this.c.a(par1IBlockSource, par2ItemStack);
        }
    }
}
