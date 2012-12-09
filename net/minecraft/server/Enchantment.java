package net.minecraft.server;

public abstract class Enchantment
{
    public static final Enchantment[] byId = new Enchantment[256];

    /** Converts environmental damage to armour damage */
    public static final Enchantment PROTECTION_ENVIRONMENTAL = new EnchantmentProtection(0, 10, 0);

    /** Protection against fire */
    public static final Enchantment PROTECTION_FIRE = new EnchantmentProtection(1, 5, 1);

    /** Less fall damage */
    public static final Enchantment PROTECTION_FALL = new EnchantmentProtection(2, 5, 2);

    /** Protection against explosions */
    public static final Enchantment PROTECTION_EXPLOSIONS = new EnchantmentProtection(3, 2, 3);

    /** Protection against projectile entities (e.g. arrows) */
    public static final Enchantment PROTECTION_PROJECTILE = new EnchantmentProtection(4, 5, 4);

    /**
     * Decreases the rate of air loss underwater; increases time between damage while suffocating
     */
    public static final Enchantment OXYGEN = new EnchantmentOxygen(5, 2);

    /** Increases underwater mining rate */
    public static final Enchantment WATER_WORKER = new EnchantmentWaterWorker(6, 2);

    /** Extra damage to mobs */
    public static final Enchantment DAMAGE_ALL = new EnchantmentWeaponDamage(16, 10, 0);

    /** Extra damage to zombies, zombie pigmen and skeletons */
    public static final Enchantment DAMAGE_UNDEAD = new EnchantmentWeaponDamage(17, 5, 1);

    /** Extra damage to spiders, cave spiders and silverfish */
    public static final Enchantment DAMAGE_ARTHROPODS = new EnchantmentWeaponDamage(18, 5, 2);

    /** Knocks mob and players backwards upon hit */
    public static final Enchantment KNOCKBACK = new EnchantmentKnockback(19, 5);

    /** Lights mobs on fire */
    public static final Enchantment FIRE_ASPECT = new EnchantmentFire(20, 2);

    /** Mobs have a chance to drop more loot */
    public static final Enchantment LOOT_BONUS_MOBS = new EnchantmentLootBonus(21, 2, EnchantmentSlotType.WEAPON);

    /** Faster resource gathering while in use */
    public static final Enchantment DIG_SPEED = new EnchantmentDigging(32, 10);

    /**
     * Blocks mined will drop themselves, even if it should drop something else (e.g. stone will drop stone, not
     * cobblestone)
     */
    public static final Enchantment SILK_TOUCH = new EnchantmentSilkTouch(33, 1);

    /**
     * Sometimes, the tool's durability will not be spent when the tool is used
     */
    public static final Enchantment DURABILITY = new EnchantmentDurability(34, 5);

    /** Can multiply the drop rate of items from blocks */
    public static final Enchantment LOOT_BONUS_BLOCKS = new EnchantmentLootBonus(35, 2, EnchantmentSlotType.DIGGER);

    /** Power enchantment for bows, add's extra damage to arrows. */
    public static final Enchantment ARROW_DAMAGE = new EnchantmentArrowDamage(48, 10);

    /**
     * Knockback enchantments for bows, the arrows will knockback the target when hit.
     */
    public static final Enchantment ARROW_KNOCKBACK = new EnchantmentArrowKnockback(49, 2);

    /**
     * Flame enchantment for bows. Arrows fired by the bow will be on fire. Any target hit will also set on fire.
     */
    public static final Enchantment ARROW_FIRE = new EnchantmentFlameArrows(50, 2);

    /**
     * Infinity enchantment for bows. The bow will not consume arrows anymore, but will still required at least one
     * arrow on inventory use the bow.
     */
    public static final Enchantment ARROW_INFINITE = new EnchantmentInfiniteArrows(51, 1);
    public final int id;
    private final int weight;

    /** The EnumEnchantmentType given to this Enchantment. */
    public EnchantmentSlotType slot;

    /** Used in localisation and stats. */
    protected String name;

    protected Enchantment(int par1, int par2, EnchantmentSlotType par3EnumEnchantmentType)
    {
        this.id = par1;
        this.weight = par2;
        this.slot = par3EnumEnchantmentType;

        if (byId[par1] != null)
        {
            throw new IllegalArgumentException("Duplicate enchantment id!");
        }
        else
        {
            byId[par1] = this;
        }
    }

    public int getRandomWeight()
    {
        return this.weight;
    }

    /**
     * Returns the minimum level that the enchantment can have.
     */
    public int getStartLevel()
    {
        return 1;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int a(int par1)
    {
        return 1 + par1 * 10;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int b(int par1)
    {
        return this.a(par1) + 5;
    }

    /**
     * Calculates de damage protection of the enchantment based on level and damage source passed.
     */
    public int a(int par1, DamageSource par2DamageSource)
    {
        return 0;
    }

    /**
     * Calculates de (magic) damage done by the enchantment on a living entity based on level and entity passed.
     */
    public int a(int par1, EntityLiving par2EntityLiving)
    {
        return 0;
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean a(Enchantment par1Enchantment)
    {
        return this != par1Enchantment;
    }

    /**
     * Sets the enchantment name
     */
    public Enchantment b(String par1Str)
    {
        this.name = par1Str;
        return this;
    }

    public String a()
    {
        return "enchantment." + this.name;
    }

    public String c(int par1)
    {
        String var2 = LocaleI18n.get(this.a());
        return var2 + " " + LocaleI18n.get("enchantment.level." + par1);
    }
}
