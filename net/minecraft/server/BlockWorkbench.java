package net.minecraft.server;

public class BlockWorkbench extends Block
{
    protected BlockWorkbench(int par1)
    {
        super(par1, Material.WOOD);
        this.textureId = 59;
        this.a(CreativeModeTab.c);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 == 1 ? this.textureId - 16 : (par1 == 0 ? Block.WOOD.a(0) : (par1 != 2 && par1 != 4 ? this.textureId : this.textureId + 1));
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isStatic)
        {
            return true;
        }
        else
        {
            par5EntityPlayer.startCrafting(par2, par3, par4);
            return true;
        }
    }
}
