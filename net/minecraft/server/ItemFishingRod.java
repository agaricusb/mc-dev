package net.minecraft.server;

public class ItemFishingRod extends Item
{
    public ItemFishingRod(int par1)
    {
        super(par1);
        this.setMaxDurability(64);
        this.d(1);
        this.a(CreativeModeTab.i);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (par3EntityPlayer.hookedFish != null)
        {
            int var4 = par3EntityPlayer.hookedFish.c();
            par1ItemStack.damage(var4, par3EntityPlayer);
            par3EntityPlayer.bH();
        }
        else
        {
            par2World.makeSound(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (d.nextFloat() * 0.4F + 0.8F));

            if (!par2World.isStatic)
            {
                par2World.addEntity(new EntityFishingHook(par2World, par3EntityPlayer));
            }

            par3EntityPlayer.bH();
        }

        return par1ItemStack;
    }
}
