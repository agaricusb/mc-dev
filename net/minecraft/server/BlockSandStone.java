package net.minecraft.server;

public class BlockSandStone extends Block
{
    public static final String[] a = new String[] {"default", "chiseled", "smooth"};

    public BlockSandStone(int par1)
    {
        super(par1, 192, Material.STONE);
        this.a(CreativeModeTab.b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par1 != 1 && (par1 != 0 || par2 != 1 && par2 != 2) ? (par1 == 0 ? 208 : (par2 == 1 ? 229 : (par2 == 2 ? 230 : 192))) : 176;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 == 1 ? this.textureId - 16 : (par1 == 0 ? this.textureId + 16 : this.textureId);
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int par1)
    {
        return par1;
    }
}
