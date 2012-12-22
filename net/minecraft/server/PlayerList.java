package net.minecraft.server;

import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public abstract class PlayerList
{
    private static final SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");

    /** Reference to the logger. */
    public static final Logger a = Logger.getLogger("Minecraft");

    /** Reference to the MinecraftServer object. */
    private final MinecraftServer server;

    /** A list of player entities that exist on this server. */
    public final List players = new ArrayList();
    private final BanList banByName = new BanList(new File("banned-players.txt"));
    private final BanList banByIP = new BanList(new File("banned-ips.txt"));

    /** A set containing the OPs. */
    private Set operators = new HashSet();

    /** The Set of all whitelisted players. */
    private Set whitelist = new HashSet();

    /** Reference to the PlayerNBTManager object. */
    private PlayerFileData playerFileData;

    /**
     * Server setting to only allow OPs and whitelisted players to join the server.
     */
    private boolean hasWhitelist;

    /** The maximum number of players that can be connected at a time. */
    protected int maxPlayers;
    protected int d;
    private EnumGamemode m;

    /** True if all players are allowed to use commands (cheats). */
    private boolean n;

    /**
     * index into playerEntities of player to ping, updated every tick; currently hardcoded to max at 200 players
     */
    private int o = 0;

    public PlayerList(MinecraftServer par1MinecraftServer)
    {
        this.server = par1MinecraftServer;
        this.banByName.setEnabled(false);
        this.banByIP.setEnabled(false);
        this.maxPlayers = 8;
    }

    public void a(INetworkManager par1INetworkManager, EntityPlayer par2EntityPlayerMP)
    {
        this.a(par2EntityPlayerMP);
        par2EntityPlayerMP.spawnIn(this.server.getWorldServer(par2EntityPlayerMP.dimension));
        par2EntityPlayerMP.playerInteractManager.a((WorldServer) par2EntityPlayerMP.world);
        String var3 = "local";

        if (par1INetworkManager.getSocketAddress() != null)
        {
            var3 = par1INetworkManager.getSocketAddress().toString();
        }

        a.info(par2EntityPlayerMP.name + "[" + var3 + "] logged in with entity id " + par2EntityPlayerMP.id + " at (" + par2EntityPlayerMP.locX + ", " + par2EntityPlayerMP.locY + ", " + par2EntityPlayerMP.locZ + ")");
        WorldServer var4 = this.server.getWorldServer(par2EntityPlayerMP.dimension);
        ChunkCoordinates var5 = var4.getSpawn();
        this.a(par2EntityPlayerMP, (EntityPlayer) null, var4);
        PlayerConnection var6 = new PlayerConnection(this.server, par1INetworkManager, par2EntityPlayerMP);
        var6.sendPacket(new Packet1Login(par2EntityPlayerMP.id, var4.getWorldData().getType(), par2EntityPlayerMP.playerInteractManager.getGameMode(), var4.getWorldData().isHardcore(), var4.worldProvider.dimension, var4.difficulty, var4.getHeight(), this.getMaxPlayers()));
        var6.sendPacket(new Packet6SpawnPosition(var5.x, var5.y, var5.z));
        var6.sendPacket(new Packet202Abilities(par2EntityPlayerMP.abilities));
        var6.sendPacket(new Packet16BlockItemSwitch(par2EntityPlayerMP.inventory.itemInHandIndex));
        this.b(par2EntityPlayerMP, var4);
        this.sendAll(new Packet3Chat("\u00a7e" + par2EntityPlayerMP.name + " joined the game."));
        this.c(par2EntityPlayerMP);
        var6.a(par2EntityPlayerMP.locX, par2EntityPlayerMP.locY, par2EntityPlayerMP.locZ, par2EntityPlayerMP.yaw, par2EntityPlayerMP.pitch);
        this.server.ae().a(var6);
        var6.sendPacket(new Packet4UpdateTime(var4.getTime(), var4.getDayTime()));

        if (this.server.getTexturePack().length() > 0)
        {
            par2EntityPlayerMP.a(this.server.getTexturePack(), this.server.S());
        }

        Iterator var7 = par2EntityPlayerMP.getEffects().iterator();

        while (var7.hasNext())
        {
            MobEffect var8 = (MobEffect)var7.next();
            var6.sendPacket(new Packet41MobEffect(par2EntityPlayerMP.id, var8));
        }

        par2EntityPlayerMP.syncInventory();
    }

    /**
     * Sets the NBT manager to the one for the WorldServer given.
     */
    public void setPlayerFileData(WorldServer[] par1ArrayOfWorldServer)
    {
        this.playerFileData = par1ArrayOfWorldServer[0].getDataManager().getPlayerFileData();
    }

    public void a(EntityPlayer par1EntityPlayerMP, WorldServer par2WorldServer)
    {
        WorldServer var3 = par1EntityPlayerMP.p();

        if (par2WorldServer != null)
        {
            par2WorldServer.getPlayerChunkMap().removePlayer(par1EntityPlayerMP);
        }

        var3.getPlayerChunkMap().addPlayer(par1EntityPlayerMP);
        var3.chunkProviderServer.getChunkAt((int) par1EntityPlayerMP.locX >> 4, (int) par1EntityPlayerMP.locZ >> 4);
    }

    public int a()
    {
        return PlayerChunkMap.getFurthestViewableBlock(this.o());
    }

    /**
     * called during player login. reads the player information from disk.
     */
    public void a(EntityPlayer par1EntityPlayerMP)
    {
        NBTTagCompound var2 = this.server.worldServer[0].getWorldData().i();

        if (par1EntityPlayerMP.getName().equals(this.server.H()) && var2 != null)
        {
            par1EntityPlayerMP.e(var2);
        }
        else
        {
            this.playerFileData.load(par1EntityPlayerMP);
        }
    }

    /**
     * also stores the NBTTags if this is an intergratedPlayerList
     */
    protected void b(EntityPlayer par1EntityPlayerMP)
    {
        this.playerFileData.save(par1EntityPlayerMP);
    }

    /**
     * Called when a player successfully logs in. Reads player data from disk and inserts the player into the world.
     */
    public void c(EntityPlayer par1EntityPlayerMP)
    {
        this.sendAll(new Packet201PlayerInfo(par1EntityPlayerMP.name, true, 1000));
        this.players.add(par1EntityPlayerMP);
        WorldServer var2 = this.server.getWorldServer(par1EntityPlayerMP.dimension);
        var2.addEntity(par1EntityPlayerMP);
        this.a(par1EntityPlayerMP, (WorldServer) null);

        for (int var3 = 0; var3 < this.players.size(); ++var3)
        {
            EntityPlayer var4 = (EntityPlayer)this.players.get(var3);
            par1EntityPlayerMP.playerConnection.sendPacket(new Packet201PlayerInfo(var4.name, true, var4.ping));
        }
    }

    /**
     * using player's dimension, update their movement when in a vehicle (e.g. cart, boat)
     */
    public void d(EntityPlayer par1EntityPlayerMP)
    {
        par1EntityPlayerMP.p().getPlayerChunkMap().movePlayer(par1EntityPlayerMP);
    }

    /**
     * Called when a player disconnects from the game. Writes player data to disk and removes them from the world.
     */
    public void disconnect(EntityPlayer par1EntityPlayerMP)
    {
        this.b(par1EntityPlayerMP);
        WorldServer var2 = par1EntityPlayerMP.p();
        var2.kill(par1EntityPlayerMP);
        var2.getPlayerChunkMap().removePlayer(par1EntityPlayerMP);
        this.players.remove(par1EntityPlayerMP);
        this.sendAll(new Packet201PlayerInfo(par1EntityPlayerMP.name, false, 9999));
    }

    /**
     * checks ban-lists, then white-lists, then space for the server. Returns null on success, or an error message
     */
    public String attemptLogin(SocketAddress par1SocketAddress, String par2Str)
    {
        if (this.banByName.isBanned(par2Str))
        {
            BanEntry var6 = (BanEntry)this.banByName.getEntries().get(par2Str);
            String var7 = "You are banned from this server!\nReason: " + var6.getReason();

            if (var6.getExpires() != null)
            {
                var7 = var7 + "\nYour ban will be removed on " + e.format(var6.getExpires());
            }

            return var7;
        }
        else if (!this.isWhitelisted(par2Str))
        {
            return "You are not white-listed on this server!";
        }
        else
        {
            String var3 = par1SocketAddress.toString();
            var3 = var3.substring(var3.indexOf("/") + 1);
            var3 = var3.substring(0, var3.indexOf(":"));

            if (this.banByIP.isBanned(var3))
            {
                BanEntry var4 = (BanEntry)this.banByIP.getEntries().get(var3);
                String var5 = "Your IP address is banned from this server!\nReason: " + var4.getReason();

                if (var4.getExpires() != null)
                {
                    var5 = var5 + "\nYour ban will be removed on " + e.format(var4.getExpires());
                }

                return var5;
            }
            else
            {
                return this.players.size() >= this.maxPlayers ? "The server is full!" : null;
            }
        }
    }

    /**
     * also checks for multiple logins
     */
    public EntityPlayer processLogin(String par1Str)
    {
        ArrayList var2 = new ArrayList();
        EntityPlayer var4;

        for (int var3 = 0; var3 < this.players.size(); ++var3)
        {
            var4 = (EntityPlayer)this.players.get(var3);

            if (var4.name.equalsIgnoreCase(par1Str))
            {
                var2.add(var4);
            }
        }

        Iterator var5 = var2.iterator();

        while (var5.hasNext())
        {
            var4 = (EntityPlayer)var5.next();
            var4.playerConnection.disconnect("You logged in from another location");
        }

        Object var6;

        if (this.server.M())
        {
            var6 = new DemoPlayerInteractManager(this.server.getWorldServer(0));
        }
        else
        {
            var6 = new PlayerInteractManager(this.server.getWorldServer(0));
        }

        return new EntityPlayer(this.server, this.server.getWorldServer(0), par1Str, (PlayerInteractManager)var6);
    }

    /**
     * Called on respawn
     */
    public EntityPlayer moveToWorld(EntityPlayer par1EntityPlayerMP, int par2, boolean par3)
    {
        par1EntityPlayerMP.p().getTracker().untrackPlayer(par1EntityPlayerMP);
        par1EntityPlayerMP.p().getTracker().untrackEntity(par1EntityPlayerMP);
        par1EntityPlayerMP.p().getPlayerChunkMap().removePlayer(par1EntityPlayerMP);
        this.players.remove(par1EntityPlayerMP);
        this.server.getWorldServer(par1EntityPlayerMP.dimension).removeEntity(par1EntityPlayerMP);
        ChunkCoordinates var4 = par1EntityPlayerMP.getBed();
        boolean var5 = par1EntityPlayerMP.isRespawnForced();
        par1EntityPlayerMP.dimension = par2;
        Object var6;

        if (this.server.M())
        {
            var6 = new DemoPlayerInteractManager(this.server.getWorldServer(par1EntityPlayerMP.dimension));
        }
        else
        {
            var6 = new PlayerInteractManager(this.server.getWorldServer(par1EntityPlayerMP.dimension));
        }

        EntityPlayer var7 = new EntityPlayer(this.server, this.server.getWorldServer(par1EntityPlayerMP.dimension), par1EntityPlayerMP.name, (PlayerInteractManager)var6);
        var7.playerConnection = par1EntityPlayerMP.playerConnection;
        var7.copyTo(par1EntityPlayerMP, par3);
        var7.id = par1EntityPlayerMP.id;
        WorldServer var8 = this.server.getWorldServer(par1EntityPlayerMP.dimension);
        this.a(var7, par1EntityPlayerMP, var8);
        ChunkCoordinates var9;

        if (var4 != null)
        {
            var9 = EntityHuman.getBed(this.server.getWorldServer(par1EntityPlayerMP.dimension), var4, var5);

            if (var9 != null)
            {
                var7.setPositionRotation((double) ((float) var9.x + 0.5F), (double) ((float) var9.y + 0.1F), (double) ((float) var9.z + 0.5F), 0.0F, 0.0F);
                var7.setRespawnPosition(var4, var5);
            }
            else
            {
                var7.playerConnection.sendPacket(new Packet70Bed(0, 0));
            }
        }

        var8.chunkProviderServer.getChunkAt((int) var7.locX >> 4, (int) var7.locZ >> 4);

        while (!var8.getCubes(var7, var7.boundingBox).isEmpty())
        {
            var7.setPosition(var7.locX, var7.locY + 1.0D, var7.locZ);
        }

        var7.playerConnection.sendPacket(new Packet9Respawn(var7.dimension, (byte) var7.world.difficulty, var7.world.getWorldData().getType(), var7.world.getHeight(), var7.playerInteractManager.getGameMode()));
        var9 = var8.getSpawn();
        var7.playerConnection.a(var7.locX, var7.locY, var7.locZ, var7.yaw, var7.pitch);
        var7.playerConnection.sendPacket(new Packet6SpawnPosition(var9.x, var9.y, var9.z));
        var7.playerConnection.sendPacket(new Packet43SetExperience(var7.exp, var7.expTotal, var7.expLevel));
        this.b(var7, var8);
        var8.getPlayerChunkMap().addPlayer(var7);
        var8.addEntity(var7);
        this.players.add(var7);
        var7.syncInventory();
        return var7;
    }

    /**
     * moves provided player from overworld to nether or vice versa
     */
    public void changeDimension(EntityPlayer par1EntityPlayerMP, int par2)
    {
        int var3 = par1EntityPlayerMP.dimension;
        WorldServer var4 = this.server.getWorldServer(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.dimension = par2;
        WorldServer var5 = this.server.getWorldServer(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.playerConnection.sendPacket(new Packet9Respawn(par1EntityPlayerMP.dimension, (byte) par1EntityPlayerMP.world.difficulty, var5.getWorldData().getType(), var5.getHeight(), par1EntityPlayerMP.playerInteractManager.getGameMode()));
        var4.removeEntity(par1EntityPlayerMP);
        par1EntityPlayerMP.dead = false;
        this.a(par1EntityPlayerMP, var3, var4, var5);
        this.a(par1EntityPlayerMP, var4);
        par1EntityPlayerMP.playerConnection.a(par1EntityPlayerMP.locX, par1EntityPlayerMP.locY, par1EntityPlayerMP.locZ, par1EntityPlayerMP.yaw, par1EntityPlayerMP.pitch);
        par1EntityPlayerMP.playerInteractManager.a(var5);
        this.b(par1EntityPlayerMP, var5);
        this.updateClient(par1EntityPlayerMP);
        Iterator var6 = par1EntityPlayerMP.getEffects().iterator();

        while (var6.hasNext())
        {
            MobEffect var7 = (MobEffect)var6.next();
            par1EntityPlayerMP.playerConnection.sendPacket(new Packet41MobEffect(par1EntityPlayerMP.id, var7));
        }
    }

    /**
     * Transfers an entity from a world to another world.
     */
    public void a(Entity par1Entity, int par2, WorldServer par3WorldServer, WorldServer par4WorldServer)
    {
        double var5 = par1Entity.locX;
        double var7 = par1Entity.locZ;
        double var9 = 8.0D;
        double var11 = par1Entity.locX;
        double var13 = par1Entity.locY;
        double var15 = par1Entity.locZ;
        float var17 = par1Entity.yaw;
        par3WorldServer.methodProfiler.a("moving");

        if (par1Entity.dimension == -1)
        {
            var5 /= var9;
            var7 /= var9;
            par1Entity.setPositionRotation(var5, par1Entity.locY, var7, par1Entity.yaw, par1Entity.pitch);

            if (par1Entity.isAlive())
            {
                par3WorldServer.entityJoinedWorld(par1Entity, false);
            }
        }
        else if (par1Entity.dimension == 0)
        {
            var5 *= var9;
            var7 *= var9;
            par1Entity.setPositionRotation(var5, par1Entity.locY, var7, par1Entity.yaw, par1Entity.pitch);

            if (par1Entity.isAlive())
            {
                par3WorldServer.entityJoinedWorld(par1Entity, false);
            }
        }
        else
        {
            ChunkCoordinates var18;

            if (par2 == 1)
            {
                var18 = par4WorldServer.getSpawn();
            }
            else
            {
                var18 = par4WorldServer.getDimensionSpawn();
            }

            var5 = (double)var18.x;
            par1Entity.locY = (double)var18.y;
            var7 = (double)var18.z;
            par1Entity.setPositionRotation(var5, par1Entity.locY, var7, 90.0F, 0.0F);

            if (par1Entity.isAlive())
            {
                par3WorldServer.entityJoinedWorld(par1Entity, false);
            }
        }

        par3WorldServer.methodProfiler.b();

        if (par2 != 1)
        {
            par3WorldServer.methodProfiler.a("placing");
            var5 = (double) MathHelper.a((int) var5, -29999872, 29999872);
            var7 = (double) MathHelper.a((int) var7, -29999872, 29999872);

            if (par1Entity.isAlive())
            {
                par4WorldServer.addEntity(par1Entity);
                par1Entity.setPositionRotation(var5, par1Entity.locY, var7, par1Entity.yaw, par1Entity.pitch);
                par4WorldServer.entityJoinedWorld(par1Entity, false);
                par4WorldServer.s().a(par1Entity, var11, var13, var15, var17);
            }

            par3WorldServer.methodProfiler.b();
        }

        par1Entity.spawnIn(par4WorldServer);
    }

    /**
     * self explanitory
     */
    public void tick()
    {
        if (++this.o > 600)
        {
            this.o = 0;
        }

        if (this.o < this.players.size())
        {
            EntityPlayer var1 = (EntityPlayer)this.players.get(this.o);
            this.sendAll(new Packet201PlayerInfo(var1.name, true, var1.ping));
        }
    }

    /**
     * sends a packet to all players
     */
    public void sendAll(Packet par1Packet)
    {
        for (int var2 = 0; var2 < this.players.size(); ++var2)
        {
            ((EntityPlayer)this.players.get(var2)).playerConnection.sendPacket(par1Packet);
        }
    }

    /**
     * Sends a packet to all players in the specified Dimension
     */
    public void a(Packet par1Packet, int par2)
    {
        for (int var3 = 0; var3 < this.players.size(); ++var3)
        {
            EntityPlayer var4 = (EntityPlayer)this.players.get(var3);

            if (var4.dimension == par2)
            {
                var4.playerConnection.sendPacket(par1Packet);
            }
        }
    }

    /**
     * returns a string containing a comma-seperated list of player names
     */
    public String c()
    {
        String var1 = "";

        for (int var2 = 0; var2 < this.players.size(); ++var2)
        {
            if (var2 > 0)
            {
                var1 = var1 + ", ";
            }

            var1 = var1 + ((EntityPlayer)this.players.get(var2)).name;
        }

        return var1;
    }

    /**
     * Returns an array of the usernames of all the connected players.
     */
    public String[] d()
    {
        String[] var1 = new String[this.players.size()];

        for (int var2 = 0; var2 < this.players.size(); ++var2)
        {
            var1[var2] = ((EntityPlayer)this.players.get(var2)).name;
        }

        return var1;
    }

    public BanList getNameBans()
    {
        return this.banByName;
    }

    public BanList getIPBans()
    {
        return this.banByIP;
    }

    /**
     * This adds a username to the ops list, then saves the op list
     */
    public void addOp(String par1Str)
    {
        this.operators.add(par1Str.toLowerCase());
    }

    /**
     * This removes a username from the ops list, then saves the op list
     */
    public void removeOp(String par1Str)
    {
        this.operators.remove(par1Str.toLowerCase());
    }

    /**
     * Determine if the player is allowed to connect based on current server settings.
     */
    public boolean isWhitelisted(String par1Str)
    {
        par1Str = par1Str.trim().toLowerCase();
        return !this.hasWhitelist || this.operators.contains(par1Str) || this.whitelist.contains(par1Str);
    }

    /**
     * Returns true if the specific player is allowed to use commands.
     */
    public boolean isOp(String par1Str)
    {
        return this.operators.contains(par1Str.trim().toLowerCase()) || this.server.I() && this.server.worldServer[0].getWorldData().allowCommands() && this.server.H().equalsIgnoreCase(par1Str) || this.n;
    }

    /**
     * gets the player entity for the player with the name specified
     */
    public EntityPlayer f(String par1Str)
    {
        Iterator var2 = this.players.iterator();
        EntityPlayer var3;

        do
        {
            if (!var2.hasNext())
            {
                return null;
            }

            var3 = (EntityPlayer)var2.next();
        }
        while (!var3.name.equalsIgnoreCase(par1Str));

        return var3;
    }

    /**
     * Find all players in a specified range and narrowing down by other parameters
     */
    public List a(ChunkCoordinates par1ChunkCoordinates, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        if (this.players.isEmpty())
        {
            return null;
        }
        else
        {
            Object var8 = new ArrayList();
            boolean var9 = par4 < 0;
            int var10 = par2 * par2;
            int var11 = par3 * par3;
            par4 = MathHelper.a(par4);

            for (int var12 = 0; var12 < this.players.size(); ++var12)
            {
                EntityPlayer var13 = (EntityPlayer)this.players.get(var12);

                if (par1ChunkCoordinates != null && (par2 > 0 || par3 > 0))
                {
                    float var14 = par1ChunkCoordinates.e(var13.b());

                    if (par2 > 0 && var14 < (float)var10 || par3 > 0 && var14 > (float)var11)
                    {
                        continue;
                    }
                }

                if ((par5 == EnumGamemode.NONE.a() || par5 == var13.playerInteractManager.getGameMode().a()) && (par6 <= 0 || var13.expLevel >= par6) && var13.expLevel <= par7)
                {
                    ((List)var8).add(var13);
                }
            }

            if (par1ChunkCoordinates != null)
            {
                Collections.sort((List)var8, new PlayerDistanceComparator(par1ChunkCoordinates));
            }

            if (var9)
            {
                Collections.reverse((List)var8);
            }

            if (par4 > 0)
            {
                var8 = ((List)var8).subList(0, Math.min(par4, ((List)var8).size()));
            }

            return (List)var8;
        }
    }

    /**
     * sends a packet to players within d3 of point (x,y,z)
     */
    public void sendPacketNearby(double par1, double par3, double par5, double par7, int par9, Packet par10Packet)
    {
        this.sendPacketNearby((EntityHuman) null, par1, par3, par5, par7, par9, par10Packet);
    }

    /**
     * params: srcPlayer,x,y,z,d,dimension. The packet is not sent to the srcPlayer, but all other players where
     * dx*dx+dy*dy+dz*dz<d*d
     */
    public void sendPacketNearby(EntityHuman par1EntityPlayer, double par2, double par4, double par6, double par8, int par10, Packet par11Packet)
    {
        for (int var12 = 0; var12 < this.players.size(); ++var12)
        {
            EntityPlayer var13 = (EntityPlayer)this.players.get(var12);

            if (var13 != par1EntityPlayer && var13.dimension == par10)
            {
                double var14 = par2 - var13.locX;
                double var16 = par4 - var13.locY;
                double var18 = par6 - var13.locZ;

                if (var14 * var14 + var16 * var16 + var18 * var18 < par8 * par8)
                {
                    var13.playerConnection.sendPacket(par11Packet);
                }
            }
        }
    }

    /**
     * Saves all of the players' current states.
     */
    public void savePlayers()
    {
        for (int var1 = 0; var1 < this.players.size(); ++var1)
        {
            this.b((EntityPlayer) this.players.get(var1));
        }
    }

    /**
     * Add the specified player to the white list.
     */
    public void addWhitelist(String par1Str)
    {
        this.whitelist.add(par1Str);
    }

    /**
     * Remove the specified player from the whitelist.
     */
    public void removeWhitelist(String par1Str)
    {
        this.whitelist.remove(par1Str);
    }

    /**
     * Returns the whitelisted players.
     */
    public Set getWhitelisted()
    {
        return this.whitelist;
    }

    public Set getOPs()
    {
        return this.operators;
    }

    /**
     * Either does nothing, or calls readWhiteList.
     */
    public void reloadWhitelist() {}

    /**
     * Updates the time and weather for the given player to those of the given world
     */
    public void b(EntityPlayer par1EntityPlayerMP, WorldServer par2WorldServer)
    {
        par1EntityPlayerMP.playerConnection.sendPacket(new Packet4UpdateTime(par2WorldServer.getTime(), par2WorldServer.getDayTime()));

        if (par2WorldServer.N())
        {
            par1EntityPlayerMP.playerConnection.sendPacket(new Packet70Bed(1, 0));
        }
    }

    /**
     * sends the players inventory to himself
     */
    public void updateClient(EntityPlayer par1EntityPlayerMP)
    {
        par1EntityPlayerMP.updateInventory(par1EntityPlayerMP.defaultContainer);
        par1EntityPlayerMP.m();
        par1EntityPlayerMP.playerConnection.sendPacket(new Packet16BlockItemSwitch(par1EntityPlayerMP.inventory.itemInHandIndex));
    }

    /**
     * Returns the number of players currently on the server.
     */
    public int getPlayerCount()
    {
        return this.players.size();
    }

    /**
     * Returns the maximum number of players allowed on the server.
     */
    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    /**
     * Returns an array of usernames for which player.dat exists for.
     */
    public String[] getSeenPlayers()
    {
        return this.server.worldServer[0].getDataManager().getPlayerFileData().getSeenPlayers();
    }

    public boolean getHasWhitelist()
    {
        return this.hasWhitelist;
    }

    public void setHasWhitelist(boolean par1)
    {
        this.hasWhitelist = par1;
    }

    public List j(String par1Str)
    {
        ArrayList var2 = new ArrayList();
        Iterator var3 = this.players.iterator();

        while (var3.hasNext())
        {
            EntityPlayer var4 = (EntityPlayer)var3.next();

            if (var4.q().equals(par1Str))
            {
                var2.add(var4);
            }
        }

        return var2;
    }

    /**
     * Gets the View Distance.
     */
    public int o()
    {
        return this.d;
    }

    public MinecraftServer getServer()
    {
        return this.server;
    }

    /**
     * gets the tags created in the last writePlayerData call
     */
    public NBTTagCompound q()
    {
        return null;
    }

    private void a(EntityPlayer par1EntityPlayerMP, EntityPlayer par2EntityPlayerMP, World par3World)
    {
        if (par2EntityPlayerMP != null)
        {
            par1EntityPlayerMP.playerInteractManager.setGameMode(par2EntityPlayerMP.playerInteractManager.getGameMode());
        }
        else if (this.m != null)
        {
            par1EntityPlayerMP.playerInteractManager.setGameMode(this.m);
        }

        par1EntityPlayerMP.playerInteractManager.b(par3World.getWorldData().getGameType());
    }

    /**
     * Kicks everyone with "Server closed" as reason.
     */
    public void r()
    {
        while (!this.players.isEmpty())
        {
            ((EntityPlayer)this.players.get(0)).playerConnection.disconnect("Server closed");
        }
    }

    public void k(String par1Str)
    {
        this.server.info(par1Str);
        this.sendAll(new Packet3Chat(par1Str));
    }
}
