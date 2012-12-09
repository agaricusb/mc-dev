package net.minecraft.server;

public class EntityGhast extends EntityFlying implements IMonster
{
    public int b = 0;
    public double c;
    public double d;
    public double e;
    private Entity target = null;

    /** Cooldown time between target loss and new target aquirement. */
    private int i = 0;
    public int f = 0;
    public int g = 0;

    public EntityGhast(World par1World)
    {
        super(par1World);
        this.texture = "/mob/ghast.png";
        this.a(4.0F, 4.0F);
        this.fireProof = true;
        this.bc = 5;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean damageEntity(DamageSource par1DamageSource, int par2)
    {
        if (this.isInvulnerable())
        {
            return false;
        }
        else if ("fireball".equals(par1DamageSource.l()) && par1DamageSource.getEntity() instanceof EntityHuman)
        {
            super.damageEntity(par1DamageSource, 1000);
            ((EntityHuman)par1DamageSource.getEntity()).a(AchievementList.y);
            return true;
        }
        else
        {
            return super.damageEntity(par1DamageSource, par2);
        }
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, Byte.valueOf((byte) 0));
    }

    public int getMaxHealth()
    {
        return 10;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();
        byte var1 = this.datawatcher.getByte(16);
        this.texture = var1 == 1 ? "/mob/ghast_fire.png" : "/mob/ghast.png";
    }

    protected void bn()
    {
        if (!this.world.isStatic && this.world.difficulty == 0)
        {
            this.die();
        }

        this.bk();
        this.f = this.g;
        double var1 = this.c - this.locX;
        double var3 = this.d - this.locY;
        double var5 = this.e - this.locZ;
        double var7 = var1 * var1 + var3 * var3 + var5 * var5;

        if (var7 < 1.0D || var7 > 3600.0D)
        {
            this.c = this.locX + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.d = this.locY + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.e = this.locZ + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (this.b-- <= 0)
        {
            this.b += this.random.nextInt(5) + 2;
            var7 = (double) MathHelper.sqrt(var7);

            if (this.a(this.c, this.d, this.e, var7))
            {
                this.motX += var1 / var7 * 0.1D;
                this.motY += var3 / var7 * 0.1D;
                this.motZ += var5 / var7 * 0.1D;
            }
            else
            {
                this.c = this.locX;
                this.d = this.locY;
                this.e = this.locZ;
            }
        }

        if (this.target != null && this.target.dead)
        {
            this.target = null;
        }

        if (this.target == null || this.i-- <= 0)
        {
            this.target = this.world.findNearbyVulnerablePlayer(this, 100.0D);

            if (this.target != null)
            {
                this.i = 20;
            }
        }

        double var9 = 64.0D;

        if (this.target != null && this.target.e(this) < var9 * var9)
        {
            double var11 = this.target.locX - this.locX;
            double var13 = this.target.boundingBox.b + (double)(this.target.length / 2.0F) - (this.locY + (double)(this.length / 2.0F));
            double var15 = this.target.locZ - this.locZ;
            this.aw = this.yaw = -((float)Math.atan2(var11, var15)) * 180.0F / (float)Math.PI;

            if (this.n(this.target))
            {
                if (this.g == 10)
                {
                    this.world.a((EntityHuman) null, 1007, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
                }

                ++this.g;

                if (this.g == 20)
                {
                    this.world.a((EntityHuman) null, 1008, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
                    EntityLargeFireball var17 = new EntityLargeFireball(this.world, this, var11, var13, var15);
                    double var18 = 4.0D;
                    Vec3D var20 = this.i(1.0F);
                    var17.locX = this.locX + var20.c * var18;
                    var17.locY = this.locY + (double)(this.length / 2.0F) + 0.5D;
                    var17.locZ = this.locZ + var20.e * var18;
                    this.world.addEntity(var17);
                    this.g = -40;
                }
            }
            else if (this.g > 0)
            {
                --this.g;
            }
        }
        else
        {
            this.aw = this.yaw = -((float)Math.atan2(this.motX, this.motZ)) * 180.0F / (float)Math.PI;

            if (this.g > 0)
            {
                --this.g;
            }
        }

        if (!this.world.isStatic)
        {
            byte var21 = this.datawatcher.getByte(16);
            byte var12 = (byte)(this.g > 10 ? 1 : 0);

            if (var21 != var12)
            {
                this.datawatcher.watch(16, Byte.valueOf(var12));
            }
        }
    }

    /**
     * True if the ghast has an unobstructed line of travel to the waypoint.
     */
    private boolean a(double par1, double par3, double par5, double par7)
    {
        double var9 = (this.c - this.locX) / par7;
        double var11 = (this.d - this.locY) / par7;
        double var13 = (this.e - this.locZ) / par7;
        AxisAlignedBB var15 = this.boundingBox.clone();

        for (int var16 = 1; (double)var16 < par7; ++var16)
        {
            var15.d(var9, var11, var13);

            if (!this.world.getCubes(this, var15).isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.ghast.moan";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.ghast.scream";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.ghast.death";
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.SULPHUR.id;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.random.nextInt(2) + this.random.nextInt(1 + par2);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.b(Item.GHAST_TEAR.id, 1);
        }

        var3 = this.random.nextInt(3) + this.random.nextInt(1 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.b(Item.SULPHUR.id, 1);
        }
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float aX()
    {
        return 10.0F;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        return this.random.nextInt(20) == 0 && super.canSpawn() && this.world.difficulty > 0;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int bv()
    {
        return 1;
    }
}
