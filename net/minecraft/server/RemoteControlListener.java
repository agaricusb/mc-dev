package net.minecraft.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RemoteControlListener extends RemoteConnectionThread
{
    /** Port RCon is running on */
    private int g;

    /** Port the server is running on */
    private int h;

    /** Hostname RCon is running on */
    private String i;

    /** The RCon ServerSocket. */
    private ServerSocket j = null;

    /** The RCon password */
    private String k;

    /** A map of client addresses to their running Threads */
    private Map l;

    public RemoteControlListener(IMinecraftServer par1IServer)
    {
        super(par1IServer);
        this.g = par1IServer.a("rcon.port", 0);
        this.k = par1IServer.a("rcon.password", "");
        this.i = par1IServer.u();
        this.h = par1IServer.v();

        if (0 == this.g)
        {
            this.g = this.h + 10;
            this.info("Setting default rcon port to " + this.g);
            par1IServer.a("rcon.port", Integer.valueOf(this.g));

            if (0 == this.k.length())
            {
                par1IServer.a("rcon.password", "");
            }

            par1IServer.a();
        }

        if (0 == this.i.length())
        {
            this.i = "0.0.0.0";
        }

        this.f();
        this.j = null;
    }

    private void f()
    {
        this.l = new HashMap();
    }

    /**
     * Cleans up the clientThreads map by removing client Threads that are not running
     */
    private void g()
    {
        Iterator var1 = this.l.entrySet().iterator();

        while (var1.hasNext())
        {
            Entry var2 = (Entry)var1.next();

            if (!((RemoteControlSession)var2.getValue()).c())
            {
                var1.remove();
            }
        }
    }

    public void run()
    {
        this.info("RCON running on " + this.i + ":" + this.g);

        try
        {
            while (this.running)
            {
                try
                {
                    Socket var1 = this.j.accept();
                    var1.setSoTimeout(500);
                    RemoteControlSession var2 = new RemoteControlSession(this.server, var1);
                    var2.a();
                    this.l.put(var1.getRemoteSocketAddress(), var2);
                    this.g();
                }
                catch (SocketTimeoutException var7)
                {
                    this.g();
                }
                catch (IOException var8)
                {
                    if (this.running)
                    {
                        this.info("IO: " + var8.getMessage());
                    }
                }
            }
        }
        finally
        {
            this.b(this.j);
        }
    }

    /**
     * Creates a new Thread object from this class and starts running
     */
    public void a()
    {
        if (0 == this.k.length())
        {
            this.warning("No rcon password set in \'" + this.server.b_() + "\', rcon disabled!");
        }
        else if (0 < this.g && 65535 >= this.g)
        {
            if (!this.running)
            {
                try
                {
                    this.j = new ServerSocket(this.g, 0, InetAddress.getByName(this.i));
                    this.j.setSoTimeout(500);
                    super.a();
                }
                catch (IOException var2)
                {
                    this.warning("Unable to initialise rcon on " + this.i + ":" + this.g + " : " + var2.getMessage());
                }
            }
        }
        else
        {
            this.warning("Invalid rcon port " + this.g + " found in \'" + this.server.b_() + "\', rcon disabled!");
        }
    }
}
