package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportTileEntityName implements Callable
{
    final TileEntity a;

    CrashReportTileEntityName(TileEntity par1TileEntity)
    {
        this.a = par1TileEntity;
    }

    public String a()
    {
        return (String) TileEntity.t().get(this.a.getClass()) + " // " + this.a.getClass().getCanonicalName();
    }

    public Object call()
    {
        return this.a();
    }
}
