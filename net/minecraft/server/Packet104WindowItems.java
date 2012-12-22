package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet104WindowItems extends Packet
{
    /**
     * The id of window which items are being sent for. 0 for player inventory.
     */
    public int a;

    /** Stack of items */
    public ItemStack[] b;

    public Packet104WindowItems() {}

    public Packet104WindowItems(int par1, List par2List)
    {
        this.a = par1;
        this.b = new ItemStack[par2List.size()];

        for (int var3 = 0; var3 < this.b.length; ++var3)
        {
            ItemStack var4 = (ItemStack)par2List.get(var3);
            this.b[var3] = var4 == null ? null : var4.cloneItemStack();
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readByte();
        short var2 = par1DataInputStream.readShort();
        this.b = new ItemStack[var2];

        for (int var3 = 0; var3 < var2; ++var3)
        {
            this.b[var3] = c(par1DataInputStream);
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.a);
        par1DataOutputStream.writeShort(this.b.length);

        for (int var2 = 0; var2 < this.b.length; ++var2)
        {
            a(this.b[var2], par1DataOutputStream);
        }
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
        return 3 + this.b.length * 5;
    }
}
