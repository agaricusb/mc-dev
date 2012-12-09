package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet61WorldEvent extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    private boolean f;

    public Packet61WorldEvent() {}

    public Packet61WorldEvent(int par1, int par2, int par3, int par4, int par5, boolean par6)
    {
        this.a = par1;
        this.c = par2;
        this.d = par3;
        this.e = par4;
        this.b = par5;
        this.f = par6;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readByte() & 255;
        this.e = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readInt();
        this.f = par1DataInputStream.readBoolean();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeByte(this.d & 255);
        par1DataOutputStream.writeInt(this.e);
        par1DataOutputStream.writeInt(this.b);
        par1DataOutputStream.writeBoolean(this.f);
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
        return 21;
    }
}
