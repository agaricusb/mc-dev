package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet6SpawnPosition extends Packet
{
    /** X coordinate of spawn. */
    public int x;

    /** Y coordinate of spawn. */
    public int y;

    /** Z coordinate of spawn. */
    public int z;

    public Packet6SpawnPosition() {}

    public Packet6SpawnPosition(int par1, int par2, int par3)
    {
        this.x = par1;
        this.y = par2;
        this.z = par3;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.x = par1DataInputStream.readInt();
        this.y = par1DataInputStream.readInt();
        this.z = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.x);
        par1DataOutputStream.writeInt(this.y);
        par1DataOutputStream.writeInt(this.z);
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
        return 12;
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

    /**
     * if this returns false, processPacket is deffered for processReadPackets to handle
     */
    public boolean a_()
    {
        return false;
    }
}
