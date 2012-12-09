package net.minecraft.server;

public class RecipesCrafting
{
    /**
     * Adds the crafting recipes to the CraftingManager.
     */
    public void a(CraftingManager par1CraftingManager)
    {
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.CHEST), new Object[]{"###", "# #", "###", '#', Block.WOOD});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.ENDER_CHEST), new Object[]{"###", "#E#", "###", '#', Block.OBSIDIAN, 'E', Item.EYE_OF_ENDER});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.FURNACE), new Object[]{"###", "# #", "###", '#', Block.COBBLESTONE});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.WORKBENCH), new Object[]{"##", "##", '#', Block.WOOD});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.SANDSTONE), new Object[]{"##", "##", '#', Block.SAND});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.SANDSTONE, 4, 2), new Object[]{"##", "##", '#', Block.SANDSTONE});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.SANDSTONE, 1, 1), new Object[]{"#", "#", '#', new ItemStack(Block.STEP, 1, 1)});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.SMOOTH_BRICK, 4), new Object[]{"##", "##", '#', Block.STONE});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.IRON_FENCE, 16), new Object[]{"###", "###", '#', Item.IRON_INGOT});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.THIN_GLASS, 16), new Object[]{"###", "###", '#', Block.GLASS});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.REDSTONE_LAMP_OFF, 1), new Object[]{" R ", "RGR", " R ", 'R', Item.REDSTONE, 'G', Block.GLOWSTONE});
        par1CraftingManager.registerShapedRecipe(new ItemStack(Block.BEACON, 1), new Object[]{"GGG", "GSG", "OOO", 'G', Block.GLASS, 'S', Item.NETHER_STAR, 'O', Block.OBSIDIAN});
    }
}
