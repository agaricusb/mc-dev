package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ContainerEnchantTable extends Container
{
    /** SlotEnchantmentTable object with ItemStack to be enchanted */
    public IInventory enchantSlots = new ContainerEnchantTableInventory(this, "Enchant", 1);

    /** current world (for bookshelf counting) */
    private World world;
    private int x;
    private int y;
    private int z;
    private Random l = new Random();

    /** used as seed for EnchantmentNameParts (see GuiEnchantment) */
    public long f;

    /** 3-member array storing the enchantment levels of each slot */
    public int[] costs = new int[3];

    public ContainerEnchantTable(PlayerInventory par1InventoryPlayer, World par2World, int par3, int par4, int par5)
    {
        this.world = par2World;
        this.x = par3;
        this.y = par4;
        this.z = par5;
        this.a(new SlotEnchant(this, this.enchantSlots, 0, 25, 47));
        int var6;

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (int var7 = 0; var7 < 9; ++var7)
            {
                this.a(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }

        for (var6 = 0; var6 < 9; ++var6)
        {
            this.a(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
        }
    }

    public void addSlotListener(ICrafting par1ICrafting)
    {
        super.addSlotListener(par1ICrafting);
        par1ICrafting.setContainerData(this, 0, this.costs[0]);
        par1ICrafting.setContainerData(this, 1, this.costs[1]);
        par1ICrafting.setContainerData(this, 2, this.costs[2]);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void b()
    {
        super.b();

        for (int var1 = 0; var1 < this.listeners.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.listeners.get(var1);
            var2.setContainerData(this, 0, this.costs[0]);
            var2.setContainerData(this, 1, this.costs[1]);
            var2.setContainerData(this, 2, this.costs[2]);
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void a(IInventory par1IInventory)
    {
        if (par1IInventory == this.enchantSlots)
        {
            ItemStack var2 = par1IInventory.getItem(0);
            int var3;

            if (var2 != null && var2.v())
            {
                this.f = this.l.nextLong();

                if (!this.world.isStatic)
                {
                    var3 = 0;
                    int var4;

                    for (var4 = -1; var4 <= 1; ++var4)
                    {
                        for (int var5 = -1; var5 <= 1; ++var5)
                        {
                            if ((var4 != 0 || var5 != 0) && this.world.isEmpty(this.x + var5, this.y, this.z + var4) && this.world.isEmpty(this.x + var5, this.y + 1, this.z + var4))
                            {
                                if (this.world.getTypeId(this.x + var5 * 2, this.y, this.z + var4 * 2) == Block.BOOKSHELF.id)
                                {
                                    ++var3;
                                }

                                if (this.world.getTypeId(this.x + var5 * 2, this.y + 1, this.z + var4 * 2) == Block.BOOKSHELF.id)
                                {
                                    ++var3;
                                }

                                if (var5 != 0 && var4 != 0)
                                {
                                    if (this.world.getTypeId(this.x + var5 * 2, this.y, this.z + var4) == Block.BOOKSHELF.id)
                                    {
                                        ++var3;
                                    }

                                    if (this.world.getTypeId(this.x + var5 * 2, this.y + 1, this.z + var4) == Block.BOOKSHELF.id)
                                    {
                                        ++var3;
                                    }

                                    if (this.world.getTypeId(this.x + var5, this.y, this.z + var4 * 2) == Block.BOOKSHELF.id)
                                    {
                                        ++var3;
                                    }

                                    if (this.world.getTypeId(this.x + var5, this.y + 1, this.z + var4 * 2) == Block.BOOKSHELF.id)
                                    {
                                        ++var3;
                                    }
                                }
                            }
                        }
                    }

                    for (var4 = 0; var4 < 3; ++var4)
                    {
                        this.costs[var4] = EnchantmentManager.a(this.l, var4, var3, var2);
                    }

                    this.b();
                }
            }
            else
            {
                for (var3 = 0; var3 < 3; ++var3)
                {
                    this.costs[var3] = 0;
                }
            }
        }
    }

    /**
     * enchants the item on the table using the specified slot; also deducts XP from player
     */
    public boolean a(EntityHuman par1EntityPlayer, int par2)
    {
        ItemStack var3 = this.enchantSlots.getItem(0);

        if (this.costs[par2] > 0 && var3 != null && (par1EntityPlayer.expLevel >= this.costs[par2] || par1EntityPlayer.abilities.canInstantlyBuild))
        {
            if (!this.world.isStatic)
            {
                List var4 = EnchantmentManager.b(this.l, var3, this.costs[par2]);

                if (var4 != null)
                {
                    par1EntityPlayer.levelDown(-this.costs[par2]);
                    Iterator var5 = var4.iterator();

                    while (var5.hasNext())
                    {
                        EnchantmentInstance var6 = (EnchantmentInstance)var5.next();
                        var3.addEnchantment(var6.enchantment, var6.level);
                    }

                    this.a(this.enchantSlots);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void b(EntityHuman par1EntityPlayer)
    {
        super.b(par1EntityPlayer);

        if (!this.world.isStatic)
        {
            ItemStack var2 = this.enchantSlots.splitWithoutUpdate(0);

            if (var2 != null)
            {
                par1EntityPlayer.drop(var2);
            }
        }
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return this.world.getTypeId(this.x, this.y, this.z) != Block.ENCHANTMENT_TABLE.id ? false : par1EntityPlayer.e((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
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

            if (par2 == 0)
            {
                if (!this.a(var5, 1, 37, true))
                {
                    return null;
                }
            }
            else
            {
                if (((Slot)this.c.get(0)).d() || !((Slot)this.c.get(0)).isAllowed(var5))
                {
                    return null;
                }

                if (var5.hasTag() && var5.count == 1)
                {
                    ((Slot)this.c.get(0)).set(var5.cloneItemStack());
                    var5.count = 0;
                }
                else if (var5.count >= 1)
                {
                    ((Slot)this.c.get(0)).set(new ItemStack(var5.id, 1, var5.getData()));
                    --var5.count;
                }
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
}
