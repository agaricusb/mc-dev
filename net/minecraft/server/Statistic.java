package net.minecraft.server;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Statistic
{
    /** The Stat ID */
    public final int e;

    /** The Stat name */
    private final String a;
    public boolean f;

    /** Holds the GUID of the stat. */
    public String g;
    private final Counter b;
    private static NumberFormat c = NumberFormat.getIntegerInstance(Locale.US);
    public static Counter h = new UnknownCounter();
    private static DecimalFormat d = new DecimalFormat("########0.00");
    public static Counter i = new TimeCounter();
    public static Counter j = new DistancesCounter();

    public Statistic(int par1, String par2Str, Counter par3IStatType)
    {
        this.f = false;
        this.e = par1;
        this.a = par2Str;
        this.b = par3IStatType;
    }

    public Statistic(int par1, String par2Str)
    {
        this(par1, par2Str, h);
    }

    /**
     * Initializes the current stat as independent (i.e., lacking prerequisites for being updated) and returns the
     * current instance.
     */
    public Statistic h()
    {
        this.f = true;
        return this;
    }

    /**
     * Register the stat into StatList.
     */
    public Statistic g()
    {
        if (StatisticList.a.containsKey(Integer.valueOf(this.e)))
        {
            throw new RuntimeException("Duplicate stat id: \"" + ((Statistic) StatisticList.a.get(Integer.valueOf(this.e))).a + "\" and \"" + this.a + "\" at id " + this.e);
        }
        else
        {
            StatisticList.b.add(this);
            StatisticList.a.put(Integer.valueOf(this.e), this);
            this.g = AchievementMap.a(this.e);
            return this;
        }
    }

    public String toString()
    {
        return LocaleI18n.get(this.a);
    }
}
