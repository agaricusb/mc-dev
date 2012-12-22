package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet24MobSpawn extends Packet
{
    /** The entity ID. */
    public int a;

    /** The type of mob. */
    public int b;

    /** The X position of the entity. */
    public int c;

    /** The Y position of the entity. */
    public int d;

    /** The Z position of the entity. */
    public int e;
    public int f;
    public int g;
    public int h;

    /** The yaw of the entity. */
    public byte i;

    /** The pitch of the entity. */
    public byte j;

    /** The yaw of the entity's head. */
    public byte k;

    /** Indexed metadata for Mob, terminated by 0x7F */
    private DataWatcher s;
    private List t;

    public Packet24MobSpawn() {}

    public Packet24MobSpawn(EntityLiving par1EntityLiving)
    {
        this.a = par1EntityLiving.id;
        this.b = (byte) EntityTypes.a(par1EntityLiving);
        this.c = par1EntityLiving.as.a(par1EntityLiving.locX);
        this.d = MathHelper.floor(par1EntityLiving.locY * 32.0D);
        this.e = par1EntityLiving.as.a(par1EntityLiving.locZ);
        this.i = (byte)((int)(par1EntityLiving.yaw * 256.0F / 360.0F));
        this.j = (byte)((int)(par1EntityLiving.pitch * 256.0F / 360.0F));
        this.k = (byte)((int)(par1EntityLiving.az * 256.0F / 360.0F));
        double var2 = 3.9D;
        double var4 = par1EntityLiving.motX;
        double var6 = par1EntityLiving.motY;
        double var8 = par1EntityLiving.motZ;

        if (var4 < -var2)
        {
            var4 = -var2;
        }

        if (var6 < -var2)
        {
            var6 = -var2;
        }

        if (var8 < -var2)
        {
            var8 = -var2;
        }

        if (var4 > var2)
        {
            var4 = var2;
        }

        if (var6 > var2)
        {
            var6 = var2;
        }

        if (var8 > var2)
        {
            var8 = var2;
        }

        this.f = (int)(var4 * 8000.0D);
        this.g = (int)(var6 * 8000.0D);
        this.h = (int)(var8 * 8000.0D);
        this.s = par1EntityLiving.getDataWatcher();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readByte() & 255;
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readInt();
        this.e = par1DataInputStream.readInt();
        this.i = par1DataInputStream.readByte();
        this.j = par1DataInputStream.readByte();
        this.k = par1DataInputStream.readByte();
        this.f = par1DataInputStream.readShort();
        this.g = par1DataInputStream.readShort();
        this.h = par1DataInputStream.readShort();
        this.t = DataWatcher.a(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeByte(this.b & 255);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeInt(this.d);
        par1DataOutputStream.writeInt(this.e);
        par1DataOutputStream.writeByte(this.i);
        par1DataOutputStream.writeByte(this.j);
        par1DataOutputStream.writeByte(this.k);
        par1DataOutputStream.writeShort(this.f);
        par1DataOutputStream.writeShort(this.g);
        par1DataOutputStream.writeShort(this.h);
        this.s.a(par1DataOutputStream);
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
        return 26;
    }
}
