package net.minecraft.server;

public class EntityOcelot extends EntityTameableAnimal
{
    /**
     * The tempt AI task for this mob, used to prevent taming while it is fleeing.
     */
    private PathfinderGoalTempt e;

    public EntityOcelot(World par1World)
    {
        super(par1World);
        this.texture = "/mob/ozelot.png";
        this.a(0.6F, 0.8F);
        this.getNavigation().a(true);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, this.d);
        this.goalSelector.a(3, this.e = new PathfinderGoalTempt(this, 0.18F, Item.RAW_FISH.id, true));
        this.goalSelector.a(4, new PathfinderGoalAvoidPlayer(this, EntityHuman.class, 16.0F, 0.23F, 0.4F));
        this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 0.3F, 10.0F, 5.0F));
        this.goalSelector.a(6, new PathfinderGoalJumpOnBlock(this, 0.4F));
        this.goalSelector.a(7, new PathfinderGoalLeapAtTarget(this, 0.3F));
        this.goalSelector.a(8, new PathfinderGoalOcelotAttack(this));
        this.goalSelector.a(9, new PathfinderGoalBreed(this, 0.23F));
        this.goalSelector.a(10, new PathfinderGoalRandomStroll(this, 0.23F));
        this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
        this.targetSelector.a(1, new PathfinderGoalRandomTargetNonTamed(this, EntityChicken.class, 14.0F, 750, false));
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(18, Byte.valueOf((byte) 0));
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    public void bm()
    {
        if (this.getControllerMove().a())
        {
            float var1 = this.getControllerMove().b();

            if (var1 == 0.18F)
            {
                this.setSneaking(true);
                this.setSprinting(false);
            }
            else if (var1 == 0.4F)
            {
                this.setSneaking(false);
                this.setSprinting(true);
            }
            else
            {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        }
        else
        {
            this.setSneaking(false);
            this.setSprinting(false);
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean bj()
    {
        return !this.isTamed();
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

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1) {}

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setInt("CatType", this.getCatType());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.setCatType(par1NBTTagCompound.getInt("CatType"));
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return this.isTamed() ? (this.r() ? "mob.cat.purr" : (this.random.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow")) : "";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.cat.hitt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.cat.hitt";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float aX()
    {
        return 0.4F;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.LEATHER.id;
    }

    public boolean m(Entity par1Entity)
    {
        return par1Entity.damageEntity(DamageSource.mobAttack(this), 3);
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
            this.d.a(false);
            return super.damageEntity(par1DamageSource, par2);
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2) {}

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getItemInHand();

        if (this.isTamed())
        {
            if (par1EntityPlayer.name.equalsIgnoreCase(this.getOwnerName()) && !this.world.isStatic && !this.c(var2))
            {
                this.d.a(!this.isSitting());
            }
        }
        else if (this.e.f() && var2 != null && var2.id == Item.RAW_FISH.id && par1EntityPlayer.e(this) < 9.0D)
        {
            if (!par1EntityPlayer.abilities.canInstantlyBuild)
            {
                --var2.count;
            }

            if (var2.count <= 0)
            {
                par1EntityPlayer.inventory.setItem(par1EntityPlayer.inventory.itemInHandIndex, (ItemStack) null);
            }

            if (!this.world.isStatic)
            {
                if (this.random.nextInt(3) == 0)
                {
                    this.setTamed(true);
                    this.setCatType(1 + this.world.random.nextInt(3));
                    this.setOwnerName(par1EntityPlayer.name);
                    this.f(true);
                    this.d.a(true);
                    this.world.broadcastEntityEffect(this, (byte) 7);
                }
                else
                {
                    this.f(false);
                    this.world.broadcastEntityEffect(this, (byte) 6);
                }
            }

            return true;
        }

        return super.a(par1EntityPlayer);
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityOcelot b(EntityAgeable par1EntityAgeable)
    {
        EntityOcelot var2 = new EntityOcelot(this.world);

        if (this.isTamed())
        {
            var2.setOwnerName(this.getOwnerName());
            var2.setTamed(true);
            var2.setCatType(this.getCatType());
        }

        return var2;
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean c(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.id == Item.RAW_FISH.id;
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean mate(EntityAnimal par1EntityAnimal)
    {
        if (par1EntityAnimal == this)
        {
            return false;
        }
        else if (!this.isTamed())
        {
            return false;
        }
        else if (!(par1EntityAnimal instanceof EntityOcelot))
        {
            return false;
        }
        else
        {
            EntityOcelot var2 = (EntityOcelot)par1EntityAnimal;
            return !var2.isTamed() ? false : this.r() && var2.r();
        }
    }

    public int getCatType()
    {
        return this.datawatcher.getByte(18);
    }

    public void setCatType(int par1)
    {
        this.datawatcher.watch(18, Byte.valueOf((byte) par1));
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        if (this.world.random.nextInt(3) == 0)
        {
            return false;
        }
        else
        {
            if (this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox))
            {
                int var1 = MathHelper.floor(this.locX);
                int var2 = MathHelper.floor(this.boundingBox.b);
                int var3 = MathHelper.floor(this.locZ);

                if (var2 < 63)
                {
                    return false;
                }

                int var4 = this.world.getTypeId(var1, var2 - 1, var3);

                if (var4 == Block.GRASS.id || var4 == Block.LEAVES.id)
                {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Gets the username of the entity.
     */
    public String getLocalizedName()
    {
        return this.isTamed() ? "entity.Cat.name" : super.getLocalizedName();
    }

    /**
     * Initialize this creature.
     */
    public void bG()
    {
        if (this.world.random.nextInt(7) == 0)
        {
            for (int var1 = 0; var1 < 2; ++var1)
            {
                EntityOcelot var2 = new EntityOcelot(this.world);
                var2.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
                var2.setAge(-24000);
                this.world.addEntity(var2);
            }
        }
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.b(par1EntityAgeable);
    }
}
