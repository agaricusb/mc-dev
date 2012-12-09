package net.minecraft.server;

import java.util.Comparator;

class RecipeSorter implements Comparator
{
    final CraftingManager a;

    RecipeSorter(CraftingManager par1CraftingManager)
    {
        this.a = par1CraftingManager;
    }

    public int a(IRecipe par1IRecipe, IRecipe par2IRecipe)
    {
        return par1IRecipe instanceof ShapelessRecipes && par2IRecipe instanceof ShapedRecipes ? 1 : (par2IRecipe instanceof ShapelessRecipes && par1IRecipe instanceof ShapedRecipes ? -1 : (par2IRecipe.a() < par1IRecipe.a() ? -1 : (par2IRecipe.a() > par1IRecipe.a() ? 1 : 0)));
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return this.a((IRecipe) par1Obj, (IRecipe) par2Obj);
    }
}
