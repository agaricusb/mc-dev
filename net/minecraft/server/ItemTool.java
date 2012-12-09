package net.minecraft.server;

public class ItemTool extends Item
{
    /** Array of blocks the tool has extra effect against. */
    private Block[] c;
    protected float a = 4.0F;

    /** Damage versus entities. */
    private int cl;

    /** The material this tool is made from. */
    protected EnumToolMaterial b;

    protected ItemTool(int par1, int par2, EnumToolMaterial par3EnumToolMaterial, Block[] par4ArrayOfBlock)
    {
        super(par1);
        this.b = par3EnumToolMaterial;
        this.c = par4ArrayOfBlock;
        this.maxStackSize = 1;
        this.setMaxDurability(par3EnumToolMaterial.a());
        this.a = par3EnumToolMaterial.b();
        this.cl = par2 + par3EnumToolMaterial.c();
        this.a(CreativeModeTab.i);
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getDestroySpeed(ItemStack par1ItemStack, Block par2Block)
    {
        for (int var3 = 0; var3 < this.c.length; ++var3)
        {
            if (this.c[var3] == par2Block)
            {
                return this.a;
            }
        }

        return 1.0F;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean a(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        par1ItemStack.damage(2, par3EntityLiving);
        return true;
    }

    public boolean a(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        if ((double) Block.byId[par3].m(par2World, par4, par5, par6) != 0.0D)
        {
            par1ItemStack.damage(1, par7EntityLiving);
        }

        return true;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int a(Entity par1Entity)
    {
        return this.cl;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int c()
    {
        return this.b.e();
    }

    /**
     * Return the name for this tool's material.
     */
    public String g()
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
