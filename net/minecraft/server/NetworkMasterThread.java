package net.minecraft.server;

class NetworkMasterThread extends Thread
{
    final NetworkManager a;

    NetworkMasterThread(NetworkManager par1TcpConnection)
    {
        this.a = par1TcpConnection;
    }

    @SuppressWarnings("deprecation")
    public void run()
    {
        try
        {
            Thread.sleep(5000L);

            if (NetworkManager.g(this.a).isAlive())
            {
                try
                {
                    NetworkManager.g(this.a).stop();
                }
                catch (Throwable var3)
                {
                    ;
                }
            }

            if (NetworkManager.h(this.a).isAlive())
            {
                try
                {
                    NetworkManager.h(this.a).stop();
                }
                catch (Throwable var2)
                {
                    ;
                }
            }
        }
        catch (InterruptedException var4)
        {
            var4.printStackTrace();
        }
    }
}
