package net.minecraft.server;

public class InventoryEnderChest extends InventorySubcontainer
{
    private TileEntityEnderChest a;

    public InventoryEnderChest()
    {
        super("container.enderchest", 27);
    }

    public void a(TileEntityEnderChest par1TileEntityEnderChest)
    {
        this.a = par1TileEntityEnderChest;
    }

    public void a(NBTTagList par1NBTTagList)
    {
        int var2;

        for (var2 = 0; var2 < this.getSize(); ++var2)
        {
            this.setItem(var2, (ItemStack) null);
        }

        for (var2 = 0; var2 < par1NBTTagList.size(); ++var2)
        {
            NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.get(var2);
            int var4 = var3.getByte("Slot") & 255;

            if (var4 >= 0 && var4 < this.getSize())
            {
                this.setItem(var4, ItemStack.a(var3));
            }
        }
    }

    public NBTTagList g()
    {
        NBTTagList var1 = new NBTTagList("EnderItems");

        for (int var2 = 0; var2 < this.getSize(); ++var2)
        {
            ItemStack var3 = this.getItem(var2);

            if (var3 != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var2);
                var3.save(var4);
                var1.add(var4);
            }
        }

        return var1;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman par1EntityPlayer)
    {
        return this.a != null && !this.a.a(par1EntityPlayer) ? false : super.a_(par1EntityPlayer);
    }

    public void startOpen()
    {
        if (this.a != null)
        {
            this.a.a();
        }

        super.startOpen();
    }

    public void f()
    {
        if (this.a != null)
        {
            this.a.b();
        }

        super.f();
        this.a = null;
    }
}
