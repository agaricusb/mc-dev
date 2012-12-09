package net.minecraft.server;

class PendingChunkToSave
{
    public final ChunkCoordIntPair a;
    public final NBTTagCompound b;

    public PendingChunkToSave(ChunkCoordIntPair par1ChunkCoordIntPair, NBTTagCompound par2NBTTagCompound)
    {
        this.a = par1ChunkCoordIntPair;
        this.b = par2NBTTagCompound;
    }
}
