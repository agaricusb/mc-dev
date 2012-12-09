package net.minecraft.server;

public final class ThreadShutdown extends Thread
{
    final DedicatedServer a;

    public ThreadShutdown(DedicatedServer par1DedicatedServer)
    {
        this.a = par1DedicatedServer;
    }

    public void run()
    {
        this.a.stop();
    }
}
