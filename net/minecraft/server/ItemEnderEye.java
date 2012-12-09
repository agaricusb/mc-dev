package net.minecraft.server;

public class ItemEnderEye extends Item
{
    public ItemEnderEye(int par1)
    {
        super(par1);
        this.a(CreativeModeTab.f);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int var11 = par3World.getTypeId(par4, par5, par6);
        int var12 = par3World.getData(par4, par5, par6);

        if (par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack) && var11 == Block.ENDER_PORTAL_FRAME.id && !BlockEnderPortalFrame.e(var12))
        {
            if (par3World.isStatic)
            {
                return true;
            }
            else
            {
                par3World.setData(par4, par5, par6, var12 + 4);
                --par1ItemStack.count;
                int var13;

                for (var13 = 0; var13 < 16; ++var13)
                {
                    double var14 = (double)((float)par4 + (5.0F + d.nextFloat() * 6.0F) / 16.0F);
                    double var16 = (double)((float)par5 + 0.8125F);
                    double var18 = (double)((float)par6 + (5.0F + d.nextFloat() * 6.0F) / 16.0F);
                    double var20 = 0.0D;
                    double var22 = 0.0D;
                    double var24 = 0.0D;
                    par3World.addParticle("smoke", var14, var16, var18, var20, var22, var24);
                }

                var13 = var12 & 3;
                int var26 = 0;
                int var15 = 0;
                boolean var27 = false;
                boolean var17 = true;
                int var28 = Direction.g[var13];
                int var19;
                int var21;
                int var23;
                int var29;
                int var30;

                for (var19 = -2; var19 <= 2; ++var19)
                {
                    var29 = par4 + Direction.a[var28] * var19;
                    var21 = par6 + Direction.b[var28] * var19;
                    var30 = par3World.getTypeId(var29, par5, var21);

                    if (var30 == Block.ENDER_PORTAL_FRAME.id)
                    {
                        var23 = par3World.getData(var29, par5, var21);

                        if (!BlockEnderPortalFrame.e(var23))
                        {
                            var17 = false;
                            break;
                        }

                        var15 = var19;

                        if (!var27)
                        {
                            var26 = var19;
                            var27 = true;
                        }
                    }
                }

                if (var17 && var15 == var26 + 2)
                {
                    for (var19 = var26; var19 <= var15; ++var19)
                    {
                        var29 = par4 + Direction.a[var28] * var19;
                        var21 = par6 + Direction.b[var28] * var19;
                        var29 += Direction.a[var13] * 4;
                        var21 += Direction.b[var13] * 4;
                        var30 = par3World.getTypeId(var29, par5, var21);
                        var23 = par3World.getData(var29, par5, var21);

                        if (var30 != Block.ENDER_PORTAL_FRAME.id || !BlockEnderPortalFrame.e(var23))
                        {
                            var17 = false;
                            break;
                        }
                    }

                    for (var19 = var26 - 1; var19 <= var15 + 1; var19 += 4)
                    {
                        for (var29 = 1; var29 <= 3; ++var29)
                        {
                            var21 = par4 + Direction.a[var28] * var19;
                            var30 = par6 + Direction.b[var28] * var19;
                            var21 += Direction.a[var13] * var29;
                            var30 += Direction.b[var13] * var29;
                            var23 = par3World.getTypeId(var21, par5, var30);
                            int var31 = par3World.getData(var21, par5, var30);

                            if (var23 != Block.ENDER_PORTAL_FRAME.id || !BlockEnderPortalFrame.e(var31))
                            {
                                var17 = false;
                                break;
                            }
                        }
                    }

                    if (var17)
                    {
                        for (var19 = var26; var19 <= var15; ++var19)
                        {
                            for (var29 = 1; var29 <= 3; ++var29)
                            {
                                var21 = par4 + Direction.a[var28] * var19;
                                var30 = par6 + Direction.b[var28] * var19;
                                var21 += Direction.a[var13] * var29;
                                var30 += Direction.b[var13] * var29;
                                par3World.setTypeId(var21, par5, var30, Block.ENDER_PORTAL.id);
                            }
                        }
                    }
                }

                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        MovingObjectPosition var4 = this.a(par2World, par3EntityPlayer, false);

        if (var4 != null && var4.type == EnumMovingObjectType.TILE)
        {
            int var5 = par2World.getTypeId(var4.b, var4.c, var4.d);

            if (var5 == Block.ENDER_PORTAL_FRAME.id)
            {
                return par1ItemStack;
            }
        }

        if (!par2World.isStatic)
        {
            ChunkPosition var7 = par2World.b("Stronghold", (int) par3EntityPlayer.locX, (int) par3EntityPlayer.locY, (int) par3EntityPlayer.locZ);

            if (var7 != null)
            {
                EntityEnderSignal var6 = new EntityEnderSignal(par2World, par3EntityPlayer.locX, par3EntityPlayer.locY + 1.62D - (double)par3EntityPlayer.height, par3EntityPlayer.locZ);
                var6.a((double) var7.x, var7.y, (double) var7.z);
                par2World.addEntity(var6);
                par2World.makeSound(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (d.nextFloat() * 0.4F + 0.8F));
                par2World.a((EntityHuman) null, 1002, (int) par3EntityPlayer.locX, (int) par3EntityPlayer.locY, (int) par3EntityPlayer.locZ, 0);

                if (!par3EntityPlayer.abilities.canInstantlyBuild)
                {
                    --par1ItemStack.count;
                }
            }
        }

        return par1ItemStack;
    }
}
