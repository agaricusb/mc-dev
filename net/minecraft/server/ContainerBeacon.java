package net.minecraft.server;

public class ContainerBeacon extends Container
{
    private TileEntityBeacon a;

    /**
     * This beacon's slot where you put in Emerald, Diamond, Gold or Iron Ingot.
     */
    private final SlotBeacon f;
    private int g;
    private int h;
    private int i;

    public ContainerBeacon(PlayerInventory par1InventoryPlayer, TileEntityBeacon par2TileEntityBeacon)
    {
        this.a = par2TileEntityBeacon;
        this.a(this.f = new SlotBeacon(this, par2TileEntityBeacon, 0, 136, 110));
        byte var3 = 36;
        short var4 = 137;
        int var5;

        for (var5 = 0; var5 < 3; ++var5)
        {
            for (int var6 = 0; var6 < 9; ++var6)
            {
                this.a(new Slot(par1InventoryPlayer, var6 + var5 * 9 + 9, var3 + var6 * 18, var4 + var5 * 18));
            }
        }

        for (var5 = 0; var5 < 9; ++var5)
        {
            this.a(new Slot(par1InventoryPlayer, var5, var3 + var5 * 18, 58 + var4));
        }

        this.g = par2TileEntityBeacon.k();
        this.h = par2TileEntityBeacon.i();
        this.i = par2TileEntityBeacon.j();
    }

    public void addSlotListener(ICrafting par1ICrafting)
    {
        super.addSlotListener(par1ICrafting);
        par1ICrafting.setContainerData(this, 0, this.g);
        par1ICrafting.setContainerData(this, 1, this.h);
        par1ICrafting.setContainerData(this, 2, this.i);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void b()
    {
        super.b();
    }

    /**
     * Returns the Tile Entity behind this beacon inventory / container
     */
    public TileEntityBeacon d()
    {
        return this.a;
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return this.a.a_(par1EntityPlayer);
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

                var4.a(var5, var3);
            }
            else if (!this.f.d() && this.f.isAllowed(var5) && var5.count == 1)
            {
                if (!this.a(var5, 0, 1, false))
                {
                    return null;
                }
            }
            else if (par2 >= 1 && par2 < 28)
            {
                if (!this.a(var5, 28, 37, false))
                {
                    return null;
                }
            }
            else if (par2 >= 28 && par2 < 37)
            {
                if (!this.a(var5, 1, 28, false))
                {
                    return null;
                }
            }
            else if (!this.a(var5, 1, 37, false))
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
