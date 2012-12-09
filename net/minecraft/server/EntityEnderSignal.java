package net.minecraft.server;

public class EntityEnderSignal extends Entity
{
    public int a = 0;

    /** 'x' location the eye should float towards. */
    private double b;

    /** 'y' location the eye should float towards. */
    private double c;

    /** 'z' location the eye should float towards. */
    private double d;
    private int e;
    private boolean f;

    public EntityEnderSignal(World par1World)
    {
        super(par1World);
        this.a(0.25F, 0.25F);
    }

    protected void a() {}

    public EntityEnderSignal(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        this.e = 0;
        this.a(0.25F, 0.25F);
        this.setPosition(par2, par4, par6);
        this.height = 0.0F;
    }

    /**
     * The location the eye should float/move towards. Currently used for moving towards the nearest stronghold. Args:
     * strongholdX, strongholdY, strongholdZ
     */
    public void a(double par1, int par3, double par4)
    {
        double var6 = par1 - this.locX;
        double var8 = par4 - this.locZ;
        float var10 = MathHelper.sqrt(var6 * var6 + var8 * var8);

        if (var10 > 12.0F)
        {
            this.b = this.locX + var6 / (double)var10 * 12.0D;
            this.d = this.locZ + var8 / (double)var10 * 12.0D;
            this.c = this.locY + 8.0D;
        }
        else
        {
            this.b = par1;
            this.c = (double)par3;
            this.d = par4;
        }

        this.e = 0;
        this.f = this.random.nextInt(5) > 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        this.T = this.locX;
        this.U = this.locY;
        this.V = this.locZ;
        super.j_();
        this.locX += this.motX;
        this.locY += this.motY;
        this.locZ += this.motZ;
        float var1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / Math.PI);

        for (this.pitch = (float)(Math.atan2(this.motY, (double)var1) * 180.0D / Math.PI); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F)
        {
            ;
        }

        while (this.pitch - this.lastPitch >= 180.0F)
        {
            this.lastPitch += 360.0F;
        }

        while (this.yaw - this.lastYaw < -180.0F)
        {
            this.lastYaw -= 360.0F;
        }

        while (this.yaw - this.lastYaw >= 180.0F)
        {
            this.lastYaw += 360.0F;
        }

        this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
        this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;

        if (!this.world.isStatic)
        {
            double var2 = this.b - this.locX;
            double var4 = this.d - this.locZ;
            float var6 = (float)Math.sqrt(var2 * var2 + var4 * var4);
            float var7 = (float)Math.atan2(var4, var2);
            double var8 = (double)var1 + (double)(var6 - var1) * 0.0025D;

            if (var6 < 1.0F)
            {
                var8 *= 0.8D;
                this.motY *= 0.8D;
            }

            this.motX = Math.cos((double)var7) * var8;
            this.motZ = Math.sin((double)var7) * var8;

            if (this.locY < this.c)
            {
                this.motY += (1.0D - this.motY) * 0.014999999664723873D;
            }
            else
            {
                this.motY += (-1.0D - this.motY) * 0.014999999664723873D;
            }
        }

        float var10 = 0.25F;

        if (this.H())
        {
            for (int var3 = 0; var3 < 4; ++var3)
            {
                this.world.addParticle("bubble", this.locX - this.motX * (double) var10, this.locY - this.motY * (double) var10, this.locZ - this.motZ * (double) var10, this.motX, this.motY, this.motZ);
            }
        }
        else
        {
            this.world.addParticle("portal", this.locX - this.motX * (double) var10 + this.random.nextDouble() * 0.6D - 0.3D, this.locY - this.motY * (double) var10 - 0.5D, this.locZ - this.motZ * (double) var10 + this.random.nextDouble() * 0.6D - 0.3D, this.motX, this.motY, this.motZ);
        }

        if (!this.world.isStatic)
        {
            this.setPosition(this.locX, this.locY, this.locZ);
            ++this.e;

            if (this.e > 80 && !this.world.isStatic)
            {
                this.die();

                if (this.f)
                {
                    this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.EYE_OF_ENDER)));
                }
                else
                {
                    this.world.triggerEffect(2003, (int) Math.round(this.locX), (int) Math.round(this.locY), (int) Math.round(this.locZ), 0);
                }
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound) {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound) {}

    /**
     * Gets how bright this entity is.
     */
    public float c(float par1)
    {
        return 1.0F;
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean aq()
    {
        return false;
    }
}
