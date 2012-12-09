package net.minecraft.server;

public class ItemSword extends Item
{
    private int damage;
    private final EnumToolMaterial b;

    public ItemSword(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1);
        this.b = par2EnumToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDurability(par2EnumToolMaterial.a());
        this.a(CreativeModeTab.j);
        this.damage = 4 + par2EnumToolMaterial.c();
    }

    public int g()
    {
        return this.b.c();
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getDestroySpeed(ItemStack par1ItemStack, Block par2Block)
    {
        if (par2Block.id == Block.WEB.id)
        {
            return 15.0F;
        }
        else
        {
            Material var3 = par2Block.material;
            return var3 != Material.PLANT && var3 != Material.REPLACEABLE_PLANT && var3 != Material.CORAL && var3 != Material.LEAVES && var3 != Material.PUMPKIN ? 1.0F : 1.5F;
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean a(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        par1ItemStack.damage(1, par3EntityLiving);
        return true;
    }

    public boolean a(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        if ((double) Block.byId[par3].m(par2World, par4, par5, par6) != 0.0D)
        {
            par1ItemStack.damage(2, par7EntityLiving);
        }

        return true;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int a(Entity par1Entity)
    {
        return this.damage;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAnimation d_(ItemStack par1ItemStack)
    {
        return EnumAnimation.d;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int a(ItemStack par1ItemStack)
    {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        par3EntityPlayer.a(par1ItemStack, this.a(par1ItemStack));
        return par1ItemStack;
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canDestroySpecialBlock(Block par1Block)
    {
        return par1Block.id == Block.WEB.id;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int c()
    {
        return this.b.e();
    }

    public String h()
    {
        return this.b.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean a(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return this.b.f() == par2ItemStack.id ? true : super.a(par1ItemStack, par2ItemStack);
    }
}
