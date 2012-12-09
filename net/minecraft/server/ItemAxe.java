package net.minecraft.server;

public class ItemAxe extends ItemTool
{
    /** an array of the blocks this axe is effective against */
    private static Block[] c = new Block[] {Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST, Block.DOUBLE_STEP, Block.STEP, Block.PUMPKIN, Block.JACK_O_LANTERN};

    protected ItemAxe(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1, 3, par2EnumToolMaterial, c);
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getDestroySpeed(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block != null && (par2Block.material == Material.WOOD || par2Block.material == Material.PLANT || par2Block.material == Material.REPLACEABLE_PLANT) ? this.a : super.getDestroySpeed(par1ItemStack, par2Block);
    }
}
