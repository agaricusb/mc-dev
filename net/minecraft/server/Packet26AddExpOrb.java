package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet26AddExpOrb extends Packet
{
    /** Entity ID for the XP Orb */
    public int a;
    public int b;
    public int c;
    public int d;

    /** The Orbs Experience points value. */
    public int e;

    public Packet26AddExpOrb() {}

    public Packet26AddExpOrb(EntityExperienceOrb par1EntityXPOrb)
    {
        this.a = par1EntityXPOrb.id;
        this.b = MathHelper.floor(par1EntityXPOrb.locX * 32.0D);
        this.c = MathHelper.floor(par1EntityXPOrb.locY * 32.0D);
        this.d = MathHelper.floor(par1EntityXPOrb.locZ * 32.0D);
        this.e = par1EntityXPOrb.c();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readInt();
        this.c = par1DataInputStream.readInt();
        this.d = par1DataInputStream.readInt();
        this.e = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeInt(this.b);
        par1DataOutputStream.writeInt(this.c);
        par1DataOutputStream.writeInt(this.d);
        par1DataOutputStream.writeShort(this.e);
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
        return 18;
    }
}
