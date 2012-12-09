package net.minecraft.server;

public class EntityComplexPart extends Entity
{
    /** The dragon entity this dragon part belongs to */
    public final IComplex owner;

    /** The name of the Dragon Part */
    public final String b;

    public EntityComplexPart(IComplex par1, String par2, float par3, float par4)
    {
        super(par1.d());
        this.a(par3, par4);
        this.owner = par1;
        this.b = par2;
    }

    protected void a() {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void a(NBTTagCompound par1NBTTagCompound) {}

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void b(NBTTagCompound par1NBTTagCompound) {}

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean damageEntity(DamageSource par1DamageSource, int par2)
    {
        return this.isInvulnerable() ? false : this.owner.a(this, par1DamageSource, par2);
    }

    /**
     * Returns true if Entity argument is equal to this Entity
     */
    public boolean i(Entity par1Entity)
    {
        return this == par1Entity || this.owner == par1Entity;
    }
}
