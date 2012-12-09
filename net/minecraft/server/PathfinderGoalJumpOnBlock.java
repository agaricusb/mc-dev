package net.minecraft.server;

public class PathfinderGoalJumpOnBlock extends PathfinderGoal
{
    private final EntityOcelot a;
    private final float b;

    /** Tracks for how long the task has been executing */
    private int c = 0;
    private int d = 0;

    /** For how long the Ocelot should be sitting */
    private int e = 0;

    /** X Coordinate of a nearby sitable block */
    private int f = 0;

    /** Y Coordinate of a nearby sitable block */
    private int g = 0;

    /** Z Coordinate of a nearby sitable block */
    private int h = 0;

    public PathfinderGoalJumpOnBlock(EntityOcelot par1EntityOcelot, float par2)
    {
        this.a = par1EntityOcelot;
        this.b = par2;
        this.a(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        return this.a.isTamed() && !this.a.isSitting() && this.a.aB().nextDouble() <= 0.006500000134110451D && this.f();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean b()
    {
        return this.c <= this.e && this.d <= 60 && this.a(this.a.world, this.f, this.g, this.h);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.a.getNavigation().a((double) ((float) this.f) + 0.5D, (double) (this.g + 1), (double) ((float) this.h) + 0.5D, this.b);
        this.c = 0;
        this.d = 0;
        this.e = this.a.aB().nextInt(this.a.aB().nextInt(1200) + 1200) + 1200;
        this.a.q().a(false);
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.a.setSitting(false);
    }

    /**
     * Updates the task
     */
    public void e()
    {
        ++this.c;
        this.a.q().a(false);

        if (this.a.e((double) this.f, (double) (this.g + 1), (double) this.h) > 1.0D)
        {
            this.a.setSitting(false);
            this.a.getNavigation().a((double) ((float) this.f) + 0.5D, (double) (this.g + 1), (double) ((float) this.h) + 0.5D, this.b);
            ++this.d;
        }
        else if (!this.a.isSitting())
        {
            this.a.setSitting(true);
        }
        else
        {
            --this.d;
        }
    }

    /**
     * Searches for a block to sit on within a 8 block range, returns 0 if none found
     */
    private boolean f()
    {
        int var1 = (int)this.a.locY;
        double var2 = 2.147483647E9D;

        for (int var4 = (int)this.a.locX - 8; (double)var4 < this.a.locX + 8.0D; ++var4)
        {
            for (int var5 = (int)this.a.locZ - 8; (double)var5 < this.a.locZ + 8.0D; ++var5)
            {
                if (this.a(this.a.world, var4, var1, var5) && this.a.world.isEmpty(var4, var1 + 1, var5))
                {
                    double var6 = this.a.e((double) var4, (double) var1, (double) var5);

                    if (var6 < var2)
                    {
                        this.f = var4;
                        this.g = var1;
                        this.h = var5;
                        var2 = var6;
                    }
                }
            }
        }

        return var2 < 2.147483647E9D;
    }

    /**
     * Determines whether the Ocelot wants to sit on the block at given coordinate
     */
    private boolean a(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getTypeId(par2, par3, par4);
        int var6 = par1World.getData(par2, par3, par4);

        if (var5 == Block.CHEST.id)
        {
            TileEntityChest var7 = (TileEntityChest)par1World.getTileEntity(par2, par3, par4);

            if (var7.h < 1)
            {
                return true;
            }
        }
        else
        {
            if (var5 == Block.BURNING_FURNACE.id)
            {
                return true;
            }

            if (var5 == Block.BED.id && !BlockBed.b_(var6))
            {
                return true;
            }
        }

        return false;
    }
}
