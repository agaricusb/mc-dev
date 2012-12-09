package net.minecraft.server;

public class RecipesWeapons
{
    private String[][] a = new String[][] {{"X", "X", "#"}};
    private Object[][] b;

    public RecipesWeapons()
    {
        this.b = new Object[][] {{Block.WOOD, Block.COBBLESTONE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT}, {Item.WOOD_SWORD, Item.STONE_SWORD, Item.IRON_SWORD, Item.DIAMOND_SWORD, Item.GOLD_SWORD}};
    }

    /**
     * Adds the weapon recipes to the CraftingManager.
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

        par1CraftingManager.registerShapedRecipe(new ItemStack(Item.BOW, 1), new Object[]{" #X", "# X", " #X", 'X', Item.STRING, '#', Item.STICK});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Item.ARROW, 4), new Object[]{"X", "#", "Y", 'Y', Item.FEATHER, 'X', Item.FLINT, '#', Item.STICK});
    }
}
