package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportCorruptNBTTag2 implements Callable
{
    final int a;

    final NBTTagCompound b;

    CrashReportCorruptNBTTag2(NBTTagCompound par1NBTTagCompound, int par2)
    {
        this.b = par1NBTTagCompound;
        this.a = par2;
    }

    public String a()
    {
        return NBTBase.b[this.a];
    }

    public Object call()
    {
        return this.a();
    }
}
