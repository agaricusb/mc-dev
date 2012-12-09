package net.minecraft.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class RegionFile
{
    private static final byte[] a = new byte[4096];
    private final File b;
    private RandomAccessFile c;
    private final int[] d = new int[1024];
    private final int[] e = new int[1024];
    private ArrayList f;

    /** McRegion sizeDelta */
    private int g;
    private long h = 0L;

    public RegionFile(File par1File)
    {
        this.b = par1File;
        this.g = 0;

        try
        {
            if (par1File.exists())
            {
                this.h = par1File.lastModified();
            }

            this.c = new RandomAccessFile(par1File, "rw");
            int var2;

            if (this.c.length() < 4096L)
            {
                for (var2 = 0; var2 < 1024; ++var2)
                {
                    this.c.writeInt(0);
                }

                for (var2 = 0; var2 < 1024; ++var2)
                {
                    this.c.writeInt(0);
                }

                this.g += 8192;
            }

            if ((this.c.length() & 4095L) != 0L)
            {
                for (var2 = 0; (long)var2 < (this.c.length() & 4095L); ++var2)
                {
                    this.c.write(0);
                }
            }

            var2 = (int)this.c.length() / 4096;
            this.f = new ArrayList(var2);
            int var3;

            for (var3 = 0; var3 < var2; ++var3)
            {
                this.f.add(Boolean.valueOf(true));
            }

            this.f.set(0, Boolean.valueOf(false));
            this.f.set(1, Boolean.valueOf(false));
            this.c.seek(0L);
            int var4;

            for (var3 = 0; var3 < 1024; ++var3)
            {
                var4 = this.c.readInt();
                this.d[var3] = var4;

                if (var4 != 0 && (var4 >> 8) + (var4 & 255) <= this.f.size())
                {
                    for (int var5 = 0; var5 < (var4 & 255); ++var5)
                    {
                        this.f.set((var4 >> 8) + var5, Boolean.valueOf(false));
                    }
                }
            }

            for (var3 = 0; var3 < 1024; ++var3)
            {
                var4 = this.c.readInt();
                this.e[var3] = var4;
            }
        }
        catch (IOException var6)
        {
            var6.printStackTrace();
        }
    }

    /**
     * args: x, y - get uncompressed chunk stream from the region file
     */
    public synchronized DataInputStream a(int par1, int par2)
    {
        if (this.d(par1, par2))
        {
            return null;
        }
        else
        {
            try
            {
                int var3 = this.e(par1, par2);

                if (var3 == 0)
                {
                    return null;
                }
                else
                {
                    int var4 = var3 >> 8;
                    int var5 = var3 & 255;

                    if (var4 + var5 > this.f.size())
                    {
                        return null;
                    }
                    else
                    {
                        this.c.seek((long)(var4 * 4096));
                        int var6 = this.c.readInt();

                        if (var6 > 4096 * var5)
                        {
                            return null;
                        }
                        else if (var6 <= 0)
                        {
                            return null;
                        }
                        else
                        {
                            byte var7 = this.c.readByte();
                            byte[] var8;

                            if (var7 == 1)
                            {
                                var8 = new byte[var6 - 1];
                                this.c.read(var8);
                                return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(var8))));
                            }
                            else if (var7 == 2)
                            {
                                var8 = new byte[var6 - 1];
                                this.c.read(var8);
                                return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(var8))));
                            }
                            else
                            {
                                return null;
                            }
                        }
                    }
                }
            }
            catch (IOException var9)
            {
                return null;
            }
        }
    }

    /**
     * args: x, z - get an output stream used to write chunk data, data is on disk when the returned stream is closed
     */
    public DataOutputStream b(int par1, int par2)
    {
        return this.d(par1, par2) ? null : new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(this, par1, par2)));
    }

    /**
     * args: x, z, data, length - write chunk data at (x, z) to disk
     */
    protected synchronized void a(int par1, int par2, byte[] par3ArrayOfByte, int par4)
    {
        try
        {
            int var5 = this.e(par1, par2);
            int var6 = var5 >> 8;
            int var7 = var5 & 255;
            int var8 = (par4 + 5) / 4096 + 1;

            if (var8 >= 256)
            {
                return;
            }

            if (var6 != 0 && var7 == var8)
            {
                this.a(var6, par3ArrayOfByte, par4);
            }
            else
            {
                int var9;

                for (var9 = 0; var9 < var7; ++var9)
                {
                    this.f.set(var6 + var9, Boolean.valueOf(true));
                }

                var9 = this.f.indexOf(Boolean.valueOf(true));
                int var10 = 0;
                int var11;

                if (var9 != -1)
                {
                    for (var11 = var9; var11 < this.f.size(); ++var11)
                    {
                        if (var10 != 0)
                        {
                            if (((Boolean)this.f.get(var11)).booleanValue())
                            {
                                ++var10;
                            }
                            else
                            {
                                var10 = 0;
                            }
                        }
                        else if (((Boolean)this.f.get(var11)).booleanValue())
                        {
                            var9 = var11;
                            var10 = 1;
                        }

                        if (var10 >= var8)
                        {
                            break;
                        }
                    }
                }

                if (var10 >= var8)
                {
                    var6 = var9;
                    this.a(par1, par2, var9 << 8 | var8);

                    for (var11 = 0; var11 < var8; ++var11)
                    {
                        this.f.set(var6 + var11, Boolean.valueOf(false));
                    }

                    this.a(var6, par3ArrayOfByte, par4);
                }
                else
                {
                    this.c.seek(this.c.length());
                    var6 = this.f.size();

                    for (var11 = 0; var11 < var8; ++var11)
                    {
                        this.c.write(a);
                        this.f.add(Boolean.valueOf(false));
                    }

                    this.g += 4096 * var8;
                    this.a(var6, par3ArrayOfByte, par4);
                    this.a(par1, par2, var6 << 8 | var8);
                }
            }

            this.b(par1, par2, (int) (System.currentTimeMillis() / 1000L));
        }
        catch (IOException var12)
        {
            var12.printStackTrace();
        }
    }

    /**
     * args: sectorNumber, data, length - write the chunk data to this RegionFile
     */
    private void a(int par1, byte[] par2ArrayOfByte, int par3) throws IOException
    {
        this.c.seek((long)(par1 * 4096));
        this.c.writeInt(par3 + 1);
        this.c.writeByte(2);
        this.c.write(par2ArrayOfByte, 0, par3);
    }

    /**
     * args: x, z - check region bounds
     */
    private boolean d(int par1, int par2)
    {
        return par1 < 0 || par1 >= 32 || par2 < 0 || par2 >= 32;
    }

    /**
     * args: x, y - get chunk's offset in region file
     */
    private int e(int par1, int par2)
    {
        return this.d[par1 + par2 * 32];
    }

    /**
     * args: x, z, - true if chunk has been saved / converted
     */
    public boolean c(int par1, int par2)
    {
        return this.e(par1, par2) != 0;
    }

    /**
     * args: x, z, offset - sets the chunk's offset in the region file
     */
    private void a(int par1, int par2, int par3) throws IOException
    {
        this.d[par1 + par2 * 32] = par3;
        this.c.seek((long)((par1 + par2 * 32) * 4));
        this.c.writeInt(par3);
    }

    /**
     * args: x, z, timestamp - sets the chunk's write timestamp
     */
    private void b(int par1, int par2, int par3) throws IOException
    {
        this.e[par1 + par2 * 32] = par3;
        this.c.seek((long)(4096 + (par1 + par2 * 32) * 4));
        this.c.writeInt(par3);
    }

    /**
     * close this RegionFile and prevent further writes
     */
    public void c() throws IOException
    {
        if (this.c != null)
        {
            this.c.close();
        }
    }
}
