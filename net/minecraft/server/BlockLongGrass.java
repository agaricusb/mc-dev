package net.minecraft.server;

import java.util.Random;

public class BlockLongGrass extends BlockFlower
{
    protected BlockLongGrass(int par1, int par2)
    {
        super(par1, par2, Material.REPLACEABLE_PLANT);
        float var3 = 0.4F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par2 == 1 ? this.textureId : (par2 == 2 ? this.textureId + 16 + 1 : (par2 == 0 ? this.textureId + 16 : this.textureId));
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return par2Random.nextInt(8) == 0 ? Item.SEEDS.id : -1;
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int getDropCount(int par1, Random par2Random)
    {
        return 1 + par2Random.nextInt(par1 * 2 + 1);
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void a(World par1World, EntityHuman par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        if (!par1World.isStatic && par2EntityPlayer.bS() != null && par2EntityPlayer.bS().id == Item.SHEARS.id)
        {
            par2EntityPlayer.a(StatisticList.C[this.id], 1);
            this.b(par1World, par3, par4, par5, new ItemStack(Block.LONG_GRASS, 1, par6));
        }
        else
        {
            super.a(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDropData(World par1World, int par2, int par3, int par4)
    {
        return par1World.getData(par2, par3, par4);
    }
}
