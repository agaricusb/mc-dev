package net.minecraft.server;

class NetworkMonitorThread extends Thread
{
    final NetworkManager a;

    NetworkMonitorThread(NetworkManager par1TcpConnection)
    {
        this.a = par1TcpConnection;
    }

    public void run()
    {
        try
        {
            Thread.sleep(2000L);

            if (NetworkManager.a(this.a))
            {
                NetworkManager.h(this.a).interrupt();
                this.a.a("disconnect.closed", new Object[0]);
            }
        }
        catch (Exception var2)
        {
            var2.printStackTrace();
        }
    }
}
