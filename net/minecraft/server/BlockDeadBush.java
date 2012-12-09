package net.minecraft.server;

import java.util.Random;

public class BlockDeadBush extends BlockFlower
{
    protected BlockDeadBush(int par1, int par2)
    {
        super(par1, par2, Material.REPLACEABLE_PLANT);
        float var3 = 0.4F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean d_(int par1)
    {
        return par1 == Block.SAND.id;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return this.textureId;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return -1;
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void a(World par1World, EntityHuman par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        if (!par1World.isStatic && par2EntityPlayer.bT() != null && par2EntityPlayer.bT().id == Item.SHEARS.id)
        {
            par2EntityPlayer.a(StatisticList.C[this.id], 1);
            this.b(par1World, par3, par4, par5, new ItemStack(Block.DEAD_BUSH, 1, par6));
        }
        else
        {
            super.a(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }
}
