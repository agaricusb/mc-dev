package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public abstract class EntityHanging extends Entity
{
    private int e;
    public int direction;
    public int x;
    public int y;
    public int z;

    public EntityHanging(World par1World)
    {
        super(par1World);
        this.e = 0;
        this.direction = 0;
        this.height = 0.0F;
        this.a(0.5F, 0.5F);
    }

    public EntityHanging(World par1World, int par2, int par3, int par4, int par5)
    {
        this(par1World);
        this.x = par2;
        this.y = par3;
        this.z = par4;
    }

    protected void a() {}

    public void setDirection(int par1)
    {
        this.direction = par1;
        this.lastYaw = this.yaw = (float)(par1 * 90);
        float var2 = (float)this.d();
        float var3 = (float)this.g();
        float var4 = (float)this.d();

        if (par1 != 2 && par1 != 0)
        {
            var2 = 0.5F;
        }
        else
        {
            var4 = 0.5F;
            this.yaw = this.lastYaw = (float)(Direction.f[par1] * 90);
        }

        var2 /= 32.0F;
        var3 /= 32.0F;
        var4 /= 32.0F;
        float var5 = (float)this.x + 0.5F;
        float var6 = (float)this.y + 0.5F;
        float var7 = (float)this.z + 0.5F;
        float var8 = 0.5625F;

        if (par1 == 2)
        {
            var7 -= var8;
        }

        if (par1 == 1)
        {
            var5 -= var8;
        }

        if (par1 == 0)
        {
            var7 += var8;
        }

        if (par1 == 3)
        {
            var5 += var8;
        }

        if (par1 == 2)
        {
            var5 -= this.g(this.d());
        }

        if (par1 == 1)
        {
            var7 += this.g(this.d());
        }

        if (par1 == 0)
        {
            var5 += this.g(this.d());
        }

        if (par1 == 3)
        {
            var7 -= this.g(this.d());
        }

        var6 += this.g(this.g());
        this.setPosition((double) var5, (double) var6, (double) var7);
        float var9 = -0.03125F;
        this.boundingBox.b((double) (var5 - var2 - var9), (double) (var6 - var3 - var9), (double) (var7 - var4 - var9), (double) (var5 + var2 + var9), (double) (var6 + var3 + var9), (double) (var7 + var4 + var9));
    }

    private float g(int par1)
    {
        return par1 == 32 ? 0.5F : (par1 == 64 ? 0.5F : 0.0F);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        if (this.e++ == 100 && !this.world.isStatic)
        {
            this.e = 0;

            if (!this.dead && !this.survives())
            {
                this.die();
                this.h();
            }
        }
    }

    /**
     * checks to make sure painting can be placed there
     */
    public boolean survives()
    {
        if (!this.world.getCubes(this, this.boundingBox).isEmpty())
        {
            return false;
        }
        else
        {
            int var1 = Math.max(1, this.d() / 16);
            int var2 = Math.max(1, this.g() / 16);
            int var3 = this.x;
            int var4 = this.y;
            int var5 = this.z;

            if (this.direction == 2)
            {
                var3 = MathHelper.floor(this.locX - (double) ((float) this.d() / 32.0F));
            }

            if (this.direction == 1)
            {
                var5 = MathHelper.floor(this.locZ - (double) ((float) this.d() / 32.0F));
            }

            if (this.direction == 0)
            {
                var3 = MathHelper.floor(this.locX - (double) ((float) this.d() / 32.0F));
            }

            if (this.direction == 3)
            {
                var5 = MathHelper.floor(this.locZ - (double) ((float) this.d() / 32.0F));
            }

            var4 = MathHelper.floor(this.locY - (double) ((float) this.g() / 32.0F));

            for (int var6 = 0; var6 < var1; ++var6)
            {
                for (int var7 = 0; var7 < var2; ++var7)
                {
                    Material var8;

                    if (this.direction != 2 && this.direction != 0)
                    {
                        var8 = this.world.getMaterial(this.x, var4 + var7, var5 + var6);
                    }
                    else
                    {
                        var8 = this.world.getMaterial(var3 + var6, var4 + var7, this.z);
                    }

                    if (!var8.isBuildable())
                    {
                        return false;
                    }
                }
            }

            List var9 = this.world.getEntities(this, this.boundingBox);
            Iterator var10 = var9.iterator();
            Entity var11;

            do
            {
                if (!var10.hasNext())
                {
                    return true;
                }

                var11 = (Entity)var10.next();
            }
            while (!(var11 instanceof EntityHanging));

            return false;
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean L()
    {
        return true;
    }

    public boolean j(Entity par1Entity)
    {
        return par1Entity instanceof EntityHuman ? this.damageEntity(DamageSource.playerAttack((EntityHuman) par1Entity), 0) : false;
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
            if (!this.dead && !this.world.isStatic)
            {
                this.die();
                this.K();
                EntityHuman var3 = null;

                if (par1DamageSource.getEntity() instanceof EntityHuman)
                {
                    var3 = (EntityHuman)par1DamageSource.getEntity();
                }

                if (var3 != null && var3.abilities.canInstantlyBuild)
                {
                    return true;
                }

                this.h();
            }

            return true;
        }
    }

    /**
     * Tries to moves the entity by the passed in displacement. Args: x, y, z
     */
    public void move(double par1, double par3, double par5)
    {
        if (!this.world.isStatic && !this.dead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.die();
            this.h();
        }
    }

    /**
     * Adds to the current velocity of the entity. Args: x, y, z
     */
    public void g(double par1, double par3, double par5)
    {
        if (!this.world.isStatic && !this.dead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.die();
            this.h();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Direction", (byte) this.direction);
        par1NBTTagCompound.setInt("TileX", this.x);
        par1NBTTagCompound.setInt("TileY", this.y);
        par1NBTTagCompound.setInt("TileZ", this.z);

        switch (this.direction)
        {
            case 0:
                par1NBTTagCompound.setByte("Dir", (byte) 2);
                break;

            case 1:
                par1NBTTagCompound.setByte("Dir", (byte) 1);
                break;

            case 2:
                par1NBTTagCompound.setByte("Dir", (byte) 0);
                break;

            case 3:
                par1NBTTagCompound.setByte("Dir", (byte) 3);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.hasKey("Direction"))
        {
            this.direction = par1NBTTagCompound.getByte("Direction");
        }
        else
        {
            switch (par1NBTTagCompound.getByte("Dir"))
            {
                case 0:
                    this.direction = 2;
                    break;

                case 1:
                    this.direction = 1;
                    break;

                case 2:
                    this.direction = 0;
                    break;

                case 3:
                    this.direction = 3;
            }
        }

        this.x = par1NBTTagCompound.getInt("TileX");
        this.y = par1NBTTagCompound.getInt("TileY");
        this.z = par1NBTTagCompound.getInt("TileZ");
        this.setDirection(this.direction);
    }

    public abstract int d();

    public abstract int g();

    /**
     * Drop the item currently on this item frame.
     */
    public abstract void h();
}
