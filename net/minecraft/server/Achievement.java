package net.minecraft.server;

public class Achievement extends Statistic
{
    /**
     * Is the column (related to center of achievement gui, in 24 pixels unit) that the achievement will be displayed.
     */
    public final int a;

    /**
     * Is the row (related to center of achievement gui, in 24 pixels unit) that the achievement will be displayed.
     */
    public final int b;

    /**
     * Holds the parent achievement, that must be taken before this achievement is avaiable.
     */
    public final Achievement c;

    /**
     * Holds the description of the achievement, ready to be formatted and/or displayed.
     */
    private final String k;

    /**
     * Holds the ItemStack that will be used to draw the achievement into the GUI.
     */
    public final ItemStack d;

    /**
     * Special achievements have a 'spiked' (on normal texture pack) frame, special achievements are the hardest ones to
     * achieve.
     */
    private boolean m;

    public Achievement(int par1, String par2Str, int par3, int par4, Item par5Item, Achievement par6Achievement)
    {
        this(par1, par2Str, par3, par4, new ItemStack(par5Item), par6Achievement);
    }

    public Achievement(int par1, String par2Str, int par3, int par4, Block par5Block, Achievement par6Achievement)
    {
        this(par1, par2Str, par3, par4, new ItemStack(par5Block), par6Achievement);
    }

    public Achievement(int par1, String par2Str, int par3, int par4, ItemStack par5ItemStack, Achievement par6Achievement)
    {
        super(5242880 + par1, "achievement." + par2Str);
        this.d = par5ItemStack;
        this.k = "achievement." + par2Str + ".desc";
        this.a = par3;
        this.b = par4;

        if (par3 < AchievementList.a)
        {
            AchievementList.a = par3;
        }

        if (par4 < AchievementList.b)
        {
            AchievementList.b = par4;
        }

        if (par3 > AchievementList.c)
        {
            AchievementList.c = par3;
        }

        if (par4 > AchievementList.d)
        {
            AchievementList.d = par4;
        }

        this.c = par6Achievement;
    }

    /**
     * Indicates whether or not the given achievement or statistic is independent (i.e., lacks prerequisites for being
     * update).
     */
    public Achievement a()
    {
        this.f = true;
        return this;
    }

    /**
     * Special achievements have a 'spiked' (on normal texture pack) frame, special achievements are the hardest ones to
     * achieve.
     */
    public Achievement b()
    {
        this.m = true;
        return this;
    }

    /**
     * Adds the achievement on the internal list of registered achievements, also, it's check for duplicated id's.
     */
    public Achievement c()
    {
        super.g();
        AchievementList.e.add(this);
        return this;
    }

    /**
     * Register the stat into StatList.
     */
    public Statistic g()
    {
        return this.c();
    }

    /**
     * Initializes the current stat as independent (i.e., lacking prerequisites for being updated) and returns the
     * current instance.
     */
    public Statistic h()
    {
        return this.a();
    }
}
