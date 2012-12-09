package net.minecraft.server;

public class EntitySmallFireball extends EntityFireball
{
    public EntitySmallFireball(World par1World)
    {
        super(par1World);
        this.a(0.3125F, 0.3125F);
    }

    public EntitySmallFireball(World par1World, EntityLiving par2EntityLiving, double par3, double par5, double par7)
    {
        super(par1World, par2EntityLiving, par3, par5, par7);
        this.a(0.3125F, 0.3125F);
    }

    public EntitySmallFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.a(0.3125F, 0.3125F);
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
                if (!par1MovingObjectPosition.entity.isFireproof() && par1MovingObjectPosition.entity.damageEntity(DamageSource.fireball(this, this.shooter), 5))
                {
                    par1MovingObjectPosition.entity.setOnFire(5);
                }
            }
            else
            {
                int var2 = par1MovingObjectPosition.b;
                int var3 = par1MovingObjectPosition.c;
                int var4 = par1MovingObjectPosition.d;

                switch (par1MovingObjectPosition.face)
                {
                    case 0:
                        --var3;
                        break;

                    case 1:
                        ++var3;
                        break;

                    case 2:
                        --var4;
                        break;

                    case 3:
                        ++var4;
                        break;

                    case 4:
                        --var2;
                        break;

                    case 5:
                        ++var2;
                }

                if (this.world.isEmpty(var2, var3, var4))
                {
                    this.world.setTypeId(var2, var3, var4, Block.FIRE.id);
                }
            }

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
}
