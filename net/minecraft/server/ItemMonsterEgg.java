package net.minecraft.server;

public class ItemMonsterEgg extends Item
{
    public ItemMonsterEgg(int par1)
    {
        super(par1);
        this.a(true);
        this.a(CreativeModeTab.f);
    }

    public String j(ItemStack par1ItemStack)
    {
        String var2 = ("" + LocaleI18n.get(this.getName() + ".name")).trim();
        String var3 = EntityTypes.b(par1ItemStack.getData());

        if (var3 != null)
        {
            var2 = var2 + " " + LocaleI18n.get("entity." + var3 + ".name");
        }

        return var2;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean interactWith(ItemStack par1ItemStack, EntityHuman par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isStatic)
        {
            return true;
        }
        else
        {
            int var11 = par3World.getTypeId(par4, par5, par6);
            par4 += Facing.b[par7];
            par5 += Facing.c[par7];
            par6 += Facing.d[par7];
            double var12 = 0.0D;

            if (par7 == 1 && Block.byId[var11] != null && Block.byId[var11].d() == 11)
            {
                var12 = 0.5D;
            }

            if (a(par3World, par1ItemStack.getData(), (double) par4 + 0.5D, (double) par5 + var12, (double) par6 + 0.5D) != null && !par2EntityPlayer.abilities.canInstantlyBuild)
            {
                --par1ItemStack.count;
            }

            return true;
        }
    }

    /**
     * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
     * Parameters: world, entityID, x, y, z.
     */
    public static Entity a(World par0World, int par1, double par2, double par4, double par6)
    {
        if (!EntityTypes.a.containsKey(Integer.valueOf(par1)))
        {
            return null;
        }
        else
        {
            Entity var8 = null;

            for (int var9 = 0; var9 < 1; ++var9)
            {
                var8 = EntityTypes.a(par1, par0World);

                if (var8 != null)
                {
                    var8.setPositionRotation(par2, par4, par6, par0World.random.nextFloat() * 360.0F, 0.0F);
                    ((EntityLiving)var8).bG();
                    par0World.addEntity(var8);
                    ((EntityLiving)var8).aO();
                }
            }

            return var8;
        }
    }
}
