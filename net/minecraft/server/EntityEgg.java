package net.minecraft.server;

public class EntityEgg extends EntityProjectile
{
    public EntityEgg(World par1World)
    {
        super(par1World);
    }

    public EntityEgg(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public EntityEgg(World par1World, double par2, double par4, double par6)
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
            par1MovingObjectPosition.entity.damageEntity(DamageSource.projectile(this, this.getShooter()), 0);
        }

        if (!this.world.isStatic && this.random.nextInt(8) == 0)
        {
            byte var2 = 1;

            if (this.random.nextInt(32) == 0)
            {
                var2 = 4;
            }

            for (int var3 = 0; var3 < var2; ++var3)
            {
                EntityChicken var4 = new EntityChicken(this.world);
                var4.setAge(-24000);
                var4.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
                this.world.addEntity(var4);
            }
        }

        for (int var5 = 0; var5 < 8; ++var5)
        {
            this.world.addParticle("snowballpoof", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.world.isStatic)
        {
            this.die();
        }
    }
}
