package net.minecraft.server;

public class RecipeIngots
{
    private Object[][] a;

    public RecipeIngots()
    {
        this.a = new Object[][] {{Block.GOLD_BLOCK, new ItemStack(Item.GOLD_INGOT, 9)}, {Block.IRON_BLOCK, new ItemStack(Item.IRON_INGOT, 9)}, {Block.DIAMOND_BLOCK, new ItemStack(Item.DIAMOND, 9)}, {Block.EMERALD_BLOCK, new ItemStack(Item.EMERALD, 9)}, {Block.LAPIS_BLOCK, new ItemStack(Item.INK_SACK, 9, 4)}};
    }

    /**
     * Adds the ingot recipes to the CraftingManager.
     */
    public void a(CraftingManager par1CraftingManager)
    {
        for (int var2 = 0; var2 < this.a.length; ++var2)
        {
            Block var3 = (Block)this.a[var2][0];
            ItemStack var4 = (ItemStack)this.a[var2][1];
            par1CraftingManager.registerShapedRecipe(new ItemStack(var3), new Object[]{"###", "###", "###", '#', var4});
            par1CraftingManager.registerShapedRecipe(var4, new Object[]{"#", '#', var3});
        }

        par1CraftingManager.registerShapedRecipe(new ItemStack(Item.GOLD_INGOT), new Object[]{"###", "###", "###", '#', Item.GOLD_NUGGET});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Item.GOLD_NUGGET, 9), new Object[]{"#", '#', Item.GOLD_INGOT});
    }
}
