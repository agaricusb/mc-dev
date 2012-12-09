package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet30Entity extends Packet
{
    /** The ID of this entity. */
    public int a;

    /** The X axis relative movement. */
    public byte b;

    /** The Y axis relative movement. */
    public byte c;

    /** The Z axis relative movement. */
    public byte d;

    /** The X axis rotation. */
    public byte e;

    /** The Y axis rotation. */
    public byte f;

    /** Boolean set to true if the entity is rotating. */
    public boolean g = false;

    public Packet30Entity() {}

    public Packet30Entity(int par1)
    {
        this.a = par1;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
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
        return 4;
    }

    public String toString()
    {
        return "Entity_" + super.toString();
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
        Packet30Entity var2 = (Packet30Entity)par1Packet;
        return var2.a == this.a;
    }
}
