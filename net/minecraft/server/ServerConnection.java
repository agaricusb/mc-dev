package net.minecraft.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ServerConnection
{
    /** Reference to the logger. */
    public static Logger a = Logger.getLogger("Minecraft");

    /** Reference to the MinecraftServer object. */
    private final MinecraftServer c;
    private final List d = Collections.synchronizedList(new ArrayList());

    /** Whether the network listener object is listening. */
    public volatile boolean b = false;

    public ServerConnection(MinecraftServer par1MinecraftServer) throws IOException
    {
        this.c = par1MinecraftServer;
        this.b = true;
    }

    /**
     * adds this connection to the list of currently connected players
     */
    public void a(NetServerHandler par1NetServerHandler)
    {
        this.d.add(par1NetServerHandler);
    }

    public void a()
    {
        this.b = false;
    }

    /**
     * Handles all incoming connections and packets
     */
    public void b()
    {
        for (int var1 = 0; var1 < this.d.size(); ++var1)
        {
            NetServerHandler var2 = (NetServerHandler)this.d.get(var1);

            try
            {
                var2.d();
            }
            catch (Exception var5)
            {
                if (var2.networkManager instanceof MemoryNetworkManager)
                {
                    CrashReport var4 = CrashReport.a(var5, "Ticking memory connection");
                    throw new ReportedException(var4);
                }

                a.log(Level.WARNING, "Failed to handle packet for " + var2.player.getLocalizedName() + "/" + var2.player.q() + ": " + var5, var5);
                var2.disconnect("Internal server error");
            }

            if (var2.disconnected)
            {
                this.d.remove(var1--);
            }

            var2.networkManager.a();
        }
    }

    public MinecraftServer d()
    {
        return this.c;
    }
}
