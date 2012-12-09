package net.minecraft.server;

import java.util.Random;

public class BlockStationary extends BlockFluids
{
    protected BlockStationary(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.b(false);

        if (par2Material == Material.LAVA)
        {
            this.b(true);
        }
    }

    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.material != Material.LAVA;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        super.doPhysics(par1World, par2, par3, par4, par5);

        if (par1World.getTypeId(par2, par3, par4) == this.id)
        {
            this.l(par1World, par2, par3, par4);
        }
    }

    /**
     * Changes the block ID to that of an updating fluid.
     */
    private void l(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getData(par2, par3, par4);
        par1World.suppressPhysics = true;
        par1World.setRawTypeIdAndData(par2, par3, par4, this.id - 1, var5);
        par1World.e(par2, par3, par4, par2, par3, par4);
        par1World.a(par2, par3, par4, this.id - 1, this.r_());
        par1World.suppressPhysics = false;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (this.material == Material.LAVA)
        {
            int var6 = par5Random.nextInt(3);
            int var7;
            int var8;

            for (var7 = 0; var7 < var6; ++var7)
            {
                par2 += par5Random.nextInt(3) - 1;
                ++par3;
                par4 += par5Random.nextInt(3) - 1;
                var8 = par1World.getTypeId(par2, par3, par4);

                if (var8 == 0)
                {
                    if (this.n(par1World, par2 - 1, par3, par4) || this.n(par1World, par2 + 1, par3, par4) || this.n(par1World, par2, par3, par4 - 1) || this.n(par1World, par2, par3, par4 + 1) || this.n(par1World, par2, par3 - 1, par4) || this.n(par1World, par2, par3 + 1, par4))
                    {
                        par1World.setTypeId(par2, par3, par4, Block.FIRE.id);
                        return;
                    }
                }
                else if (Block.byId[var8].material.isSolid())
                {
                    return;
                }
            }

            if (var6 == 0)
            {
                var7 = par2;
                var8 = par4;

                for (int var9 = 0; var9 < 3; ++var9)
                {
                    par2 = var7 + par5Random.nextInt(3) - 1;
                    par4 = var8 + par5Random.nextInt(3) - 1;

                    if (par1World.isEmpty(par2, par3 + 1, par4) && this.n(par1World, par2, par3, par4))
                    {
                        par1World.setTypeId(par2, par3 + 1, par4, Block.FIRE.id);
                    }
                }
            }
        }
    }

    /**
     * Checks to see if the block is flammable.
     */
    private boolean n(World par1World, int par2, int par3, int par4)
    {
        return par1World.getMaterial(par2, par3, par4).isBurnable();
    }
}
