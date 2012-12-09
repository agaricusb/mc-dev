package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;

public class RecipesFurnace
{
    private static final RecipesFurnace a = new RecipesFurnace();

    /** The list of smelting results. */
    private Map recipes = new HashMap();
    private Map c = new HashMap();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final RecipesFurnace getInstance()
    {
        return a;
    }

    private RecipesFurnace()
    {
        this.registerRecipe(Block.IRON_ORE.id, new ItemStack(Item.IRON_INGOT), 0.7F);
        this.registerRecipe(Block.GOLD_ORE.id, new ItemStack(Item.GOLD_INGOT), 1.0F);
        this.registerRecipe(Block.DIAMOND_ORE.id, new ItemStack(Item.DIAMOND), 1.0F);
        this.registerRecipe(Block.SAND.id, new ItemStack(Block.GLASS), 0.1F);
        this.registerRecipe(Item.PORK.id, new ItemStack(Item.GRILLED_PORK), 0.35F);
        this.registerRecipe(Item.RAW_BEEF.id, new ItemStack(Item.COOKED_BEEF), 0.35F);
        this.registerRecipe(Item.RAW_CHICKEN.id, new ItemStack(Item.COOKED_CHICKEN), 0.35F);
        this.registerRecipe(Item.RAW_FISH.id, new ItemStack(Item.COOKED_FISH), 0.35F);
        this.registerRecipe(Block.COBBLESTONE.id, new ItemStack(Block.STONE), 0.1F);
        this.registerRecipe(Item.CLAY_BALL.id, new ItemStack(Item.CLAY_BRICK), 0.3F);
        this.registerRecipe(Block.CACTUS.id, new ItemStack(Item.INK_SACK, 1, 2), 0.2F);
        this.registerRecipe(Block.LOG.id, new ItemStack(Item.COAL, 1, 1), 0.15F);
        this.registerRecipe(Block.EMERALD_ORE.id, new ItemStack(Item.EMERALD), 1.0F);
        this.registerRecipe(Item.POTATO.id, new ItemStack(Item.POTATO_BAKED), 0.35F);
        this.registerRecipe(Block.COAL_ORE.id, new ItemStack(Item.COAL), 0.1F);
        this.registerRecipe(Block.REDSTONE_ORE.id, new ItemStack(Item.REDSTONE), 0.7F);
        this.registerRecipe(Block.LAPIS_ORE.id, new ItemStack(Item.INK_SACK, 1, 4), 0.2F);
    }

    /**
     * Adds a smelting recipe.
     */
    public void registerRecipe(int par1, ItemStack par2ItemStack, float par3)
    {
        this.recipes.put(Integer.valueOf(par1), par2ItemStack);
        this.c.put(Integer.valueOf(par2ItemStack.id), Float.valueOf(par3));
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getResult(int par1)
    {
        return (ItemStack)this.recipes.get(Integer.valueOf(par1));
    }

    public Map getRecipes()
    {
        return this.recipes;
    }

    public float c(int par1)
    {
        return this.c.containsKey(Integer.valueOf(par1)) ? ((Float)this.c.get(Integer.valueOf(par1))).floatValue() : 0.0F;
    }
}
