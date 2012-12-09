package net.minecraft.server;

public class RegistryDefault extends RegistrySimple
{
    /**
     * Default object for this registry, returned when an object is not found.
     */
    private final Object b;

    public RegistryDefault(Object par1Obj)
    {
        this.b = par1Obj;
    }

    public Object a(Object par1Obj)
    {
        Object var2 = super.a(par1Obj);
        return var2 == null ? this.b : var2;
    }
}
