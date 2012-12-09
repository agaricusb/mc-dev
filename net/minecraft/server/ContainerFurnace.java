package net.minecraft.server;

public class ContainerFurnace extends Container
{
    private TileEntityFurnace furnace;
    private int f = 0;
    private int g = 0;
    private int h = 0;

    public ContainerFurnace(PlayerInventory par1InventoryPlayer, TileEntityFurnace par2TileEntityFurnace)
    {
        this.furnace = par2TileEntityFurnace;
        this.a(new Slot(par2TileEntityFurnace, 0, 56, 17));
        this.a(new Slot(par2TileEntityFurnace, 1, 56, 53));
        this.a(new SlotFurnaceResult(par1InventoryPlayer.player, par2TileEntityFurnace, 2, 116, 35));
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
        par1ICrafting.setContainerData(this, 0, this.furnace.cookTime);
        par1ICrafting.setContainerData(this, 1, this.furnace.burnTime);
        par1ICrafting.setContainerData(this, 2, this.furnace.ticksForCurrentFuel);
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

            if (this.f != this.furnace.cookTime)
            {
                var2.setContainerData(this, 0, this.furnace.cookTime);
            }

            if (this.g != this.furnace.burnTime)
            {
                var2.setContainerData(this, 1, this.furnace.burnTime);
            }

            if (this.h != this.furnace.ticksForCurrentFuel)
            {
                var2.setContainerData(this, 2, this.furnace.ticksForCurrentFuel);
            }
        }

        this.f = this.furnace.cookTime;
        this.g = this.furnace.burnTime;
        this.h = this.furnace.ticksForCurrentFuel;
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return this.furnace.a_(par1EntityPlayer);
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
            else if (par2 != 1 && par2 != 0)
            {
                if (RecipesFurnace.getInstance().getResult(var5.getItem().id) != null)
                {
                    if (!this.a(var5, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (TileEntityFurnace.isFuel(var5))
                {
                    if (!this.a(var5, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 3 && par2 < 30)
                {
                    if (!this.a(var5, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 30 && par2 < 39 && !this.a(var5, 3, 30, false))
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
}
