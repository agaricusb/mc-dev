package net.minecraft.server;

public class ThreadServerApplication extends Thread
{
    final MinecraftServer a;

    public ThreadServerApplication(MinecraftServer par1MinecraftServer, String par2Str)
    {
        super(par2Str);
        this.a = par1MinecraftServer;
    }

    public void run()
    {
        this.a.run();
    }
}
