package net.minecraft.server;

public class EntitySnowball extends EntityProjectile
{
    public EntitySnowball(World par1World)
    {
        super(par1World);
    }

    public EntitySnowball(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public EntitySnowball(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void a(MovingObjectPosition par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entity != null)
        {
            byte var2 = 0;

            if (par1MovingObjectPosition.entity instanceof EntityBlaze)
            {
                var2 = 3;
            }

            par1MovingObjectPosition.entity.damageEntity(DamageSource.projectile(this, this.getShooter()), var2);
        }

        for (int var3 = 0; var3 < 8; ++var3)
        {
            this.world.addParticle("snowballpoof", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.world.isStatic)
        {
            this.die();
        }
    }
}
