package net.minecraft.server;

import java.util.concurrent.Callable;

public class CrashReportPlayerCount implements Callable
{
    final MinecraftServer a;

    public CrashReportPlayerCount(MinecraftServer par1MinecraftServer)
    {
        this.a = par1MinecraftServer;
    }

    public String a()
    {
        return MinecraftServer.a(this.a).getPlayerCount() + " / " + MinecraftServer.a(this.a).getMaxPlayers() + "; " + MinecraftServer.a(this.a).players;
    }

    public Object call()
    {
        return this.a();
    }
}
