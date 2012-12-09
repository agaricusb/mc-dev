package net.minecraft.server;

public class EntityChicken extends EntityAnimal
{
    public boolean d = false;
    public float e = 0.0F;
    public float f = 0.0F;
    public float g;
    public float h;
    public float i = 1.0F;

    /** The time until the next egg is spawned. */
    public int j;

    public EntityChicken(World par1World)
    {
        super(par1World);
        this.texture = "/mob/chicken.png";
        this.a(0.3F, 0.7F);
        this.j = this.random.nextInt(6000) + 6000;
        float var2 = 0.25F;
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38F));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, var2));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 0.25F, Item.SEEDS.id, false));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 0.28F));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, var2));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
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
        return 4;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        super.c();
        this.h = this.e;
        this.g = this.f;
        this.f = (float)((double)this.f + (double)(this.onGround ? -1 : 4) * 0.3D);

        if (this.f < 0.0F)
        {
            this.f = 0.0F;
        }

        if (this.f > 1.0F)
        {
            this.f = 1.0F;
        }

        if (!this.onGround && this.i < 1.0F)
        {
            this.i = 1.0F;
        }

        this.i = (float)((double)this.i * 0.9D);

        if (!this.onGround && this.motY < 0.0D)
        {
            this.motY *= 0.6D;
        }

        this.e += this.i * 2.0F;

        if (!this.isBaby() && !this.world.isStatic && --this.j <= 0)
        {
            this.makeSound("mob.chicken.plop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.b(Item.EGG.id, 1);
            this.j = this.random.nextInt(6000) + 6000;
        }
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
        return "mob.chicken.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.chicken.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.chicken.hurt";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void a(int par1, int par2, int par3, int par4)
    {
        this.makeSound("mob.chicken.step", 0.15F, 1.0F);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.FEATHER.id;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.random.nextInt(3) + this.random.nextInt(1 + par2);

        for (int var4 = 0; var4 < var3; ++var4)
        {
            this.b(Item.FEATHER.id, 1);
        }

        if (this.isBurning())
        {
            this.b(Item.COOKED_CHICKEN.id, 1);
        }
        else
        {
            this.b(Item.RAW_CHICKEN.id, 1);
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityChicken b(EntityAgeable par1EntityAgeable)
    {
        return new EntityChicken(this.world);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean c(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.getItem() instanceof ItemSeeds;
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.b(par1EntityAgeable);
    }
}
