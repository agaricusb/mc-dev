package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet130UpdateSign extends Packet
{
    public int x;
    public int y;
    public int z;
    public String[] lines;

    public Packet130UpdateSign()
    {
        this.lowPriority = true;
    }

    public Packet130UpdateSign(int par1, int par2, int par3, String[] par4ArrayOfStr)
    {
        this.lowPriority = true;
        this.x = par1;
        this.y = par2;
        this.z = par3;
        this.lines = new String[] {par4ArrayOfStr[0], par4ArrayOfStr[1], par4ArrayOfStr[2], par4ArrayOfStr[3]};
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.x = par1DataInputStream.readInt();
        this.y = par1DataInputStream.readShort();
        this.z = par1DataInputStream.readInt();
        this.lines = new String[4];

        for (int var2 = 0; var2 < 4; ++var2)
        {
            this.lines[var2] = a(par1DataInputStream, 15);
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.x);
        par1DataOutputStream.writeShort(this.y);
        par1DataOutputStream.writeInt(this.z);

        for (int var2 = 0; var2 < 4; ++var2)
        {
            a(this.lines[var2], par1DataOutputStream);
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
        int var1 = 0;

        for (int var2 = 0; var2 < 4; ++var2)
        {
            var1 += this.lines[var2].length();
        }

        return var1;
    }
}
