package net.minecraft.server;

import java.util.Iterator;

public class WorldManager implements IWorldAccess
{
    /** Reference to the MinecraftServer object. */
    private MinecraftServer server;

    /** The WorldServer object. */
    private WorldServer world;

    public WorldManager(MinecraftServer par1MinecraftServer, WorldServer par2WorldServer)
    {
        this.server = par1MinecraftServer;
        this.world = par2WorldServer;
    }

    /**
     * Spawns a particle. Arg: particleType, x, y, z, velX, velY, velZ
     */
    public void a(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {}

    /**
     * Start the skin for this entity downloading, if necessary, and increment its reference counter
     */
    public void a(Entity par1Entity)
    {
        this.world.getTracker().track(par1Entity);
    }

    /**
     * Decrement the reference counter for this entity's skin image data
     */
    public void b(Entity par1Entity)
    {
        this.world.getTracker().untrackEntity(par1Entity);
    }

    /**
     * Plays the specified sound. Arg: soundName, x, y, z, volume, pitch
     */
    public void a(String par1Str, double par2, double par4, double par6, float par8, float par9)
    {
        this.server.getServerConfigurationManager().sendPacketNearby(par2, par4, par6, par8 > 1.0F ? (double) (16.0F * par8) : 16.0D, this.world.worldProvider.dimension, new Packet62NamedSoundEffect(par1Str, par2, par4, par6, par8, par9));
    }

    public void a(EntityHuman par1EntityPlayer, String par2Str, double par3, double par5, double par7, float par9, float par10)
    {
        this.server.getServerConfigurationManager().sendPacketNearby(par1EntityPlayer, par3, par5, par7, par9 > 1.0F ? (double) (16.0F * par9) : 16.0D, this.world.worldProvider.dimension, new Packet62NamedSoundEffect(par2Str, par3, par5, par7, par9, par10));
    }

    /**
     * On the client, re-renders all blocks in this range, inclusive. On the server, does nothing. Args: min x, min y,
     * min z, max x, max y, max z
     */
    public void a(int par1, int par2, int par3, int par4, int par5, int par6) {}

    /**
     * On the client, re-renders the block. On the server, sends the block to the client (which will re-render it),
     * including the tile entity description packet if applicable. Args: x, y, z
     */
    public void a(int par1, int par2, int par3)
    {
        this.world.getPlayerManager().flagDirty(par1, par2, par3);
    }

    /**
     * On the client, re-renders this block. On the server, does nothing. Used for lighting updates.
     */
    public void b(int par1, int par2, int par3) {}

    /**
     * Plays the specified record. Arg: recordName, x, y, z
     */
    public void a(String par1Str, int par2, int par3, int par4) {}

    /**
     * Plays a pre-canned sound effect along with potentially auxiliary data-driven one-shot behaviour (particles, etc).
     */
    public void a(EntityHuman par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
    {
        this.server.getServerConfigurationManager().sendPacketNearby(par1EntityPlayer, (double) par3, (double) par4, (double) par5, 64.0D, this.world.worldProvider.dimension, new Packet61WorldEvent(par2, par3, par4, par5, par6, false));
    }

    public void a(int par1, int par2, int par3, int par4, int par5)
    {
        this.server.getServerConfigurationManager().sendAll(new Packet61WorldEvent(par1, par2, par3, par4, par5, true));
    }

    /**
     * Starts (or continues) destroying a block with given ID at the given coordinates for the given partially destroyed
     * value
     */
    public void b(int par1, int par2, int par3, int par4, int par5)
    {
        Iterator var6 = this.server.getServerConfigurationManager().players.iterator();

        while (var6.hasNext())
        {
            EntityPlayer var7 = (EntityPlayer)var6.next();

            if (var7 != null && var7.world == this.world && var7.id != par1)
            {
                double var8 = (double)par2 - var7.locX;
                double var10 = (double)par3 - var7.locY;
                double var12 = (double)par4 - var7.locZ;

                if (var8 * var8 + var10 * var10 + var12 * var12 < 1024.0D)
                {
                    var7.netServerHandler.sendPacket(new Packet55BlockBreakAnimation(par1, par2, par3, par4, par5));
                }
            }
        }
    }
}
