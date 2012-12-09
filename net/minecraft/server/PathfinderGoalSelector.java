package net.minecraft.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PathfinderGoalSelector
{
    /** A list of EntityAITaskEntrys in EntityAITasks. */
    private List a = new ArrayList();

    /** A list of EntityAITaskEntrys that are currently being executed. */
    private List b = new ArrayList();

    /** Instance of Profiler. */
    private final MethodProfiler c;
    private int d = 0;
    private int e = 3;

    public PathfinderGoalSelector(MethodProfiler par1Profiler)
    {
        this.c = par1Profiler;
    }

    public void a(int par1, PathfinderGoal par2EntityAIBase)
    {
        this.a.add(new PathfinderGoalSelectorItem(this, par1, par2EntityAIBase));
    }

    public void a(PathfinderGoal par1EntityAIBase)
    {
        Iterator var2 = this.a.iterator();

        while (var2.hasNext())
        {
            PathfinderGoalSelectorItem var3 = (PathfinderGoalSelectorItem)var2.next();
            PathfinderGoal var4 = var3.a;

            if (var4 == par1EntityAIBase)
            {
                if (this.b.contains(var3))
                {
                    var4.d();
                    this.b.remove(var3);
                }

                var2.remove();
            }
        }
    }

    public void a()
    {
        ArrayList var1 = new ArrayList();
        Iterator var2;
        PathfinderGoalSelectorItem var3;

        if (this.d++ % this.e == 0)
        {
            var2 = this.a.iterator();

            while (var2.hasNext())
            {
                var3 = (PathfinderGoalSelectorItem)var2.next();
                boolean var4 = this.b.contains(var3);

                if (var4)
                {
                    if (this.b(var3) && this.a(var3))
                    {
                        continue;
                    }

                    var3.a.d();
                    this.b.remove(var3);
                }

                if (this.b(var3) && var3.a.a())
                {
                    var1.add(var3);
                    this.b.add(var3);
                }
            }
        }
        else
        {
            var2 = this.b.iterator();

            while (var2.hasNext())
            {
                var3 = (PathfinderGoalSelectorItem)var2.next();

                if (!var3.a.b())
                {
                    var3.a.d();
                    var2.remove();
                }
            }
        }

        this.c.a("goalStart");
        var2 = var1.iterator();

        while (var2.hasNext())
        {
            var3 = (PathfinderGoalSelectorItem)var2.next();
            this.c.a(var3.a.getClass().getSimpleName());
            var3.a.c();
            this.c.b();
        }

        this.c.b();
        this.c.a("goalTick");
        var2 = this.b.iterator();

        while (var2.hasNext())
        {
            var3 = (PathfinderGoalSelectorItem)var2.next();
            var3.a.e();
        }

        this.c.b();
    }

    private boolean a(PathfinderGoalSelectorItem par1EntityAITaskEntry)
    {
        this.c.a("canContinue");
        boolean var2 = par1EntityAITaskEntry.a.b();
        this.c.b();
        return var2;
    }

    private boolean b(PathfinderGoalSelectorItem par1EntityAITaskEntry)
    {
        this.c.a("canUse");
        Iterator var2 = this.a.iterator();

        while (var2.hasNext())
        {
            PathfinderGoalSelectorItem var3 = (PathfinderGoalSelectorItem)var2.next();

            if (var3 != par1EntityAITaskEntry)
            {
                if (par1EntityAITaskEntry.b >= var3.b)
                {
                    if (this.b.contains(var3) && !this.a(par1EntityAITaskEntry, var3))
                    {
                        this.c.b();
                        return false;
                    }
                }
                else if (this.b.contains(var3) && !var3.a.i())
                {
                    this.c.b();
                    return false;
                }
            }
        }

        this.c.b();
        return true;
    }

    /**
     * Returns whether two EntityAITaskEntries can be executed concurrently
     */
    private boolean a(PathfinderGoalSelectorItem par1EntityAITaskEntry, PathfinderGoalSelectorItem par2EntityAITaskEntry)
    {
        return (par1EntityAITaskEntry.a.j() & par2EntityAITaskEntry.a.j()) == 0;
    }
}
