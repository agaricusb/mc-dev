package net.minecraft.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class RemoteConnectionThread implements Runnable
{
    /** True i */
    protected boolean running = false;

    /** Reference to the IServer object. */
    protected IMinecraftServer server;

    /** Thread for this runnable class */
    protected Thread thread;
    protected int d = 5;

    /** A list of registered DatagramSockets */
    protected List e = new ArrayList();

    /** A list of registered ServerSockets */
    protected List f = new ArrayList();

    RemoteConnectionThread(IMinecraftServer par1IServer)
    {
        this.server = par1IServer;

        if (this.server.isDebugging())
        {
            this.warning("Debugging is enabled, performance maybe reduced!");
        }
    }

    /**
     * Creates a new Thread object from this class and starts running
     */
    public synchronized void a()
    {
        this.thread = new Thread(this);
        this.thread.start();
        this.running = true;
    }

    /**
     * Returns true if the Thread is running, false otherwise
     */
    public boolean c()
    {
        return this.running;
    }

    /**
     * Log debug message
     */
    protected void debug(String par1Str)
    {
        this.server.j(par1Str);
    }

    /**
     * Log information message
     */
    protected void info(String par1Str)
    {
        this.server.info(par1Str);
    }

    /**
     * Log warning message
     */
    protected void warning(String par1Str)
    {
        this.server.warning(par1Str);
    }

    /**
     * Log severe error message
     */
    protected void error(String par1Str)
    {
        this.server.i(par1Str);
    }

    /**
     * Returns the number of players on the server
     */
    protected int d()
    {
        return this.server.y();
    }

    /**
     * Registers a DatagramSocket with this thread
     */
    protected void a(DatagramSocket par1DatagramSocket)
    {
        this.debug("registerSocket: " + par1DatagramSocket);
        this.e.add(par1DatagramSocket);
    }

    /**
     * Closes the specified DatagramSocket
     */
    protected boolean a(DatagramSocket par1DatagramSocket, boolean par2)
    {
        this.debug("closeSocket: " + par1DatagramSocket);

        if (null == par1DatagramSocket)
        {
            return false;
        }
        else
        {
            boolean var3 = false;

            if (!par1DatagramSocket.isClosed())
            {
                par1DatagramSocket.close();
                var3 = true;
            }

            if (par2)
            {
                this.e.remove(par1DatagramSocket);
            }

            return var3;
        }
    }

    /**
     * Closes the specified ServerSocket
     */
    protected boolean b(ServerSocket par1ServerSocket)
    {
        return this.a(par1ServerSocket, true);
    }

    /**
     * Closes the specified ServerSocket
     */
    protected boolean a(ServerSocket par1ServerSocket, boolean par2)
    {
        this.debug("closeSocket: " + par1ServerSocket);

        if (null == par1ServerSocket)
        {
            return false;
        }
        else
        {
            boolean var3 = false;

            try
            {
                if (!par1ServerSocket.isClosed())
                {
                    par1ServerSocket.close();
                    var3 = true;
                }
            }
            catch (IOException var5)
            {
                this.warning("IO: " + var5.getMessage());
            }

            if (par2)
            {
                this.f.remove(par1ServerSocket);
            }

            return var3;
        }
    }

    /**
     * Closes all of the opened sockets
     */
    protected void e()
    {
        this.a(false);
    }

    /**
     * Closes all of the opened sockets
     */
    protected void a(boolean par1)
    {
        int var2 = 0;
        Iterator var3 = this.e.iterator();

        while (var3.hasNext())
        {
            DatagramSocket var4 = (DatagramSocket)var3.next();

            if (this.a(var4, false))
            {
                ++var2;
            }
        }

        this.e.clear();
        var3 = this.f.iterator();

        while (var3.hasNext())
        {
            ServerSocket var5 = (ServerSocket)var3.next();

            if (this.a(var5, false))
            {
                ++var2;
            }
        }

        this.f.clear();

        if (par1 && 0 < var2)
        {
            this.warning("Force closed " + var2 + " sockets");
        }
    }
}
