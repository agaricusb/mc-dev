package net.minecraft.server;

public class ItemWorldMap extends ItemWorldMapBase
{
    protected ItemWorldMap(int par1)
    {
        super(par1);
        this.a(true);
    }

    public WorldMap getSavedMap(ItemStack par1ItemStack, World par2World)
    {
        String var3 = "map_" + par1ItemStack.getData();
        WorldMap var4 = (WorldMap)par2World.a(WorldMap.class, var3);

        if (var4 == null && !par2World.isStatic)
        {
            par1ItemStack.setData(par2World.b("map"));
            var3 = "map_" + par1ItemStack.getData();
            var4 = new WorldMap(var3);
            var4.scale = 3;
            int var5 = 128 * (1 << var4.scale);
            var4.centerX = Math.round((float)par2World.getWorldData().c() / (float)var5) * var5;
            var4.centerZ = Math.round((float)(par2World.getWorldData().e() / var5)) * var5;
            var4.map = (byte)par2World.worldProvider.dimension;
            var4.c();
            par2World.a(var3, var4);
        }

        return var4;
    }

    public void a(World par1World, Entity par2Entity, WorldMap par3MapData)
    {
        if (par1World.worldProvider.dimension == par3MapData.map && par2Entity instanceof EntityHuman)
        {
            short var4 = 128;
            short var5 = 128;
            int var6 = 1 << par3MapData.scale;
            int var7 = par3MapData.centerX;
            int var8 = par3MapData.centerZ;
            int var9 = MathHelper.floor(par2Entity.locX - (double) var7) / var6 + var4 / 2;
            int var10 = MathHelper.floor(par2Entity.locZ - (double) var8) / var6 + var5 / 2;
            int var11 = 128 / var6;

            if (par1World.worldProvider.f)
            {
                var11 /= 2;
            }

            WorldMapHumanTracker var12 = par3MapData.a((EntityHuman) par2Entity);
            ++var12.d;

            for (int var13 = var9 - var11 + 1; var13 < var9 + var11; ++var13)
            {
                if ((var13 & 15) == (var12.d & 15))
                {
                    int var14 = 255;
                    int var15 = 0;
                    double var16 = 0.0D;

                    for (int var18 = var10 - var11 - 1; var18 < var10 + var11; ++var18)
                    {
                        if (var13 >= 0 && var18 >= -1 && var13 < var4 && var18 < var5)
                        {
                            int var19 = var13 - var9;
                            int var20 = var18 - var10;
                            boolean var21 = var19 * var19 + var20 * var20 > (var11 - 2) * (var11 - 2);
                            int var22 = (var7 / var6 + var13 - var4 / 2) * var6;
                            int var23 = (var8 / var6 + var18 - var5 / 2) * var6;
                            int[] var24 = new int[256];
                            Chunk var25 = par1World.getChunkAtWorldCoords(var22, var23);

                            if (!var25.isEmpty())
                            {
                                int var26 = var22 & 15;
                                int var27 = var23 & 15;
                                int var28 = 0;
                                double var29 = 0.0D;
                                int var31;
                                int var32;
                                int var33;
                                int var36;

                                if (par1World.worldProvider.f)
                                {
                                    var31 = var22 + var23 * 231871;
                                    var31 = var31 * var31 * 31287121 + var31 * 11;

                                    if ((var31 >> 20 & 1) == 0)
                                    {
                                        var24[Block.DIRT.id] += 10;
                                    }
                                    else
                                    {
                                        var24[Block.STONE.id] += 10;
                                    }

                                    var29 = 100.0D;
                                }
                                else
                                {
                                    for (var31 = 0; var31 < var6; ++var31)
                                    {
                                        for (var32 = 0; var32 < var6; ++var32)
                                        {
                                            var33 = var25.b(var31 + var26, var32 + var27) + 1;
                                            int var34 = 0;

                                            if (var33 > 1)
                                            {
                                                boolean var35;

                                                do
                                                {
                                                    var35 = true;
                                                    var34 = var25.getTypeId(var31 + var26, var33 - 1, var32 + var27);

                                                    if (var34 == 0)
                                                    {
                                                        var35 = false;
                                                    }
                                                    else if (var33 > 0 && var34 > 0 && Block.byId[var34].material.G == MaterialMapColor.b)
                                                    {
                                                        var35 = false;
                                                    }

                                                    if (!var35)
                                                    {
                                                        --var33;

                                                        if (var33 <= 0)
                                                        {
                                                            break;
                                                        }

                                                        var34 = var25.getTypeId(var31 + var26, var33 - 1, var32 + var27);
                                                    }
                                                }
                                                while (var33 > 0 && !var35);

                                                if (var33 > 0 && var34 != 0 && Block.byId[var34].material.isLiquid())
                                                {
                                                    var36 = var33 - 1;
                                                    boolean var37 = false;
                                                    int var43;

                                                    do
                                                    {
                                                        var43 = var25.getTypeId(var31 + var26, var36--, var32 + var27);
                                                        ++var28;
                                                    }
                                                    while (var36 > 0 && var43 != 0 && Block.byId[var43].material.isLiquid());
                                                }
                                            }

                                            var29 += (double)var33 / (double)(var6 * var6);
                                            ++var24[var34];
                                        }
                                    }
                                }

                                var28 /= var6 * var6;
                                var31 = 0;
                                var32 = 0;

                                for (var33 = 0; var33 < 256; ++var33)
                                {
                                    if (var24[var33] > var31)
                                    {
                                        var32 = var33;
                                        var31 = var24[var33];
                                    }
                                }

                                double var40 = (var29 - var16) * 4.0D / (double)(var6 + 4) + ((double)(var13 + var18 & 1) - 0.5D) * 0.4D;
                                byte var39 = 1;

                                if (var40 > 0.6D)
                                {
                                    var39 = 2;
                                }

                                if (var40 < -0.6D)
                                {
                                    var39 = 0;
                                }

                                var36 = 0;

                                if (var32 > 0)
                                {
                                    MaterialMapColor var42 = Block.byId[var32].material.G;

                                    if (var42 == MaterialMapColor.n)
                                    {
                                        var40 = (double)var28 * 0.1D + (double)(var13 + var18 & 1) * 0.2D;
                                        var39 = 1;

                                        if (var40 < 0.5D)
                                        {
                                            var39 = 2;
                                        }

                                        if (var40 > 0.9D)
                                        {
                                            var39 = 0;
                                        }
                                    }

                                    var36 = var42.q;
                                }

                                var16 = var29;

                                if (var18 >= 0 && var19 * var19 + var20 * var20 < var11 * var11 && (!var21 || (var13 + var18 & 1) != 0))
                                {
                                    byte var41 = par3MapData.colors[var13 + var18 * var4];
                                    byte var38 = (byte)(var36 * 4 + var39);

                                    if (var41 != var38)
                                    {
                                        if (var14 > var18)
                                        {
                                            var14 = var18;
                                        }

                                        if (var15 < var18)
                                        {
                                            var15 = var18;
                                        }

                                        par3MapData.colors[var13 + var18 * var4] = var38;
                                    }
                                }
                            }
                        }
                    }

                    if (var14 <= var15)
                    {
                        par3MapData.flagDirty(var13, var14, var15);
                    }
                }
            }
        }
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void a(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (!par2World.isStatic)
        {
            WorldMap var6 = this.getSavedMap(par1ItemStack, par2World);

            if (par3Entity instanceof EntityHuman)
            {
                EntityHuman var7 = (EntityHuman)par3Entity;
                var6.a(var7, par1ItemStack);
            }

            if (par5)
            {
                this.a(par2World, par3Entity, var6);
            }
        }
    }

    public Packet c(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        byte[] var4 = this.getSavedMap(par1ItemStack, par2World).getUpdatePacket(par1ItemStack, par2World, par3EntityPlayer);
        return var4 == null ? null : new Packet131ItemData((short) Item.MAP.id, (short)par1ItemStack.getData(), var4);
    }

    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
    public void d(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (par1ItemStack.hasTag() && par1ItemStack.getTag().getBoolean("map_is_scaling"))
        {
            WorldMap var4 = Item.MAP.getSavedMap(par1ItemStack, par2World);
            par1ItemStack.setData(par2World.b("map"));
            WorldMap var5 = new WorldMap("map_" + par1ItemStack.getData());
            var5.scale = (byte)(var4.scale + 1);

            if (var5.scale > 4)
            {
                var5.scale = 4;
            }

            var5.centerX = var4.centerX;
            var5.centerZ = var4.centerZ;
            var5.map = var4.map;
            var5.c();
            par2World.a("map_" + par1ItemStack.getData(), var5);
        }
    }
}
