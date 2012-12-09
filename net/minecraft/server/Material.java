package net.minecraft.server;

public class Material
{
    public static final Material AIR = new MaterialGas(MaterialMapColor.b);

    /** The material used by BlockGrass. */
    public static final Material GRASS = new Material(MaterialMapColor.c);
    public static final Material EARTH = new Material(MaterialMapColor.l);
    public static final Material WOOD = (new Material(MaterialMapColor.o)).g();
    public static final Material STONE = (new Material(MaterialMapColor.m)).f();
    public static final Material ORE = (new Material(MaterialMapColor.h)).f();
    public static final Material HEAVY = (new Material(MaterialMapColor.h)).f().o();
    public static final Material WATER = (new MaterialLiquid(MaterialMapColor.n)).n();
    public static final Material LAVA = (new MaterialLiquid(MaterialMapColor.f)).n();
    public static final Material LEAVES = (new Material(MaterialMapColor.i)).g().r().n();
    public static final Material PLANT = (new MaterialDecoration(MaterialMapColor.i)).n();
    public static final Material REPLACEABLE_PLANT = (new MaterialDecoration(MaterialMapColor.i)).g().n().i();
    public static final Material SPONGE = new Material(MaterialMapColor.e);
    public static final Material CLOTH = (new Material(MaterialMapColor.e)).g();
    public static final Material FIRE = (new MaterialGas(MaterialMapColor.b)).n();
    public static final Material SAND = new Material(MaterialMapColor.d);
    public static final Material ORIENTABLE = (new MaterialDecoration(MaterialMapColor.b)).n();
    public static final Material SHATTERABLE = (new Material(MaterialMapColor.b)).r().p();
    public static final Material BUILDABLE_GLASS = (new Material(MaterialMapColor.b)).p();
    public static final Material TNT = (new Material(MaterialMapColor.f)).g().r();
    public static final Material CORAL = (new Material(MaterialMapColor.i)).n();
    public static final Material ICE = (new Material(MaterialMapColor.g)).r().p();
    public static final Material SNOW_LAYER = (new MaterialDecoration(MaterialMapColor.j)).i().r().f().n();

    /** The material for crafted snow. */
    public static final Material SNOW_BLOCK = (new Material(MaterialMapColor.j)).f();
    public static final Material CACTUS = (new Material(MaterialMapColor.i)).r().n();
    public static final Material CLAY = new Material(MaterialMapColor.k);

    /** pumpkin */
    public static final Material PUMPKIN = (new Material(MaterialMapColor.i)).n();
    public static final Material DRAGON_EGG = (new Material(MaterialMapColor.i)).n();

    /** Material used for portals */
    public static final Material PORTAL = (new MaterialPortal(MaterialMapColor.b)).o();

    /** Cake's material, see BlockCake */
    public static final Material CAKE = (new Material(MaterialMapColor.b)).n();

    /** Web's material. */
    public static final Material WEB = (new MaterialWeb(MaterialMapColor.e)).f().n();

    /** Pistons' material. */
    public static final Material PISTON = (new Material(MaterialMapColor.m)).o();

    /** Bool defining if the block can burn or not. */
    private boolean canBurn;

    /**
     * Determines whether blocks with this material can be "overwritten" by other blocks when placed - eg snow, vines
     * and tall grass.
     */
    private boolean I;

    /** Indicates if the material is translucent */
    private boolean J;

    /** The color index used to draw the blocks of this material on maps. */
    public final MaterialMapColor G;

    /**
     * Determines if the material can be harvested without a tool (or with the wrong tool)
     */
    private boolean K = true;

    /**
     * Mobility information flag. 0 indicates that this block is normal, 1 indicates that it can't push other blocks, 2
     * indicates that it can't be pushed.
     */
    private int L;
    private boolean M;

    public Material(MaterialMapColor par1MapColor)
    {
        this.G = par1MapColor;
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    public boolean isLiquid()
    {
        return false;
    }

    public boolean isBuildable()
    {
        return true;
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    public boolean blocksLight()
    {
        return true;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean isSolid()
    {
        return true;
    }

    /**
     * Marks the material as translucent
     */
    private Material r()
    {
        this.J = true;
        return this;
    }

    /**
     * Makes blocks with this material require the correct tool to be harvested.
     */
    protected Material f()
    {
        this.K = false;
        return this;
    }

    /**
     * Set the canBurn bool to True and return the current object.
     */
    protected Material g()
    {
        this.canBurn = true;
        return this;
    }

    /**
     * Returns if the block can burn or not.
     */
    public boolean isBurnable()
    {
        return this.canBurn;
    }

    /**
     * Sets {@link #I} to true.
     */
    public Material i()
    {
        this.I = true;
        return this;
    }

    /**
     * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
     */
    public boolean isReplaceable()
    {
        return this.I;
    }

    /**
     * Indicate if the material is opaque
     */
    public boolean k()
    {
        return this.J ? false : this.isSolid();
    }

    /**
     * Returns true if the material can be harvested without a tool (or with the wrong tool)
     */
    public boolean isAlwaysDestroyable()
    {
        return this.K;
    }

    /**
     * Returns the mobility information of the material, 0 = free, 1 = can't push but can move over, 2 = total
     * immobility and stop pistons.
     */
    public int getPushReaction()
    {
        return this.L;
    }

    /**
     * This type of material can't be pushed, but pistons can move over it.
     */
    protected Material n()
    {
        this.L = 1;
        return this;
    }

    /**
     * This type of material can't be pushed, and pistons are blocked to move.
     */
    protected Material o()
    {
        this.L = 2;
        return this;
    }

    protected Material p()
    {
        this.M = true;
        return this;
    }

    public boolean q()
    {
        return this.M;
    }
}
