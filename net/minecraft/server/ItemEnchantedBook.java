package net.minecraft.server;

import java.util.Random;

public class ItemEnchantedBook extends Item
{
    public ItemEnchantedBook(int par1)
    {
        super(par1);
    }

    /**
     * Checks isDamagable and if it cannot be stacked
     */
    public boolean d_(ItemStack par1ItemStack)
    {
        return false;
    }

    public NBTTagList g(ItemStack par1ItemStack)
    {
        return par1ItemStack.tag != null && par1ItemStack.tag.hasKey("StoredEnchantments") ? (NBTTagList)par1ItemStack.tag.get("StoredEnchantments") : new NBTTagList();
    }

    public void a(ItemStack par1ItemStack, EnchantmentInstance par2EnchantmentData)
    {
        NBTTagList var3 = this.g(par1ItemStack);
        boolean var4 = true;

        for (int var5 = 0; var5 < var3.size(); ++var5)
        {
            NBTTagCompound var6 = (NBTTagCompound)var3.get(var5);

            if (var6.getShort("id") == par2EnchantmentData.enchantment.id)
            {
                if (var6.getShort("lvl") < par2EnchantmentData.level)
                {
                    var6.setShort("lvl", (short) par2EnchantmentData.level);
                }

                var4 = false;
                break;
            }
        }

        if (var4)
        {
            NBTTagCompound var7 = new NBTTagCompound();
            var7.setShort("id", (short) par2EnchantmentData.enchantment.id);
            var7.setShort("lvl", (short) par2EnchantmentData.level);
            var3.add(var7);
        }

        if (!par1ItemStack.hasTag())
        {
            par1ItemStack.setTag(new NBTTagCompound());
        }

        par1ItemStack.getTag().set("StoredEnchantments", var3);
    }

    public ItemStack a(EnchantmentInstance par1EnchantmentData)
    {
        ItemStack var2 = new ItemStack(this);
        this.a(var2, par1EnchantmentData);
        return var2;
    }

    public ItemStack a(Random par1Random)
    {
        Enchantment var2 = Enchantment.c[par1Random.nextInt(Enchantment.c.length)];
        ItemStack var3 = new ItemStack(this.id, 1, 0);
        int var4 = MathHelper.nextInt(par1Random, var2.getStartLevel(), var2.getMaxLevel());
        this.a(var3, new EnchantmentInstance(var2, var4));
        return var3;
    }

    public StructurePieceTreasure b(Random par1Random)
    {
        return this.a(par1Random, 1, 1, 1);
    }

    public StructurePieceTreasure a(Random par1, int par2, int par3, int par4)
    {
        Enchantment var5 = Enchantment.c[par1.nextInt(Enchantment.c.length)];
        ItemStack var6 = new ItemStack(this.id, 1, 0);
        int var7 = MathHelper.nextInt(par1, var5.getStartLevel(), var5.getMaxLevel());
        this.a(var6, new EnchantmentInstance(var5, var7));
        return new StructurePieceTreasure(var6, par2, par3, par4);
    }
}
