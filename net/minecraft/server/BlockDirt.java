package net.minecraft.server;

public class BlockDirt extends Block
{
    protected BlockDirt(int par1, int par2)
    {
        super(par1, par2, Material.EARTH);
        this.a(CreativeModeTab.b);
    }
}
