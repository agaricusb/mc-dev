package net.minecraft.server;

public class EntityBlaze extends EntityMonster
{
    /** Random offset used in floating behaviour */
    private float d = 0.5F;

    /** ticks until heightOffset is randomized */
    private int e;
    private int f;

    public EntityBlaze(World par1World)
    {
        super(par1World);
        this.texture = "/mob/fire.png";
        this.fireProof = true;
        this.bc = 10;
    }

    public int getMaxHealth()
    {
        return 20;
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, new Byte((byte) 0));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.blaze.breathe";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.blaze.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.blaze.death";
    }

    /**
     * Gets how bright this entity is.
     */
    public float c(float par1)
    {
        return 1.0F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        if (!this.world.isStatic)
        {
            if (this.G())
            {
                this.damageEntity(DamageSource.DROWN, 1);
            }

            --this.e;

            if (this.e <= 0)
            {
                this.e = 100;
                this.d = 0.5F + (float)this.random.nextGaussian() * 3.0F;
            }

            if (this.l() != null && this.l().locY + (double)this.l().getHeadHeight() > this.locY + (double)this.getHeadHeight() + (double)this.d)
            {
                this.motY += (0.30000001192092896D - this.motY) * 0.30000001192092896D;
            }
        }

        if (this.random.nextInt(24) == 0)
        {
            this.world.makeSound(this.locX + 0.5D, this.locY + 0.5D, this.locZ + 0.5D, "fire.fire", 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F);
        }

        if (!this.onGround && this.motY < 0.0D)
        {
            this.motY *= 0.6D;
        }

        for (int var1 = 0; var1 < 2; ++var1)
        {
            this.world.addParticle("largesmoke", this.locX + (this.random.nextDouble() - 0.5D) * (double) this.width, this.locY + this.random.nextDouble() * (double) this.length, this.locZ + (this.random.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
        }

        super.c();
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void a(Entity par1Entity, float par2)
    {
        if (this.attackTicks <= 0 && par2 < 2.0F && par1Entity.boundingBox.e > this.boundingBox.b && par1Entity.boundingBox.b < this.boundingBox.e)
        {
            this.attackTicks = 20;
            this.m(par1Entity);
        }
        else if (par2 < 30.0F)
        {
            double var3 = par1Entity.locX - this.locX;
            double var5 = par1Entity.boundingBox.b + (double)(par1Entity.length / 2.0F) - (this.locY + (double)(this.length / 2.0F));
            double var7 = par1Entity.locZ - this.locZ;

            if (this.attackTicks == 0)
            {
                ++this.f;

                if (this.f == 1)
                {
                    this.attackTicks = 60;
                    this.f(true);
                }
                else if (this.f <= 4)
                {
                    this.attackTicks = 6;
                }
                else
                {
                    this.attackTicks = 100;
                    this.f = 0;
                    this.f(false);
                }

                if (this.f > 1)
                {
                    float var9 = MathHelper.c(par2) * 0.5F;
                    this.world.a((EntityHuman) null, 1009, (int) this.locX, (int) this.locY, (int) this.locZ, 0);

                    for (int var10 = 0; var10 < 1; ++var10)
                    {
                        EntitySmallFireball var11 = new EntitySmallFireball(this.world, this, var3 + this.random.nextGaussian() * (double)var9, var5, var7 + this.random.nextGaussian() * (double)var9);
                        var11.locY = this.locY + (double)(this.length / 2.0F) + 0.5D;
                        this.world.addEntity(var11);
                    }
                }
            }

            this.yaw = (float)(Math.atan2(var7, var3) * 180.0D / Math.PI) - 90.0F;
            this.b = true;
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1) {}

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.BLAZE_ROD.id;
    }

    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning()
    {
        return this.m();
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        if (par1)
        {
            int var3 = this.random.nextInt(2 + par2);

            for (int var4 = 0; var4 < var3; ++var4)
            {
                this.b(Item.BLAZE_ROD.id, 1);
            }
        }
    }

    public boolean m()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void f(boolean par1)
    {
        byte var2 = this.datawatcher.getByte(16);

        if (par1)
        {
            var2 = (byte)(var2 | 1);
        }
        else
        {
            var2 &= -2;
        }

        this.datawatcher.watch(16, Byte.valueOf(var2));
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean i_()
    {
        return true;
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int c(Entity par1Entity)
    {
        return 6;
    }
}
