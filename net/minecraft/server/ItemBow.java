package net.minecraft.server;

public class ItemBow extends Item
{
    public ItemBow(int par1)
    {
        super(par1);
        this.maxStackSize = 1;
        this.setMaxDurability(384);
        this.a(CreativeModeTab.j);
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer, int par4)
    {
        boolean var5 = par3EntityPlayer.abilities.canInstantlyBuild || EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, par1ItemStack) > 0;

        if (var5 || par3EntityPlayer.inventory.e(Item.ARROW.id))
        {
            int var6 = this.c_(par1ItemStack) - par4;
            float var7 = (float)var6 / 20.0F;
            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

            if ((double)var7 < 0.1D)
            {
                return;
            }

            if (var7 > 1.0F)
            {
                var7 = 1.0F;
            }

            EntityArrow var8 = new EntityArrow(par2World, par3EntityPlayer, var7 * 2.0F);

            if (var7 == 1.0F)
            {
                var8.e(true);
            }

            int var9 = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, par1ItemStack);

            if (var9 > 0)
            {
                var8.b(var8.c() + (double) var9 * 0.5D + 0.5D);
            }

            int var10 = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, par1ItemStack);

            if (var10 > 0)
            {
                var8.a(var10);
            }

            if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, par1ItemStack) > 0)
            {
                var8.setOnFire(100);
            }

            par1ItemStack.damage(1, par3EntityPlayer);
            par2World.makeSound(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (d.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

            if (var5)
            {
                var8.fromPlayer = 2;
            }
            else
            {
                par3EntityPlayer.inventory.d(Item.ARROW.id);
            }

            if (!par2World.isStatic)
            {
                par2World.addEntity(var8);
            }
        }
    }

    public ItemStack b(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        return par1ItemStack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int c_(ItemStack par1ItemStack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAnimation b_(ItemStack par1ItemStack)
    {
        return EnumAnimation.e;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (par3EntityPlayer.abilities.canInstantlyBuild || par3EntityPlayer.inventory.e(Item.ARROW.id))
        {
            par3EntityPlayer.a(par1ItemStack, this.c_(par1ItemStack));
        }

        return par1ItemStack;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int c()
    {
        return 1;
    }
}
