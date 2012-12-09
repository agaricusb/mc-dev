package net.minecraft.server;

public class EntityPig extends EntityAnimal
{
    /** AI task for player control. */
    private final PathfinderGoalPassengerCarrotStick d;

    public EntityPig(World par1World)
    {
        super(par1World);
        this.texture = "/mob/pig.png";
        this.a(0.9F, 0.9F);
        this.getNavigation().a(true);
        float var2 = 0.25F;
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38F));
        this.goalSelector.a(2, this.d = new PathfinderGoalPassengerCarrotStick(this, 0.34F));
        this.goalSelector.a(3, new PathfinderGoalBreed(this, var2));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 0.3F, Item.CARROT_STICK.id, false));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 0.3F, Item.CARROT.id, false));
        this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 0.28F));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, var2));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean be()
    {
        return true;
    }

    public int getMaxHealth()
    {
        return 10;
    }

    protected void bl()
    {
        super.bl();
    }

    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean bI()
    {
        ItemStack var1 = ((EntityHuman)this.passenger).bD();
        return var1 != null && var1.id == Item.CARROT_STICK.id;
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, Byte.valueOf((byte) 0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Saddle", this.hasSaddle());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.setSaddle(par1NBTTagCompound.getBoolean("Saddle"));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return "mob.pig.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.pig.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.pig.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void a(int par1, int par2, int par3, int par4)
    {
        this.makeSound("mob.pig.step", 0.15F, 1.0F);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        if (super.a(par1EntityPlayer))
        {
            return true;
        }
        else if (this.hasSaddle() && !this.world.isStatic && (this.passenger == null || this.passenger == par1EntityPlayer))
        {
            par1EntityPlayer.mount(this);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return this.isBurning() ? Item.GRILLED_PORK.id : Item.PORK.id;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.random.nextInt(3) + 1 + this.random.nextInt(1 + par2);

        for (int var4 = 0; var4 < var3; ++var4)
        {
            if (this.isBurning())
            {
                this.b(Item.GRILLED_PORK.id, 1);
            }
            else
            {
                this.b(Item.PORK.id, 1);
            }
        }

        if (this.hasSaddle())
        {
            this.b(Item.SADDLE.id, 1);
        }
    }

    /**
     * Returns true if the pig is saddled.
     */
    public boolean hasSaddle()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    /**
     * Set or remove the saddle of the pig.
     */
    public void setSaddle(boolean par1)
    {
        if (par1)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) 1));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) 0));
        }
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void a(EntityLightning par1EntityLightningBolt)
    {
        if (!this.world.isStatic)
        {
            EntityPigZombie var2 = new EntityPigZombie(this.world);
            var2.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            this.world.addEntity(var2);
            this.die();
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1)
    {
        super.a(par1);

        if (par1 > 5.0F && this.passenger instanceof EntityHuman)
        {
            ((EntityHuman)this.passenger).a(AchievementList.u);
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityPig b(EntityAgeable par1EntityAgeable)
    {
        return new EntityPig(this.world);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean c(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.id == Item.CARROT.id;
    }

    /**
     * Return the AI task for player control.
     */
    public PathfinderGoalPassengerCarrotStick n()
    {
        return this.d;
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.b(par1EntityAgeable);
    }
}
