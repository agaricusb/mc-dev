package net.minecraft.server;

import java.util.Comparator;

class PackageNameComparator implements Comparator
{
    final CrashReportSuspiciousClasses a;

    PackageNameComparator(CrashReportSuspiciousClasses par1CallableSuspiciousClasses)
    {
        this.a = par1CallableSuspiciousClasses;
    }

    public int a(Class par1Class, Class par2Class)
    {
        String var3 = par1Class.getPackage() == null ? "" : par1Class.getPackage().getName();
        String var4 = par2Class.getPackage() == null ? "" : par2Class.getPackage().getName();
        return var3.compareTo(var4);
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return this.a((Class) par1Obj, (Class) par2Obj);
    }
}
