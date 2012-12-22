package net.minecraft.server;

public final class ItemStack
{
    /** Size of the stack. */
    public int count;

    /**
     * Number of animation frames to go when receiving an item (by walking into it, for example).
     */
    public int b;

    /** ID of the item. */
    public int id;

    /**
     * A NBTTagMap containing data about an ItemStack. Can only be used for non stackable items
     */
    public NBTTagCompound tag;

    /** Damage dealt to the item or number of use. Raise when using items. */
    private int damage;

    /** Item frame this stack is on, or null if not on an item frame. */
    private EntityItemFrame f;

    public ItemStack(Block par1Block)
    {
        this(par1Block, 1);
    }

    public ItemStack(Block par1Block, int par2)
    {
        this(par1Block.id, par2, 0);
    }

    public ItemStack(Block par1Block, int par2, int par3)
    {
        this(par1Block.id, par2, par3);
    }

    public ItemStack(Item par1Item)
    {
        this(par1Item.id, 1, 0);
    }

    public ItemStack(Item par1Item, int par2)
    {
        this(par1Item.id, par2, 0);
    }

    public ItemStack(Item par1Item, int par2, int par3)
    {
        this(par1Item.id, par2, par3);
    }

    public ItemStack(int par1, int par2, int par3)
    {
        this.count = 0;
        this.f = null;
        this.id = par1;
        this.count = par2;
        this.damage = par3;
    }

    public static ItemStack a(NBTTagCompound par0NBTTagCompound)
    {
        ItemStack var1 = new ItemStack();
        var1.c(par0NBTTagCompound);
        return var1.getItem() != null ? var1 : null;
    }

    private ItemStack()
    {
        this.count = 0;
        this.f = null;
    }

    /**
     * Remove the argument from the stack size. Return a new stack object with argument size.
     */
    public ItemStack a(int par1)
    {
        ItemStack var2 = new ItemStack(this.id, par1, this.damage);

        if (this.tag != null)
        {
            var2.tag = (NBTTagCompound)this.tag.clone();
        }

        this.count -= par1;
        return var2;
    }

    /**
     * Returns the object corresponding to the stack.
     */
    public Item getItem()
    {
        return Item.byId[this.id];
    }

    public boolean placeItem(EntityHuman par1EntityPlayer, World par2World, int par3, int par4, int par5, int par6, float par7, float par8, float par9)
    {
        boolean var10 = this.getItem().interactWith(this, par1EntityPlayer, par2World, par3, par4, par5, par6, par7, par8, par9);

        if (var10)
        {
            par1EntityPlayer.a(StatisticList.E[this.id], 1);
        }

        return var10;
    }

    /**
     * Returns the strength of the stack against a given block.
     */
    public float a(Block par1Block)
    {
        return this.getItem().getDestroySpeed(this, par1Block);
    }

    /**
     * Called whenever this item stack is equipped and right clicked. Returns the new item stack to put in the position
     * where this item is. Args: world, player
     */
    public ItemStack a(World par1World, EntityHuman par2EntityPlayer)
    {
        return this.getItem().a(this, par1World, par2EntityPlayer);
    }

    public ItemStack b(World par1World, EntityHuman par2EntityPlayer)
    {
        return this.getItem().b(this, par1World, par2EntityPlayer);
    }

    /**
     * Write the stack fields to a NBT object. Return the new NBT object.
     */
    public NBTTagCompound save(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("id", (short) this.id);
        par1NBTTagCompound.setByte("Count", (byte) this.count);
        par1NBTTagCompound.setShort("Damage", (short) this.damage);

        if (this.tag != null)
        {
            par1NBTTagCompound.set("tag", this.tag);
        }

        return par1NBTTagCompound;
    }

    /**
     * Read the stack fields from a NBT object.
     */
    public void c(NBTTagCompound par1NBTTagCompound)
    {
        this.id = par1NBTTagCompound.getShort("id");
        this.count = par1NBTTagCompound.getByte("Count");
        this.damage = par1NBTTagCompound.getShort("Damage");

        if (par1NBTTagCompound.hasKey("tag"))
        {
            this.tag = par1NBTTagCompound.getCompound("tag");
        }
    }

    /**
     * Returns maximum size of the stack.
     */
    public int getMaxStackSize()
    {
        return this.getItem().getMaxStackSize();
    }

    /**
     * Returns true if the ItemStack can hold 2 or more units of the item.
     */
    public boolean isStackable()
    {
        return this.getMaxStackSize() > 1 && (!this.f() || !this.h());
    }

    /**
     * true if this itemStack is damageable
     */
    public boolean f()
    {
        return Item.byId[this.id].getMaxDurability() > 0;
    }

    public boolean usesData()
    {
        return Item.byId[this.id].l();
    }

    /**
     * returns true when a damageable item is damaged
     */
    public boolean h()
    {
        return this.f() && this.damage > 0;
    }

    /**
     * gets the damage of an itemstack, for displaying purposes
     */
    public int i()
    {
        return this.damage;
    }

