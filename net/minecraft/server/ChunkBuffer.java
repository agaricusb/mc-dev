package net.minecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class ChunkBuffer extends ByteArrayOutputStream
{
    private int b;
    private int c;

    final RegionFile a;

    public ChunkBuffer(RegionFile par1RegionFile, int par2, int par3)
    {
        super(8096);
        this.a = par1RegionFile;
        this.b = par2;
        this.c = par3;
    }

    public void close() throws IOException
    {
        this.a.a(this.b, this.c, this.buf, this.count);
    }
}
