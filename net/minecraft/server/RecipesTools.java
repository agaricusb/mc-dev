package net.minecraft.server;

public class RecipesTools
{
    private String[][] a = new String[][] {{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}};
    private Object[][] b;

    public RecipesTools()
    {
        this.b = new Object[][] {{Block.WOOD, Block.COBBLESTONE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT}, {Item.WOOD_PICKAXE, Item.STONE_PICKAXE, Item.IRON_PICKAXE, Item.DIAMOND_PICKAXE, Item.GOLD_PICKAXE}, {Item.WOOD_SPADE, Item.STONE_SPADE, Item.IRON_SPADE, Item.DIAMOND_SPADE, Item.GOLD_SPADE}, {Item.WOOD_AXE, Item.STONE_AXE, Item.IRON_AXE, Item.DIAMOND_AXE, Item.GOLD_AXE}, {Item.WOOD_HOE, Item.STONE_HOE, Item.IRON_HOE, Item.DIAMOND_HOE, Item.GOLD_HOE}};
    }

    /**
     * Adds the tool recipes to the CraftingManager.
     */
    public void a(CraftingManager par1CraftingManager)
    {
        for (int var2 = 0; var2 < this.b[0].length; ++var2)
        {
            Object var3 = this.b[0][var2];

            for (int var4 = 0; var4 < this.b.length - 1; ++var4)
            {
                Item var5 = (Item)this.b[var4 + 1][var2];
                par1CraftingManager.registerShapedRecipe(new ItemStack(var5), new Object[]{this.a[var4], '#', Item.STICK, 'X', var3});
            }
        }

        par1CraftingManager.registerShapedRecipe(new ItemStack(Item.SHEARS), new Object[]{" #", "# ", '#', Item.IRON_INGOT});
    }
}
