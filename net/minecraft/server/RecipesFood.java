package net.minecraft.server;

public class RecipesFood
{
    /**
     * Adds the food recipes to the CraftingManager.
     */
    public void a(CraftingManager par1CraftingManager)
    {
        par1CraftingManager.registerShapelessRecipe(new ItemStack(Item.MUSHROOM_SOUP), new Object[]{Block.BROWN_MUSHROOM, Block.RED_MUSHROOM, Item.BOWL});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Item.COOKIE, 8), new Object[]{"#X#", 'X', new ItemStack(Item.INK_SACK, 1, 3), '#', Item.WHEAT});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.MELON), new Object[]{"MMM", "MMM", "MMM", 'M', Item.MELON});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Item.MELON_SEEDS), new Object[]{"M", 'M', Item.MELON});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Item.PUMPKIN_SEEDS, 4), new Object[]{"M", 'M', Block.PUMPKIN});
        par1CraftingManager.registerShapelessRecipe(new ItemStack(Item.PUMPKIN_PIE), new Object[]{Block.PUMPKIN, Item.SUGAR, Item.EGG});
        par1CraftingManager.registerShapelessRecipe(new ItemStack(Item.FERMENTED_SPIDER_EYE), new Object[]{Item.SPIDER_EYE, Block.BROWN_MUSHROOM, Item.SUGAR});
        par1CraftingManager.registerShapelessRecipe(new ItemStack(Item.SPECKLED_MELON), new Object[]{Item.MELON, Item.GOLD_NUGGET});
        par1CraftingManager.registerShapelessRecipe(new ItemStack(Item.BLAZE_POWDER, 2), new Object[]{Item.BLAZE_ROD});
        par1CraftingManager.registerShapelessRecipe(new ItemStack(Item.MAGMA_CREAM), new Object[]{Item.BLAZE_POWDER, Item.SLIME_BALL});
    }
}
