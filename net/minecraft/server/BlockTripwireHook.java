package net.minecraft.server;

import java.util.Random;

public class BlockTripwireHook extends Block
{
    public BlockTripwireHook(int par1)
    {
        super(par1, 172, Material.ORIENTABLE);
        this.a(CreativeModeTab.d);
        this.b(true);
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
        return 29;
    }

    /**
     * How many world ticks before ticking
     */
    public int r_()
    {
        return 10;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4, int par5)
    {
        return par5 == 2 && par1World.t(par2, par3, par4 + 1) ? true : (par5 == 3 && par1World.t(par2, par3, par4 - 1) ? true : (par5 == 4 && par1World.t(par2 + 1, par3, par4) ? true : par5 == 5 && par1World.t(par2 - 1, par3, par4)));
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlace(World par1World, int par2, int par3, int par4)
    {
        return par1World.t(par2 - 1, par3, par4) ? true : (par1World.t(par2 + 1, par3, par4) ? true : (par1World.t(par2, par3, par4 - 1) ? true : par1World.t(par2, par3, par4 + 1)));
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int getPlacedData(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        byte var10 = 0;

        if (par5 == 2 && par1World.b(par2, par3, par4 + 1, true))
        {
            var10 = 2;
        }

        if (par5 == 3 && par1World.b(par2, par3, par4 - 1, true))
        {
            var10 = 0;
        }

        if (par5 == 4 && par1World.b(par2 + 1, par3, par4, true))
        {
            var10 = 1;
        }

        if (par5 == 5 && par1World.b(par2 - 1, par3, par4, true))
        {
            var10 = 3;
        }

        return var10;
    }

    /**
     * Called after a block is placed
     */
    public void postPlace(World par1World, int par2, int par3, int par4, int par5)
    {
        this.a(par1World, par2, par3, par4, this.id, par5, false, -1, 0);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 != this.id)
        {
            if (this.l(par1World, par2, par3, par4))
            {
                int var6 = par1World.getData(par2, par3, par4);
                int var7 = var6 & 3;
                boolean var8 = false;

                if (!par1World.t(par2 - 1, par3, par4) && var7 == 3)
                {
                    var8 = true;
                }

                if (!par1World.t(par2 + 1, par3, par4) && var7 == 1)
                {
                    var8 = true;
                }

                if (!par1World.t(par2, par3, par4 - 1) && var7 == 0)
                {
                    var8 = true;
                }

                if (!par1World.t(par2, par3, par4 + 1) && var7 == 2)
                {
                    var8 = true;
                }

                if (var8)
                {
                    this.c(par1World, par2, par3, par4, var6, 0);
                    par1World.setTypeId(par2, par3, par4, 0);
                }
            }
        }
    }

    public void a(World par1World, int par2, int par3, int par4, int par5, int par6, boolean par7, int par8, int par9)
    {
        int var10 = par6 & 3;
        boolean var11 = (par6 & 4) == 4;
        boolean var12 = (par6 & 8) == 8;
        boolean var13 = par5 == Block.TRIPWIRE_SOURCE.id;
        boolean var14 = false;
        boolean var15 = !par1World.v(par2, par3 - 1, par4);
        int var16 = Direction.a[var10];
        int var17 = Direction.b[var10];
        int var18 = 0;
        int[] var19 = new int[42];
        int var21;
        int var20;
        int var23;
        int var22;
        int var24;

        for (var20 = 1; var20 < 42; ++var20)
        {
            var21 = par2 + var16 * var20;
            var22 = par4 + var17 * var20;
            var23 = par1World.getTypeId(var21, par3, var22);

            if (var23 == Block.TRIPWIRE_SOURCE.id)
            {
                var24 = par1World.getData(var21, par3, var22);

                if ((var24 & 3) == Direction.f[var10])
                {
                    var18 = var20;
                }

                break;
            }

            if (var23 != Block.TRIPWIRE.id && var20 != par8)
            {
                var19[var20] = -1;
                var13 = false;
            }
            else
            {
                var24 = var20 == par8 ? par9 : par1World.getData(var21, par3, var22);
                boolean var25 = (var24 & 8) != 8;
                boolean var26 = (var24 & 1) == 1;
                boolean var27 = (var24 & 2) == 2;
                var13 &= var27 == var15;
                var14 |= var25 && var26;
                var19[var20] = var24;

                if (var20 == par8)
                {
                    par1World.a(par2, par3, par4, par5, this.r_());
                    var13 &= var25;
                }
            }
        }

        var13 &= var18 > 1;
        var14 &= var13;
        var20 = (var13 ? 4 : 0) | (var14 ? 8 : 0);
        par6 = var10 | var20;

        if (var18 > 0)
        {
            var21 = par2 + var16 * var18;
            var22 = par4 + var17 * var18;
            var23 = Direction.f[var10];
            par1World.setData(var21, par3, var22, var23 | var20);
            this.d(par1World, var21, par3, var22, var23);
            this.a(par1World, var21, par3, var22, var13, var14, var11, var12);
        }

        this.a(par1World, par2, par3, par4, var13, var14, var11, var12);

        if (par5 > 0)
        {
            par1World.setData(par2, par3, par4, par6);

            if (par7)
            {
                this.d(par1World, par2, par3, par4, var10);
            }
        }

        if (var11 != var13)
        {
            for (var21 = 1; var21 < var18; ++var21)
            {
                var22 = par2 + var16 * var21;
                var23 = par4 + var17 * var21;
                var24 = var19[var21];

                if (var24 >= 0)
                {
                    if (var13)
                    {
                        var24 |= 4;
                    }
                    else
                    {
                        var24 &= -5;
                    }

                    par1World.setData(var22, par3, var23, var24);
                }
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void b(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        this.a(par1World, par2, par3, par4, this.id, par1World.getData(par2, par3, par4), true, -1, 0);
    }

    /**
     * only of the conditions are right
     */
    private void a(World par1World, int par2, int par3, int par4, boolean par5, boolean par6, boolean par7, boolean par8)
    {
        if (par6 && !par8)
        {
            par1World.makeSound((double) par2 + 0.5D, (double) par3 + 0.1D, (double) par4 + 0.5D, "random.click", 0.4F, 0.6F);
        }
        else if (!par6 && par8)
        {
            par1World.makeSound((double) par2 + 0.5D, (double) par3 + 0.1D, (double) par4 + 0.5D, "random.click", 0.4F, 0.5F);
        }
        else if (par5 && !par7)
        {
            par1World.makeSound((double) par2 + 0.5D, (double) par3 + 0.1D, (double) par4 + 0.5D, "random.click", 0.4F, 0.7F);
        }
        else if (!par5 && par7)
        {
            par1World.makeSound((double) par2 + 0.5D, (double) par3 + 0.1D, (double) par4 + 0.5D, "random.bowhit", 0.4F, 1.2F / (par1World.random.nextFloat() * 0.2F + 0.9F));
        }
    }

    private void d(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.applyPhysics(par2, par3, par4, this.id);

        if (par5 == 3)
        {
            par1World.applyPhysics(par2 - 1, par3, par4, this.id);
        }
        else if (par5 == 1)
        {
            par1World.applyPhysics(par2 + 1, par3, par4, this.id);
        }
        else if (par5 == 0)
        {
            par1World.applyPhysics(par2, par3, par4 - 1, this.id);
        }
        else if (par5 == 2)
        {
            par1World.applyPhysics(par2, par3, par4 + 1, this.id);
        }
    }

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
        int var5 = par1IBlockAccess.getData(par2, par3, par4) & 3;
        float var6 = 0.1875F;

        if (var5 == 3)
        {
            this.a(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F, 0.5F + var6);
        }
        else if (var5 == 1)
        {
            this.a(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F, 0.8F, 0.5F + var6);
        }
        else if (var5 == 0)
        {
            this.a(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F, var6 * 2.0F);
        }
        else if (var5 == 2)
        {
            this.a(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F, 0.5F + var6, 0.8F, 1.0F);
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void remove(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        boolean var7 = (par6 & 4) == 4;
        boolean var8 = (par6 & 8) == 8;

        if (var7 || var8)
        {
            this.a(par1World, par2, par3, par4, 0, par6, false, -1, 0);
        }

        if (var8)
        {
            par1World.applyPhysics(par2, par3, par4, this.id);
            int var9 = par6 & 3;

            if (var9 == 3)
            {
                par1World.applyPhysics(par2 - 1, par3, par4, this.id);
            }
            else if (var9 == 1)
            {
                par1World.applyPhysics(par2 + 1, par3, par4, this.id);
            }
            else if (var9 == 0)
            {
                par1World.applyPhysics(par2, par3, par4 - 1, this.id);
            }
            else if (var9 == 2)
            {
                par1World.applyPhysics(par2, par3, par4 + 1, this.id);
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
        return (par1IBlockAccess.getData(par2, par3, par4) & 8) == 8;
    }

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int var6 = par1IBlockAccess.getData(par2, par3, par4);

        if ((var6 & 8) != 8)
        {
            return false;
        }
        else
        {
            int var7 = var6 & 3;
            return var7 == 2 && par5 == 2 ? true : (var7 == 0 && par5 == 3 ? true : (var7 == 1 && par5 == 4 ? true : var7 == 3 && par5 == 5));
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
