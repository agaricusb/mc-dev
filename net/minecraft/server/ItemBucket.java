package net.minecraft.server;

public class ItemBucket extends Item
{
    /** field for checking if the bucket has been filled. */
    private int a;

    public ItemBucket(int par1, int par2)
    {
        super(par1);
        this.maxStackSize = 1;
        this.a = par2;
        this.a(CreativeModeTab.f);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        float var4 = 1.0F;
        double var5 = par3EntityPlayer.lastX + (par3EntityPlayer.locX - par3EntityPlayer.lastX) * (double)var4;
        double var7 = par3EntityPlayer.lastY + (par3EntityPlayer.locY - par3EntityPlayer.lastY) * (double)var4 + 1.62D - (double)par3EntityPlayer.height;
        double var9 = par3EntityPlayer.lastZ + (par3EntityPlayer.locZ - par3EntityPlayer.lastZ) * (double)var4;
        boolean var11 = this.a == 0;
        MovingObjectPosition var12 = this.a(par2World, par3EntityPlayer, var11);

        if (var12 == null)
        {
            return par1ItemStack;
        }
        else
        {
            if (var12.type == EnumMovingObjectType.TILE)
            {
                int var13 = var12.b;
                int var14 = var12.c;
                int var15 = var12.d;

                if (!par2World.a(par3EntityPlayer, var13, var14, var15))
                {
                    return par1ItemStack;
                }

                if (this.a == 0)
                {
                    if (!par3EntityPlayer.a(var13, var14, var15, var12.face, par1ItemStack))
                    {
                        return par1ItemStack;
                    }

                    if (par2World.getMaterial(var13, var14, var15) == Material.WATER && par2World.getData(var13, var14, var15) == 0)
                    {
                        par2World.setTypeId(var13, var14, var15, 0);

                        if (par3EntityPlayer.abilities.canInstantlyBuild)
                        {
                            return par1ItemStack;
                        }

                        if (--par1ItemStack.count <= 0)
                        {
                            return new ItemStack(Item.WATER_BUCKET);
                        }

                        if (!par3EntityPlayer.inventory.pickup(new ItemStack(Item.WATER_BUCKET)))
                        {
                            par3EntityPlayer.drop(new ItemStack(Item.WATER_BUCKET.id, 1, 0));
                        }

                        return par1ItemStack;
                    }

                    if (par2World.getMaterial(var13, var14, var15) == Material.LAVA && par2World.getData(var13, var14, var15) == 0)
                    {
                        par2World.setTypeId(var13, var14, var15, 0);

                        if (par3EntityPlayer.abilities.canInstantlyBuild)
                        {
                            return par1ItemStack;
                        }

                        if (--par1ItemStack.count <= 0)
                        {
                            return new ItemStack(Item.LAVA_BUCKET);
                        }

                        if (!par3EntityPlayer.inventory.pickup(new ItemStack(Item.LAVA_BUCKET)))
                        {
                            par3EntityPlayer.drop(new ItemStack(Item.LAVA_BUCKET.id, 1, 0));
                        }

                        return par1ItemStack;
                    }
                }
                else
                {
                    if (this.a < 0)
                    {
                        return new ItemStack(Item.BUCKET);
                    }

                    if (var12.face == 0)
                    {
                        --var14;
                    }

                    if (var12.face == 1)
                    {
                        ++var14;
                    }

                    if (var12.face == 2)
                    {
                        --var15;
                    }

                    if (var12.face == 3)
                    {
                        ++var15;
                    }

                    if (var12.face == 4)
                    {
                        --var13;
                    }

                    if (var12.face == 5)
                    {
                        ++var13;
                    }

                    if (!par3EntityPlayer.a(var13, var14, var15, var12.face, par1ItemStack))
                    {
                        return par1ItemStack;
                    }

                    if (this.a(par2World, var5, var7, var9, var13, var14, var15) && !par3EntityPlayer.abilities.canInstantlyBuild)
                    {
                        return new ItemStack(Item.BUCKET);
                    }
                }
            }
            else if (this.a == 0 && var12.entity instanceof EntityCow)
            {
                return new ItemStack(Item.MILK_BUCKET);
            }

            return par1ItemStack;
        }
    }

    /**
     * Attempts to place the liquid contained inside the bucket.
     */
    public boolean a(World par1World, double par2, double par4, double par6, int par8, int par9, int par10)
    {
        if (this.a <= 0)
        {
            return false;
        }
        else if (!par1World.isEmpty(par8, par9, par10) && par1World.getMaterial(par8, par9, par10).isBuildable())
        {
            return false;
        }
        else
        {
            if (par1World.worldProvider.e && this.a == Block.WATER.id)
            {
                par1World.makeSound(par2 + 0.5D, par4 + 0.5D, par6 + 0.5D, "random.fizz", 0.5F, 2.6F + (par1World.random.nextFloat() - par1World.random.nextFloat()) * 0.8F);

                for (int var11 = 0; var11 < 8; ++var11)
                {
                    par1World.addParticle("largesmoke", (double) par8 + Math.random(), (double) par9 + Math.random(), (double) par10 + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            }
            else
            {
                par1World.setTypeIdAndData(par8, par9, par10, this.a, 0);
            }

            return true;
        }
    }
}
