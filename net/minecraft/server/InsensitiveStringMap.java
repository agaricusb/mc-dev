package net.minecraft.server;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class InsensitiveStringMap implements Map
{
    private final Map a = new LinkedHashMap();

    public int size()
    {
        return this.a.size();
    }

    public boolean isEmpty()
    {
        return this.a.isEmpty();
    }

    public boolean containsKey(Object par1Obj)
    {
        return this.a.containsKey(par1Obj.toString().toLowerCase());
    }

    public boolean containsValue(Object par1Obj)
    {
        return this.a.containsKey(par1Obj);
    }

    public Object get(Object par1Obj)
    {
        return this.a.get(par1Obj.toString().toLowerCase());
    }

    /**
     * a map already defines a general put
     */
    public Object put(String par1Str, Object par2Obj)
    {
        return this.a.put(par1Str.toLowerCase(), par2Obj);
    }

    public Object remove(Object par1Obj)
    {
        return this.a.remove(par1Obj.toString().toLowerCase());
    }

    public void putAll(Map par1Map)
    {
        Iterator var2 = par1Map.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();
            this.put((String) var3.getKey(), var3.getValue());
        }
    }

    public void clear()
    {
        this.a.clear();
    }

    public Set keySet()
    {
        return this.a.keySet();
    }

    public Collection values()
    {
        return this.a.values();
    }

    public Set entrySet()
    {
        return this.a.entrySet();
    }

    public Object put(Object par1Obj, Object par2Obj)
    {
        return this.put((String) par1Obj, par2Obj);
    }
}
