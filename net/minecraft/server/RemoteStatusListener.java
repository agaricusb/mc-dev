package net.minecraft.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RemoteStatusListener extends RemoteConnectionThread
{
    /** The time of the last client auth check */
    private long clearedTime;

    /** The RCon query port */
    private int bindPort;

    /** Port the server is running on */
    private int serverPort;

    /** The maximum number of players allowed on the server */
    private int maxPlayers;

    /** The current server message of the day */
    private String localAddress;

    /** The name of the currently lo */
    private String worldName;

    /** The remote socket querying the server */
    private DatagramSocket socket = null;

    /** A buffer for incoming DatagramPackets */
    private byte[] n = new byte[1460];

    /** Storage for incoming DatagramPackets */
    private DatagramPacket o = null;
    private Map p;

    /** The hostname of this query server */
    private String hostname;

    /** The hostname of the running server */
    private String motd;

    /** A map of SocketAddress objects to RConThreadQueryAuth objects */
    private Map challenges;

    /**
     * The time that this RConThreadQuery was constructed, from (new Date()).getTime()
     */
    private long t;

    /** The RConQuery output stream */
    private RemoteStatusReply cachedReply;

    /** The time of the last query response sent */
    private long cacheTime;

    public RemoteStatusListener(IMinecraftServer par1IServer)
    {
        super(par1IServer);
        this.bindPort = par1IServer.a("query.port", 0);
        this.motd = par1IServer.u();
        this.serverPort = par1IServer.v();
        this.localAddress = par1IServer.w();
        this.maxPlayers = par1IServer.z();
        this.worldName = par1IServer.J();
        this.cacheTime = 0L;
        this.hostname = "0.0.0.0";

        if (0 != this.motd.length() && !this.hostname.equals(this.motd))
        {
            this.hostname = this.motd;
        }
        else
        {
            this.motd = "0.0.0.0";

            try
            {
                InetAddress var2 = InetAddress.getLocalHost();
                this.hostname = var2.getHostAddress();
            }
            catch (UnknownHostException var3)
            {
                this.warning("Unable to determine local host IP, please set server-ip in \'" + par1IServer.b_() + "\' : " + var3.getMessage());
            }
        }

        if (0 == this.bindPort)
        {
            this.bindPort = this.serverPort;
            this.info("Setting default query port to " + this.bindPort);
            par1IServer.a("query.port", Integer.valueOf(this.bindPort));
            par1IServer.a("debug", Boolean.valueOf(false));
            par1IServer.a();
        }

        this.p = new HashMap();
        this.cachedReply = new RemoteStatusReply(1460);
        this.challenges = new HashMap();
        this.t = (new Date()).getTime();
    }

    /**
     * Sends a byte array as a DatagramPacket response to the client who sent the given DatagramPacket
     */
    private void send(byte[] par1ArrayOfByte, DatagramPacket par2DatagramPacket) throws IOException
    {
        this.socket.send(new DatagramPacket(par1ArrayOfByte, par1ArrayOfByte.length, par2DatagramPacket.getSocketAddress()));
    }

    /**
     * Parses an incoming DatagramPacket, returning true if the packet was valid
     */
    private boolean parsePacket(DatagramPacket par1DatagramPacket) throws IOException
    {
        byte[] var2 = par1DatagramPacket.getData();
        int var3 = par1DatagramPacket.getLength();
        SocketAddress var4 = par1DatagramPacket.getSocketAddress();
        this.debug("Packet len " + var3 + " [" + var4 + "]");

        if (3 <= var3 && -2 == var2[0] && -3 == var2[1])
        {
            this.debug("Packet \'" + StatusChallengeUtils.a(var2[2]) + "\' [" + var4 + "]");

            switch (var2[2])
            {
                case 0:
                    if (!this.hasChallenged(par1DatagramPacket).booleanValue())
                    {
                        this.debug("Invalid challenge [" + var4 + "]");
                        return false;
                    }
                    else if (15 == var3)
                    {
                        this.send(this.getFullReply(par1DatagramPacket), par1DatagramPacket);
                        this.debug("Rules [" + var4 + "]");
                    }
                    else
                    {
                        RemoteStatusReply var5 = new RemoteStatusReply(1460);
                        var5.write(0);
                        var5.write(this.getIdentityToken(par1DatagramPacket.getSocketAddress()));
                        var5.write(this.localAddress);
                        var5.write("SMP");
                        var5.write(this.worldName);
                        var5.write(Integer.toString(this.d()));
                        var5.write(Integer.toString(this.maxPlayers));
                        var5.write((short) this.serverPort);
                        var5.write(this.hostname);
                        this.send(var5.getBytes(), par1DatagramPacket);
                        this.debug("Status [" + var4 + "]");
                    }

                case 9:
                    this.createChallenge(par1DatagramPacket);
                    this.debug("Challenge [" + var4 + "]");
                    return true;

                default:
                    return true;
            }
        }
        else
        {
            this.debug("Invalid packet [" + var4 + "]");
            return false;
        }
    }

    /**
     * Creates a query response as a byte array for the specified query DatagramPacket
     */
    private byte[] getFullReply(DatagramPacket par1DatagramPacket) throws IOException
    {
        long var2 = System.currentTimeMillis();

        if (var2 < this.cacheTime + 5000L)
        {
            byte[] var7 = this.cachedReply.getBytes();
            byte[] var8 = this.getIdentityToken(par1DatagramPacket.getSocketAddress());
            var7[1] = var8[0];
            var7[2] = var8[1];
            var7[3] = var8[2];
            var7[4] = var8[3];
            return var7;
        }
        else
        {
            this.cacheTime = var2;
            this.cachedReply.reset();
            this.cachedReply.write(0);
            this.cachedReply.write(this.getIdentityToken(par1DatagramPacket.getSocketAddress()));
            this.cachedReply.write("splitnum");
            this.cachedReply.write(128);
            this.cachedReply.write(0);
            this.cachedReply.write("hostname");
            this.cachedReply.write(this.localAddress);
            this.cachedReply.write("gametype");
            this.cachedReply.write("SMP");
            this.cachedReply.write("game_id");
            this.cachedReply.write("MINECRAFT");
            this.cachedReply.write("version");
            this.cachedReply.write(this.server.getVersion());
            this.cachedReply.write("plugins");
            this.cachedReply.write(this.server.getPlugins());
            this.cachedReply.write("map");
            this.cachedReply.write(this.worldName);
            this.cachedReply.write("numplayers");
            this.cachedReply.write("" + this.d());
            this.cachedReply.write("maxplayers");
            this.cachedReply.write("" + this.maxPlayers);
            this.cachedReply.write("hostport");
            this.cachedReply.write("" + this.serverPort);
            this.cachedReply.write("hostip");
            this.cachedReply.write(this.hostname);
            this.cachedReply.write(0);
            this.cachedReply.write(1);
            this.cachedReply.write("player_");
            this.cachedReply.write(0);
            String[] var4 = this.server.getPlayers();
            byte var5 = (byte)var4.length;

            for (byte var6 = (byte)(var5 - 1); var6 >= 0; --var6)
            {
                this.cachedReply.write(var4[var6]);
            }

            this.cachedReply.write(0);
            return this.cachedReply.getBytes();
        }
    }

    /**
     * Returns the request ID provided by the authorized client
     */
    private byte[] getIdentityToken(SocketAddress par1SocketAddress)
    {
        return ((RemoteStatusChallenge)this.challenges.get(par1SocketAddress)).getIdentityToken();
    }

    /**
     * Returns true if the client has a valid auth, otherwise false
     */
    private Boolean hasChallenged(DatagramPacket par1DatagramPacket)
    {
        SocketAddress var2 = par1DatagramPacket.getSocketAddress();

        if (!this.challenges.containsKey(var2))
        {
            return Boolean.valueOf(false);
        }
        else
        {
            byte[] var3 = par1DatagramPacket.getData();
            return ((RemoteStatusChallenge)this.challenges.get(var2)).getToken() != StatusChallengeUtils.c(var3, 7, par1DatagramPacket.getLength()) ? Boolean.valueOf(false) : Boolean.valueOf(true);
        }
    }

    /**
     * Sends an auth challenge DatagramPacket to the client and adds the client to the queryClients map
     */
    private void createChallenge(DatagramPacket par1DatagramPacket) throws IOException
    {
        RemoteStatusChallenge var2 = new RemoteStatusChallenge(this, par1DatagramPacket);
        this.challenges.put(par1DatagramPacket.getSocketAddress(), var2);
        this.send(var2.getChallengeResponse(), par1DatagramPacket);
    }

    /**
     * Removes all clients whose auth is no longer valid
     */
    private void cleanChallenges()
    {
        if (this.running)
        {
            long var1 = System.currentTimeMillis();

            if (var1 >= this.clearedTime + 30000L)
            {
                this.clearedTime = var1;
                Iterator var3 = this.challenges.entrySet().iterator();

                while (var3.hasNext())
                {
                    Entry var4 = (Entry)var3.next();

                    if (((RemoteStatusChallenge)var4.getValue()).isExpired(var1).booleanValue())
                    {
                        var3.remove();
                    }
                }
            }
        }
    }

    public void run()
    {
        this.info("Query running on " + this.motd + ":" + this.bindPort);
        this.clearedTime = System.currentTimeMillis();
        this.o = new DatagramPacket(this.n, this.n.length);

        try
        {
            while (this.running)
            {
                try
                {
                    this.socket.receive(this.o);
                    this.cleanChallenges();
                    this.parsePacket(this.o);
                }
                catch (SocketTimeoutException var7)
                {
                    this.cleanChallenges();
                }
                catch (PortUnreachableException var8)
                {
                    ;
                }
                catch (IOException var9)
                {
                    this.a(var9);
                }
            }
        }
        finally
        {
            this.e();
        }
    }

    /**
     * Creates a new Thread object from this class and starts running
     */
    public void a()
    {
        if (!this.running)
        {
            if (0 < this.bindPort && 65535 >= this.bindPort)
            {
                if (this.g())
                {
                    super.a();
                }
            }
            else
            {
                this.warning("Invalid query port " + this.bindPort + " found in \'" + this.server.b_() + "\' (queries disabled)");
            }
        }
    }

    /**
     * Stops the query server and reports the given Exception
     */
    private void a(Exception par1Exception)
    {
        if (this.running)
        {
            this.warning("Unexpected exception, buggy JRE? (" + par1Exception.toString() + ")");

            if (!this.g())
            {
                this.error("Failed to recover from buggy JRE, shutting down!");
                this.running = false;
            }
        }
    }

    /**
     * Initializes the query system by binding it to a port
     */
    private boolean g()
    {
        try
        {
            this.socket = new DatagramSocket(this.bindPort, InetAddress.getByName(this.motd));
            this.a(this.socket);
            this.socket.setSoTimeout(500);
            return true;
        }
        catch (SocketException var2)
        {
            this.warning("Unable to initialise query system on " + this.motd + ":" + this.bindPort + " (Socket): " + var2.getMessage());
        }
        catch (UnknownHostException var3)
        {
            this.warning("Unable to initialise query system on " + this.motd + ":" + this.bindPort + " (Unknown Host): " + var3.getMessage());
        }
        catch (Exception var4)
        {
            this.warning("Unable to initialise query system on " + this.motd + ":" + this.bindPort + " (E): " + var4.getMessage());
        }

        return false;
    }
}
