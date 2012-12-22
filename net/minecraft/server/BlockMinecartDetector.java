package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class BlockMinecartDetector extends BlockMinecartTrack
{
    public BlockMinecartDetector(int par1, int par2)
    {
        super(par1, par2, true);
        this.b(true);
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 20;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return true;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (!par1World.isStatic)
        {
            int var6 = par1World.getData(par2, par3, par4);

            if ((var6 & 8) == 0)
            {
                this.d(par1World, par2, par3, par4, var6);
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isStatic)
        {
            int var6 = par1World.getData(par2, par3, par4);

            if ((var6 & 8) != 0)
            {
                this.d(par1World, par2, par3, par4, var6);
            }
        }
    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean b(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return (par1IBlockAccess.getData(par2, par3, par4) & 8) != 0;
    }

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return (par1IBlockAccess.getData(par2, par3, par4) & 8) == 0 ? false : par5 == 1;
    }

    /**
     * Update the detector rail power state if a minecart enter, stays or leave the block.
     */
    private void d(World par1World, int par2, int par3, int par4, int par5)
    {
        boolean var6 = (par5 & 8) != 0;
        boolean var7 = false;
        float var8 = 0.125F;
        List var9 = par1World.a(EntityMinecart.class, AxisAlignedBB.a().a((double) ((float) par2 + var8), (double) par3, (double) ((float) par4 + var8), (double) ((float) (par2 + 1) - var8), (double) ((float) (par3 + 1) - var8), (double) ((float) (par4 + 1) - var8)));

        if (!var9.isEmpty())
        {
            var7 = true;
        }

        if (var7 && !var6)
        {
            par1World.setData(par2, par3, par4, par5 | 8);
            par1World.applyPhysics(par2, par3, par4, this.id);
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            par1World.e(par2, par3, par4, par2, par3, par4);
        }

        if (!var7 && var6)
        {
            par1World.setData(par2, par3, par4, par5 & 7);
            par1World.applyPhysics(par2, par3, par4, this.id);
            par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            par1World.e(par2, par3, par4, par2, par3, par4);
        }

        if (var7)
        {
            par1World.a(par2, par3, par4, this.id, this.r_());
        }
    }
}
