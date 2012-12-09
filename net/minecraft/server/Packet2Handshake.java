package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet2Handshake extends Packet
{
    private int a;
    private String b;
    private String c;
    private int d;

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readByte();
        this.b = a(par1DataInputStream, 16);
        this.c = a(par1DataInputStream, 255);
        this.d = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.a);
        a(this.b, par1DataOutputStream);
        a(this.c, par1DataOutputStream);
        par1DataOutputStream.writeInt(this.d);
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
        return 3 + 2 * this.b.length();
    }

    /**
     * Returns the protocol version.
     */
    public int d()
    {
        return this.a;
    }

    /**
     * Returns the username.
     */
    public String f()
    {
        return this.b;
    }
}
