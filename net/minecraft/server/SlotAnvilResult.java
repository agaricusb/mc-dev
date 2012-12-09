package net.minecraft.server;

class SlotAnvilResult extends Slot
{
    final World a;

    final int b;

    final int c;

    final int d;

    /** The anvil this slot belongs to. */
    final ContainerAnvil e;

    SlotAnvilResult(ContainerAnvil par1ContainerRepair, IInventory par2IInventory, int par3, int par4, int par5, World par6World, int par7, int par8, int par9)
    {
        super(par2IInventory, par3, par4, par5);
        this.e = par1ContainerRepair;
        this.a = par6World;
        this.b = par7;
        this.c = par8;
        this.d = par9;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isAllowed(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        return (par1EntityPlayer.abilities.canInstantlyBuild || par1EntityPlayer.expLevel >= this.e.a) && this.e.a > 0 && this.d();
    }

    public void a(EntityHuman par1EntityPlayer, ItemStack par2ItemStack)
    {
        if (!par1EntityPlayer.abilities.canInstantlyBuild)
        {
            par1EntityPlayer.levelDown(-this.e.a);
        }

        ContainerAnvil.a(this.e).setItem(0, (ItemStack) null);

        if (ContainerAnvil.b(this.e) > 0)
        {
            ItemStack var3 = ContainerAnvil.a(this.e).getItem(1);

            if (var3 != null && var3.count > ContainerAnvil.b(this.e))
            {
                var3.count -= ContainerAnvil.b(this.e);
                ContainerAnvil.a(this.e).setItem(1, var3);
            }
            else
            {
                ContainerAnvil.a(this.e).setItem(1, (ItemStack) null);
            }
        }
        else
        {
            ContainerAnvil.a(this.e).setItem(1, (ItemStack) null);
        }

        this.e.a = 0;

        if (!par1EntityPlayer.abilities.canInstantlyBuild && !this.a.isStatic && this.a.getTypeId(this.b, this.c, this.d) == Block.ANVIL.id && par1EntityPlayer.aB().nextFloat() < 0.12F)
        {
            int var6 = this.a.getData(this.b, this.c, this.d);
            int var4 = var6 & 3;
            int var5 = var6 >> 2;
            ++var5;

            if (var5 > 2)
            {
                this.a.setTypeId(this.b, this.c, this.d, 0);
                this.a.triggerEffect(1020, this.b, this.c, this.d, 0);
            }
            else
            {
                this.a.setData(this.b, this.c, this.d, var4 | var5 << 2);
                this.a.triggerEffect(1021, this.b, this.c, this.d, 0);
            }
        }
        else if (!this.a.isStatic)
        {
            this.a.triggerEffect(1021, this.b, this.c, this.d, 0);
        }
    }
}
