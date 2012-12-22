package net.minecraft.server;

public class SlotResult extends Slot
{
    /** The craft matrix inventory linked to this result slot. */
    private final IInventory a;

    /** The player that is using the GUI where this slot resides. */
    private EntityHuman b;

    /**
     * The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset.
     */
    private int c;

    public SlotResult(EntityHuman par1EntityPlayer, IInventory par2IInventory, IInventory par3IInventory, int par4, int par5, int par6)
    {
        super(par3IInventory, par4, par5, par6);
        this.b = par1EntityPlayer;
        this.a = par2IInventory;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isAllowed(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack a(int par1)
    {
        if (this.d())
        {
            this.c += Math.min(par1, this.getItem().count);
        }

        return super.a(par1);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void a(ItemStack par1ItemStack, int par2)
    {
        this.c += par2;
        this.b(par1ItemStack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void b(ItemStack par1ItemStack)
    {
        par1ItemStack.a(this.b.world, this.b, this.c);
        this.c = 0;

        if (par1ItemStack.id == Block.WORKBENCH.id)
        {
            this.b.a(AchievementList.h, 1);
        }
        else if (par1ItemStack.id == Item.WOOD_PICKAXE.id)
        {
            this.b.a(AchievementList.i, 1);
        }
        else if (par1ItemStack.id == Block.FURNACE.id)
        {
            this.b.a(AchievementList.j, 1);
        }
        else if (par1ItemStack.id == Item.WOOD_HOE.id)
        {
            this.b.a(AchievementList.l, 1);
        }
        else if (par1ItemStack.id == Item.BREAD.id)
        {
            this.b.a(AchievementList.m, 1);
        }
        else if (par1ItemStack.id == Item.CAKE.id)
        {
            this.b.a(AchievementList.n, 1);
        }
        else if (par1ItemStack.id == Item.STONE_PICKAXE.id)
        {
            this.b.a(AchievementList.o, 1);
        }
        else if (par1ItemStack.id == Item.WOOD_SWORD.id)
        {
            this.b.a(AchievementList.r, 1);
        }
        else if (par1ItemStack.id == Block.ENCHANTMENT_TABLE.id)
        {
            this.b.a(AchievementList.D, 1);
        }
        else if (par1ItemStack.id == Block.BOOKSHELF.id)
        {
            this.b.a(AchievementList.F, 1);
        }
    }

    public void a(EntityHuman par1EntityPlayer, ItemStack par2ItemStack)
    {
        this.b(par2ItemStack);

        for (int var3 = 0; var3 < this.a.getSize(); ++var3)
        {
            ItemStack var4 = this.a.getItem(var3);

            if (var4 != null)
            {
                this.a.splitStack(var3, 1);

                if (var4.getItem().s())
                {
                    ItemStack var5 = new ItemStack(var4.getItem().r());

                    if (!var4.getItem().j(var4) || !this.b.inventory.pickup(var5))
                    {
                        if (this.a.getItem(var3) == null)
                        {
                            this.a.setItem(var3, var5);
                        }
                        else
                        {
                            this.b.drop(var5);
                        }
                    }
                }
            }
        }
    }
}
