package net.minecraft.server;

public class ItemBookAndQuill extends Item
{
    public ItemBookAndQuill(int par1)
    {
        super(par1);
        this.d(1);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        par3EntityPlayer.d(par1ItemStack);
        return par1ItemStack;
    }

    /**
     * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
     */
    public boolean q()
    {
        return true;
    }

    public static boolean a(NBTTagCompound par0NBTTagCompound)
    {
        if (par0NBTTagCompound == null)
        {
            return false;
        }
        else if (!par0NBTTagCompound.hasKey("pages"))
        {
            return false;
        }
        else
        {
            NBTTagList var1 = (NBTTagList)par0NBTTagCompound.get("pages");

            for (int var2 = 0; var2 < var1.size(); ++var2)
            {
                NBTTagString var3 = (NBTTagString)var1.get(var2);

                if (var3.data == null)
                {
                    return false;
                }

                if (var3.data.length() > 256)
                {
                    return false;
                }
            }

            return true;
        }
    }
}
