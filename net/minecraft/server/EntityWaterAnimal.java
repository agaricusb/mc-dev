package net.minecraft.server;

public abstract class EntityWaterAnimal extends EntityCreature implements IAnimal
{
    public EntityWaterAnimal(World par1World)
    {
        super(par1World);
    }

    public boolean bc()
    {
        return true;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        return this.world.b(this.boundingBox);
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int aN()
    {
        return 120;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean bj()
    {
        return true;
    }

    /**
     * Get the experience points the entity currently has.
     */
    protected int getExpValue(EntityHuman par1EntityPlayer)
    {
        return 1 + this.world.random.nextInt(3);
    }

    /**
     * Gets called every tick from main Entity class
     */
    public void y()
    {
        int var1 = this.getAirTicks();
        super.y();

        if (this.isAlive() && !this.a(Material.WATER))
        {
            --var1;
            this.setAirTicks(var1);

            if (this.getAirTicks() == -20)
            {
                this.setAirTicks(0);
                this.damageEntity(DamageSource.DROWN, 2);
            }
        }
        else
        {
            this.setAirTicks(300);
        }
    }
}
