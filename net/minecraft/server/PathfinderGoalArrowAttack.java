package net.minecraft.server;

public class PathfinderGoalArrowAttack extends PathfinderGoal
{
    /** The entity the AI instance has been applied to */
    private final EntityLiving a;

    /**
     * The entity (as a RangedAttackMob) the AI instance has been applied to.
     */
    private final IRangedEntity b;
    private EntityLiving c;

    /**
     * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
     * maxRangedAttackTime.
     */
    private int d = 0;
    private float e;
    private int f = 0;

    /**
     * The maximum time the AI has to wait before peforming another ranged attack.
     */
    private int g;
    private float h;

    public PathfinderGoalArrowAttack(IRangedEntity par1IRangedAttackMob, float par2, int par3, float par4)
    {
        if (!(par1IRangedAttackMob instanceof EntityLiving))
        {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        else
        {
            this.b = par1IRangedAttackMob;
            this.a = (EntityLiving)par1IRangedAttackMob;
            this.e = par2;
            this.g = par3;
            this.h = par4 * par4;
            this.d = par3 / 2;
            this.a(3);
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        EntityLiving var1 = this.a.aG();

        if (var1 == null)
        {
            return false;
        }
        else
        {
            this.c = var1;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.a() || !this.a.getNavigation().f();
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.c = null;
        this.f = 0;
        this.d = this.g / 2;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        double var1 = this.a.e(this.c.locX, this.c.boundingBox.b, this.c.locZ);
        boolean var3 = this.a.aA().canSee(this.c);

        if (var3)
        {
            ++this.f;
        }
        else
        {
            this.f = 0;
        }

        if (var1 <= (double)this.h && this.f >= 20)
        {
            this.a.getNavigation().g();
        }
        else
        {
            this.a.getNavigation().a(this.c, this.e);
        }

        this.a.getControllerLook().a(this.c, 30.0F, 30.0F);
        this.d = Math.max(this.d - 1, 0);

        if (this.d <= 0)
        {
            if (var1 <= (double)this.h && var3)
            {
                this.b.d(this.c);
                this.d = this.g;
            }
        }
    }
}
