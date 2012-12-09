package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NBTTagList extends NBTBase
{
    /** The array list containing the tags encapsulated in this list. */
    private List list = new ArrayList();

    /**
     * The type byte for the tags in the list - they must all be of the same type.
     */
    private byte type;

    public NBTTagList()
    {
        super("");
    }

    public NBTTagList(String par1Str)
    {
        super(par1Str);
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput par1DataOutput) throws IOException
    {
        if (!this.list.isEmpty())
        {
            this.type = ((NBTBase)this.list.get(0)).getTypeId();
        }
        else
        {
            this.type = 1;
        }

        par1DataOutput.writeByte(this.type);
        par1DataOutput.writeInt(this.list.size());

        for (int var2 = 0; var2 < this.list.size(); ++var2)
        {
            ((NBTBase)this.list.get(var2)).write(par1DataOutput);
        }
    }

    /**
     * Read the actual data contents of the tag, implemented in NBT extension classes
     */
    void load(DataInput par1DataInput) throws IOException
    {
        this.type = par1DataInput.readByte();
        int var2 = par1DataInput.readInt();
        this.list = new ArrayList();

        for (int var3 = 0; var3 < var2; ++var3)
        {
            NBTBase var4 = NBTBase.createTag(this.type, (String) null);
            var4.load(par1DataInput);
            this.list.add(var4);
        }
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getTypeId()
    {
        return (byte)9;
    }

    public String toString()
    {
        return "" + this.list.size() + " entries of type " + NBTBase.getTagName(this.type);
    }

    /**
     * Adds the provided tag to the end of the list. There is no check to verify this tag is of the same type as any
     * previous tag.
     */
    public void add(NBTBase par1NBTBase)
    {
        this.type = par1NBTBase.getTypeId();
        this.list.add(par1NBTBase);
    }

    /**
     * Retrieves the tag at the specified index from the list.
     */
    public NBTBase get(int par1)
    {
        return (NBTBase)this.list.get(par1);
    }

    /**
     * Returns the number of tags in the list.
     */
    public int size()
    {
        return this.list.size();
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase clone()
    {
        NBTTagList var1 = new NBTTagList(this.getName());
        var1.type = this.type;
        Iterator var2 = this.list.iterator();

        while (var2.hasNext())
        {
            NBTBase var3 = (NBTBase)var2.next();
            NBTBase var4 = var3.clone();
            var1.list.add(var4);
        }

        return var1;
    }

    public boolean equals(Object par1Obj)
    {
        if (super.equals(par1Obj))
        {
            NBTTagList var2 = (NBTTagList)par1Obj;

            if (this.type == var2.type)
            {
                return this.list.equals(var2.list);
            }
        }

        return false;
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.list.hashCode();
    }
}
