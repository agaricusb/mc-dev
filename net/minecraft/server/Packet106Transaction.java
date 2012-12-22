package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet106Transaction extends Packet
{
    /** The id of the window that the action occurred in. */
    public int a;
    public short b;
    public boolean c;

    public Packet106Transaction() {}

    public Packet106Transaction(int par1, short par2, boolean par3)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(Connection par1NetHandler)
    {
        par1NetHandler.a(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readByte();
        this.b = par1DataInputStream.readShort();
        this.c = par1DataInputStream.readByte() != 0;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.a);
        par1DataOutputStream.writeShort(this.b);
        par1DataOutputStream.writeByte(this.c ? 1 : 0);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 4;
    }
}
