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

    /** determined by damage of input item and stackSize of repair materials */
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

    /**
     * called when the Anvil Input Slot changes, calculates the new result and puts it in the output slot
     */
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
            boolean var8 = false;
            int var19 = var3 + var1.getRepairCost() + (var6 == null ? 0 : var6.getRepairCost());
            this.l = 0;
            int var9;
            int var10;
            int var11;
            int var13;
            int var14;
            Iterator var21;
            Enchantment var22;

            if (var6 != null)
            {
                var8 = var6.id == Item.ENCHANTED_BOOK.id && Item.ENCHANTED_BOOK.g(var6).size() > 0;

                if (var5.f() && Item.byId[var5.id].a(var1, var6))
                {
                    var9 = Math.min(var5.i(), var5.k() / 4);

                    if (var9 <= 0)
                    {
                        this.f.setItem(0, (ItemStack) null);
                        this.a = 0;
                        return;
                    }

                    for (var10 = 0; var9 > 0 && var10 < var6.count; ++var10)
                    {
                        var11 = var5.i() - var9;
                        var5.setData(var11);
                        var2 += Math.max(1, var9 / 100) + var7.size();
                        var9 = Math.min(var5.i(), var5.k() / 4);
                    }

                    this.l = var10;
                }
                else
                {
                    if (!var8 && (var5.id != var6.id || !var5.f()))
                    {
                        this.f.setItem(0, (ItemStack) null);
                        this.a = 0;
                        return;
                    }

                    if (var5.f() && !var8)
                    {
                        var9 = var1.k() - var1.i();
                        var10 = var6.k() - var6.i();
                        var11 = var10 + var5.k() * 12 / 100;
                        int var12 = var9 + var11;
                        var13 = var5.k() - var12;

                        if (var13 < 0)
                        {
                            var13 = 0;
                        }

                        if (var13 < var5.getData())
                        {
                            var5.setData(var13);
                            var2 += Math.max(1, var11 / 100);
                        }
                    }

                    Map var20 = EnchantmentManager.a(var6);
                    var21 = var20.keySet().iterator();

                    while (var21.hasNext())
                    {
                        var11 = ((Integer)var21.next()).intValue();
                        var22 = Enchantment.byId[var11];
                        var13 = var7.containsKey(Integer.valueOf(var11)) ? ((Integer)var7.get(Integer.valueOf(var11))).intValue() : 0;
                        var14 = ((Integer)var20.get(Integer.valueOf(var11))).intValue();
                        int var10000;

                        if (var13 == var14)
                        {
                            ++var14;
                            var10000 = var14;
                        }
                        else
                        {
                            var10000 = Math.max(var14, var13);
                        }

                        var14 = var10000;
                        int var15 = var14 - var13;
                        boolean var16 = var22.canEnchant(var1);

                        if (this.n.abilities.canInstantlyBuild)
                        {
                            var16 = true;
                        }

                        Iterator var17 = var7.keySet().iterator();

                        while (var17.hasNext())
                        {
                            int var18 = ((Integer)var17.next()).intValue();

                            if (var18 != var11 && !var22.a(Enchantment.byId[var18]))
                            {
                                var16 = false;
                                var2 += var15;
                            }
                        }

                        if (var16)
                        {
                            if (var14 > var22.getMaxLevel())
                            {
                                var14 = var22.getMaxLevel();
                            }

                            var7.put(Integer.valueOf(var11), Integer.valueOf(var14));
                            int var23 = 0;

                            switch (var22.getRandomWeight())
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

                            if (var8)
                            {
                                var23 = Math.max(1, var23 / 2);
                            }

                            var2 += var23 * var15;
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
                    var19 += var4 / 2;
                }

                var5.c(this.m);
            }

            var9 = 0;

            for (var21 = var7.keySet().iterator(); var21.hasNext(); var19 += var9 + var13 * var14)
            {
                var11 = ((Integer)var21.next()).intValue();
                var22 = Enchantment.byId[var11];
                var13 = ((Integer)var7.get(Integer.valueOf(var11))).intValue();
                var14 = 0;
                ++var9;

                switch (var22.getRandomWeight())
                {
                    case 1:
                        var14 = 8;
                        break;

                    case 2:
                        var14 = 4;

                    case 3:
                    case 4:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    default:
                        break;

                    case 5:
                        var14 = 2;
                        break;

                    case 10:
                        var14 = 1;
                }

                if (var8)
                {
                    var14 = Math.max(1, var14 / 2);
                }
            }

            if (var8)
            {
                var19 = Math.max(1, var19 / 2);
            }

            this.a = var19 + var2;

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
                var10 = var5.getRepairCost();

                if (var6 != null && var10 < var6.getRepairCost())
                {
                    var10 = var6.getRepairCost();
                }

                if (var5.s())
                {
                    var10 -= 9;
                }

                if (var10 < 0)
                {
                    var10 = 0;
                }

                var10 += 2;
                var5.setRepairCost(var10);
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

    /**
     * used by the Anvil GUI to update the Item Name being typed by the player
     */
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
