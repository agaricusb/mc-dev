package net.minecraft.server;

import java.util.Comparator;

public class DistanceComparator implements Comparator
{
    private Entity b;

    final PathfinderGoalNearestAttackableTarget a;

    public DistanceComparator(PathfinderGoalNearestAttackableTarget par1EntityAINearestAttackableTarget, Entity par2Entity)
    {
        this.a = par1EntityAINearestAttackableTarget;
        this.b = par2Entity;
    }

    public int a(Entity par1Entity, Entity par2Entity)
    {
        double var3 = this.b.e(par1Entity);
        double var5 = this.b.e(par2Entity);
        return var3 < var5 ? -1 : (var3 > var5 ? 1 : 0);
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return this.a((Entity) par1Obj, (Entity) par2Obj);
    }
}
