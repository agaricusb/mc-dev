package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import javax.crypto.SecretKey;

public class Packet252KeyResponse extends Packet
{
    private byte[] a = new byte[0];
    private byte[] b = new byte[0];

    /**
     * Secret AES key decrypted from sharedSecret via the server's private RSA key
     */
    private SecretKey c;

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void a(DataInputStream par1DataInputStream) throws IOException
    {
        this.a = b(par1DataInputStream);
        this.b = b(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        a(par1DataOutputStream, this.a);
        a(par1DataOutputStream, this.b);
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
        return 2 + this.a.length + 2 + this.b.length;
    }

    /**
     * Return secretKey, decrypting it from the sharedSecret byte array if needed
     */
    public SecretKey a(PrivateKey par1PrivateKey)
    {
        return par1PrivateKey == null ? this.c : (this.c = MinecraftEncryption.a(par1PrivateKey, this.a));
    }

    /**
     * Return the secret AES sharedKey (used by client only)
     */
    public SecretKey d()
    {
        return this.a((PrivateKey) null);
    }

    /**
     * Return verifyToken, decrypting it with the server's RSA private key
     */
    public byte[] b(PrivateKey par1PrivateKey)
    {
        return par1PrivateKey == null ? this.b : MinecraftEncryption.b(par1PrivateKey, this.b);
    }
}
