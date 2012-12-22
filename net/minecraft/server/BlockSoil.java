package net.minecraft.server;

import java.util.Random;

public class BlockSoil extends Block
{
    protected BlockSoil(int par1)
    {
        super(par1, Material.EARTH);
        this.textureId = 87;
        this.b(true);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        this.h(255);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.a().a((double) (par2 + 0), (double) (par3 + 0), (double) (par4 + 0), (double) (par2 + 1), (double) (par3 + 1), (double) (par4 + 1));
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean c()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int par1, int par2)
    {
        return par1 == 1 && par2 > 0 ? this.textureId - 1 : (par1 == 1 ? this.textureId : 2);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!this.n(par1World, par2, par3, par4) && !par1World.D(par2, par3 + 1, par4))
        {
            int var6 = par1World.getData(par2, par3, par4);

            if (var6 > 0)
            {
                par1World.setData(par2, par3, par4, var6 - 1);
            }
            else if (!this.l(par1World, par2, par3, par4))
            {
                par1World.setTypeId(par2, par3, par4, Block.DIRT.id);
            }
        }
        else
        {
            par1World.setData(par2, par3, par4, 7);
        }
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6)
    {
        if (!par1World.isStatic && par1World.random.nextFloat() < par6 - 0.5F)
        {
            if (!(par5Entity instanceof EntityHuman) && !par1World.getGameRules().getBoolean("mobGriefing"))
            {
                return;
            }

            par1World.setTypeId(par2, par3, par4, Block.DIRT.id);
        }
    }

    /**
     * returns true if there is at least one cropblock nearby (x-1 to x+1, y+1, z-1 to z+1)
     */
    private boolean l(World par1World, int par2, int par3, int par4)
    {
        byte var5 = 0;

        for (int var6 = par2 - var5; var6 <= par2 + var5; ++var6)
        {
            for (int var7 = par4 - var5; var7 <= par4 + var5; ++var7)
            {
                int var8 = par1World.getTypeId(var6, par3 + 1, var7);

                if (var8 == Block.CROPS.id || var8 == Block.MELON_STEM.id || var8 == Block.PUMPKIN_STEM.id || var8 == Block.POTATOES.id || var8 == Block.CARROTS.id)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * returns true if there's water nearby (x-4 to x+4, y to y+1, k-4 to k+4)
     */
    private boolean n(World par1World, int par2, int par3, int par4)
    {
        for (int var5 = par2 - 4; var5 <= par2 + 4; ++var5)
        {
            for (int var6 = par3; var6 <= par3 + 1; ++var6)
            {
                for (int var7 = par4 - 4; var7 <= par4 + 4; ++var7)
                {
                    if (par1World.getMaterial(var5, var6, var7) == Material.WATER)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        super.doPhysics(par1World, par2, par3, par4, par5);
        Material var6 = par1World.getMaterial(par2, par3 + 1, par4);

        if (var6.isBuildable())
        {
            par1World.setTypeId(par2, par3, par4, Block.DIRT.id);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Block.DIRT.getDropType(0, par2Random, par3);
    }
}
