package net.minecraft.server;

class ThreadSleepForever extends Thread
{
    /** Instance of the DecitatedServer. */
    final DedicatedServer a;

    ThreadSleepForever(DedicatedServer par1DedicatedServer)
    {
        this.a = par1DedicatedServer;
        this.setDaemon(true);
        this.start();
    }

    public void run()
    {
        while (true)
        {
            try
            {
                while (true)
                {
                    Thread.sleep(2147483647L);
                }
            }
            catch (InterruptedException var2)
            {
                ;
            }
        }
    }
}
