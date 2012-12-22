package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NBTTagCompound extends NBTBase
{
    /**
     * The key-value pairs for the tag. Each key is a UTF string, each value is a tag.
     */
    private Map map = new HashMap();

    public NBTTagCompound()
    {
        super("");
    }

    public NBTTagCompound(String par1Str)
    {
        super(par1Str);
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput par1DataOutput) throws IOException
    {
        Iterator var2 = this.map.values().iterator();

        while (var2.hasNext())
        {
            NBTBase var3 = (NBTBase)var2.next();
            NBTBase.a(var3, par1DataOutput);
        }

        par1DataOutput.writeByte(0);
    }

    /**
     * Read the actual data contents of the tag, implemented in NBT extension classes
     */
    void load(DataInput par1DataInput) throws IOException
    {
        this.map.clear();
        NBTBase var2;

        while ((var2 = NBTBase.b(par1DataInput)).getTypeId() != 0)
        {
            this.map.put(var2.getName(), var2);
        }
    }

    /**
     * Returns all the values in the tagMap HashMap.
     */
    public Collection c()
    {
        return this.map.values();
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getTypeId()
    {
        return (byte)10;
    }

    /**
     * Stores the given tag into the map with the given string key. This is mostly used to store tag lists.
     */
    public void set(String par1Str, NBTBase par2NBTBase)
    {
        this.map.put(par1Str, par2NBTBase.setName(par1Str));
    }

    /**
     * Stores a new NBTTagByte with the given byte value into the map with the given string key.
     */
    public void setByte(String par1Str, byte par2)
    {
        this.map.put(par1Str, new NBTTagByte(par1Str, par2));
    }

    /**
     * Stores a new NBTTagShort with the given short value into the map with the given string key.
     */
    public void setShort(String par1Str, short par2)
    {
        this.map.put(par1Str, new NBTTagShort(par1Str, par2));
    }

    /**
     * Stores a new NBTTagInt with the given integer value into the map with the given string key.
     */
    public void setInt(String par1Str, int par2)
    {
        this.map.put(par1Str, new NBTTagInt(par1Str, par2));
    }

    /**
     * Stores a new NBTTagLong with the given long value into the map with the given string key.
     */
    public void setLong(String par1Str, long par2)
    {
        this.map.put(par1Str, new NBTTagLong(par1Str, par2));
    }

    /**
     * Stores a new NBTTagFloat with the given float value into the map with the given string key.
     */
    public void setFloat(String par1Str, float par2)
    {
        this.map.put(par1Str, new NBTTagFloat(par1Str, par2));
    }

    /**
     * Stores a new NBTTagDouble with the given double value into the map with the given string key.
     */
    public void setDouble(String par1Str, double par2)
    {
        this.map.put(par1Str, new NBTTagDouble(par1Str, par2));
    }

    /**
     * Stores a new NBTTagString with the given string value into the map with the given string key.
     */
    public void setString(String par1Str, String par2Str)
    {
        this.map.put(par1Str, new NBTTagString(par1Str, par2Str));
    }

    /**
     * Stores a new NBTTagByteArray with the given array as data into the map with the given string key.
     */
    public void setByteArray(String par1Str, byte[] par2ArrayOfByte)
    {
        this.map.put(par1Str, new NBTTagByteArray(par1Str, par2ArrayOfByte));
    }

    /**
     * Stores a new NBTTagIntArray with the given array as data into the map with the given string key.
     */
    public void setIntArray(String par1Str, int[] par2ArrayOfInteger)
    {
        this.map.put(par1Str, new NBTTagIntArray(par1Str, par2ArrayOfInteger));
    }

    /**
     * Stores the given NBTTagCompound into the map with the given string key.
     */
    public void setCompound(String par1Str, NBTTagCompound par2NBTTagCompound)
    {
        this.map.put(par1Str, par2NBTTagCompound.setName(par1Str));
    }

    /**
     * Stores the given boolean value as a NBTTagByte, storing 1 for true and 0 for false, using the given string key.
     */
    public void setBoolean(String par1Str, boolean par2)
    {
        this.setByte(par1Str, (byte) (par2 ? 1 : 0));
    }

    /**
     * gets a generic tag with the specified name
     */
    public NBTBase get(String par1Str)
    {
        return (NBTBase)this.map.get(par1Str);
    }

    /**
     * Returns whether the given string has been previously stored as a key in the map.
     */
    public boolean hasKey(String par1Str)
    {
        return this.map.containsKey(par1Str);
    }

    /**
     * Retrieves a byte value using the specified key, or 0 if no such key was stored.
     */
    public byte getByte(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? 0 : ((NBTTagByte)this.map.get(par1Str)).data;
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 1, var3));
        }
    }

    /**
     * Retrieves a short value using the specified key, or 0 if no such key was stored.
     */
    public short getShort(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? 0 : ((NBTTagShort)this.map.get(par1Str)).data;
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 2, var3));
        }
    }

    /**
     * Retrieves an integer value using the specified key, or 0 if no such key was stored.
     */
    public int getInt(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? 0 : ((NBTTagInt)this.map.get(par1Str)).data;
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 3, var3));
        }
    }

    /**
     * Retrieves a long value using the specified key, or 0 if no such key was stored.
     */
    public long getLong(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? 0L : ((NBTTagLong)this.map.get(par1Str)).data;
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 4, var3));
        }
    }

    /**
     * Retrieves a float value using the specified key, or 0 if no such key was stored.
     */
    public float getFloat(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? 0.0F : ((NBTTagFloat)this.map.get(par1Str)).data;
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 5, var3));
        }
    }

    /**
     * Retrieves a double value using the specified key, or 0 if no such key was stored.
     */
    public double getDouble(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? 0.0D : ((NBTTagDouble)this.map.get(par1Str)).data;
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 6, var3));
        }
    }

    /**
     * Retrieves a string value using the specified key, or an empty string if no such key was stored.
     */
    public String getString(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? "" : ((NBTTagString)this.map.get(par1Str)).data;
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 8, var3));
        }
    }

    /**
     * Retrieves a byte array using the specified key, or a zero-length array if no such key was stored.
     */
    public byte[] getByteArray(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? new byte[0] : ((NBTTagByteArray)this.map.get(par1Str)).data;
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 7, var3));
        }
    }

    /**
     * Retrieves an int array using the specified key, or a zero-length array if no such key was stored.
     */
    public int[] getIntArray(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? new int[0] : ((NBTTagIntArray)this.map.get(par1Str)).data;
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 11, var3));
        }
    }

    /**
     * Retrieves a NBTTagCompound subtag matching the specified key, or a new empty NBTTagCompound if no such key was
     * stored.
     */
    public NBTTagCompound getCompound(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? new NBTTagCompound(par1Str) : (NBTTagCompound)this.map.get(par1Str);
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 10, var3));
        }
    }

    /**
     * Retrieves a NBTTagList subtag matching the specified key, or a new empty NBTTagList if no such key was stored.
     */
    public NBTTagList getList(String par1Str)
    {
        try
        {
            return !this.map.containsKey(par1Str) ? new NBTTagList(par1Str) : (NBTTagList)this.map.get(par1Str);
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.a(par1Str, 9, var3));
        }
    }

    /**
     * Retrieves a boolean value using the specified key, or false if no such key was stored. This uses the getByte
     * method.
     */
    public boolean getBoolean(String par1Str)
    {
        return this.getByte(par1Str) != 0;
    }

    /**
     * Remove the specified tag.
     */
    public void o(String par1Str)
    {
        this.map.remove(par1Str);
    }

    public String toString()
    {
        return "" + this.map.size() + " entries";
    }

    /**
     * Return whether this compound has no tags.
     */
    public boolean d()
    {
        return this.map.isEmpty();
    }

    /**
     * Create a crash report which indicates a NBT read error.
     */
    private CrashReport a(String par1Str, int par2, ClassCastException par3ClassCastException)
    {
        CrashReport var4 = CrashReport.a(par3ClassCastException, "Reading NBT data");
        CrashReportSystemDetails var5 = var4.a("Corrupt NBT tag", 1);
        var5.a("Tag type found", new CrashReportCorruptNBTTag(this, par1Str));
        var5.a("Tag type expected", new CrashReportCorruptNBTTag2(this, par2));
        var5.a("Tag name", par1Str);

        if (this.getName() != null && this.getName().length() > 0)
        {
            var5.a("Tag parent", this.getName());
        }

        return var4;
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase clone()
    {
        NBTTagCompound var1 = new NBTTagCompound(this.getName());
        Iterator var2 = this.map.keySet().iterator();

        while (var2.hasNext())
        {
            String var3 = (String)var2.next();
            var1.set(var3, ((NBTBase) this.map.get(var3)).clone());
        }

        return var1;
    }

    public boolean equals(Object par1Obj)
    {
        if (super.equals(par1Obj))
        {
            NBTTagCompound var2 = (NBTTagCompound)par1Obj;
            return this.map.entrySet().equals(var2.map.entrySet());
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.map.hashCode();
    }

    /**
     * Return the tag map for this compound.
     */
    static Map a(NBTTagCompound par0NBTTagCompound)
    {
        return par0NBTTagCompound.map;
    }
}
