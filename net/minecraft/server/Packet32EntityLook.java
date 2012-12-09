package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet32EntityLook extends Packet30Entity
{
    public Packet32EntityLook()
    {
        this.g = true;
    }

    public Packet32EntityLook(int par1, byte par2, byte par3)
    {
        super(par1);
        this.e = par2;
        this.f = par3;
        this.g = true;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        super.a(par1DataInputStream);
        this.e = par1DataInputStream.readByte();
        this.f = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        super.a(par1DataOutputStream);
        par1DataOutputStream.writeByte(this.e);
        par1DataOutputStream.writeByte(this.f);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 6;
    }
}
