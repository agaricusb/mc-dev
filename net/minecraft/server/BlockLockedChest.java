package net.minecraft.server;

import java.util.Random;

public class BlockLockedChest extends Block
{
    protected BlockLockedChest(int par1)
    {
        super(par1, Material.WOOD);
        this.textureId = 26;
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 == 1 ? this.textureId - 1 : (par1 == 0 ? this.textureId - 1 : (par1 == 3 ? this.textureId + 1 : this.textureId));
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return true;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        par1World.setTypeId(par2, par3, par4, 0);
    }
}
