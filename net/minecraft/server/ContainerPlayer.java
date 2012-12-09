package net.minecraft.server;

public class ContainerPlayer extends Container
{
    /** The crafting matrix inventory. */
    public InventoryCrafting craftInventory = new InventoryCrafting(this, 2, 2);
    public IInventory resultInventory = new InventoryCraftResult();

    /** Determines if inventory manipulation should be handled. */
    public boolean g = false;
    private final EntityHuman h;

    public ContainerPlayer(PlayerInventory par1InventoryPlayer, boolean par2, EntityHuman par3EntityPlayer)
    {
        this.g = par2;
        this.h = par3EntityPlayer;
        this.a(new SlotResult(par1InventoryPlayer.player, this.craftInventory, this.resultInventory, 0, 144, 36));
        int var4;
        int var5;

        for (var4 = 0; var4 < 2; ++var4)
        {
            for (var5 = 0; var5 < 2; ++var5)
            {
                this.a(new Slot(this.craftInventory, var5 + var4 * 2, 88 + var5 * 18, 26 + var4 * 18));
            }
        }

        for (var4 = 0; var4 < 4; ++var4)
        {
            this.a(new SlotArmor(this, par1InventoryPlayer, par1InventoryPlayer.getSize() - 1 - var4, 8, 8 + var4 * 18, var4));
        }

        for (var4 = 0; var4 < 3; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.a(new Slot(par1InventoryPlayer, var5 + (var4 + 1) * 9, 8 + var5 * 18, 84 + var4 * 18));
            }
        }

        for (var4 = 0; var4 < 9; ++var4)
        {
            this.a(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 142));
        }

        this.a(this.craftInventory);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void a(IInventory par1IInventory)
    {
        this.resultInventory.setItem(0, CraftingManager.getInstance().craft(this.craftInventory, this.h.world));
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void b(EntityHuman par1EntityPlayer)
    {
        super.b(par1EntityPlayer);

        for (int var2 = 0; var2 < 4; ++var2)
        {
            ItemStack var3 = this.craftInventory.splitWithoutUpdate(var2);

            if (var3 != null)
            {
                par1EntityPlayer.drop(var3);
            }
        }

        this.resultInventory.setItem(0, (ItemStack) null);
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return true;
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
                if (!this.a(var5, 9, 45, true))
                {
                    return null;
                }

                var4.a(var5, var3);
            }
            else if (par2 >= 1 && par2 < 5)
            {
                if (!this.a(var5, 9, 45, false))
                {
                    return null;
                }
            }
            else if (par2 >= 5 && par2 < 9)
            {
                if (!this.a(var5, 9, 45, false))
                {
                    return null;
                }
            }
            else if (var3.getItem() instanceof ItemArmor && !((Slot)this.c.get(5 + ((ItemArmor)var3.getItem()).a)).d())
            {
                int var6 = 5 + ((ItemArmor)var3.getItem()).a;

                if (!this.a(var5, var6, var6 + 1, false))
                {
                    return null;
                }
            }
            else if (par2 >= 9 && par2 < 36)
            {
                if (!this.a(var5, 36, 45, false))
                {
                    return null;
                }
            }
            else if (par2 >= 36 && par2 < 45)
            {
                if (!this.a(var5, 9, 36, false))
                {
                    return null;
                }
            }
            else if (!this.a(var5, 9, 45, false))
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
