package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet35EntityHeadRotation extends Packet
{
    public int a;
    public byte b;

    public Packet35EntityHeadRotation() {}

    public Packet35EntityHeadRotation(int par1, byte par2)
    {
        this.a = par1;
        this.b = par2;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeByte(this.b);
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
        return 5;
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
        Packet35EntityHeadRotation var2 = (Packet35EntityHeadRotation)par1Packet;
        return var2.a == this.a;
    }

    /**
     * If this returns true, the packet may be processed on any thread; otherwise it is queued for the main thread to
     * handle.
     */
    public boolean a_()
    {
        return true;
    }
}
