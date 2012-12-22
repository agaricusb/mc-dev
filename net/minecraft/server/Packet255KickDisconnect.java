package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet255KickDisconnect extends Packet
{
    /** Displayed to the client when the connection terminates. */
    public String a;

    public Packet255KickDisconnect() {}

    public Packet255KickDisconnect(String par1Str)
    {
        this.a = par1Str;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = a(par1DataInputStream, 256);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        a(this.a, par1DataOutputStream);
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
        return this.a.length();
    }

    /**
     * only false for the abstract Packet class, all real packets return true
     */
    public boolean e()
    {
        return true;
    }

    /**
     * eg return packet30entity.entityId == entityId; WARNING : will throw if you compare a packet to a different packet
     * class
     */
    public boolean a(Packet par1Packet)
    {
        return true;
    }
}
