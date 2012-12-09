package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet53BlockChange extends Packet
{
    /** Block X position. */
    public int a;

    /** Block Y position. */
    public int b;

    /** Block Z position. */
    public int c;

    /** The new block type for the block. */
    public int material;

    /** Metadata of the block. */
    public int data;

    public Packet53BlockChange()
    {
        this.lowPriority = true;
    }

    public Packet53BlockChange(int par1, int par2, int par3, World par4World)
    {
        this.lowPriority = true;
        this.a = par1;
        this.b = par2;
        this.c = par3;
        this.material = par4World.getTypeId(par1, par2, par3);
        this.data = par4World.getData(par1, par2, par3);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.read();
        this.c = par1DataInputStream.readInt();
        this.material = par1DataInputStream.readShort();
        this.data = par1DataInputStream.read();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.write(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeShort(this.material);
        par1DataOutputStream.write(this.data);
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
        return 11;
    }
}
