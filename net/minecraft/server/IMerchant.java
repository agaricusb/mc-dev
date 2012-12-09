package net.minecraft.server;

public interface IMerchant
{
    void b_(EntityHuman var1);

    EntityHuman m_();

    MerchantRecipeList getOffers(EntityHuman var1);

    void a(MerchantRecipe var1);
}
