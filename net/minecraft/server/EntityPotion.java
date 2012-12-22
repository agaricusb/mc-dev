package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class EntityPotion extends EntityProjectile
{
    /**
     * The damage value of the thrown potion that this EntityPotion represents.
     */
    private ItemStack c;

    public EntityPotion(World par1World)
    {
        super(par1World);
    }

    public EntityPotion(World par1World, EntityLiving par2EntityLiving, int par3)
    {
        this(par1World, par2EntityLiving, new ItemStack(Item.POTION, 1, par3));
    }

    public EntityPotion(World par1World, EntityLiving par2EntityLiving, ItemStack par3ItemStack)
    {
        super(par1World, par2EntityLiving);
        this.c = par3ItemStack;
    }

    public EntityPotion(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
    {
        super(par1World, par2, par4, par6);
        this.c = par8ItemStack;
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float g()
    {
        return 0.05F;
    }

    protected float c()
    {
        return 0.5F;
    }

    protected float d()
    {
        return -20.0F;
    }

    public void setPotionValue(int par1)
    {
        if (this.c == null)
        {
            this.c = new ItemStack(Item.POTION, 1, 0);
        }

        this.c.setData(par1);
    }

    /**
     * Returns the damage value of the thrown potion that this EntityPotion represents.
     */
    public int getPotionValue()
    {
        if (this.c == null)
        {
            this.c = new ItemStack(Item.POTION, 1, 0);
        }

        return this.c.getData();
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void a(MovingObjectPosition par1MovingObjectPosition)
    {
        if (!this.world.isStatic)
        {
            List var2 = Item.POTION.g(this.c);

            if (var2 != null && !var2.isEmpty())
            {
                AxisAlignedBB var3 = this.boundingBox.grow(4.0D, 2.0D, 4.0D);
                List var4 = this.world.a(EntityLiving.class, var3);

                if (var4 != null && !var4.isEmpty())
                {
                    Iterator var5 = var4.iterator();

                    while (var5.hasNext())
                    {
                        EntityLiving var6 = (EntityLiving)var5.next();
                        double var7 = this.e(var6);

                        if (var7 < 16.0D)
                        {
                            double var9 = 1.0D - Math.sqrt(var7) / 4.0D;

                            if (var6 == par1MovingObjectPosition.entity)
                            {
                                var9 = 1.0D;
                            }

                            Iterator var11 = var2.iterator();

                            while (var11.hasNext())
                            {
                                MobEffect var12 = (MobEffect)var11.next();
                                int var13 = var12.getEffectId();

                                if (MobEffectList.byId[var13].isInstant())
                                {
                                    MobEffectList.byId[var13].applyInstantEffect(this.getShooter(), var6, var12.getAmplifier(), var9);
                                }
                                else
                                {
                                    int var14 = (int)(var9 * (double)var12.getDuration() + 0.5D);

                                    if (var14 > 20)
                                    {
                                        var6.addEffect(new MobEffect(var13, var14, var12.getAmplifier()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.world.triggerEffect(2002, (int) Math.round(this.locX), (int) Math.round(this.locY), (int) Math.round(this.locZ), this.getPotionValue());
            this.die();
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("Potion"))
        {
            this.c = ItemStack.a(par1NBTTagCompound.getCompound("Potion"));
        }
        else
        {
            this.setPotionValue(par1NBTTagCompound.getInt("potionValue"));
        }

        if (this.c == null)
        {
            this.die();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);

        if (this.c != null)
        {
            par1NBTTagCompound.setCompound("Potion", this.c.save(new NBTTagCompound()));
        }
    }
}
