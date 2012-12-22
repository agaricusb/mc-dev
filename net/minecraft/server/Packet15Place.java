package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet15Place extends Packet
{
    private int a;
    private int b;
    private int c;

    /** The offset to use for block/item placement. */
    private int d;
    private ItemStack e;

    /** The offset from xPosition where the actual click took place */
    private float f;

    /** The offset from yPosition where the actual click took place */
    private float g;

    /** The offset from zPosition where the actual click took place */
    private float h;

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.read();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.read();
        this.e = c(par1DataInputStream);
        this.f = (float)par1DataInputStream.read() / 16.0F;
        this.g = (float)par1DataInputStream.read() / 16.0F;
        this.h = (float)par1DataInputStream.read() / 16.0F;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.write(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.write(this.d);
        a(this.e, par1DataOutputStream);
        par1DataOutputStream.write((int)(this.f * 16.0F));
        par1DataOutputStream.write((int)(this.g * 16.0F));
        par1DataOutputStream.write((int)(this.h * 16.0F));
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
        return 19;
    }

    public int d()
    {
        return this.a;
    }

    public int f()
    {
        return this.b;
    }

    public int g()
    {
        return this.c;
    }

    public int getFace()
    {
        return this.d;
    }

    public ItemStack getItemStack()
    {
        return this.e;
    }

    /**
     * Returns the offset from xPosition where the actual click took place
     */
    public float j()
    {
        return this.f;
    }

    /**
     * Returns the offset from yPosition where the actual click took place
     */
    public float l()
    {
        return this.g;
    }

    /**
     * Returns the offset from zPosition where the actual click took place
     */
    public float m()
    {
        return this.h;
    }
}
