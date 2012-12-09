package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet5EntityEquipment extends Packet
{
    /** Entity ID of the object. */
    public int a;

    /** Equipment slot: 0=held, 1-4=armor slot */
    public int b;

    /** The item in the slot format (an ItemStack) */
    private ItemStack c;

    public Packet5EntityEquipment() {}

    public Packet5EntityEquipment(int par1, int par2, ItemStack par3ItemStack)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3ItemStack == null ? null : par3ItemStack.cloneItemStack();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readShort();
        this.c = c(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeShort(this.b);
        a(this.c, par1DataOutputStream);
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
        Packet5EntityEquipment var2 = (Packet5EntityEquipment)par1Packet;
        return var2.a == this.a && var2.b == this.b;
    }
}
