package net.minecraft.server;

import java.util.Iterator;
import java.util.Map;

public class ContainerAnvil extends Container
{
    /** Here comes out item you merged and/or renamed. */
    private IInventory f = new InventoryCraftResult();

    /**
     * The 2slots where you put your items in that you want to merge and/or rename.
     */
    private IInventory g = new ContainerAnvilInventory(this, "Repair", 2);
    private World h;
    private int i;
    private int j;
    private int k;

    /** The maximum cost of repairing/renaming in the anvil. */
    public int a = 0;
    private int l = 0;
    private String m;

    /** The player that has this container open. */
    private final EntityHuman n;

    public ContainerAnvil(PlayerInventory par1InventoryPlayer, World par2World, int par3, int par4, int par5, EntityHuman par6EntityPlayer)
    {
        this.h = par2World;
        this.i = par3;
        this.j = par4;
        this.k = par5;
        this.n = par6EntityPlayer;
        this.a(new Slot(this.g, 0, 27, 47));
        this.a(new Slot(this.g, 1, 76, 47));
        this.a(new SlotAnvilResult(this, this.f, 2, 134, 47, par2World, par3, par4, par5));
        int var7;

        for (var7 = 0; var7 < 3; ++var7)
        {
            for (int var8 = 0; var8 < 9; ++var8)
            {
                this.a(new Slot(par1InventoryPlayer, var8 + var7 * 9 + 9, 8 + var8 * 18, 84 + var7 * 18));
            }
        }

        for (var7 = 0; var7 < 9; ++var7)
        {
            this.a(new Slot(par1InventoryPlayer, var7, 8 + var7 * 18, 142));
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void a(IInventory par1IInventory)
    {
        super.a(par1IInventory);

        if (par1IInventory == this.g)
        {
            this.d();
        }
    }

    public void d()
    {
        ItemStack var1 = this.g.getItem(0);
        this.a = 0;
        int var2 = 0;
        byte var3 = 0;
        int var4 = 0;

        if (var1 == null)
        {
            this.f.setItem(0, (ItemStack) null);
            this.a = 0;
        }
        else
        {
            ItemStack var5 = var1.cloneItemStack();
            ItemStack var6 = this.g.getItem(1);
            Map var7 = EnchantmentManager.a(var5);
            int var18 = var3 + var1.getRepairCost() + (var6 == null ? 0 : var6.getRepairCost());
            this.l = 0;
            int var8;
            int var9;
            int var10;
            int var12;
            Enchantment var21;
            Iterator var20;

            if (var6 != null)
            {
                if (var5.f() && Item.byId[var5.id].a(var1, var6))
                {
                    var8 = Math.min(var5.i(), var5.k() / 4);

                    if (var8 <= 0)
                    {
                        this.f.setItem(0, (ItemStack) null);
                        this.a = 0;
                        return;
                    }

                    for (var9 = 0; var8 > 0 && var9 < var6.count; ++var9)
                    {
                        var10 = var5.i() - var8;
                        var5.setData(var10);
                        var2 += Math.max(1, var8 / 100) + var7.size();
                        var8 = Math.min(var5.i(), var5.k() / 4);
                    }

                    this.l = var9;
                }
                else
                {
                    if (var5.id != var6.id || !var5.f())
                    {
                        this.f.setItem(0, (ItemStack) null);
                        this.a = 0;
                        return;
                    }

                    if (var5.f())
                    {
                        var8 = var1.k() - var1.i();
                        var9 = var6.k() - var6.i();
                        var10 = var9 + var5.k() * 12 / 100;
                        int var11 = var8 + var10;
                        var12 = var5.k() - var11;

                        if (var12 < 0)
                        {
                            var12 = 0;
                        }

                        if (var12 < var5.getData())
                        {
                            var5.setData(var12);
                            var2 += Math.max(1, var10 / 100);
                        }
                    }

                    Map var19 = EnchantmentManager.a(var6);
                    var20 = var19.keySet().iterator();

                    while (var20.hasNext())
                    {
                        var10 = ((Integer)var20.next()).intValue();
                        var21 = Enchantment.byId[var10];
                        var12 = var7.containsKey(Integer.valueOf(var10)) ? ((Integer)var7.get(Integer.valueOf(var10))).intValue() : 0;
                        int var13 = ((Integer)var19.get(Integer.valueOf(var10))).intValue();
                        int var10000;

                        if (var12 == var13)
                        {
                            ++var13;
                            var10000 = var13;
                        }
                        else
                        {
                            var10000 = Math.max(var13, var12);
                        }

                        var13 = var10000;
                        int var14 = var13 - var12;
                        boolean var15 = true;
                        Iterator var16 = var7.keySet().iterator();

                        while (var16.hasNext())
                        {
                            int var17 = ((Integer)var16.next()).intValue();

                            if (var17 != var10 && !var21.a(Enchantment.byId[var17]))
                            {
                                var15 = false;
                                var2 += var14;
                            }
                        }

                        if (var15)
                        {
                            if (var13 > var21.getMaxLevel())
                            {
                                var13 = var21.getMaxLevel();
                            }

                            var7.put(Integer.valueOf(var10), Integer.valueOf(var13));
                            byte var23 = 0;

                            switch (var21.getRandomWeight())
                            {
                                case 1:
                                    var23 = 8;
                                    break;

                                case 2:
                                    var23 = 4;

                                case 3:
                                case 4:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                default:
                                    break;

                                case 5:
                                    var23 = 2;
                                    break;

                                case 10:
                                    var23 = 1;
                            }

                            var2 += var23 * var14;
                        }
                    }
                }
            }

            if (this.m != null && !this.m.equalsIgnoreCase(var1.r()) && this.m.length() > 0)
            {
                var4 = var1.f() ? 7 : var1.count * 5;
                var2 += var4;

                if (var1.s())
                {
                    var18 += var4 / 2;
                }

                var5.c(this.m);
            }

            var8 = 0;
            byte var22;

            for (var20 = var7.keySet().iterator(); var20.hasNext(); var18 += var8 + var12 * var22)
            {
                var10 = ((Integer)var20.next()).intValue();
                var21 = Enchantment.byId[var10];
                var12 = ((Integer)var7.get(Integer.valueOf(var10))).intValue();
                var22 = 0;
                ++var8;

                switch (var21.getRandomWeight())
                {
                    case 1:
                        var22 = 8;
                        break;

                    case 2:
                        var22 = 4;

                    case 3:
                    case 4:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    default:
                        break;

                    case 5:
                        var22 = 2;
                        break;

                    case 10:
                        var22 = 1;
                }
            }

            this.a = var18 + var2;

            if (var2 <= 0)
            {
                var5 = null;
            }

            if (var4 == var2 && var4 > 0 && this.a >= 40)
            {
                System.out.println("Naming an item only, cost too high; giving discount to cap cost to 39 levels");
                this.a = 39;
            }

            if (this.a >= 40 && !this.n.abilities.canInstantlyBuild)
            {
                var5 = null;
            }

            if (var5 != null)
            {
                var9 = var5.getRepairCost();

                if (var6 != null && var9 < var6.getRepairCost())
                {
                    var9 = var6.getRepairCost();
                }

                if (var5.s())
                {
                    var9 -= 9;
                }

                if (var9 < 0)
                {
                    var9 = 0;
                }

                var9 += 2;
                var5.setRepairCost(var9);
                EnchantmentManager.a(var7, var5);
            }

            this.f.setItem(0, var5);
            this.b();
        }
    }

    public void addSlotListener(ICrafting par1ICrafting)
    {
        super.addSlotListener(par1ICrafting);
        par1ICrafting.setContainerData(this, 0, this.a);
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void b(EntityHuman par1EntityPlayer)
    {
        super.b(par1EntityPlayer);

        if (!this.h.isStatic)
        {
            for (int var2 = 0; var2 < this.g.getSize(); ++var2)
            {
                ItemStack var3 = this.g.splitWithoutUpdate(var2);

                if (var3 != null)
                {
                    par1EntityPlayer.drop(var3);
                }
            }
        }
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return this.h.getTypeId(this.i, this.j, this.k) != Block.ANVIL.id ? false : par1EntityPlayer.e((double) this.i + 0.5D, (double) this.j + 0.5D, (double) this.k + 0.5D) <= 64.0D;
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    public ItemStack b(EntityHuman par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.c.get(par2);

        if (var4 != null && var4.d())
        {
            ItemStack var5 = var4.getItem();
            var3 = var5.cloneItemStack();

            if (par2 == 2)
            {
                if (!this.a(var5, 3, 39, true))
                {
                    return null;
                }

                var4.a(var5, var3);
            }
            else if (par2 != 0 && par2 != 1)
            {
                if (par2 >= 3 && par2 < 39 && !this.a(var5, 0, 2, false))
                {
                    return null;
                }
            }
            else if (!this.a(var5, 3, 39, false))
            {
                return null;
            }

            if (var5.count == 0)
            {
                var4.set((ItemStack) null);
            }
            else
            {
                var4.e();
            }

            if (var5.count == var3.count)
            {
                return null;
            }

            var4.a(par1EntityPlayer, var5);
        }

        return var3;
    }

    public void a(String par1Str)
    {
        this.m = par1Str;

        if (this.getSlot(2).d())
        {
            this.getSlot(2).getItem().c(this.m);
        }

        this.d();
    }

    static IInventory a(ContainerAnvil par0ContainerRepair)
    {
        return par0ContainerRepair.g;
    }

    static int b(ContainerAnvil par0ContainerRepair)
    {
        return par0ContainerRepair.l;
    }
}
