package net.minecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet52MultiBlockChange extends Packet
{
    /** Chunk X position. */
    public int a;

    /** Chunk Z position. */
    public int b;

    /** The metadata for each block changed. */
    public byte[] c;

    /** The size of the arrays. */
    public int d;
    private static byte[] e = new byte[0];

    public Packet52MultiBlockChange()
    {
        this.lowPriority = true;
    }

    public Packet52MultiBlockChange(int par1, int par2, short[] par3ArrayOfShort, int par4, World par5World)
    {
        this.lowPriority = true;
        this.a = par1;
        this.b = par2;
        this.d = par4;
        int var6 = 4 * par4;
        Chunk var7 = par5World.getChunkAt(par1, par2);

        try
        {
            if (par4 >= 64)
            {
                System.out.println("ChunkTilesUpdatePacket compress " + par4);

                if (e.length < var6)
                {
                    e = new byte[var6];
                }
            }
            else
            {
                ByteArrayOutputStream var8 = new ByteArrayOutputStream(var6);
                DataOutputStream var9 = new DataOutputStream(var8);

                for (int var10 = 0; var10 < par4; ++var10)
                {
                    int var11 = par3ArrayOfShort[var10] >> 12 & 15;
                    int var12 = par3ArrayOfShort[var10] >> 8 & 15;
                    int var13 = par3ArrayOfShort[var10] & 255;
                    var9.writeShort(par3ArrayOfShort[var10]);
                    var9.writeShort((short)((var7.getTypeId(var11, var13, var12) & 4095) << 4 | var7.getData(var11, var13, var12) & 15));
                }

                this.c = var8.toByteArray();

                if (this.c.length != var6)
                {
                    throw new RuntimeException("Expected length " + var6 + " doesn\'t match received length " + this.c.length);
                }
            }
        }
        catch (IOException var14)
        {
            System.err.println(var14.getMessage());
            this.c = null;
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readShort() & 65535;
        int var2 = par1DataInputStream.readInt();

        if (var2 > 0)
        {
            this.c = new byte[var2];
            par1DataInputStream.readFully(this.c);
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeInt(this.b);
        par1DataOutputStream.writeShort((short)this.d);

        if (this.c != null)
        {
            par1DataOutputStream.writeInt(this.c.length);
            par1DataOutputStream.write(this.c);
        }
        else
        {
            par1DataOutputStream.writeInt(0);
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
        return 10 + this.d * 4;
    }
}
