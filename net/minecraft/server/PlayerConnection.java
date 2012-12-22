package net.minecraft.server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;

public class PlayerConnection extends Connection
{
    /** The logging system. */
    public static Logger logger = Logger.getLogger("Minecraft");

    /** The underlying network manager for this server handler. */
    public INetworkManager networkManager;

    /** This is set to true whenever a player disconnects from the server. */
    public boolean disconnected = false;

    /** Reference to the MinecraftServer object. */
    private MinecraftServer minecraftServer;

    /** Reference to the EntityPlayerMP object. */
    public EntityPlayer player;

    /** incremented each tick */
    private int f;

    /** holds the amount of tick the player is floating */
    private int g;
    private boolean h;
    private int i;
    private long j;

    /** The Java Random object. */
    private static Random k = new Random();
    private long l;
    private int m = 0;
    private int x = 0;

    /** The last known x position for this connection. */
    private double y;

    /** The last known y position for this connection. */
    private double z;

    /** The last known z position for this connection. */
    private double q;

    /** is true when the player has moved since his last movement packet */
    private boolean checkMovement = true;
    private IntHashMap s = new IntHashMap();

    public PlayerConnection(MinecraftServer par1, INetworkManager par2, EntityPlayer par3)
    {
        this.minecraftServer = par1;
        this.networkManager = par2;
        par2.a(this);
        this.player = par3;
        par3.playerConnection = this;
    }

    /**
     * handle all the packets for the connection
     */
    public void d()
    {
        this.h = false;
        ++this.f;
        this.minecraftServer.methodProfiler.a("packetflow");
        this.networkManager.b();
        this.minecraftServer.methodProfiler.c("keepAlive");

        if ((long)this.f - this.l > 20L)
        {
            this.l = (long)this.f;
            this.j = System.nanoTime() / 1000000L;
            this.i = k.nextInt();
            this.sendPacket(new Packet0KeepAlive(this.i));
        }

        if (this.m > 0)
        {
            --this.m;
        }

        if (this.x > 0)
        {
            --this.x;
        }

        this.minecraftServer.methodProfiler.c("playerTick");
        this.minecraftServer.methodProfiler.b();
    }

    /**
     * Kick the offending player and give a reason why
     */
    public void disconnect(String par1Str)
    {
        if (!this.disconnected)
        {
            this.player.l();
            this.sendPacket(new Packet255KickDisconnect(par1Str));
            this.networkManager.d();
            this.minecraftServer.getPlayerList().sendAll(new Packet3Chat("\u00a7e" + this.player.name + " left the game."));
            this.minecraftServer.getPlayerList().disconnect(this.player);
            this.disconnected = true;
        }
    }

