package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet70Bed extends Packet
{
    public static final String[] a = new String[] {"tile.bed.notValid", null, null, "gameMode.changed"};

    /**
     * Either 1 or 2. 1 indicates to begin raining, 2 indicates to stop raining.
     */
    public int b;

    /** Used only when reason = 3. 0 is survival, 1 is creative. */
    public int c;

    public Packet70Bed() {}

    public Packet70Bed(int par1, int par2)
    {
        this.b = par1;
        this.c = par2;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.b = par1DataInputStream.readByte();
        this.c = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.b);
        par1DataOutputStream.writeByte(this.c);
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
        return 2;
    }
}
