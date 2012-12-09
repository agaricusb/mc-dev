package net.minecraft.server;

public class ContainerBrewingStand extends Container
{
    private TileEntityBrewingStand brewingStand;

    /** Instance of Slot. */
    private final Slot f;
    private int g = 0;

    public ContainerBrewingStand(PlayerInventory par1InventoryPlayer, TileEntityBrewingStand par2TileEntityBrewingStand)
    {
        this.brewingStand = par2TileEntityBrewingStand;
        this.a(new SlotPotionBottle(par1InventoryPlayer.player, par2TileEntityBrewingStand, 0, 56, 46));
        this.a(new SlotPotionBottle(par1InventoryPlayer.player, par2TileEntityBrewingStand, 1, 79, 53));
        this.a(new SlotPotionBottle(par1InventoryPlayer.player, par2TileEntityBrewingStand, 2, 102, 46));
        this.f = this.a(new SlotBrewing(this, par2TileEntityBrewingStand, 3, 79, 17));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.a(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.a(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    public void addSlotListener(ICrafting par1ICrafting)
    {
        super.addSlotListener(par1ICrafting);
        par1ICrafting.setContainerData(this, 0, this.brewingStand.x_());
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

            if (this.g != this.brewingStand.x_())
            {
                var2.setContainerData(this, 0, this.brewingStand.x_());
            }
        }

        this.g = this.brewingStand.x_();
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return this.brewingStand.a_(par1EntityPlayer);
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

            if ((par2 < 0 || par2 > 2) && par2 != 3)
            {
                if (!this.f.d() && this.f.isAllowed(var5))
                {
                    if (!this.a(var5, 3, 4, false))
                    {
                        return null;
                    }
                }
                else if (SlotPotionBottle.a_(var3))
                {
                    if (!this.a(var5, 0, 3, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 4 && par2 < 31)
                {
                    if (!this.a(var5, 31, 40, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 31 && par2 < 40)
                {
                    if (!this.a(var5, 4, 31, false))
                    {
                        return null;
                    }
                }
                else if (!this.a(var5, 4, 40, false))
                {
                    return null;
                }
            }
            else
            {
                if (!this.a(var5, 4, 40, true))
                {
                    return null;
                }

                var4.a(var5, var3);
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
