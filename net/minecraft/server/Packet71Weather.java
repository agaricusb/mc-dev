package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet71Weather extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;

    public Packet71Weather() {}

    public Packet71Weather(Entity par1Entity)
    {
        this.a = par1Entity.id;
        this.b = MathHelper.floor(par1Entity.locX * 32.0D);
        this.c = MathHelper.floor(par1Entity.locY * 32.0D);
        this.d = MathHelper.floor(par1Entity.locZ * 32.0D);

        if (par1Entity instanceof EntityLightning)
        {
            this.e = 1;
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.e = par1DataInputStream.readByte();
        this.b = par1DataInputStream.readInt();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeByte(this.e);
        par1DataOutputStream.writeInt(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeInt(this.d);
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
        return 17;
    }
}
