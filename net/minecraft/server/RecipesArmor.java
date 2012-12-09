package net.minecraft.server;

public class RecipesArmor
{
    private String[][] a = new String[][] {{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
    private Object[][] b;

    public RecipesArmor()
    {
        this.b = new Object[][] {{Item.LEATHER, Block.FIRE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT}, {Item.LEATHER_HELMET, Item.CHAINMAIL_HELMET, Item.IRON_HELMET, Item.DIAMOND_HELMET, Item.GOLD_HELMET}, {Item.LEATHER_CHESTPLATE, Item.CHAINMAIL_CHESTPLATE, Item.IRON_CHESTPLATE, Item.DIAMOND_CHESTPLATE, Item.GOLD_CHESTPLATE}, {Item.LEATHER_LEGGINGS, Item.CHAINMAIL_LEGGINGS, Item.IRON_LEGGINGS, Item.DIAMOND_LEGGINGS, Item.GOLD_LEGGINGS}, {Item.LEATHER_BOOTS, Item.CHAINMAIL_BOOTS, Item.IRON_BOOTS, Item.DIAMOND_BOOTS, Item.GOLD_BOOTS}};
    }

    /**
     * Adds the armor recipes to the CraftingManager.
     */
    public void a(CraftingManager par1CraftingManager)
    {
        for (int var2 = 0; var2 < this.b[0].length; ++var2)
        {
            Object var3 = this.b[0][var2];

            for (int var4 = 0; var4 < this.b.length - 1; ++var4)
            {
                Item var5 = (Item)this.b[var4 + 1][var2];
                par1CraftingManager.registerShapedRecipe(new ItemStack(var5), new Object[]{this.a[var4], 'X', var3});
            }
        }
    }
}