    public void a(Packet10Flying par1Packet10Flying)
    {
        WorldServer var2 = this.minecraftServer.getWorldServer(this.player.dimension);
        this.h = true;

        if (!this.player.viewingCredits)
        {
            double var3;

            if (!this.checkMovement)
            {
                var3 = par1Packet10Flying.y - this.z;

                if (par1Packet10Flying.x == this.y && var3 * var3 < 0.01D && par1Packet10Flying.z == this.q)
                {
                    this.checkMovement = true;
                }
            }

            if (this.checkMovement)
            {
                double var5;
                double var7;
                double var9;
                double var13;

                if (this.player.vehicle != null)
                {
                    float var34 = this.player.yaw;
                    float var4 = this.player.pitch;
                    this.player.vehicle.V();
                    var5 = this.player.locX;
                    var7 = this.player.locY;
                    var9 = this.player.locZ;
                    double var35 = 0.0D;
                    var13 = 0.0D;

                    if (par1Packet10Flying.hasLook)
                    {
                        var34 = par1Packet10Flying.yaw;
                        var4 = par1Packet10Flying.pitch;
                    }

                    if (par1Packet10Flying.hasPos && par1Packet10Flying.y == -999.0D && par1Packet10Flying.stance == -999.0D)
                    {
                        if (Math.abs(par1Packet10Flying.x) > 1.0D || Math.abs(par1Packet10Flying.z) > 1.0D)
                        {
                            System.err.println(this.player.name + " was caught trying to crash the server with an invalid position.");
                            this.disconnect("Nope!");
                            return;
                        }

                        var35 = par1Packet10Flying.x;
                        var13 = par1Packet10Flying.z;
                    }

                    this.player.onGround = par1Packet10Flying.g;
                    this.player.g();
                    this.player.move(var35, 0.0D, var13);
                    this.player.setLocation(var5, var7, var9, var34, var4);
                    this.player.motX = var35;
                    this.player.motZ = var13;

                    if (this.player.vehicle != null)
                    {
                        var2.vehicleEnteredWorld(this.player.vehicle, true);
                    }

                    if (this.player.vehicle != null)
                    {
                        this.player.vehicle.V();
                    }

                    this.minecraftServer.getPlayerList().d(this.player);
                    this.y = this.player.locX;
                    this.z = this.player.locY;
                    this.q = this.player.locZ;
                    var2.playerJoinedWorld(this.player);
                    return;
                }

                if (this.player.isSleeping())
                {
                    this.player.g();
                    this.player.setLocation(this.y, this.z, this.q, this.player.yaw, this.player.pitch);
                    var2.playerJoinedWorld(this.player);
                    return;
                }

                var3 = this.player.locY;
                this.y = this.player.locX;
                this.z = this.player.locY;
                this.q = this.player.locZ;
                var5 = this.player.locX;
                var7 = this.player.locY;
                var9 = this.player.locZ;
                float var11 = this.player.yaw;
                float var12 = this.player.pitch;

                if (par1Packet10Flying.hasPos && par1Packet10Flying.y == -999.0D && par1Packet10Flying.stance == -999.0D)
                {
                    par1Packet10Flying.hasPos = false;
                }

                if (par1Packet10Flying.hasPos)
                {
                    var5 = par1Packet10Flying.x;
                    var7 = par1Packet10Flying.y;
                    var9 = par1Packet10Flying.z;
                    var13 = par1Packet10Flying.stance - par1Packet10Flying.y;

                    if (!this.player.isSleeping() && (var13 > 1.65D || var13 < 0.1D))
                    {
                        this.disconnect("Illegal stance");
                        logger.warning(this.player.name + " had an illegal stance: " + var13);
                        return;
                    }

                    if (Math.abs(par1Packet10Flying.x) > 3.2E7D || Math.abs(par1Packet10Flying.z) > 3.2E7D)
                    {
                        this.disconnect("Illegal position");
                        return;
                    }
                }

                if (par1Packet10Flying.hasLook)
                {
                    var11 = par1Packet10Flying.yaw;
                    var12 = par1Packet10Flying.pitch;
                }

                this.player.g();
                this.player.W = 0.0F;
                this.player.setLocation(this.y, this.z, this.q, var11, var12);

                if (!this.checkMovement)
                {
                    return;
                }

                var13 = var5 - this.player.locX;
                double var15 = var7 - this.player.locY;
                double var17 = var9 - this.player.locZ;
                double var19 = Math.min(Math.abs(var13), Math.abs(this.player.motX));
                double var21 = Math.min(Math.abs(var15), Math.abs(this.player.motY));
                double var23 = Math.min(Math.abs(var17), Math.abs(this.player.motZ));
                double var25 = var19 * var19 + var21 * var21 + var23 * var23;

                if (var25 > 100.0D && (!this.minecraftServer.I() || !this.minecraftServer.H().equals(this.player.name)))
                {
                    logger.warning(this.player.name + " moved too quickly! " + var13 + "," + var15 + "," + var17 + " (" + var19 + ", " + var21 + ", " + var23 + ")");
                    this.a(this.y, this.z, this.q, this.player.yaw, this.player.pitch);
                    return;
                }

                float var27 = 0.0625F;
                boolean var28 = var2.getCubes(this.player, this.player.boundingBox.clone().shrink((double) var27, (double) var27, (double) var27)).isEmpty();

                if (this.player.onGround && !par1Packet10Flying.g && var15 > 0.0D)
                {
                    this.player.j(0.2F);
                }

                this.player.move(var13, var15, var17);
                this.player.onGround = par1Packet10Flying.g;
                this.player.checkMovement(var13, var15, var17);
                double var29 = var15;
                var13 = var5 - this.player.locX;
                var15 = var7 - this.player.locY;

                if (var15 > -0.5D || var15 < 0.5D)
                {
                    var15 = 0.0D;
                }

                var17 = var9 - this.player.locZ;
                var25 = var13 * var13 + var15 * var15 + var17 * var17;
                boolean var31 = false;

                if (var25 > 0.0625D && !this.player.isSleeping() && !this.player.playerInteractManager.isCreative())
                {
                    var31 = true;
                    logger.warning(this.player.name + " moved wrongly!");
                }

                this.player.setLocation(var5, var7, var9, var11, var12);
                boolean var32 = var2.getCubes(this.player, this.player.boundingBox.clone().shrink((double) var27, (double) var27, (double) var27)).isEmpty();

                if (var28 && (var31 || !var32) && !this.player.isSleeping())
                {
                    this.a(this.y, this.z, this.q, var11, var12);
                    return;
                }

                AxisAlignedBB var33 = this.player.boundingBox.clone().grow((double) var27, (double) var27, (double) var27).a(0.0D, -0.55D, 0.0D);

                if (!this.minecraftServer.getAllowFlight() && !this.player.playerInteractManager.isCreative() && !var2.c(var33))
                {
                    if (var29 >= -0.03125D)
                    {
                        ++this.g;

                        if (this.g > 80)
                        {
                            logger.warning(this.player.name + " was kicked for floating too long!");
                            this.disconnect("Flying is not enabled on this server");
                            return;
                        }
                    }
                }
                else
                {
                    this.g = 0;
                }

                this.player.onGround = par1Packet10Flying.g;
                this.minecraftServer.getPlayerList().d(this.player);
                this.player.b(this.player.locY - var3, par1Packet10Flying.g);
            }
        }
    }

