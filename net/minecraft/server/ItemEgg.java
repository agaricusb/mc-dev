package net.minecraft.server;

public class ItemEgg extends Item
{
    public ItemEgg(int par1)
    {
        super(par1);
        this.maxStackSize = 16;
        this.a(CreativeModeTab.l);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (!par3EntityPlayer.abilities.canInstantlyBuild)
        {
            --par1ItemStack.count;
        }

        par2World.makeSound(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (d.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isStatic)
        {
            par2World.addEntity(new EntityEgg(par2World, par3EntityPlayer));
        }

        return par1ItemStack;
    }
}
