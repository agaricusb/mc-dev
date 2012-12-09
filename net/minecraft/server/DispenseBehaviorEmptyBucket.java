package net.minecraft.server;

public class DispenseBehaviorEmptyBucket extends DispenseBehaviorItem
{
    /** Reference to the BehaviorDefaultDispenseItem object. */
    private final DispenseBehaviorItem c;

    final MinecraftServer b;

    public DispenseBehaviorEmptyBucket(MinecraftServer par1)
    {
        this.b = par1;
        this.c = new DispenseBehaviorItem();
    }

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    public ItemStack b(ISourceBlock par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing var3 = EnumFacing.a(par1IBlockSource.h());
        World var4 = par1IBlockSource.k();
        int var5 = par1IBlockSource.getBlockX() + var3.c();
        int var6 = par1IBlockSource.getBlockY();
        int var7 = par1IBlockSource.getBlockZ() + var3.e();
        Material var8 = var4.getMaterial(var5, var6, var7);
        int var9 = var4.getData(var5, var6, var7);
        Item var10;

        if (Material.WATER.equals(var8) && var9 == 0)
        {
            var10 = Item.WATER_BUCKET;
        }
        else
        {
            if (!Material.LAVA.equals(var8) || var9 != 0)
            {
                return super.b(par1IBlockSource, par2ItemStack);
            }

            var10 = Item.LAVA_BUCKET;
        }

        var4.setTypeId(var5, var6, var7, 0);

        if (--par2ItemStack.count == 0)
        {
            par2ItemStack.id = var10.id;
            par2ItemStack.count = 1;
        }
        else if (((TileEntityDispenser)par1IBlockSource.getTileEntity()).addItem(new ItemStack(var10)) < 0)
        {
            this.c.a(par1IBlockSource, new ItemStack(var10));
        }

        return par2ItemStack;
    }
}
