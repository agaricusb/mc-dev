package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapelessRecipes implements IRecipe
{
    /** Is the ItemStack that you get when craft the recipe. */
    private final ItemStack result;

    /** Is a List of ItemStack that composes the recipe. */
    private final List ingredients;

    public ShapelessRecipes(ItemStack par1ItemStack, List par2List)
    {
        this.result = par1ItemStack;
        this.ingredients = par2List;
    }

    public ItemStack b()
    {
        return this.result;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean a(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        ArrayList var3 = new ArrayList(this.ingredients);

        for (int var4 = 0; var4 < 3; ++var4)
        {
            for (int var5 = 0; var5 < 3; ++var5)
            {
                ItemStack var6 = par1InventoryCrafting.b(var5, var4);

                if (var6 != null)
                {
                    boolean var7 = false;
                    Iterator var8 = var3.iterator();

                    while (var8.hasNext())
                    {
                        ItemStack var9 = (ItemStack)var8.next();

                        if (var6.id == var9.id && (var9.getData() == -1 || var6.getData() == var9.getData()))
                        {
                            var7 = true;
                            var3.remove(var9);
                            break;
                        }
                    }

                    if (!var7)
                    {
                        return false;
                    }
                }
            }
        }

        return var3.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack a(InventoryCrafting par1InventoryCrafting)
    {
        return this.result.cloneItemStack();
    }

    /**
     * Returns the size of the recipe area
     */
    public int a()
    {
        return this.ingredients.size();
    }
}
