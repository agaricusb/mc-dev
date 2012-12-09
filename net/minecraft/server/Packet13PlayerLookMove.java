package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet13PlayerLookMove extends Packet10Flying
{
    public Packet13PlayerLookMove()
    {
        this.hasLook = true;
        this.hasPos = true;
    }

    public Packet13PlayerLookMove(double par1, double par3, double par5, double par7, float par9, float par10, boolean par11)
    {
        this.x = par1;
        this.y = par3;
        this.stance = par5;
        this.z = par7;
        this.yaw = par9;
        this.pitch = par10;
        this.g = par11;
        this.hasLook = true;
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
        this.yaw = par1DataInputStream.readFloat();
        this.pitch = par1DataInputStream.readFloat();
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
        par1DataOutputStream.writeFloat(this.yaw);
        par1DataOutputStream.writeFloat(this.pitch);
        super.a(par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 41;
    }
}
