package net.minecraft.server;

public class PlayerInventory implements IInventory
{
    /**
     * An array of 36 item stacks indicating the main player inventory (including the visible bar).
     */
    public ItemStack[] items = new ItemStack[36];

    /** An array of 4 item stacks containing the currently worn armor pieces. */
    public ItemStack[] armor = new ItemStack[4];

    /** The index of the currently held item (0-8). */
    public int itemInHandIndex = 0;

    /** The player whose inventory this is. */
    public EntityHuman player;
    private ItemStack g;

    /**
     * Set true whenever the inventory changes. Nothing sets it false so you will have to write your own code to check
     * it and reset the value.
     */
    public boolean e = false;

    public PlayerInventory(EntityHuman par1EntityPlayer)
    {
        this.player = par1EntityPlayer;
    }

    /**
     * Returns the item stack currently held by the player.
     */
    public ItemStack getItemInHand()
    {
        return this.itemInHandIndex < 9 && this.itemInHandIndex >= 0 ? this.items[this.itemInHandIndex] : null;
    }

    /**
     * Get the size of the player hotbar inventory
     */
    public static int getHotbarSize()
    {
        return 9;
    }

    /**
     * Returns a slot index in main inventory containing a specific itemID
     */
    private int h(int par1)
    {
        for (int var2 = 0; var2 < this.items.length; ++var2)
        {
            if (this.items[var2] != null && this.items[var2].id == par1)
            {
                return var2;
            }
        }

        return -1;
    }

    /**
     * stores an itemstack in the users inventory
     */
    private int firstPartial(ItemStack par1ItemStack)
    {
        for (int var2 = 0; var2 < this.items.length; ++var2)
        {
            if (this.items[var2] != null && this.items[var2].id == par1ItemStack.id && this.items[var2].isStackable() && this.items[var2].count < this.items[var2].getMaxStackSize() && this.items[var2].count < this.getMaxStackSize() && (!this.items[var2].usesData() || this.items[var2].getData() == par1ItemStack.getData()) && ItemStack.equals(this.items[var2], par1ItemStack))
            {
                return var2;
            }
        }

        return -1;
    }

    /**
     * Returns the first item stack that is empty.
     */
    public int i()
    {
        for (int var1 = 0; var1 < this.items.length; ++var1)
        {
            if (this.items[var1] == null)
            {
                return var1;
            }
        }

        return -1;
    }

    /**
     * Clear this player's inventory, using the specified ID and metadata as filters or -1 for no filter.
     */
    public int b(int par1, int par2)
    {
        int var3 = 0;
        int var4;
        ItemStack var5;

        for (var4 = 0; var4 < this.items.length; ++var4)
        {
            var5 = this.items[var4];

            if (var5 != null && (par1 <= -1 || var5.id == par1) && (par2 <= -1 || var5.getData() == par2))
            {
                var3 += var5.count;
                this.items[var4] = null;
            }
        }

        for (var4 = 0; var4 < this.armor.length; ++var4)
        {
            var5 = this.armor[var4];

            if (var5 != null && (par1 <= -1 || var5.id == par1) && (par2 <= -1 || var5.getData() == par2))
            {
                var3 += var5.count;
                this.armor[var4] = null;
            }
        }

        return var3;
    }

    /**
     * This function stores as many items of an ItemStack as possible in a matching slot and returns the quantity of
     * left over items.
     */
    private int e(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.id;
        int var3 = par1ItemStack.count;
        int var4;

        if (par1ItemStack.getMaxStackSize() == 1)
        {
            var4 = this.i();

            if (var4 < 0)
            {
                return var3;
            }
            else
            {
                if (this.items[var4] == null)
                {
                    this.items[var4] = ItemStack.b(par1ItemStack);
                }

                return 0;
            }
        }
        else
        {
            var4 = this.firstPartial(par1ItemStack);

            if (var4 < 0)
            {
                var4 = this.i();
            }

            if (var4 < 0)
            {
                return var3;
            }
            else
            {
                if (this.items[var4] == null)
                {
                    this.items[var4] = new ItemStack(var2, 0, par1ItemStack.getData());

                    if (par1ItemStack.hasTag())
                    {
                        this.items[var4].setTag((NBTTagCompound) par1ItemStack.getTag().clone());
                    }
                }

                int var5 = var3;

                if (var3 > this.items[var4].getMaxStackSize() - this.items[var4].count)
                {
                    var5 = this.items[var4].getMaxStackSize() - this.items[var4].count;
                }

                if (var5 > this.getMaxStackSize() - this.items[var4].count)
                {
                    var5 = this.getMaxStackSize() - this.items[var4].count;
                }

                if (var5 == 0)
                {
                    return var3;
                }
                else
                {
                    var3 -= var5;
                    this.items[var4].count += var5;
                    this.items[var4].b = 5;
                    return var3;
                }
            }
        }
    }

