package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet1Login extends Packet
{
    /** The player's entity ID */
    public int a = 0;
    public WorldType b;
    public boolean c;
    public EnumGamemode d;

    /** -1: The Nether, 0: The Overworld, 1: The End */
    public int e;

    /** The difficulty setting byte. */
    public byte f;

    /** Defaults to 128 */
    public byte g;

    /** The maximum players. */
    public byte h;

    public Packet1Login() {}

    public Packet1Login(int par1, WorldType par2WorldType, EnumGamemode par3EnumGameType, boolean par4, int par5, int par6, int par7, int par8)
    {
        this.a = par1;
        this.b = par2WorldType;
        this.e = par5;
        this.f = (byte)par6;
        this.d = par3EnumGameType;
        this.g = (byte)par7;
        this.h = (byte)par8;
        this.c = par4;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = par1DataInputStream.readInt();
        String var2 = a(par1DataInputStream, 16);
        this.b = WorldType.getType(var2);

        if (this.b == null)
        {
            this.b = WorldType.NORMAL;
        }

        byte var3 = par1DataInputStream.readByte();
        this.c = (var3 & 8) == 8;
        int var4 = var3 & -9;
        this.d = EnumGamemode.a(var4);
        this.e = par1DataInputStream.readByte();
        this.f = par1DataInputStream.readByte();
        this.g = par1DataInputStream.readByte();
        this.h = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.a);
        a(this.b == null ? "" : this.b.name(), par1DataOutputStream);
        int var2 = this.d.a();

        if (this.c)
        {
            var2 |= 8;
        }

        par1DataOutputStream.writeByte(var2);
        par1DataOutputStream.writeByte(this.e);
        par1DataOutputStream.writeByte(this.f);
        par1DataOutputStream.writeByte(this.g);
        par1DataOutputStream.writeByte(this.h);
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
        int var1 = 0;

        if (this.b != null)
        {
            var1 = this.b.name().length();
        }

        return 6 + 2 * var1 + 4 + 4 + 1 + 1 + 1;
    }
}
