package net.minecraft.server;

public class ItemMilkBucket extends Item
{
    public ItemMilkBucket(int par1)
    {
        super(par1);
        this.d(1);
        this.a(CreativeModeTab.f);
    }

    public ItemStack b(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (!par3EntityPlayer.abilities.canInstantlyBuild)
        {
            --par1ItemStack.count;
        }

        if (!par2World.isStatic)
        {
            par3EntityPlayer.by();
        }

        return par1ItemStack.count <= 0 ? new ItemStack(Item.BUCKET) : par1ItemStack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int a(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAnimation d_(ItemStack par1ItemStack)
    {
        return EnumAnimation.c;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        par3EntityPlayer.a(par1ItemStack, this.a(par1ItemStack));
        return par1ItemStack;
    }
}
