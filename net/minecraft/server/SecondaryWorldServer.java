package net.minecraft.server;


public class SecondaryWorldServer extends WorldServer
{
    public SecondaryWorldServer(MinecraftServer par1MinecraftServer, IDataManager par2ISaveHandler, String par3Str, int par4, WorldSettings par5WorldSettings, WorldServer par6WorldServer, MethodProfiler par7Profiler)
    {
        super(par1MinecraftServer, par2ISaveHandler, par3Str, par4, par5WorldSettings, par7Profiler);
        this.worldMaps = par6WorldServer.worldMaps;
        this.worldData = new SecondaryWorldData(par6WorldServer.getWorldData());
    }

    /**
     * Saves the chunks to disk.
     */
    protected void a() throws ExceptionWorldConflict {}
}
