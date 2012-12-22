package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet62NamedSoundEffect extends Packet
{
    /** e.g. step.grass */
    private String a;

    /** Effect X multiplied by 8 */
    private int b;

    /** Effect Y multiplied by 8 */
    private int c = Integer.MAX_VALUE;

    /** Effect Z multiplied by 8 */
    private int d;

    /** 1 is 100%. Can be more. */
    private float e;

    /** 63 is 100%. Can be more. */
    private int f;

    public Packet62NamedSoundEffect() {}

    public Packet62NamedSoundEffect(String par1Str, double par2, double par4, double par6, float par8, float par9)
    {
        this.a = par1Str;
        this.b = (int)(par2 * 8.0D);
        this.c = (int)(par4 * 8.0D);
        this.d = (int)(par6 * 8.0D);
        this.e = par8;
        this.f = (int)(par9 * 63.0F);

        if (this.f < 0)
        {
            this.f = 0;
        }

        if (this.f > 255)
        {
            this.f = 255;
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = a(par1DataInputStream, 32);
        this.b = par1DataInputStream.readInt();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readInt();
        this.e = par1DataInputStream.readFloat();
        this.f = par1DataInputStream.readUnsignedByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        a(this.a, par1DataOutputStream);
        par1DataOutputStream.writeInt(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeInt(this.d);
        par1DataOutputStream.writeFloat(this.e);
        par1DataOutputStream.writeByte(this.f);
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
        return 24;
    }
}
