package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PathfinderGoalBreed extends PathfinderGoal
{
    private EntityAnimal d;
    World a;
    private EntityAnimal e;

    /**
     * Delay preventing a baby from spawning immediately when two mate-able animals find each other.
     */
    int b = 0;

    /** The speed the creature moves at during mating behavior. */
    float c;

    public PathfinderGoalBreed(EntityAnimal par1EntityAnimal, float par2)
    {
        this.d = par1EntityAnimal;
        this.a = par1EntityAnimal.world;
        this.c = par2;
        this.a(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        if (!this.d.r())
        {
            return false;
        }
        else
        {
            this.e = this.f();
            return this.e != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.e.isAlive() && this.e.r() && this.b < 60;
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.e = null;
        this.b = 0;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        this.d.getControllerLook().a(this.e, 10.0F, (float) this.d.bp());
        this.d.getNavigation().a(this.e, this.c);
        ++this.b;

        if (this.b == 60)
        {
            this.g();
        }
    }

    /**
     * Loops through nearby animals and finds another animal of the same type that can be mated with. Returns the first
     * valid mate found.
     */
    private EntityAnimal f()
    {
        float var1 = 8.0F;
        List var2 = this.a.a(this.d.getClass(), this.d.boundingBox.grow((double) var1, (double) var1, (double) var1));
        Iterator var3 = var2.iterator();
        EntityAnimal var4;

        do
        {
            if (!var3.hasNext())
            {
                return null;
            }

            var4 = (EntityAnimal)var3.next();
        }
        while (!this.d.mate(var4));

        return var4;
    }

    /**
     * Spawns a baby animal of the same type.
     */
    private void g()
    {
        EntityAgeable var1 = this.d.createChild(this.e);

        if (var1 != null)
        {
            this.d.setAge(6000);
            this.e.setAge(6000);
            this.d.s();
            this.e.s();
            var1.setAge(-24000);
            var1.setPositionRotation(this.d.locX, this.d.locY, this.d.locZ, 0.0F, 0.0F);
            this.a.addEntity(var1);
            Random var2 = this.d.aB();

            for (int var3 = 0; var3 < 7; ++var3)
            {
                double var4 = var2.nextGaussian() * 0.02D;
                double var6 = var2.nextGaussian() * 0.02D;
                double var8 = var2.nextGaussian() * 0.02D;
                this.a.addParticle("heart", this.d.locX + (double) (var2.nextFloat() * this.d.width * 2.0F) - (double) this.d.width, this.d.locY + 0.5D + (double) (var2.nextFloat() * this.d.length), this.d.locZ + (double) (var2.nextFloat() * this.d.width * 2.0F) - (double) this.d.width, var4, var6, var8);
            }

            this.a.addEntity(new EntityExperienceOrb(this.a, this.d.locX, this.d.locY, this.d.locZ, var2.nextInt(4) + 1));
        }
    }
}
