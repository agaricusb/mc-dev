package net.minecraft.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ThreadCommandReader extends Thread
{
    final DedicatedServer server;

    ThreadCommandReader(DedicatedServer par1DedicatedServer)
    {
        this.server = par1DedicatedServer;
    }

    public void run()
    {
        BufferedReader var1 = new BufferedReader(new InputStreamReader(System.in));
        String var2;

        try
        {
            while (!this.server.isStopped() && this.server.isRunning() && (var2 = var1.readLine()) != null)
            {
                this.server.issueCommand(var2, this.server);
            }
        }
        catch (IOException var4)
        {
            var4.printStackTrace();
        }
    }
}
