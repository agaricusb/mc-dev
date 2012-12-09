package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet54PlayNoteBlock extends Packet
{
    public int a;
    public int b;
    public int c;

    /** 1=Double Bass, 2=Snare Drum, 3=Clicks / Sticks, 4=Bass Drum, 5=Harp */
    public int d;

    /**
     * The pitch of the note (between 0-24 inclusive where 0 is the lowest and 24 is the highest).
     */
    public int e;

    /** The block ID this action is set for. */
    public int f;

    public Packet54PlayNoteBlock() {}

    public Packet54PlayNoteBlock(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
        this.d = par5;
        this.e = par6;
        this.f = par4;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readShort();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.read();
        this.e = par1DataInputStream.read();
        this.f = par1DataInputStream.readShort() & 4095;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeShort(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.write(this.d);
        par1DataOutputStream.write(this.e);
        par1DataOutputStream.writeShort(this.f & 4095);
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
        return 14;
    }
}
