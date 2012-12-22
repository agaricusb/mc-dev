package net.minecraft.server;

public class BlockLever extends Block
{
    protected BlockLever(int par1, int par2)
    {
        super(par1, par2, Material.ORIENTABLE);
        this.a(CreativeModeTab.d);
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
        return 12;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4, int par5)
    {
        return par5 == 0 && par1World.t(par2, par3 + 1, par4) ? true : (par5 == 1 && par1World.v(par2, par3 - 1, par4) ? true : (par5 == 2 && par1World.t(par2, par3, par4 + 1) ? true : (par5 == 3 && par1World.t(par2, par3, par4 - 1) ? true : (par5 == 4 && par1World.t(par2 + 1, par3, par4) ? true : par5 == 5 && par1World.t(par2 - 1, par3, par4)))));
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return par1World.t(par2 - 1, par3, par4) ? true : (par1World.t(par2 + 1, par3, par4) ? true : (par1World.t(par2, par3, par4 - 1) ? true : (par1World.t(par2, par3, par4 + 1) ? true : (par1World.v(par2, par3 - 1, par4) ? true : par1World.t(par2, par3 + 1, par4)))));
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int getPlacedData(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int var11 = par9 & 8;
        int var10 = par9 & 7;
        var10 = -1;

        if (par5 == 0 && par1World.t(par2, par3 + 1, par4))
        {
            var10 = par1World.random.nextBoolean() ? 0 : 7;
        }

        if (par5 == 1 && par1World.v(par2, par3 - 1, par4))
        {
            var10 = 5 + par1World.random.nextInt(2);
        }

        if (par5 == 2 && par1World.t(par2, par3, par4 + 1))
        {
            var10 = 4;
        }

        if (par5 == 3 && par1World.t(par2, par3, par4 - 1))
        {
            var10 = 3;
        }

        if (par5 == 4 && par1World.t(par2 + 1, par3, par4))
        {
            var10 = 2;
        }

        if (par5 == 5 && par1World.t(par2 - 1, par3, par4))
        {
            var10 = 1;
        }

        return var10 + var11;
    }

    /**
     * only used in ComponentScatteredFeatureJunglePyramid.addComponentParts"
     */
    public static int e(int par0)
    {
        switch (par0)
        {
            case 0:
                return 0;

            case 1:
                return 5;

            case 2:
                return 4;

            case 3:
                return 3;

            case 4:
                return 2;

            case 5:
                return 1;

            default:
                return -1;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (this.l(par1World, par2, par3, par4))
        {
            int var6 = par1World.getData(par2, par3, par4) & 7;
            boolean var7 = false;

            if (!par1World.t(par2 - 1, par3, par4) && var6 == 1)
            {
                var7 = true;
            }

            if (!par1World.t(par2 + 1, par3, par4) && var6 == 2)
            {
                var7 = true;
            }

            if (!par1World.t(par2, par3, par4 - 1) && var6 == 3)
            {
                var7 = true;
            }

            if (!par1World.t(par2, par3, par4 + 1) && var6 == 4)
            {
                var7 = true;
            }

            if (!par1World.v(par2, par3 - 1, par4) && var6 == 5)
            {
                var7 = true;
            }

            if (!par1World.v(par2, par3 - 1, par4) && var6 == 6)
            {
                var7 = true;
            }

            if (!par1World.t(par2, par3 + 1, par4) && var6 == 0)
            {
                var7 = true;
            }

            if (!par1World.t(par2, par3 + 1, par4) && var6 == 7)
            {
                var7 = true;
            }

            if (var7)
            {
                this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
                par1World.setTypeId(par2, par3, par4, 0);
            }
        }
    }

    /**
     * Checks if the block is attached to another block. If it is not, it returns false and drops the block as an item.
     * If it is it returns true.
     */
    private boolean l(World par1World, int par2, int par3, int par4)
    {
        if (!this.canPlace(par1World, par2, par3, par4))
        {
            this.c(par1World, par2, par3, par4, par1World.getData(par2, par3, par4), 0);
            par1World.setTypeId(par2, par3, par4, 0);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void updateShape(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getData(par2, par3, par4) & 7;
        float var6 = 0.1875F;

        if (var5 == 1)
        {
            this.a(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F, 0.5F + var6);
        }
        else if (var5 == 2)
        {
            this.a(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F, 0.8F, 0.5F + var6);
        }
        else if (var5 == 3)
        {
            this.a(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F, var6 * 2.0F);
        }
        else if (var5 == 4)
        {
            this.a(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F, 0.5F + var6, 0.8F, 1.0F);
        }
        else if (var5 != 5 && var5 != 6)
        {
            if (var5 == 0 || var5 == 7)
            {
                var6 = 0.25F;
                this.a(0.5F - var6, 0.4F, 0.5F - var6, 0.5F + var6, 1.0F, 0.5F + var6);
            }
        }
        else
        {
            var6 = 0.25F;
            this.a(0.5F - var6, 0.0F, 0.5F - var6, 0.5F + var6, 0.6F, 0.5F + var6);
        }
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void attack(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer) {}

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean interact(World par1World, int par2, int par3, int par4, EntityHuman par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isStatic)
        {
            return true;
        }
        else
        {
            int var10 = par1World.getData(par2, par3, par4);
            int var11 = var10 & 7;
            int var12 = 8 - (var10 & 8);
            par1World.setData(par2, par3, par4, var11 + var12);
            par1World.e(par2, par3, par4, par2, par3, par4);
            par1World.makeSound((double) par2 + 0.5D, (double) par3 + 0.5D, (double) par4 + 0.5D, "random.click", 0.3F, var12 > 0 ? 0.6F : 0.5F);
            par1World.applyPhysics(par2, par3, par4, this.id);

            if (var11 == 1)
            {
                par1World.applyPhysics(par2 - 1, par3, par4, this.id);
            }
            else if (var11 == 2)
            {
                par1World.applyPhysics(par2 + 1, par3, par4, this.id);
            }
            else if (var11 == 3)
            {
                par1World.applyPhysics(par2, par3, par4 - 1, this.id);
            }
            else if (var11 == 4)
            {
                par1World.applyPhysics(par2, par3, par4 + 1, this.id);
            }
            else if (var11 != 5 && var11 != 6)
            {
                if (var11 == 0 || var11 == 7)
                {
                    par1World.applyPhysics(par2, par3 + 1, par4, this.id);
                }
            }
            else
            {
                par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            }

            return true;
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if ((par6 & 8) > 0)
        {
            par1World.applyPhysics(par2, par3, par4, this.id);
            int var7 = par6 & 7;

            if (var7 == 1)
            {
                par1World.applyPhysics(par2 - 1, par3, par4, this.id);
            }
            else if (var7 == 2)
            {
                par1World.applyPhysics(par2 + 1, par3, par4, this.id);
            }
            else if (var7 == 3)
            {
                par1World.applyPhysics(par2, par3, par4 - 1, this.id);
            }
            else if (var7 == 4)
            {
                par1World.applyPhysics(par2, par3, par4 + 1, this.id);
            }
            else if (var7 != 5 && var7 != 6)
            {
                if (var7 == 0 || var7 == 7)
                {
                    par1World.applyPhysics(par2, par3 + 1, par4, this.id);
                }
            }
            else
            {
                par1World.applyPhysics(par2, par3 - 1, par4, this.id);
            }
        }

        super.remove(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean b(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return (par1IBlockAccess.getData(par2, par3, par4) & 8) > 0;
    }

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int var6 = par1IBlockAccess.getData(par2, par3, par4);

        if ((var6 & 8) == 0)
        {
            return false;
        }
        else
        {
            int var7 = var6 & 7;
            return var7 == 0 && par5 == 0 ? true : (var7 == 7 && par5 == 0 ? true : (var7 == 6 && par5 == 1 ? true : (var7 == 5 && par5 == 1 ? true : (var7 == 4 && par5 == 2 ? true : (var7 == 3 && par5 == 3 ? true : (var7 == 2 && par5 == 4 ? true : var7 == 1 && par5 == 5))))));
        }
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return true;
    }
}
