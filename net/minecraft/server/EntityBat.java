package net.minecraft.server;

import java.util.Calendar;

public class EntityBat extends EntityAmbient
{
    /** Coordinates of where the bat spawned. */
    private ChunkCoordinates a;

    public EntityBat(World par1World)
    {
        super(par1World);
        this.texture = "/mob/bat.png";
        this.a(0.5F, 0.9F);
        this.f(true);
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, new Byte((byte) 0));
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float aX()
    {
        return 0.1F;
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float aV()
    {
        return super.aV() * 0.95F;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String aY()
    {
        return this.h() && this.random.nextInt(4) != 0 ? null : "mob.bat.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String aZ()
    {
        return "mob.bat.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String ba()
    {
        return "mob.bat.death";
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean M()
    {
        return false;
    }

    protected void o(Entity par1Entity) {}

    protected void bd() {}

    public int getMaxHealth()
    {
        return 6;
    }

    public boolean h()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void f(boolean par1)
    {
        byte var2 = this.datawatcher.getByte(16);

        if (par1)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var2 | 1)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var2 & -2)));
        }
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean be()
    {
        return true;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();

        if (this.h())
        {
            this.motX = this.motY = this.motZ = 0.0D;
            this.locY = (double) MathHelper.floor(this.locY) + 1.0D - (double)this.length;
        }
        else
        {
            this.motY *= 0.6000000238418579D;
        }
    }

    protected void bl()
    {
        super.bl();

        if (this.h())
        {
            if (!this.world.t(MathHelper.floor(this.locX), (int) this.locY + 1, MathHelper.floor(this.locZ)))
            {
                this.f(false);
                this.world.a((EntityHuman) null, 1015, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
            }
            else
            {
                if (this.random.nextInt(200) == 0)
                {
                    this.az = (float)this.random.nextInt(360);
                }

                if (this.world.findNearbyPlayer(this, 4.0D) != null)
                {
                    this.f(false);
                    this.world.a((EntityHuman) null, 1015, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
                }
            }
        }
        else
        {
            if (this.a != null && (!this.world.isEmpty(this.a.x, this.a.y, this.a.z) || this.a.y < 1))
            {
                this.a = null;
            }

            if (this.a == null || this.random.nextInt(30) == 0 || this.a.e((int) this.locX, (int) this.locY, (int) this.locZ) < 4.0F)
            {
                this.a = new ChunkCoordinates((int)this.locX + this.random.nextInt(7) - this.random.nextInt(7), (int)this.locY + this.random.nextInt(6) - 2, (int)this.locZ + this.random.nextInt(7) - this.random.nextInt(7));
            }

            double var1 = (double)this.a.x + 0.5D - this.locX;
            double var3 = (double)this.a.y + 0.1D - this.locY;
            double var5 = (double)this.a.z + 0.5D - this.locZ;
            this.motX += (Math.signum(var1) * 0.5D - this.motX) * 0.10000000149011612D;
            this.motY += (Math.signum(var3) * 0.699999988079071D - this.motY) * 0.10000000149011612D;
            this.motZ += (Math.signum(var5) * 0.5D - this.motZ) * 0.10000000149011612D;
            float var7 = (float)(Math.atan2(this.motZ, this.motX) * 180.0D / Math.PI) - 90.0F;
            float var8 = MathHelper.g(var7 - this.yaw);
            this.bD = 0.5F;
            this.yaw += var8;

            if (this.random.nextInt(100) == 0 && this.world.t(MathHelper.floor(this.locX), (int) this.locY + 1, MathHelper.floor(this.locZ)))
            {
                this.f(true);
            }
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean f_()
    {
        return false;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void a(float par1) {}

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void a(double par1, boolean par3) {}

    /**
     * Return whether this entity should NOT trigger a pressure plate or a tripwire.
     */
    public boolean au()
    {
        return true;
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
            if (!this.world.isStatic && this.h())
            {
                this.f(false);
            }

            return super.damageEntity(par1DamageSource, par2);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        this.datawatcher.watch(16, Byte.valueOf(par1NBTTagCompound.getByte("BatFlags")));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setByte("BatFlags", this.datawatcher.getByte(16));
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean canSpawn()
    {
        int var1 = MathHelper.floor(this.boundingBox.b);

        if (var1 >= 63)
        {
            return false;
        }
        else
        {
            int var2 = MathHelper.floor(this.locX);
            int var3 = MathHelper.floor(this.locZ);
            int var4 = this.world.getLightLevel(var2, var1, var3);
            byte var5 = 4;
            Calendar var6 = this.world.T();

            if ((var6.get(2) + 1 != 10 || var6.get(5) < 20) && (var6.get(2) + 1 != 11 || var6.get(5) > 3))
            {
                if (this.random.nextBoolean())
                {
                    return false;
                }
            }
            else
            {
                var5 = 7;
            }

            return var4 > this.random.nextInt(var5) ? false : super.canSpawn();
        }
    }

    /**
     * Initialize this creature.
     */
    public void bG() {}
}
