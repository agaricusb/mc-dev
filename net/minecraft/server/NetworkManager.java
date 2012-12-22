package net.minecraft.server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;

public class NetworkManager implements INetworkManager
{
    public static AtomicInteger a = new AtomicInteger();
    public static AtomicInteger b = new AtomicInteger();

    /** The object used for synchronization on the send queue. */
    private Object h = new Object();

    /** The socket used by this network manager. */
    private Socket socket;

    /** The InetSocketAddress of the remote endpoint */
    private final SocketAddress j;

    /** The input stream connected to the socket. */
    private volatile DataInputStream input;

    /** The output stream connected to the socket. */
    private volatile DataOutputStream output;

    /** Whether the network is currently operational. */
    private volatile boolean m = true;

    /**
     * Whether this network manager is currently terminating (and should ignore further errors).
     */
    private volatile boolean n = false;

    /**
     * Linked list of packets that have been read and are awaiting processing.
     */
    private List inboundQueue = Collections.synchronizedList(new ArrayList());

    /** Linked list of packets awaiting sending. */
    private List highPriorityQueue = Collections.synchronizedList(new ArrayList());

    /** Linked list of packets with chunk data that are awaiting sending. */
    private List lowPriorityQueue = Collections.synchronizedList(new ArrayList());

    /** A reference to the NetHandler object. */
    private Connection connection;

    /**
     * Whether this server is currently terminating. If this is a client, this is always false.
     */
    private boolean s = false;

    /** The thread used for writing. */
    private Thread t;

    /** The thread used for reading. */
    private Thread u;

    /** A String indicating why the network has shutdown. */
    private String v = "";
    private Object[] w;
    private int x = 0;

    /**
     * The length in bytes of the packets in both send queues (data and chunkData).
     */
    private int y = 0;
    public static int[] c = new int[256];
    public static int[] d = new int[256];
    public int e = 0;
    boolean f = false;
    boolean g = false;
    private SecretKey z = null;
    private PrivateKey A = null;

    /**
     * Delay for sending pending chunk data packets (as opposed to pending non-chunk data packets)
     */
    private int lowPriorityQueueDelay = 50;

    public NetworkManager(Socket par1Socket, String par2Str, Connection par3NetHandler, PrivateKey par4PrivateKey) throws IOException
    {
        this.A = par4PrivateKey;
        this.socket = par1Socket;
        this.j = par1Socket.getRemoteSocketAddress();
        this.connection = par3NetHandler;

        try
        {
            par1Socket.setSoTimeout(30000);
            par1Socket.setTrafficClass(24);
        }
        catch (SocketException var6)
        {
            System.err.println(var6.getMessage());
        }

        this.input = new DataInputStream(par1Socket.getInputStream());
        this.output = new DataOutputStream(new BufferedOutputStream(par1Socket.getOutputStream(), 5120));
        this.u = new NetworkReaderThread(this, par2Str + " read thread");
        this.t = new NetworkWriterThread(this, par2Str + " write thread");
        this.u.start();
        this.t.start();
    }

    /**
     * Sets the NetHandler for this NetworkManager. Server-only.
     */
    public void a(Connection par1NetHandler)
    {
        this.connection = par1NetHandler;
    }

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    public void queue(Packet par1Packet)
    {
        if (!this.s)
        {
            Object var2 = this.h;

            synchronized (this.h)
            {
                this.y += par1Packet.a() + 1;
                this.highPriorityQueue.add(par1Packet);
            }
        }
    }

