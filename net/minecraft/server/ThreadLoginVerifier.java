package net.minecraft.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;

class ThreadLoginVerifier extends Thread
{
    /** The login handler that spawned this thread. */
    final PendingConnection pendingConnection;

    ThreadLoginVerifier(PendingConnection par1NetLoginHandler)
    {
        this.pendingConnection = par1NetLoginHandler;
    }

    public void run()
    {
        try
        {
            String var1 = (new BigInteger(MinecraftEncryption.a(PendingConnection.a(this.pendingConnection), PendingConnection.b(this.pendingConnection).F().getPublic(), PendingConnection.c(this.pendingConnection)))).toString(16);
            URL var2 = new URL("http://session.minecraft.net/game/checkserver.jsp?user=" + URLEncoder.encode(PendingConnection.d(this.pendingConnection), "UTF-8") + "&serverId=" + URLEncoder.encode(var1, "UTF-8"));
            BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.openStream()));
            String var4 = var3.readLine();
            var3.close();

            if (!"YES".equals(var4))
            {
                this.pendingConnection.disconnect("Failed to verify username!");
                return;
            }

            PendingConnection.a(this.pendingConnection, true);
        }
        catch (Exception var5)
        {
            this.pendingConnection.disconnect("Failed to verify username! [internal error " + var5 + "]");
            var5.printStackTrace();
        }
    }
}
