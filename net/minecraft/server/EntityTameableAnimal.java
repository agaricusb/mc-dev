package net.minecraft.server;

public abstract class EntityTameableAnimal extends EntityAnimal
{
    protected PathfinderGoalSit d = new PathfinderGoalSit(this);

    public EntityTameableAnimal(World par1World)
    {
        super(par1World);
    }

    protected void a()
    {
        super.a();
        this.datawatcher.a(16, Byte.valueOf((byte) 0));
        this.datawatcher.a(17, "");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);

        if (this.getOwnerName() == null)
        {
            par1NBTTagCompound.setString("Owner", "");
        }
        else
        {
            par1NBTTagCompound.setString("Owner", this.getOwnerName());
        }

        par1NBTTagCompound.setBoolean("Sitting", this.isSitting());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        String var2 = par1NBTTagCompound.getString("Owner");

        if (var2.length() > 0)
        {
            this.setOwnerName(var2);
            this.setTamed(true);
        }

        this.d.a(par1NBTTagCompound.getBoolean("Sitting"));
        this.setSitting(par1NBTTagCompound.getBoolean("Sitting"));
    }

    /**
     * Play the taming effect, will either be hearts or smoke depending on status
     */
    protected void f(boolean par1)
    {
        String var2 = "heart";

        if (!par1)
        {
            var2 = "smoke";
        }

        for (int var3 = 0; var3 < 7; ++var3)
        {
            double var4 = this.random.nextGaussian() * 0.02D;
            double var6 = this.random.nextGaussian() * 0.02D;
            double var8 = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(var2, this.locX + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, this.locY + 0.5D + (double) (this.random.nextFloat() * this.length), this.locZ + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, var4, var6, var8);
        }
    }

    public boolean isTamed()
    {
        return (this.datawatcher.getByte(16) & 4) != 0;
    }

    public void setTamed(boolean par1)
    {
        byte var2 = this.datawatcher.getByte(16);

        if (par1)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var2 | 4)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte) (var2 & -5)));
        }
    }

    public boolean isSitting()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void setSitting(boolean par1)
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

    public String getOwnerName()
    {
        return this.datawatcher.getString(17);
    }

    public void setOwnerName(String par1Str)
    {
        this.datawatcher.watch(17, par1Str);
    }

    public EntityLiving getOwner()
    {
        return this.world.a(this.getOwnerName());
    }

    public PathfinderGoalSit q()
    {
        return this.d;
    }
}
