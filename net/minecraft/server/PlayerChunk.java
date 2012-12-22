package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

class PlayerChunk
{
    /** the list of all players in this instance (chunk) */
    private final List b;

    /** the chunk the player currently resides in */
    private final ChunkCoordIntPair location;

    /** array of blocks to update this tick */
    private short[] dirtyBlocks;

    /** the number of blocks that need to be updated next tick */
    private int dirtyCount;
    private int f;

    final PlayerChunkMap playerChunkMap;

    public PlayerChunk(PlayerChunkMap par1PlayerManager, int par2, int par3)
    {
        this.playerChunkMap = par1PlayerManager;
        this.b = new ArrayList();
        this.dirtyBlocks = new short[64];
        this.dirtyCount = 0;
        this.location = new ChunkCoordIntPair(par2, par3);
        par1PlayerManager.a().chunkProviderServer.getChunkAt(par2, par3);
    }

    /**
     * adds this player to the playerInstance
     */
    public void a(EntityPlayer par1EntityPlayerMP)
    {
        if (this.b.contains(par1EntityPlayerMP))
        {
            throw new IllegalStateException("Failed to add player. " + par1EntityPlayerMP + " already is in chunk " + this.location.x + ", " + this.location.z);
        }
        else
        {
            this.b.add(par1EntityPlayerMP);
            par1EntityPlayerMP.chunkCoordIntPairQueue.add(this.location);
        }
    }

    /**
     * remove player from this instance
     */
    public void b(EntityPlayer par1EntityPlayerMP)
    {
        if (this.b.contains(par1EntityPlayerMP))
        {
            par1EntityPlayerMP.playerConnection.sendPacket(new Packet51MapChunk(PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z), true, 0));
            this.b.remove(par1EntityPlayerMP);
            par1EntityPlayerMP.chunkCoordIntPairQueue.remove(this.location);

            if (this.b.isEmpty())
            {
                long var2 = (long)this.location.x + 2147483647L | (long)this.location.z + 2147483647L << 32;
                PlayerChunkMap.b(this.playerChunkMap).remove(var2);

                if (this.dirtyCount > 0)
                {
                    PlayerChunkMap.c(this.playerChunkMap).remove(this);
                }

                this.playerChunkMap.a().chunkProviderServer.queueUnload(this.location.x, this.location.z);
            }
        }
    }

    /**
     * mark the block as changed so that it will update clients who need to know about it
     */
    public void a(int par1, int par2, int par3)
    {
        if (this.dirtyCount == 0)
        {
            PlayerChunkMap.c(this.playerChunkMap).add(this);
        }

        this.f |= 1 << (par2 >> 4);

        if (this.dirtyCount < 64)
        {
            short var4 = (short)(par1 << 12 | par3 << 8 | par2);

            for (int var5 = 0; var5 < this.dirtyCount; ++var5)
            {
                if (this.dirtyBlocks[var5] == var4)
                {
                    return;
                }
            }

            this.dirtyBlocks[this.dirtyCount++] = var4;
        }
    }

    /**
     * sends the packet to all players in the current instance
     */
    public void sendAll(Packet par1Packet)
    {
        for (int var2 = 0; var2 < this.b.size(); ++var2)
        {
            EntityPlayer var3 = (EntityPlayer)this.b.get(var2);

            if (!var3.chunkCoordIntPairQueue.contains(this.location))
            {
                var3.playerConnection.sendPacket(par1Packet);
            }
        }
    }

    public void a()
    {
        if (this.dirtyCount != 0)
        {
            int var1;
            int var2;
            int var3;

            if (this.dirtyCount == 1)
            {
                var1 = this.location.x * 16 + (this.dirtyBlocks[0] >> 12 & 15);
                var2 = this.dirtyBlocks[0] & 255;
                var3 = this.location.z * 16 + (this.dirtyBlocks[0] >> 8 & 15);
                this.sendAll(new Packet53BlockChange(var1, var2, var3, PlayerChunkMap.a(this.playerChunkMap)));

                if (PlayerChunkMap.a(this.playerChunkMap).isTileEntity(var1, var2, var3))
                {
                    this.sendTileEntity(PlayerChunkMap.a(this.playerChunkMap).getTileEntity(var1, var2, var3));
                }
            }
            else
            {
                int var4;

                if (this.dirtyCount == 64)
                {
                    var1 = this.location.x * 16;
                    var2 = this.location.z * 16;
                    this.sendAll(new Packet51MapChunk(PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z), false, this.f));

                    for (var3 = 0; var3 < 16; ++var3)
                    {
                        if ((this.f & 1 << var3) != 0)
                        {
                            var4 = var3 << 4;
                            List var5 = PlayerChunkMap.a(this.playerChunkMap).getTileEntities(var1, var4, var2, var1 + 16, var4 + 16, var2 + 16);

                            for (int var6 = 0; var6 < var5.size(); ++var6)
                            {
                                this.sendTileEntity((TileEntity) var5.get(var6));
                            }
                        }
                    }
                }
                else
                {
                    this.sendAll(new Packet52MultiBlockChange(this.location.x, this.location.z, this.dirtyBlocks, this.dirtyCount, PlayerChunkMap.a(this.playerChunkMap)));

                    for (var1 = 0; var1 < this.dirtyCount; ++var1)
                    {
                        var2 = this.location.x * 16 + (this.dirtyBlocks[var1] >> 12 & 15);
                        var3 = this.dirtyBlocks[var1] & 255;
                        var4 = this.location.z * 16 + (this.dirtyBlocks[var1] >> 8 & 15);

                        if (PlayerChunkMap.a(this.playerChunkMap).isTileEntity(var2, var3, var4))
                        {
                            this.sendTileEntity(PlayerChunkMap.a(this.playerChunkMap).getTileEntity(var2, var3, var4));
                        }
                    }
                }
            }

            this.dirtyCount = 0;
            this.f = 0;
        }
    }

    /**
     * sends players update packet about the given entity
     */
    private void sendTileEntity(TileEntity par1TileEntity)
    {
        if (par1TileEntity != null)
        {
            Packet var2 = par1TileEntity.getUpdatePacket();

            if (var2 != null)
            {
                this.sendAll(var2);
            }
        }
    }

    static ChunkCoordIntPair a(PlayerChunk par0PlayerInstance)
    {
        return par0PlayerInstance.location;
    }

    static List b(PlayerChunk par0PlayerInstance)
    {
        return par0PlayerInstance.b;
    }
}
