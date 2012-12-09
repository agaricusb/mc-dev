package net.minecraft.server;

public class EntitySpider extends EntityMonster
{
    public EntitySpider(World par1World)
    {
        super(par1World);
        this.texture = "/mob/spider.png";
        this.a(1.4F, 0.9F);
        this.bG = 0.8F;
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, new Byte((byte) 0));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();

        if (!this.world.isStatic)
        {
            this.f(this.positionChanged);
        }
    }

    public int getMaxHealth()
    {
        return 16;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double X()
    {
        return (double)this.length * 0.75D - 0.5D;
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findTarget()
    {
        float var1 = this.c(1.0F);

        if (var1 < 0.5F)
        {
            double var2 = 16.0D;
            return this.world.findNearbyVulnerablePlayer(this, var2);
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.spider.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void a(int par1, int par2, int par3, int par4)
    {
        this.makeSound("mob.spider.step", 0.15F, 1.0F);
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void a(Entity par1Entity, float par2)
    {
        float var3 = this.c(1.0F);

        if (var3 > 0.5F && this.random.nextInt(100) == 0)
        {
            this.target = null;
        }
        else
        {
            if (par2 > 2.0F && par2 < 6.0F && this.random.nextInt(10) == 0)
            {
                if (this.onGround)
                {
                    double var4 = par1Entity.locX - this.locX;
                    double var6 = par1Entity.locZ - this.locZ;
                    float var8 = MathHelper.sqrt(var4 * var4 + var6 * var6);
                    this.motX = var4 / (double)var8 * 0.5D * 0.800000011920929D + this.motX * 0.20000000298023224D;
                    this.motZ = var6 / (double)var8 * 0.5D * 0.800000011920929D + this.motZ * 0.20000000298023224D;
                    this.motY = 0.4000000059604645D;
                }
            }
            else
            {
                super.a(par1Entity, par2);
            }
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.STRING.id;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        super.dropDeathLoot(par1, par2);

        if (par1 && (this.random.nextInt(3) == 0 || this.random.nextInt(1 + par2) > 0))
        {
            this.b(Item.SPIDER_EYE.id, 1);
        }
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean g_()
    {
        return this.o();
    }

    /**
     * Sets the Entity inside a web block.
     */
    public void am() {}

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumMonsterType getMonsterType()
    {
        return EnumMonsterType.ARTHROPOD;
    }

    public boolean e(MobEffect par1PotionEffect)
    {
        return par1PotionEffect.getEffectId() == MobEffectList.POISON.id ? false : super.e(par1PotionEffect);
    }

    /**
     * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
     * setBesideClimableBlock.
     */
    public boolean o()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
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
     * Initialize this creature.
     */
    public void bG()
    {
        if (this.world.random.nextInt(100) == 0)
        {
            EntitySkeleton var1 = new EntitySkeleton(this.world);
            var1.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
            var1.bG();
            this.world.addEntity(var1);
            var1.mount(this);
        }
    }
}
