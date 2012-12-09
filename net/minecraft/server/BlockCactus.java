package net.minecraft.server;

import java.util.Random;

public class BlockCactus extends Block
{
    protected BlockCactus(int par1, int par2)
    {
        super(par1, par2, Material.CACTUS);
        this.b(true);
        this.a(CreativeModeTab.c);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.isEmpty(par2, par3 + 1, par4))
        {
            int var6;

            for (var6 = 1; par1World.getTypeId(par2, par3 - var6, par4) == this.id; ++var6)
            {
                ;
            }

            if (var6 < 3)
            {
                int var7 = par1World.getData(par2, par3, par4);

                if (var7 == 15)
                {
                    par1World.setTypeId(par2, par3 + 1, par4, this.id);
                    par1World.setData(par2, par3, par4, 0);
                }
                else
                {
                    par1World.setData(par2, par3, par4, var7 + 1);
                }
            }
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        float var5 = 0.0625F;
        return AxisAlignedBB.a().a((double) ((float) par2 + var5), (double) par3, (double) ((float) par4 + var5), (double) ((float) (par2 + 1) - var5), (double) ((float) (par3 + 1) - var5), (double) ((float) (par4 + 1) - var5));
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int par1)
    {
        return par1 == 1 ? this.textureId - 1 : (par1 == 0 ? this.textureId + 1 : this.textureId);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean b()
    {
        return false;
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
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 13;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return !super.canPlace(par1World, par2, par3, par4) ? false : this.d(par1World, par2, par3, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!this.d(par1World, par2, par3, par4))
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean d(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getMaterial(par2 - 1, par3, par4).isBuildable())
        {
            return false;
        }
        else if (par1World.getMaterial(par2 + 1, par3, par4).isBuildable())
        {
            return false;
        }
        else if (par1World.getMaterial(par2, par3, par4 - 1).isBuildable())
        {
            return false;
        }
        else if (par1World.getMaterial(par2, par3, par4 + 1).isBuildable())
        {
            return false;
        }
        else
        {
            int var5 = par1World.getTypeId(par2, par3 - 1, par4);
            return var5 == Block.CACTUS.id || var5 == Block.SAND.id;
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        par5Entity.damageEntity(DamageSource.CACTUS, 1);
    }
}
