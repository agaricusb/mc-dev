package net.minecraft.server;

public class EntityLargeFireball extends EntityFireball
{
    public int e = 1;

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

            this.world.createExplosion((Entity) null, this.locX, this.locY, this.locZ, (float) this.e, true, this.world.getGameRules().getBoolean("mobGriefing"));
            this.die();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setInt("ExplosionPower", this.e);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("ExplosionPower"))
        {
            this.e = par1NBTTagCompound.getInt("ExplosionPower");
        }
    }
}
