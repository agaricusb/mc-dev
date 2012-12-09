package net.minecraft.server;

public class ContainerChest extends Container
{
    private IInventory container;
    private int f;

    public ContainerChest(IInventory par1IInventory, IInventory par2IInventory)
    {
        this.container = par2IInventory;
        this.f = par2IInventory.getSize() / 9;
        par2IInventory.startOpen();
        int var3 = (this.f - 4) * 18;
        int var4;
        int var5;

        for (var4 = 0; var4 < this.f; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.a(new Slot(par2IInventory, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18));
            }
        }

        for (var4 = 0; var4 < 3; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.a(new Slot(par1IInventory, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3));
            }
        }

        for (var4 = 0; var4 < 9; ++var4)
        {
            this.a(new Slot(par1IInventory, var4, 8 + var4 * 18, 161 + var3));
        }
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return this.container.a_(par1EntityPlayer);
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

            if (par2 < this.f * 9)
            {
                if (!this.a(var5, this.f * 9, this.c.size(), true))
                {
                    return null;
                }
            }
            else if (!this.a(var5, 0, this.f * 9, false))
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
        }

        return var3;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void b(EntityHuman par1EntityPlayer)
    {
        super.b(par1EntityPlayer);
        this.container.f();
    }

    /**
     * Return this chest container's lower chest inventory.
     */
    public IInventory d()
    {
        return this.container;
    }
}
