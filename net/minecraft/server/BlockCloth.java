package net.minecraft.server;

public class BlockCloth extends Block
{
    public BlockCloth()
    {
        super(35, 64, Material.CLOTH);
        this.a(CreativeModeTab.b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        if (par2 == 0)
        {
            return this.textureId;
        }
        else
        {
            par2 = ~(par2 & 15);
            return 113 + ((par2 & 8) >> 3) + (par2 & 7) * 16;
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int par1)
    {
        return par1;
    }

    /**
     * Takes a dye damage value and returns the block damage value to match
     */
    public static int e_(int par0)
    {
        return ~par0 & 15;
    }

    /**
     * Takes a block damage value and returns the dye damage value to match
     */
    public static int d(int par0)
    {
        return ~par0 & 15;
    }
}
