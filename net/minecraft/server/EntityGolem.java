package net.minecraft.server;

public abstract class EntityGolem extends EntityCreature implements IAnimal
{
    public EntityGolem(World par1World)
    {
        super(par1World);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1) {}

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "none";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "none";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "none";
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
        return false;
    }
}
