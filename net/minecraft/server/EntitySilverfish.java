package net.minecraft.server;

public class EntitySilverfish extends EntityMonster
{
    /**
     * A cooldown before this entity will search for another Silverfish to join them in battle.
     */
    private int d;

    public EntitySilverfish(World par1World)
    {
        super(par1World);
        this.texture = "/mob/silverfish.png";
        this.a(0.3F, 0.7F);
        this.bH = 0.6F;
    }

    public int getMaxHealth()
    {
        return 8;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findTarget()
    {
        double var1 = 8.0D;
        return this.world.findNearbyVulnerablePlayer(this, var1);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.silverfish.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.silverfish.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.silverfish.kill";
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
        else
        {
            if (this.d <= 0 && (par1DamageSource instanceof EntityDamageSource || par1DamageSource == DamageSource.MAGIC))
            {
                this.d = 20;
            }

            return super.damageEntity(par1DamageSource, par2);
        }
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void a(Entity par1Entity, float par2)
    {
        if (this.attackTicks <= 0 && par2 < 1.2F && par1Entity.boundingBox.e > this.boundingBox.b && par1Entity.boundingBox.b < this.boundingBox.e)
        {
            this.attackTicks = 20;
            this.m(par1Entity);
        }
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void a(int par1, int par2, int par3, int par4)
    {
        this.makeSound("mob.silverfish.step", 0.15F, 1.0F);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        this.ax = this.yaw;
        super.j_();
    }

    protected void bn()
    {
        super.bn();

        if (!this.world.isStatic)
        {
            int var1;
            int var2;
            int var3;
            int var5;

            if (this.d > 0)
            {
                --this.d;

                if (this.d == 0)
                {
                    var1 = MathHelper.floor(this.locX);
                    var2 = MathHelper.floor(this.locY);
                    var3 = MathHelper.floor(this.locZ);
                    boolean var4 = false;

                    for (var5 = 0; !var4 && var5 <= 5 && var5 >= -5; var5 = var5 <= 0 ? 1 - var5 : 0 - var5)
                    {
                        for (int var6 = 0; !var4 && var6 <= 10 && var6 >= -10; var6 = var6 <= 0 ? 1 - var6 : 0 - var6)
                        {
                            for (int var7 = 0; !var4 && var7 <= 10 && var7 >= -10; var7 = var7 <= 0 ? 1 - var7 : 0 - var7)
                            {
                                int var8 = this.world.getTypeId(var1 + var6, var2 + var5, var3 + var7);

                                if (var8 == Block.MONSTER_EGGS.id)
                                {
                                    this.world.triggerEffect(2001, var1 + var6, var2 + var5, var3 + var7, Block.MONSTER_EGGS.id + (this.world.getData(var1 + var6, var2 + var5, var3 + var7) << 12));
                                    this.world.setTypeId(var1 + var6, var2 + var5, var3 + var7, 0);
                                    Block.MONSTER_EGGS.postBreak(this.world, var1 + var6, var2 + var5, var3 + var7, 0);

                                    if (this.random.nextBoolean())
                                    {
                                        var4 = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (this.target == null && !this.k())
            {
                var1 = MathHelper.floor(this.locX);
                var2 = MathHelper.floor(this.locY + 0.5D);
                var3 = MathHelper.floor(this.locZ);
                int var9 = this.random.nextInt(6);
                var5 = this.world.getTypeId(var1 + Facing.b[var9], var2 + Facing.c[var9], var3 + Facing.d[var9]);

                if (BlockMonsterEggs.e(var5))
                {
                    this.world.setTypeIdAndData(var1 + Facing.b[var9], var2 + Facing.c[var9], var3 + Facing.d[var9], Block.MONSTER_EGGS.id, BlockMonsterEggs.f(var5));
                    this.aR();
                    this.die();
                }
                else
                {
                    this.i();
                }
            }
            else if (this.target != null && !this.k())
            {
                this.target = null;
            }
        }
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float a(int par1, int par2, int par3)
    {
        return this.world.getTypeId(par1, par2 - 1, par3) == Block.STONE.id ? 10.0F : super.a(par1, par2, par3);
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean i_()
    {
        return true;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        if (super.canSpawn())
        {
            EntityHuman var1 = this.world.findNearbyPlayer(this, 5.0D);
            return var1 == null;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int c(Entity par1Entity)
    {
        return 1;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumMonsterType getMonsterType()
    {
        return EnumMonsterType.ARTHROPOD;
    }
}
