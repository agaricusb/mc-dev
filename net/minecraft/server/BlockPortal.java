package net.minecraft.server;

import java.util.Random;

public class BlockPortal extends BlockHalfTransparant
{
    public BlockPortal(int par1, int par2)
    {
        super(par1, par2, Material.PORTAL, false);
        this.b(true);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.b(par1World, par2, par3, par4, par5Random);

        if (par1World.worldProvider.d() && par5Random.nextInt(2000) < par1World.difficulty)
        {
            int var6;

            for (var6 = par3; !par1World.v(par2, var6, par4) && var6 > 0; --var6)
            {
                ;
            }

            if (var6 > 0 && !par1World.t(par2, var6 + 1, par4))
            {
                Entity var7 = ItemMonsterEgg.a(par1World, 57, (double) par2 + 0.5D, (double) var6 + 1.1D, (double) par4 + 0.5D);

                if (var7 != null)
                {
                    var7.portalCooldown = var7.ab();
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
        return null;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        float var5;
        float var6;

        if (par1IBlockAccess.getTypeId(par2 - 1, par3, par4) != this.id && par1IBlockAccess.getTypeId(par2 + 1, par3, par4) != this.id)
        {
            var5 = 0.125F;
            var6 = 0.5F;
            this.a(0.5F - var5, 0.0F, 0.5F - var6, 0.5F + var5, 1.0F, 0.5F + var6);
        }
        else
        {
            var5 = 0.5F;
            var6 = 0.125F;
            this.a(0.5F - var5, 0.0F, 0.5F - var6, 0.5F + var5, 1.0F, 0.5F + var6);
        }
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
     * Checks to see if this location is valid to create a portal and will return True if it does. Args: world, x, y, z
     */
    public boolean i_(World par1World, int par2, int par3, int par4)
    {
        byte var5 = 0;
        byte var6 = 0;

        if (par1World.getTypeId(par2 - 1, par3, par4) == Block.OBSIDIAN.id || par1World.getTypeId(par2 + 1, par3, par4) == Block.OBSIDIAN.id)
        {
            var5 = 1;
        }

        if (par1World.getTypeId(par2, par3, par4 - 1) == Block.OBSIDIAN.id || par1World.getTypeId(par2, par3, par4 + 1) == Block.OBSIDIAN.id)
        {
            var6 = 1;
        }

        if (var5 == var6)
        {
            return false;
        }
        else
        {
            if (par1World.getTypeId(par2 - var5, par3, par4 - var6) == 0)
            {
                par2 -= var5;
                par4 -= var6;
            }

            int var7;
            int var8;

            for (var7 = -1; var7 <= 2; ++var7)
            {
                for (var8 = -1; var8 <= 3; ++var8)
                {
                    boolean var9 = var7 == -1 || var7 == 2 || var8 == -1 || var8 == 3;

                    if (var7 != -1 && var7 != 2 || var8 != -1 && var8 != 3)
                    {
                        int var10 = par1World.getTypeId(par2 + var5 * var7, par3 + var8, par4 + var6 * var7);

                        if (var9)
                        {
                            if (var10 != Block.OBSIDIAN.id)
                            {
                                return false;
                            }
                        }
                        else if (var10 != 0 && var10 != Block.FIRE.id)
                        {
                            return false;
                        }
                    }
                }
            }

            par1World.suppressPhysics = true;

            for (var7 = 0; var7 < 2; ++var7)
            {
                for (var8 = 0; var8 < 3; ++var8)
                {
                    par1World.setTypeId(par2 + var5 * var7, par3 + var8, par4 + var6 * var7, Block.PORTAL.id);
                }
            }

            par1World.suppressPhysics = false;
            return true;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        byte var6 = 0;
        byte var7 = 1;

        if (par1World.getTypeId(par2 - 1, par3, par4) == this.id || par1World.getTypeId(par2 + 1, par3, par4) == this.id)
        {
            var6 = 1;
            var7 = 0;
        }

        int var8;

        for (var8 = par3; par1World.getTypeId(par2, var8 - 1, par4) == this.id; --var8)
        {
            ;
        }

        if (par1World.getTypeId(par2, var8 - 1, par4) != Block.OBSIDIAN.id)
        {
            par1World.setTypeId(par2, par3, par4, 0);
        }
        else
        {
            int var9;

            for (var9 = 1; var9 < 4 && par1World.getTypeId(par2, var8 + var9, par4) == this.id; ++var9)
            {
                ;
            }

            if (var9 == 3 && par1World.getTypeId(par2, var8 + var9, par4) == Block.OBSIDIAN.id)
            {
                boolean var10 = par1World.getTypeId(par2 - 1, par3, par4) == this.id || par1World.getTypeId(par2 + 1, par3, par4) == this.id;
                boolean var11 = par1World.getTypeId(par2, par3, par4 - 1) == this.id || par1World.getTypeId(par2, par3, par4 + 1) == this.id;

                if (var10 && var11)
                {
                    par1World.setTypeId(par2, par3, par4, 0);
                }
                else
                {
                    if ((par1World.getTypeId(par2 + var6, par3, par4 + var7) != Block.OBSIDIAN.id || par1World.getTypeId(par2 - var6, par3, par4 - var7) != this.id) && (par1World.getTypeId(par2 - var6, par3, par4 - var7) != Block.OBSIDIAN.id || par1World.getTypeId(par2 + var6, par3, par4 + var7) != this.id))
                    {
                        par1World.setTypeId(par2, par3, par4, 0);
                    }
                }
            }
            else
            {
                par1World.setTypeId(par2, par3, par4, 0);
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
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void a(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity.vehicle == null && par5Entity.passenger == null)
        {
            par5Entity.aa();
        }
    }
}
