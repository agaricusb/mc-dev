package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet100OpenWindow extends Packet
{
    public int a;
    public int b;
    public String c;
    public int d;

    public Packet100OpenWindow() {}

    public Packet100OpenWindow(int par1, int par2, String par3Str, int par4)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3Str;
        this.d = par4;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(NetHandler par1NetHandler)
    {
        par1NetHandler.a(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readByte() & 255;
        this.b = par1DataInputStream.readByte() & 255;
        this.c = a(par1DataInputStream, 32);
        this.d = par1DataInputStream.readByte() & 255;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.a & 255);
        par1DataOutputStream.writeByte(this.b & 255);
        a(this.c, par1DataOutputStream);
        par1DataOutputStream.writeByte(this.d & 255);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 3 + this.c.length();
    }
}
