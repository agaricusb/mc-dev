package net.minecraft.server;

public class ItemGoldenApple extends ItemFood
{
    public ItemGoldenApple(int par1, int par2, float par3, boolean par4)
    {
        super(par1, par2, par3, par4);
        this.a(true);
    }

    protected void c(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (par1ItemStack.getData() > 0)
        {
            if (!par2World.isStatic)
            {
                par3EntityPlayer.addEffect(new MobEffect(MobEffectList.REGENERATION.id, 600, 3));
                par3EntityPlayer.addEffect(new MobEffect(MobEffectList.RESISTANCE.id, 6000, 0));
                par3EntityPlayer.addEffect(new MobEffect(MobEffectList.FIRE_RESISTANCE.id, 6000, 0));
            }
        }
        else
        {
            super.c(par1ItemStack, par2World, par3EntityPlayer);
        }
    }
}
