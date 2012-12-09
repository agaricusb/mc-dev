package net.minecraft.server;

public abstract class EntityAgeable extends EntityCreature
{
    public EntityAgeable(World par1World)
    {
        super(par1World);
    }

    public abstract EntityAgeable createChild(EntityAgeable var1);

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getItemInHand();

        if (var2 != null && var2.id == Item.MONSTER_EGG.id && !this.world.isStatic)
        {
            Class var3 = EntityTypes.a(var2.getData());

            if (var3 != null && var3.isAssignableFrom(this.getClass()))
            {
                EntityAgeable var4 = this.createChild(this);

                if (var4 != null)
                {
                    var4.setAge(-24000);
                    var4.setPositionRotation(this.locX, this.locY, this.locZ, 0.0F, 0.0F);
                    this.world.addEntity(var4);
                }
            }
        }

        return super.a(par1EntityPlayer);
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(12, new Integer(0));
    }

    /**
     * The age value may be negative or positive or zero. If it's negative, it get's incremented on each tick, if it's
     * positive, it get's decremented each tick. Don't confuse this with EntityLiving.getAge. With a negative value the
     * Entity is considered a child.
     */
    public int getAge()
    {
        return this.datawatcher.getInt(12);
    }

    /**
     * The age value may be negative or positive or zero. If it's negative, it get's incremented on each tick, if it's
     * positive, it get's decremented each tick. With a negative value the Entity is considered a child.
     */
    public void setAge(int par1)
    {
        this.datawatcher.watch(12, Integer.valueOf(par1));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setInt("Age", this.aE());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.setAge(par1NBTTagCompound.getInt("Age"));
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        super.c();
        int var1 = this.aE();

        if (var1 < 0)
        {
            ++var1;
            this.setAge(var1);
        }
        else if (var1 > 0)
        {
            --var1;
            this.setAge(var1);
        }
    }

    /**
     * If Animal, checks if the age timer is negative
     */
    public boolean isBaby()
    {
        return this.aE() < 0;
    }
}