    /**
     * Decrement the number of animations remaining. Only called on client side. This is used to handle the animation of
     * receiving a block.
     */
    public void j()
    {
        for (int var1 = 0; var1 < this.items.length; ++var1)
        {
            if (this.items[var1] != null)
            {
                this.items[var1].a(this.player.world, this.player, var1, this.itemInHandIndex == var1);
            }
        }
    }

    /**
     * removed one item of specified itemID from inventory (if it is in a stack, the stack size will reduce with 1)
     */
    public boolean d(int par1)
    {
        int var2 = this.h(par1);

        if (var2 < 0)
        {
            return false;
        }
        else
        {
            if (--this.items[var2].count <= 0)
            {
                this.items[var2] = null;
            }

            return true;
        }
    }

    /**
     * Get if a specifiied item id is inside the inventory.
     */
    public boolean e(int par1)
    {
        int var2 = this.h(par1);
        return var2 >= 0;
    }

    /**
     * Adds the item stack to the inventory, returns false if it is impossible.
     */
    public boolean pickup(ItemStack par1ItemStack)
    {
        int var2;

        if (par1ItemStack.h())
        {
            var2 = this.i();

            if (var2 >= 0)
            {
                this.items[var2] = ItemStack.b(par1ItemStack);
                this.items[var2].b = 5;
                par1ItemStack.count = 0;
                return true;
            }
            else if (this.player.abilities.canInstantlyBuild)
            {
                par1ItemStack.count = 0;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            do
            {
                var2 = par1ItemStack.count;
                par1ItemStack.count = this.e(par1ItemStack);
            }
            while (par1ItemStack.count > 0 && par1ItemStack.count < var2);

            if (par1ItemStack.count == var2 && this.player.abilities.canInstantlyBuild)
            {
                par1ItemStack.count = 0;
                return true;
            }
            else
            {
                return par1ItemStack.count < var2;
            }
        }
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack splitStack(int par1, int par2)
    {
        ItemStack[] var3 = this.items;

        if (par1 >= this.items.length)
        {
            var3 = this.armor;
            par1 -= this.items.length;
        }

        if (var3[par1] != null)
        {
            ItemStack var4;

            if (var3[par1].count <= par2)
            {
                var4 = var3[par1];
                var3[par1] = null;
                return var4;
            }
            else
            {
                var4 = var3[par1].a(par2);

                if (var3[par1].count == 0)
                {
                    var3[par1] = null;
                }

                return var4;
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
        ItemStack[] var2 = this.items;

        if (par1 >= this.items.length)
        {
            var2 = this.armor;
            par1 -= this.items.length;
        }

        if (var2[par1] != null)
        {
            ItemStack var3 = var2[par1];
            var2[par1] = null;
            return var3;
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
        ItemStack[] var3 = this.items;

        if (par1 >= var3.length)
        {
            par1 -= var3.length;
            var3 = this.armor;
        }

        var3[par1] = par2ItemStack;
    }

    /**
     * Gets the strength of the current item (tool) against the specified block, 1.0f if not holding anything.
     */
    public float a(Block par1Block)
    {
        float var2 = 1.0F;

        if (this.items[this.itemInHandIndex] != null)
        {
            var2 *= this.items[this.itemInHandIndex].a(par1Block);
        }

        return var2;
    }

    /**
     * Writes the inventory out as a list of compound tags. This is where the slot indices are used (+100 for armor, +80
     * for crafting).
     */
    public NBTTagList a(NBTTagList par1NBTTagList)
    {
        int var2;
        NBTTagCompound var3;

        for (var2 = 0; var2 < this.items.length; ++var2)
        {
            if (this.items[var2] != null)
            {
                var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte) var2);
                this.items[var2].save(var3);
                par1NBTTagList.add(var3);
            }
        }

        for (var2 = 0; var2 < this.armor.length; ++var2)
        {
            if (this.armor[var2] != null)
            {
                var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte) (var2 + 100));
                this.armor[var2].save(var3);
                par1NBTTagList.add(var3);
            }
        }

        return par1NBTTagList;
    }

    /**
     * Reads from the given tag list and fills the slots in the inventory with the correct items.
     */
    public void b(NBTTagList par1NBTTagList)
    {
        this.items = new ItemStack[36];
        this.armor = new ItemStack[4];

        for (int var2 = 0; var2 < par1NBTTagList.size(); ++var2)
        {
            NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.get(var2);
            int var4 = var3.getByte("Slot") & 255;
            ItemStack var5 = ItemStack.a(var3);

            if (var5 != null)
            {
                if (var4 >= 0 && var4 < this.items.length)
                {
                    this.items[var4] = var5;
                }

                if (var4 >= 100 && var4 < this.armor.length + 100)
                {
                    this.armor[var4 - 100] = var5;
                }
            }
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return this.items.length + 4;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int par1)
    {
        ItemStack[] var2 = this.items;

        if (par1 >= var2.length)
        {
            par1 -= var2.length;
            var2 = this.armor;
        }

        return var2[par1];
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "container.inventory";
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
     * Return damage vs an entity done by the current held weapon, or 1 if nothing is held
     */
    public int a(Entity par1Entity)
    {
        ItemStack var2 = this.getItem(this.itemInHandIndex);
        return var2 != null ? var2.a(par1Entity) : 1;
    }

    /**
     * Returns whether the current item (tool) can harvest from the specified block (actually get a result).
     */
    public boolean b(Block par1Block)
    {
        if (par1Block.material.isAlwaysDestroyable())
        {
            return true;
        }
        else
        {
            ItemStack var2 = this.getItem(this.itemInHandIndex);
            return var2 != null ? var2.b(par1Block) : false;
        }
    }

    /**
     * returns a player armor item (as itemstack) contained in specified armor slot.
     */
    public ItemStack f(int par1)
    {
        return this.armor[par1];
    }

    /**
     * Based on the damage values and maximum damage values of each armor item, returns the current armor value.
     */
    public int k()
    {
        int var1 = 0;

        for (int var2 = 0; var2 < this.armor.length; ++var2)
        {
            if (this.armor[var2] != null && this.armor[var2].getItem() instanceof ItemArmor)
            {
                int var3 = ((ItemArmor)this.armor[var2].getItem()).b;
                var1 += var3;
            }
        }

        return var1;
    }

    /**
     * Damages armor in each slot by the specified amount.
     */
    public void g(int par1)
    {
        par1 /= 4;

        if (par1 < 1)
        {
            par1 = 1;
        }

        for (int var2 = 0; var2 < this.armor.length; ++var2)
        {
            if (this.armor[var2] != null && this.armor[var2].getItem() instanceof ItemArmor)
            {
                this.armor[var2].damage(par1, this.player);

                if (this.armor[var2].count == 0)
                {
                    this.armor[var2] = null;
                }
            }
        }
    }

    /**
     * Drop all armor and main inventory items.
     */
    public void l()
    {
        int var1;

        for (var1 = 0; var1 < this.items.length; ++var1)
        {
            if (this.items[var1] != null)
            {
                this.player.a(this.items[var1], true);
                this.items[var1] = null;
            }
        }

        for (var1 = 0; var1 < this.armor.length; ++var1)
        {
            if (this.armor[var1] != null)
            {
                this.player.a(this.armor[var1], true);
                this.armor[var1] = null;
            }
        }
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void update()
    {
        this.e = true;
    }

    public void setCarried(ItemStack par1ItemStack)
    {
        this.g = par1ItemStack;
    }

    public ItemStack getCarried()
    {
        return this.g;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman par1EntityPlayer)
    {
        return this.player.dead ? false : par1EntityPlayer.e(this.player) <= 64.0D;
    }

    /**
     * Returns true if the specified ItemStack exists in the inventory.
     */
    public boolean c(ItemStack par1ItemStack)
    {
        int var2;

        for (var2 = 0; var2 < this.armor.length; ++var2)
        {
            if (this.armor[var2] != null && this.armor[var2].doMaterialsMatch(par1ItemStack))
            {
                return true;
            }
        }

        for (var2 = 0; var2 < this.items.length; ++var2)
        {
            if (this.items[var2] != null && this.items[var2].doMaterialsMatch(par1ItemStack))
            {
                return true;
            }
        }

        return false;
    }

    public void startOpen() {}

    public void f() {}

    /**
     * Copy the ItemStack contents from another InventoryPlayer instance
     */
    public void b(PlayerInventory par1InventoryPlayer)
    {
        int var2;

        for (var2 = 0; var2 < this.items.length; ++var2)
        {
            this.items[var2] = ItemStack.b(par1InventoryPlayer.items[var2]);
        }

        for (var2 = 0; var2 < this.armor.length; ++var2)
        {
            this.armor[var2] = ItemStack.b(par1InventoryPlayer.armor[var2]);
        }

        this.itemInHandIndex = par1InventoryPlayer.itemInHandIndex;
    }
}
