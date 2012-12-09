package net.minecraft.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;

class ThreadLoginVerifier extends Thread
{
    /** The login handler that spawned this thread. */
    final NetLoginHandler netLoginHandler;

    ThreadLoginVerifier(NetLoginHandler par1NetLoginHandler)
    {
        this.netLoginHandler = par1NetLoginHandler;
    }

    public void run()
    {
        try
        {
            String var1 = (new BigInteger(MinecraftEncryption.a(NetLoginHandler.a(this.netLoginHandler), NetLoginHandler.b(this.netLoginHandler).F().getPublic(), NetLoginHandler.c(this.netLoginHandler)))).toString(16);
            URL var2 = new URL("http://session.minecraft.net/game/checkserver.jsp?user=" + URLEncoder.encode(NetLoginHandler.d(this.netLoginHandler), "UTF-8") + "&serverId=" + URLEncoder.encode(var1, "UTF-8"));
            BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.openStream()));
            String var4 = var3.readLine();
            var3.close();

            if (!"YES".equals(var4))
            {
                this.netLoginHandler.disconnect("Failed to verify username!");
                return;
            }

            NetLoginHandler.a(this.netLoginHandler, true);
        }
        catch (Exception var5)
        {
            this.netLoginHandler.disconnect("Failed to verify username! [internal error " + var5 + "]");
            var5.printStackTrace();
        }
    }
}
