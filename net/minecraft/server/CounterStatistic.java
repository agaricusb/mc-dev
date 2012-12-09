package net.minecraft.server;

public class CounterStatistic extends Statistic
{
    public CounterStatistic(int par1, String par2Str, Counter par3IStatType)
    {
        super(par1, par2Str, par3IStatType);
    }

    public CounterStatistic(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    /**
     * Register the stat into StatList.
     */
    public Statistic g()
    {
        super.g();
        StatisticList.c.add(this);
        return this;
    }
}
