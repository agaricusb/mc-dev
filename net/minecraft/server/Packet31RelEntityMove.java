package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet31RelEntityMove extends Packet30Entity
{
    public Packet31RelEntityMove() {}

    public Packet31RelEntityMove(int par1, byte par2, byte par3, byte par4)
    {
        super(par1);
        this.b = par2;
        this.c = par3;
        this.d = par4;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        super.a(par1DataInputStream);
        this.b = par1DataInputStream.readByte();
        this.c = par1DataInputStream.readByte();
        this.d = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        super.a(par1DataOutputStream);
        par1DataOutputStream.writeByte(this.b);
        par1DataOutputStream.writeByte(this.c);
        par1DataOutputStream.writeByte(this.d);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 7;
    }
}
