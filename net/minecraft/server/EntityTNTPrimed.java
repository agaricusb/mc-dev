package net.minecraft.server;

public class EntityTNTPrimed extends Entity
{
    /** How long the fuse is */
    public int fuseTicks;

    public EntityTNTPrimed(World par1World)
    {
        super(par1World);
        this.fuseTicks = 0;
        this.m = true;
        this.a(0.98F, 0.98F);
        this.height = this.length / 2.0F;
    }

    public EntityTNTPrimed(World par1World, double par2, double par4, double par6)
    {
        this(par1World);
        this.setPosition(par2, par4, par6);
        float var8 = (float)(Math.random() * Math.PI * 2.0D);
        this.motX = (double)(-((float)Math.sin((double)var8)) * 0.02F);
        this.motY = 0.20000000298023224D;
        this.motZ = (double)(-((float)Math.cos((double)var8)) * 0.02F);
        this.fuseTicks = 80;
        this.lastX = par2;
        this.lastY = par4;
        this.lastZ = par6;
    }

    protected void a() {}

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return !this.dead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.03999999910593033D;
        this.move(this.motX, this.motY, this.motZ);
        this.motX *= 0.9800000190734863D;
        this.motY *= 0.9800000190734863D;
        this.motZ *= 0.9800000190734863D;

        if (this.onGround)
        {
            this.motX *= 0.699999988079071D;
            this.motZ *= 0.699999988079071D;
            this.motY *= -0.5D;
        }

        if (this.fuseTicks-- <= 0)
        {
            this.die();

            if (!this.world.isStatic)
            {
                this.explode();
            }
        }
        else
        {
            this.world.addParticle("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
        }
    }

    private void explode()
    {
        float var1 = 4.0F;
        this.world.explode((Entity) null, this.locX, this.locY, this.locZ, var1, true);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Fuse", (byte) this.fuseTicks);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void a(NBTTagCompound par1NBTTagCompound)
    {
        this.fuseTicks = par1NBTTagCompound.getByte("Fuse");
    }
}
