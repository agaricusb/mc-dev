package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet14BlockDig extends Packet
{
    /** Block X position. */
    public int a;

    /** Block Y position. */
    public int b;

    /** Block Z position. */
    public int c;

    /** Punched face of the block. */
    public int face;

    /** Status of the digging (started, ongoing, broken). */
    public int e;

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.e = par1DataInputStream.read();
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.read();
        this.c = par1DataInputStream.readInt();
        this.face = par1DataInputStream.read();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.write(this.e);
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.write(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.write(this.face);
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
        return 11;
    }
}
