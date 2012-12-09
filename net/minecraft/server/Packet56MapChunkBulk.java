package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Packet56MapChunkBulk extends Packet
{
    private int[] c;
    private int[] d;
    public int[] a;
    public int[] b;
    private byte[] buffer;
    private byte[][] inflatedBuffers;
    private int size;
    private static byte[] buildBuffer = new byte[0];

    public Packet56MapChunkBulk() {}

    public Packet56MapChunkBulk(List par1List)
    {
        int var2 = par1List.size();
        this.c = new int[var2];
        this.d = new int[var2];
        this.a = new int[var2];
        this.b = new int[var2];
        this.inflatedBuffers = new byte[var2][];
        int var3 = 0;

        for (int var4 = 0; var4 < var2; ++var4)
        {
            Chunk var5 = (Chunk)par1List.get(var4);
            ChunkMap var6 = Packet51MapChunk.a(var5, true, 65535);

            if (buildBuffer.length < var3 + var6.a.length)
            {
                byte[] var7 = new byte[var3 + var6.a.length];
                System.arraycopy(buildBuffer, 0, var7, 0, buildBuffer.length);
                buildBuffer = var7;
            }

            System.arraycopy(var6.a, 0, buildBuffer, var3, var6.a.length);
            var3 += var6.a.length;
            this.c[var4] = var5.x;
            this.d[var4] = var5.z;
            this.a[var4] = var6.b;
            this.b[var4] = var6.c;
            this.inflatedBuffers[var4] = var6.a;
        }

        Deflater var11 = new Deflater(-1);

        try
        {
            var11.setInput(buildBuffer, 0, var3);
            var11.finish();
            this.buffer = new byte[var3];
            this.size = var11.deflate(this.buffer);
        }
        finally
        {
            var11.end();
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        short var2 = par1DataInputStream.readShort();
        this.size = par1DataInputStream.readInt();
        this.c = new int[var2];
        this.d = new int[var2];
        this.a = new int[var2];
        this.b = new int[var2];
        this.inflatedBuffers = new byte[var2][];

        if (buildBuffer.length < this.size)
        {
            buildBuffer = new byte[this.size];
        }

        par1DataInputStream.readFully(buildBuffer, 0, this.size);
        byte[] var3 = new byte[196864 * var2];
        Inflater var4 = new Inflater();
        var4.setInput(buildBuffer, 0, this.size);

        try
        {
            var4.inflate(var3);
        }
        catch (DataFormatException var11)
        {
            throw new IOException("Bad compressed data format");
        }
        finally
        {
            var4.end();
        }

        int var5 = 0;

        for (int var6 = 0; var6 < var2; ++var6)
        {
            this.c[var6] = par1DataInputStream.readInt();
            this.d[var6] = par1DataInputStream.readInt();
            this.a[var6] = par1DataInputStream.readShort();
            this.b[var6] = par1DataInputStream.readShort();
            int var7 = 0;
            int var8;

            for (var8 = 0; var8 < 16; ++var8)
            {
                var7 += this.a[var6] >> var8 & 1;
            }

            var8 = 2048 * 5 * var7 + 256;
            this.inflatedBuffers[var6] = new byte[var8];
            System.arraycopy(var3, var5, this.inflatedBuffers[var6], 0, var8);
            var5 += var8;
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeShort(this.c.length);
        par1DataOutputStream.writeInt(this.size);
        par1DataOutputStream.write(this.buffer, 0, this.size);

        for (int var2 = 0; var2 < this.c.length; ++var2)
        {
            par1DataOutputStream.writeInt(this.c[var2]);
            par1DataOutputStream.writeInt(this.d[var2]);
            par1DataOutputStream.writeShort((short)(this.a[var2] & 65535));
            par1DataOutputStream.writeShort((short)(this.b[var2] & 65535));
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
        return 6 + this.size + 12 * this.d();
    }

    public int d()
    {
        return this.c.length;
    }
}
