package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportLevelStorageVersion implements Callable
{
    final WorldData a;

    CrashReportLevelStorageVersion(WorldData par1WorldInfo)
    {
        this.a = par1WorldInfo;
    }

    public String a()
    {
        String var1 = "Unknown?";

        try
        {
            switch (WorldData.j(this.a))
            {
                case 19132:
                    var1 = "McRegion";
                    break;

                case 19133:
                    var1 = "Anvil";
            }
        }
        catch (Throwable var3)
        {
            ;
        }

        return String.format("0x%05X - %s", new Object[] {Integer.valueOf(WorldData.j(this.a)), var1});
    }

    public Object call()
    {
        return this.a();
    }
}
