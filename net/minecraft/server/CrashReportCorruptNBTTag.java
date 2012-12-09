package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportCorruptNBTTag implements Callable
{
    final String a;

    final NBTTagCompound b;

    CrashReportCorruptNBTTag(NBTTagCompound par1NBTTagCompound, String par2Str)
    {
        this.b = par1NBTTagCompound;
        this.a = par2Str;
    }

    public String a()
    {
        return NBTBase.b[((NBTBase) NBTTagCompound.a(this.b).get(this.a)).getTypeId()];
    }

    public Object call()
    {
        return this.a();
    }
}
