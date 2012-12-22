package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet131ItemData extends Packet
{
    public short a;

    /**
     * Contains a unique ID for the item that this packet will be populating.
     */
    public short b;

    /**
     * Contains a buffer of arbitrary data with which to populate an individual item in the world.
     */
    public byte[] c;

    public Packet131ItemData()
    {
        this.lowPriority = true;
    }

    public Packet131ItemData(short par1, short par2, byte[] par3ArrayOfByte)
    {
        this.lowPriority = true;
        this.a = par1;
        this.b = par2;
        this.c = par3ArrayOfByte;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readShort();
        this.b = par1DataInputStream.readShort();
        this.c = new byte[par1DataInputStream.readUnsignedShort()];
        par1DataInputStream.readFully(this.c);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeShort(this.a);
        par1DataOutputStream.writeShort(this.b);
        par1DataOutputStream.writeShort(this.c.length);
        par1DataOutputStream.write(this.c);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(Connection par1NetHandler)
    {
        par1NetHandler.a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 4 + this.c.length;
    }
}
