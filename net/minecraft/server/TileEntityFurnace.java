package net.minecraft.server;

public class TileEntityFurnace extends TileEntity implements IInventory
{
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] items = new ItemStack[3];

    /** The number of ticks that the furnace will keep burning */
    public int burnTime = 0;

    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    public int ticksForCurrentFuel = 0;

    /** The number of ticks that the current item has been cooking for */
    public int cookTime = 0;

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return this.items.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int par1)
    {
        return this.items[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack splitStack(int par1, int par2)
    {
        if (this.items[par1] != null)
        {
            ItemStack var3;

            if (this.items[par1].count <= par2)
            {
                var3 = this.items[par1];
                this.items[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.items[par1].a(par2);

                if (this.items[par1].count == 0)
                {
                    this.items[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int par1)
    {
        if (this.items[par1] != null)
        {
            ItemStack var2 = this.items[par1];
            this.items[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int par1, ItemStack par2ItemStack)
    {
        this.items[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.count > this.getMaxStackSize())
        {
            par2ItemStack.count = this.getMaxStackSize();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "container.furnace";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound par1NBTTagCompound)
    {
        super.a(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getList("Items");
        this.items = new ItemStack[this.getSize()];

        for (int var3 = 0; var3 < var2.size(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.items.length)
            {
                this.items[var5] = ItemStack.a(var4);
            }
        }

        this.burnTime = par1NBTTagCompound.getShort("BurnTime");
        this.cookTime = par1NBTTagCompound.getShort("CookTime");
        this.ticksForCurrentFuel = fuelTime(this.items[1]);
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound par1NBTTagCompound)
    {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.burnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.cookTime);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.items.length; ++var3)
        {
            if (this.items[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.items[var3].save(var4);
                var2.add(var4);
            }
        }

        par1NBTTagCompound.set("Items", var2);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return 64;
    }

    /**
     * Returns true if the furnace is currently burning
     */
    public boolean isBurning()
    {
        return this.burnTime > 0;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void g()
    {
        boolean var1 = this.burnTime > 0;
        boolean var2 = false;

        if (this.burnTime > 0)
        {
            --this.burnTime;
        }

        if (!this.world.isStatic)
        {
            if (this.burnTime == 0 && this.canBurn())
            {
                this.ticksForCurrentFuel = this.burnTime = fuelTime(this.items[1]);

                if (this.burnTime > 0)
                {
                    var2 = true;

                    if (this.items[1] != null)
                    {
                        --this.items[1].count;

                        if (this.items[1].count == 0)
                        {
                            Item var3 = this.items[1].getItem().r();
                            this.items[1] = var3 != null ? new ItemStack(var3) : null;
                        }
                    }
                }
            }

            if (this.isBurning() && this.canBurn())
            {
                ++this.cookTime;

                if (this.cookTime == 200)
                {
                    this.cookTime = 0;
                    this.burn();
                    var2 = true;
                }
            }
            else
            {
                this.cookTime = 0;
            }

            if (var1 != this.burnTime > 0)
            {
                var2 = true;
                BlockFurnace.a(this.burnTime > 0, this.world, this.x, this.y, this.z);
            }
        }

        if (var2)
        {
            this.update();
        }
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canBurn()
    {
        if (this.items[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack var1 = RecipesFurnace.getInstance().getResult(this.items[0].getItem().id);
            return var1 == null ? false : (this.items[2] == null ? true : (!this.items[2].doMaterialsMatch(var1) ? false : (this.items[2].count < this.getMaxStackSize() && this.items[2].count < this.items[2].getMaxStackSize() ? true : this.items[2].count < var1.getMaxStackSize())));
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void burn()
    {
        if (this.canBurn())
        {
            ItemStack var1 = RecipesFurnace.getInstance().getResult(this.items[0].getItem().id);

            if (this.items[2] == null)
            {
                this.items[2] = var1.cloneItemStack();
            }
            else if (this.items[2].id == var1.id)
            {
                ++this.items[2].count;
            }

            --this.items[0].count;

            if (this.items[0].count <= 0)
            {
                this.items[0] = null;
            }
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
    public static int fuelTime(ItemStack par0ItemStack)
    {
        if (par0ItemStack == null)
        {
            return 0;
        }
        else
        {
            int var1 = par0ItemStack.getItem().id;
            Item var2 = par0ItemStack.getItem();

            if (var1 < 256 && Block.byId[var1] != null)
            {
                Block var3 = Block.byId[var1];

                if (var3 == Block.WOOD_STEP)
                {
                    return 150;
                }

                if (var3.material == Material.WOOD)
                {
                    return 300;
                }
            }

            return var2 instanceof ItemTool && ((ItemTool)var2).g().equals("WOOD") ? 200 : (var2 instanceof ItemSword && ((ItemSword)var2).h().equals("WOOD") ? 200 : (var2 instanceof ItemHoe && ((ItemHoe)var2).g().equals("WOOD") ? 200 : (var1 == Item.STICK.id ? 100 : (var1 == Item.COAL.id ? 1600 : (var1 == Item.LAVA_BUCKET.id ? 20000 : (var1 == Block.SAPLING.id ? 100 : (var1 == Item.BLAZE_ROD.id ? 2400 : 0)))))));
        }
    }

    /**
     * Return true if item is a fuel source (getItemBurnTime() > 0).
     */
    public static boolean isFuel(ItemStack par0ItemStack)
    {
        return fuelTime(par0ItemStack) > 0;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman par1EntityPlayer)
    {
        return this.world.getTileEntity(this.x, this.y, this.z) != this ? false : par1EntityPlayer.e((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
    }

    public void startOpen() {}

    public void f() {}
}
