package net.minecraft.server;

public class RecipeMapExtend extends ShapedRecipes
{
    public RecipeMapExtend()
    {
        super(3, 3, new ItemStack[] {new ItemStack(Item.PAPER), new ItemStack(Item.PAPER), new ItemStack(Item.PAPER), new ItemStack(Item.PAPER), new ItemStack(Item.MAP, 0, -1), new ItemStack(Item.PAPER), new ItemStack(Item.PAPER), new ItemStack(Item.PAPER), new ItemStack(Item.PAPER)}, new ItemStack(Item.MAP_EMPTY, 0, 0));
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean a(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        if (!super.a(par1InventoryCrafting, par2World))
        {
            return false;
        }
        else
        {
            ItemStack var3 = null;

            for (int var4 = 0; var4 < par1InventoryCrafting.getSize() && var3 == null; ++var4)
            {
                ItemStack var5 = par1InventoryCrafting.getItem(var4);

                if (var5 != null && var5.id == Item.MAP.id)
                {
                    var3 = var5;
                }
            }

            if (var3 == null)
            {
                return false;
            }
            else
            {
                WorldMap var6 = Item.MAP.getSavedMap(var3, par2World);
                return var6 == null ? false : var6.scale < 4;
            }
        }
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack a(InventoryCrafting par1InventoryCrafting)
    {
        ItemStack var2 = null;

        for (int var3 = 0; var3 < par1InventoryCrafting.getSize() && var2 == null; ++var3)
        {
            ItemStack var4 = par1InventoryCrafting.getItem(var3);

            if (var4 != null && var4.id == Item.MAP.id)
            {
                var2 = var4;
            }
        }

        var2 = var2.cloneItemStack();
        var2.count = 1;

        if (var2.getTag() == null)
        {
            var2.setTag(new NBTTagCompound());
        }

        var2.getTag().setBoolean("map_is_scaling", true);
        return var2;
    }
}
