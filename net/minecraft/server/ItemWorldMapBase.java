package net.minecraft.server;

public class ItemWorldMapBase extends Item
{
    protected ItemWorldMapBase(int par1)
    {
        super(par1);
    }

    /**
     * false for all Items except sub-classes of ItemMapBase
     */
    public boolean f()
    {
        return true;
    }

    public Packet c(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        return null;
    }
}
