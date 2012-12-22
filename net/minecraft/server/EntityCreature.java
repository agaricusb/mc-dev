package net.minecraft.server;

public abstract class EntityCreature extends EntityLiving
{
    private PathEntity pathEntity;

    /** The Entity this EntityCreature is set to attack. */
    protected Entity target;

    /**
     * returns true if a creature has attacked recently only used for creepers and skeletons
     */
    protected boolean b = false;

    /** Used to make a creature speed up and wander away when hit. */
    protected int c = 0;

    public EntityCreature(World par1World)
    {
        super(par1World);
    }

    /**
     * Disables a mob's ability to move on its own while true.
     */
    protected boolean h()
    {
        return false;
    }

    protected void bn()
    {
        this.world.methodProfiler.a("ai");

        if (this.c > 0)
        {
            --this.c;
        }

        this.b = this.h();
        float var1 = 16.0F;

        if (this.target == null)
        {
            this.target = this.findTarget();

            if (this.target != null)
            {
                this.pathEntity = this.world.findPath(this, this.target, var1, true, false, false, true);
            }
        }
        else if (this.target.isAlive())
        {
            float var2 = this.target.d(this);

            if (this.n(this.target))
            {
                this.a(this.target, var2);
            }
        }
        else
        {
            this.target = null;
        }

        this.world.methodProfiler.b();

        if (!this.b && this.target != null && (this.pathEntity == null || this.random.nextInt(20) == 0))
        {
            this.pathEntity = this.world.findPath(this, this.target, var1, true, false, false, true);
        }
        else if (!this.b && (this.pathEntity == null && this.random.nextInt(180) == 0 || this.random.nextInt(120) == 0 || this.c > 0) && this.bB < 100)
        {
            this.i();
        }

        int var21 = MathHelper.floor(this.boundingBox.b + 0.5D);
        boolean var3 = this.H();
        boolean var4 = this.J();
        this.pitch = 0.0F;

        if (this.pathEntity != null && this.random.nextInt(100) != 0)
        {
            this.world.methodProfiler.a("followpath");
            Vec3D var5 = this.pathEntity.a(this);
            double var6 = (double)(this.width * 2.0F);

            while (var5 != null && var5.d(this.locX, var5.d, this.locZ) < var6 * var6)
            {
                this.pathEntity.a();

                if (this.pathEntity.b())
                {
                    var5 = null;
                    this.pathEntity = null;
                }
                else
                {
                    var5 = this.pathEntity.a(this);
                }
            }

            this.bF = false;

            if (var5 != null)
            {
                double var8 = var5.c - this.locX;
                double var10 = var5.e - this.locZ;
                double var12 = var5.d - (double)var21;
                float var14 = (float)(Math.atan2(var10, var8) * 180.0D / Math.PI) - 90.0F;
                float var15 = MathHelper.g(var14 - this.yaw);
                this.bD = this.bH;

                if (var15 > 30.0F)
                {
                    var15 = 30.0F;
                }

                if (var15 < -30.0F)
                {
                    var15 = -30.0F;
                }

                this.yaw += var15;

                if (this.b && this.target != null)
                {
                    double var16 = this.target.locX - this.locX;
                    double var18 = this.target.locZ - this.locZ;
                    float var20 = this.yaw;
                    this.yaw = (float)(Math.atan2(var18, var16) * 180.0D / Math.PI) - 90.0F;
                    var15 = (var20 - this.yaw + 90.0F) * (float)Math.PI / 180.0F;
                    this.bC = -MathHelper.sin(var15) * this.bD * 1.0F;
                    this.bD = MathHelper.cos(var15) * this.bD * 1.0F;
                }

                if (var12 > 0.0D)
                {
                    this.bF = true;
                }
            }

            if (this.target != null)
            {
                this.a(this.target, 30.0F, 30.0F);
            }

            if (this.positionChanged && !this.k())
            {
                this.bF = true;
            }

            if (this.random.nextFloat() < 0.8F && (var3 || var4))
            {
                this.bF = true;
            }

            this.world.methodProfiler.b();
        }
        else
        {
            super.bn();
            this.pathEntity = null;
        }
    }

    /**
     * Time remaining during which the Animal is sped up and flees.
     */
    protected void i()
    {
        this.world.methodProfiler.a("stroll");
        boolean var1 = false;
        int var2 = -1;
        int var3 = -1;
        int var4 = -1;
        float var5 = -99999.0F;

        for (int var6 = 0; var6 < 10; ++var6)
        {
            int var7 = MathHelper.floor(this.locX + (double) this.random.nextInt(13) - 6.0D);
            int var8 = MathHelper.floor(this.locY + (double) this.random.nextInt(7) - 3.0D);
            int var9 = MathHelper.floor(this.locZ + (double) this.random.nextInt(13) - 6.0D);
            float var10 = this.a(var7, var8, var9);

            if (var10 > var5)
            {
                var5 = var10;
                var2 = var7;
                var3 = var8;
                var4 = var9;
                var1 = true;
            }
        }

        if (var1)
        {
            this.pathEntity = this.world.a(this, var2, var3, var4, 10.0F, true, false, false, true);
        }

        this.world.methodProfiler.b();
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void a(Entity par1Entity, float par2) {}

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float a(int par1, int par2, int par3)
    {
        return 0.0F;
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findTarget()
    {
        return null;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        int var1 = MathHelper.floor(this.locX);
        int var2 = MathHelper.floor(this.boundingBox.b);
        int var3 = MathHelper.floor(this.locZ);
        return super.canSpawn() && this.a(var1, var2, var3) >= 0.0F;
    }

    /**
     * if the entity got a PathEntity it returns true, else false
     */
    public boolean k()
    {
        return this.pathEntity != null;
    }

    /**
     * sets the pathToEntity
     */
    public void setPathEntity(PathEntity par1PathEntity)
    {
        this.pathEntity = par1PathEntity;
    }

    /**
     * returns the target Entity
     */
    public Entity l()
    {
        return this.target;
    }

    /**
     * Sets the entity which is to be attacked.
     */
    public void setTarget(Entity par1Entity)
    {
        this.target = par1Entity;
    }

    /**
     * This method returns a value to be applied directly to entity speed, this factor is less than 1 when a slowdown
     * potion effect is applied, more than 1 when a haste potion effect is applied and 2 for fleeing entities.
     */
    public float bB()
    {
        float var1 = super.bB();

        if (this.c > 0 && !this.be())
        {
            var1 *= 2.0F;
        }

        return var1;
    }
}
