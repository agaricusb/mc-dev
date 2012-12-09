package net.minecraft.server;

public class BlockSponge extends Block
{
    protected BlockSponge(int par1)
    {
        super(par1, Material.SPONGE);
        this.textureId = 48;
        this.a(CreativeModeTab.b);
    }
}
