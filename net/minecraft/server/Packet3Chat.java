package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet3Chat extends Packet
{
    /** Maximum number of characters allowed in chat string in each packet. */
    public static int a = 119;

    /** The message being sent. */
    public String message;
    private boolean c;

    public Packet3Chat()
    {
        this.c = true;
    }

    public Packet3Chat(String par1Str)
    {
        this(par1Str, true);
    }

    public Packet3Chat(String par1Str, boolean par2)
    {
        this.c = true;

        if (par1Str.length() > a)
        {
            par1Str = par1Str.substring(0, a);
        }

        this.message = par1Str;
        this.c = par2;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.message = a(par1DataInputStream, a);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        a(this.message, par1DataOutputStream);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(NetHandler par1NetHandler)
    {
        par1NetHandler.a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 2 + this.message.length() * 2;
    }

    public boolean isServer()
    {
        return this.c;
    }

    /**
     * if this returns false, processPacket is deffered for processReadPackets to handle
     */
    public boolean a_()
    {
        return !this.message.startsWith("/");
    }
}
