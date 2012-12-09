package net.minecraft.server;

import java.io.IOException;
import java.net.InetAddress;

public class DedicatedServerConnection extends ServerConnection
{
    /** Instance of ServerListenThread. */
    private final DedicatedServerConnectionThread c;

    public DedicatedServerConnection(MinecraftServer par1MinecraftServer, InetAddress par2InetAddress, int par3) throws IOException
    {
        super(par1MinecraftServer);
        this.c = new DedicatedServerConnectionThread(this, par2InetAddress, par3);
        this.c.start();
    }

    public void a()
    {
        super.a();
        this.c.b();
        this.c.interrupt();
    }

    /**
     * Handles all incoming connections and packets
     */
    public void b()
    {
        this.c.a();
        super.b();
    }

    public DedicatedServer c()
    {
        return (DedicatedServer)super.d();
    }

    public void a(InetAddress par1InetAddress)
    {
        this.c.a(par1InetAddress);
    }

    public MinecraftServer d()
    {
        return this.c();
    }
}
