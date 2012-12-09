package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class AchievementList
{
    /** Is the smallest column used to display a achievement on the GUI. */
    public static int a;

    /** Is the smallest row used to display a achievement on the GUI. */
    public static int b;

    /** Is the biggest column used to display a achievement on the GUI. */
    public static int c;

    /** Is the biggest row used to display a achievement on the GUI. */
    public static int d;

    /** The list holding all achievements */
    public static List e = new ArrayList();

    /** Is the 'open inventory' achievement. */
    public static Achievement f = (new Achievement(0, "openInventory", 0, 0, Item.BOOK, (Achievement)null)).a().c();

    /** Is the 'getting wood' achievement. */
    public static Achievement g = (new Achievement(1, "mineWood", 2, 1, Block.LOG, f)).c();

    /** Is the 'benchmarking' achievement. */
    public static Achievement h = (new Achievement(2, "buildWorkBench", 4, -1, Block.WORKBENCH, g)).c();

    /** Is the 'time to mine' achievement. */
    public static Achievement i = (new Achievement(3, "buildPickaxe", 4, 2, Item.WOOD_PICKAXE, h)).c();

    /** Is the 'hot topic' achievement. */
    public static Achievement j = (new Achievement(4, "buildFurnace", 3, 4, Block.BURNING_FURNACE, i)).c();

    /** Is the 'acquire hardware' achievement. */
    public static Achievement k = (new Achievement(5, "acquireIron", 1, 4, Item.IRON_INGOT, j)).c();

    /** Is the 'time to farm' achievement. */
    public static Achievement l = (new Achievement(6, "buildHoe", 2, -3, Item.WOOD_HOE, h)).c();

    /** Is the 'bake bread' achievement. */
    public static Achievement m = (new Achievement(7, "makeBread", -1, -3, Item.BREAD, l)).c();

    /** Is the 'the lie' achievement. */
    public static Achievement n = (new Achievement(8, "bakeCake", 0, -5, Item.CAKE, l)).c();

    /** Is the 'getting a upgrade' achievement. */
    public static Achievement o = (new Achievement(9, "buildBetterPickaxe", 6, 2, Item.STONE_PICKAXE, i)).c();

    /** Is the 'delicious fish' achievement. */
    public static Achievement p = (new Achievement(10, "cookFish", 2, 6, Item.COOKED_FISH, j)).c();

    /** Is the 'on a rail' achievement */
    public static Achievement q = (new Achievement(11, "onARail", 2, 3, Block.RAILS, k)).b().c();

    /** Is the 'time to strike' achievement. */
    public static Achievement r = (new Achievement(12, "buildSword", 6, -1, Item.WOOD_SWORD, h)).c();

    /** Is the 'monster hunter' achievement. */
    public static Achievement s = (new Achievement(13, "killEnemy", 8, -1, Item.BONE, r)).c();

    /** is the 'cow tipper' achievement. */
    public static Achievement t = (new Achievement(14, "killCow", 7, -3, Item.LEATHER, r)).c();

    /** Is the 'when pig fly' achievement. */
    public static Achievement u = (new Achievement(15, "flyPig", 8, -4, Item.SADDLE, t)).b().c();

    /** The achievement for killing a Skeleton from 50 meters aways. */
    public static Achievement v = (new Achievement(16, "snipeSkeleton", 7, 0, Item.BOW, s)).b().c();

    /** Is the 'DIAMONDS!' achievement */
    public static Achievement w = (new Achievement(17, "diamonds", -1, 5, Item.DIAMOND, k)).c();

    /** Is the 'We Need to Go Deeper' achievement */
    public static Achievement x = (new Achievement(18, "portal", -1, 7, Block.OBSIDIAN, w)).c();

    /** Is the 'Return to Sender' achievement */
    public static Achievement y = (new Achievement(19, "ghast", -4, 8, Item.GHAST_TEAR, x)).b().c();

    /** Is the 'Into Fire' achievement */
    public static Achievement z = (new Achievement(20, "blazeRod", 0, 9, Item.BLAZE_ROD, x)).c();

    /** Is the 'Local Brewery' achievement */
    public static Achievement A = (new Achievement(21, "potion", 2, 8, Item.POTION, z)).c();

    /** Is the 'The End?' achievement */
    public static Achievement B = (new Achievement(22, "theEnd", 3, 10, Item.EYE_OF_ENDER, z)).b().c();

    /** Is the 'The End.' achievement */
    public static Achievement C = (new Achievement(23, "theEnd2", 4, 13, Block.DRAGON_EGG, B)).b().c();

    /** Is the 'Enchanter' achievement */
    public static Achievement D = (new Achievement(24, "enchantments", -4, 4, Block.ENCHANTMENT_TABLE, w)).c();
    public static Achievement E = (new Achievement(25, "overkill", -4, 1, Item.DIAMOND_SWORD, D)).b().c();

    /** Is the 'Librarian' achievement */
    public static Achievement F = (new Achievement(26, "bookcase", -3, 6, Block.BOOKSHELF, D)).c();

    /**
     * A stub functions called to make the static initializer for this class run.
     */
    public static void a() {}

    static
    {
        System.out.println(e.size() + " achievements");
    }
}
