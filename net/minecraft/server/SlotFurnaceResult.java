package net.minecraft.server;

public class SlotFurnaceResult extends Slot
{
    /** The player that is using the GUI where this slot resides. */
    private EntityHuman a;
    private int b;

    public SlotFurnaceResult(EntityHuman par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.a = par1EntityPlayer;
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
            this.b += Math.min(par1, this.getItem().count);
        }

        return super.a(par1);
    }

    public void a(EntityHuman par1EntityPlayer, ItemStack par2ItemStack)
    {
        this.b(par2ItemStack);
        super.a(par1EntityPlayer, par2ItemStack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void a(ItemStack par1ItemStack, int par2)
    {
        this.b += par2;
        this.b(par1ItemStack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void b(ItemStack par1ItemStack)
    {
        par1ItemStack.a(this.a.world, this.a, this.b);

        if (!this.a.world.isStatic)
        {
            int var2 = this.b;
            float var3 = RecipesFurnace.getInstance().c(par1ItemStack.id);
            int var4;

            if (var3 == 0.0F)
            {
                var2 = 0;
            }
            else if (var3 < 1.0F)
            {
                var4 = MathHelper.d((float) var2 * var3);

                if (var4 < MathHelper.f((float) var2 * var3) && (float)Math.random() < (float)var2 * var3 - (float)var4)
                {
                    ++var4;
                }

                var2 = var4;
            }

            while (var2 > 0)
            {
                var4 = EntityExperienceOrb.getOrbValue(var2);
                var2 -= var4;
                this.a.world.addEntity(new EntityExperienceOrb(this.a.world, this.a.locX, this.a.locY + 0.5D, this.a.locZ + 0.5D, var4));
            }
        }

        this.b = 0;

        if (par1ItemStack.id == Item.IRON_INGOT.id)
        {
            this.a.a(AchievementList.k, 1);
        }

        if (par1ItemStack.id == Item.COOKED_FISH.id)
        {
            this.a.a(AchievementList.p, 1);
        }
    }
}
