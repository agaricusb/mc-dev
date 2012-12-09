package net.minecraft.server;

public class ContainerWorkbench extends Container
{
    /** The crafting matrix inventory (3x3). */
    public InventoryCrafting craftInventory = new InventoryCrafting(this, 3, 3);
    public IInventory resultInventory = new InventoryCraftResult();
    private World g;
    private int h;
    private int i;
    private int j;

    public ContainerWorkbench(PlayerInventory par1InventoryPlayer, World par2World, int par3, int par4, int par5)
    {
        this.g = par2World;
        this.h = par3;
        this.i = par4;
        this.j = par5;
        this.a(new SlotResult(par1InventoryPlayer.player, this.craftInventory, this.resultInventory, 0, 124, 35));
        int var6;
        int var7;

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (var7 = 0; var7 < 3; ++var7)
            {
                this.a(new Slot(this.craftInventory, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
            }
        }

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (var7 = 0; var7 < 9; ++var7)
            {
                this.a(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }

        for (var6 = 0; var6 < 9; ++var6)
        {
            this.a(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
        }

        this.a(this.craftInventory);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void a(IInventory par1IInventory)
    {
        this.resultInventory.setItem(0, CraftingManager.getInstance().craft(this.craftInventory, this.g));
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void b(EntityHuman par1EntityPlayer)
    {
        super.b(par1EntityPlayer);

        if (!this.g.isStatic)
        {
            for (int var2 = 0; var2 < 9; ++var2)
            {
                ItemStack var3 = this.craftInventory.splitWithoutUpdate(var2);

                if (var3 != null)
                {
                    par1EntityPlayer.drop(var3);
                }
            }
        }
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return this.g.getTypeId(this.h, this.i, this.j) != Block.WORKBENCH.id ? false : par1EntityPlayer.e((double) this.h + 0.5D, (double) this.i + 0.5D, (double) this.j + 0.5D) <= 64.0D;
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
                if (!this.a(var5, 10, 46, true))
                {
                    return null;
                }

                var4.a(var5, var3);
            }
            else if (par2 >= 10 && par2 < 37)
            {
                if (!this.a(var5, 37, 46, false))
                {
                    return null;
                }
            }
            else if (par2 >= 37 && par2 < 46)
            {
                if (!this.a(var5, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.a(var5, 10, 46, false))
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
