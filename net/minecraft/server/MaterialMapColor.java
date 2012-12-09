package net.minecraft.server;

public class MaterialMapColor
{
    /**
     * Holds all the 16 colors used on maps, very similar of a pallete system.
     */
    public static final MaterialMapColor[] a = new MaterialMapColor[16];

    /** The map color for Air blocks */
    public static final MaterialMapColor b = new MaterialMapColor(0, 0);

    /** this is the grass color in html format */
    public static final MaterialMapColor c = new MaterialMapColor(1, 8368696);

    /** This is the color of the sand */
    public static final MaterialMapColor d = new MaterialMapColor(2, 16247203);

    /** The map color for Cloth and Sponge blocks */
    public static final MaterialMapColor e = new MaterialMapColor(3, 10987431);

    /** The map color for TNT blocks */
    public static final MaterialMapColor f = new MaterialMapColor(4, 16711680);

    /** The map color for Ice blocks */
    public static final MaterialMapColor g = new MaterialMapColor(5, 10526975);

    /** The map color for Iron blocks */
    public static final MaterialMapColor h = new MaterialMapColor(6, 10987431);

    /** The map color for Leaf, Plant, Cactus, and Pumpkin blocks. */
    public static final MaterialMapColor i = new MaterialMapColor(7, 31744);

    /** The map color for Snow Cover and Snow blocks */
    public static final MaterialMapColor j = new MaterialMapColor(8, 16777215);

    /** The map color for Clay blocks */
    public static final MaterialMapColor k = new MaterialMapColor(9, 10791096);

    /** The map color for Dirt blocks */
    public static final MaterialMapColor l = new MaterialMapColor(10, 12020271);

    /** The map color for Stone blocks */
    public static final MaterialMapColor m = new MaterialMapColor(11, 7368816);

    /** The map color for Water blocks */
    public static final MaterialMapColor n = new MaterialMapColor(12, 4210943);

    /** The map color for Wood blocks */
    public static final MaterialMapColor o = new MaterialMapColor(13, 6837042);

    /** Holds the color in RGB value that will be rendered on maps. */
    public final int p;

    /** Holds the index of the color used on map. */
    public final int q;

    private MaterialMapColor(int par1, int par2)
    {
        this.q = par1;
        this.p = par2;
        a[par1] = this;
    }
}