    /**
     * Sends a data packet if there is one to send, or sends a chunk data packet if there is one and the counter is up,
     * or does nothing.
     */
    private boolean h()
    {
        boolean var1 = false;

        try
        {
            Packet var2;
            int var10001;
            int[] var10000;

            if (this.e == 0 || !this.highPriorityQueue.isEmpty() && System.currentTimeMillis() - ((Packet)this.highPriorityQueue.get(0)).timestamp >= (long)this.e)
            {
                var2 = this.a(false);

                if (var2 != null)
                {
                    Packet.a(var2, this.output);

                    if (var2 instanceof Packet252KeyResponse && !this.g)
                    {
                        if (!this.connection.a())
                        {
                            this.z = ((Packet252KeyResponse)var2).d();
                        }

                        this.k();
                    }

                    var10000 = d;
                    var10001 = var2.k();
                    var10000[var10001] += var2.a() + 1;
                    var1 = true;
                }
            }

            if (this.lowPriorityQueueDelay-- <= 0 && (this.e == 0 || !this.lowPriorityQueue.isEmpty() && System.currentTimeMillis() - ((Packet)this.lowPriorityQueue.get(0)).timestamp >= (long)this.e))
            {
                var2 = this.a(true);

                if (var2 != null)
                {
                    Packet.a(var2, this.output);
                    var10000 = d;
                    var10001 = var2.k();
                    var10000[var10001] += var2.a() + 1;
                    this.lowPriorityQueueDelay = 0;
                    var1 = true;
                }
            }

            return var1;
        }
        catch (Exception var3)
        {
            if (!this.n)
            {
                this.a(var3);
            }

            return false;
        }
    }

    private Packet a(boolean par1)
    {
        Packet var2 = null;
        List var3 = par1 ? this.lowPriorityQueue : this.highPriorityQueue;
        Object var4 = this.h;

        synchronized (this.h)
        {
            while (!var3.isEmpty() && var2 == null)
            {
                var2 = (Packet)var3.remove(0);
                this.y -= var2.a() + 1;

                if (this.a(var2, par1))
                {
                    var2 = null;
                }
            }

            return var2;
        }
    }

    private boolean a(Packet par1Packet, boolean par2)
    {
        if (!par1Packet.e())
        {
            return false;
        }
        else
        {
            List var3 = par2 ? this.lowPriorityQueue : this.highPriorityQueue;
            Iterator var4 = var3.iterator();
            Packet var5;

            do
            {
                if (!var4.hasNext())
                {
                    return false;
                }

                var5 = (Packet)var4.next();
            }
            while (var5.k() != par1Packet.k());

            return par1Packet.a(var5);
        }
    }

    /**
     * Wakes reader and writer threads
     */
    public void a()
    {
        if (this.u != null)
        {
            this.u.interrupt();
        }

        if (this.t != null)
        {
            this.t.interrupt();
        }
    }

    /**
     * Reads a single packet from the input stream and adds it to the read queue. If no packet is read, it shuts down
     * the network.
     */
    private boolean i()
    {
        boolean var1 = false;

        try
        {
            Packet var2 = Packet.a(this.input, this.connection.a(), this.socket);

            if (var2 != null)
            {
                if (var2 instanceof Packet252KeyResponse && !this.f)
                {
                    if (this.connection.a())
                    {
                        this.z = ((Packet252KeyResponse)var2).a(this.A);
                    }

                    this.j();
                }

                int[] var10000 = c;
                int var10001 = var2.k();
                var10000[var10001] += var2.a() + 1;

                if (!this.s)
                {
                    if (var2.a_() && this.connection.b())
                    {
                        this.x = 0;
                        var2.handle(this.connection);
                    }
                    else
                    {
                        this.inboundQueue.add(var2);
                    }
                }

                var1 = true;
            }
            else
            {
                this.a("disconnect.endOfStream", new Object[0]);
            }

            return var1;
        }
        catch (Exception var3)
        {
            if (!this.n)
            {
                this.a(var3);
            }

            return false;
        }
    }