    /**
     * Moves the player to the specified destination and rotation
     */
    public void a(double par1, double par3, double par5, float par7, float par8)
    {
        this.checkMovement = false;
        this.y = par1;
        this.z = par3;
        this.q = par5;
        this.player.setLocation(par1, par3, par5, par7, par8);
        this.player.playerConnection.sendPacket(new Packet13PlayerLookMove(par1, par3 + 1.6200000047683716D, par3, par5, par7, par8, false));
    }

    public void a(Packet14BlockDig par1Packet14BlockDig)
    {
        WorldServer var2 = this.minecraftServer.getWorldServer(this.player.dimension);

        if (par1Packet14BlockDig.e == 4)
        {
            this.player.f(false);
        }
        else if (par1Packet14BlockDig.e == 3)
        {
            this.player.f(true);
        }
        else if (par1Packet14BlockDig.e == 5)
        {
            this.player.bO();
        }
        else
        {
            int var3 = this.minecraftServer.getSpawnProtection();
            boolean var4 = var2.worldProvider.dimension != 0 || this.minecraftServer.getPlayerList().getOPs().isEmpty() || this.minecraftServer.getPlayerList().isOp(this.player.name) || var3 <= 0 || this.minecraftServer.I();
            boolean var5 = false;

            if (par1Packet14BlockDig.e == 0)
            {
                var5 = true;
            }

            if (par1Packet14BlockDig.e == 1)
            {
                var5 = true;
            }

            if (par1Packet14BlockDig.e == 2)
            {
                var5 = true;
            }

            int var6 = par1Packet14BlockDig.a;
            int var7 = par1Packet14BlockDig.b;
            int var8 = par1Packet14BlockDig.c;

            if (var5)
            {
                double var9 = this.player.locX - ((double)var6 + 0.5D);
                double var11 = this.player.locY - ((double)var7 + 0.5D) + 1.5D;
                double var13 = this.player.locZ - ((double)var8 + 0.5D);
                double var15 = var9 * var9 + var11 * var11 + var13 * var13;

                if (var15 > 36.0D)
                {
                    return;
                }

                if (var7 >= this.minecraftServer.getMaxBuildHeight())
                {
                    return;
                }
            }

            ChunkCoordinates var17 = var2.getSpawn();
            int var10 = MathHelper.a(var6 - var17.x);
            int var18 = MathHelper.a(var8 - var17.z);

            if (var10 > var18)
            {
                var18 = var10;
            }

            if (par1Packet14BlockDig.e == 0)
            {
                if (var18 <= var3 && !var4)
                {
                    this.player.playerConnection.sendPacket(new Packet53BlockChange(var6, var7, var8, var2));
                }
                else
                {
                    this.player.playerInteractManager.dig(var6, var7, var8, par1Packet14BlockDig.face);
                }
            }
            else if (par1Packet14BlockDig.e == 2)
            {
                this.player.playerInteractManager.a(var6, var7, var8);

                if (var2.getTypeId(var6, var7, var8) != 0)
                {
                    this.player.playerConnection.sendPacket(new Packet53BlockChange(var6, var7, var8, var2));
                }
            }
            else if (par1Packet14BlockDig.e == 1)
            {
                this.player.playerInteractManager.c(var6, var7, var8);

                if (var2.getTypeId(var6, var7, var8) != 0)
                {
                    this.player.playerConnection.sendPacket(new Packet53BlockChange(var6, var7, var8, var2));
                }
            }
        }
    }

