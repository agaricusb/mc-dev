package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet29DestroyEntity extends Packet
{
    /** ID of the entity to be destroyed on the client. */
    public int[] a;

    public Packet29DestroyEntity() {}

    public Packet29DestroyEntity(int... par1ArrayOfInteger)
    {
        this.a = par1ArrayOfInteger;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = new int[par1DataInputStream.readByte()];

        for (int var2 = 0; var2 < this.a.length; ++var2)
        {
            this.a[var2] = par1DataInputStream.readInt();
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.a.length);

        for (int var2 = 0; var2 < this.a.length; ++var2)
        {
            par1DataOutputStream.writeInt(this.a[var2]);
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
        return 1 + this.a.length * 4;
    }
}
