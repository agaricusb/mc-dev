package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet25EntityPainting extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public String f;

    public Packet25EntityPainting() {}

    public Packet25EntityPainting(EntityPainting par1EntityPainting)
    {
        this.a = par1EntityPainting.id;
        this.b = par1EntityPainting.x;
        this.c = par1EntityPainting.y;
        this.d = par1EntityPainting.z;
        this.e = par1EntityPainting.direction;
        this.f = par1EntityPainting.art.B;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.f = a(par1DataInputStream, EnumArt.A);
        this.b = par1DataInputStream.readInt();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readInt();
        this.e = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        a(this.f, par1DataOutputStream);
        par1DataOutputStream.writeInt(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeInt(this.d);
        par1DataOutputStream.writeInt(this.e);
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
        return 24;
    }
}