    /**
     * Used to report network errors and causes a network shutdown.
     */
    private void a(Exception par1Exception)
    {
        par1Exception.printStackTrace();
        this.a("disconnect.genericReason", new Object[]{"Internal exception: " + par1Exception.toString()});
    }

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    public void a(String par1Str, Object... par2ArrayOfObj)
    {
        if (this.m)
        {
            this.n = true;
            this.v = par1Str;
            this.w = par2ArrayOfObj;
            this.m = false;
            (new NetworkMasterThread(this)).start();

            try
            {
                this.input.close();
            }
            catch (Throwable var6)
            {
                ;
            }

            try
            {
                this.output.close();
            }
            catch (Throwable var5)
            {
                ;
            }

            try
            {
                this.socket.close();
            }
            catch (Throwable var4)
            {
                ;
            }

            this.input = null;
            this.output = null;
            this.socket = null;
        }
    }

    /**
     * Checks timeouts and processes all pending read packets.
     */
    public void b()
    {
        if (this.y > 2097152)
        {
            this.a("disconnect.overflow", new Object[0]);
        }

        if (this.inboundQueue.isEmpty())
        {
            if (this.x++ == 1200)
            {
                this.a("disconnect.timeout", new Object[0]);
            }
        }
        else
        {
            this.x = 0;
        }

        int var1 = 1000;

        while (!this.inboundQueue.isEmpty() && var1-- >= 0)
        {
            Packet var2 = (Packet)this.inboundQueue.remove(0);
            var2.handle(this.connection);
        }

        this.a();

        if (this.n && this.inboundQueue.isEmpty())
        {
            this.connection.a(this.v, this.w);
        }
    }

    /**
     * Returns the socket address of the remote side. Server-only.
     */
    public SocketAddress getSocketAddress()
    {
        return this.j;
    }

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    public void d()
    {
        if (!this.s)
        {
            this.a();
            this.s = true;
            this.u.interrupt();
            (new NetworkMonitorThread(this)).start();
        }
    }

    private void j() throws IOException
    {
        this.f = true;
        InputStream var1 = this.socket.getInputStream();
        this.input = new DataInputStream(MinecraftEncryption.a(this.z, var1));
    }

    /**
     * flushes the stream and replaces it with an encryptedOutputStream
     */
    private void k() throws IOException
    {
        this.output.flush();
        this.g = true;
        BufferedOutputStream var1 = new BufferedOutputStream(MinecraftEncryption.a(this.z, this.socket.getOutputStream()), 5120);
        this.output = new DataOutputStream(var1);
    }

    /**
     * Returns the number of chunk data packets waiting to be sent.
     */
    public int e()
    {
        return this.lowPriorityQueue.size();
    }

    public Socket getSocket()
    {
        return this.socket;
    }

    /**
     * Whether the network is operational.
     */
    static boolean a(NetworkManager par0TcpConnection)
    {
        return par0TcpConnection.m;
    }

    /**
     * Is the server terminating? Client side aways returns false.
     */
    static boolean b(NetworkManager par0TcpConnection)
    {
        return par0TcpConnection.s;
    }

    /**
     * Static accessor to readPacket.
     */
    static boolean c(NetworkManager par0TcpConnection)
    {
        return par0TcpConnection.i();
    }

    /**
     * Static accessor to sendPacket.
     */
    static boolean d(NetworkManager par0TcpConnection)
    {
        return par0TcpConnection.h();
    }

    static DataOutputStream e(NetworkManager par0TcpConnection)
    {
        return par0TcpConnection.output;
    }

    /**
     * Gets whether the Network manager is terminating.
     */
    static boolean f(NetworkManager par0TcpConnection)
    {
        return par0TcpConnection.n;
    }

    /**
     * Sends the network manager an error
     */
    static void a(NetworkManager par0TcpConnection, Exception par1Exception)
    {
        par0TcpConnection.a(par1Exception);
    }

    /**
     * Returns the read thread.
     */
    static Thread g(NetworkManager par0TcpConnection)
    {
        return par0TcpConnection.u;
    }

    /**
     * Returns the write thread.
     */
    static Thread h(NetworkManager par0TcpConnection)
    {
        return par0TcpConnection.t;
    }
}
