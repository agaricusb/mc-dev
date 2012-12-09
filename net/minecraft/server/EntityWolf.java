package net.minecraft.server;

public class EntityWolf extends EntityTameableAnimal
{
    private float e;
    private float f;

    /** true is the wolf is wet else false */
    private boolean g;
    private boolean h;

    /**
     * This time increases while wolf is shaking and emitting water particles.
     */
    private float i;
    private float j;

    public EntityWolf(World par1World)
    {
        super(par1World);
        this.texture = "/mob/wolf.png";
        this.a(0.6F, 0.8F);
        this.bG = 0.3F;
        this.getNavigation().a(true);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, this.d);
        this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, this.bG, true));
        this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, this.bG, 10.0F, 2.0F));
        this.goalSelector.a(6, new PathfinderGoalBreed(this, this.bG));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, this.bG));
        this.goalSelector.a(8, new PathfinderGoalBeg(this, 8.0F));
        this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
        this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
        this.targetSelector.a(3, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(4, new PathfinderGoalRandomTargetNonTamed(this, EntitySheep.class, 16.0F, 200, false));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean be()
    {
        return true;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void b(EntityLiving par1EntityLiving)
    {
        super.b(par1EntityLiving);

        if (par1EntityLiving instanceof EntityHuman)
        {
            this.setAngry(true);
        }
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void bm()
    {
        this.datawatcher.watch(18, Integer.valueOf(this.getHealth()));
    }

    public int getMaxHealth()
    {
        return this.isTamed() ? 20 : 8;
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(18, new Integer(this.getHealth()));
        this.datawatcher.a(19, new Byte((byte) 0));
        this.datawatcher.a(20, new Byte((byte) BlockCloth.e_(1)));
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void a(int par1, int par2, int par3, int par4)
    {
        this.makeSound("mob.wolf.step", 0.15F, 1.0F);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Angry", this.isAngry());
        par1NBTTagCompound.setByte("CollarColor", (byte)this.getCollarColor());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.setAngry(par1NBTTagCompound.getBoolean("Angry"));

        if (par1NBTTagCompound.hasKey("CollarColor"))
        {
            this.setCollarColor(par1NBTTagCompound.getByte("CollarColor"));
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean bj()
    {
        return this.isAngry();
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return this.isAngry() ? "mob.wolf.growl" : (this.random.nextInt(3) == 0 ? (this.isTamed() && this.datawatcher.getInt(18) < 10 ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.wolf.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.wolf.death";
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
        return -1;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        super.c();

        if (!this.world.isStatic && this.g && !this.h && !this.k() && this.onGround)
        {
            this.h = true;
            this.i = 0.0F;
            this.j = 0.0F;
            this.world.broadcastEntityEffect(this, (byte) 8);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();
        this.f = this.e;

        if (this.bM())
        {
            this.e += (1.0F - this.e) * 0.4F;
        }
        else
        {
            this.e += (0.0F - this.e) * 0.4F;
        }

        if (this.bM())
        {
            this.bH = 10;
        }

        if (this.G())
        {
            this.g = true;
            this.h = false;
            this.i = 0.0F;
            this.j = 0.0F;
        }
        else if ((this.g || this.h) && this.h)
        {
            if (this.i == 0.0F)
            {
                this.makeSound("mob.wolf.shake", this.aX(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            this.j = this.i;
            this.i += 0.05F;

            if (this.j >= 2.0F)
            {
                this.g = false;
                this.h = false;
                this.j = 0.0F;
                this.i = 0.0F;
            }

            if (this.i > 0.4F)
            {
                float var1 = (float)this.boundingBox.b;
                int var2 = (int)(MathHelper.sin((this.i - 0.4F) * (float) Math.PI) * 7.0F);

                for (int var3 = 0; var3 < var2; ++var3)
                {
                    float var4 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float var5 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.world.addParticle("splash", this.locX + (double) var4, (double) (var1 + 0.8F), this.locZ + (double) var5, this.motX, this.motY, this.motZ);
                }
            }
        }
    }

    public float getHeadHeight()
    {
        return this.length * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int bp()
    {
        return this.isSitting() ? 20 : super.bp();
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
            this.d.a(false);

            if (var3 != null && !(var3 instanceof EntityHuman) && !(var3 instanceof EntityArrow))
            {
                par2 = (par2 + 1) / 2;
            }

            return super.damageEntity(par1DamageSource, par2);
        }
    }

    public boolean m(Entity par1Entity)
    {
        int var2 = this.isTamed() ? 4 : 2;
        return par1Entity.damageEntity(DamageSource.mobAttack(this), var2);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getItemInHand();

        if (this.isTamed())
        {
            if (var2 != null)
            {
                if (Item.byId[var2.id] instanceof ItemFood)
                {
                    ItemFood var3 = (ItemFood) Item.byId[var2.id];

                    if (var3.i() && this.datawatcher.getInt(18) < 20)
                    {
                        if (!par1EntityPlayer.abilities.canInstantlyBuild)
                        {
                            --var2.count;
                        }

                        this.heal(var3.getNutrition());

                        if (var2.count <= 0)
                        {
                            par1EntityPlayer.inventory.setItem(par1EntityPlayer.inventory.itemInHandIndex, (ItemStack) null);
                        }

                        return true;
                    }
                }
                else if (var2.id == Item.INK_SACK.id)
                {
                    int var4 = BlockCloth.e_(var2.getData());

                    if (var4 != this.getCollarColor())
                    {
                        this.setCollarColor(var4);

                        if (!par1EntityPlayer.abilities.canInstantlyBuild && --var2.count <= 0)
                        {
                            par1EntityPlayer.inventory.setItem(par1EntityPlayer.inventory.itemInHandIndex, (ItemStack) null);
                        }

                        return true;
                    }
                }
            }

            if (par1EntityPlayer.name.equalsIgnoreCase(this.getOwnerName()) && !this.world.isStatic && !this.c(var2))
            {
                this.d.a(!this.isSitting());
                this.bE = false;
                this.setPathEntity((PathEntity) null);
            }
        }
        else if (var2 != null && var2.id == Item.BONE.id && !this.isAngry())
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
                    this.setPathEntity((PathEntity) null);
                    this.b((EntityLiving) null);
                    this.d.a(true);
                    this.setHealth(20);
                    this.setOwnerName(par1EntityPlayer.name);
                    this.f(true);
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
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean c(ItemStack par1ItemStack)
    {
        return par1ItemStack == null ? false : (!(Item.byId[par1ItemStack.id] instanceof ItemFood) ? false : ((ItemFood) Item.byId[par1ItemStack.id]).i());
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int bv()
    {
        return 8;
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    public boolean isAngry()
    {
        return (this.datawatcher.getByte(16) & 2) != 0;
    }

    /**
     * Sets whether this wolf is angry or not.
     */
    public void setAngry(boolean par1)
    {
        byte var2 = this.datawatcher.getByte(16);

        if (par1)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var2 | 2)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var2 & -3)));
        }
    }

    /**
     * Return this wolf's collar color.
     */
    public int getCollarColor()
    {
        return this.datawatcher.getByte(20) & 15;
    }

    /**
     * Set this wolf's collar color.
     */
    public void setCollarColor(int par1)
    {
        this.datawatcher.watch(20, Byte.valueOf((byte) (par1 & 15)));
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityWolf b(EntityAgeable par1EntityAgeable)
    {
        EntityWolf var2 = new EntityWolf(this.world);
        String var3 = this.getOwnerName();

        if (var3 != null && var3.trim().length() > 0)
        {
            var2.setOwnerName(var3);
            var2.setTamed(true);
        }

        return var2;
    }

    public void j(boolean par1)
    {
        byte var2 = this.datawatcher.getByte(19);

        if (par1)
        {
            this.datawatcher.watch(19, Byte.valueOf((byte) 1));
        }
        else
        {
            this.datawatcher.watch(19, Byte.valueOf((byte) 0));
        }
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
        else if (!(par1EntityAnimal instanceof EntityWolf))
        {
            return false;
        }
        else
        {
            EntityWolf var2 = (EntityWolf)par1EntityAnimal;
            return !var2.isTamed() ? false : (var2.isSitting() ? false : this.r() && var2.r());
        }
    }

    public boolean bM()
    {
        return this.datawatcher.getByte(19) == 1;
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.b(par1EntityAgeable);
    }
}
