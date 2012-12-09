package net.minecraft.server;

public class BlockOreBlock extends Block
{
    public BlockOreBlock(int par1, int par2)
    {
        super(par1, Material.ORE);
        this.textureId = par2;
        this.a(CreativeModeTab.b);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return this.textureId;
    }
}
