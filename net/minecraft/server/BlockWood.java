package net.minecraft.server;

public class BlockWood extends Block
{
    /** The type of tree this block came from. */
    public static final String[] a = new String[] {"oak", "spruce", "birch", "jungle"};

    public BlockWood(int par1)
    {
        super(par1, 4, Material.WOOD);
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
                return 198;

            case 2:
                return 214;

            case 3:
                return 199;

            default:
                return 4;
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
