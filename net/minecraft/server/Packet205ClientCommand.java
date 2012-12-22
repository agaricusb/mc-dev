package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet205ClientCommand extends Packet
{
    /**
     * 0 sent to a netLoginHandler starts the server, 1 sent to NetServerHandler forces a respawn
     */
    public int a;

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.a & 255);
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
        return 1;
    }
}
