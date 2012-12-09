package net.minecraft.server;

public class EntityGiantZombie extends EntityMonster
{
    public EntityGiantZombie(World par1World)
    {
        super(par1World);
        this.texture = "/mob/zombie.png";
        this.bG = 0.5F;
        this.height *= 6.0F;
        this.a(this.width * 6.0F, this.length * 6.0F);
    }

    public int getMaxHealth()
    {
        return 100;
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float a(int par1, int par2, int par3)
    {
        return this.world.p(par1, par2, par3) - 0.5F;
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int c(Entity par1Entity)
    {
        return 50;
    }
}
