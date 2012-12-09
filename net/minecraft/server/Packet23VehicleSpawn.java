package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet23VehicleSpawn extends Packet
{
    /** Entity ID of the object. */
    public int a;

    /** The X position of the object. */
    public int b;

    /** The Y position of the object. */
    public int c;

    /** The Z position of the object. */
    public int d;

    /**
     * Not sent if the thrower entity ID is 0. The speed of this fireball along the X axis.
     */
    public int e;

    /**
     * Not sent if the thrower entity ID is 0. The speed of this fireball along the Y axis.
     */
    public int f;

    /**
     * Not sent if the thrower entity ID is 0. The speed of this fireball along the Z axis.
     */
    public int g;

    /** The type of object. */
    public int h;

    /** 0 if not a fireball. Otherwise, this is the Entity ID of the thrower. */
    public int i;

    public Packet23VehicleSpawn() {}

    public Packet23VehicleSpawn(Entity par1Entity, int par2)
    {
        this(par1Entity, par2, 0);
    }

    public Packet23VehicleSpawn(Entity par1Entity, int par2, int par3)
    {
        this.a = par1Entity.id;
        this.b = MathHelper.floor(par1Entity.locX * 32.0D);
        this.c = MathHelper.floor(par1Entity.locY * 32.0D);
        this.d = MathHelper.floor(par1Entity.locZ * 32.0D);
        this.h = par2;
        this.i = par3;

        if (par3 > 0)
        {
            double var4 = par1Entity.motX;
            double var6 = par1Entity.motY;
            double var8 = par1Entity.motZ;
            double var10 = 3.9D;

            if (var4 < -var10)
            {
                var4 = -var10;
            }

            if (var6 < -var10)
            {
                var6 = -var10;
            }

            if (var8 < -var10)
            {
                var8 = -var10;
            }

            if (var4 > var10)
            {
                var4 = var10;
            }

            if (var6 > var10)
            {
                var6 = var10;
            }

            if (var8 > var10)
            {
                var8 = var10;
            }

            this.e = (int)(var4 * 8000.0D);
            this.f = (int)(var6 * 8000.0D);
            this.g = (int)(var8 * 8000.0D);
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.h = par1DataInputStream.readByte();
        this.b = par1DataInputStream.readInt();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readInt();
        this.i = par1DataInputStream.readInt();

        if (this.i > 0)
        {
            this.e = par1DataInputStream.readShort();
            this.f = par1DataInputStream.readShort();
            this.g = par1DataInputStream.readShort();
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeByte(this.h);
        par1DataOutputStream.writeInt(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeInt(this.d);
        par1DataOutputStream.writeInt(this.i);

        if (this.i > 0)
        {
            par1DataOutputStream.writeShort(this.e);
            par1DataOutputStream.writeShort(this.f);
            par1DataOutputStream.writeShort(this.g);
        }
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
        return 21 + this.i > 0 ? 6 : 0;
    }
}
