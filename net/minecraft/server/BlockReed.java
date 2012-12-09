package net.minecraft.server;

import java.util.Random;

public class BlockReed extends Block
{
    protected BlockReed(int par1, int par2)
    {
        super(par1, Material.PLANT);
        this.textureId = par2;
        float var3 = 0.375F;
        this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
        this.b(true);
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
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getTypeId(par2, par3 - 1, par4);
        return var5 == this.id ? true : (var5 != Block.GRASS.id && var5 != Block.DIRT.id && var5 != Block.SAND.id ? false : (par1World.getMaterial(par2 - 1, par3 - 1, par4) == Material.WATER ? true : (par1World.getMaterial(par2 + 1, par3 - 1, par4) == Material.WATER ? true : (par1World.getMaterial(par2, par3 - 1, par4 - 1) == Material.WATER ? true : par1World.getMaterial(par2, par3 - 1, par4 + 1) == Material.WATER))));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        this.k_(par1World, par2, par3, par4);
    }

    /**
     * Checks if current block pos is valid, if not, breaks the block as dropable item. Used for reed and cactus.
     */
    protected final void k_(World par1World, int par2, int par3, int par4)
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
        return this.canPlace(par1World, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB e(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int par1, Random par2Random, int par3)
    {
        return Item.SUGAR_CANE.id;
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
     * The type of render function that is called for this block
     */
    public int d()
    {
        return 1;
    }
}
