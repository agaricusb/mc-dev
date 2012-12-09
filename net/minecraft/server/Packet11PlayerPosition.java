package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet11PlayerPosition extends Packet10Flying
{
    public Packet11PlayerPosition()
    {
        this.hasPos = true;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.x = par1DataInputStream.readDouble();
        this.y = par1DataInputStream.readDouble();
        this.stance = par1DataInputStream.readDouble();
        this.z = par1DataInputStream.readDouble();
        super.a(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeDouble(this.x);
        par1DataOutputStream.writeDouble(this.y);
        par1DataOutputStream.writeDouble(this.stance);
        par1DataOutputStream.writeDouble(this.z);
        super.a(par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 33;
    }
}
