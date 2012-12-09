package net.minecraft.server;

public interface IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean a(InventoryCrafting var1, World var2);

    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack a(InventoryCrafting var1);

    /**
     * Returns the size of the recipe area
     */
    int a();

    ItemStack b();
}
