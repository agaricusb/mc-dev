package net.minecraft.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MerchantRecipeList extends ArrayList
{
    public MerchantRecipeList() {}

    public MerchantRecipeList(NBTTagCompound par1NBTTagCompound)
    {
        this.a(par1NBTTagCompound);
    }

    /**
     * can par1,par2 be used to in crafting recipe par3
     */
    public MerchantRecipe a(ItemStack par1ItemStack, ItemStack par2ItemStack, int par3)
    {
        if (par3 > 0 && par3 < this.size())
        {
            MerchantRecipe var6 = (MerchantRecipe)this.get(par3);
            return par1ItemStack.id == var6.getBuyItem1().id && (par2ItemStack == null && !var6.hasSecondItem() || var6.hasSecondItem() && par2ItemStack != null && var6.getBuyItem2().id == par2ItemStack.id) && par1ItemStack.count >= var6.getBuyItem1().count && (!var6.hasSecondItem() || par2ItemStack.count >= var6.getBuyItem2().count) ? var6 : null;
        }
        else
        {
            for (int var4 = 0; var4 < this.size(); ++var4)
            {
                MerchantRecipe var5 = (MerchantRecipe)this.get(var4);

                if (par1ItemStack.id == var5.getBuyItem1().id && par1ItemStack.count >= var5.getBuyItem1().count && (!var5.hasSecondItem() && par2ItemStack == null || var5.hasSecondItem() && par2ItemStack != null && var5.getBuyItem2().id == par2ItemStack.id && par2ItemStack.count >= var5.getBuyItem2().count))
                {
                    return var5;
                }
            }

            return null;
        }
    }

    /**
     * checks if there is a recipie for the same ingredients already on the list, and replaces it. otherwise, adds it
     */
    public void a(MerchantRecipe par1MerchantRecipe)
    {
        for (int var2 = 0; var2 < this.size(); ++var2)
        {
            MerchantRecipe var3 = (MerchantRecipe)this.get(var2);

            if (par1MerchantRecipe.a(var3))
            {
                if (par1MerchantRecipe.b(var3))
                {
                    this.set(var2, par1MerchantRecipe);
                }

                return;
            }
        }

        this.add(par1MerchantRecipe);
    }

    public void a(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte((byte)(this.size() & 255));

        for (int var2 = 0; var2 < this.size(); ++var2)
        {
            MerchantRecipe var3 = (MerchantRecipe)this.get(var2);
            Packet.a(var3.getBuyItem1(), par1DataOutputStream);
            Packet.a(var3.getBuyItem3(), par1DataOutputStream);
            ItemStack var4 = var3.getBuyItem2();
            par1DataOutputStream.writeBoolean(var4 != null);

            if (var4 != null)
            {
                Packet.a(var4, par1DataOutputStream);
            }

            par1DataOutputStream.writeBoolean(var3.g());
        }
    }

    public void a(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagList var2 = par1NBTTagCompound.getList("Recipes");

        for (int var3 = 0; var3 < var2.size(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
            this.add(new MerchantRecipe(var4));
        }
    }

    public NBTTagCompound a()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        NBTTagList var2 = new NBTTagList("Recipes");

        for (int var3 = 0; var3 < this.size(); ++var3)
        {
            MerchantRecipe var4 = (MerchantRecipe)this.get(var3);
            var2.add(var4.i());
        }

        var1.set("Recipes", var2);
        return var1;
    }
}
