package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Packet51MapChunk extends Packet
{
    /** The x-position of the transmitted chunk, in chunk coordinates. */
    public int a;

    /** The z-position of the transmitted chunk, in chunk coordinates. */
    public int b;

    /**
     * The y-position of the lowest chunk Section in the transmitted chunk, in chunk coordinates.
     */
    public int c;

    /**
     * The y-position of the highest chunk Section in the transmitted chunk, in chunk coordinates.
     */
    public int d;

    /** The transmitted chunk data, decompressed. */
    private byte[] buffer;

    /** The compressed chunk data */
    private byte[] inflatedBuffer;

    /**
     * Whether to initialize the Chunk before applying the effect of the Packet51MapChunk.
     */
    public boolean e;

    /** The length of the compressed chunk data byte array. */
    private int size;

    /** A temporary storage for the compressed chunk data byte array. */
    private static byte[] buildBuffer = new byte[196864];

    public Packet51MapChunk()
    {
        this.lowPriority = true;
    }

    public Packet51MapChunk(Chunk par1Chunk, boolean par2, int par3)
    {
        this.lowPriority = true;
        this.a = par1Chunk.x;
        this.b = par1Chunk.z;
        this.e = par2;
        ChunkMap var4 = a(par1Chunk, par2, par3);
        Deflater var5 = new Deflater(-1);
        this.d = var4.c;
        this.c = var4.b;

        try
        {
            this.inflatedBuffer = var4.a;
            var5.setInput(var4.a, 0, var4.a.length);
            var5.finish();
            this.buffer = new byte[var4.a.length];
            this.size = var5.deflate(this.buffer);
        }
        finally
        {
            var5.end();
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readInt();
        this.e = par1DataInputStream.readBoolean();
        this.c = par1DataInputStream.readShort();
        this.d = par1DataInputStream.readShort();
        this.size = par1DataInputStream.readInt();

        if (buildBuffer.length < this.size)
        {
            buildBuffer = new byte[this.size];
        }

        par1DataInputStream.readFully(buildBuffer, 0, this.size);
        int var2 = 0;
        int var3;

        for (var3 = 0; var3 < 16; ++var3)
        {
            var2 += this.c >> var3 & 1;
        }

        var3 = 12288 * var2;

        if (this.e)
        {
            var3 += 256;
        }

        this.inflatedBuffer = new byte[var3];
        Inflater var4 = new Inflater();
        var4.setInput(buildBuffer, 0, this.size);

        try
        {
            var4.inflate(this.inflatedBuffer);
        }
        catch (DataFormatException var9)
        {
            throw new IOException("Bad compressed data format");
        }
        finally
        {
            var4.end();
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeInt(this.b);
        par1DataOutputStream.writeBoolean(this.e);
        par1DataOutputStream.writeShort((short)(this.c & 65535));
        par1DataOutputStream.writeShort((short)(this.d & 65535));
        par1DataOutputStream.writeInt(this.size);
        par1DataOutputStream.write(this.buffer, 0, this.size);
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
        return 17 + this.size;
    }

    public static ChunkMap a(Chunk par0Chunk, boolean par1, int par2)
    {
        int var3 = 0;
        ChunkSection[] var4 = par0Chunk.i();
        int var5 = 0;
        ChunkMap var6 = new ChunkMap();
        byte[] var7 = buildBuffer;

        if (par1)
        {
            par0Chunk.seenByPlayer = true;
        }

        int var8;

        for (var8 = 0; var8 < var4.length; ++var8)
        {
            if (var4[var8] != null && (!par1 || !var4[var8].a()) && (par2 & 1 << var8) != 0)
            {
                var6.b |= 1 << var8;

                if (var4[var8].i() != null)
                {
                    var6.c |= 1 << var8;
                    ++var5;
                }
            }
        }

        for (var8 = 0; var8 < var4.length; ++var8)
        {
            if (var4[var8] != null && (!par1 || !var4[var8].a()) && (par2 & 1 << var8) != 0)
            {
                byte[] var9 = var4[var8].g();
                System.arraycopy(var9, 0, var7, var3, var9.length);
                var3 += var9.length;
            }
        }

        NibbleArray var10;

        for (var8 = 0; var8 < var4.length; ++var8)
        {
            if (var4[var8] != null && (!par1 || !var4[var8].a()) && (par2 & 1 << var8) != 0)
            {
                var10 = var4[var8].j();
                System.arraycopy(var10.a, 0, var7, var3, var10.a.length);
                var3 += var10.a.length;
            }
        }

        for (var8 = 0; var8 < var4.length; ++var8)
        {
            if (var4[var8] != null && (!par1 || !var4[var8].a()) && (par2 & 1 << var8) != 0)
            {
                var10 = var4[var8].k();
                System.arraycopy(var10.a, 0, var7, var3, var10.a.length);
                var3 += var10.a.length;
            }
        }

        if (!par0Chunk.world.worldProvider.f)
        {
            for (var8 = 0; var8 < var4.length; ++var8)
            {
                if (var4[var8] != null && (!par1 || !var4[var8].a()) && (par2 & 1 << var8) != 0)
                {
                    var10 = var4[var8].l();
                    System.arraycopy(var10.a, 0, var7, var3, var10.a.length);
                    var3 += var10.a.length;
                }
            }
        }

        if (var5 > 0)
        {
            for (var8 = 0; var8 < var4.length; ++var8)
            {
                if (var4[var8] != null && (!par1 || !var4[var8].a()) && var4[var8].i() != null && (par2 & 1 << var8) != 0)
                {
                    var10 = var4[var8].i();
                    System.arraycopy(var10.a, 0, var7, var3, var10.a.length);
                    var3 += var10.a.length;
                }
            }
        }

        if (par1)
        {
            byte[] var11 = par0Chunk.m();
            System.arraycopy(var11, 0, var7, var3, var11.length);
            var3 += var11.length;
        }

        var6.a = new byte[var3];
        System.arraycopy(var7, 0, var6.a, 0, var3);
        return var6;
    }
}
