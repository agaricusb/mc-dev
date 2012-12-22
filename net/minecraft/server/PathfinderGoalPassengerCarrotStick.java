package net.minecraft.server;

public class PathfinderGoalPassengerCarrotStick extends PathfinderGoal
{
    private final EntityLiving a;
    private final float b;
    private float c = 0.0F;

    /** Whether the entity's speed is boosted. */
    private boolean d = false;

    /**
     * Counter for speed boosting, upon reaching maxSpeedBoostTime the speed boost will be disabled
     */
    private int e = 0;

    /** Maximum time the entity's speed should be boosted for. */
    private int f = 0;

    public PathfinderGoalPassengerCarrotStick(EntityLiving par1EntityLiving, float par2)
    {
        this.a = par1EntityLiving;
        this.b = par2;
        this.a(7);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.c = 0.0F;
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.d = false;
        this.c = 0.0F;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        return this.a.isAlive() && this.a.passenger != null && this.a.passenger instanceof EntityHuman && (this.d || this.a.bI());
    }

    /**
     * Updates the task
     */
    public void e()
    {
        EntityHuman var1 = (EntityHuman)this.a.passenger;
        EntityCreature var2 = (EntityCreature)this.a;
        float var3 = MathHelper.g(var1.yaw - this.a.yaw) * 0.5F;

        if (var3 > 5.0F)
        {
            var3 = 5.0F;
        }

        if (var3 < -5.0F)
        {
            var3 = -5.0F;
        }

        this.a.yaw = MathHelper.g(this.a.yaw + var3);

        if (this.c < this.b)
        {
            this.c += (this.b - this.c) * 0.01F;
        }

        if (this.c > this.b)
        {
            this.c = this.b;
        }

        int var4 = MathHelper.floor(this.a.locX);
        int var5 = MathHelper.floor(this.a.locY);
        int var6 = MathHelper.floor(this.a.locZ);
        float var7 = this.c;

        if (this.d)
        {
            if (this.e++ > this.f)
            {
                this.d = false;
            }

            var7 += var7 * 1.15F * MathHelper.sin((float) this.e / (float) this.f * (float) Math.PI);
        }

        float var8 = 0.91F;

        if (this.a.onGround)
        {
            var8 = 0.54600006F;
            int var9 = this.a.world.getTypeId(MathHelper.d((float) var4), MathHelper.d((float) var5) - 1, MathHelper.d((float) var6));

            if (var9 > 0)
            {
                var8 = Block.byId[var9].frictionFactor * 0.91F;
            }
        }

        float var22 = 0.16277136F / (var8 * var8 * var8);
        float var10 = MathHelper.sin(var2.yaw * (float) Math.PI / 180.0F);
        float var11 = MathHelper.cos(var2.yaw * (float) Math.PI / 180.0F);
        float var12 = var2.aF() * var22;
        float var13 = Math.max(var7, 1.0F);
        var13 = var12 / var13;
        float var14 = var7 * var13;
        float var15 = -(var14 * var10);
        float var16 = var14 * var11;

        if (MathHelper.abs(var15) > MathHelper.abs(var16))
        {
            if (var15 < 0.0F)
            {
                var15 -= this.a.width / 2.0F;
            }

            if (var15 > 0.0F)
            {
                var15 += this.a.width / 2.0F;
            }

            var16 = 0.0F;
        }
        else
        {
            var15 = 0.0F;

            if (var16 < 0.0F)
            {
                var16 -= this.a.width / 2.0F;
            }

            if (var16 > 0.0F)
            {
                var16 += this.a.width / 2.0F;
            }
        }

        int var17 = MathHelper.floor(this.a.locX + (double) var15);
        int var18 = MathHelper.floor(this.a.locZ + (double) var16);
        PathPoint var19 = new PathPoint(MathHelper.d(this.a.width + 1.0F), MathHelper.d(this.a.length + var1.length + 1.0F), MathHelper.d(this.a.width + 1.0F));

        if ((var4 != var17 || var6 != var18) && Pathfinder.a(this.a, var17, var5, var18, var19, false, false, true) == 0 && Pathfinder.a(this.a, var4, var5 + 1, var6, var19, false, false, true) == 1 && Pathfinder.a(this.a, var17, var5 + 1, var18, var19, false, false, true) == 1)
        {
            var2.getControllerJump().a();
        }

        if (!var1.abilities.canInstantlyBuild && this.c >= this.b * 0.5F && this.a.aB().nextFloat() < 0.006F && !this.d)
        {
            ItemStack var20 = var1.bD();

            if (var20 != null && var20.id == Item.CARROT_STICK.id)
            {
                var20.damage(1, var1);

                if (var20.count == 0)
                {
                    ItemStack var21 = new ItemStack(Item.FISHING_ROD);
                    var21.setTag(var20.tag);
                    var1.inventory.items[var1.inventory.itemInHandIndex] = var21;
                }
            }
        }

        this.a.e(0.0F, var7);
    }

    /**
     * Return whether the entity's speed is boosted.
     */
    public boolean f()
    {
        return this.d;
    }

    /**
     * Boost the entity's movement speed.
     */
    public void g()
    {
        this.d = true;
        this.e = 0;
        this.f = this.a.aB().nextInt(841) + 140;
    }

    /**
     * Return whether the entity is being controlled by a player.
     */
    public boolean h()
    {
        return !this.f() && this.c > this.b * 0.3F;
    }
}