    public void a(Packet15Place par1Packet15Place)
    {
        WorldServer var2 = this.minecraftServer.getWorldServer(this.player.dimension);
        ItemStack var3 = this.player.inventory.getItemInHand();
        boolean var4 = false;
        int var5 = par1Packet15Place.d();
        int var6 = par1Packet15Place.f();
        int var7 = par1Packet15Place.g();
        int var8 = par1Packet15Place.getFace();
        int var9 = this.minecraftServer.getSpawnProtection();
        boolean var10 = var2.worldProvider.dimension != 0 || this.minecraftServer.getPlayerList().getOPs().isEmpty() || this.minecraftServer.getPlayerList().isOp(this.player.name) || var9 <= 0 || this.minecraftServer.I();

        if (par1Packet15Place.getFace() == 255)
        {
            if (var3 == null)
            {
                return;
            }

            this.player.playerInteractManager.useItem(this.player, var2, var3);
        }
        else if (par1Packet15Place.f() >= this.minecraftServer.getMaxBuildHeight() - 1 && (par1Packet15Place.getFace() == 1 || par1Packet15Place.f() >= this.minecraftServer.getMaxBuildHeight()))
        {
            this.player.playerConnection.sendPacket(new Packet3Chat("\u00a77Height limit for building is " + this.minecraftServer.getMaxBuildHeight()));
            var4 = true;
        }
        else
        {
            ChunkCoordinates var11 = var2.getSpawn();
            int var12 = MathHelper.a(var5 - var11.x);
            int var13 = MathHelper.a(var7 - var11.z);

            if (var12 > var13)
            {
                var13 = var12;
            }

            if (this.checkMovement && this.player.e((double) var5 + 0.5D, (double) var6 + 0.5D, (double) var7 + 0.5D) < 64.0D && (var13 > var9 || var10))
            {
                this.player.playerInteractManager.interact(this.player, var2, var3, var5, var6, var7, var8, par1Packet15Place.j(), par1Packet15Place.l(), par1Packet15Place.m());
            }

            var4 = true;
        }

        if (var4)
        {
            this.player.playerConnection.sendPacket(new Packet53BlockChange(var5, var6, var7, var2));

            if (var8 == 0)
            {
                --var6;
            }

            if (var8 == 1)
            {
                ++var6;
            }

            if (var8 == 2)
            {
                --var7;
            }

            if (var8 == 3)
            {
                ++var7;
            }

            if (var8 == 4)
            {
                --var5;
            }

            if (var8 == 5)
            {
                ++var5;
            }

            this.player.playerConnection.sendPacket(new Packet53BlockChange(var5, var6, var7, var2));
        }

        var3 = this.player.inventory.getItemInHand();

        if (var3 != null && var3.count == 0)
        {
            this.player.inventory.items[this.player.inventory.itemInHandIndex] = null;
            var3 = null;
        }

        if (var3 == null || var3.m() == 0)
        {
            this.player.h = true;
            this.player.inventory.items[this.player.inventory.itemInHandIndex] = ItemStack.b(this.player.inventory.items[this.player.inventory.itemInHandIndex]);
            Slot var14 = this.player.activeContainer.a(this.player.inventory, this.player.inventory.itemInHandIndex);
            this.player.activeContainer.b();
            this.player.h = false;

            if (!ItemStack.matches(this.player.inventory.getItemInHand(), par1Packet15Place.getItemStack()))
            {
                this.sendPacket(new Packet103SetSlot(this.player.activeContainer.windowId, var14.g, this.player.inventory.getItemInHand()));
            }
        }
    }

    public void a(String par1Str, Object[] par2ArrayOfObj)
    {
        logger.info(this.player.name + " lost connection: " + par1Str);
        this.minecraftServer.getPlayerList().sendAll(new Packet3Chat("\u00a7e" + this.player.name + " left the game."));
        this.minecraftServer.getPlayerList().disconnect(this.player);
        this.disconnected = true;

        if (this.minecraftServer.I() && this.player.name.equals(this.minecraftServer.H()))
        {
            logger.info("Stopping singleplayer server as player logged out");
            this.minecraftServer.safeShutdown();
        }
    }

