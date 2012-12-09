package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet105CraftProgressBar extends Packet
{
    /** The id of the window that the progress bar is in. */
    public int a;

    /**
     * Which of the progress bars that should be updated. (For furnaces, 0 = progress arrow, 1 = fire icon)
     */
    public int b;

    /**
     * The value of the progress bar. The maximum values vary depending on the progress bar. Presumably the values are
     * specified as in-game ticks. Some progress bar values increase, while others decrease. For furnaces, 0 is empty,
     * full progress arrow = about 180, full fire icon = about 250)
     */
    public int c;

    public Packet105CraftProgressBar() {}

    public Packet105CraftProgressBar(int par1, int par2, int par3)
    {
        this.a = par1;
        this.b = par2;
        this.c = par3;
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
        this.c = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.a);
        par1DataOutputStream.writeShort(this.b);
        par1DataOutputStream.writeShort(this.c);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 5;
    }
}