    /**
     * gets the damage of an itemstack
     */
    public int getData()
    {
        return this.damage;
    }

    /**
     * Sets the item damage of the ItemStack.
     */
    public void setData(int par1)
    {
        this.damage = par1;
    }

    /**
     * Returns the max damage an item in the stack can take.
     */
    public int k()
    {
        return Item.byId[this.id].getMaxDurability();
    }

    /**
     * Damages the item in the ItemStack
     */
    public void damage(int par1, EntityLiving par2EntityLiving)
    {
        if (this.f())
        {
            if (par1 > 0 && par2EntityLiving instanceof EntityHuman)
            {
                int var3 = EnchantmentManager.getEnchantmentLevel(Enchantment.DURABILITY.id, this);
                int var4 = 0;

                for (int var5 = 0; var3 > 0 && var5 < par1; ++var5)
                {
                    if (EnchantmentDurability.a(this, var3, par2EntityLiving.world.random))
                    {
                        ++var4;
                    }
                }

                par1 -= var4;

                if (par1 <= 0)
                {
                    return;
                }
            }

            if (!(par2EntityLiving instanceof EntityHuman) || !((EntityHuman)par2EntityLiving).abilities.canInstantlyBuild)
            {
                this.damage += par1;
            }

            if (this.damage > this.k())
            {
                par2EntityLiving.a(this);

                if (par2EntityLiving instanceof EntityHuman)
                {
                    ((EntityHuman)par2EntityLiving).a(StatisticList.F[this.id], 1);
                }

                --this.count;

                if (this.count < 0)
                {
                    this.count = 0;
                }

                this.damage = 0;
            }
        }
    }

    /**
     * Calls the corresponding fct in di
     */
    public void a(EntityLiving par1EntityLiving, EntityHuman par2EntityPlayer)
    {
        boolean var3 = Item.byId[this.id].a(this, par1EntityLiving, par2EntityPlayer);

        if (var3)
        {
            par2EntityPlayer.a(StatisticList.E[this.id], 1);
        }
    }

    public void a(World par1World, int par2, int par3, int par4, int par5, EntityHuman par6EntityPlayer)
    {
        boolean var7 = Item.byId[this.id].a(this, par1World, par2, par3, par4, par5, par6EntityPlayer);

        if (var7)
        {
            par6EntityPlayer.a(StatisticList.E[this.id], 1);
        }
    }

    /**
     * Returns the damage against a given entity.
     */
    public int a(Entity par1Entity)
    {
        return Item.byId[this.id].a(par1Entity);
    }

    /**
     * Checks if the itemStack object can harvest a specified block
     */
    public boolean b(Block par1Block)
    {
        return Item.byId[this.id].canDestroySpecialBlock(par1Block);
    }

    public boolean a(EntityLiving par1EntityLiving)
    {
        return Item.byId[this.id].a(this, par1EntityLiving);
    }

    /**
     * Returns a new stack with the same properties.
     */
    public ItemStack cloneItemStack()
    {
        ItemStack var1 = new ItemStack(this.id, this.count, this.damage);

        if (this.tag != null)
        {
            var1.tag = (NBTTagCompound)this.tag.clone();
        }

        return var1;
    }

    public static boolean equals(ItemStack par0ItemStack, ItemStack par1ItemStack)
    {
        return par0ItemStack == null && par1ItemStack == null ? true : (par0ItemStack != null && par1ItemStack != null ? (par0ItemStack.tag == null && par1ItemStack.tag != null ? false : par0ItemStack.tag == null || par0ItemStack.tag.equals(par1ItemStack.tag)) : false);
    }

    /**
     * compares ItemStack argument1 with ItemStack argument2; returns true if both ItemStacks are equal
     */
    public static boolean matches(ItemStack par0ItemStack, ItemStack par1ItemStack)
    {
        return par0ItemStack == null && par1ItemStack == null ? true : (par0ItemStack != null && par1ItemStack != null ? par0ItemStack.d(par1ItemStack) : false);
    }

    /**
     * compares ItemStack argument to the instance ItemStack; returns true if both ItemStacks are equal
     */
    private boolean d(ItemStack par1ItemStack)
    {
        return this.count != par1ItemStack.count ? false : (this.id != par1ItemStack.id ? false : (this.damage != par1ItemStack.damage ? false : (this.tag == null && par1ItemStack.tag != null ? false : this.tag == null || this.tag.equals(par1ItemStack.tag))));
    }

    /**
     * compares ItemStack argument to the instance ItemStack; returns true if the Items contained in both ItemStacks are
     * equal
     */
    public boolean doMaterialsMatch(ItemStack par1ItemStack)
    {
        return this.id == par1ItemStack.id && this.damage == par1ItemStack.damage;
    }

    public String a()
    {
        return Item.byId[this.id].d(this);
    }

    /**
     * Creates a copy of a ItemStack, a null parameters will return a null.
     */
    public static ItemStack b(ItemStack par0ItemStack)
    {
        return par0ItemStack == null ? null : par0ItemStack.cloneItemStack();
    }

