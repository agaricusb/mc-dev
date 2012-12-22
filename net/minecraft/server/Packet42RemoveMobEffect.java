package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet42RemoveMobEffect extends Packet
{
    /** The ID of the entity which an effect is being removed from. */
    public int a;

    /** The ID of the effect which is being removed from an entity. */
    public byte b;

    public Packet42RemoveMobEffect() {}

    public Packet42RemoveMobEffect(int par1, MobEffect par2PotionEffect)
    {
        this.a = par1;
        this.b = (byte)(par2PotionEffect.getEffectId() & 255);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeByte(this.b);
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
        return 5;
    }
}
