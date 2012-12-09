package net.minecraft.server;

import java.util.Random;

public class BlockIce extends BlockHalfTransparant
{
    public BlockIce(int par1, int par2)
    {
        super(par1, par2, Material.ICE, false);
        this.frictionFactor = 0.98F;
        this.b(true);
        this.a(CreativeModeTab.b);
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void a(World par1World, EntityHuman par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        par2EntityPlayer.a(StatisticList.C[this.id], 1);
        par2EntityPlayer.j(0.025F);

        if (this.s_() && EnchantmentManager.hasSilkTouchEnchantment(par2EntityPlayer))
        {
            ItemStack var9 = this.f_(par6);

            if (var9 != null)
            {
                this.b(par1World, par3, par4, par5, var9);
            }
        }
        else
        {
            if (par1World.worldProvider.e)
            {
                par1World.setTypeId(par3, par4, par5, 0);
                return;
            }

            int var7 = EnchantmentManager.getBonusBlockLootEnchantmentLevel(par2EntityPlayer);
            this.c(par1World, par3, par4, par5, par6, var7);
            Material var8 = par1World.getMaterial(par3, par4 - 1, par5);

            if (var8.isSolid() || var8.isLiquid())
            {
                par1World.setTypeId(par3, par4, par5, Block.WATER.id);
            }
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int a(Random par1Random)
    {
        return 0;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.b(EnumSkyBlock.BLOCK, par2, par3, par4) > 11 - Block.lightBlock[this.id])
        {
            if (par1World.worldProvider.e)
            {
                par1World.setTypeId(par2, par3, par4, 0);
                return;
            }

            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, Block.STATIONARY_WATER.id);
        }
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int q_()
    {
        return 0;
    }
}
