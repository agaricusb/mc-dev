package net.minecraft.server;

public class ItemGlassBottle extends Item
{
    public ItemGlassBottle(int par1)
    {
        super(par1);
        this.a(CreativeModeTab.k);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        MovingObjectPosition var4 = this.a(par2World, par3EntityPlayer, true);

        if (var4 == null)
        {
            return par1ItemStack;
        }
        else
        {
            if (var4.type == EnumMovingObjectType.TILE)
            {
                int var5 = var4.b;
                int var6 = var4.c;
                int var7 = var4.d;

                if (!par2World.a(par3EntityPlayer, var5, var6, var7))
                {
                    return par1ItemStack;
                }

                if (!par3EntityPlayer.a(var5, var6, var7, var4.face, par1ItemStack))
                {
                    return par1ItemStack;
                }

                if (par2World.getMaterial(var5, var6, var7) == Material.WATER)
                {
                    --par1ItemStack.count;

                    if (par1ItemStack.count <= 0)
                    {
                        return new ItemStack(Item.POTION);
                    }

                    if (!par3EntityPlayer.inventory.pickup(new ItemStack(Item.POTION)))
                    {
                        par3EntityPlayer.drop(new ItemStack(Item.POTION.id, 1, 0));
                    }
                }
            }

            return par1ItemStack;
        }
    }
}
