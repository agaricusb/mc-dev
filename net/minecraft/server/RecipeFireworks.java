package net.minecraft.server;

import java.util.ArrayList;

public class RecipeFireworks implements IRecipe
{
    private ItemStack a;

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean a(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        this.a = null;
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;

        for (int var9 = 0; var9 < par1InventoryCrafting.getSize(); ++var9)
        {
            ItemStack var10 = par1InventoryCrafting.getItem(var9);

            if (var10 != null)
            {
                if (var10.id == Item.SULPHUR.id)
                {
                    ++var4;
                }
                else if (var10.id == Item.FIREWORKS_CHARGE.id)
                {
                    ++var6;
                }
                else if (var10.id == Item.INK_SACK.id)
                {
                    ++var5;
                }
                else if (var10.id == Item.PAPER.id)
                {
                    ++var3;
                }
                else if (var10.id == Item.GLOWSTONE_DUST.id)
                {
                    ++var7;
                }
                else if (var10.id == Item.DIAMOND.id)
                {
                    ++var7;
                }
                else if (var10.id == Item.FIREBALL.id)
                {
                    ++var8;
                }
                else if (var10.id == Item.FEATHER.id)
                {
                    ++var8;
                }
                else if (var10.id == Item.GOLD_NUGGET.id)
                {
                    ++var8;
                }
                else
                {
                    if (var10.id != Item.SKULL.id)
                    {
                        return false;
                    }

                    ++var8;
                }
            }
        }

        var7 += var5 + var8;

        if (var4 <= 3 && var3 <= 1)
        {
            NBTTagCompound var15;
            NBTTagCompound var18;

            if (var4 >= 1 && var3 == 1 && var7 == 0)
            {
                this.a = new ItemStack(Item.FIREWORKS);

                if (var6 > 0)
                {
                    var15 = new NBTTagCompound();
                    var18 = new NBTTagCompound("Fireworks");
                    NBTTagList var25 = new NBTTagList("Explosions");

                    for (int var22 = 0; var22 < par1InventoryCrafting.getSize(); ++var22)
                    {
                        ItemStack var26 = par1InventoryCrafting.getItem(var22);

                        if (var26 != null && var26.id == Item.FIREWORKS_CHARGE.id && var26.hasTag() && var26.getTag().hasKey("Explosion"))
                        {
                            var25.add(var26.getTag().getCompound("Explosion"));
                        }
                    }

                    var18.set("Explosions", var25);
                    var18.setByte("Flight", (byte) var4);
                    var15.set("Fireworks", var18);
                    this.a.setTag(var15);
                }

                return true;
            }
            else if (var4 == 1 && var3 == 0 && var6 == 0 && var5 > 0 && var8 <= 1)
            {
                this.a = new ItemStack(Item.FIREWORKS_CHARGE);
                var15 = new NBTTagCompound();
                var18 = new NBTTagCompound("Explosion");
                byte var21 = 0;
                ArrayList var12 = new ArrayList();

                for (int var13 = 0; var13 < par1InventoryCrafting.getSize(); ++var13)
                {
                    ItemStack var14 = par1InventoryCrafting.getItem(var13);

                    if (var14 != null)
                    {
                        if (var14.id == Item.INK_SACK.id)
                        {
                            var12.add(Integer.valueOf(ItemDye.b[var14.getData()]));
                        }
                        else if (var14.id == Item.GLOWSTONE_DUST.id)
                        {
                            var18.setBoolean("Flicker", true);
                        }
                        else if (var14.id == Item.DIAMOND.id)
                        {
                            var18.setBoolean("Trail", true);
                        }
                        else if (var14.id == Item.FIREBALL.id)
                        {
                            var21 = 1;
                        }
                        else if (var14.id == Item.FEATHER.id)
                        {
                            var21 = 4;
                        }
                        else if (var14.id == Item.GOLD_NUGGET.id)
                        {
                            var21 = 2;
                        }
                        else if (var14.id == Item.SKULL.id)
                        {
                            var21 = 3;
                        }
                    }
                }

                int[] var24 = new int[var12.size()];

                for (int var27 = 0; var27 < var24.length; ++var27)
                {
                    var24[var27] = ((Integer)var12.get(var27)).intValue();
                }

                var18.setIntArray("Colors", var24);
                var18.setByte("Type", var21);
                var15.set("Explosion", var18);
                this.a.setTag(var15);
                return true;
            }
            else if (var4 == 0 && var3 == 0 && var6 == 1 && var5 > 0 && var5 == var7)
            {
                ArrayList var16 = new ArrayList();

                for (int var20 = 0; var20 < par1InventoryCrafting.getSize(); ++var20)
                {
                    ItemStack var11 = par1InventoryCrafting.getItem(var20);

                    if (var11 != null)
                    {
                        if (var11.id == Item.INK_SACK.id)
                        {
                            var16.add(Integer.valueOf(ItemDye.b[var11.getData()]));
                        }
                        else if (var11.id == Item.FIREWORKS_CHARGE.id)
                        {
                            this.a = var11.cloneItemStack();
                            this.a.count = 1;
                        }
                    }
                }

                int[] var17 = new int[var16.size()];

                for (int var19 = 0; var19 < var17.length; ++var19)
                {
                    var17[var19] = ((Integer)var16.get(var19)).intValue();
                }

                if (this.a != null && this.a.hasTag())
                {
                    NBTTagCompound var23 = this.a.getTag().getCompound("Explosion");

                    if (var23 == null)
                    {
                        return false;
                    }
                    else
                    {
                        var23.setIntArray("FadeColors", var17);
                        return true;
                    }
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack a(InventoryCrafting par1InventoryCrafting)
    {
        return this.a.cloneItemStack();
    }

    /**
     * Returns the size of the recipe area
     */
    public int a()
    {
        return 10;
    }

    public ItemStack b()
    {
        return this.a;
    }
}
