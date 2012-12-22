package net.minecraft.server;

public class ContainerMerchant extends Container
{
    /** Instance of Merchant. */
    private IMerchant merchant;
    private InventoryMerchant f;

    /** Instance of World. */
    private final World g;

    public ContainerMerchant(PlayerInventory par1InventoryPlayer, IMerchant par2IMerchant, World par3World)
    {
        this.merchant = par2IMerchant;
        this.g = par3World;
        this.f = new InventoryMerchant(par1InventoryPlayer.player, par2IMerchant);
        this.a(new Slot(this.f, 0, 36, 53));
        this.a(new Slot(this.f, 1, 62, 53));
        this.a(new SlotMerchantResult(par1InventoryPlayer.player, par2IMerchant, this.f, 2, 120, 53));
        int var4;

        for (var4 = 0; var4 < 3; ++var4)
        {
            for (int var5 = 0; var5 < 9; ++var5)
            {
                this.a(new Slot(par1InventoryPlayer, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
            }
        }

        for (var4 = 0; var4 < 9; ++var4)
        {
            this.a(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 142));
        }
    }

    public InventoryMerchant getMerchantInventory()
    {
        return this.f;
    }

    public void addSlotListener(ICrafting par1ICrafting)
    {
        super.addSlotListener(par1ICrafting);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void b()
    {
        super.b();
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void a(IInventory par1IInventory)
    {
        this.f.g();
        super.a(par1IInventory);
    }

    public void b(int par1)
    {
        this.f.c(par1);
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return this.merchant.m_() == par1EntityPlayer;
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
                if (par2 >= 3 && par2 < 30)
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

    /**
     * Callback for when the crafting gui is closed.
     */
    public void b(EntityHuman par1EntityPlayer)
    {
        super.b(par1EntityPlayer);
        this.merchant.b_((EntityHuman) null);
        super.b(par1EntityPlayer);

        if (!this.g.isStatic)
        {
            ItemStack var2 = this.f.splitWithoutUpdate(0);

            if (var2 != null)
            {
                par1EntityPlayer.drop(var2);
            }

            var2 = this.f.splitWithoutUpdate(1);

            if (var2 != null)
            {
                par1EntityPlayer.drop(var2);
            }
        }
    }
}
