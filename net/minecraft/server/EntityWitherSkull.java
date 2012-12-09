package net.minecraft.server;

public class EntityWitherSkull extends EntityFireball
{
    public EntityWitherSkull(World par1World)
    {
        super(par1World);
        this.a(0.3125F, 0.3125F);
    }

    public EntityWitherSkull(World par1World, EntityLiving par2EntityLiving, double par3, double par5, double par7)
    {
        super(par1World, par2EntityLiving, par3, par5, par7);
        this.a(0.3125F, 0.3125F);
    }

    /**
     * Return the motion factor for this projectile. The factor is multiplied by the original motion.
     */
    protected float c()
    {
        return this.d() ? 0.73F : super.c();
    }

    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning()
    {
        return false;
    }

    public float a(Explosion par1Explosion, Block par2Block, int par3, int par4, int par5)
    {
        float var6 = super.a(par1Explosion, par2Block, par3, par4, par5);

        if (this.d() && par2Block != Block.BEDROCK && par2Block != Block.ENDER_PORTAL && par2Block != Block.ENDER_PORTAL_FRAME)
        {
            var6 = Math.min(0.8F, var6);
        }

        return var6;
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void a(MovingObjectPosition par1MovingObjectPosition)
    {
        if (!this.world.isStatic)
        {
            if (par1MovingObjectPosition.entity != null)
            {
                if (this.shooter != null)
                {
                    if (par1MovingObjectPosition.entity.damageEntity(DamageSource.mobAttack(this.shooter), 8) && !par1MovingObjectPosition.entity.isAlive())
                    {
                        this.shooter.heal(5);
                    }
                }
                else
                {
                    par1MovingObjectPosition.entity.damageEntity(DamageSource.MAGIC, 5);
                }

                if (par1MovingObjectPosition.entity instanceof EntityLiving)
                {
                    byte var2 = 0;

                    if (this.world.difficulty > 1)
                    {
                        if (this.world.difficulty == 2)
                        {
                            var2 = 10;
                        }
                        else if (this.world.difficulty == 3)
                        {
                            var2 = 40;
                        }
                    }

                    if (var2 > 0)
                    {
                        ((EntityLiving)par1MovingObjectPosition.entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 20 * var2, 1));
                    }
                }
            }

            this.world.createExplosion(this, this.locX, this.locY, this.locZ, 1.0F, false, this.world.getGameRules().getBoolean("mobGriefing"));
            this.die();
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean damageEntity(DamageSource par1DamageSource, int par2)
    {
        return false;
    }

    protected void a()
    {
        this.datawatcher.a(10, Byte.valueOf((byte) 0));
    }

    /**
     * Return whether this skull comes from an invulnerable (aura) wither boss.
     */
    public boolean d()
    {
        return this.datawatcher.getByte(10) == 1;
    }

    /**
     * Set whether this skull comes from an invulnerable (aura) wither boss.
     */
    public void e(boolean par1)
    {
        this.datawatcher.watch(10, Byte.valueOf((byte) (par1 ? 1 : 0)));
    }
}
