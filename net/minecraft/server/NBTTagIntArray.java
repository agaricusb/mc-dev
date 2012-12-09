package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase
{
    /** The array of saved integers */
    public int[] data;

    public NBTTagIntArray(String par1Str)
    {
        super(par1Str);
    }

    public NBTTagIntArray(String par1Str, int[] par2ArrayOfInteger)
    {
        super(par1Str);
        this.data = par2ArrayOfInteger;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.writeInt(this.data.length);

        for (int var2 = 0; var2 < this.data.length; ++var2)
        {
            par1DataOutput.writeInt(this.data[var2]);
        }
    }

    /**
     * Read the actual data contents of the tag, implemented in NBT extension classes
     */
    void load(DataInput par1DataInput) throws IOException
    {
        int var2 = par1DataInput.readInt();
        this.data = new int[var2];

        for (int var3 = 0; var3 < var2; ++var3)
        {
            this.data[var3] = par1DataInput.readInt();
        }
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getTypeId()
    {
        return (byte)11;
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
        int[] var1 = new int[this.data.length];
        System.arraycopy(this.data, 0, var1, 0, this.data.length);
        return new NBTTagIntArray(this.getName(), var1);
    }

    public boolean equals(Object par1Obj)
    {
        if (!super.equals(par1Obj))
        {
            return false;
        }
        else
        {
            NBTTagIntArray var2 = (NBTTagIntArray)par1Obj;
            return this.data == null && var2.data == null || this.data != null && Arrays.equals(this.data, var2.data);
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }
}
