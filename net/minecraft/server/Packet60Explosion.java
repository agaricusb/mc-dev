package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Packet60Explosion extends Packet
{
    public double a;
    public double b;
    public double c;
    public float d;
    public List e;
    private float f;
    private float g;
    private float h;

    public Packet60Explosion() {}

    public Packet60Explosion(double par1, double par3, double par5, float par7, List par8List, Vec3D par9Vec3)
    {
        this.a = par1;
        this.b = par3;
        this.c = par5;
        this.d = par7;
        this.e = new ArrayList(par8List);

        if (par9Vec3 != null)
        {
            this.f = (float)par9Vec3.c;
            this.g = (float)par9Vec3.d;
            this.h = (float)par9Vec3.e;
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readDouble();
        this.b = par1DataInputStream.readDouble();
        this.c = par1DataInputStream.readDouble();
        this.d = par1DataInputStream.readFloat();
        int var2 = par1DataInputStream.readInt();
        this.e = new ArrayList(var2);
        int var3 = (int)this.a;
        int var4 = (int)this.b;
        int var5 = (int)this.c;

        for (int var6 = 0; var6 < var2; ++var6)
        {
            int var7 = par1DataInputStream.readByte() + var3;
            int var8 = par1DataInputStream.readByte() + var4;
            int var9 = par1DataInputStream.readByte() + var5;
            this.e.add(new ChunkPosition(var7, var8, var9));
        }

        this.f = par1DataInputStream.readFloat();
        this.g = par1DataInputStream.readFloat();
        this.h = par1DataInputStream.readFloat();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeDouble(this.a);
        par1DataOutputStream.writeDouble(this.b);
        par1DataOutputStream.writeDouble(this.c);
        par1DataOutputStream.writeFloat(this.d);
        par1DataOutputStream.writeInt(this.e.size());
        int var2 = (int)this.a;
        int var3 = (int)this.b;
        int var4 = (int)this.c;
        Iterator var5 = this.e.iterator();

        while (var5.hasNext())
        {
            ChunkPosition var6 = (ChunkPosition)var5.next();
            int var7 = var6.x - var2;
            int var8 = var6.y - var3;
            int var9 = var6.z - var4;
            par1DataOutputStream.writeByte(var7);
            par1DataOutputStream.writeByte(var8);
            par1DataOutputStream.writeByte(var9);
        }

        par1DataOutputStream.writeFloat(this.f);
        par1DataOutputStream.writeFloat(this.g);
        par1DataOutputStream.writeFloat(this.h);
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
        return 32 + this.e.size() * 3 + 3;
    }
}
