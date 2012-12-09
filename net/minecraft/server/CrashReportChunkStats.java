package net.minecraft.server;

import java.util.concurrent.Callable;

class CrashReportChunkStats implements Callable
{
    /** Reference to the World object. */
    final World a;

    CrashReportChunkStats(World par1World)
    {
        this.a = par1World;
    }

    /**
     * Returns the result of the ChunkProvider's makeString
     */
    public String a()
    {
        return this.a.chunkProvider.getName();
    }

    public Object call()
    {
        return this.a();
    }
}
