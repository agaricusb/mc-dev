package net.minecraft.server;

public class ItemCoal extends Item
{
    public ItemCoal(int par1)
    {
        super(par1);
        this.a(true);
        this.setMaxDurability(0);
        this.a(CreativeModeTab.l);
    }

    public String c_(ItemStack par1ItemStack)
    {
        return par1ItemStack.getData() == 1 ? "item.charcoal" : "item.coal";
    }
}
