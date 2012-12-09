package net.minecraft.server;

import java.util.HashMap;
import java.util.Map;

public class RegistrySimple implements IRegistry
{
    /** Objects registered on this registry. */
    protected final Map a = new HashMap();

    public Object a(Object par1Obj)
    {
        return this.a.get(par1Obj);
    }

    /**
     * Register an object on this registry.
     */
    public void a(Object par1Obj, Object par2Obj)
    {
        this.a.put(par1Obj, par2Obj);
    }
}
