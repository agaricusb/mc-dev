package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet34EntityTeleport extends Packet
{
    /** ID of the entity. */
    public int a;

    /** X position of the entity. */
    public int b;

    /** Y position of the entity. */
    public int c;

    /** Z position of the entity. */
    public int d;

    /** Yaw of the entity. */
    public byte e;

    /** Pitch of the entity. */
    public byte f;

    public Packet34EntityTeleport() {}

    public Packet34EntityTeleport(Entity par1Entity)
    {
        this.a = par1Entity.id;
        this.b = MathHelper.floor(par1Entity.locX * 32.0D);
        this.c = MathHelper.floor(par1Entity.locY * 32.0D);
        this.d = MathHelper.floor(par1Entity.locZ * 32.0D);
        this.e = (byte)((int)(par1Entity.yaw * 256.0F / 360.0F));
        this.f = (byte)((int)(par1Entity.pitch * 256.0F / 360.0F));
    }

    public Packet34EntityTeleport(int par1, int par2, int par3, int par4, byte par5, byte par6)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
        this.d = par4;
        this.e = par5;
        this.f = par6;
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
        this.e = (byte)par1DataInputStream.read();
        this.f = (byte)par1DataInputStream.read();
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
        par1DataOutputStream.write(this.f);
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
        return 34;
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
        Packet34EntityTeleport var2 = (Packet34EntityTeleport)par1Packet;
        return var2.a == this.a;
    }
}
