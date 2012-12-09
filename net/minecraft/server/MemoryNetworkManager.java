package net.minecraft.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryNetworkManager implements INetworkManager
{
    private static final SocketAddress a = new InetSocketAddress("127.0.0.1", 0);
    private final List b = Collections.synchronizedList(new ArrayList());
    private MemoryNetworkManager c;
    private NetHandler d;
    private boolean e = false;
    private String f = "";
    private Object[] g;
    private boolean h = false;

    public MemoryNetworkManager(NetHandler par1NetHandler) throws IOException
    {
        this.d = par1NetHandler;
    }

    /**
     * Sets the NetHandler for this NetworkManager. Server-only.
     */
    public void a(NetHandler par1NetHandler)
    {
        this.d = par1NetHandler;
    }

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    public void queue(Packet par1Packet)
    {
        if (!this.e)
        {
            this.c.b(par1Packet);
        }
    }

    /**
     * Wakes reader and writer threads
     */
    public void a() {}

    /**
     * Checks timeouts and processes all pending read packets.
     */
    public void b()
    {
        int var1 = 2500;

        while (var1-- >= 0 && !this.b.isEmpty())
        {
            Packet var2 = (Packet)this.b.remove(0);
            var2.handle(this.d);
        }

        if (this.b.size() > var1)
        {
            System.out.println("Memory connection overburdened; after processing 2500 packets, we still have " + this.b.size() + " to go!");
        }

        if (this.e && this.b.isEmpty())
        {
            this.d.a(this.f, this.g);
        }
    }

    /**
     * Returns the socket address of the remote side. Server-only.
     */
    public SocketAddress getSocketAddress()
    {
        return a;
    }

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    public void d()
    {
        this.e = true;
    }

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    public void a(String par1Str, Object... par2ArrayOfObj)
    {
        this.e = true;
        this.f = par1Str;
        this.g = par2ArrayOfObj;
    }

    /**
     * Returns the number of chunk data packets waiting to be sent.
     */
    public int e()
    {
        return 0;
    }

    public void b(Packet par1Packet)
    {
        String var2 = this.d.a() ? ">" : "<";

        if (par1Packet.a_() && this.d.b())
        {
            par1Packet.handle(this.d);
        }
        else
        {
            this.b.add(par1Packet);
        }
    }
}
