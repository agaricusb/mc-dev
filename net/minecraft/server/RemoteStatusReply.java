package net.minecraft.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RemoteStatusReply
{
    /** Output stream */
    private ByteArrayOutputStream buffer;

    /** ByteArrayOutputStream wrapper */
    private DataOutputStream stream;

    public RemoteStatusReply(int par1)
    {
        this.buffer = new ByteArrayOutputStream(par1);
        this.stream = new DataOutputStream(this.buffer);
    }

    /**
     * Writes the given byte array to the output stream
     */
    public void write(byte[] par1ArrayOfByte) throws IOException
    {
        this.stream.write(par1ArrayOfByte, 0, par1ArrayOfByte.length);
    }

    /**
     * Writes the given String to the output stream
     */
    public void write(String par1Str) throws IOException
    {
        this.stream.writeBytes(par1Str);
        this.stream.write(0);
    }

    /**
     * Writes the given int to the output stream
     */
    public void write(int par1) throws IOException
    {
        this.stream.write(par1);
    }

    /**
     * Writes the given short to the output stream
     */
    public void write(short par1) throws IOException
    {
        this.stream.writeShort(Short.reverseBytes(par1));
    }

    /**
     * Returns the contents of the output stream as a byte array
     */
    public byte[] getBytes()
    {
        return this.buffer.toByteArray();
    }

    /**
     * Resets the byte array output.
     */
    public void reset()
    {
        this.buffer.reset();
    }
}
