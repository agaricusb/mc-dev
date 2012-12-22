package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet55BlockBreakAnimation extends Packet
{
    /** Entity breaking the block */
    private int a;

    /** X posiiton of the block */
    private int b;

    /** Y position of the block */
    private int c;

    /** Z position of the block */
    private int d;

    /** How far destroyed this block is */
    private int e;

    public Packet55BlockBreakAnimation() {}

    public Packet55BlockBreakAnimation(int par1, int par2, int par3, int par4, int par5)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
        this.d = par4;
        this.e = par5;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readInt();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readInt();
        this.e = par1DataInputStream.read();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeInt(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeInt(this.d);
        par1DataOutputStream.write(this.e);
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
        return 13;
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
        Packet55BlockBreakAnimation var2 = (Packet55BlockBreakAnimation)par1Packet;
        return var2.a == this.a;
    }
}
