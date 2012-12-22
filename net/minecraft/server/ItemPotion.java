package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemPotion extends Item
{
    /**
     * Contains a map from integers to the list of potion effects that potions with that damage value confer (to prevent
     * recalculating it).
     */
    private HashMap a = new HashMap();
    private static final Map b = new LinkedHashMap();

    public ItemPotion(int par1)
    {
        super(par1);
        this.d(1);
        this.a(true);
        this.setMaxDurability(0);
        this.a(CreativeModeTab.k);
    }

    /**
     * Returns a list of potion effects for the specified itemstack.
     */
    public List g(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTag() && par1ItemStack.getTag().hasKey("CustomPotionEffects"))
        {
            ArrayList var6 = new ArrayList();
            NBTTagList var3 = par1ItemStack.getTag().getList("CustomPotionEffects");

            for (int var4 = 0; var4 < var3.size(); ++var4)
            {
                NBTTagCompound var5 = (NBTTagCompound)var3.get(var4);
                var6.add(MobEffect.b(var5));
            }

            return var6;
        }
        else
        {
            List var2 = (List)this.a.get(Integer.valueOf(par1ItemStack.getData()));

            if (var2 == null)
            {
                var2 = PotionBrewer.getEffects(par1ItemStack.getData(), false);
                this.a.put(Integer.valueOf(par1ItemStack.getData()), var2);
            }

            return var2;
        }
    }

    /**
     * Returns a list of effects for the specified potion damage value.
     */
    public List f(int par1)
    {
        List var2 = (List)this.a.get(Integer.valueOf(par1));

        if (var2 == null)
        {
            var2 = PotionBrewer.getEffects(par1, false);
            this.a.put(Integer.valueOf(par1), var2);
        }

        return var2;
    }

    public ItemStack b(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (!par3EntityPlayer.abilities.canInstantlyBuild)
        {
            --par1ItemStack.count;
        }

        if (!par2World.isStatic)
        {
            List var4 = this.g(par1ItemStack);

            if (var4 != null)
            {
                Iterator var5 = var4.iterator();

                while (var5.hasNext())
                {
                    MobEffect var6 = (MobEffect)var5.next();
                    par3EntityPlayer.addEffect(new MobEffect(var6));
                }
            }
        }

        if (!par3EntityPlayer.abilities.canInstantlyBuild)
        {
            if (par1ItemStack.count <= 0)
            {
                return new ItemStack(Item.GLASS_BOTTLE);
            }

            par3EntityPlayer.inventory.pickup(new ItemStack(Item.GLASS_BOTTLE));
        }

        return par1ItemStack;
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
        return EnumAnimation.c;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack a(ItemStack par1ItemStack, World par2World, EntityHuman par3EntityPlayer)
    {
        if (g(par1ItemStack.getData()))
        {
            if (!par3EntityPlayer.abilities.canInstantlyBuild)
            {
                --par1ItemStack.count;
            }

            par2World.makeSound(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (d.nextFloat() * 0.4F + 0.8F));

            if (!par2World.isStatic)
            {
                par2World.addEntity(new EntityPotion(par2World, par3EntityPlayer, par1ItemStack));
            }

            return par1ItemStack;
        }
        else
        {
            par3EntityPlayer.a(par1ItemStack, this.c_(par1ItemStack));
            return par1ItemStack;
        }
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        return false;
    }

    /**
     * returns wether or not a potion is a throwable splash potion based on damage value
     */
    public static boolean g(int par0)
    {
        return (par0 & 16384) != 0;
    }

    public String l(ItemStack par1ItemStack)
    {
        if (par1ItemStack.getData() == 0)
        {
            return LocaleI18n.get("item.emptyPotion.name").trim();
        }
        else
        {
            String var2 = "";

            if (g(par1ItemStack.getData()))
            {
                var2 = LocaleI18n.get("potion.prefix.grenade").trim() + " ";
            }

            List var3 = Item.POTION.g(par1ItemStack);
            String var4;

            if (var3 != null && !var3.isEmpty())
            {
                var4 = ((MobEffect)var3.get(0)).f();
                var4 = var4 + ".postfix";
                return var2 + LocaleI18n.get(var4).trim();
            }
            else
            {
                var4 = PotionBrewer.c(par1ItemStack.getData());
                return LocaleI18n.get(var4).trim() + " " + super.l(par1ItemStack);
            }
        }
    }
}
