package net.minecraft.server;

public class EntityEnderCrystal extends Entity
{
    /** Used to create the rotation animation when rendering the crystal. */
    public int a = 0;
    public int b;

    public EntityEnderCrystal(World par1World)
    {
        super(par1World);
        this.m = true;
        this.a(2.0F, 2.0F);
        this.height = this.length / 2.0F;
        this.b = 5;
        this.a = this.random.nextInt(100000);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    protected void a()
    {
        this.datawatcher.a(8, Integer.valueOf(this.b));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        ++this.a;
        this.datawatcher.watch(8, Integer.valueOf(this.b));
        int var1 = MathHelper.floor(this.locX);
        int var2 = MathHelper.floor(this.locY);
        int var3 = MathHelper.floor(this.locZ);

        if (this.world.getTypeId(var1, var2, var3) != Block.FIRE.id)
        {
            this.world.setTypeId(var1, var2, var3, Block.FIRE.id);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void b(NBTTagCompound par1NBTTagCompound) {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void a(NBTTagCompound par1NBTTagCompound) {}

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
        if (this.isInvulnerable())
        {
            return false;
        }
        else
        {
            if (!this.dead && !this.world.isStatic)
            {
                this.b = 0;

                if (this.b <= 0)
                {
                    this.die();

                    if (!this.world.isStatic)
                    {
                        this.world.explode((Entity) null, this.locX, this.locY, this.locZ, 6.0F, true);
                    }
                }
            }

            return true;
        }
    }
}
