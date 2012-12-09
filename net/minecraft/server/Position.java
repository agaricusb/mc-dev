package net.minecraft.server;

public class Position implements IPosition
{
    protected final double a;
    protected final double b;
    protected final double c;

    public Position(double par1, double par3, double par5)
    {
        this.a = par1;
        this.b = par3;
        this.c = par5;
    }

    public double getX()
    {
        return this.a;
    }

    public double getY()
    {
        return this.b;
    }

    public double getZ()
    {
        return this.c;
    }
}
