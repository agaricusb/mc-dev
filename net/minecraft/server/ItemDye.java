package net.minecraft.server;

public class ItemDye extends Item
{
    /** List of dye color names */
    public static final String[] a = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
    public static final int[] b = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 2651799, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

    public ItemDye(int par1)
    {
        super(par1);
        this.a(true);
        this.setMaxDurability(0);
        this.a(CreativeModeTab.l);
    }

    public String c_(ItemStack par1ItemStack)
    {
        int var2 = MathHelper.a(par1ItemStack.getData(), 0, 15);
        return super.getName() + "." + a[var2];
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (!par2EntityPlayer.a(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            int var11;
            int var12;
            int var13;

            if (par1ItemStack.getData() == 15)
            {
                var11 = par3World.getTypeId(par4, par5, par6);

                if (var11 == Block.SAPLING.id)
                {
                    if (!par3World.isStatic)
                    {
                        ((BlockSapling) Block.SAPLING).grow(par3World, par4, par5, par6, par3World.random);
                        --par1ItemStack.count;
                    }

                    return true;
                }

                if (var11 == Block.BROWN_MUSHROOM.id || var11 == Block.RED_MUSHROOM.id)
                {
                    if (!par3World.isStatic && ((BlockMushroom) Block.byId[var11]).grow(par3World, par4, par5, par6, par3World.random))
                    {
                        --par1ItemStack.count;
                    }

                    return true;
                }

                if (var11 == Block.MELON_STEM.id || var11 == Block.PUMPKIN_STEM.id)
                {
                    if (par3World.getData(par4, par5, par6) == 7)
                    {
                        return false;
                    }

                    if (!par3World.isStatic)
                    {
                        ((BlockStem) Block.byId[var11]).l(par3World, par4, par5, par6);
                        --par1ItemStack.count;
                    }

                    return true;
                }

                if (var11 > 0 && Block.byId[var11] instanceof BlockCrops)
                {
                    if (par3World.getData(par4, par5, par6) == 7)
                    {
                        return false;
                    }

                    if (!par3World.isStatic)
                    {
                        ((BlockCrops) Block.byId[var11]).c_(par3World, par4, par5, par6);
                        --par1ItemStack.count;
                    }

                    return true;
                }

                if (var11 == Block.COCOA.id)
                {
                    if (!par3World.isStatic)
                    {
                        par3World.setData(par4, par5, par6, 8 | BlockDirectional.e(par3World.getData(par4, par5, par6)));
                        --par1ItemStack.count;
                    }

                    return true;
                }

                if (var11 == Block.GRASS.id)
                {
                    if (!par3World.isStatic)
                    {
                        --par1ItemStack.count;
                        label133:

                        for (var12 = 0; var12 < 128; ++var12)
                        {
                            var13 = par4;
                            int var14 = par5 + 1;
                            int var15 = par6;

                            for (int var16 = 0; var16 < var12 / 16; ++var16)
                            {
                                var13 += d.nextInt(3) - 1;
                                var14 += (d.nextInt(3) - 1) * d.nextInt(3) / 2;
                                var15 += d.nextInt(3) - 1;

                                if (par3World.getTypeId(var13, var14 - 1, var15) != Block.GRASS.id || par3World.t(var13, var14, var15))
                                {
                                    continue label133;
                                }
                            }

                            if (par3World.getTypeId(var13, var14, var15) == 0)
                            {
                                if (d.nextInt(10) != 0)
                                {
                                    if (Block.LONG_GRASS.d(par3World, var13, var14, var15))
                                    {
                                        par3World.setTypeIdAndData(var13, var14, var15, Block.LONG_GRASS.id, 1);
                                    }
                                }
                                else if (d.nextInt(3) != 0)
                                {
                                    if (Block.YELLOW_FLOWER.d(par3World, var13, var14, var15))
                                    {
                                        par3World.setTypeId(var13, var14, var15, Block.YELLOW_FLOWER.id);
                                    }
                                }
                                else if (Block.RED_ROSE.d(par3World, var13, var14, var15))
                                {
                                    par3World.setTypeId(var13, var14, var15, Block.RED_ROSE.id);
                                }
                            }
                        }
                    }

                    return true;
                }
            }
            else if (par1ItemStack.getData() == 3)
            {
                var11 = par3World.getTypeId(par4, par5, par6);
                var12 = par3World.getData(par4, par5, par6);

                if (var11 == Block.LOG.id && BlockLog.e(var12) == 3)
                {
                    if (par7 == 0)
                    {
                        return false;
                    }

                    if (par7 == 1)
                    {
                        return false;
                    }

                    if (par7 == 2)
                    {
                        --par6;
                    }

                    if (par7 == 3)
                    {
                        ++par6;
                    }

                    if (par7 == 4)
                    {
                        --par4;
                    }

                    if (par7 == 5)
                    {
                        ++par4;
                    }

                    if (par3World.isEmpty(par4, par5, par6))
                    {
                        var13 = Block.byId[Block.COCOA.id].getPlacedData(par3World, par4, par5, par6, par7, par8, par9, par10, 0);
                        par3World.setTypeIdAndData(par4, par5, par6, Block.COCOA.id, var13);

                        if (!par2EntityPlayer.abilities.canInstantlyBuild)
                        {
                            --par1ItemStack.count;
                        }
                    }

                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public boolean a(ItemStack par1ItemStack, EntityLiving par2EntityLiving)
    {
        if (par2EntityLiving instanceof EntitySheep)
        {
            EntitySheep var3 = (EntitySheep)par2EntityLiving;
            int var4 = BlockCloth.e_(par1ItemStack.getData());

            if (!var3.isSheared() && var3.getColor() != var4)
            {
                var3.setColor(var4);
                --par1ItemStack.count;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
