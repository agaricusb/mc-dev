package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet21PickupSpawn extends Packet
{
    /** Unique entity ID. */
    public int a;

    /** The item X position. */
    public int b;

    /** The item Y position. */
    public int c;

    /** The item Z position. */
    public int d;

    /** The item rotation. */
    public byte e;

    /** The item pitch. */
    public byte f;

    /** The item roll. */
    public byte g;
    public ItemStack h;

    public Packet21PickupSpawn() {}

    public Packet21PickupSpawn(EntityItem par1EntityItem)
    {
        this.a = par1EntityItem.id;
        this.h = par1EntityItem.itemStack.cloneItemStack();
        this.b = MathHelper.floor(par1EntityItem.locX * 32.0D);
        this.c = MathHelper.floor(par1EntityItem.locY * 32.0D);
        this.d = MathHelper.floor(par1EntityItem.locZ * 32.0D);
        this.e = (byte)((int)(par1EntityItem.motX * 128.0D));
        this.f = (byte)((int)(par1EntityItem.motY * 128.0D));
        this.g = (byte)((int)(par1EntityItem.motZ * 128.0D));
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.h = c(par1DataInputStream);
        this.b = par1DataInputStream.readInt();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readInt();
        this.e = par1DataInputStream.readByte();
        this.f = par1DataInputStream.readByte();
        this.g = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        a(this.h, par1DataOutputStream);
        par1DataOutputStream.writeInt(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeInt(this.d);
        par1DataOutputStream.writeByte(this.e);
        par1DataOutputStream.writeByte(this.f);
        par1DataOutputStream.writeByte(this.g);
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
        return 24;
    }
}
