package net.minecraft.server;

public class EntityExperienceOrb extends Entity
{
    /**
     * A constantly increasing value that RenderXPOrb uses to control the colour shifting (Green / yellow)
     */
    public int a;

    /** The age of the XP orb in ticks. */
    public int b = 0;
    public int c;

    /** The health of this XP orb. */
    private int d = 5;

    /** This is how much XP this orb has. */
    private int value;

    /** The closest EntityPlayer to this orb. */
    private EntityHuman targetPlayer;

    /** Threshold color for tracking players */
    private int targetTime;

    public EntityExperienceOrb(World par1World, double par2, double par4, double par6, int par8)
    {
        super(par1World);
        this.a(0.5F, 0.5F);
        this.height = this.length / 2.0F;
        this.setPosition(par2, par4, par6);
        this.yaw = (float)(Math.random() * 360.0D);
        this.motX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.motY = (double)((float)(Math.random() * 0.2D) * 2.0F);
        this.motZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.value = par8;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    public EntityExperienceOrb(World par1World)
    {
        super(par1World);
        this.a(0.25F, 0.25F);
        this.height = this.length / 2.0F;
    }

    protected void a() {}

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();

        if (this.c > 0)
        {
            --this.c;
        }

        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.029999999329447746D;

        if (this.world.getMaterial(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) == Material.LAVA)
        {
            this.motY = 0.20000000298023224D;
            this.motX = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            this.motZ = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            this.makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
        }

        this.i(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0D, this.locZ);
        double var1 = 8.0D;

        if (this.targetTime < this.a - 20 + this.id % 100)
        {
            if (this.targetPlayer == null || this.targetPlayer.e(this) > var1 * var1)
            {
                this.targetPlayer = this.world.findNearbyPlayer(this, var1);
            }

            this.targetTime = this.a;
        }

        if (this.targetPlayer != null)
        {
            double var3 = (this.targetPlayer.locX - this.locX) / var1;
            double var5 = (this.targetPlayer.locY + (double)this.targetPlayer.getHeadHeight() - this.locY) / var1;
            double var7 = (this.targetPlayer.locZ - this.locZ) / var1;
            double var9 = Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
            double var11 = 1.0D - var9;

            if (var11 > 0.0D)
            {
                var11 *= var11;
                this.motX += var3 / var9 * var11 * 0.1D;
                this.motY += var5 / var9 * var11 * 0.1D;
                this.motZ += var7 / var9 * var11 * 0.1D;
            }
        }

        this.move(this.motX, this.motY, this.motZ);
        float var13 = 0.98F;

        if (this.onGround)
        {
            var13 = 0.58800006F;
            int var4 = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));

            if (var4 > 0)
            {
                var13 = Block.byId[var4].frictionFactor * 0.98F;
            }
        }

        this.motX *= (double)var13;
        this.motY *= 0.9800000190734863D;
        this.motZ *= (double)var13;

        if (this.onGround)
        {
            this.motY *= -0.8999999761581421D;
        }

        ++this.a;
        ++this.b;

        if (this.b >= 6000)
        {
            this.die();
        }
    }

    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    public boolean I()
    {
        return this.world.a(this.boundingBox, Material.WATER, this);
    }

    /**
     * Will deal the specified amount of damage to the entity if the entity isn't immune to fire damage. Args:
     * amountDamage
     */
    protected void burn(int par1)
    {
        this.damageEntity(DamageSource.FIRE, par1);
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
            this.K();
            this.d -= par2;

            if (this.d <= 0)
            {
                this.die();
            }

            return false;
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("Health", (short) ((byte) this.d));
        par1NBTTagCompound.setShort("Age", (short) this.b);
        par1NBTTagCompound.setShort("Value", (short) this.value);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.d = par1NBTTagCompound.getShort("Health") & 255;
        this.b = par1NBTTagCompound.getShort("Age");
        this.value = par1NBTTagCompound.getShort("Value");
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void c_(EntityHuman par1EntityPlayer)
    {
        if (!this.world.isStatic)
        {
            if (this.c == 0 && par1EntityPlayer.bS == 0)
            {
                par1EntityPlayer.bS = 2;
                this.makeSound("random.orb", 0.1F, 0.5F * ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.8F));
                par1EntityPlayer.receive(this, 1);
                par1EntityPlayer.giveExp(this.value);
                this.die();
            }
        }
    }

    /**
     * Returns the XP value of this XP orb.
     */
    public int c()
    {
        return this.value;
    }

    /**
     * Get a fragment of the maximum experience points value for the supplied value of experience points value.
     */
    public static int getOrbValue(int par0)
    {
        return par0 >= 2477 ? 2477 : (par0 >= 1237 ? 1237 : (par0 >= 617 ? 617 : (par0 >= 307 ? 307 : (par0 >= 149 ? 149 : (par0 >= 73 ? 73 : (par0 >= 37 ? 37 : (par0 >= 17 ? 17 : (par0 >= 7 ? 7 : (par0 >= 3 ? 3 : 1)))))))));
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean aq()
    {
        return false;
    }
}
