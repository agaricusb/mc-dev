package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataWatcher
{
    private static final HashMap a = new HashMap();
    private final Map b = new HashMap();

    /** true if one or more object was changed */
    private boolean c;
    private ReadWriteLock d = new ReentrantReadWriteLock();

    /**
     * adds a new object to dataWatcher to watch, to update an already existing object see updateObject. Arguments: data
     * Value Id, Object to add
     */
    public void a(int par1, Object par2Obj)
    {
        Integer var3 = (Integer) a.get(par2Obj.getClass());

        if (var3 == null)
        {
            throw new IllegalArgumentException("Unknown data type: " + par2Obj.getClass());
        }
        else if (par1 > 31)
        {
            throw new IllegalArgumentException("Data value id is too big with " + par1 + "! (Max is " + 31 + ")");
        }
        else if (this.b.containsKey(Integer.valueOf(par1)))
        {
            throw new IllegalArgumentException("Duplicate id value for " + par1 + "!");
        }
        else
        {
            WatchableObject var4 = new WatchableObject(var3.intValue(), par1, par2Obj);
            this.d.writeLock().lock();
            this.b.put(Integer.valueOf(par1), var4);
            this.d.writeLock().unlock();
        }
    }

    /**
     * Add a new object for the DataWatcher to watch, using the specified data type.
     */
    public void a(int par1, int par2)
    {
        WatchableObject var3 = new WatchableObject(par2, par1, (Object)null);
        this.d.writeLock().lock();
        this.b.put(Integer.valueOf(par1), var3);
        this.d.writeLock().unlock();
    }

    /**
     * gets the bytevalue of a watchable object
     */
    public byte getByte(int par1)
    {
        return ((Byte)this.i(par1).b()).byteValue();
    }

    public short getShort(int par1)
    {
        return ((Short)this.i(par1).b()).shortValue();
    }

    /**
     * gets a watchable object and returns it as a Integer
     */
    public int getInt(int par1)
    {
        return ((Integer)this.i(par1).b()).intValue();
    }

    /**
     * gets a watchable object and returns it as a String
     */
    public String getString(int par1)
    {
        return (String)this.i(par1).b();
    }

    /**
     * Get a watchable object as an ItemStack.
     */
    public ItemStack f(int par1)
    {
        return (ItemStack)this.i(par1).b();
    }

    /**
     * is threadsafe, unless it throws an exception, then
     */
    private WatchableObject i(int par1)
    {
        this.d.readLock().lock();
        WatchableObject var2;

        try
        {
            var2 = (WatchableObject)this.b.get(Integer.valueOf(par1));
        }
        catch (Throwable var6)
        {
            CrashReport var4 = CrashReport.a(var6, "Getting synched entity data");
            CrashReportSystemDetails var5 = var4.a("Synched entity data");
            var5.a("Data ID", Integer.valueOf(par1));
            throw new ReportedException(var4);
        }

        this.d.readLock().unlock();
        return var2;
    }

    /**
     * updates an already existing object
     */
    public void watch(int par1, Object par2Obj)
    {
        WatchableObject var3 = this.i(par1);

        if (!par2Obj.equals(var3.b()))
        {
            var3.a(par2Obj);
            var3.a(true);
            this.c = true;
        }
    }

    public void h(int par1)
    {
        WatchableObject.a(this.i(par1), true);
        this.c = true;
    }

    /**
     * true if one or more object was changed
     */
    public boolean a()
    {
        return this.c;
    }

    /**
     * writes every object in passed list to dataoutputstream, terminated by 0x7F
     */
    public static void a(List par0List, DataOutputStream par1DataOutputStream) throws IOException
    {
        if (par0List != null)
        {
            Iterator var2 = par0List.iterator();

            while (var2.hasNext())
            {
                WatchableObject var3 = (WatchableObject)var2.next();
                a(par1DataOutputStream, var3);
            }
        }

        par1DataOutputStream.writeByte(127);
    }

    public List b()
    {
        ArrayList var1 = null;

        if (this.c)
        {
            this.d.readLock().lock();
            Iterator var2 = this.b.values().iterator();

            while (var2.hasNext())
            {
                WatchableObject var3 = (WatchableObject)var2.next();

                if (var3.d())
                {
                    var3.a(false);

                    if (var1 == null)
                    {
                        var1 = new ArrayList();
                    }

                    var1.add(var3);
                }
            }

            this.d.readLock().unlock();
        }

        this.c = false;
        return var1;
    }

    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        this.d.readLock().lock();
        Iterator var2 = this.b.values().iterator();

        while (var2.hasNext())
        {
            WatchableObject var3 = (WatchableObject)var2.next();
            a(par1DataOutputStream, var3);
        }

        this.d.readLock().unlock();
        par1DataOutputStream.writeByte(127);
    }

    public List c()
    {
        ArrayList var1 = null;
        this.d.readLock().lock();
        WatchableObject var3;

        for (Iterator var2 = this.b.values().iterator(); var2.hasNext(); var1.add(var3))
        {
            var3 = (WatchableObject)var2.next();

            if (var1 == null)
            {
                var1 = new ArrayList();
            }
        }

        this.d.readLock().unlock();
        return var1;
    }

    private static void a(DataOutputStream par0DataOutputStream, WatchableObject par1WatchableObject) throws IOException
    {
        int var2 = (par1WatchableObject.c() << 5 | par1WatchableObject.a() & 31) & 255;
        par0DataOutputStream.writeByte(var2);

        switch (par1WatchableObject.c())
        {
            case 0:
                par0DataOutputStream.writeByte(((Byte)par1WatchableObject.b()).byteValue());
                break;

            case 1:
                par0DataOutputStream.writeShort(((Short)par1WatchableObject.b()).shortValue());
                break;

            case 2:
                par0DataOutputStream.writeInt(((Integer)par1WatchableObject.b()).intValue());
                break;

            case 3:
                par0DataOutputStream.writeFloat(((Float)par1WatchableObject.b()).floatValue());
                break;

            case 4:
                Packet.a((String) par1WatchableObject.b(), par0DataOutputStream);
                break;

            case 5:
                ItemStack var4 = (ItemStack)par1WatchableObject.b();
                Packet.a(var4, par0DataOutputStream);
                break;

            case 6:
                ChunkCoordinates var3 = (ChunkCoordinates)par1WatchableObject.b();
                par0DataOutputStream.writeInt(var3.x);
                par0DataOutputStream.writeInt(var3.y);
                par0DataOutputStream.writeInt(var3.z);
        }
    }

    public static List a(DataInputStream par0DataInputStream) throws IOException
    {
        ArrayList var1 = null;

        for (byte var2 = par0DataInputStream.readByte(); var2 != 127; var2 = par0DataInputStream.readByte())
        {
            if (var1 == null)
            {
                var1 = new ArrayList();
            }

            int var3 = (var2 & 224) >> 5;
            int var4 = var2 & 31;
            WatchableObject var5 = null;

            switch (var3)
            {
                case 0:
                    var5 = new WatchableObject(var3, var4, Byte.valueOf(par0DataInputStream.readByte()));
                    break;

                case 1:
                    var5 = new WatchableObject(var3, var4, Short.valueOf(par0DataInputStream.readShort()));
                    break;

                case 2:
                    var5 = new WatchableObject(var3, var4, Integer.valueOf(par0DataInputStream.readInt()));
                    break;

                case 3:
                    var5 = new WatchableObject(var3, var4, Float.valueOf(par0DataInputStream.readFloat()));
                    break;

                case 4:
                    var5 = new WatchableObject(var3, var4, Packet.a(par0DataInputStream, 64));
                    break;

                case 5:
                    var5 = new WatchableObject(var3, var4, Packet.c(par0DataInputStream));
                    break;

                case 6:
                    int var6 = par0DataInputStream.readInt();
                    int var7 = par0DataInputStream.readInt();
                    int var8 = par0DataInputStream.readInt();
                    var5 = new WatchableObject(var3, var4, new ChunkCoordinates(var6, var7, var8));
            }

            var1.add(var5);
        }

        return var1;
    }

    static
    {
        a.put(Byte.class, Integer.valueOf(0));
        a.put(Short.class, Integer.valueOf(1));
        a.put(Integer.class, Integer.valueOf(2));
        a.put(Float.class, Integer.valueOf(3));
        a.put(String.class, Integer.valueOf(4));
        a.put(ItemStack.class, Integer.valueOf(5));
        a.put(ChunkCoordinates.class, Integer.valueOf(6));
    }
}
