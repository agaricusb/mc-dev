package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet204LocaleAndViewDistance extends Packet
{
    private String a;
    private int b;
    private int c;
    private boolean d;
    private int e;
    private boolean f;

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = a(par1DataInputStream, 7);
        this.b = par1DataInputStream.readByte();
        byte var2 = par1DataInputStream.readByte();
        this.c = var2 & 7;
        this.d = (var2 & 8) == 8;
        this.e = par1DataInputStream.readByte();
        this.f = par1DataInputStream.readBoolean();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        a(this.a, par1DataOutputStream);
        par1DataOutputStream.writeByte(this.b);
        par1DataOutputStream.writeByte(this.c | (this.d ? 1 : 0) << 3);
        par1DataOutputStream.writeByte(this.e);
        par1DataOutputStream.writeBoolean(this.f);
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
        return 7;
    }

    public String d()
    {
        return this.a;
    }

    public int f()
    {
        return this.b;
    }

    public int g()
    {
        return this.c;
    }

    public boolean h()
    {
        return this.d;
    }

    public int i()
    {
        return this.e;
    }

    public boolean j()
    {
        return this.f;
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
