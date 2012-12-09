package net.minecraft.server;

public class EntitySquid extends EntityWaterAnimal
{
    public float d = 0.0F;
    public float e = 0.0F;
    public float f = 0.0F;
    public float g = 0.0F;
    public float h = 0.0F;
    public float i = 0.0F;

    /** angle of the tentacles in radians */
    public float j = 0.0F;

    /** the last calculated angle of the tentacles in radians */
    public float bI = 0.0F;
    private float bJ = 0.0F;
    private float bK = 0.0F;
    private float bL = 0.0F;
    private float bM = 0.0F;
    private float bN = 0.0F;
    private float bO = 0.0F;

    public EntitySquid(World par1World)
    {
        super(par1World);
        this.texture = "/mob/squid.png";
        this.a(0.95F, 0.95F);
        this.bK = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
    }

    public int getMaxHealth()
    {
        return 10;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return null;
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
        return 0;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropDeathLoot(boolean par1, int par2)
    {
        int var3 = this.random.nextInt(3 + par2) + 1;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            this.a(new ItemStack(Item.INK_SACK, 1, 0), 0.0F);
        }
    }

    /**
     * Checks if this entity is inside water (if inWater field is true as a result of handleWaterMovement() returning
     * true)
     */
    public boolean H()
    {
        return this.world.a(this.boundingBox.grow(0.0D, -0.6000000238418579D, 0.0D), Material.WATER, this);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void c()
    {
        super.c();
        this.e = this.d;
        this.g = this.f;
        this.i = this.h;
        this.bI = this.j;
        this.h += this.bK;

        if (this.h > ((float)Math.PI * 2F))
        {
            this.h -= ((float)Math.PI * 2F);

            if (this.random.nextInt(10) == 0)
            {
                this.bK = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
            }
        }

        if (this.H())
        {
            float var1;

            if (this.h < (float)Math.PI)
            {
                var1 = this.h / (float)Math.PI;
                this.j = MathHelper.sin(var1 * var1 * (float) Math.PI) * (float)Math.PI * 0.25F;

                if ((double)var1 > 0.75D)
                {
                    this.bJ = 1.0F;
                    this.bL = 1.0F;
                }
                else
                {
                    this.bL *= 0.8F;
                }
            }
            else
            {
                this.j = 0.0F;
                this.bJ *= 0.9F;
                this.bL *= 0.99F;
            }

            if (!this.world.isStatic)
            {
                this.motX = (double)(this.bM * this.bJ);
                this.motY = (double)(this.bN * this.bJ);
                this.motZ = (double)(this.bO * this.bJ);
            }

            var1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.aw += (-((float)Math.atan2(this.motX, this.motZ)) * 180.0F / (float)Math.PI - this.aw) * 0.1F;
            this.yaw = this.aw;
            this.f += (float)Math.PI * this.bL * 1.5F;
            this.d += (-((float)Math.atan2((double)var1, this.motY)) * 180.0F / (float)Math.PI - this.d) * 0.1F;
        }
        else
        {
            this.j = MathHelper.abs(MathHelper.sin(this.h)) * (float)Math.PI * 0.25F;

            if (!this.world.isStatic)
            {
                this.motX = 0.0D;
                this.motY -= 0.08D;
                this.motY *= 0.9800000190734863D;
                this.motZ = 0.0D;
            }

            this.d = (float)((double)this.d + (double)(-90.0F - this.d) * 0.02D);
        }
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void e(float par1, float par2)
    {
        this.move(this.motX, this.motY, this.motZ);
    }

    protected void bn()
    {
        ++this.bA;

        if (this.bA > 100)
        {
            this.bM = this.bN = this.bO = 0.0F;
        }
        else if (this.random.nextInt(50) == 0 || !this.ad || this.bM == 0.0F && this.bN == 0.0F && this.bO == 0.0F)
        {
            float var1 = this.random.nextFloat() * (float)Math.PI * 2.0F;
            this.bM = MathHelper.cos(var1) * 0.2F;
            this.bN = -0.1F + this.random.nextFloat() * 0.2F;
            this.bO = MathHelper.sin(var1) * 0.2F;
        }

        this.bk();
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        return this.locY > 45.0D && this.locY < 63.0D && super.canSpawn();
    }
}
