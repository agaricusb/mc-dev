package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet8UpdateHealth extends Packet
{
    /** Variable used for incoming health packets */
    public int a;
    public int b;

    /**
     * Players logging on get a saturation of 5.0. Eating food increases the saturation as well as the food bar.
     */
    public float c;

    public Packet8UpdateHealth() {}

    public Packet8UpdateHealth(int par1, int par2, float par3)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readShort();
        this.b = par1DataInputStream.readShort();
        this.c = par1DataInputStream.readFloat();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeShort(this.a);
        par1DataOutputStream.writeShort(this.b);
        par1DataOutputStream.writeFloat(this.c);
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
        return 8;
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