    /**
     * Default handler called for packets that don't have their own handlers in NetServerHandler; kicks player from the
     * server.
     */
    public void onUnhandledPacket(Packet par1Packet)
    {
        logger.warning(this.getClass() + " wasn\'t prepared to deal with a " + par1Packet.getClass());
        this.disconnect("Protocol error, unexpected packet");
    }

    /**
     * Adds the packet to the underlying network manager's send queue.
     */
    public void sendPacket(Packet par1Packet)
    {
        if (par1Packet instanceof Packet3Chat)
        {
            Packet3Chat var2 = (Packet3Chat)par1Packet;
            int var3 = this.player.getChatFlags();

            if (var3 == 2)
            {
                return;
            }

            if (var3 == 1 && !var2.isServer())
            {
                return;
            }
        }

        this.networkManager.queue(par1Packet);
    }

    public void a(Packet16BlockItemSwitch par1Packet16BlockItemSwitch)
    {
        if (par1Packet16BlockItemSwitch.itemInHandIndex >= 0 && par1Packet16BlockItemSwitch.itemInHandIndex < PlayerInventory.getHotbarSize())
        {
            this.player.inventory.itemInHandIndex = par1Packet16BlockItemSwitch.itemInHandIndex;
        }
        else
        {
            logger.warning(this.player.name + " tried to set an invalid carried item");
        }
    }

    public void a(Packet3Chat par1Packet3Chat)
    {
        if (this.player.getChatFlags() == 2)
        {
            this.sendPacket(new Packet3Chat("Cannot send chat message."));
        }
        else
        {
            String var2 = par1Packet3Chat.message;

            if (var2.length() > 100)
            {
                this.disconnect("Chat message too long");
            }
            else
            {
                var2 = var2.trim();

                for (int var3 = 0; var3 < var2.length(); ++var3)
                {
                    if (!SharedConstants.isAllowedChatCharacter(var2.charAt(var3)))
                    {
                        this.disconnect("Illegal characters in chat");
                        return;
                    }
                }

                if (var2.startsWith("/"))
                {
                    this.handleCommand(var2);
                }
                else
                {
                    if (this.player.getChatFlags() == 1)
                    {
                        this.sendPacket(new Packet3Chat("Cannot send chat message."));
                        return;
                    }

                    var2 = "<" + this.player.name + "> " + var2;
                    logger.info(var2);
                    this.minecraftServer.getPlayerList().sendAll(new Packet3Chat(var2, false));
                }

                this.m += 20;

                if (this.m > 200 && !this.minecraftServer.getPlayerList().isOp(this.player.name))
                {
                    this.disconnect("disconnect.spam");
                }
            }
        }
    }

    /**
     * Processes a / command
     */
    private void handleCommand(String par1Str)
    {
        this.minecraftServer.getCommandHandler().a(this.player, par1Str);
    }

    public void a(Packet18ArmAnimation par1Packet18Animation)
    {
        if (par1Packet18Animation.b == 1)
        {
            this.player.bH();
        }
    }

    /**
     * runs registerPacket on the given Packet19EntityAction
     */
    public void a(Packet19EntityAction par1Packet19EntityAction)
    {
        if (par1Packet19EntityAction.animation == 1)
        {
            this.player.setSneaking(true);
        }
        else if (par1Packet19EntityAction.animation == 2)
        {
            this.player.setSneaking(false);
        }
        else if (par1Packet19EntityAction.animation == 4)
        {
            this.player.setSprinting(true);
        }
        else if (par1Packet19EntityAction.animation == 5)
        {
            this.player.setSprinting(false);
        }
        else if (par1Packet19EntityAction.animation == 3)
        {
            this.player.a(false, true, true);
            this.checkMovement = false;
        }
    }

    public void a(Packet255KickDisconnect par1Packet255KickDisconnect)
    {
        this.networkManager.a("disconnect.quitting", new Object[0]);
    }

    /**
     * return the number of chuckDataPackets from the netManager
     */
    public int lowPriorityCount()
    {
        return this.networkManager.e();
    }

    public void a(Packet7UseEntity par1Packet7UseEntity)
    {
        WorldServer var2 = this.minecraftServer.getWorldServer(this.player.dimension);
        Entity var3 = var2.getEntity(par1Packet7UseEntity.target);

        if (var3 != null)
        {
            boolean var4 = this.player.n(var3);
            double var5 = 36.0D;

            if (!var4)
            {
                var5 = 9.0D;
            }

            if (this.player.e(var3) < var5)
            {
                if (par1Packet7UseEntity.action == 0)
                {
                    this.player.p(var3);
                }
                else if (par1Packet7UseEntity.action == 1)
                {
                    this.player.attack(var3);
                }
            }
        }
    }

