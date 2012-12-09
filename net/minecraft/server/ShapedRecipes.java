package net.minecraft.server;

public class ShapedRecipes implements IRecipe
{
    /** How many horizontal slots this recipe is wide. */
    private int width;

    /** How many vertical slots this recipe uses. */
    private int height;

    /** Is a array of ItemStack that composes the recipe. */
    private ItemStack[] items;

    /** Is the ItemStack that you get when craft the recipe. */
    private ItemStack result;

    /** Is the itemID of the output item that you get when craft the recipe. */
    public final int a;

    public ShapedRecipes(int par1, int par2, ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack)
    {
        this.a = par4ItemStack.id;
        this.width = par1;
        this.height = par2;
        this.items = par3ArrayOfItemStack;
        this.result = par4ItemStack;
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
        for (int var3 = 0; var3 <= 3 - this.width; ++var3)
        {
            for (int var4 = 0; var4 <= 3 - this.height; ++var4)
            {
                if (this.a(par1InventoryCrafting, var3, var4, true))
                {
                    return true;
                }

                if (this.a(par1InventoryCrafting, var3, var4, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean a(InventoryCrafting par1InventoryCrafting, int par2, int par3, boolean par4)
    {
        for (int var5 = 0; var5 < 3; ++var5)
        {
            for (int var6 = 0; var6 < 3; ++var6)
            {
                int var7 = var5 - par2;
                int var8 = var6 - par3;
                ItemStack var9 = null;

                if (var7 >= 0 && var8 >= 0 && var7 < this.width && var8 < this.height)
                {
                    if (par4)
                    {
                        var9 = this.items[this.width - var7 - 1 + var8 * this.width];
                    }
                    else
                    {
                        var9 = this.items[var7 + var8 * this.width];
                    }
                }

                ItemStack var10 = par1InventoryCrafting.b(var5, var6);

                if (var10 != null || var9 != null)
                {
                    if (var10 == null && var9 != null || var10 != null && var9 == null)
                    {
                        return false;
                    }

                    if (var9.id != var10.id)
                    {
                        return false;
                    }

                    if (var9.getData() != -1 && var9.getData() != var10.getData())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack a(InventoryCrafting par1InventoryCrafting)
    {
        return this.b().cloneItemStack();
    }

    /**
     * Returns the size of the recipe area
     */
    public int a()
    {
        return this.width * this.height;
    }
}
