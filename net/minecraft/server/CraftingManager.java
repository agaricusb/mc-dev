package net.minecraft.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CraftingManager
{
    /** The static instance of this class */
    private static final CraftingManager a = new CraftingManager();

    /** A list of all the recipes added */
    private List recipes = new ArrayList();

    /**
     * Returns the static instance of this class
     */
    public static final CraftingManager getInstance()
    {
        return a;
    }

    private CraftingManager()
    {
        (new RecipesTools()).a(this);
        (new RecipesWeapons()).a(this);
        (new RecipeIngots()).a(this);
        (new RecipesFood()).a(this);
        (new RecipesCrafting()).a(this);
        (new RecipesArmor()).a(this);
        (new RecipesDyes()).a(this);
        this.recipes.add(new RecipesArmorDye());
        this.recipes.add(new RecipesMapClone());
        this.recipes.add(new RecipesMapExtend());
        this.registerShapedRecipe(new ItemStack(Item.PAPER, 3), new Object[]{"###", '#', Item.SUGAR_CANE});
        this.registerShapelessRecipe(new ItemStack(Item.BOOK, 1), new Object[]{Item.PAPER, Item.PAPER, Item.PAPER, Item.LEATHER});
        this.registerShapelessRecipe(new ItemStack(Item.BOOK_AND_QUILL, 1), new Object[]{Item.BOOK, new ItemStack(Item.INK_SACK, 1, 0), Item.FEATHER});
        this.registerShapedRecipe(new ItemStack(Block.FENCE, 2), new Object[]{"###", "###", '#', Item.STICK});
        this.registerShapedRecipe(new ItemStack(Block.COBBLE_WALL, 6, 0), new Object[]{"###", "###", '#', Block.COBBLESTONE});
        this.registerShapedRecipe(new ItemStack(Block.COBBLE_WALL, 6, 1), new Object[]{"###", "###", '#', Block.MOSSY_COBBLESTONE});
        this.registerShapedRecipe(new ItemStack(Block.NETHER_FENCE, 6), new Object[]{"###", "###", '#', Block.NETHER_BRICK});
        this.registerShapedRecipe(new ItemStack(Block.FENCE_GATE, 1), new Object[]{"#W#", "#W#", '#', Item.STICK, 'W', Block.WOOD});
        this.registerShapedRecipe(new ItemStack(Block.JUKEBOX, 1), new Object[]{"###", "#X#", "###", '#', Block.WOOD, 'X', Item.DIAMOND});
        this.registerShapedRecipe(new ItemStack(Block.NOTE_BLOCK, 1), new Object[]{"###", "#X#", "###", '#', Block.WOOD, 'X', Item.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Block.BOOKSHELF, 1), new Object[]{"###", "XXX", "###", '#', Block.WOOD, 'X', Item.BOOK});
        this.registerShapedRecipe(new ItemStack(Block.SNOW_BLOCK, 1), new Object[]{"##", "##", '#', Item.SNOW_BALL});
        this.registerShapedRecipe(new ItemStack(Block.CLAY, 1), new Object[]{"##", "##", '#', Item.CLAY_BALL});
        this.registerShapedRecipe(new ItemStack(Block.BRICK, 1), new Object[]{"##", "##", '#', Item.CLAY_BRICK});
        this.registerShapedRecipe(new ItemStack(Block.GLOWSTONE, 1), new Object[]{"##", "##", '#', Item.GLOWSTONE_DUST});
        this.registerShapedRecipe(new ItemStack(Block.WOOL, 1), new Object[]{"##", "##", '#', Item.STRING});
        this.registerShapedRecipe(new ItemStack(Block.TNT, 1), new Object[]{"X#X", "#X#", "X#X", 'X', Item.SULPHUR, '#', Block.SAND});
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 3), new Object[]{"###", '#', Block.COBBLESTONE});
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 0), new Object[]{"###", '#', Block.STONE});
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 1), new Object[]{"###", '#', Block.SANDSTONE});
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 4), new Object[]{"###", '#', Block.BRICK});
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 5), new Object[]{"###", '#', Block.SMOOTH_BRICK});
        this.registerShapedRecipe(new ItemStack(Block.WOOD_STEP, 6, 0), new Object[]{"###", '#', new ItemStack(Block.WOOD, 1, 0)});
        this.registerShapedRecipe(new ItemStack(Block.WOOD_STEP, 6, 2), new Object[]{"###", '#', new ItemStack(Block.WOOD, 1, 2)});
        this.registerShapedRecipe(new ItemStack(Block.WOOD_STEP, 6, 1), new Object[]{"###", '#', new ItemStack(Block.WOOD, 1, 1)});
        this.registerShapedRecipe(new ItemStack(Block.WOOD_STEP, 6, 3), new Object[]{"###", '#', new ItemStack(Block.WOOD, 1, 3)});
        this.registerShapedRecipe(new ItemStack(Block.LADDER, 3), new Object[]{"# #", "###", "# #", '#', Item.STICK});
        this.registerShapedRecipe(new ItemStack(Item.WOOD_DOOR, 1), new Object[]{"##", "##", "##", '#', Block.WOOD});
        this.registerShapedRecipe(new ItemStack(Block.TRAP_DOOR, 2), new Object[]{"###", "###", '#', Block.WOOD});
        this.registerShapedRecipe(new ItemStack(Item.IRON_DOOR, 1), new Object[]{"##", "##", "##", '#', Item.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Item.SIGN, 3), new Object[]{"###", "###", " X ", '#', Block.WOOD, 'X', Item.STICK});
        this.registerShapedRecipe(new ItemStack(Item.CAKE, 1), new Object[]{"AAA", "BEB", "CCC", 'A', Item.MILK_BUCKET, 'B', Item.SUGAR, 'C', Item.WHEAT, 'E', Item.EGG});
        this.registerShapedRecipe(new ItemStack(Item.SUGAR, 1), new Object[]{"#", '#', Item.SUGAR_CANE});
        this.registerShapedRecipe(new ItemStack(Block.WOOD, 4, 0), new Object[]{"#", '#', new ItemStack(Block.LOG, 1, 0)});
        this.registerShapedRecipe(new ItemStack(Block.WOOD, 4, 1), new Object[]{"#", '#', new ItemStack(Block.LOG, 1, 1)});
        this.registerShapedRecipe(new ItemStack(Block.WOOD, 4, 2), new Object[]{"#", '#', new ItemStack(Block.LOG, 1, 2)});
        this.registerShapedRecipe(new ItemStack(Block.WOOD, 4, 3), new Object[]{"#", '#', new ItemStack(Block.LOG, 1, 3)});
        this.registerShapedRecipe(new ItemStack(Item.STICK, 4), new Object[]{"#", "#", '#', Block.WOOD});
        this.registerShapedRecipe(new ItemStack(Block.TORCH, 4), new Object[]{"X", "#", 'X', Item.COAL, '#', Item.STICK});
        this.registerShapedRecipe(new ItemStack(Block.TORCH, 4), new Object[]{"X", "#", 'X', new ItemStack(Item.COAL, 1, 1), '#', Item.STICK});
        this.registerShapedRecipe(new ItemStack(Item.BOWL, 4), new Object[]{"# #", " # ", '#', Block.WOOD});
        this.registerShapedRecipe(new ItemStack(Item.GLASS_BOTTLE, 3), new Object[]{"# #", " # ", '#', Block.GLASS});
        this.registerShapedRecipe(new ItemStack(Block.RAILS, 16), new Object[]{"X X", "X#X", "X X", 'X', Item.IRON_INGOT, '#', Item.STICK});
        this.registerShapedRecipe(new ItemStack(Block.GOLDEN_RAIL, 6), new Object[]{"X X", "X#X", "XRX", 'X', Item.GOLD_INGOT, 'R', Item.REDSTONE, '#', Item.STICK});
        this.registerShapedRecipe(new ItemStack(Block.DETECTOR_RAIL, 6), new Object[]{"X X", "X#X", "XRX", 'X', Item.IRON_INGOT, 'R', Item.REDSTONE, '#', Block.STONE_PLATE});
        this.registerShapedRecipe(new ItemStack(Item.MINECART, 1), new Object[]{"# #", "###", '#', Item.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Item.CAULDRON, 1), new Object[]{"# #", "# #", "###", '#', Item.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Item.BREWING_STAND, 1), new Object[]{" B ", "###", '#', Block.COBBLESTONE, 'B', Item.BLAZE_ROD});
        this.registerShapedRecipe(new ItemStack(Block.JACK_O_LANTERN, 1), new Object[]{"A", "B", 'A', Block.PUMPKIN, 'B', Block.TORCH});
        this.registerShapedRecipe(new ItemStack(Item.STORAGE_MINECART, 1), new Object[]{"A", "B", 'A', Block.CHEST, 'B', Item.MINECART});
        this.registerShapedRecipe(new ItemStack(Item.POWERED_MINECART, 1), new Object[]{"A", "B", 'A', Block.FURNACE, 'B', Item.MINECART});
        this.registerShapedRecipe(new ItemStack(Item.BOAT, 1), new Object[]{"# #", "###", '#', Block.WOOD});
        this.registerShapedRecipe(new ItemStack(Item.BUCKET, 1), new Object[]{"# #", " # ", '#', Item.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Item.FLOWER_POT, 1), new Object[]{"# #", " # ", '#', Item.CLAY_BRICK});
        this.registerShapedRecipe(new ItemStack(Item.FLINT_AND_STEEL, 1), new Object[]{"A ", " B", 'A', Item.IRON_INGOT, 'B', Item.FLINT});
        this.registerShapedRecipe(new ItemStack(Item.BREAD, 1), new Object[]{"###", '#', Item.WHEAT});
        this.registerShapedRecipe(new ItemStack(Block.WOOD_STAIRS, 4), new Object[]{"#  ", "## ", "###", '#', new ItemStack(Block.WOOD, 1, 0)});
        this.registerShapedRecipe(new ItemStack(Block.BIRCH_WOOD_STAIRS, 4), new Object[]{"#  ", "## ", "###", '#', new ItemStack(Block.WOOD, 1, 2)});
        this.registerShapedRecipe(new ItemStack(Block.SPRUCE_WOOD_STAIRS, 4), new Object[]{"#  ", "## ", "###", '#', new ItemStack(Block.WOOD, 1, 1)});
        this.registerShapedRecipe(new ItemStack(Block.JUNGLE_WOOD_STAIRS, 4), new Object[]{"#  ", "## ", "###", '#', new ItemStack(Block.WOOD, 1, 3)});
        this.registerShapedRecipe(new ItemStack(Item.FISHING_ROD, 1), new Object[]{"  #", " #X", "# X", '#', Item.STICK, 'X', Item.STRING});
        this.registerShapedRecipe(new ItemStack(Item.CARROT_STICK, 1), new Object[]{"# ", " X", '#', Item.FISHING_ROD, 'X', Item.CARROT});
        this.registerShapedRecipe(new ItemStack(Block.COBBLESTONE_STAIRS, 4), new Object[]{"#  ", "## ", "###", '#', Block.COBBLESTONE});
        this.registerShapedRecipe(new ItemStack(Block.BRICK_STAIRS, 4), new Object[]{"#  ", "## ", "###", '#', Block.BRICK});
        this.registerShapedRecipe(new ItemStack(Block.STONE_STAIRS, 4), new Object[]{"#  ", "## ", "###", '#', Block.SMOOTH_BRICK});
        this.registerShapedRecipe(new ItemStack(Block.NETHER_BRICK_STAIRS, 4), new Object[]{"#  ", "## ", "###", '#', Block.NETHER_BRICK});
        this.registerShapedRecipe(new ItemStack(Block.SANDSTONE_STAIRS, 4), new Object[]{"#  ", "## ", "###", '#', Block.SANDSTONE});
        this.registerShapedRecipe(new ItemStack(Item.PAINTING, 1), new Object[]{"###", "#X#", "###", '#', Item.STICK, 'X', Block.WOOL});
        this.registerShapedRecipe(new ItemStack(Item.ITEM_FRAME, 1), new Object[]{"###", "#X#", "###", '#', Item.STICK, 'X', Item.LEATHER});
        this.registerShapedRecipe(new ItemStack(Item.GOLDEN_APPLE, 1, 0), new Object[]{"###", "#X#", "###", '#', Item.GOLD_NUGGET, 'X', Item.APPLE});
        this.registerShapedRecipe(new ItemStack(Item.GOLDEN_APPLE, 1, 1), new Object[]{"###", "#X#", "###", '#', Block.GOLD_BLOCK, 'X', Item.APPLE});
        this.registerShapedRecipe(new ItemStack(Item.CARROT_GOLDEN, 1, 0), new Object[]{"###", "#X#", "###", '#', Item.GOLD_NUGGET, 'X', Item.CARROT});
        this.registerShapedRecipe(new ItemStack(Block.LEVER, 1), new Object[]{"X", "#", '#', Block.COBBLESTONE, 'X', Item.STICK});
        this.registerShapedRecipe(new ItemStack(Block.TRIPWIRE_SOURCE, 2), new Object[]{"I", "S", "#", '#', Block.WOOD, 'S', Item.STICK, 'I', Item.IRON_INGOT});
        this.registerShapedRecipe(new ItemStack(Block.REDSTONE_TORCH_ON, 1), new Object[]{"X", "#", '#', Item.STICK, 'X', Item.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Item.DIODE, 1), new Object[]{"#X#", "III", '#', Block.REDSTONE_TORCH_ON, 'X', Item.REDSTONE, 'I', Block.STONE});
        this.registerShapedRecipe(new ItemStack(Item.WATCH, 1), new Object[]{" # ", "#X#", " # ", '#', Item.GOLD_INGOT, 'X', Item.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Item.COMPASS, 1), new Object[]{" # ", "#X#", " # ", '#', Item.IRON_INGOT, 'X', Item.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Item.MAP_EMPTY, 1), new Object[]{"###", "#X#", "###", '#', Item.PAPER, 'X', Item.COMPASS});
        this.registerShapedRecipe(new ItemStack(Block.STONE_BUTTON, 1), new Object[]{"#", '#', Block.STONE});
        this.registerShapedRecipe(new ItemStack(Block.WOOD_BUTTON, 1), new Object[]{"#", '#', Block.WOOD});
        this.registerShapedRecipe(new ItemStack(Block.STONE_PLATE, 1), new Object[]{"##", '#', Block.STONE});
        this.registerShapedRecipe(new ItemStack(Block.WOOD_PLATE, 1), new Object[]{"##", '#', Block.WOOD});
        this.registerShapedRecipe(new ItemStack(Block.DISPENSER, 1), new Object[]{"###", "#X#", "#R#", '#', Block.COBBLESTONE, 'X', Item.BOW, 'R', Item.REDSTONE});
        this.registerShapedRecipe(new ItemStack(Block.PISTON, 1), new Object[]{"TTT", "#X#", "#R#", '#', Block.COBBLESTONE, 'X', Item.IRON_INGOT, 'R', Item.REDSTONE, 'T', Block.WOOD});
        this.registerShapedRecipe(new ItemStack(Block.PISTON_STICKY, 1), new Object[]{"S", "P", 'S', Item.SLIME_BALL, 'P', Block.PISTON});
        this.registerShapedRecipe(new ItemStack(Item.BED, 1), new Object[]{"###", "XXX", '#', Block.WOOL, 'X', Block.WOOD});
        this.registerShapedRecipe(new ItemStack(Block.ENCHANTMENT_TABLE, 1), new Object[]{" B ", "D#D", "###", '#', Block.OBSIDIAN, 'B', Item.BOOK, 'D', Item.DIAMOND});
        this.registerShapedRecipe(new ItemStack(Block.ANVIL, 1), new Object[]{"III", " i ", "iii", 'I', Block.IRON_BLOCK, 'i', Item.IRON_INGOT});
        this.registerShapelessRecipe(new ItemStack(Item.EYE_OF_ENDER, 1), new Object[]{Item.ENDER_PEARL, Item.BLAZE_POWDER});
        this.registerShapelessRecipe(new ItemStack(Item.FIREBALL, 3), new Object[]{Item.SULPHUR, Item.BLAZE_POWDER, Item.COAL});
        this.registerShapelessRecipe(new ItemStack(Item.FIREBALL, 3), new Object[]{Item.SULPHUR, Item.BLAZE_POWDER, new ItemStack(Item.COAL, 1, 1)});
        Collections.sort(this.recipes, new RecipeSorter(this));
        System.out.println(this.recipes.size() + " recipes");
    }

    /**
     * Adds a recipe. See spreadsheet on first page for details.
     */
    void registerShapedRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
    {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;

        if (par2ArrayOfObj[var4] instanceof String[])
        {
            String[] var7 = (String[])((String[])par2ArrayOfObj[var4++]);

            for (int var8 = 0; var8 < var7.length; ++var8)
            {
                String var9 = var7[var8];
                ++var6;
                var5 = var9.length();
                var3 = var3 + var9;
            }
        }
        else
        {
            while (par2ArrayOfObj[var4] instanceof String)
            {
                String var11 = (String)par2ArrayOfObj[var4++];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        }

        HashMap var12;

        for (var12 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2)
        {
            Character var13 = (Character)par2ArrayOfObj[var4];
            ItemStack var14 = null;

            if (par2ArrayOfObj[var4 + 1] instanceof Item)
            {
                var14 = new ItemStack((Item)par2ArrayOfObj[var4 + 1]);
            }
            else if (par2ArrayOfObj[var4 + 1] instanceof Block)
            {
                var14 = new ItemStack((Block)par2ArrayOfObj[var4 + 1], 1, -1);
            }
            else if (par2ArrayOfObj[var4 + 1] instanceof ItemStack)
            {
                var14 = (ItemStack)par2ArrayOfObj[var4 + 1];
            }

            var12.put(var13, var14);
        }

        ItemStack[] var15 = new ItemStack[var5 * var6];

        for (int var16 = 0; var16 < var5 * var6; ++var16)
        {
            char var10 = var3.charAt(var16);

            if (var12.containsKey(Character.valueOf(var10)))
            {
                var15[var16] = ((ItemStack)var12.get(Character.valueOf(var10))).cloneItemStack();
            }
            else
            {
                var15[var16] = null;
            }
        }

        this.recipes.add(new ShapedRecipes(var5, var6, var15, par1ItemStack));
    }

    void registerShapelessRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
    {
        ArrayList var3 = new ArrayList();
        Object[] var4 = par2ArrayOfObj;
        int var5 = par2ArrayOfObj.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            Object var7 = var4[var6];

            if (var7 instanceof ItemStack)
            {
                var3.add(((ItemStack)var7).cloneItemStack());
            }
            else if (var7 instanceof Item)
            {
                var3.add(new ItemStack((Item)var7));
            }
            else
            {
                if (!(var7 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                var3.add(new ItemStack((Block)var7));
            }
        }

        this.recipes.add(new ShapelessRecipes(par1ItemStack, var3));
    }

    public ItemStack craft(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        int var3 = 0;
        ItemStack var4 = null;
        ItemStack var5 = null;
        int var6;

        for (var6 = 0; var6 < par1InventoryCrafting.getSize(); ++var6)
        {
            ItemStack var7 = par1InventoryCrafting.getItem(var6);

            if (var7 != null)
            {
                if (var3 == 0)
                {
                    var4 = var7;
                }

                if (var3 == 1)
                {
                    var5 = var7;
                }

                ++var3;
            }
        }

        if (var3 == 2 && var4.id == var5.id && var4.count == 1 && var5.count == 1 && Item.byId[var4.id].n())
        {
            Item var11 = Item.byId[var4.id];
            int var13 = var11.getMaxDurability() - var4.i();
            int var8 = var11.getMaxDurability() - var5.i();
            int var9 = var13 + var8 + var11.getMaxDurability() * 5 / 100;
            int var10 = var11.getMaxDurability() - var9;

            if (var10 < 0)
            {
                var10 = 0;
            }

            return new ItemStack(var4.id, 1, var10);
        }
        else
        {
            for (var6 = 0; var6 < this.recipes.size(); ++var6)
            {
                IRecipe var12 = (IRecipe)this.recipes.get(var6);

                if (var12.a(par1InventoryCrafting, par2World))
                {
                    return var12.a(par1InventoryCrafting);
                }
            }

            return null;
        }
    }

    /**
     * returns the List<> of all recipes
     */
    public List getRecipes()
    {
        return this.recipes;
    }
}
