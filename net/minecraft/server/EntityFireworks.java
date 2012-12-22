package net.minecraft.server;

public class EntityFireworks extends Entity
{
    private int a;
    private int b;

    public EntityFireworks(World par1World)
    {
        super(par1World);
        this.a(0.25F, 0.25F);
    }

    protected void a()
    {
        this.datawatcher.a(8, new ItemStack(0, 0, 0));
    }

    public EntityFireworks(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
    {
        super(par1World);
        this.a = 0;
        this.a(0.25F, 0.25F);
        this.setPosition(par2, par4, par6);
        this.height = 0.0F;
        int var9 = 1;

        if (par8ItemStack != null && par8ItemStack.hasTag())
        {
            this.datawatcher.watch(8, par8ItemStack);
            NBTTagCompound var10 = par8ItemStack.getTag();
            NBTTagCompound var11 = var10.getCompound("Fireworks");

            if (var11 != null)
            {
                var9 += var11.getByte("Flight");
            }
        }

        this.motX = this.random.nextGaussian() * 0.001D;
        this.motZ = this.random.nextGaussian() * 0.001D;
        this.motY = 0.05D;
        this.b = 10 * var9 + this.random.nextInt(6) + this.random.nextInt(7);
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
        this.motX *= 1.15D;
        this.motZ *= 1.15D;
        this.motY += 0.04D;
        this.move(this.motX, this.motY, this.motZ);
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

        if (this.a == 0)
        {
            this.world.makeSound(this, "fireworks.launch", 3.0F, 1.0F);
        }

        ++this.a;

        if (this.world.isStatic && this.a % 2 < 2)
        {
            this.world.addParticle("fireworksSpark", this.locX, this.locY - 0.3D, this.locZ, this.random.nextGaussian() * 0.05D, -this.motY * 0.5D, this.random.nextGaussian() * 0.05D);
        }

        if (!this.world.isStatic && this.a > this.b)
        {
            this.world.broadcastEntityEffect(this, (byte) 17);
            this.die();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInt("Life", this.a);
        par1NBTTagCompound.setInt("LifeTime", this.b);
        ItemStack var2 = this.datawatcher.f(8);

        if (var2 != null)
        {
            NBTTagCompound var3 = new NBTTagCompound();
            var2.save(var3);
            par1NBTTagCompound.setCompound("FireworksItem", var3);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        this.a = par1NBTTagCompound.getInt("Life");
        this.b = par1NBTTagCompound.getInt("LifeTime");
        NBTTagCompound var2 = par1NBTTagCompound.getCompound("FireworksItem");

        if (var2 != null)
        {
            ItemStack var3 = ItemStack.a(var2);

            if (var3 != null)
            {
                this.datawatcher.watch(8, var3);
            }
        }
    }

    /**
     * Gets how bright this entity is.
     */
    public float c(float par1)
    {
        return super.c(par1);
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean aq()
    {
        return false;
    }
}
