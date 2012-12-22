package net.minecraft.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTCompressedStreamTools
{
    /**
     * Load the gzipped compound from the inputstream.
     */
    public static NBTTagCompound a(InputStream par0InputStream) throws IOException
    {
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(par0InputStream)));
        NBTTagCompound var2;

        try
        {
            var2 = a((DataInput) var1);
        }
        finally
        {
            var1.close();
        }

        return var2;
    }

    /**
     * Write the compound, gzipped, to the outputstream.
     */
    public static void a(NBTTagCompound par0NBTTagCompound, OutputStream par1OutputStream) throws IOException
    {
        DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(par1OutputStream));

        try
        {
            a(par0NBTTagCompound, (DataOutput) var2);
        }
        finally
        {
            var2.close();
        }
    }

    public static NBTTagCompound a(byte[] par0ArrayOfByte) throws IOException
    {
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(par0ArrayOfByte))));
        NBTTagCompound var2;

        try
        {
            var2 = a((DataInput) var1);
        }
        finally
        {
            var1.close();
        }

        return var2;
    }

    public static byte[] a(NBTTagCompound par0NBTTagCompound) throws IOException
    {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));

        try
        {
            a(par0NBTTagCompound, (DataOutput) var2);
        }
        finally
        {
            var2.close();
        }

        return var1.toByteArray();
    }

    /**
     * Reads from a CompressedStream.
     */
    public static NBTTagCompound a(DataInput par0DataInput) throws IOException
    {
        NBTBase var1 = NBTBase.b(par0DataInput);

        if (var1 instanceof NBTTagCompound)
        {
            return (NBTTagCompound)var1;
        }
        else
        {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    public static void a(NBTTagCompound par0NBTTagCompound, DataOutput par1DataOutput) throws IOException
    {
        NBTBase.a(par0NBTTagCompound, par1DataOutput);
    }
}
