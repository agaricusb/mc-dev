package net.minecraft.server;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WorldMapCollection
{
    private IDataManager a;

    /** Map of item data String id to loaded MapDataBases */
    private Map b = new HashMap();

    /** List of loaded MapDataBases. */
    private List c = new ArrayList();

    /**
     * Map of MapDataBase id String prefixes ('map' etc) to max known unique Short id (the 0 part etc) for that prefix
     */
    private Map d = new HashMap();

    public WorldMapCollection(IDataManager par1ISaveHandler)
    {
        this.a = par1ISaveHandler;
        this.b();
    }

    /**
     * Loads an existing MapDataBase corresponding to the given String id from disk, instantiating the given Class, or
     * returns null if none such file exists. args: Class to instantiate, String dataid
     */
    public WorldMapBase get(Class par1Class, String par2Str)
    {
        WorldMapBase var3 = (WorldMapBase)this.b.get(par2Str);

        if (var3 != null)
        {
            return var3;
        }
        else
        {
            if (this.a != null)
            {
                try
                {
                    File var4 = this.a.getDataFile(par2Str);

                    if (var4 != null && var4.exists())
                    {
                        try
                        {
                            var3 = (WorldMapBase)par1Class.getConstructor(new Class[] {String.class}).newInstance(new Object[] {par2Str});
                        }
                        catch (Exception var7)
                        {
                            throw new RuntimeException("Failed to instantiate " + par1Class.toString(), var7);
                        }

                        FileInputStream var5 = new FileInputStream(var4);
                        NBTTagCompound var6 = NBTCompressedStreamTools.a(var5);
                        var5.close();
                        var3.a(var6.getCompound("data"));
                    }
                }
                catch (Exception var8)
                {
                    var8.printStackTrace();
                }
            }

            if (var3 != null)
            {
                this.b.put(par2Str, var3);
                this.c.add(var3);
            }

            return var3;
        }
    }

    /**
     * Assigns the given String id to the given MapDataBase, removing any existing ones of the same id.
     */
    public void a(String par1Str, WorldMapBase par2WorldSavedData)
    {
        if (par2WorldSavedData == null)
        {
            throw new RuntimeException("Can\'t set null data");
        }
        else
        {
            if (this.b.containsKey(par1Str))
            {
                this.c.remove(this.b.remove(par1Str));
            }

            this.b.put(par1Str, par2WorldSavedData);
            this.c.add(par2WorldSavedData);
        }
    }

    /**
     * Saves all dirty loaded MapDataBases to disk.
     */
    public void a()
    {
        for (int var1 = 0; var1 < this.c.size(); ++var1)
        {
            WorldMapBase var2 = (WorldMapBase)this.c.get(var1);

            if (var2.d())
            {
                this.a(var2);
                var2.a(false);
            }
        }
    }

    /**
     * Saves the given MapDataBase to disk.
     */
    private void a(WorldMapBase par1WorldSavedData)
    {
        if (this.a != null)
        {
            try
            {
                File var2 = this.a.getDataFile(par1WorldSavedData.id);

                if (var2 != null)
                {
                    NBTTagCompound var3 = new NBTTagCompound();
                    par1WorldSavedData.b(var3);
                    NBTTagCompound var4 = new NBTTagCompound();
                    var4.setCompound("data", var3);
                    FileOutputStream var5 = new FileOutputStream(var2);
                    NBTCompressedStreamTools.a(var4, var5);
                    var5.close();
                }
            }
            catch (Exception var6)
            {
                var6.printStackTrace();
            }
        }
    }

    /**
     * Loads the idCounts Map from the 'idcounts' file.
     */
    private void b()
    {
        try
        {
            this.d.clear();

            if (this.a == null)
            {
                return;
            }

            File var1 = this.a.getDataFile("idcounts");

            if (var1 != null && var1.exists())
            {
                DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
                NBTTagCompound var3 = NBTCompressedStreamTools.a((DataInput) var2); // read
                var2.close();
                Iterator var4 = var3.c().iterator();

                while (var4.hasNext())
                {
                    NBTBase var5 = (NBTBase)var4.next();

                    if (var5 instanceof NBTTagShort)
                    {
                        NBTTagShort var6 = (NBTTagShort)var5;
                        String var7 = var6.getName();
                        short var8 = var6.data;
                        this.d.put(var7, Short.valueOf(var8));
                    }
                }
            }
        }
        catch (Exception var9)
        {
            var9.printStackTrace();
        }
    }

    /**
     * Returns an unique new data id for the given prefix and saves the idCounts map to the 'idcounts' file.
     */
    public int a(String par1Str)
    {
        Short var2 = (Short)this.d.get(par1Str);

        if (var2 == null)
        {
            var2 = Short.valueOf((short)0);
        }
        else
        {
            var2 = Short.valueOf((short)(var2.shortValue() + 1));
        }

        this.d.put(par1Str, var2);

        if (this.a == null)
        {
            return var2.shortValue();
        }
        else
        {
            try
            {
                File var3 = this.a.getDataFile("idcounts");

                if (var3 != null)
                {
                    NBTTagCompound var4 = new NBTTagCompound();
                    Iterator var5 = this.d.keySet().iterator();

                    while (var5.hasNext())
                    {
                        String var6 = (String)var5.next();
                        short var7 = ((Short)this.d.get(var6)).shortValue();
                        var4.setShort(var6, var7);
                    }

                    DataOutputStream var9 = new DataOutputStream(new FileOutputStream(var3));
                    NBTCompressedStreamTools.a(var4, (DataOutput) var9); // write
                    var9.close();
                }
            }
            catch (Exception var8)
            {
                var8.printStackTrace();
            }

            return var2.shortValue();
        }
    }
}
