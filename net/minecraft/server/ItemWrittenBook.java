package net.minecraft.server;

public class ItemWrittenBook extends Item
{
    public ItemWrittenBook(int par1)
    {
        super(par1);
        this.d(1);
    }

    public static boolean a(NBTTagCompound par0NBTTagCompound)
    {
        if (!ItemBookAndQuill.a(par0NBTTagCompound))
        {
            return false;
        }
        else if (!par0NBTTagCompound.hasKey("title"))
        {
            return false;
        }
        else
        {
            String var1 = par0NBTTagCompound.getString("title");
            return var1 != null && var1.length() <= 16 ? par0NBTTagCompound.hasKey("author") : false;
        }
    }

    public String j(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTag())
        {
            NBTTagCompound var2 = par1ItemStack.getTag();
            NBTTagString var3 = (NBTTagString)var2.get("title");

            if (var3 != null)
            {
                return var3.toString();
            }
        }

        return super.j(par1ItemStack);
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
}
