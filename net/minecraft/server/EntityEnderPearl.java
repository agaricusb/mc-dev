package net.minecraft.server;

public class EntityEnderPearl extends EntityProjectile
{
    public EntityEnderPearl(World par1World)
    {
        super(par1World);
    }

    public EntityEnderPearl(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
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

        for (int var2 = 0; var2 < 32; ++var2)
        {
            this.world.addParticle("portal", this.locX, this.locY + this.random.nextDouble() * 2.0D, this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }

        if (!this.world.isStatic)
        {
            if (this.getShooter() != null && this.getShooter() instanceof EntityPlayer)
            {
                EntityPlayer var3 = (EntityPlayer)this.getShooter();

                if (!var3.playerConnection.disconnected && var3.world == this.world)
                {
                    this.getShooter().enderTeleportTo(this.locX, this.locY, this.locZ);
                    this.getShooter().fallDistance = 0.0F;
                    this.getShooter().damageEntity(DamageSource.FALL, 5);
                }
            }

            this.die();
        }
    }
}
