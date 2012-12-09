package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.PublicKey;

public class Packet253KeyRequest extends Packet
{
    private String a;
    private PublicKey b;
    private byte[] c = new byte[0];

    public Packet253KeyRequest() {}

    public Packet253KeyRequest(String par1Str, PublicKey par2PublicKey, byte[] par3ArrayOfByte)
    {
        this.a = par1Str;
        this.b = par2PublicKey;
        this.c = par3ArrayOfByte;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = a(par1DataInputStream, 20);
        this.b = MinecraftEncryption.a(b(par1DataInputStream));
        this.c = b(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        a(this.a, par1DataOutputStream);
        a(par1DataOutputStream, this.b.getEncoded());
        a(par1DataOutputStream, this.c);
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
        return 2 + this.a.length() * 2 + 2 + this.b.getEncoded().length + 2 + this.c.length;
    }
}
