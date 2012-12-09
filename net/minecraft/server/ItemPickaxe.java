package net.minecraft.server;

public class ItemPickaxe extends ItemTool
{
    /** an array of the blocks this pickaxe is effective against */
    private static Block[] c = new Block[] {Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK, Block.REDSTONE_ORE, Block.GLOWING_REDSTONE_ORE, Block.RAILS, Block.DETECTOR_RAIL, Block.GOLDEN_RAIL};

    protected ItemPickaxe(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1, 2, par2EnumToolMaterial, c);
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canDestroySpecialBlock(Block par1Block)
    {
        return par1Block == Block.OBSIDIAN ? this.b.d() == 3 : (par1Block != Block.DIAMOND_BLOCK && par1Block != Block.DIAMOND_ORE ? (par1Block != Block.EMERALD_ORE && par1Block != Block.EMERALD_BLOCK ? (par1Block != Block.GOLD_BLOCK && par1Block != Block.GOLD_ORE ? (par1Block != Block.IRON_BLOCK && par1Block != Block.IRON_ORE ? (par1Block != Block.LAPIS_BLOCK && par1Block != Block.LAPIS_ORE ? (par1Block != Block.REDSTONE_ORE && par1Block != Block.GLOWING_REDSTONE_ORE ? (par1Block.material == Material.STONE ? true : (par1Block.material == Material.ORE ? true : par1Block.material == Material.HEAVY)) : this.b.d() >= 2) : this.b.d() >= 1) : this.b.d() >= 1) : this.b.d() >= 2) : this.b.d() >= 2) : this.b.d() >= 2);
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getDestroySpeed(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block != null && (par2Block.material == Material.ORE || par2Block.material == Material.HEAVY || par2Block.material == Material.STONE) ? this.a : super.getDestroySpeed(par1ItemStack, par2Block);
    }
}
