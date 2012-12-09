package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet33RelEntityMoveLook extends Packet30Entity
{
    public Packet33RelEntityMoveLook()
    {
        this.g = true;
    }

    public Packet33RelEntityMoveLook(int par1, byte par2, byte par3, byte par4, byte par5, byte par6)
    {
        super(par1);
        this.b = par2;
        this.c = par3;
        this.d = par4;
        this.e = par5;
        this.f = par6;
        this.g = true;
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
        this.e = par1DataInputStream.readByte();
        this.f = par1DataInputStream.readByte();
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
        par1DataOutputStream.writeByte(this.e);
        par1DataOutputStream.writeByte(this.f);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 9;
    }
}
