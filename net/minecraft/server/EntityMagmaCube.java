package net.minecraft.server;

public class EntityMagmaCube extends EntitySlime
{
    public EntityMagmaCube(World par1World)
    {
        super(par1World);
        this.texture = "/mob/lava.png";
        this.fireProof = true;
        this.aN = 0.2F;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        return this.world.difficulty > 0 && this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox);
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int aW()
    {
        return this.getSize() * 3;
    }

    /**
     * Gets how bright this entity is.
     */
    public float c(float par1)
    {
        return 1.0F;
    }

    /**
     * Returns the name of a particle effect that may be randomly created by EntitySlime.onUpdate()
     */
    protected String h()
    {
        return "flame";
    }

    protected EntitySlime i()
    {
        return new EntityMagmaCube(this.world);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.MAGMA_CREAM.id;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.getLootId();

        if (var3 > 0 && this.getSize() > 1)
        {
            int var4 = this.random.nextInt(4) - 2;

            if (par2 > 0)
            {
                var4 += this.random.nextInt(par2 + 1);
            }

            for (int var5 = 0; var5 < var4; ++var5)
            {
                this.b(var3, 1);
            }
        }
    }

    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning()
    {
        return false;
    }

    /**
     * Gets the amount of time the slime needs to wait between jumps.
     */
    protected int j()
    {
        return super.j() * 4;
    }

    protected void k()
    {
        this.b *= 0.9F;
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void bi()
    {
        this.motY = (double)(0.42F + (float)this.getSize() * 0.1F);
        this.am = true;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1) {}

    /**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    protected boolean l()
    {
        return true;
    }

    /**
     * Gets the amount of damage dealt to the player when "attacked" by the slime.
     */
    protected int m()
    {
        return super.m() + 2;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.slime." + (this.getSize() > 1 ? "big" : "small");
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.slime." + (this.getSize() > 1 ? "big" : "small");
    }

    /**
     * Returns the name of the sound played when the slime jumps.
     */
    protected String n()
    {
        return this.getSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
    }

    /**
     * Whether or not the current entity is in lava
     */
    public boolean J()
    {
        return false;
    }

    /**
     * Returns true if the slime makes a sound when it lands after a jump (based upon the slime's size)
     */
    protected boolean o()
    {
        return true;
    }
}
