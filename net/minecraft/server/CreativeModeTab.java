package net.minecraft.server;

public class CreativeModeTab
{
    public static final CreativeModeTab[] a = new CreativeModeTab[12];
    public static final CreativeModeTab b = new CreativeModeTab1(0, "buildingBlocks");
    public static final CreativeModeTab c = new CreativeModeTab2(1, "decorations");
    public static final CreativeModeTab d = new CreativeModeTab3(2, "redstone");
    public static final CreativeModeTab e = new CreativeModeTab4(3, "transportation");
    public static final CreativeModeTab f = new CreativeModeTab5(4, "misc");
    public static final CreativeModeTab g = (new CreativeModeTab6(5, "search")).a("search.png");
    public static final CreativeModeTab h = new CreativeModeTab7(6, "food");
    public static final CreativeModeTab i = new CreativeModeTab8(7, "tools");
    public static final CreativeModeTab j = new CreativeModeTab9(8, "combat");
    public static final CreativeModeTab k = new CreativeModeTab10(9, "brewing");
    public static final CreativeModeTab l = new CreativeModeTab11(10, "materials");
    public static final CreativeModeTab m = (new CreativeModeTab12(11, "inventory")).a("survival_inv.png").j().h();
    private final int n;
    private final String o;

    /** Texture to use. */
    private String p = "list_items.png";
    private boolean q = true;

    /** Whether to draw the title in the foreground of the creative GUI */
    private boolean r = true;

    public CreativeModeTab(int par1, String par2Str)
    {
        this.n = par1;
        this.o = par2Str;
        a[par1] = this;
    }

    public CreativeModeTab a(String par1Str)
    {
        this.p = par1Str;
        return this;
    }

    public CreativeModeTab h()
    {
        this.r = false;
        return this;
    }

    public CreativeModeTab j()
    {
        this.q = false;
        return this;
    }
}
