package net.minecraft.server;

public class ItemSaddle extends Item
{
    public ItemSaddle(int par1)
    {
        super(par1);
        this.maxStackSize = 1;
        this.a(CreativeModeTab.e);
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public boolean a(ItemStack par1ItemStack, EntityLiving par2EntityLiving)
    {
        if (par2EntityLiving instanceof EntityPig)
        {
            EntityPig var3 = (EntityPig)par2EntityLiving;

            if (!var3.hasSaddle() && !var3.isBaby())
            {
                var3.setSaddle(true);
                --par1ItemStack.count;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean a(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        this.a(par1ItemStack, par2EntityLiving);
        return true;
    }
}
