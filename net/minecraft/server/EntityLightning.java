package net.minecraft.server;

import java.util.List;

public class EntityLightning extends EntityWeather
{
    /**
     * Declares which state the lightning bolt is in. Whether it's in the air, hit the ground, etc.
     */
    private int lifeTicks;

    /**
     * A random long that is used to change the vertex of the lightning rendered in RenderLightningBolt
     */
    public long a = 0L;

    /**
     * Determines the time before the EntityLightningBolt is destroyed. It is a random integer decremented over time.
     */
    private int c;

    public EntityLightning(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        this.setPositionRotation(par2, par4, par6, 0.0F, 0.0F);
        this.lifeTicks = 2;
        this.a = this.random.nextLong();
        this.c = this.random.nextInt(3) + 1;

        if (!par1World.isStatic && par1World.difficulty >= 2 && par1World.areChunksLoaded(MathHelper.floor(par2), MathHelper.floor(par4), MathHelper.floor(par6), 10))
        {
            int var8 = MathHelper.floor(par2);
            int var9 = MathHelper.floor(par4);
            int var10 = MathHelper.floor(par6);

            if (par1World.getTypeId(var8, var9, var10) == 0 && Block.FIRE.canPlace(par1World, var8, var9, var10))
            {
                par1World.setTypeId(var8, var9, var10, Block.FIRE.id);
            }

            for (var8 = 0; var8 < 4; ++var8)
            {
                var9 = MathHelper.floor(par2) + this.random.nextInt(3) - 1;
                var10 = MathHelper.floor(par4) + this.random.nextInt(3) - 1;
                int var11 = MathHelper.floor(par6) + this.random.nextInt(3) - 1;

                if (par1World.getTypeId(var9, var10, var11) == 0 && Block.FIRE.canPlace(par1World, var9, var10, var11))
                {
                    par1World.setTypeId(var9, var10, var11, Block.FIRE.id);
                }
            }
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void j_()
    {
        super.j_();

        if (this.lifeTicks == 2)
        {
            this.world.makeSound(this.locX, this.locY, this.locZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.random.nextFloat() * 0.2F);
            this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 2.0F, 0.5F + this.random.nextFloat() * 0.2F);
        }

        --this.lifeTicks;

        if (this.lifeTicks < 0)
        {
            if (this.c == 0)
            {
                this.die();
            }
            else if (this.lifeTicks < -this.random.nextInt(10))
            {
                --this.c;
                this.lifeTicks = 1;
                this.a = this.random.nextLong();

                if (!this.world.isStatic && this.world.areChunksLoaded(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 10))
                {
                    int var1 = MathHelper.floor(this.locX);
                    int var2 = MathHelper.floor(this.locY);
                    int var3 = MathHelper.floor(this.locZ);

                    if (this.world.getTypeId(var1, var2, var3) == 0 && Block.FIRE.canPlace(this.world, var1, var2, var3))
                    {
                        this.world.setTypeId(var1, var2, var3, Block.FIRE.id);
                    }
                }
            }
        }

        if (this.lifeTicks >= 0)
        {
            if (this.world.isStatic)
            {
                this.world.q = 2;
            }
            else
            {
                double var6 = 3.0D;
                List var7 = this.world.getEntities(this, AxisAlignedBB.a().a(this.locX - var6, this.locY - var6, this.locZ - var6, this.locX + var6, this.locY + 6.0D + var6, this.locZ + var6));

                for (int var4 = 0; var4 < var7.size(); ++var4)
                {
                    Entity var5 = (Entity)var7.get(var4);
                    var5.a(this);
                }
            }
        }
    }

    protected void a() {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void a(NBTTagCompound par1NBTTagCompound) {}

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void b(NBTTagCompound par1NBTTagCompound) {}
}