    public void a(Packet205ClientCommand par1Packet205ClientCommand)
    {
        if (par1Packet205ClientCommand.a == 1)
        {
            if (this.player.viewingCredits)
            {
                this.player = this.minecraftServer.getPlayerList().moveToWorld(this.player, 0, true);
            }
            else if (this.player.p().getWorldData().isHardcore())
            {
                if (this.minecraftServer.I() && this.player.name.equals(this.minecraftServer.H()))
                {
                    this.player.playerConnection.disconnect("You have died. Game over, man, it\'s game over!");
                    this.minecraftServer.P();
                }
                else
                {
                    BanEntry var2 = new BanEntry(this.player.name);
                    var2.setReason("Death in Hardcore");
                    this.minecraftServer.getPlayerList().getNameBans().add(var2);
                    this.player.playerConnection.disconnect("You have died. Game over, man, it\'s game over!");
                }
            }
            else
            {
                if (this.player.getHealth() > 0)
                {
                    return;
                }

                this.player = this.minecraftServer.getPlayerList().moveToWorld(this.player, 0, false);
            }
        }
    }

    /**
     * If this returns false, all packets will be queued for the main thread to handle, even if they would otherwise be
     * processed asynchronously. Used to avoid processing packets on the client before the world has been downloaded
     * (which happens on the main thread)
     */
    public boolean b()
    {
        return true;
    }

    /**
     * respawns the player
     */
    public void a(Packet9Respawn par1Packet9Respawn) {}

    public void handleContainerClose(Packet101CloseWindow par1Packet101CloseWindow)
    {
        this.player.k();
    }

    public void a(Packet102WindowClick par1Packet102WindowClick)
    {
        if (this.player.activeContainer.windowId == par1Packet102WindowClick.a && this.player.activeContainer.c(this.player))
        {
            ItemStack var2 = this.player.activeContainer.clickItem(par1Packet102WindowClick.slot, par1Packet102WindowClick.button, par1Packet102WindowClick.shift, this.player);

            if (ItemStack.matches(par1Packet102WindowClick.item, var2))
            {
                this.player.playerConnection.sendPacket(new Packet106Transaction(par1Packet102WindowClick.a, par1Packet102WindowClick.d, true));
                this.player.h = true;
                this.player.activeContainer.b();
                this.player.broadcastCarriedItem();
                this.player.h = false;
            }
            else
            {
                this.s.a(this.player.activeContainer.windowId, Short.valueOf(par1Packet102WindowClick.d));
                this.player.playerConnection.sendPacket(new Packet106Transaction(par1Packet102WindowClick.a, par1Packet102WindowClick.d, false));
                this.player.activeContainer.a(this.player, false);
                ArrayList var3 = new ArrayList();

                for (int var4 = 0; var4 < this.player.activeContainer.c.size(); ++var4)
                {
                    var3.add(((Slot)this.player.activeContainer.c.get(var4)).getItem());
                }

                this.player.a(this.player.activeContainer, var3);
            }
        }
    }

    public void a(Packet108ButtonClick par1Packet108EnchantItem)
    {
        if (this.player.activeContainer.windowId == par1Packet108EnchantItem.a && this.player.activeContainer.c(this.player))
        {
            this.player.activeContainer.a(this.player, par1Packet108EnchantItem.b);
            this.player.activeContainer.b();
        }
    }

    /**
     * Handle a creative slot packet.
     */
    public void a(Packet107SetCreativeSlot par1Packet107CreativeSetSlot)
    {
        if (this.player.playerInteractManager.isCreative())
        {
            boolean var2 = par1Packet107CreativeSetSlot.slot < 0;
            ItemStack var3 = par1Packet107CreativeSetSlot.b;
            boolean var4 = par1Packet107CreativeSetSlot.slot >= 1 && par1Packet107CreativeSetSlot.slot < 36 + PlayerInventory.getHotbarSize();
            boolean var5 = var3 == null || var3.id < Item.byId.length && var3.id >= 0 && Item.byId[var3.id] != null;
            boolean var6 = var3 == null || var3.getData() >= 0 && var3.getData() >= 0 && var3.count <= 64 && var3.count > 0;

            if (var4 && var5 && var6)
            {
                if (var3 == null)
                {
                    this.player.defaultContainer.setItem(par1Packet107CreativeSetSlot.slot, (ItemStack) null);
                }
                else
                {
                    this.player.defaultContainer.setItem(par1Packet107CreativeSetSlot.slot, var3);
                }

                this.player.defaultContainer.a(this.player, true);
            }
            else if (var2 && var5 && var6 && this.x < 200)
            {
                this.x += 20;
                EntityItem var7 = this.player.drop(var3);

                if (var7 != null)
                {
                    var7.c();
                }
            }
        }
    }

