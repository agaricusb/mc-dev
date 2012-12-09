package net.minecraft.server;

public class ItemSpade extends ItemTool
{
    /** an array of the blocks this spade is effective against */
    private static Block[] c = new Block[] {Block.GRASS, Block.DIRT, Block.SAND, Block.GRAVEL, Block.SNOW, Block.SNOW_BLOCK, Block.CLAY, Block.SOIL, Block.SOUL_SAND, Block.MYCEL};

    public ItemSpade(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1, 1, par2EnumToolMaterial, c);
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canDestroySpecialBlock(Block par1Block)
    {
        return par1Block == Block.SNOW ? true : par1Block == Block.SNOW_BLOCK;
    }
}
