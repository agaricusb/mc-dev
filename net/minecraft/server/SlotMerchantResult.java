package net.minecraft.server;

public class SlotMerchantResult extends Slot
{
    /** Merchant's inventory. */
    private final InventoryMerchant a;

    /** The Player whos trying to buy/sell stuff. */
    private EntityHuman b;
    private int c;

    /** "Instance" of the Merchant. */
    private final IMerchant d;

    public SlotMerchantResult(EntityHuman par1EntityPlayer, IMerchant par2IMerchant, InventoryMerchant par3InventoryMerchant, int par4, int par5, int par6)
    {
        super(par3InventoryMerchant, par4, par5, par6);
        this.b = par1EntityPlayer;
        this.d = par2IMerchant;
        this.a = par3InventoryMerchant;
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
    }

    public void a(EntityHuman par1EntityPlayer, ItemStack par2ItemStack)
    {
        this.b(par2ItemStack);
        MerchantRecipe var3 = this.a.getRecipe();

        if (var3 != null)
        {
            ItemStack var4 = this.a.getItem(0);
            ItemStack var5 = this.a.getItem(1);

            if (this.a(var3, var4, var5) || this.a(var3, var5, var4))
            {
                if (var4 != null && var4.count <= 0)
                {
                    var4 = null;
                }

                if (var5 != null && var5.count <= 0)
                {
                    var5 = null;
                }

                this.a.setItem(0, var4);
                this.a.setItem(1, var5);
                this.d.a(var3);
            }
        }
    }

    private boolean a(MerchantRecipe par1MerchantRecipe, ItemStack par2ItemStack, ItemStack par3ItemStack)
    {
        ItemStack var4 = par1MerchantRecipe.getBuyItem1();
        ItemStack var5 = par1MerchantRecipe.getBuyItem2();

        if (par2ItemStack != null && par2ItemStack.id == var4.id)
        {
            if (var5 != null && par3ItemStack != null && var5.id == par3ItemStack.id)
            {
                par2ItemStack.count -= var4.count;
                par3ItemStack.count -= var5.count;
                return true;
            }

            if (var5 == null && par3ItemStack == null)
            {
                par2ItemStack.count -= var4.count;
                return true;
            }
        }

        return false;
    }
}
