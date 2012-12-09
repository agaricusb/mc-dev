package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportPlayers implements Callable
{
    /** Reference to the World object. */
    final World a;

    CrashReportPlayers(World par1World)
    {
        this.a = par1World;
    }

    /**
     * Returns the size and contents of the player entity list.
     */
    public String a()
    {
        return this.a.players.size() + " total; " + this.a.players.toString();
    }

    public Object call()
    {
        return this.a();
    }
}
