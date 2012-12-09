package net.minecraft.server;

public class ItemMapEmpty extends ItemWorldMapBase
{
    protected ItemMapEmpty(int par1)
    {
        super(par1);
        this.a(CreativeModeTab.f);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        ItemStack var4 = new ItemStack(Item.MAP, 1, par2World.b("map"));
        String var5 = "map_" + var4.getData();
        WorldMap var6 = new WorldMap(var5);
        par2World.a(var5, var6);
        var6.scale = 0;
        int var7 = 128 * (1 << var6.scale);
        var6.centerX = (int)(Math.round(par3EntityPlayer.locX / (double)var7) * (long)var7);
        var6.centerZ = (int)(Math.round(par3EntityPlayer.locZ / (double)var7) * (long)var7);
        var6.map = (byte)par2World.worldProvider.dimension;
        var6.c();
        --par1ItemStack.count;

        if (par1ItemStack.count <= 0)
        {
            return var4;
        }
        else
        {
            if (!par3EntityPlayer.inventory.pickup(var4.cloneItemStack()))
            {
                par3EntityPlayer.drop(var4);
            }

            return par1ItemStack;
        }
    }
}
