package net.minecraft.server;

import java.util.List;

public class EntityPigZombie extends EntityZombie
{
    /** Above zero if this PigZombie is Angry. */
    private int angerLevel = 0;

    /** A random delay until this PigZombie next makes a sound. */
    private int soundDelay = 0;

    public EntityPigZombie(World par1World)
    {
        super(par1World);
        this.texture = "/mob/pigzombie.png";
        this.bH = 0.5F;
        this.fireProof = true;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean be()
    {
        return false;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        this.bH = this.target != null ? 0.95F : 0.5F;

        if (this.soundDelay > 0 && --this.soundDelay == 0)
        {
            this.makeSound("mob.zombiepig.zpigangry", this.aX() * 2.0F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
        }

        super.j_();
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        return this.world.difficulty > 0 && this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Anger", (short) this.angerLevel);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.angerLevel = par1NBTTagCompound.getShort("Anger");
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findTarget()
    {
        return this.angerLevel == 0 ? null : super.findTarget();
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
            Entity var3 = par1DamageSource.getEntity();

            if (var3 instanceof EntityHuman)
            {
                List var4 = this.world.getEntities(this, this.boundingBox.grow(32.0D, 32.0D, 32.0D));

                for (int var5 = 0; var5 < var4.size(); ++var5)
                {
                    Entity var6 = (Entity)var4.get(var5);

                    if (var6 instanceof EntityPigZombie)
                    {
                        EntityPigZombie var7 = (EntityPigZombie)var6;
                        var7.p(var3);
                    }
                }

                this.p(var3);
            }

            return super.damageEntity(par1DamageSource, par2);
        }
    }

    /**
     * Causes this PigZombie to become angry at the supplied Entity (which will be a player).
     */
    private void p(Entity par1Entity)
    {
        this.target = par1Entity;
        this.angerLevel = 400 + this.random.nextInt(400);
        this.soundDelay = this.random.nextInt(40);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.zombiepig.zpig";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.zombiepig.zpighurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.zombiepig.zpigdeath";
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.random.nextInt(2 + par2);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.b(Item.ROTTEN_FLESH.id, 1);
        }

        var3 = this.random.nextInt(2 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.b(Item.GOLD_NUGGET.id, 1);
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        return false;
    }

    protected void l(int par1)
    {
        this.b(Item.GOLD_INGOT.id, 1);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.ROTTEN_FLESH.id;
    }

    protected void bE()
    {
        this.setEquipment(0, new ItemStack(Item.GOLD_SWORD));
    }

    /**
     * Initialize this creature.
     */
    public void bG()
    {
        super.bG();
        this.setVillager(false);
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int c(Entity par1Entity)
    {
        ItemStack var2 = this.bD();
        int var3 = 5;

        if (var2 != null)
        {
            var3 += var2.a((Entity) this);
        }

        return var3;
    }
}
