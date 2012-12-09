package net.minecraft.server;

public class InventoryMerchant implements IInventory
{
    private final IMerchant merchant;
    private ItemStack[] itemsInSlots = new ItemStack[3];
    private final EntityHuman player;
    private MerchantRecipe recipe;
    private int e;

    public InventoryMerchant(EntityHuman par1EntityPlayer, IMerchant par2IMerchant)
    {
        this.player = par1EntityPlayer;
        this.merchant = par2IMerchant;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return this.itemsInSlots.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int par1)
    {
        return this.itemsInSlots[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack splitStack(int par1, int par2)
    {
        if (this.itemsInSlots[par1] != null)
        {
            ItemStack var3;

            if (par1 == 2)
            {
                var3 = this.itemsInSlots[par1];
                this.itemsInSlots[par1] = null;
                return var3;
            }
            else if (this.itemsInSlots[par1].count <= par2)
            {
                var3 = this.itemsInSlots[par1];
                this.itemsInSlots[par1] = null;

                if (this.d(par1))
                {
                    this.g();
                }

                return var3;
            }
            else
            {
                var3 = this.itemsInSlots[par1].a(par2);

                if (this.itemsInSlots[par1].count == 0)
                {
                    this.itemsInSlots[par1] = null;
                }

                if (this.d(par1))
                {
                    this.g();
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
     * if par1 slot has changed, does resetRecipeAndSlots need to be called?
     */
    private boolean d(int par1)
    {
        return par1 == 0 || par1 == 1;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int par1)
    {
        if (this.itemsInSlots[par1] != null)
        {
            ItemStack var2 = this.itemsInSlots[par1];
            this.itemsInSlots[par1] = null;
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
        this.itemsInSlots[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.count > this.getMaxStackSize())
        {
            par2ItemStack.count = this.getMaxStackSize();
        }

        if (this.d(par1))
        {
            this.g();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "mob.villager";
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
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a_(EntityHuman par1EntityPlayer)
    {
        return this.merchant.m_() == par1EntityPlayer;
    }

    public void startOpen() {}

    public void f() {}

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void update()
    {
        this.g();
    }

    public void g()
    {
        this.recipe = null;
        ItemStack var1 = this.itemsInSlots[0];
        ItemStack var2 = this.itemsInSlots[1];

        if (var1 == null)
        {
            var1 = var2;
            var2 = null;
        }

        if (var1 == null)
        {
            this.setItem(2, (ItemStack) null);
        }
        else
        {
            MerchantRecipeList var3 = this.merchant.getOffers(this.player);

            if (var3 != null)
            {
                MerchantRecipe var4 = var3.a(var1, var2, this.e);

                if (var4 != null && !var4.g())
                {
                    this.recipe = var4;
                    this.setItem(2, var4.getBuyItem3().cloneItemStack());
                }
                else if (var2 != null)
                {
                    var4 = var3.a(var2, var1, this.e);

                    if (var4 != null && !var4.g())
                    {
                        this.recipe = var4;
                        this.setItem(2, var4.getBuyItem3().cloneItemStack());
                    }
                    else
                    {
                        this.setItem(2, (ItemStack) null);
                    }
                }
                else
                {
                    this.setItem(2, (ItemStack) null);
                }
            }
        }
    }

    public MerchantRecipe getRecipe()
    {
        return this.recipe;
    }

    public void c(int par1)
    {
        this.e = par1;
        this.g();
    }
}
