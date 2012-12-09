package net.minecraft.server;

import java.io.IOException;

class NetworkWriterThread extends Thread
{
    final NetworkManager a;

    NetworkWriterThread(NetworkManager par1TcpConnection, String par2Str)
    {
        super(par2Str);
        this.a = par1TcpConnection;
    }

    public void run()
    {
        NetworkManager.b.getAndIncrement();

        try
        {
            while (NetworkManager.a(this.a))
            {
                boolean var1;

                for (var1 = false; NetworkManager.d(this.a); var1 = true)
                {
                    ;
                }

                try
                {
                    if (var1 && NetworkManager.e(this.a) != null)
                    {
                        NetworkManager.e(this.a).flush();
                    }
                }
                catch (IOException var8)
                {
                    if (!NetworkManager.f(this.a))
                    {
                        NetworkManager.a(this.a, var8);
                    }

                    var8.printStackTrace();
                }

                try
                {
                    sleep(2L);
                }
                catch (InterruptedException var7)
                {
                    ;
                }
            }
        }
        finally
        {
            NetworkManager.b.getAndDecrement();
        }
    }
}
