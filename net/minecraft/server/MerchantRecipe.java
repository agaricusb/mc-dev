package net.minecraft.server;

public class MerchantRecipe
{
    /** Item the Villager buys. */
    private ItemStack buyingItem1;

    /** Second Item the Villager buys. */
    private ItemStack buyingItem2;

    /** Item the Villager sells. */
    private ItemStack sellingItem;

    /**
     * Saves how much has been tool used when put into to slot to be enchanted.
     */
    private int uses;

    /** Maximum times this trade can be used. */
    private int maxUses;

    public MerchantRecipe(NBTTagCompound par1NBTTagCompound)
    {
        this.a(par1NBTTagCompound);
    }

    public MerchantRecipe(ItemStack par1ItemStack, ItemStack par2ItemStack, ItemStack par3ItemStack)
    {
        this.buyingItem1 = par1ItemStack;
        this.buyingItem2 = par2ItemStack;
        this.sellingItem = par3ItemStack;
        this.maxUses = 7;
    }

    public MerchantRecipe(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        this(par1ItemStack, (ItemStack)null, par2ItemStack);
    }

    public MerchantRecipe(ItemStack par1ItemStack, Item par2Item)
    {
        this(par1ItemStack, new ItemStack(par2Item));
    }

    /**
     * Gets the itemToBuy.
     */
    public ItemStack getBuyItem1()
    {
        return this.buyingItem1;
    }

    /**
     * Gets secondItemToBuy.
     */
    public ItemStack getBuyItem2()
    {
        return this.buyingItem2;
    }

    /**
     * Gets if Villager has secondItemToBuy.
     */
    public boolean hasSecondItem()
    {
        return this.buyingItem2 != null;
    }

    /**
     * Gets itemToSell.
     */
    public ItemStack getBuyItem3()
    {
        return this.sellingItem;
    }

    /**
     * checks if both the first and second ItemToBuy IDs are the same
     */
    public boolean a(MerchantRecipe par1MerchantRecipe)
    {
        return this.buyingItem1.id == par1MerchantRecipe.buyingItem1.id && this.sellingItem.id == par1MerchantRecipe.sellingItem.id ? this.buyingItem2 == null && par1MerchantRecipe.buyingItem2 == null || this.buyingItem2 != null && par1MerchantRecipe.buyingItem2 != null && this.buyingItem2.id == par1MerchantRecipe.buyingItem2.id : false;
    }

    /**
     * checks first and second ItemToBuy ID's and count. Calls hasSameIDs
     */
    public boolean b(MerchantRecipe par1MerchantRecipe)
    {
        return this.a(par1MerchantRecipe) && (this.buyingItem1.count < par1MerchantRecipe.buyingItem1.count || this.buyingItem2 != null && this.buyingItem2.count < par1MerchantRecipe.buyingItem2.count);
    }

    public void f()
    {
        ++this.uses;
    }

    public void a(int par1)
    {
        this.maxUses += par1;
    }

    public boolean g()
    {
        return this.uses >= this.maxUses;
    }

    public void a(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagCompound var2 = par1NBTTagCompound.getCompound("buy");
        this.buyingItem1 = ItemStack.a(var2);
        NBTTagCompound var3 = par1NBTTagCompound.getCompound("sell");
        this.sellingItem = ItemStack.a(var3);

        if (par1NBTTagCompound.hasKey("buyB"))
        {
            this.buyingItem2 = ItemStack.a(par1NBTTagCompound.getCompound("buyB"));
        }

        if (par1NBTTagCompound.hasKey("uses"))
        {
            this.uses = par1NBTTagCompound.getInt("uses");
        }

        if (par1NBTTagCompound.hasKey("maxUses"))
        {
            this.maxUses = par1NBTTagCompound.getInt("maxUses");
        }
        else
        {
            this.maxUses = 7;
        }
    }

    public NBTTagCompound i()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        var1.setCompound("buy", this.buyingItem1.save(new NBTTagCompound("buy")));
        var1.setCompound("sell", this.sellingItem.save(new NBTTagCompound("sell")));

        if (this.buyingItem2 != null)
        {
            var1.setCompound("buyB", this.buyingItem2.save(new NBTTagCompound("buyB")));
        }

        var1.setInt("uses", this.uses);
        var1.setInt("maxUses", this.maxUses);
        return var1;
    }
}
