package net.minecraft.server;

public class EntityThrownExpBottle extends EntityProjectile
{
    public EntityThrownExpBottle(World par1World)
    {
        super(par1World);
    }

    public EntityThrownExpBottle(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public EntityThrownExpBottle(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float g()
    {
        return 0.07F;
    }

    protected float c()
    {
        return 0.7F;
    }

    protected float d()
    {
        return -20.0F;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void a(MovingObjectPosition par1MovingObjectPosition)
    {
        if (!this.world.isStatic)
        {
            this.world.triggerEffect(2002, (int) Math.round(this.locX), (int) Math.round(this.locY), (int) Math.round(this.locZ), 0);
            int var2 = 3 + this.world.random.nextInt(5) + this.world.random.nextInt(5);

            while (var2 > 0)
            {
                int var3 = EntityExperienceOrb.getOrbValue(var2);
                var2 -= var3;
                this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, var3));
            }

            this.die();
        }
    }
}