    public void a(Packet106Transaction par1Packet106Transaction)
    {
        Short var2 = (Short)this.s.get(this.player.activeContainer.windowId);

        if (var2 != null && par1Packet106Transaction.b == var2.shortValue() && this.player.activeContainer.windowId == par1Packet106Transaction.a && !this.player.activeContainer.c(this.player))
        {
            this.player.activeContainer.a(this.player, true);
        }
    }

    /**
     * Updates Client side signs
     */
    public void a(Packet130UpdateSign par1Packet130UpdateSign)
    {
        WorldServer var2 = this.minecraftServer.getWorldServer(this.player.dimension);

        if (var2.isLoaded(par1Packet130UpdateSign.x, par1Packet130UpdateSign.y, par1Packet130UpdateSign.z))
        {
            TileEntity var3 = var2.getTileEntity(par1Packet130UpdateSign.x, par1Packet130UpdateSign.y, par1Packet130UpdateSign.z);

            if (var3 instanceof TileEntitySign)
            {
                TileEntitySign var4 = (TileEntitySign)var3;

                if (!var4.a())
                {
                    this.minecraftServer.warning("Player " + this.player.name + " just tried to change non-editable sign");
                    return;
                }
            }

            int var6;
            int var8;

            for (var8 = 0; var8 < 4; ++var8)
            {
                boolean var5 = true;

                if (par1Packet130UpdateSign.lines[var8].length() > 15)
                {
                    var5 = false;
                }
                else
                {
                    for (var6 = 0; var6 < par1Packet130UpdateSign.lines[var8].length(); ++var6)
                    {
                        if (SharedConstants.allowedCharacters.indexOf(par1Packet130UpdateSign.lines[var8].charAt(var6)) < 0)
                        {
                            var5 = false;
                        }
                    }
                }

                if (!var5)
                {
                    par1Packet130UpdateSign.lines[var8] = "!?";
                }
            }

            if (var3 instanceof TileEntitySign)
            {
                var8 = par1Packet130UpdateSign.x;
                int var9 = par1Packet130UpdateSign.y;
                var6 = par1Packet130UpdateSign.z;
                TileEntitySign var7 = (TileEntitySign)var3;
                System.arraycopy(par1Packet130UpdateSign.lines, 0, var7.lines, 0, 4);
                var7.update();
                var2.notify(var8, var9, var6);
            }
        }
    }

    /**
     * Handle a keep alive packet.
     */
    public void a(Packet0KeepAlive par1Packet0KeepAlive)
    {
        if (par1Packet0KeepAlive.a == this.i)
        {
            int var2 = (int)(System.nanoTime() / 1000000L - this.j);
            this.player.ping = (this.player.ping * 3 + var2) / 4;
        }
    }

    /**
     * determine if it is a server handler
     */
    public boolean a()
    {
        return true;
    }

    /**
     * Handle a player abilities packet.
     */
    public void a(Packet202Abilities par1Packet202PlayerAbilities)
    {
        this.player.abilities.isFlying = par1Packet202PlayerAbilities.f() && this.player.abilities.canFly;
    }

    public void a(Packet203TabComplete par1Packet203AutoComplete)
    {
        StringBuilder var2 = new StringBuilder();
        String var4;

        for (Iterator var3 = this.minecraftServer.a(this.player, par1Packet203AutoComplete.d()).iterator(); var3.hasNext(); var2.append(var4))
        {
            var4 = (String)var3.next();

            if (var2.length() > 0)
            {
                var2.append("\u0000");
            }
        }

        this.player.playerConnection.sendPacket(new Packet203TabComplete(var2.toString()));
    }

    public void a(Packet204LocaleAndViewDistance par1Packet204ClientInfo)
    {
        this.player.a(par1Packet204ClientInfo);
    }

