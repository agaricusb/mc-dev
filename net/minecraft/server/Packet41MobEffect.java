package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet41MobEffect extends Packet
{
    public int a;
    public byte b;

    /** The effect's amplifier. */
    public byte c;
    public short d;

    public Packet41MobEffect() {}

    public Packet41MobEffect(int par1, MobEffect par2PotionEffect)
    {
        this.a = par1;
        this.b = (byte)(par2PotionEffect.getEffectId() & 255);
        this.c = (byte)(par2PotionEffect.getAmplifier() & 255);
        this.d = (short)par2PotionEffect.getDuration();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readByte();
        this.c = par1DataInputStream.readByte();
        this.d = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeByte(this.b);
        par1DataOutputStream.writeByte(this.c);
        par1DataOutputStream.writeShort(this.d);
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
        return 8;
    }

    /**
     * only false for the abstract Packet class, all real packets return true
     */
    public boolean e()
    {
        return true;
    }

    /**
     * eg return packet30entity.entityId == entityId; WARNING : will throw if you compare a packet to a different packet
     * class
     */
    public boolean a(Packet par1Packet)
    {
        Packet41MobEffect var2 = (Packet41MobEffect)par1Packet;
        return var2.a == this.a && var2.b == this.b;
    }
}
