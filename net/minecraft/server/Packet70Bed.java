package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet70Bed extends Packet
{
    /**
     * The client prints clientMessage[eventType] to chat when this packet is received.
     */
    public static final String[] a = new String[] {"tile.bed.notValid", null, null, "gameMode.changed"};

    /** 0: Invalid bed, 1: Rain starts, 2: Rain stops, 3: Game mode changed. */
    public int b;

    /**
     * When reason==3, the game mode to set.  See EnumGameType for a list of values.
     */
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
    public void handle(Connection par1NetHandler)
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
