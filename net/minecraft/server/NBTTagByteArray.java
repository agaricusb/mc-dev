package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase
{
    /** The byte array stored in the tag. */
    public byte[] data;

    public NBTTagByteArray(String par1Str)
    {
        super(par1Str);
    }

    public NBTTagByteArray(String par1Str, byte[] par2ArrayOfByte)
    {
        super(par1Str);
        this.data = par2ArrayOfByte;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.writeInt(this.data.length);
        par1DataOutput.write(this.data);
    }

    /**
     * Read the actual data contents of the tag, implemented in NBT extension classes
     */
    void load(DataInput par1DataInput) throws IOException
    {
        int var2 = par1DataInput.readInt();
        this.data = new byte[var2];
        par1DataInput.readFully(this.data);
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getTypeId()
    {
        return (byte)7;
    }

    public String toString()
    {
        return "[" + this.data.length + " bytes]";
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase clone()
    {
        byte[] var1 = new byte[this.data.length];
        System.arraycopy(this.data, 0, var1, 0, this.data.length);
        return new NBTTagByteArray(this.getName(), var1);
    }

    public boolean equals(Object par1Obj)
    {
        return super.equals(par1Obj) ? Arrays.equals(this.data, ((NBTTagByteArray)par1Obj).data) : false;
    }

    public int hashCode()
    {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }
}
