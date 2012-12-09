package net.minecraft.server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class ServerWindowAdapter extends WindowAdapter
{
    /** The Minecraft instance. */
    final DedicatedServer a;

    ServerWindowAdapter(DedicatedServer par1DedicatedServer)
    {
        this.a = par1DedicatedServer;
    }

    public void windowClosing(WindowEvent par1WindowEvent)
    {
        this.a.safeShutdown();

        while (!this.a.isStopped())
        {
            try
            {
                Thread.sleep(100L);
            }
            catch (InterruptedException var3)
            {
                var3.printStackTrace();
            }
        }

        System.exit(0);
    }
}
