package net.minecraft.server;

public final class ProfilerInfo implements Comparable
{
    public double a;
    public double b;
    public String c;

    public ProfilerInfo(String par1Str, double par2, double par4)
    {
        this.c = par1Str;
        this.a = par2;
        this.b = par4;
    }

    public int a(ProfilerInfo par1ProfilerResult)
    {
        return par1ProfilerResult.a < this.a ? -1 : (par1ProfilerResult.a > this.a ? 1 : par1ProfilerResult.c.compareTo(this.c));
    }

    public int compareTo(Object par1Obj)
    {
        return this.a((ProfilerInfo) par1Obj);
    }
}
