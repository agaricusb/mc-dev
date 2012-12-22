package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet9Respawn extends Packet
{
    public int a;

    /**
     * The difficulty setting. 0 through 3 for peaceful, easy, normal, hard. The client always sends 1.
     */
    public int b;

    /** Defaults to 128 */
    public int c;
    public EnumGamemode d;
    public WorldType e;

    public Packet9Respawn() {}

    public Packet9Respawn(int par1, byte par2, WorldType par3WorldType, int par4, EnumGamemode par5EnumGameType)
    {
        this.a = par1;
        this.b = par2;
        this.c = par4;
        this.d = par5EnumGameType;
        this.e = par3WorldType;
    }

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
        this.a = par1DataInputStream.readInt();
        this.b = par1DataInputStream.readByte();
        this.d = EnumGamemode.a(par1DataInputStream.readByte());
        this.c = par1DataInputStream.readShort();
        String var2 = a(par1DataInputStream, 16);
        this.e = WorldType.getType(var2);

        if (this.e == null)
        {
            this.e = WorldType.NORMAL;
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        par1DataOutputStream.writeByte(this.b);
        par1DataOutputStream.writeByte(this.d.a());
        par1DataOutputStream.writeShort(this.c);
        a(this.e.name(), par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int a()
    {
        return 8 + (this.e == null ? 0 : this.e.name().length());
    }
}
