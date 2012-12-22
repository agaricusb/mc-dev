package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet102WindowClick extends Packet
{
    /** The id of the window which was clicked. 0 for player inventory. */
    public int a;

    /** The clicked slot (-999 is outside of inventory) */
    public int slot;

    /** 1 when right-clicking and otherwise 0 */
    public int button;

    /** A unique number for the action, used for transaction handling */
    public short d;

    /** Item stack for inventory */
    public ItemStack item;
    public int shift;

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(Connection par1NetHandler)
    {
        par1NetHandler.a(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readByte();
        this.slot = par1DataInputStream.readShort();
        this.button = par1DataInputStream.readByte();
        this.d = par1DataInputStream.readShort();
        this.shift = par1DataInputStream.readByte();
        this.item = c(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.a);
        par1DataOutputStream.writeShort(this.slot);
        par1DataOutputStream.writeByte(this.button);
        par1DataOutputStream.writeShort(this.d);
        par1DataOutputStream.writeByte(this.shift);
        a(this.item, par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 11;
    }
}
