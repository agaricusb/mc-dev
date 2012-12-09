package net.minecraft.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class RemoteControlSession extends RemoteConnectionThread
{
    /**
     * True if the client has succefssfully logged into the RCon, otherwise false
     */
    private boolean g = false;

    /** The client's Socket connection */
    private Socket h;

    /** A buffer for incoming Socket data */
    private byte[] i = new byte[1460];

    /** The RCon password */
    private String j;

    RemoteControlSession(IMinecraftServer par1IServer, Socket par2Socket)
    {
        super(par1IServer);
        this.h = par2Socket;

        try
        {
            this.h.setSoTimeout(0);
        }
        catch (Exception var4)
        {
            this.running = false;
        }

        this.j = par1IServer.a("rcon.password", "");
        this.info("Rcon connection from: " + par2Socket.getInetAddress());
    }

    public void run()
    {
        while (true)
        {
            try
            {
                if (!this.running)
                {
                    break;
                }

                BufferedInputStream var1 = new BufferedInputStream(this.h.getInputStream());
                int var2 = var1.read(this.i, 0, 1460);

                if (10 <= var2)
                {
                    byte var3 = 0;
                    int var4 = StatusChallengeUtils.b(this.i, 0, var2);

                    if (var4 != var2 - 4)
                    {
                        return;
                    }

                    int var21 = var3 + 4;
                    int var5 = StatusChallengeUtils.b(this.i, var21, var2);
                    var21 += 4;
                    int var6 = StatusChallengeUtils.b(this.i, var21);
                    var21 += 4;

                    switch (var6)
                    {
                        case 2:
                            if (this.g)
                            {
                                String var8 = StatusChallengeUtils.a(this.i, var21, var2);

                                try
                                {
                                    this.a(var5, this.server.h(var8));
                                }
                                catch (Exception var16)
                                {
                                    this.a(var5, "Error executing: " + var8 + " (" + var16.getMessage() + ")");
                                }

                                continue;
                            }

                            this.f();
                            continue;

                        case 3:
                            String var7 = StatusChallengeUtils.a(this.i, var21, var2);
                            int var10000 = var21 + var7.length();

                            if (0 != var7.length() && var7.equals(this.j))
                            {
                                this.g = true;
                                this.a(var5, 2, "");
                                continue;
                            }

                            this.g = false;
                            this.f();
                            continue;

                        default:
                            this.a(var5, String.format("Unknown request %s", new Object[]{Integer.toHexString(var6)}));
                            continue;
                    }
                }
            }
            catch (SocketTimeoutException var17)
            {
                break;
            }
            catch (IOException var18)
            {
                break;
            }
            catch (Exception var19)
            {
                System.out.println(var19);
                break;
            }
            finally
            {
                this.g();
            }

            return;
        }
    }

    /**
     * Sends the given response message to the client
     */
    private void a(int par1, int par2, String par3Str) throws IOException
    {
        ByteArrayOutputStream var4 = new ByteArrayOutputStream(1248);
        DataOutputStream var5 = new DataOutputStream(var4);
        var5.writeInt(Integer.reverseBytes(par3Str.length() + 10));
        var5.writeInt(Integer.reverseBytes(par1));
        var5.writeInt(Integer.reverseBytes(par2));
        var5.writeBytes(par3Str);
        var5.write(0);
        var5.write(0);
        this.h.getOutputStream().write(var4.toByteArray());
    }

    /**
     * Sends the standard RCon 'authorization failed' response packet
     */
    private void f() throws IOException
    {
        this.a(-1, 2, "");
    }

    /**
     * Splits the response message into individual packets and sends each one
     */
    private void a(int par1, String par2Str) throws IOException
    {
        int var3 = par2Str.length();

        do
        {
            int var4 = 4096 <= var3 ? 4096 : var3;
            this.a(par1, 0, par2Str.substring(0, var4));
            par2Str = par2Str.substring(var4);
            var3 = par2Str.length();
        }
        while (0 != var3);
    }

    /**
     * Closes the client socket
     */
    private void g()
    {
        if (null != this.h)
        {
            try
            {
                this.h.close();
            }
            catch (IOException var2)
            {
                this.warning("IO: " + var2.getMessage());
            }

            this.h = null;
        }
    }
}
