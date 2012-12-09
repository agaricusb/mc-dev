package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet132TileEntityData extends Packet
{
    /** The X position of the tile entity to update. */
    public int a;

    /** The Y position of the tile entity to update. */
    public int b;

    /** The Z position of the tile entity to update. */
    public int c;

    /** The type of update to perform on the tile entity. */
    public int d;

    /** Custom parameter 1 passed to the tile entity on update. */
    public NBTTagCompound e;

    public Packet132TileEntityData()
    {
        this.lowPriority = true;
    }

    public Packet132TileEntityData(int par1, int par2, int par3, int par4, NBTTagCompound par5NBTTagCompound)
    {
        this.lowPriority = true;
        this.a = par1;
        this.b = par2;
        this.c = par3;
        this.d = par4;
        this.e = par5NBTTagCompound;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readShort();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readByte();
        this.e = d(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeShort(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeByte((byte)this.d);
        a(this.e, par1DataOutputStream);
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
        return 25;
    }
}
