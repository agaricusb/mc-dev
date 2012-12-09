package net.minecraft.server;

public class BlockSmoothBrick extends Block
{
    public static final String[] a = new String[] {"default", "mossy", "cracked", "chiseled"};

    public BlockSmoothBrick(int par1)
    {
        super(par1, 54, Material.STONE);
        this.a(CreativeModeTab.b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        switch (par2)
        {
            case 1:
                return 100;

            case 2:
                return 101;

            case 3:
                return 213;

            default:
                return 54;
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int par1)
    {
        return par1;
    }
}
