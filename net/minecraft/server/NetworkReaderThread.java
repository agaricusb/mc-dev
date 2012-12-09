package net.minecraft.server;

class NetworkReaderThread extends Thread
{
    final NetworkManager a;

    NetworkReaderThread(NetworkManager par1TcpConnection, String par2Str)
    {
        super(par2Str);
        this.a = par1TcpConnection;
    }

    public void run()
    {
        NetworkManager.a.getAndIncrement();

        try
        {
            while (NetworkManager.a(this.a) && !NetworkManager.b(this.a))
            {
                while (true)
                {
                    if (!NetworkManager.c(this.a))
                    {
                        try
                        {
                            sleep(2L);
                        }
                        catch (InterruptedException var5)
                        {
                            ;
                        }
                    }
                }
            }
        }
        finally
        {
            NetworkManager.a.getAndDecrement();
        }
    }
}
