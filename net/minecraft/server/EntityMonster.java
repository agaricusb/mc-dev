package net.minecraft.server;

public abstract class EntityMonster extends EntityCreature implements IMonster
{
    public EntityMonster(World par1World)
    {
        super(par1World);
        this.bc = 5;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        this.bo();
        float var1 = this.c(1.0F);

        if (var1 > 0.5F)
        {
            this.bA += 2;
        }

        super.c();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();

        if (!this.world.isStatic && this.world.difficulty == 0)
        {
            this.die();
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findTarget()
    {
        EntityHuman var1 = this.world.findNearbyVulnerablePlayer(this, 16.0D);
        return var1 != null && this.n(var1) ? var1 : null;
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
        else if (super.damageEntity(par1DamageSource, par2))
        {
            Entity var3 = par1DamageSource.getEntity();

            if (this.passenger != var3 && this.vehicle != var3)
            {
                if (var3 != this)
                {
                    this.target = var3;
                }

                return true;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean m(Entity par1Entity)
    {
        int var2 = this.c(par1Entity);

        if (this.hasEffect(MobEffectList.INCREASE_DAMAGE))
        {
            var2 += 3 << this.getEffect(MobEffectList.INCREASE_DAMAGE).getAmplifier();
        }

        if (this.hasEffect(MobEffectList.WEAKNESS))
        {
            var2 -= 2 << this.getEffect(MobEffectList.WEAKNESS).getAmplifier();
        }

        int var3 = 0;

        if (par1Entity instanceof EntityLiving)
        {
            var2 += EnchantmentManager.a(this, (EntityLiving) par1Entity);
            var3 += EnchantmentManager.getKnockbackEnchantmentLevel(this, (EntityLiving) par1Entity);
        }

        boolean var4 = par1Entity.damageEntity(DamageSource.mobAttack(this), var2);

        if (var4)
        {
            if (var3 > 0)
            {
                par1Entity.g((double) (-MathHelper.sin(this.yaw * (float) Math.PI / 180.0F) * (float) var3 * 0.5F), 0.1D, (double) (MathHelper.cos(this.yaw * (float) Math.PI / 180.0F) * (float) var3 * 0.5F));
                this.motX *= 0.6D;
                this.motZ *= 0.6D;
            }

            int var5 = EnchantmentManager.getFireAspectEnchantmentLevel(this);

            if (var5 > 0)
            {
                par1Entity.setOnFire(var5 * 4);
            }
        }

        return var4;
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
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float a(int par1, int par2, int par3)
    {
        return 0.5F - this.world.p(par1, par2, par3);
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean i_()
    {
        int var1 = MathHelper.floor(this.locX);
        int var2 = MathHelper.floor(this.boundingBox.b);
        int var3 = MathHelper.floor(this.locZ);

        if (this.world.b(EnumSkyBlock.SKY, var1, var2, var3) > this.random.nextInt(32))
        {
            return false;
        }
        else
        {
            int var4 = this.world.getLightLevel(var1, var2, var3);

            if (this.world.M())
            {
                int var5 = this.world.j;
                this.world.j = 10;
                var4 = this.world.getLightLevel(var1, var2, var3);
                this.world.j = var5;
            }

            return var4 <= this.random.nextInt(8);
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        return this.i_() && super.canSpawn();
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int c(Entity par1Entity)
    {
        return 2;
    }
}
