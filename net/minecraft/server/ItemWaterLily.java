package net.minecraft.server;

public class ItemWaterLily extends ItemWithAuxData
{
    public ItemWaterLily(int par1)
    {
        super(par1, false);
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

                if (par2World.getMaterial(var5, var6, var7) == Material.WATER && par2World.getData(var5, var6, var7) == 0 && par2World.isEmpty(var5, var6 + 1, var7))
                {
                    par2World.setTypeId(var5, var6 + 1, var7, Block.WATER_LILY.id);

                    if (!par3EntityPlayer.abilities.canInstantlyBuild)
                    {
                        --par1ItemStack.count;
                    }
                }
            }

            return par1ItemStack;
        }
    }
}
