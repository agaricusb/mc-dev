package net.minecraft.server;

public class WeightedRandomChoice
{
    /**
     * The Weight is how often the item is chosen(higher number is higher chance(lower is lower))
     */
    protected int a;

    public WeightedRandomChoice(int par1)
    {
        this.a = par1;
    }
}
