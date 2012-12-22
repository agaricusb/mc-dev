package net.minecraft.server;

public class RecipeMapClone implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean a(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        int var3 = 0;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < par1InventoryCrafting.getSize(); ++var5)
        {
            ItemStack var6 = par1InventoryCrafting.getItem(var5);

            if (var6 != null)
            {
                if (var6.id == Item.MAP.id)
                {
                    if (var4 != null)
                    {
                        return false;
                    }

                    var4 = var6;
                }
                else
                {
                    if (var6.id != Item.MAP_EMPTY.id)
                    {
                        return false;
                    }

                    ++var3;
                }
            }
        }

        return var4 != null && var3 > 0;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack a(InventoryCrafting par1InventoryCrafting)
    {
        int var2 = 0;
        ItemStack var3 = null;

        for (int var4 = 0; var4 < par1InventoryCrafting.getSize(); ++var4)
        {
            ItemStack var5 = par1InventoryCrafting.getItem(var4);

            if (var5 != null)
            {
                if (var5.id == Item.MAP.id)
                {
                    if (var3 != null)
                    {
                        return null;
                    }

                    var3 = var5;
                }
                else
                {
                    if (var5.id != Item.MAP_EMPTY.id)
                    {
                        return null;
                    }

                    ++var2;
                }
            }
        }

        if (var3 != null && var2 >= 1)
        {
            ItemStack var6 = new ItemStack(Item.MAP, var2 + 1, var3.getData());

            if (var3.s())
            {
                var6.c(var3.r());
            }

            return var6;
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int a()
    {
        return 9;
    }

    public ItemStack b()
    {
        return null;
    }
}