    public String toString()
    {
        return this.count + "x" + Item.byId[this.id].getName() + "@" + this.damage;
    }

    /**
     * Called each tick as long the ItemStack in on player inventory. Used to progress the pickup animation and update
     * maps.
     */
    public void a(World par1World, Entity par2Entity, int par3, boolean par4)
    {
        if (this.b > 0)
        {
            --this.b;
        }

        Item.byId[this.id].a(this, par1World, par2Entity, par3, par4);
    }

    public void a(World par1World, EntityHuman par2EntityPlayer, int par3)
    {
        par2EntityPlayer.a(StatisticList.D[this.id], par3);
        Item.byId[this.id].d(this, par1World, par2EntityPlayer);
    }

    public int m()
    {
        return this.getItem().c_(this);
    }

    public EnumAnimation n()
    {
        return this.getItem().b_(this);
    }

    /**
     * Called when the player releases the use item button. Args: world, entityplayer, itemInUseCount
     */
    public void b(World par1World, EntityHuman par2EntityPlayer, int par3)
    {
        this.getItem().a(this, par1World, par2EntityPlayer, par3);
    }

    /**
     * Returns true if the ItemStack has an NBTTagCompound. Currently used to store enchantments.
     */
    public boolean hasTag()
    {
        return this.tag != null;
    }

    /**
     * Returns the NBTTagCompound of the ItemStack.
     */
    public NBTTagCompound getTag()
    {
        return this.tag;
    }

    public NBTTagList getEnchantments()
    {
        return this.tag == null ? null : (NBTTagList)this.tag.get("ench");
    }

    /**
     * Assigns a NBTTagCompound to the ItemStack, minecraft validates that only non-stackable items can have it.
     */
    public void setTag(NBTTagCompound par1NBTTagCompound)
    {
        this.tag = par1NBTTagCompound;
    }

    /**
     * returns the display name of the itemstack
     */
    public String r()
    {
        String var1 = this.getItem().l(this);

        if (this.tag != null && this.tag.hasKey("display"))
        {
            NBTTagCompound var2 = this.tag.getCompound("display");

            if (var2.hasKey("Name"))
            {
                var1 = var2.getString("Name");
            }
        }

        return var1;
    }

    /**
     * Sets the item's name (used by anvil to rename the items).
     */
    public void c(String par1Str)
    {
        if (this.tag == null)
        {
            this.tag = new NBTTagCompound();
        }

        if (!this.tag.hasKey("display"))
        {
            this.tag.setCompound("display", new NBTTagCompound());
        }

        this.tag.getCompound("display").setString("Name", par1Str);
    }

    /**
     * Returns true if the itemstack has a display name
     */
    public boolean s()
    {
        return this.tag == null ? false : (!this.tag.hasKey("display") ? false : this.tag.getCompound("display").hasKey("Name"));
    }

    /**
     * True if it is a tool and has no enchantments to begin with
     */
    public boolean v()
    {
        return !this.getItem().d_(this) ? false : !this.hasEnchantments();
    }

    /**
     * Adds an enchantment with a desired level on the ItemStack.
     */
    public void addEnchantment(Enchantment par1Enchantment, int par2)
    {
        if (this.tag == null)
        {
            this.setTag(new NBTTagCompound());
        }

        if (!this.tag.hasKey("ench"))
        {
            this.tag.set("ench", new NBTTagList("ench"));
        }

        NBTTagList var3 = (NBTTagList)this.tag.get("ench");
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setShort("id", (short) par1Enchantment.id);
        var4.setShort("lvl", (short) ((byte) par2));
        var3.add(var4);
    }

    /**
     * True if the item has enchantment data
     */
    public boolean hasEnchantments()
    {
        return this.tag != null && this.tag.hasKey("ench");
    }

    public void a(String par1Str, NBTBase par2NBTBase)
    {
        if (this.tag == null)
        {
            this.setTag(new NBTTagCompound());
        }

        this.tag.set(par1Str, par2NBTBase);
    }

    public boolean x()
    {
        return this.getItem().x();
    }

    /**
     * Return whether this stack is on an item frame.
     */
    public boolean y()
    {
        return this.f != null;
    }

    /**
     * Set the item frame this stack is on.
     */
    public void a(EntityItemFrame par1EntityItemFrame)
    {
        this.f = par1EntityItemFrame;
    }

    /**
     * Return the item frame this stack is on. Returns null if not on an item frame.
     */
    public EntityItemFrame z()
    {
        return this.f;
    }

    /**
     * Get this stack's repair cost, or 0 if no repair cost is defined.
     */
    public int getRepairCost()
    {
        return this.hasTag() && this.tag.hasKey("RepairCost") ? this.tag.getInt("RepairCost") : 0;
    }

    /**
     * Set this stack's repair cost, or 0 if no repair cost is defined.
     */
    public void setRepairCost(int par1)
    {
        if (!this.hasTag())
        {
            this.tag = new NBTTagCompound();
        }

        this.tag.setInt("RepairCost", par1);
    }
}
