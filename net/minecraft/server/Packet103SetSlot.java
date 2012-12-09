package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet103SetSlot extends Packet
{
    /** The window which is being updated. 0 for player inventory */
    public int a;

    /** Slot that should be updated */
    public int b;

    /** Item stack */
    public ItemStack c;

    public Packet103SetSlot() {}

    public Packet103SetSlot(int par1, int par2, ItemStack par3ItemStack)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3ItemStack == null ? par3ItemStack : par3ItemStack.cloneItemStack();
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(NetHandler par1NetHandler)
    {
        par1NetHandler.a(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readByte();
        this.b = par1DataInputStream.readShort();
        this.c = c(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.a);
        par1DataOutputStream.writeShort(this.b);
        a(this.c, par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 8;
    }
}
