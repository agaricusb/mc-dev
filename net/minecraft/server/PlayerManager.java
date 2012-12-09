package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager
{
    private final WorldServer world;

    /** players in the current instance */
    private final List managedPlayers = new ArrayList();

    /** the hash of all playerInstances created */
    private final LongHashMap c = new LongHashMap();

    /** the playerInstances(chunks) that need to be updated */
    private final List d = new ArrayList();

    /**
     * Number of chunks the server sends to the client. Valid 3<=x<=15. In server.properties.
     */
    private final int e;

    /** x, z direction vectors: east, south, west, north */
    private final int[][] f = new int[][] {{1, 0}, {0, 1}, { -1, 0}, {0, -1}};

    public PlayerManager(WorldServer par1WorldServer, int par2)
    {
        if (par2 > 15)
        {
            throw new IllegalArgumentException("Too big view radius!");
        }
        else if (par2 < 3)
        {
            throw new IllegalArgumentException("Too small view radius!");
        }
        else
        {
            this.e = par2;
            this.world = par1WorldServer;
        }
    }

    /**
     * Returns the MinecraftServer associated with the PlayerManager.
     */
    public WorldServer a()
    {
        return this.world;
    }

    /**
     * updates all the player instances that need to be updated
     */
    public void flush()
    {
        for (int var1 = 0; var1 < this.d.size(); ++var1)
        {
            ((PlayerInstance)this.d.get(var1)).a();
        }

        this.d.clear();

        if (this.managedPlayers.isEmpty())
        {
            WorldProvider var2 = this.world.worldProvider;

            if (!var2.e())
            {
                this.world.chunkProviderServer.a();
            }
        }
    }

    /**
     * passi n the chunk x and y and a flag as to whether or not the instance should be made if it doesnt exist
     */
    private PlayerInstance a(int par1, int par2, boolean par3)
    {
        long var4 = (long)par1 + 2147483647L | (long)par2 + 2147483647L << 32;
        PlayerInstance var6 = (PlayerInstance)this.c.getEntry(var4);

        if (var6 == null && par3)
        {
            var6 = new PlayerInstance(this, par1, par2);
            this.c.put(var4, var6);
        }

        return var6;
    }

    public void flagDirty(int par1, int par2, int par3)
    {
        int var4 = par1 >> 4;
        int var5 = par3 >> 4;
        PlayerInstance var6 = this.a(var4, var5, false);

        if (var6 != null)
        {
            var6.a(par1 & 15, par2, par3 & 15);
        }
    }

    /**
     * Adds an EntityPlayerMP to the PlayerManager.
     */
    public void addPlayer(EntityPlayer par1EntityPlayerMP)
    {
        int var2 = (int)par1EntityPlayerMP.locX >> 4;
        int var3 = (int)par1EntityPlayerMP.locZ >> 4;
        par1EntityPlayerMP.d = par1EntityPlayerMP.locX;
        par1EntityPlayerMP.e = par1EntityPlayerMP.locZ;

        for (int var4 = var2 - this.e; var4 <= var2 + this.e; ++var4)
        {
            for (int var5 = var3 - this.e; var5 <= var3 + this.e; ++var5)
            {
                this.a(var4, var5, true).a(par1EntityPlayerMP);
            }
        }

        this.managedPlayers.add(par1EntityPlayerMP);
        this.b(par1EntityPlayerMP);
    }

    /**
     * Removes all chunks from the given player's chunk load queue that are not in viewing range of the player.
     */
    public void b(EntityPlayer par1EntityPlayerMP)
    {
        ArrayList var2 = new ArrayList(par1EntityPlayerMP.chunkCoordIntPairQueue);
        int var3 = 0;
        int var4 = this.e;
        int var5 = (int)par1EntityPlayerMP.locX >> 4;
        int var6 = (int)par1EntityPlayerMP.locZ >> 4;
        int var7 = 0;
        int var8 = 0;
        ChunkCoordIntPair var9 = PlayerInstance.a(this.a(var5, var6, true));
        par1EntityPlayerMP.chunkCoordIntPairQueue.clear();

        if (var2.contains(var9))
        {
            par1EntityPlayerMP.chunkCoordIntPairQueue.add(var9);
        }

        int var10;

        for (var10 = 1; var10 <= var4 * 2; ++var10)
        {
            for (int var11 = 0; var11 < 2; ++var11)
            {
                int[] var12 = this.f[var3++ % 4];

                for (int var13 = 0; var13 < var10; ++var13)
                {
                    var7 += var12[0];
                    var8 += var12[1];
                    var9 = PlayerInstance.a(this.a(var5 + var7, var6 + var8, true));

                    if (var2.contains(var9))
                    {
                        par1EntityPlayerMP.chunkCoordIntPairQueue.add(var9);
                    }
                }
            }
        }

        var3 %= 4;

        for (var10 = 0; var10 < var4 * 2; ++var10)
        {
            var7 += this.f[var3][0];
            var8 += this.f[var3][1];
            var9 = PlayerInstance.a(this.a(var5 + var7, var6 + var8, true));

            if (var2.contains(var9))
            {
                par1EntityPlayerMP.chunkCoordIntPairQueue.add(var9);
            }
        }
    }

    /**
     * Removes an EntityPlayerMP from the PlayerManager.
     */
    public void removePlayer(EntityPlayer par1EntityPlayerMP)
    {
        int var2 = (int)par1EntityPlayerMP.d >> 4;
        int var3 = (int)par1EntityPlayerMP.e >> 4;

        for (int var4 = var2 - this.e; var4 <= var2 + this.e; ++var4)
        {
            for (int var5 = var3 - this.e; var5 <= var3 + this.e; ++var5)
            {
                PlayerInstance var6 = this.a(var4, var5, false);

                if (var6 != null)
                {
                    var6.b(par1EntityPlayerMP);
                }
            }
        }

        this.managedPlayers.remove(par1EntityPlayerMP);
    }

    private boolean a(int par1, int par2, int par3, int par4, int par5)
    {
        int var6 = par1 - par3;
        int var7 = par2 - par4;
        return var6 >= -par5 && var6 <= par5 ? var7 >= -par5 && var7 <= par5 : false;
    }

    /**
     * update chunks around a player being moved by server logic (e.g. cart, boat)
     */
    public void movePlayer(EntityPlayer par1EntityPlayerMP)
    {
        int var2 = (int)par1EntityPlayerMP.locX >> 4;
        int var3 = (int)par1EntityPlayerMP.locZ >> 4;
        double var4 = par1EntityPlayerMP.d - par1EntityPlayerMP.locX;
        double var6 = par1EntityPlayerMP.e - par1EntityPlayerMP.locZ;
        double var8 = var4 * var4 + var6 * var6;

        if (var8 >= 64.0D)
        {
            int var10 = (int)par1EntityPlayerMP.d >> 4;
            int var11 = (int)par1EntityPlayerMP.e >> 4;
            int var12 = this.e;
            int var13 = var2 - var10;
            int var14 = var3 - var11;

            if (var13 != 0 || var14 != 0)
            {
                for (int var15 = var2 - var12; var15 <= var2 + var12; ++var15)
                {
                    for (int var16 = var3 - var12; var16 <= var3 + var12; ++var16)
                    {
                        if (!this.a(var15, var16, var10, var11, var12))
                        {
                            this.a(var15, var16, true).a(par1EntityPlayerMP);
                        }

                        if (!this.a(var15 - var13, var16 - var14, var2, var3, var12))
                        {
                            PlayerInstance var17 = this.a(var15 - var13, var16 - var14, false);

                            if (var17 != null)
                            {
                                var17.b(par1EntityPlayerMP);
                            }
                        }
                    }
                }

                this.b(par1EntityPlayerMP);
                par1EntityPlayerMP.d = par1EntityPlayerMP.locX;
                par1EntityPlayerMP.e = par1EntityPlayerMP.locZ;
            }
        }
    }

    public boolean a(EntityPlayer par1EntityPlayerMP, int par2, int par3)
    {
        PlayerInstance var4 = this.a(par2, par3, false);
        return var4 == null ? false : PlayerInstance.b(var4).contains(par1EntityPlayerMP) && !par1EntityPlayerMP.chunkCoordIntPairQueue.contains(PlayerInstance.a(var4));
    }

    public static int getFurthestViewableBlock(int par0)
    {
        return par0 * 16 - 16;
    }

    static WorldServer a(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.world;
    }

    static LongHashMap b(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.c;
    }

    static List c(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.d;
    }
}
