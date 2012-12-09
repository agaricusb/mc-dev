package net.minecraft.server;

public class EntityItemFrame extends EntityHanging
{
    /** Chance for this item frame's item to drop from the frame. */
    private float e = 1.0F;

    public EntityItemFrame(World par1World)
    {
        super(par1World);
    }

    public EntityItemFrame(World par1World, int par2, int par3, int par4, int par5)
    {
        super(par1World, par2, par3, par4, par5);
        this.setDirection(par5);
    }

    protected void a()
    {
        this.getDataWatcher().a(2, 5);
        this.getDataWatcher().a(3, Byte.valueOf((byte) 0));
    }

    public int d()
    {
        return 9;
    }

    public int g()
    {
        return 9;
    }

    /**
     * Drop the item currently on this item frame.
     */
    public void h()
    {
        this.a(new ItemStack(Item.ITEM_FRAME), 0.0F);
        ItemStack var1 = this.i();

        if (var1 != null && this.random.nextFloat() < this.e)
        {
            var1 = var1.cloneItemStack();
            var1.a((EntityItemFrame) null);
            this.a(var1, 0.0F);
        }
    }

    public ItemStack i()
    {
        return this.getDataWatcher().f(2);
    }

    public void a(ItemStack par1ItemStack)
    {
        par1ItemStack = par1ItemStack.cloneItemStack();
        par1ItemStack.count = 1;
        par1ItemStack.a(this);
        this.getDataWatcher().watch(2, par1ItemStack);
        this.getDataWatcher().h(2);
    }

    /**
     * Return the rotation of the item currently on this frame.
     */
    public int j()
    {
        return this.getDataWatcher().getByte(3);
    }

    public void g(int par1)
    {
        this.getDataWatcher().watch(3, Byte.valueOf((byte) (par1 % 4)));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        if (this.i() != null)
        {
            par1NBTTagCompound.setCompound("Item", this.i().save(new NBTTagCompound()));
            par1NBTTagCompound.setByte("ItemRotation", (byte)this.j());
            par1NBTTagCompound.setFloat("ItemDropChance", this.e);
        }

        super.b(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagCompound var2 = par1NBTTagCompound.getCompound("Item");

        if (var2 != null && !var2.d())
        {
            this.a(ItemStack.a(var2));
            this.g(par1NBTTagCompound.getByte("ItemRotation"));

            if (par1NBTTagCompound.hasKey("ItemDropChance"))
            {
                this.e = par1NBTTagCompound.getFloat("ItemDropChance");
            }
        }

        super.a(par1NBTTagCompound);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean a(EntityHuman par1EntityPlayer)
    {
        if (this.i() == null)
        {
            ItemStack var2 = par1EntityPlayer.bD();

            if (var2 != null && !this.world.isStatic)
            {
                this.a(var2);

                if (!par1EntityPlayer.abilities.canInstantlyBuild && --var2.count <= 0)
                {
                    par1EntityPlayer.inventory.setItem(par1EntityPlayer.inventory.itemInHandIndex, (ItemStack) null);
                }
            }
        }
        else if (!this.world.isStatic)
        {
            this.g(this.j() + 1);
        }

        return true;
    }
}
