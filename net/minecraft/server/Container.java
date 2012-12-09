package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Container
{
    /** the list of all items(stacks) for the corresponding slot */
    public List b = new ArrayList();

    /** the list of all slots in the inventory */
    public List c = new ArrayList();
    public int windowId = 0;
    private short a = 0;

    /**
     * list of all people that need to be notified when this craftinventory changes
     */
    protected List listeners = new ArrayList();
    private Set f = new HashSet();

    /**
     * Adds an item slot to this container
     */
    protected Slot a(Slot par1Slot)
    {
        par1Slot.g = this.c.size();
        this.c.add(par1Slot);
        this.b.add((Object)null);
        return par1Slot;
    }

    public void addSlotListener(ICrafting par1ICrafting)
    {
        if (this.listeners.contains(par1ICrafting))
        {
            throw new IllegalArgumentException("Listener already listening");
        }
        else
        {
            this.listeners.add(par1ICrafting);
            par1ICrafting.a(this, this.a());
            this.b();
        }
    }

    /**
     * returns a list if itemStacks, for each slot.
     */
    public List a()
    {
        ArrayList var1 = new ArrayList();

        for (int var2 = 0; var2 < this.c.size(); ++var2)
        {
            var1.add(((Slot)this.c.get(var2)).getItem());
        }

        return var1;
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void b()
    {
        for (int var1 = 0; var1 < this.c.size(); ++var1)
        {
            ItemStack var2 = ((Slot)this.c.get(var1)).getItem();
            ItemStack var3 = (ItemStack)this.b.get(var1);

            if (!ItemStack.matches(var3, var2))
            {
                var3 = var2 == null ? null : var2.cloneItemStack();
                this.b.set(var1, var3);

                for (int var4 = 0; var4 < this.listeners.size(); ++var4)
                {
                    ((ICrafting)this.listeners.get(var4)).a(this, var1, var3);
                }
            }
        }
    }

    /**
     * enchants the item on the table using the specified slot; also deducts XP from player
     */
    public boolean a(EntityHuman par1EntityPlayer, int par2)
    {
        return false;
    }

    public Slot a(IInventory par1IInventory, int par2)
    {
        for (int var3 = 0; var3 < this.c.size(); ++var3)
        {
            Slot var4 = (Slot)this.c.get(var3);

            if (var4.a(par1IInventory, par2))
            {
                return var4;
            }
        }

        return null;
    }

    public Slot getSlot(int par1)
    {
        return (Slot)this.c.get(par1);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    public ItemStack b(EntityHuman par1EntityPlayer, int par2)
    {
        Slot var3 = (Slot)this.c.get(par2);
        return var3 != null ? var3.getItem() : null;
    }

    public ItemStack clickItem(int par1, int par2, int par3, EntityHuman par4EntityPlayer)
    {
        ItemStack var5 = null;
        PlayerInventory var6 = par4EntityPlayer.inventory;
        Slot var7;
        ItemStack var8;
        int var10;
        ItemStack var11;

        if ((par3 == 0 || par3 == 1) && (par2 == 0 || par2 == 1))
        {
            if (par1 == -999)
            {
                if (var6.getCarried() != null && par1 == -999)
                {
                    if (par2 == 0)
                    {
                        par4EntityPlayer.drop(var6.getCarried());
                        var6.setCarried((ItemStack) null);
                    }

                    if (par2 == 1)
                    {
                        par4EntityPlayer.drop(var6.getCarried().a(1));

                        if (var6.getCarried().count == 0)
                        {
                            var6.setCarried((ItemStack) null);
                        }
                    }
                }
            }
            else if (par3 == 1)
            {
                var7 = (Slot)this.c.get(par1);

                if (var7 != null && var7.a(par4EntityPlayer))
                {
                    var8 = this.b(par4EntityPlayer, par1);

                    if (var8 != null)
                    {
                        int var12 = var8.id;
                        var5 = var8.cloneItemStack();

                        if (var7 != null && var7.getItem() != null && var7.getItem().id == var12)
                        {
                            this.a(par1, par2, true, par4EntityPlayer);
                        }
                    }
                }
            }
            else
            {
                if (par1 < 0)
                {
                    return null;
                }

                var7 = (Slot)this.c.get(par1);

                if (var7 != null)
                {
                    var8 = var7.getItem();
                    ItemStack var13 = var6.getCarried();

                    if (var8 != null)
                    {
                        var5 = var8.cloneItemStack();
                    }

                    if (var8 == null)
                    {
                        if (var13 != null && var7.isAllowed(var13))
                        {
                            var10 = par2 == 0 ? var13.count : 1;

                            if (var10 > var7.a())
                            {
                                var10 = var7.a();
                            }

                            var7.set(var13.a(var10));

                            if (var13.count == 0)
                            {
                                var6.setCarried((ItemStack) null);
                            }
                        }
                    }
                    else if (var7.a(par4EntityPlayer))
                    {
                        if (var13 == null)
                        {
                            var10 = par2 == 0 ? var8.count : (var8.count + 1) / 2;
                            var11 = var7.a(var10);
                            var6.setCarried(var11);

                            if (var8.count == 0)
                            {
                                var7.set((ItemStack) null);
                            }

                            var7.a(par4EntityPlayer, var6.getCarried());
                        }
                        else if (var7.isAllowed(var13))
                        {
                            if (var8.id == var13.id && (!var8.usesData() || var8.getData() == var13.getData()) && ItemStack.equals(var8, var13))
                            {
                                var10 = par2 == 0 ? var13.count : 1;

                                if (var10 > var7.a() - var8.count)
                                {
                                    var10 = var7.a() - var8.count;
                                }

                                if (var10 > var13.getMaxStackSize() - var8.count)
                                {
                                    var10 = var13.getMaxStackSize() - var8.count;
                                }

                                var13.a(var10);

                                if (var13.count == 0)
                                {
                                    var6.setCarried((ItemStack) null);
                                }

                                var8.count += var10;
                            }
                            else if (var13.count <= var7.a())
                            {
                                var7.set(var13);
                                var6.setCarried(var8);
                            }
                        }
                        else if (var8.id == var13.id && var13.getMaxStackSize() > 1 && (!var8.usesData() || var8.getData() == var13.getData()) && ItemStack.equals(var8, var13))
                        {
                            var10 = var8.count;

                            if (var10 > 0 && var10 + var13.count <= var13.getMaxStackSize())
                            {
                                var13.count += var10;
                                var8 = var7.a(var10);

                                if (var8.count == 0)
                                {
                                    var7.set((ItemStack) null);
                                }

                                var7.a(par4EntityPlayer, var6.getCarried());
                            }
                        }
                    }

                    var7.e();
                }
            }
        }
        else if (par3 == 2 && par2 >= 0 && par2 < 9)
        {
            var7 = (Slot)this.c.get(par1);

            if (var7.a(par4EntityPlayer))
            {
                var8 = var6.getItem(par2);
                boolean var9 = var8 == null || var7.inventory == var6 && var7.isAllowed(var8);
                var10 = -1;

                if (!var9)
                {
                    var10 = var6.i();
                    var9 |= var10 > -1;
                }

                if (var7.d() && var9)
                {
                    var11 = var7.getItem();
                    var6.setItem(par2, var11);

                    if ((var7.inventory != var6 || !var7.isAllowed(var8)) && var8 != null)
                    {
                        if (var10 > -1)
                        {
                            var6.pickup(var8);
                            var7.set((ItemStack) null);
                            var7.a(par4EntityPlayer, var11);
                        }
                    }
                    else
                    {
                        var7.set(var8);
                        var7.a(par4EntityPlayer, var11);
                    }
                }
                else if (!var7.d() && var8 != null && var7.isAllowed(var8))
                {
                    var6.setItem(par2, (ItemStack) null);
                    var7.set(var8);
                }
            }
        }
        else if (par3 == 3 && par4EntityPlayer.abilities.canInstantlyBuild && var6.getCarried() == null && par1 >= 0)
        {
            var7 = (Slot)this.c.get(par1);

            if (var7 != null && var7.d())
            {
                var8 = var7.getItem().cloneItemStack();
                var8.count = var8.getMaxStackSize();
                var6.setCarried(var8);
            }
        }

        return var5;
    }

    protected void a(int par1, int par2, boolean par3, EntityHuman par4EntityPlayer)
    {
        this.clickItem(par1, par2, 1, par4EntityPlayer);
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void b(EntityHuman par1EntityPlayer)
    {
        PlayerInventory var2 = par1EntityPlayer.inventory;

        if (var2.getCarried() != null)
        {
            par1EntityPlayer.drop(var2.getCarried());
            var2.setCarried((ItemStack) null);
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void a(IInventory par1IInventory)
    {
        this.b();
    }

    /**
     * args: slotID, itemStack to put in slot
     */
    public void setItem(int par1, ItemStack par2ItemStack)
    {
        this.getSlot(par1).set(par2ItemStack);
    }

    /**
     * gets whether or not the player can craft in this inventory or not
     */
    public boolean c(EntityHuman par1EntityPlayer)
    {
        return !this.f.contains(par1EntityPlayer);
    }

    /**
     * sets whether the player can craft in this inventory or not
     */
    public void a(EntityHuman par1EntityPlayer, boolean par2)
    {
        if (par2)
        {
            this.f.remove(par1EntityPlayer);
        }
        else
        {
            this.f.add(par1EntityPlayer);
        }
    }

    public abstract boolean a(EntityHuman var1);

    /**
     * merges provided ItemStack with the first avaliable one in the container/player inventory
     */
    protected boolean a(ItemStack par1ItemStack, int par2, int par3, boolean par4)
    {
        boolean var5 = false;
        int var6 = par2;

        if (par4)
        {
            var6 = par3 - 1;
        }

        Slot var7;
        ItemStack var8;

        if (par1ItemStack.isStackable())
        {
            while (par1ItemStack.count > 0 && (!par4 && var6 < par3 || par4 && var6 >= par2))
            {
                var7 = (Slot)this.c.get(var6);
                var8 = var7.getItem();

                if (var8 != null && var8.id == par1ItemStack.id && (!par1ItemStack.usesData() || par1ItemStack.getData() == var8.getData()) && ItemStack.equals(par1ItemStack, var8))
                {
                    int var9 = var8.count + par1ItemStack.count;

                    if (var9 <= par1ItemStack.getMaxStackSize())
                    {
                        par1ItemStack.count = 0;
                        var8.count = var9;
                        var7.e();
                        var5 = true;
                    }
                    else if (var8.count < par1ItemStack.getMaxStackSize())
                    {
                        par1ItemStack.count -= par1ItemStack.getMaxStackSize() - var8.count;
                        var8.count = par1ItemStack.getMaxStackSize();
                        var7.e();
                        var5 = true;
                    }
                }

                if (par4)
                {
                    --var6;
                }
                else
                {
                    ++var6;
                }
            }
        }

        if (par1ItemStack.count > 0)
        {
            if (par4)
            {
                var6 = par3 - 1;
            }
            else
            {
                var6 = par2;
            }

            while (!par4 && var6 < par3 || par4 && var6 >= par2)
            {
                var7 = (Slot)this.c.get(var6);
                var8 = var7.getItem();

                if (var8 == null)
                {
                    var7.set(par1ItemStack.cloneItemStack());
                    var7.e();
                    par1ItemStack.count = 0;
                    var5 = true;
                    break;
                }

                if (par4)
                {
                    --var6;
                }
                else
                {
                    ++var6;
                }
            }
        }

        return var5;
    }
}
