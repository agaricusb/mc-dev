package net.minecraft.server;

public class ItemFood extends Item
{
    /** Number of ticks to run while 'EnumAction'ing until result. */
    public final int a;

    /** The amount this food item heals the player. */
    private final int b;
    private final float c;

    /** Whether wolves like this food (true for raw and cooked porkchop). */
    private final boolean co;

    /**
     * If this field is true, the food can be consumed even if the player don't need to eat.
     */
    private boolean cp;

    /**
     * represents the potion effect that will occurr upon eating this food. Set by setPotionEffect
     */
    private int cq;

    /** set by setPotionEffect */
    private int cr;

    /** set by setPotionEffect */
    private int cs;

    /** probably of the set potion effect occurring */
    private float ct;

    public ItemFood(int par1, int par2, float par3, boolean par4)
    {
        super(par1);
        this.a = 32;
        this.b = par2;
        this.co = par4;
        this.c = par3;
        this.a(CreativeModeTab.h);
    }

    public ItemFood(int par1, int par2, boolean par3)
    {
        this(par1, par2, 0.6F, par3);
    }

    public ItemStack b(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        --par1ItemStack.count;
        par3EntityPlayer.getFoodData().a(this);
        par2World.makeSound(par3EntityPlayer, "random.burp", 0.5F, par2World.random.nextFloat() * 0.1F + 0.9F);
        this.c(par1ItemStack, par2World, par3EntityPlayer);
        return par1ItemStack;
    }

    protected void c(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (!par2World.isStatic && this.cq > 0 && par2World.random.nextFloat() < this.ct)
        {
            par3EntityPlayer.addEffect(new MobEffect(this.cq, this.cr * 20, this.cs));
        }
    }

    /**
     * How long it takes to use or consume an item
     */
    public int c_(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAnimation b_(ItemStack par1ItemStack)
    {
        return EnumAnimation.b;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (par3EntityPlayer.g(this.cp))
        {
            par3EntityPlayer.a(par1ItemStack, this.c_(par1ItemStack));
        }

        return par1ItemStack;
    }

    public int getNutrition()
    {
        return this.b;
    }

    /**
     * gets the saturationModifier of the ItemFood
     */
    public float getSaturationModifier()
    {
        return this.c;
    }

    /**
     * Whether wolves like this food (true for raw and cooked porkchop).
     */
    public boolean i()
    {
        return this.co;
    }

    /**
     * sets a potion effect on the item. Args: int potionId, int duration (will be multiplied by 20), int amplifier,
     * float probability of effect happening
     */
    public ItemFood a(int par1, int par2, int par3, float par4)
    {
        this.cq = par1;
        this.cr = par2;
        this.cs = par3;
        this.ct = par4;
        return this;
    }

    /**
     * Set the field 'alwaysEdible' to true, and make the food edible even if the player don't need to eat.
     */
    public ItemFood j()
    {
        this.cp = true;
        return this;
    }
}
