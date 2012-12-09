package net.minecraft.server;

import java.util.Random;

public class BlockOre extends Block
{
    public BlockOre(int par1, int par2)
    {
        super(par1, par2, Material.STONE);
        this.a(CreativeModeTab.b);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return this.id == Block.COAL_ORE.id ? Item.COAL.id : (this.id == Block.DIAMOND_ORE.id ? Item.DIAMOND.id : (this.id == Block.LAPIS_ORE.id ? Item.INK_SACK.id : (this.id == Block.EMERALD_ORE.id ? Item.EMERALD.id : this.id)));
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return this.id == Block.LAPIS_ORE.id ? 4 + par1Random.nextInt(5) : 1;
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int getDropCount(int par1, Random par2Random)
    {
        if (par1 > 0 && this.id != this.getDropType(0, par2Random, par1))
        {
            int var3 = par2Random.nextInt(par1 + 2) - 1;

            if (var3 < 0)
            {
                var3 = 0;
            }

            return this.a(par2Random) * (var3 + 1);
        }
        else
        {
            return this.a(par2Random);
        }
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropNaturally(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropNaturally(par1World, par2, par3, par4, par5, par6, par7);

        if (this.getDropType(par5, par1World.random, par7) != this.id)
        {
            int var8 = 0;

            if (this.id == Block.COAL_ORE.id)
            {
                var8 = MathHelper.nextInt(par1World.random, 0, 2);
            }
            else if (this.id == Block.DIAMOND_ORE.id)
            {
                var8 = MathHelper.nextInt(par1World.random, 3, 7);
            }
            else if (this.id == Block.EMERALD_ORE.id)
            {
                var8 = MathHelper.nextInt(par1World.random, 3, 7);
            }
            else if (this.id == Block.LAPIS_ORE.id)
            {
                var8 = MathHelper.nextInt(par1World.random, 2, 5);
            }

            this.f(par1World, par2, par3, par4, var8);
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int getDropData(int par1)
    {
        return this.id == Block.LAPIS_ORE.id ? 4 : 0;
    }
}
