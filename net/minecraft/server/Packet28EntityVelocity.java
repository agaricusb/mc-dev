package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet28EntityVelocity extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;

    public Packet28EntityVelocity() {}

    public Packet28EntityVelocity(Entity par1Entity)
    {
        this(par1Entity.id, par1Entity.motX, par1Entity.motY, par1Entity.motZ);
    }

    public Packet28EntityVelocity(int par1, double par2, double par4, double par6)
    {
        this.a = par1;
        double var8 = 3.9D;

        if (par2 < -var8)
        {
            par2 = -var8;
        }

        if (par4 < -var8)
        {
            par4 = -var8;
        }

        if (par6 < -var8)
        {
            par6 = -var8;
        }

        if (par2 > var8)
        {
            par2 = var8;
        }

        if (par4 > var8)
        {
            par4 = var8;
        }

        if (par6 > var8)
        {
            par6 = var8;
        }

        this.b = (int)(par2 * 8000.0D);
        this.c = (int)(par4 * 8000.0D);
        this.d = (int)(par6 * 8000.0D);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readShort();
        this.c = par1DataInputStream.readShort();
        this.d = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeShort(this.b);
        par1DataOutputStream.writeShort(this.c);
        par1DataOutputStream.writeShort(this.d);
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
        return 10;
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
        Packet28EntityVelocity var2 = (Packet28EntityVelocity)par1Packet;
        return var2.a == this.a;
    }
}
