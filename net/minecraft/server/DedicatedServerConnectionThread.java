package net.minecraft.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DedicatedServerConnectionThread extends Thread
{
    private static Logger a = Logger.getLogger("Minecraft");
    private final List b = Collections.synchronizedList(new ArrayList());

    /**
     * This map stores a list of InetAddresses and the last time which they connected at
     */
    private final HashMap c = new HashMap();
    private int d = 0;
    private final ServerSocket e;
    private ServerConnection f;
    private final InetAddress g;
    private final int h;

    public DedicatedServerConnectionThread(ServerConnection par1NetworkListenThread, InetAddress par2InetAddress, int par3) throws IOException
    {
        super("Listen thread");
        this.f = par1NetworkListenThread;
        this.h = par3;
        this.e = new ServerSocket(par3, 0, par2InetAddress);
        this.g = par2InetAddress == null ? this.e.getInetAddress() : par2InetAddress;
        this.e.setPerformancePreferences(0, 2, 1);
    }

    public void a()
    {
        List var1 = this.b;

        synchronized (this.b)
        {
            for (int var2 = 0; var2 < this.b.size(); ++var2)
            {
                PendingConnection var3 = (PendingConnection)this.b.get(var2);

                try
                {
                    var3.c();
                }
                catch (Exception var6)
                {
                    var3.disconnect("Internal server error");
                    a.log(Level.WARNING, "Failed to handle packet for " + var3.getName() + ": " + var6, var6);
                }

                if (var3.c)
                {
                    this.b.remove(var2--);
                }

                var3.networkManager.a();
            }
        }
    }

    public void run()
    {
        while (this.f.b)
        {
            try
            {
                Socket var1 = this.e.accept();
                InetAddress var2 = var1.getInetAddress();
                long var3 = System.currentTimeMillis();
                HashMap var5 = this.c;

                synchronized (this.c)
                {
                    if (this.c.containsKey(var2) && !b(var2) && var3 - ((Long)this.c.get(var2)).longValue() < 4000L)
                    {
                        this.c.put(var2, Long.valueOf(var3));
                        var1.close();
                        continue;
                    }

                    this.c.put(var2, Long.valueOf(var3));
                }

                PendingConnection var9 = new PendingConnection(this.f.d(), var1, "Connection #" + this.d++);
                this.a(var9);
            }
            catch (IOException var8)
            {
                var8.printStackTrace();
            }
        }

        System.out.println("Closing listening thread");
    }

    private void a(PendingConnection par1NetLoginHandler)
    {
        if (par1NetLoginHandler == null)
        {
            throw new IllegalArgumentException("Got null pendingconnection!");
        }
        else
        {
            List var2 = this.b;

            synchronized (this.b)
            {
                this.b.add(par1NetLoginHandler);
            }
        }
    }

    private static boolean b(InetAddress par0InetAddress)
    {
        return "127.0.0.1".equals(par0InetAddress.getHostAddress());
    }

    public void a(InetAddress par1InetAddress)
    {
        if (par1InetAddress != null)
        {
            HashMap var2 = this.c;

            synchronized (this.c)
            {
                this.c.remove(par1InetAddress);
            }
        }
    }

    public void b()
    {
        try
        {
            this.e.close();
        }
        catch (Throwable var2)
        {
            ;
        }
    }
}