    public void a(Packet250CustomPayload par1Packet250CustomPayload)
    {
        DataInputStream var2;
        ItemStack var3;
        ItemStack var4;

        if ("MC|BEdit".equals(par1Packet250CustomPayload.tag))
        {
            try
            {
                var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                var3 = Packet.c(var2);

                if (!ItemBookAndQuill.a(var3.getTag()))
                {
                    throw new IOException("Invalid book tag!");
                }

                var4 = this.player.inventory.getItemInHand();

                if (var3 != null && var3.id == Item.BOOK_AND_QUILL.id && var3.id == var4.id)
                {
                    var4.a("pages", var3.getTag().getList("pages"));
                }
            }
            catch (Exception var12)
            {
                var12.printStackTrace();
            }
        }
        else if ("MC|BSign".equals(par1Packet250CustomPayload.tag))
        {
            try
            {
                var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                var3 = Packet.c(var2);

                if (!ItemWrittenBook.a(var3.getTag()))
                {
                    throw new IOException("Invalid book tag!");
                }

                var4 = this.player.inventory.getItemInHand();

                if (var3 != null && var3.id == Item.WRITTEN_BOOK.id && var4.id == Item.BOOK_AND_QUILL.id)
                {
                    var4.a("author", new NBTTagString("author", this.player.name));
                    var4.a("title", new NBTTagString("title", var3.getTag().getString("title")));
                    var4.a("pages", var3.getTag().getList("pages"));
                    var4.id = Item.WRITTEN_BOOK.id;
                }
            }
            catch (Exception var11)
            {
                var11.printStackTrace();
            }
        }
        else
        {
            int var14;

            if ("MC|TrSel".equals(par1Packet250CustomPayload.tag))
            {
                try
                {
                    var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                    var14 = var2.readInt();
                    Container var16 = this.player.activeContainer;

                    if (var16 instanceof ContainerMerchant)
                    {
                        ((ContainerMerchant)var16).b(var14);
                    }
                }
                catch (Exception var10)
                {
                    var10.printStackTrace();
                }
            }
            else
            {
                int var18;

                if ("MC|AdvCdm".equals(par1Packet250CustomPayload.tag))
                {
                    if (!this.minecraftServer.getEnableCommandBlock())
                    {
                        this.player.sendMessage(this.player.a("advMode.notEnabled", new Object[0]));
                    }
                    else if (this.player.a(2, "") && this.player.abilities.canInstantlyBuild)
                    {
                        try
                        {
                            var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                            var14 = var2.readInt();
                            var18 = var2.readInt();
                            int var5 = var2.readInt();
                            String var6 = Packet.a(var2, 256);
                            TileEntity var7 = this.player.world.getTileEntity(var14, var18, var5);

                            if (var7 != null && var7 instanceof TileEntityCommand)
                            {
                                ((TileEntityCommand)var7).b(var6);
                                this.player.world.notify(var14, var18, var5);
                                this.player.sendMessage("Command set: " + var6);
                            }
                        }
                        catch (Exception var9)
                        {
                            var9.printStackTrace();
                        }
                    }
                    else
                    {
                        this.player.sendMessage(this.player.a("advMode.notAllowed", new Object[0]));
                    }
                }
                else if ("MC|Beacon".equals(par1Packet250CustomPayload.tag))
                {
                    if (this.player.activeContainer instanceof ContainerBeacon)
                    {
                        try
                        {
                            var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                            var14 = var2.readInt();
                            var18 = var2.readInt();
                            ContainerBeacon var17 = (ContainerBeacon)this.player.activeContainer;
                            Slot var19 = var17.getSlot(0);

                            if (var19.d())
                            {
                                var19.a(1);
                                TileEntityBeacon var20 = var17.d();
                                var20.d(var14);
                                var20.e(var18);
                                var20.update();
                            }
                        }
                        catch (Exception var8)
                        {
                            var8.printStackTrace();
                        }
                    }
                }
                else if ("MC|ItemName".equals(par1Packet250CustomPayload.tag) && this.player.activeContainer instanceof ContainerAnvil)
                {
                    ContainerAnvil var13 = (ContainerAnvil)this.player.activeContainer;

                    if (par1Packet250CustomPayload.data != null && par1Packet250CustomPayload.data.length >= 1)
                    {
                        String var15 = SharedConstants.a(new String(par1Packet250CustomPayload.data));

                        if (var15.length() <= 30)
                        {
                            var13.a(var15);
                        }
                    }
                    else
                    {
                        var13.a("");
                    }
                }
            }
        }
    }
}
