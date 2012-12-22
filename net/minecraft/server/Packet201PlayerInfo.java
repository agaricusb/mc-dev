package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet201PlayerInfo extends Packet
{
    /** The player's name. */
    public String a;

    /** Byte that tells whether the player is connected. */
    public boolean b;
    public int c;

    public Packet201PlayerInfo() {}

    public Packet201PlayerInfo(String par1Str, boolean par2, int par3)
    {
        this.a = par1Str;
        this.b = par2;
        this.c = par3;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = a(par1DataInputStream, 16);
        this.b = par1DataInputStream.readByte() != 0;
        this.c = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        a(this.a, par1DataOutputStream);
        par1DataOutputStream.writeByte(this.b ? 1 : 0);
        par1DataOutputStream.writeShort(this.c);
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
        return this.a.length() + 2 + 1 + 2;
    }
}
