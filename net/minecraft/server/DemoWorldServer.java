package net.minecraft.server;


public class DemoWorldServer extends WorldServer
{
    private static final long J = (long)"North Carolina".hashCode();
    public static final WorldSettings a = (new WorldSettings(J, EnumGamemode.SURVIVAL, true, false, WorldType.NORMAL)).a();

    public DemoWorldServer(MinecraftServer par1MinecraftServer, IDataManager par2ISaveHandler, String par3Str, int par4, MethodProfiler par5Profiler)
    {
        super(par1MinecraftServer, par2ISaveHandler, par3Str, par4, a, par5Profiler);
    }
}
