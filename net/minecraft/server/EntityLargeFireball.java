package net.minecraft.server;

public class EntityLargeFireball extends EntityFireball
{
    public EntityLargeFireball(World par1World)
    {
        super(par1World);
    }

    public EntityLargeFireball(World par1World, EntityLiving par2EntityLiving, double par3, double par5, double par7)
    {
        super(par1World, par2EntityLiving, par3, par5, par7);
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
                par1MovingObjectPosition.entity.damageEntity(DamageSource.fireball(this, this.shooter), 6);
            }

            this.world.createExplosion((Entity) null, this.locX, this.locY, this.locZ, 1.0F, true, this.world.getGameRules().getBoolean("mobGriefing"));
            this.die();
        }
    }
}
